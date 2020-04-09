package com.kiki_cpg.development.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.repository.PackageRepository;
import com.kiki_cpg.development.service.PackagesService;

@Service
public class PackagesServiceImpl implements PackagesService {
	@Autowired
	PackageRepository packageRepo;

	@Override
	public List<Packages> getPackageById(Integer packageID) {
		// TODO Auto-generated method stub
		List<Packages>packagers=packageRepo.getPackageById(packageID);
		if(packagers!=null) {
			return packagers;
		}
		return null;
	}

}
