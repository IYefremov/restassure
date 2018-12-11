package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamPresetCustomerTestCases extends BaseTestCaseTeamEditionRegistration {

    RetailCustomer retailCustomer = new RetailCustomer("Preset", "RetailCustomer");

    @BeforeClass(description="Team Preset Customer Test Cases")
    public void beforeClass() {
    }

    @Test(testName= "Test Case 82203:Verify user can preset retail customer",
            description = "Verify user can preset retail customer")
    public void testVerifyUseCanPresetRetailCustomer() {
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        customersscreen.switchToRetailMode();
        VNextNewCustomerScreen newCustomerScreen = customersscreen.clickAddCustomerButton();
        newCustomerScreen.createNewCustomer(retailCustomer);
        customersscreen = new VNextCustomersScreen(appiumdriver);
        customersscreen.setCustomerAsDefault(retailCustomer);

        Assert.assertEquals(customersscreen.getDefaultCustomerValue(), retailCustomer.getFullName());
        customersscreen.clickBackButton();
        homescreen = new VNextHomeScreen(appiumdriver);
        Assert.assertEquals(homescreen.getDefaultCustomerValue(), retailCustomer.getFullName());
    }

    @Test(testName= "Test Case 82204:Verify user can preset wholwsale customer",
            description = "Verify user can preset wholwsale customer")
    public void testVerifyUseCanPresetWholwsaleCustomer() {
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        customersscreen.switchToWholesaleMode();
        customersscreen.setCustomerAsDefault(testwholesailcustomer);

        Assert.assertEquals(customersscreen.getDefaultCustomerValue(), testwholesailcustomer.getFullName());
        customersscreen.clickBackButton();
        homescreen = new VNextHomeScreen(appiumdriver);
        Assert.assertEquals(homescreen.getDefaultCustomerValue(), testwholesailcustomer.getFullName());
    }
}
