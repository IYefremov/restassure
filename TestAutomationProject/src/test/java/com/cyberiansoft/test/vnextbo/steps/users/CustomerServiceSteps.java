package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;


public class CustomerServiceSteps {

    // Step for creation customer if not Exist
    public static void createCustomerIfNotExist(RetailCustomer retailCustomer){
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        CustomersScreenSteps.switchToRetailMode();
        if (!customersScreen.isCustomerExists(retailCustomer)) {
            CustomersScreenSteps.clickAddCustomerButton();
            VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
            newCustomerScreen.createNewCustomer(retailCustomer);
        }
    }

    public static void createCustomerIfNotExistAndSetAsDefault(RetailCustomer retailCustomer){
        createCustomerIfNotExist(retailCustomer);
        CustomersScreenSteps.setCustomerAsDefault(retailCustomer);
    }
}
