package com.kiki_cpg.development.repository.Custom;

import java.util.List;

import com.kiki_cpg.development.entity.PaymentPolicies;


public interface PaymentRepositoryCustom {

	  public List<PaymentPolicies> getPaymentOptions(Integer packageId) throws Exception;
	  
}
