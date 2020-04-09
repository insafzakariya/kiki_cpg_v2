package com.kiki_cpg.development.service;

import java.util.List;

import com.kiki_cpg.development.entity.Packages;

public interface PackagesService {

	List<Packages> getPackageById(Integer packageID);

}
