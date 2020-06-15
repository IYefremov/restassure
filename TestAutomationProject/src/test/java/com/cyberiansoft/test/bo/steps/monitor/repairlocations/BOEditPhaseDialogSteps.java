package com.cyberiansoft.test.bo.steps.monitor.repairlocations;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations.BOEditPhaseDialog;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOEditPhaseDialogSteps {

    public static void waitForPhaseDialogToBeOpened() {
        WaitUtilsWebDriver.waitForVisibility(new BOEditPhaseDialog().getDialogHeader(), 4);
    }

    public static void setWorkStatusTracking(String status) {
        final BOEditPhaseDialog phaseDialog = new BOEditPhaseDialog();
        selectComboboxValue(phaseDialog.getWorkStatusCmb(), phaseDialog.getWorkStatusDd(), status);
    }

    public static void confirm() {
        final BOEditPhaseDialog phaseDialog = new BOEditPhaseDialog();
        Utils.clickElement(phaseDialog.getOkButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }
}
