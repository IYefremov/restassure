package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import org.testng.Assert;

import java.time.LocalDate;

public class MonitorSearchValidations {

    public static void validateTimeFrameFromValue(String expectedDateValue) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getDateFrom().getRootElement().getAttribute("value"),
                expectedDateValue);
    }

    public static void validateTimeFrameToValue(String expectedDateValue) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getDateTo().getRootElement().getAttribute("value"),
                expectedDateValue);
    }
}
