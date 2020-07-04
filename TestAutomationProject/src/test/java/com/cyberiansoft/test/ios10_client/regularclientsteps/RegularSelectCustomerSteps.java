package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularCustomerSelectionScreen;

public class RegularSelectCustomerSteps {

    public static void selectCustomer(AppCustomer appCustomer) {
        RegularCustomerSelectionScreen customerSelectionScreen = new RegularCustomerSelectionScreen();
        if (appCustomer.isWholesale())
            customerSelectionScreen.switchToWholeSaleMode();
        else
            customerSelectionScreen.switchToRetailMode();
        customerSelectionScreen.selectCustomer(appCustomer);
    }
}
