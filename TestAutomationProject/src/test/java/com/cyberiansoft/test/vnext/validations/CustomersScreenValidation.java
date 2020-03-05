package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class CustomersScreenValidation {

    public static void validateDefaultCustomerValue(String customerValue) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        WaitUtils.waitUntilElementIsClickable(customersScreen.getCustomersList());
        Assert.assertEquals(customersScreen.getDefaultCustomerValue(), customerValue);
    }

    public static void validateAddCustomerButtonDisplayed(boolean isDisplayed) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        WaitUtils.waitUntilElementIsClickable(customersScreen.getCustomersList());
        if (isDisplayed)
            Assert.assertTrue(customersScreen.isAddCustomerButtonDisplayed());
        else
            Assert.assertFalse(customersScreen.isAddCustomerButtonDisplayed());
    }

}
