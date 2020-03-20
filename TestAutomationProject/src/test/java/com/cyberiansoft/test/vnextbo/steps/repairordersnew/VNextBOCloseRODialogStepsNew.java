package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOCloseRODialogNew;

public class VNextBOCloseRODialogStepsNew {

    public static void closeOrderWithReason(String reason) {

        VNextBOCloseRODialogNew closeOrderDialog = new VNextBOCloseRODialogNew();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        Utils.clickElement(closeOrderDialog.getReasonDropDown());
        ConditionWaiter.create(__ -> closeOrderDialog.reasonDropDownOption(reason).isEnabled());
        Utils.clickWithJS(closeOrderDialog.reasonDropDownOption(reason));
        Utils.clickElement(closeOrderDialog.getCloseROButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
