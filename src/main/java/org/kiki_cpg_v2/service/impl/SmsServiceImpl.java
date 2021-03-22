package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.client.MobitelClient;
import org.kiki_cpg_v2.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private MobitelClient mobitelClient;

	/*@Override
	public Integer sendSms(String mobile, String message) throws Exception {
		try {
			mobitelClient.testMobitelConnection();
			mobitelClient.logInToMobitelESMS("esmsusr_1u7l ", "1ho422g");
			Integer resultCode = mobitelClient.sendSms("KiKi", mobile, message);
			if (resultCode == 200) {
				return resultCode;
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			mobitelClient.logOutFromMobitelESMS();
		}

	}*/

	public Integer sendSms(String mobile, String message) throws Exception {
		return 200;

	}
	
	@Override
	public Integer sendSms(String[] mobiles, String message) throws Exception {
		try {
			for (String mobile : mobiles) {
				sendSms(mobile, message);
			}
			return 200;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

}
