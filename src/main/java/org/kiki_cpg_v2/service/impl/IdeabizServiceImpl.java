package org.kiki_cpg_v2.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.DialogPaymentConfirmDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.entity.InvoiceEntity;
import org.kiki_cpg_v2.repository.IdeabizRepository;
import org.kiki_cpg_v2.service.IdeabizService;
import org.kiki_cpg_v2.service.InvoiceService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.PaymentMethodService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdeabizServiceImpl implements IdeabizService {

	@Autowired
	private DialogClient dialogClient;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private IdeabizRepository ideabizRepository;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Autowired
	private AppUtility appUtility;

	@Override
	public DialogPaymentConfirmDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception {
		String accessToken = dialogClient.createAccessToken();
		Double amount = paymentMethodService.getPaymentPlanAmount(dialogOtpConfirmDto.getDay(), 4);

		String message = "";

		if (amount > 0) {
			DialogOtpDto dialogOtpDto = dialogClient.pinSubscriptionConfirm(dialogOtpConfirmDto, amount, accessToken);
			System.out.println(dialogOtpDto.toString());

			if (dialogOtpDto.getStatusCode().equals("SUCCESS")) {
				String mobileNo = dialogOtpDto.getMsisdn();
				mobileNo = mobileNo.replace("tel:", "");
				viewerService.updateViewerMobileNumber(mobileNo, dialogOtpConfirmDto.getViewerId());

				if (dialogOtpDto.getStatus().equals("SUBSCRIBED")) {

					IdeabizEntity ideabizEntity = getIdeabizEntity(dialogOtpConfirmDto.getViewerId(), mobileNo,
							dialogOtpConfirmDto.getDay());
					message = "SUBSCRIBED";

					if (ideabizRepository.save(ideabizEntity) != null) {
						String resp = processIdeabizPayment(dialogOtpConfirmDto.getServerRef(),
								dialogOtpConfirmDto.getViewerId(), dialogOtpConfirmDto.getDay(), mobileNo, amount);
						if (resp.equalsIgnoreCase("Success")) {

						}

					} else {
						message = "SUBSCRIBED SAVE ERROR";
						paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
								dialogOtpConfirmDto.getViewerId(), mobileNo, dialogOtpDto.getResult());

						System.out.println("ALREADY SUBSCRIBED");
					}

				} else {

					message = "ALREADY SUBSCRIBED";
					paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
							dialogOtpConfirmDto.getViewerId(), mobileNo, dialogOtpDto.getResult());

					if (processUnsubscriptionIdeabiz(accessToken, dialogOtpConfirmDto.getViewerId(), mobileNo, false)) {
						IdeabizEntity ideabizEntity = getIdeabizEntity(dialogOtpConfirmDto.getViewerId(), mobileNo,
								dialogOtpConfirmDto.getDay());
						message = "SUBSCRIBED";
						if (ideabizRepository.save(ideabizEntity) != null) {
							String resp = processIdeabizPayment(dialogOtpConfirmDto.getServerRef(),
									dialogOtpConfirmDto.getViewerId(), dialogOtpConfirmDto.getDay(), mobileNo, amount);
							if (resp.equalsIgnoreCase("Success")) {
							}
						} else {
							message = "SUBSCRIBED SAVE ERROR";
							paymentLogService.createPaymentLog("Dialog", "-",
									dialogOtpDto.getTransactionOperationStatus(), dialogOtpConfirmDto.getViewerId(),
									mobileNo, dialogOtpDto.getResult());
							System.out.println("ALREADY SUBSCRIBED");
						}
					}

				}

			} else {
				paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getMessage(),
						dialogOtpConfirmDto.getViewerId(), dialogOtpDto.getMsisdn(), dialogOtpDto.getResult());
			}

			DialogPaymentConfirmDto confirmDto = new DialogPaymentConfirmDto();
			confirmDto.setStatusCode(dialogOtpDto.getStatusCode());
			confirmDto.setServerRef(dialogOtpDto.getServerRef());
			confirmDto.setStatus(dialogOtpDto.getStatus());
			confirmDto.setServiceId(dialogOtpDto.getServiceId());
			confirmDto.setAccessCode(accessToken);
			confirmDto.setMsisdn(dialogOtpDto.getMsisdn());
			confirmDto.setMessage(message);

			return confirmDto;

		}

		return null;
	}

	@Override
	public boolean processUnsubscriptionIdeabiz(String accessToken, Integer viewerId, String mobileNo,
			boolean unsubscribeFromDialog) throws Exception {
		
		if(accessToken == null) {
			accessToken = dialogClient.createAccessToken();
		}
		
		IdeabizEntity ideabizEntity = ideabizRepository.findOneByViwerIdAndSubscribe(viewerId, AppConstant.ACTIVE);
		if (ideabizEntity != null) {
			ideabizEntity.setSubscribe(AppConstant.INACTIVE);

			if (unsubscribeFromDialog) {
				dialogClient.unsubscribe(accessToken, viewerId, ideabizEntity.getSubscribedDays(), mobileNo);
			}
			if (ideabizRepository.save(ideabizEntity) != null) {
				if (viewerUnsubscriptionService.unubscribe(mobileNo, viewerId, "UNSUBCRIBE", "Dialog")) {
					return true;
				}
			}
		} else {
			return true;
		}

		return false;
	}

	@Override
	public String processIdeabizPayment(String serverRef, Integer viewerId, Integer day, String mobileNo, Double amount)
			throws Exception {

		String paid = paymentConfirm(serverRef, mobileNo, amount, day, viewerId);
		System.out.println(paid);

		if (paid.equals("Success")) {
			List<Date> dates = appUtility.getDatesBetweenUsingJava7(day);
			InvoiceEntity invoiceEntity = invoiceService.createInvoice(serverRef, viewerId, day, amount, mobileNo,
					AppConstant.ACTIVE, dates);
			Integer packageId = -1;
			if (day == 7) {
				packageId = 106;
			} else if (day == 1) {
				packageId = 81;
			}
			if (packageId > 0) {
				if (viewerPolicyService
						.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(viewerId, packageId))
						.equalsIgnoreCase("success")) {
					if (updateIdeabizPolicyExpDate(viewerId, dates.get(dates.size() - 1),
							invoiceEntity.getCreatedDate()) != null) {
						if (viewerUnsubscriptionService.save(mobileNo, viewerId, "SUBCRIBE", "Dialog")) {
							return "Success";
						} else {
							return "Unsubscription save error";
						}
					} else {
						return "Policy Expire Update Error";
					}
				} else {
					return "Viewer Policy Update Error";
				}
			} else {
				return "Unidentified Package";
			}

		} else {
			return "Payment Confirmation Error";
		}
	}

	@Override
	public IdeabizEntity updateIdeabizPolicyExpDate(Integer viewerId, Date valiedDate, Date createDate)
			throws Exception {
		IdeabizEntity ideabizEntity = ideabizRepository.findOneByViwerIdAndSubscribe(viewerId, AppConstant.ACTIVE);
		ideabizEntity.setLastPolicyUpdatedAt(createDate);
		ideabizEntity.setPolicyExpireAt(appUtility.getbeforeDay(1, valiedDate));
		return ideabizRepository.save(ideabizEntity);
	}

	@Override
	public String paymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			Integer viewerId) throws Exception {
		String paid = dialogClient.dialogPaymentConfirm(serverRef, mobileNo, amount, subscribedDays, viewerId);
		return paid;
	}

	@Override
	public IdeabizEntity getIdeabizEntity(Integer viewerId, String mobileNo, Integer day) throws Exception {
		IdeabizEntity ideabizEntity = new IdeabizEntity();
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);

		ideabizEntity.setMobile(mobileNo);
		ideabizEntity.setViwerId(viewerId);
		ideabizEntity.setCreatedDate(ts);
		ideabizEntity.setSubscribe(1);
		ideabizEntity.setSubscribedDays(day);
		return ideabizEntity;
	}

}
