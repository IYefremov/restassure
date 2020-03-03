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

}
