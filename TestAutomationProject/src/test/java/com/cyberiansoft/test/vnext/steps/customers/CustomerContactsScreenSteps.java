package com.cyberiansoft.test.vnext.steps.customers;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomerContactsScreen;

public class CustomerContactsScreenSteps {

    public static void clickPlusButton() {

        ConditionWaiter.create(__ -> new VNextCustomerContactsScreen().getPlusButton().isDisplayed()).execute();
        new VNextCustomerContactsScreen().getPlusButton().click();
    }

    public static void clickXIconButton() {

        new VNextCustomerContactsScreen().getXIconButton().click();
    }

    public static void clickNewContactButton() {

        new VNextCustomerContactsScreen().getNewContactButton().click();
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickEditContactButton() {

        new VNextCustomerContactsScreen().getEditContactButton().click();
    }

    public static void clickCloseButton() {

        new VNextCustomerContactsScreen().getCloseButton().click();
    }

    public static void tapOnFirstContact() {

        Utils.clickElement(new VNextCustomerContactsScreen().getContactsRecordsList().get(0).getRootElement());
    }
}
