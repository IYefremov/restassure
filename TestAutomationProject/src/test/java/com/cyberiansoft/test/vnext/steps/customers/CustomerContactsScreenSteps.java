package com.cyberiansoft.test.vnext.steps.customers;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomerContactsScreen;

public class CustomerContactsScreenSteps {

    public static void clickPlusButton() {

        ConditionWaiter.create(__ -> new VNextCustomerContactsScreen().getPlusButton().isDisplayed()).execute();
        Utils.clickElement(new VNextCustomerContactsScreen().getPlusButton());
    }

    public static void clickCloseButton() {

        Utils.clickElement(new VNextCustomerContactsScreen().getCloseButton());
    }
}
