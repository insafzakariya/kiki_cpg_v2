/**
 * @DaSep 9, 2020 @SubscriptionServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

/**
 * @author Anjana Thrishakya
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService{
	
	@Autowired
	private AppUtility appUtility;

	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	
	@Override
	public SubscriptionEntity generateSubsctiptionEntity(String mobileNo, Integer viewerId, PaymantPlanDto paymentPlanDto,  String type) throws Exception{
		SubscriptionEntity entity = new SubscriptionEntity();
		Integer seauenceId = generateSequenceId(viewerId, type);
		
		entity.setAmount(paymentPlanDto.getAmount());
		entity.setCreateDate(new Date());
		entity.setMobile("+94" + appUtility.getNineDigitMobileNumber(mobileNo));
		entity.setPaymentPlan(paymentPlanDto.getId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setSubscribe(AppConstant.ACTIVE);
		entity.setSubscribedDays(paymentPlanDto.getDay());
		entity.setType(type);
		entity.setViewerId(viewerId);
		entity.setSeqNo(seauenceId);
		return entity;
	}
	
	// Not Use after angular release
	@Override
	public SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto transactionBeginDto,
			PaymentRefDto paymentRefDto, String type) throws Exception{
		SubscriptionEntity entity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribeAndType(transactionBeginDto.getViewerId(), AppConstant.ACTIVE, AppConstant.ACTIVE, type);
		Integer seauenceId = generateSequenceId(transactionBeginDto.getViewerId(), type);
		if(entity == null) {
			entity = new SubscriptionEntity();
		}
		
		entity.setAmount(paymentRefDto.getAmount());
		entity.setCreateDate(new Date());
		entity.setMobile("+94" + appUtility.getNineDigitMobileNumber(transactionBeginDto.getMobileNo()));
		entity.setPaymentPlan(transactionBeginDto.getPlanId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setSubscribe(AppConstant.ACTIVE);
		entity.setSubscribedDays(paymentRefDto.getDays());
		entity.setType(type);
		entity.setViewerId(transactionBeginDto.getViewerId());
		entity.setSeqNo(seauenceId);
		return entity;
	}

	@Override
	public Integer generateSequenceId(Integer viewerId, String type) throws Exception{
		List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findByViewerIdAndType(viewerId, type);
		if(subscriptionEntities== null || subscriptionEntities.isEmpty()) {
			return 1;
		} else {
			return subscriptionEntities.size()+1;
		}
	}

	@Override
	public PaymentRefDto getPaymentRefDto(TransactionBeginDto transactionBeginDto, int days, double value) {
		PaymentRefDto dto = new PaymentRefDto();
		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(transactionBeginDto.getPlanId()).get();
		if (days < 0 && value < 0.0) {
			
			days = entity.getDays();
			value = entity.getValue();
		}

		String ref = transactionBeginDto.getViewerId() + new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());
		ref.replace("+", "");
		dto.setReferanceNo(ref);
		dto.setTransactionUUID(UUID.randomUUID().toString());
		dto.setAmount(value);
		dto.setDays(days);
		dto.setServiceCode(entity.getServiceCode());

		String frequency = AppUtility.getHnbFrequency(days);
		dto.setFrequency(frequency);

		dto.setDate(new SimpleDateFormat("yyyyMMdd").format(appUtility.getbeforeDay(30, new Date())));

		return dto;
	}

	@Override
	public SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(String transactionType,
			SubscriptionEntity subscriptionEntity) throws Exception {

		String transactionNo = new SimpleDateFormat("yyMMdd").format(new Date()) + RandomString.make(6);

		SubscriptionInvoiceEntity invoiceEntity = new SubscriptionInvoiceEntity();
		invoiceEntity.setAmount(subscriptionEntity.getAmount());
		invoiceEntity.setCreatedDate(new Date());
		invoiceEntity.setExpireDate(appUtility.getbeforeDay(subscriptionEntity.getSubscribedDays(), new Date()));
		invoiceEntity.setDecision(AppConstant.ACCEPT);
		invoiceEntity.setMobile("+94" + appUtility.getNineDigitMobileNumber(subscriptionEntity.getMobile()));
		invoiceEntity.setReferanceNo(UUID.randomUUID().toString());
		invoiceEntity.setStatus(AppConstant.ACTIVE);
		invoiceEntity.setSubscribedDays(subscriptionEntity.getSubscribedDays());
		invoiceEntity.setSuccess(AppConstant.ACTIVE);
		invoiceEntity.setTransactionNo(transactionNo);
		invoiceEntity.setTransactionType(transactionType);
		invoiceEntity.setViewerId(subscriptionEntity.getViewerId());
		invoiceEntity.setType(subscriptionEntity.getType());
		return invoiceEntity;
	}

	@Override
	public boolean inavtive(Integer id, String type) throws Exception {
		List<SubscriptionEntity> entities = subscriptionRepository.findByViewerIdAndStatusAndSubscribeAndType(id, AppConstant.ACTIVE, AppConstant.ACTIVE, type);
		
		entities.forEach(e-> {
			e.setSubscribe(AppConstant.INACTIVE);
			e.setUpdateDate(new Date());
		});
		
		if(subscriptionRepository.saveAll(entities) != null) {
			return true;
		}
		
		return false;
		
	}

	@Override
	public boolean unsubscribeAllByMobileAndType(String mobileNo, String type) throws Exception {
		List<SubscriptionEntity> entities = subscriptionRepository.findByMobileContainingAndTypeAndStatus(mobileNo, type, AppConstant.ACTIVE);
		if(entities != null && !entities.isEmpty()) {
			List<Integer> viewerIds = new ArrayList<Integer>();
			entities.forEach(e-> {
				viewerIds.add(e.getViewerId());
			});
			LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(viewerIds);
			ArrayList<Integer> viewerIdsDistint = new ArrayList<>(hashSet);
			for (Integer viewer : viewerIdsDistint) {
				SubscriptionEntity subscriptionEntity = null;
				for (SubscriptionEntity entity : entities) {
					if(viewer == entity.getViewerId()) {
						if(subscriptionEntity != null) {
							if(subscriptionEntity.getId() < entity.getId()) {
								subscriptionEntity = entity;
							}
						} else {
							subscriptionEntity = entity;
						}
					}
				}

				if(subscriptionEntity != null) {
					SubscriptionEntity entity = new SubscriptionEntity();
					try {
						Integer seauenceId = generateSequenceId(viewer, type);
						entity.setAmount(subscriptionEntity.getAmount());
						entity.setCreateDate(new Date());
						entity.setMobile(subscriptionEntity.getMobile());
						entity.setPaymentPlan(subscriptionEntity.getId());
						entity.setStatus(AppConstant.ACTIVE);
						entity.setSubscribe(AppConstant.INACTIVE);
						entity.setSubscribedDays(subscriptionEntity.getSubscribedDays());
						entity.setType(subscriptionEntity.getType());
						entity.setViewerId(viewer);
						entity.setSeqNo(seauenceId);
						
						subscriptionRepository.save(entity);
						
						return true; 
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return false;
	}
}
