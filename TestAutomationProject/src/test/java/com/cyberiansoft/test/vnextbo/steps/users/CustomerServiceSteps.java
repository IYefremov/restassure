package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;


public class CustomerServiceSteps {

    // Step for creation customer if not Exist
    public static void createCustomerIfNotExistAndSetAsDefault(RetailCustomer retailCustomer){
        VNextCustomersScreen customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        customersScreen.switchToRetailMode();
        if (!customersScreen.isCustomerExists(retailCustomer)) {
            VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
            newCustomerScreen.createNewCustomer(retailCustomer);
            customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        customersScreen.setCustomerAsDefault(retailCustomer);
        WaitUtilsWebDriver.waitForTextToBePresentInElement(customersScreen.getPresetcustomerpanel(), retailCustomer.getFullName());
    }
}
