package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOCalendarWidgetDialog;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCalendarWidgetDialogValidations;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VNextBOCalendarWidgetDialogInteractions {

    public static void selectFromDate(String date) {
        if (VNextBOCalendarWidgetDialogValidations.isCalendarOpened(new VNextBOCalendarWidgetDialog().getFromDateCalendarWidget())) {
            WebElement fromDate = new VNextBOCalendarWidgetDialog().getDate(date).get(0);
            Utils.clickElement(fromDate);
            WaitUtilsWebDriver.waitForAttributeToContainIgnoringException(fromDate, "aria-selected", "true", 5);
        } else {
            System.out.println("In else From Date");
            VNextBOROAdvancedSearchDialogInteractions.clickFromDateButton();
            selectFromDate(date);
        }
    }

    public static void selectToDate(String date) {
        if (VNextBOCalendarWidgetDialogValidations.isCalendarOpened(new VNextBOCalendarWidgetDialog().getToDateCalendarWidget())) {
            WebElement toDate = new VNextBOCalendarWidgetDialog().getDate(date).get(1);
            Utils.clickElement(toDate);
            WaitUtilsWebDriver.waitForAttributeToContainIgnoringException(toDate, "aria-selected", "true", 5);
        } else {
            System.out.println("In else To Date");
            VNextBOROAdvancedSearchDialogInteractions.clickToDateButton();
            selectToDate(date);
        }
    }

    public static String getMonthReplace(int monthValue, int prevMonthValue, LocalDateTime before) {
        DateTimeFormatter dataValueFormat = DateTimeFormatter.ofPattern("yyyy/M/d");
        return before
                .format(dataValueFormat)
                .replace("/" + String.valueOf(monthValue) + "/", "/" + String.valueOf(prevMonthValue) + "/");
    }
}
