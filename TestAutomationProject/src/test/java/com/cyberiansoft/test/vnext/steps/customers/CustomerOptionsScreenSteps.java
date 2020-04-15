package com.cyberiansoft.test.vnext.steps.customers;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomerOptionsScreen;

public class CustomerOptionsScreenSteps {

    public static void closeScreen() {

        Utils.clickElement(new VNextCustomerOptionsScreen().getCloseButton());
    }

    public static void openCustomerContacts() {

        Utils.clickElement(new VNextCustomerOptionsScreen().getCustomerContactsButton());
    }
}
