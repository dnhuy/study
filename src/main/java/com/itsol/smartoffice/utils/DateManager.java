package com.itsol.smartoffice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class DateManager {
	public int countDayofMonth(String Date) {
		Calendar calendar = Calendar.getInstance();
		String[] arr = Date.split("-");
		int month = Integer.parseInt(arr[0]);
		int year = Integer.parseInt(arr[1]);
		calendar.set(year,month,1);
		int numberDay = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		return numberDay;
	}
	public Date convertStringToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-yyyy-MM");
        java.util.Date date = null;
        java.sql.Date sqlDate = null;
		try {
			date = sdf.parse(dateStr);
			sqlDate = new Date(date.getTime());    
			System.out.println(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
        return sqlDate; 
	}
	public Date getCurrentDate() {
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		return date;
	}
}
