package com.kiki_cpg.development.service;

import java.util.List;

import com.kiki_cpg.development.entity.Policies;

public interface PolicyService {

	//List<Policies> getPoliciesByPackageID(int newPackageID);

	List<Policies> getPoliciesByPackageIDAndValidDate(int newPackageID);

}
