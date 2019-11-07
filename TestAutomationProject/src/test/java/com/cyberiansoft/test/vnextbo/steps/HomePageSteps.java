package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import org.testng.Assert;

public class HomePageSteps {

    public static void openRepairOrdersMenuWithLocation(String location) {
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(location);
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(location), "The location hasn't been set");
    }

    public static void openRepairOrdersMenuWithLocation(String location, boolean isSetWithEnter) {
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(location, isSetWithEnter);
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(location), "The location hasn't been set");
    }
}