package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;


public class CustomerServiceSteps {

    // Step for creation customer if not Exist
    public static void createCustomerIfNotExistAndSetAsDefault(VNextCustomersScreen customersscreen, RetailCustomer retailCustomer){

        customersscreen.switchToRetailMode();
        if (!customersscreen.isCustomerExists(retailCustomer)) {
            VNextNewCustomerScreen newCustomerScreen = customersscreen.clickAddCustomerButton();
            newCustomerScreen.createNewCustomer(retailCustomer);
            customersscreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        customersscreen.setCustomerAsDefault(retailCustomer);
        WaitUtilsWebDriver.waitForTextToBePresentInElement(customersscreen.getPresetcustomerpanel(), retailCustomer.getFullName());
    }
}
