package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;

public class CustomersScreenSteps {

    public static void selectCustomer(AppCustomer customer) {
        CustomersScreen customersScreen = new CustomersScreen();
        customersScreen.selectCustomer(customer);
    }
}
