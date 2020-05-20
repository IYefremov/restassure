package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBODashboardPanel;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBODashboardPanelValidations;

public class VNextBODashboardPanelSteps {

    public static void waitForDashboardToBeLoaded() {
        final VNextBODashboardPanel dashboardPanel = new VNextBODashboardPanel();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(dashboardPanel.getDashboardItems(), 7);
    }

    public static void applyFilter(String name) {
        clickFilterByName(name);
        VNextBODashboardPanelValidations.verifyFilterIsAppliedByName(name, true);
    }

    private static void clickFilterByName(String name) {
        final VNextBODashboardPanel dashboardPanel = new VNextBODashboardPanel();
        Utils.clickElement(dashboardPanel.getItemByName(name));
        WaitUtilsWebDriver.waitForPageToBeLoaded(7);
    }

    public static int getFilterValueByName(String name) {
        return Integer.parseInt(Utils.getText(new VNextBODashboardPanel().getItemCounterByName(name)));
    }
}