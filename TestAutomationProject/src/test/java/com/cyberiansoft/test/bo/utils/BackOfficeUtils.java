package com.cyberiansoft.test.bo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class BackOfficeUtils {
	
	public static String getFullDateFormat() {
		return "MM/dd/yyyy";
	}
	
	public static String getTomorrowDateFormatted() {
		LocalDate date = LocalDate.now();
		date = date.plusDays(1);
		return date.format(DateTimeFormatter.ofPattern("MM/d/uuuu"));
	}
	
	public static String getDayAfterTomorrowDateFormatted() {
		LocalDate date = LocalDate.now();
		date = date.plusDays(2);
		return date.format(DateTimeFormatter.ofPattern("MM/d/uuuu"));
	}
	
	public static String getShortTomorrowDateFormatted() {
		LocalDate date = LocalDate.now();
		date = date.plusDays(1);
		return date.format(DateTimeFormatter.ofPattern("M/d/uuuu"));
	}
	
	public static String getCurrentDateFormatted() {
		LocalDate date = LocalDate.now();
		return date.format(DateTimeFormatter.ofPattern("MM/d/uuuu"));
	}
	
	public static String getShortCurrentDateFormatted() {
		LocalDate date = LocalDate.now();
		return date.format(DateTimeFormatter.ofPattern("M/d/uuuu"));
	}
	
	private static Calendar getCalendarForNow() {
		Date today = new Date();                   
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(today);
	    return calendar;
	}
	
	public static Date getWeekStartDate() {
		Calendar calendar = getCalendarForNow();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
		calendar.add(Calendar.DAY_OF_MONTH, - dayOfWeek - 1);
		return calendar.getTime();
	}
	
	public static Date getLastWeekStartDate() {
		Calendar calendar = getCalendarForNow();
		int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
		calendar.add(Calendar.DATE, -i - 7 - 1);
		return calendar.getTime();
	}
	
	public static Date getLastWeekEndDate() {
		Calendar calendar = getCalendarForNow();
		int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
		calendar.add(Calendar.DATE, -i - 7);
		calendar.add(Calendar.DATE, 7);
		return calendar.getTime();
	}
	
	public static Date getMonthStartDate() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.getTime();
	}
	
	public static Date getLastMonthStartDate() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.DATE, 0);
		return calendar.getTime();
	}
	
	public static Date getLastMonthEndDate() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	public static Date getYearStartDate() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DAY_OF_YEAR, 0);
		return calendar.getTime();
	}
	
	public static Date getLastYearStartDate() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.MONTH, 0);
		return calendar.getTime();
	}
	
	public static Date getLastYearEndDate() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}
	
	public static String getFullPriceRepresentation(String price) {
		String formedprice = "";
		final String doublepart = ".00";
		if (price.contains(".")) {
			if (isDouble(price))
				formedprice = price;
		} else if (isInteger(price)) {
			formedprice = price + doublepart;
		} else {
			formedprice = price;
		}
		return formedprice;
	}
	
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}

}
