package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MonitorSearchSteps {

    public static void selectTimeFrame(TimeFrameValues timeFrameValue) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getTimeframe().getRootElement());
        commonFilterScreen.getTimeframe().selectOption(timeFrameValue.getName());
    }

    public static void selectDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getDateFrom());
        System.out.println("++++" + dateFrom.format(DateTimeFormatter.ofPattern("MMddYYYY", Locale.US)));
        dateFrom.format(DateTimeFormatter.ofPattern("MMddYYYY", Locale.US));
        commonFilterScreen.getDateFrom().sendKeys(dateFrom.format(DateTimeFormatter.ofPattern("MMddYYYY", Locale.US)));
        commonFilterScreen.getDateTo().sendKeys(dateTo.format(DateTimeFormatter.ofPattern("MMddYYYY", Locale.US)));
    }

}
