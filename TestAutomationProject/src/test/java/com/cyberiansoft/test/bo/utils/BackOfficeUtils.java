package com.cyberiansoft.test.bo.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BackOfficeUtils {

	public static String MONEY_SYMBOL =  "$";

	public static String getFullDateFormat() {
		return "MM/dd/yyyy";
	}
	
	public static String getTheShortestDateFormat() {
		return "M/d/yyyy";
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
	
	public static String getShortCurrentTimeWithTimeZone() {
		LocalDateTime localDateAndTime = LocalDateTime.now(ZoneOffset.of("-08:00"));
		DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/uuuu");
	    
	    
	    return localDateAndTime.format(format);
	}
	
	private static Calendar getCalendarForNow() {
		Date today = new Date();                   
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(today);
	    return calendar;
	}
	
	public static LocalDate getWeekStartDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		return localDate.with(fieldUS, 1);
	}
	
	public static LocalDate getLastWeekStartDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();

//		localDate = localDate.minusWeeks(1);
		localDate = localDate.minusWeeks(2);
		return localDate.with(fieldUS, 1);
	}
	
//	public static LocalDate getLastWeekEndDate() {
//		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
//		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
//
//		localDate = localDate.minusWeeks(1);
//		return localDate.with(fieldUS, 7);
//	}

    public static LocalDate getLastWeekEndDate(LocalDate lastweekstart) {
//		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();

		lastweekstart = lastweekstart.plusWeeks(1);
		return lastweekstart.with(fieldUS, 7);
	}

    public static LocalDate getLastWeekEndDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();

		localDate = localDate.plusWeeks(1);
		return localDate.with(fieldUS, 7);
	}
	
	public static LocalDate getMonthStartDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		return localDate.withDayOfMonth(1);
		
	}
	
	public static LocalDate getLastMonthStartDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		localDate = localDate.minusMonths(1);
		return localDate.withDayOfMonth(1);
		
	}
	
	public static LocalDate getLastMonthEndDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		localDate = localDate.minusMonths(1);
		return localDate.withDayOfMonth(localDate.lengthOfMonth());
	}
	
	public static LocalDate getYearStartDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		return localDate.withDayOfYear(1);
	}
	
	public static LocalDate getLastYearStartDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		localDate = localDate.minusYears(1);
		return localDate.withDayOfYear(1);
	}
	
	public static LocalDate getLastYearEndDate() {
		LocalDate localDate = LocalDate.now(ZoneOffset.of("-08:00"));
		localDate = localDate.minusYears(1);
		return localDate.withDayOfYear(localDate.lengthOfYear());
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

	public static float getServicePriceValue(String servicePriceString) {
		if (servicePriceString.contains(MONEY_SYMBOL))
			servicePriceString = servicePriceString.replace (MONEY_SYMBOL, "").trim();
		else
			servicePriceString = servicePriceString.trim();
		return Float.valueOf(servicePriceString).floatValue();
	}

	public static String getFormattedServicePriceValue(float servicePrice) {
		String servicePriceFormatted= MONEY_SYMBOL + String.format("%.2f", servicePrice);
		return servicePriceFormatted;
	}

}
