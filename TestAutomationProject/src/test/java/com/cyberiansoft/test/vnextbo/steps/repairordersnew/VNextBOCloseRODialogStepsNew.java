package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOCloseRODialogNew;

public class VNextBOCloseRODialogStepsNew {

    public static void closeOrderWithCompletedReason() {

        VNextBOCloseRODialogNew closeOrderDialog = new VNextBOCloseRODialogNew();
        Utils.clickElement(closeOrderDialog.getReasonDropDown());
        Utils.clickElement(closeOrderDialog.getCompletedDropDownOption());
        Utils.clickElement(closeOrderDialog.getCloseROButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
