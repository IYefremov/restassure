package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import org.testng.Assert;

public class CustomersScreenValidation {

    public static void validateDefaultCustomerValue(String customerValue) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(customersScreen.getDefaultCustomerValue(), customerValue);
    }

}
