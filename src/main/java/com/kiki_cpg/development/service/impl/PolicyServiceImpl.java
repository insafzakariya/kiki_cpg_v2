package com.kiki_cpg.development.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.repository.PolicyRepository;
import com.kiki_cpg.development.service.PolicyService;
@Service
public class PolicyServiceImpl implements PolicyService {
	
	@Autowired
	PolicyRepository policyRepo;

	@Override
	public List<Policies> getPoliciesByPackageIDAndValidDate(int newPackageID) {
		// TODO Auto-generated method stub
		List<Policies>policiesList=policyRepo.getPoliciesByPackageIDAndValidDate(newPackageID);
		if(policiesList!=null) {
			return policiesList;
		}
		return null;
	}

//	@Override
//	public List<Policies> getPoliciesByPackageID(int newPackageID) {
//		// TODO Auto-generated method stub
//		List<Policies>policiesList=policyRepo.getPoliciesByPackageID(newPackageID);
//		return null;
//	}

	
}
