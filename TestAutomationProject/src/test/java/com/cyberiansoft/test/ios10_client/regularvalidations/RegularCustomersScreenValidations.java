package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class RegularCustomersScreenValidations {

    public static void validateCustomerExists(AppCustomer appCustomer) {
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        WaitUtils.collectionSizeIsGreaterThan(customersScreen.getCustomersTable().findElementsByAccessibilityId(appCustomer.getLastName()), 0);
    }
}
