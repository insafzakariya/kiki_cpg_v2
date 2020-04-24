package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.service.CommonService;
import com.kiki_cpg.development.util.AppUtility;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private AppUtility appUtility;

	@Override
	public String verifyNumber(Integer method, String number) throws Exception {
		String resp = "fail";
		if (method == 4) {
			if (appUtility.getIsDialogNumber(number)) {
				 resp = "success";
			}
		} else if (method == 5) {
			if (appUtility.getIsMobitelNumber(number)) {
				resp = "success";
			}
		}

		return resp;
	}

}
