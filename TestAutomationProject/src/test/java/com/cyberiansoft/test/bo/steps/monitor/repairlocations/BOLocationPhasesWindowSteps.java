package com.cyberiansoft.test.bo.steps.monitor.repairlocations;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations.BOLocationPhasesWindow;

import java.util.List;

public class BOLocationPhasesWindowSteps {

    public static void waitForLocationPhasesToBeOpened() {
        WaitUtilsWebDriver.waitForVisibility(new BOLocationPhasesWindow().getPhasesHeader());
    }

    public static void openEditDialogByPhaseName(String phase) {
        final BOLocationPhasesWindow phasesWindow = new BOLocationPhasesWindow();
        final List<String> phasesList = Utils.getText(phasesWindow.getPhasesList());
        for (int i = 0; i < phasesList.size(); i++) {
            if (phasesList.get(i).equals(phase)) {
                Utils.clickElement(phasesWindow.getEditButtonsList().get(i));
                WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
                WaitUtilsWebDriver.waitForPendingRequestsToComplete();
                BOEditPhaseDialogSteps.waitForPhaseDialogToBeOpened();
                break;
            }
        }
    }
}
