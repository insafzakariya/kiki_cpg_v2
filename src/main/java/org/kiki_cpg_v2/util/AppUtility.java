package org.kiki_cpg_v2.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AppUtility {

	public boolean getIsMobitelNumber(String number) {
		if (number == null || number.isEmpty()) {
			return false;
		}
		number = "0" + getNineDigitMobileNumber(number);
		
		String serviceProviderCode = number.substring(0, 3);
		if (serviceProviderCode.equals("070") || serviceProviderCode.equals("071")) {
			return true;
		}
		return false;
	}

	public String getNineDigitMobileNumber(String number) {
		return number.substring(number.length() - 9, number.length());
	}

	public boolean getIsDialogNumber(String number) {

		if (number == null || number.isEmpty()) {
			return false;
		}
		
		number = "+94" + getNineDigitMobileNumber(number);
		String serviceProviderCode = number.substring(0, 5);
		System.out.println(serviceProviderCode);
		if (serviceProviderCode.equals("+9477") || serviceProviderCode.equals("+9476")) {
			return true;
		}
		return false;
	}
	
	public List<Date> getDatesBetweenUsingJava7(Integer days) {
		Date startDate = new Date();

		Date endDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, days);
		endDate = c.getTime();

		List<Date> datesInRange = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);

		while (calendar.before(endCalendar)) {
			Date result = calendar.getTime();
			datesInRange.add(result);
			calendar.add(Calendar.DATE, 1);
			System.out.println(result);
		}
		return datesInRange;
	}

	public Date getbeforeDay(Integer days, Date valiedDate) {
		Date startDate = valiedDate;

		Date endDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, days);
		endDate = c.getTime();

		return endDate;
	}
}
