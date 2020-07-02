package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOLogInfoDialogNew;

public class VNextBOLogInfoDialogStepsNew {

    public static void openServicesTab() {

        Utils.clickElement(new VNextBOLogInfoDialogNew().getServicesTab());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void closeDialog() {

        Utils.clickElement(new VNextBOLogInfoDialogNew().getCloseDialogButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
