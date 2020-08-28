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
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentDetailService;
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
	private PackageConfigService packageConfigService;
	
	@Autowired
	private PaymentDetailService paymentDetailService;

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
								dialogOtpConfirmDto.getViewerId(), dialogOtpConfirmDto.getDay(), mobileNo, amount, true,
								false, -1);
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
									dialogOtpConfirmDto.getViewerId(), dialogOtpConfirmDto.getDay(), mobileNo, amount,
									true, false, -1);
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

		if (accessToken == null) {
			accessToken = dialogClient.createAccessToken();
		}

		IdeabizEntity ideabizEntity = ideabizRepository.findOneByViwerIdAndSubscribe(viewerId, AppConstant.ACTIVE);
		if (ideabizEntity != null) {
			ideabizEntity.setSubscribe(AppConstant.INACTIVE);

			if (unsubscribeFromDialog) {
				dialogClient.unsubscribe(accessToken, viewerId, ideabizEntity.getSubscribedDays(), mobileNo);
			}
			if (ideabizRepository.save(ideabizEntity) != null) {
				if (viewerUnsubscriptionService.unubscribe(mobileNo, viewerId, "UNSUBSCRIBE", "Dialog")) {
					return true;
				}
			}
		} else {
			return true;
		}

		return false;
	}

	@Override
	public String processIdeabizPayment(String serverRef, Integer viewerId, Integer day, String mobileNo, Double amount,
			boolean unsubscrideEntityUpdate, boolean isUpdateCronViewer, Integer cronId) throws Exception {
		List<Date> dates = appUtility.getDatesBetweenUsingJava7(day);

//		InvoiceEntity invoiceEntity = invoiceService.createInvoice(AppConstant.IDEABIZ, viewerId, day, amount, mobileNo,
//				AppConstant.INACTIVE, dates);
		
		InvoiceEntity invoiceEntity = invoiceService.createInvoice(AppConstant.IDEABIZ, viewerId, day, amount, mobileNo,
				AppConstant.INACTIVE, null);

		if (serverRef == null) {
			serverRef = invoiceEntity.getId().toString();
		}

		String paid = paymentConfirm(serverRef, mobileNo, amount, day, viewerId, isUpdateCronViewer, cronId);
		System.out.println(paid);

		if (paid.equals("Success")) {

			invoiceEntity.setSuccess(AppConstant.ACTIVE);
			if (invoiceService.update(invoiceEntity)) {
				
				

				Integer packageId = packageConfigService.getPackageId(day, AppConstant.DIALOG);
				/*if (day == 7) {
					packageId = 106;
				} else if (day == 1) {
					packageId = 81;
				} else if (day == 30) {
					packageId = 110;
				} else if (day == 90) {
					packageId = 111;
				}
*/
				if (packageId > 0) {
					if (viewerPolicyService
							.updateViewerPolicy(
									viewerPolicyService.getViewerPolicyUpdateRequestDto(viewerId, packageId))
							.equalsIgnoreCase("success")) {
						
						IdeabizEntity ideabizEntity = updateIdeabizPolicyExpDate(viewerId, dates.get(dates.size() - 1),
								invoiceEntity.getCreatedDate());
						if (ideabizEntity != null) {
							
							if(paymentDetailService.save(amount, day, ideabizEntity.getPolicyExpireAt(), invoiceEntity.getId(), AppConstant.DIALOG)!= null) {
								if (viewerUnsubscriptionService.save(mobileNo, viewerId, "SUBSCRIBE", "Dialog",
										unsubscrideEntityUpdate)) {
									return "Success";
								} else {
									return "Unsubscription save error";
								}
							} else {
								return "Payment Details save error";
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
				return "Invoice Not Updated";
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
			Integer viewerId, boolean isUpdateCronViewer, Integer cronId) throws Exception {
		String paid = dialogClient.dialogPaymentConfirm(serverRef, mobileNo, amount, subscribedDays, viewerId,
				isUpdateCronViewer, cronId);
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
