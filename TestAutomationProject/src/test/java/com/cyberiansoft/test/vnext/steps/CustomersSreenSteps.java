package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;

public class CustomersSreenSteps {

    public static void selectCustomer(AppCustomer customer) {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.selectCustomer(customer);
    }

    public static void clickAddCustomerButton() {
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.clickAddCustomerButton();
    }

    public static void createNewRetailCustomer(RetailCustomer retailCustomer) {
        clickAddCustomerButton();
        VNextNewCustomerScreen nextNewCustomerScreen = new VNextNewCustomerScreen();
        nextNewCustomerScreen.createNewCustomer(retailCustomer);
    }
}
