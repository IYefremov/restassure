package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBODashboardPanel;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBODashboardPanelSteps;
import org.openqa.selenium.By;
import org.testng.Assert;

public class VNextBODashboardPanelValidations {

    public static void verifyFiltersAreNotApplied() {
        final VNextBODashboardPanel dashboardPanel = new VNextBODashboardPanel();
        final boolean present = dashboardPanel.getDashboardItems()
                .stream()
                .noneMatch(f -> f.getAttribute("style").contains("color"));
        Assert.assertTrue(present, "The dashboard filters have been applied");
    }

    public static void verifyFilterIsAppliedByName(String name, boolean expected) {
        final VNextBODashboardPanel dashboardPanel = new VNextBODashboardPanel();
        final boolean applied = dashboardPanel.getItemByName(name).findElement(By.xpath(".//.."))
                .getAttribute("style").contains("color");
        Assert.assertEquals(expected, applied, "The dashboard filter has been (not) applied for " + name);
    }

    public static void verifyDashboardValueByFilterName(String name, int expected) {
        Assert.assertEquals(expected, VNextBODashboardPanelSteps.getFilterValueByName(name),
                "The expected dashboard value is not equal to the number of part status '" + name + "'");
    }
}
