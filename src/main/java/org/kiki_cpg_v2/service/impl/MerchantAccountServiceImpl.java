package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.repository.MerchantAccountRepository;
import org.kiki_cpg_v2.service.MerchantAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantAccountServiceImpl implements MerchantAccountService {

	@Autowired
	MerchantAccountRepository merchantAccountRepository;

	@Override
	public MerchantAccountEntity getMerchantAccountById(Integer invoiceId) {
		return merchantAccountRepository.findById(invoiceId).get();
	}

}
