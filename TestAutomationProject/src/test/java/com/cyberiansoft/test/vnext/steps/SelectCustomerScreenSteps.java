package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.webelements.customers.CustomersListElement;

public class SelectCustomerScreenSteps {

    public static void selectCustomer(AppCustomer customer) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        CustomersListElement customersListElement = customersScreen.getCustomerElement(customer.getFullName());
        customersListElement.selectCustomer();
    }
}
