package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROResolveProblemDialogNew;

public class VNextBOROResolveProblemDialogStepsNew {

    public static void resolveProblem() {

        VNextBOROResolveProblemDialogNew resolveProblemDialog = new VNextBOROResolveProblemDialogNew();
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickElement(resolveProblemDialog.getResolveButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
