package com.cyberiansoft.test.bo.utils;

import com.cyberiansoft.test.baseutils.DataUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BackOfficeUtils {
	
	public static String getTomorrowDateFormatted() {
		return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern(DataUtils.SHORT_DATE_FORMAT.getData()));
	}

	public static String getPreviousDateFormatted() {
		return LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern(DataUtils.SHORT_DATE_FORMAT.getData()));
	}
	
	public static String getDayAfterTomorrowDateFormatted() {
		return LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern(DataUtils.SHORT_DATE_FORMAT.getData()));
	}

	public static String getShortTomorrowDateFormatted() {
		return LocalDate.now().plusDays(1).format(DateTimeFormatter
                .ofPattern(DataUtils.THE_SHORTEST_DATE_FORMAT.getData()));
	}
	
	public static String getCurrentDateFormatted() {
        return getFormattedDate(DataUtils.SHORT_DATE_FORMAT);
	}

    public static String getCurrentDate(boolean... isLocalized) {
        if (isLocalized[0]) {
            return getFormattedDate(DataUtils.FULL_DATE_FORMAT);
        } else {
            return LocalDate.now().format(DateTimeFormatter.ofPattern(DataUtils.FULL_DATE_FORMAT.getData()));
        }
    }
	
	public static String getTheShortestCurrentDateFormatted() {
        return getFormattedDate(DataUtils.THE_SHORTEST_DATE_FORMAT);
	}

	public static String getDetailedCurrentDateFormatted() {
        return getFormattedDate(DataUtils.DETAILED_FULL_DATE_FORMAT);
	}

    public static String getDetailedTomorrowDateFormatted() {
	    return LocalDate.now().plusDays(1).format(DateTimeFormatter
                .ofPattern(DataUtils.DETAILED_FULL_DATE_FORMAT.getData(), Locale.US));
    }

    public static String getTomorrowFullDateFormatted() {
	    return LocalDate.now().plusDays(1).format(DateTimeFormatter
                .ofPattern(DataUtils.FULL_DATE_FORMAT.getData(), Locale.US));
    }

    private static String getFormattedDate(DataUtils format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format.getData(), Locale.US));
    }

    public static String getShortCurrentTimeWithTimeZone() {
		LocalDateTime localDateAndTime = LocalDateTime.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DataUtils.THE_SHORTEST_DATE_FORMAT.getData());
	    return localDateAndTime.format(format);
	}
	
	private static Calendar getCalendarForNow() {
		Date today = new Date();                   
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(today);
	    return calendar;
	}
	
	public static LocalDate getWeekStartDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		return localDate.with(fieldUS, 1);
	}
	
	public static LocalDate getLastWeekStartDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();

		localDate = localDate.minusWeeks(2);
		return localDate.with(fieldUS, 1);
	}

    public static LocalDate getLastWeekEndDate(LocalDate lastweekstart) {
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();

		lastweekstart = lastweekstart.plusWeeks(1);
		return lastweekstart.with(fieldUS, 7);
	}

    public static LocalDate getLastWeekEndDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();

		localDate = localDate.plusWeeks(1);
		return localDate.with(fieldUS, 7);
	}
	
	public static LocalDate getMonthStartDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		return localDate.withDayOfMonth(1);
	}
	
	public static LocalDate getLastMonthStartDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		localDate = localDate.minusMonths(1);
		return localDate.withDayOfMonth(1);
	}
	
	public static LocalDate getLastMonthEndDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		localDate = localDate.minusMonths(1);
		return localDate.withDayOfMonth(localDate.lengthOfMonth());
	}
	
	public static LocalDate getYearStartDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		return localDate.withDayOfYear(1);
	}
	
	public static LocalDate getLastYearStartDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
		localDate = localDate.minusYears(1);
		return localDate.withDayOfYear(1);
	}
	
	public static LocalDate getLastYearEndDate() {
		LocalDate localDate = LocalDate.now(ZoneId.of(DataUtils.ZONE_ID.getData()));
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
		} catch(NumberFormatException | NullPointerException e) {
			return false;
		}
        return true;
	}
	
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch(NumberFormatException | NullPointerException e) {
			return false;
		}
        return true;
	}

	public static float getServicePriceValue(String servicePriceString) {
		if (servicePriceString.contains(DataUtils.MONEY_SYMBOL.getData()))
			servicePriceString = servicePriceString.replace (DataUtils.MONEY_SYMBOL.getData(), "").trim();
		else
			servicePriceString = servicePriceString.trim();
		return Float.valueOf(servicePriceString).floatValue();
	}

	public static float getServiceQuantityValue(String serviceQuantityString) {
		String quantityValue = serviceQuantityString.trim();
		if (quantityValue.equals("0") || quantityValue.equals("0.00"))
			quantityValue = "1.00";
		return Float.valueOf(quantityValue).floatValue();
	}

	public static String getFormattedServicePriceValue(float servicePrice) {
		return DataUtils.MONEY_SYMBOL.getData() + String.format("%,.2f", servicePrice);
	}
}
