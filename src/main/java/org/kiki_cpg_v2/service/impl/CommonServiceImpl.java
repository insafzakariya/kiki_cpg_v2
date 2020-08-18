package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.service.CommonService;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService{

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
		} else if (method == 7) {
			if (appUtility.getIsMobitelNumber(number) || appUtility.getIsDialogNumber(number) || appUtility.getIsMobileNumber(number)) {
				resp = "success";
			}
		}

		return resp;
	}

}
