package com.cyberiansoft.test.vnext.validations.customers;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomerContactsScreen;
import org.testng.Assert;

public class CustomerContactsScreenValidations {

    public static void verifyPlusButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getPlusButton()),
                "'Plus' button hasn't been displayed");
    }

    public static void verifyContactsListIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getContactsList()),
                "Contacts list hasn't been displayed");
    }

    public static void verifyContactsWereFoundCorrectlyByName(String name) {

        Assert.assertTrue(new VNextCustomerContactsScreen().getContactsNamesList().
                        stream().allMatch(contact -> contact.getContactName().contains(name)),
                "Contacts have been found incorrectly by name");
    }

    public static void verifyCloseButtonIsDisplayed() {

        ConditionWaiter.create(__ -> new VNextCustomerContactsScreen().getCloseButton().isDisplayed()).execute();
        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getCloseButton()),
                "'Close' button hasn't been displayed");
    }

    public static void verifyFromContactsIconAndButtonAreDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getFromContactsButton()),
                "'From contacts' button button hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getFromContactsIcon()),
                "'From contacts' icon button hasn't been displayed");
    }

    public static void verifyNewContactIconAndButtonAreDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getNewContactButton()),
                "'New contact' button button hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getNewContactIcon()),
                "'New contact' icon button hasn't been displayed");
    }
}
