package com.cyberiansoft.test.vnext.steps.customers;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomerContactsScreen;

public class CustomerContactsScreenSteps {

    public static void clickPlusButton() {

        ConditionWaiter.create(__ -> new VNextCustomerContactsScreen().getPlusButton().isDisplayed()).execute();
        Utils.clickElement(new VNextCustomerContactsScreen().getPlusButton());
    }

    public static void clickXIconButton() {

        Utils.clickElement(new VNextCustomerContactsScreen().getXIconButton());
    }

    public static void clickNewContactButton() {

        Utils.clickElement(new VNextCustomerContactsScreen().getNewContactButton());
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickEditContactButton() {

        Utils.clickElement(new VNextCustomerContactsScreen().getEditContactButton());
    }

    public static void clickCloseButton() {

        Utils.clickElement(new VNextCustomerContactsScreen().getCloseButton());
    }

    public static void tapOnFirstContact() {

        Utils.clickElement(new VNextCustomerContactsScreen().getContactsRecordsList().get(0).getRootElement());
    }
}
