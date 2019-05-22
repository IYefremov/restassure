package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;

public class CustomersSreenSteps {

    public static void selectCustomer(AppCustomer customer) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.selectCustomer(customer);
    }
}
