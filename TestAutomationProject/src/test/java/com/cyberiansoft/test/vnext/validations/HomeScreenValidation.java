package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import org.testng.Assert;

public class HomeScreenValidation {

    public static void validateDefaultCustomerValue(String customerValue) {
        VNextCustomersScreen homeScreen = new VNextCustomersScreen();
        Assert.assertEquals(homeScreen.getDefaultCustomerValue(), customerValue);
    }

    public static void verifyHomeScreenIsOpened() {

        Assert.assertTrue(new VNextHomeScreen().getRootElement().isDisplayed(),
                "Home screen hasn't been opened");
    }
}
