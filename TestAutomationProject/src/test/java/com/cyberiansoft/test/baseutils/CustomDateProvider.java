package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.enums.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomDateProvider {

    private static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalDate getCurrentDateLocalized() {
        return LocalDate.now(getZoneId());
    }

    private static ZoneId getZoneId() {
        return ZoneId.of(DateUtils.ZONE_ID.getDate());
    }

    private static LocalDate getCurrentDatePlusDays(int days) {
        return getCurrentDate().plusDays(days);
    }

    private static LocalDate getCurrentDateMinusDays(int days) {
        return getCurrentDate().minusDays(days);
    }

    private static String getFormattedLocalizedDate(DateUtils format) {
        return getFormattedLocalizedDate(getCurrentDate(), format);
    }

    private static String getFormattedLocalizedDate(LocalDate date, DateUtils format) {
        return date.format(DateTimeFormatter.ofPattern(format.getDate(), Locale.US));
    }

    private static String getFormattedLocalizedDateTime(LocalDateTime dateTime, DateUtils format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format.getDate(), Locale.US));
    }

    private static TemporalField getUSField() {
        return WeekFields.of(Locale.US).dayOfWeek();
    }

    public static String getTomorrowLocalizedDateFormattedShort() {
        return getFormattedLocalizedDate(getCurrentDatePlusDays(1), DateUtils.SHORT_DATE_FORMAT);
    }

    public static String getPreviousLocalizedDateFormattedShort() {
        return getFormattedLocalizedDate(getCurrentDateMinusDays(1), DateUtils.SHORT_DATE_FORMAT);
    }

    public static String getDayAfterTomorrowLocalizedDateFormattedShort() {
        return getFormattedLocalizedDate(getCurrentDatePlusDays(2), DateUtils.SHORT_DATE_FORMAT);
    }

    public static String getTomorrowLocalizedDateFormattedTheShortest() {
        return getFormattedLocalizedDate(getCurrentDatePlusDays(1), DateUtils.THE_SHORTEST_DATE_FORMAT);
    }

    public static String getCurrentDateInShortFormat() {
        return getFormattedLocalizedDate(DateUtils.SHORT_DATE_FORMAT);
    }

    public static String getCurrentDateInFullFormat(boolean... isLocalized) {
        if (isLocalized[0]) {
            return getFormattedLocalizedDate(DateUtils.FULL_DATE_FORMAT);
        } else {
            return getCurrentDate().format(DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getDate()));
        }
    }

    public static String getLocalizedCurrentDateInTheShortestFormat() {
        return getFormattedLocalizedDate(DateUtils.THE_SHORTEST_DATE_FORMAT);
    }

    public static String getLocalizedCurrentDateFormattedDetailed() {
        return getFormattedLocalizedDate(DateUtils.DETAILED_FULL_DATE_FORMAT);
    }

    public static String getTomorrowLocalizedDateFormattedDetailed() {
        return getFormattedLocalizedDate(getCurrentDatePlusDays(1), DateUtils.DETAILED_FULL_DATE_FORMAT);
    }

    public static String getTomorrowDateFormattedFull() {
        return getFormattedLocalizedDate(getCurrentDatePlusDays(1), DateUtils.FULL_DATE_FORMAT);
    }

    public static String getTheShortestCurrentDateWithTimeZone() {
        return getFormattedLocalizedDateTime(LocalDateTime.now(getZoneId()), DateUtils.THE_SHORTEST_DATE_FORMAT);
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public static LocalDate getWeekStartDate() {
        return getCurrentDateLocalized().with(getUSField(), 1);
    }

    public static LocalDate getLastWeekStartDate() {
        return getCurrentDateLocalized().minusWeeks(2).with(getUSField(), 1);
    }

    public static LocalDate getLastWeekEndDate(LocalDate lastWeekStart) {
        return lastWeekStart.plusWeeks(1).with(getUSField(), 7);
    }

    public static LocalDate getLastWeekEndDate() {
        return getCurrentDateLocalized().plusWeeks(1).with(getUSField(), 7);
    }

    public static LocalDate getMonthStartDate() {
        return getCurrentDateLocalized().withDayOfMonth(1);
    }

    public static LocalDate getLastMonthStartDate() {
        return getCurrentDateLocalized().minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate getLastThirtyDaysStartDate() {
        return getCurrentDateLocalized().minusDays(31);
    }

    public static LocalDate getWeekToDateStartDate() {
        return getCurrentDateLocalized().minusDays(8);
    }

    public static LocalDate getYearToDateStartDate() {
        return getCurrentDateLocalized().minusMonths(12).withDayOfYear(1);
    }

    public static LocalDate getLastMonthEndDate() {
        LocalDate localDate = getCurrentDateLocalized().minusMonths(1);
        return localDate.withDayOfMonth(localDate.lengthOfMonth());
    }

    public static LocalDate getYearStartDate() {
        return getCurrentDateLocalized().withDayOfYear(1);
    }

    public static LocalDate getLastYearStartDate() {
        return getCurrentDateLocalized().minusYears(1).withDayOfYear(1);
    }

    public static LocalDate getThreeMonthsBeforeCurrentDate() {
        return getCurrentDateLocalized().minusMonths(3).minusDays(1);
    }

    public static LocalDate getLastYearEndDate() {
        LocalDate localDate = getCurrentDateLocalized().minusYears(1);
        return localDate.withDayOfYear(localDate.lengthOfYear());
    }
}