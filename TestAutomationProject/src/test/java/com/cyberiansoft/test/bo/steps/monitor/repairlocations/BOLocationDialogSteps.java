package com.cyberiansoft.test.bo.steps.monitor.repairlocations;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations.BOLocationDialog;

public class BOLocationDialogSteps {

    public static void waitForLocationDialogToBeOpened() {
        WaitUtilsWebDriver.waitForVisibility(new BOLocationDialog().getDialogHeader(), 4);
    }
}
