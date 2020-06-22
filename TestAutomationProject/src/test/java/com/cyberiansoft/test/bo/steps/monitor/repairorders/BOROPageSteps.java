package com.cyberiansoft.test.bo.steps.monitor.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairorders.BOROPage;

public class BOROPageSteps {

    public static void waitForRoPageToBeDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new BOROPage().getRoPage());
    }
}
