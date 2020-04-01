package com.kiki_cpg.development.util;

import org.springframework.stereotype.Component;

@Component
public class AppUtility {

	public boolean getIsMobitelNumber(String number){
		if(number == null || number.isEmpty()){
			return false;
		}
		String prefix = "0";
		number = prefix + number.substring(number.length() -9, number.length());
		String serviceProviderCode = number.substring(0,3);
		if(serviceProviderCode.equals("070") ||serviceProviderCode.equals("071")){
			return true;
		}
		return false;
	}

	public String getNineDigitMobileNumber(String number) {
		return number.substring(number.length() -9, number.length());
	}
	
}
