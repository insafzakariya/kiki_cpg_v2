package org.kiki_cpg_v2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	/**
	 * @param number
	 * @return
	 */
	public boolean getIsMobileNumber(String number) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @param integer
	 * @return
	 */
	public static String getHnbFrequency(Integer day) {
		String frequency = "";
		switch (day) {
		case 30:
			frequency = "monthly";
			break;
		case 180:
			frequency = "semi-annually";
			break;
		case 365:
			frequency = "annually";
			break;

		default:
			break;
		}

		return frequency;
	}

	/**
	 * @param number
	 * @return
	 */
	public boolean getIsHutchNumber(String number) {
		if (number == null || number.isEmpty()) {
			return false;
		}
		number = "0" + getNineDigitMobileNumber(number);

		String serviceProviderCode = number.substring(0, 3);
		if (serviceProviderCode.equals("078") || serviceProviderCode.equals("072")) {
			return true;
		}
		return false;
	}

	public String getHutchPackageFrequance(Integer date) {
		String text = "";
		switch (date) {
		case 1:
			text = "day";
			break;
		case 7:
			text = "week";
			break;
		case 30:
			text = "month";
			break;
		case 90:
			text = "3 month";
			break;

		default:
			break;
		}
		
		return text;
	}
	

	/**
	 * @return
	 * @throws ParseException 
	 */
	public Date getLastMinitue() throws ParseException {
		Date d = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = simpleDateFormat.format(d) + " 23:59:00";
		Date expireDateTill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
		
		return expireDateTill;
	}

	/**
	 * @param number
	 * @return
	 */
	public boolean getIsAirtelNumber(String number) {
		if (number == null || number.isEmpty()) {
			return false;
		}
		number = "0" + getNineDigitMobileNumber(number);

		String serviceProviderCode = number.substring(0, 3);
		if (serviceProviderCode.equals("075")) {
			return true;
		}
		return false;
	}
}
