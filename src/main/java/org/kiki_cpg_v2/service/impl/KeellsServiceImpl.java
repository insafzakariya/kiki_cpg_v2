/**
 * @DaSep 9, 2020 @KeellsServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.PaymentResponseDto;
import org.kiki_cpg_v2.dto.request.PaymentRequestDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.CustomerEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.CustomerRepository;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.CustomerService;
import org.kiki_cpg_v2.service.KeellsService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class KeellsServiceImpl implements KeellsService {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;

	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;

	@Autowired
	private PaymentDetailService paymentDetailService;

	@Autowired
	private PackageConfigService packageConfigService;

	@Autowired
	private ViewerPolicyService viewerPolicyService;
	
	@Autowired
	private ViewerService viewerService;

	@Autowired
	private AppUtility appUtility;

	@Override
	public PaymentRefDto beginTransaction(TransactionBeginDto transactionBeginDto) throws Exception {
		PaymentRefDto paymentRefDto = subscriptionService.getPaymentRefDto(transactionBeginDto, -1, -0.1);
		SubscriptionEntity subscriptionEntity = subscriptionService.getSubsctiptionEntity(transactionBeginDto,
				paymentRefDto, AppConstant.KEELLS);
		viewerService.updateViewerMobileNumber(transactionBeginDto.getMobileNo(), transactionBeginDto.getViewerId());

		if (subscriptionRepository.save(subscriptionEntity) != null) {
			CustomerEntity customerEntity = customerService.getCustomerEntity(2, subscriptionEntity.getMobile(),
					subscriptionEntity.getViewerId());
			if (customerRepository.save(customerEntity) != null) {
				return paymentRefDto;
			} else {
				throw new InternalError("CustomerEntity not Saved");
			}
		} else {
			throw new InternalError("SubscriptionEntity not Saved");
		}

	}

	@Override
	public ResponseEntity<Object> pay(PaymentRequestDto paymentRequestDto) throws Exception {
		PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
		paymentResponseDto.setStatus("Fail");
		ViewerEntity viewerEntity = viewerRepository.findById(paymentRequestDto.getViewerId()).get();
		if (viewerEntity != null) {
			paymentLogService.createPaymentLog(AppConstant.KEELLS, "", "", viewerEntity.getId(),
					viewerEntity.getMobileNumber(), paymentRequestDto.toString());
			SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribe(
					viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE);
			PaymentMethodPlanEntity paymentMethodPlanEntity = paymentMethodPlanRepository
					.findById(subscriptionEntity.getPaymentPlan()).get();
			if (subscriptionEntity != null) {
				SubscriptionInvoiceEntity invoiceEntity = subscriptionService.getSubscriptionInvoiceEntity(
						viewerEntity.getMobileNumber(), "", subscriptionEntity, AppConstant.KEELLS);
				double subscribeDays = paymentRequestDto.getAmount() / paymentMethodPlanEntity.getValue();
				subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay((int) subscribeDays, new Date()));
				subscriptionEntity.setUpdateDate(new Date());
				if (subscriptionRepository.save(subscriptionEntity) != null) {
					invoiceEntity = subscriptionInvoiceRepository.save(invoiceEntity);
					if (invoiceEntity != null) {
						if (paymentDetailService.save(subscriptionEntity.getAmount(),
								subscriptionEntity.getSubscribedDays(), subscriptionEntity.getPolicyExpDate(),
								invoiceEntity.getId(), AppConstant.HUTCH) != null) {
							Integer packageId = packageConfigService
									.getPackageId(subscriptionEntity.getSubscribedDays(), AppConstant.KEELLS);
							if (packageId > 0) {
								if (viewerPolicyService
										.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(
												subscriptionEntity.getViewerId(), packageId), (int)subscribeDays)
										.equalsIgnoreCase("success")) {
									
									
									paymentResponseDto.setMessage("Success");
									paymentResponseDto.setReferance(invoiceEntity.getTransactionNo());
									paymentResponseDto.setStatus("Success");
									paymentResponseDto.setType(AppConstant.KEELLS);
									paymentResponseDto.setViewerId(viewerEntity.getId());
									
									
								} else {
									paymentResponseDto.setMessage("Policy Not Updated");
								}
							} else {
								paymentResponseDto.setMessage("Package Not Found");
							}
						} else {
							paymentResponseDto.setMessage("Payment Details save error");
							
						}
					} else {
						paymentResponseDto.setMessage("subscriptionInvoice save error");
					}
				} else {
					paymentResponseDto.setMessage("subscription save error");
				}
			} 

		} else {
			paymentResponseDto.setMessage("viewerEntity not found");
		}

		ResponseEntity<Object> response = new ResponseEntity<Object>(paymentResponseDto, HttpStatus.OK);
		return response;
	}
}
