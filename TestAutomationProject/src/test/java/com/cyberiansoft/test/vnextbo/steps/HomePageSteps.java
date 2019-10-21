package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import org.testng.Assert;

public class HomePageSteps {

    public static void openRepairOrdersMenuWithLocation(String location) {
        new VNextBOLeftMenuInteractions().selectRepairOrdersMenu();
        new VNextBOBreadCrumbInteractions().setLocation(location);
        Assert.assertTrue(new VNextBOBreadCrumbInteractions().isLocationSet(location), "The location hasn't been set");
    }

    public static void openRepairOrdersMenuWithLocation(String location, boolean isSetWithEnter) {
        new VNextBOLeftMenuInteractions().selectRepairOrdersMenu();
        new VNextBOBreadCrumbInteractions().setLocation(location, isSetWithEnter);
        Assert.assertTrue(new VNextBOBreadCrumbInteractions().isLocationSet(location), "The location hasn't been set");
    }
}