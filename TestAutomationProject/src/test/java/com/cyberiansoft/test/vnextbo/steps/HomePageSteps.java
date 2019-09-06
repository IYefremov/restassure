package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePageSteps {

    public VNextBOLeftMenuInteractions leftMenuInteractions;
    public VNextBOBreadCrumbInteractions breadCrumbInteractions;

    public HomePageSteps() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        leftMenuInteractions = new VNextBOLeftMenuInteractions();
        breadCrumbInteractions = PageFactory.initElements(driver, VNextBOBreadCrumbInteractions.class);
    }

    public void openRepairOrdersMenuWithLocation(String location) {
        leftMenuInteractions.selectRepairOrdersMenu();
        breadCrumbInteractions.setLocation(location);
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(location), "The location hasn't been set");
    }

    public void openRepairOrdersMenuWithLocation(String location, boolean isSetWithEnter) {
        leftMenuInteractions.selectRepairOrdersMenu();
        breadCrumbInteractions.setLocation(location, isSetWithEnter);
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(location), "The location hasn't been set");
    }
}