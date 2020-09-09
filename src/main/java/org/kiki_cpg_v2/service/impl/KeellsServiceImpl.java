/**
 * @DaSep 9, 2020 @KeellsServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.CustomerEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.repository.CustomerRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.service.CustomerService;
import org.kiki_cpg_v2.service.KeellsService;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class KeellsServiceImpl implements KeellsService{
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Override
	public PaymentRefDto beginTransaction(TransactionBeginDto transactionBeginDto) throws Exception {
		PaymentRefDto paymentRefDto = subscriptionService.getPaymentRefDto(transactionBeginDto, -1, -0.1);
		SubscriptionEntity subscriptionEntity = subscriptionService.getSubsctiptionEntity(transactionBeginDto, paymentRefDto, AppConstant.KEELLS);
		
		if(subscriptionRepository.save(subscriptionEntity) != null) {
			CustomerEntity customerEntity = customerService.getCustomerEntity(2, subscriptionEntity.getMobile(), subscriptionEntity.getViewerId());
			if (customerRepository.save(customerEntity)!= null) {
				return paymentRefDto;
			} else {
				throw new InternalError("CustomerEntity not Saved");
			}
		} else {
			throw new InternalError("SubscriptionEntity not Saved");
		}
		
	}

}
