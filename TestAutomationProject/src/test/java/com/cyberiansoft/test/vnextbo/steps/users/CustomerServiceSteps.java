package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.steps.CustomersScreenSteps;


public class CustomerServiceSteps {

    // Step for creation customer if not Exist
    public static void createCustomerIfNotExistAndSetAsDefault(RetailCustomer retailCustomer){
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        if (!customersScreen.isCustomerExists(retailCustomer)) {
            VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
            newCustomerScreen.createNewCustomer(retailCustomer);
        }
        CustomersScreenSteps.setCustomerAsDefault(retailCustomer);
    }
}
