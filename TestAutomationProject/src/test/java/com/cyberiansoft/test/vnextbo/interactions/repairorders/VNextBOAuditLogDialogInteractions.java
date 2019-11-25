package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAuditLogDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOAuditLogDialogInteractions {

    public static List<String> getAuditLogsTabsNames() {
        return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOAuditLogDialog().getAuditLogsTabsList())
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private static void clickAuditLogsTab(WebElement tab) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOAuditLogDialog().getAuditLogsTabsList());
        Utils.clickElement(tab);
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForAttributeToBe(tab.findElement(By.xpath("./..")), "class", "active");
    }

    public static void clickAuditLogsAllTab() {
        clickAuditLogsTab(new VNextBOAuditLogDialog().getAuditLogAllTab());
    }

    public static void clickAuditLogsPhasesAndDepartmentsTab() {
        clickAuditLogsTab(new VNextBOAuditLogDialog().getAuditLogPhasesAndDepartmentsTab());
    }

    public static void clickAuditLogsServicesTab() {
        clickAuditLogsTab(new VNextBOAuditLogDialog().getAuditLogServicesTab());
    }

    public static void clickAuditLogsPartsTab() {
        clickAuditLogsTab(new VNextBOAuditLogDialog().getAuditLogPartsTab());
    }

    public static String getDepartmentsAndPhasesLastRecord() {
        return Utils.getText(new VNextBOAuditLogDialog().getDepartmentsAndPhasesLastRecord());
    }

    public static String getServicesActivityTimeFirstRecord() {
        return Utils.getText(new VNextBOAuditLogDialog().getServicesActivityTimeFirstRecord());
    }

    public static String getActualLocalDateTime() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        return localDateTime.format(formatter);
    }

    public static String getActualLocalDateTimePlusMinute() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.plusMinutes(1);
        return localDateTimeVariant.format(formatter);
    }

    public static String getActualLocalDateTimePlusTwoMinutes() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.plusMinutes(2);
        return localDateTimeVariant.format(formatter);
    }

    public static String getActualLocalDateTimeMinusMinute() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.minusMinutes(1);
        return localDateTimeVariant.format(formatter);
    }

    public static String getActualLocalDateTimeMinusTwoMinutes() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.minusMinutes(2);
        return localDateTimeVariant.format(formatter);
    }
}
