package com.kiki_cpg.development.service.calculation;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateCalculate {

	public static List<Date> getDatesBetweenUsingJava7(
			   Integer days) {
		 		Date startDate =new Date();

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
	public static Date getbeforeDay(
			   Integer days,Date from_date) {
		 		Date startDate =from_date;

				Date endDate = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(startDate); 
				c.add(Calendar.DATE, days);
				endDate = c.getTime();
		
			   
			    return endDate;
			}
}