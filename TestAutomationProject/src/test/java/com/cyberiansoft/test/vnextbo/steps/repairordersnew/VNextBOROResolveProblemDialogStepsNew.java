package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROResolveProblemDialogNew;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOROResolveProblemDialogStepsNew {

    public static void resolveProblemWithoutDescription() {

        VNextBOROResolveProblemDialogNew resolveProblemDialog = new VNextBOROResolveProblemDialogNew();
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(resolveProblemDialog.getResolveButton()));
        Utils.clickWithJS(resolveProblemDialog.getResolveButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
