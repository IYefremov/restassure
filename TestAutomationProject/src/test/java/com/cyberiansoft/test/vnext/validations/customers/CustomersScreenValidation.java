package com.cyberiansoft.test.vnext.validations.customers;

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

    public static void verifyCustomersAreFoundCorrectly(String searchText) {

        Assert.assertTrue(new VNextCustomersScreen().getCustomersRecordsList().
                        stream().allMatch(customer -> customer.getCustomerFullName().contains(searchText)),
                "Customers have been found incorrectly by customer name");
    }
}
