package com.cyberiansoft.test.bo.steps.monitor.repairlocations;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations.BORepairLocationsPage;

import java.util.List;

public class BORepairLocationsPageSteps {

    public static void openEditRepairLocationDialogByLocationName(String location) {
        final BORepairLocationsPage repairLocationsPage = new BORepairLocationsPage();
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(repairLocationsPage.getRepairLocationsTable().getWrappedElement(), 2);
        final List<String> locationsList = Utils.getText(repairLocationsPage.getLocationsList());
        for (int i = 0; i < locationsList.size(); i++) {
            if (locationsList.get(i).equals(location)) {
                Utils.clickElement(repairLocationsPage.getEditButtonsList().get(i));
                WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
                WaitUtilsWebDriver.waitForPendingRequestsToComplete();
                BOLocationDialogSteps.waitForLocationDialogToBeOpened();
            }
        }
    }

    public static String openPhasesLocationDialogByLocationName(String location) {
        final BORepairLocationsPage repairLocationsPage = new BORepairLocationsPage();
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(repairLocationsPage.getRepairLocationsTable().getWrappedElement(), 2);
        final List<String> locationsList = Utils.getText(repairLocationsPage.getLocationsList());
        for (int i = 0; i < locationsList.size(); i++) {
            if (locationsList.get(i).equals(location)) {
                Utils.clickElement(repairLocationsPage.getPhasesList().get(i));
                WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
                WaitUtilsWebDriver.waitForPendingRequestsToComplete();
                String mainWindow = Utils.getParentTab();
                WaitUtilsWebDriver.waitForNewTab();
                Utils.getNewTab(mainWindow);
                BOLocationPhasesWindowSteps.waitForLocationPhasesToBeOpened();
                return mainWindow;
            }
        }
        return "";
    }
}
