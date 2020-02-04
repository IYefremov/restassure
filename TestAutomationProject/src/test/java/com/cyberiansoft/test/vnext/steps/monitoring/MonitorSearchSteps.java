package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.time.LocalDate;

public class MonitorSearchSteps {

    public static void selectTimeFrame(TimeFrameValues timeFrameValue) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getTimeframe().getRootElement());
        commonFilterScreen.getTimeframe().selectOption(timeFrameValue.getName());
    }

    public static void selectDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.waitUntilElementIsClickable(commonFilterScreen.getDateFrom().getRootElement());
        commonFilterScreen.getDateFrom().selectDateOption(dateFrom.getMonthValue(), dateFrom.getDayOfMonth(), dateFrom.getYear());
        commonFilterScreen.getDateTo().selectDateOption(dateTo.getMonthValue(), dateTo.getDayOfMonth(), dateTo.getYear());
    }

}
