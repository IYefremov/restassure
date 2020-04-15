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

        Assert.assertTrue(new VNextCustomerContactsScreen().getContactsRecordsList().
                        stream().allMatch(contact -> contact.getContactName().contains(name)),
                "Contacts have been found incorrectly by name");
    }

    public static void verifyXIconButtonIsDisplayed() {

        ConditionWaiter.create(__ -> new VNextCustomerContactsScreen().getXIconButton().isDisplayed()).execute();
        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getXIconButton()),
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

    public static void verifyNotFoundMessageIsDisplayed() {

        Assert.assertTrue(Utils.getText(new VNextCustomerContactsScreen().getNothingFoundMessage()).contains("Nothing found"),
                "'Nothing found' text hasn't been displayed");
        Assert.assertTrue(Utils.getText(new VNextCustomerContactsScreen().getNothingFoundMessage()).contains("add new contact"),
                "'add new contact' text hasn't been displayed");
    }

    public static void verifyEditButtonIsDisplayed() {

        ConditionWaiter.create(__ -> new VNextCustomerContactsScreen().getEditContactButton().isDisplayed()).execute();
        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getEditContactButton()),
                "'Edit' contact button hasn't been displayed");
    }

    public static void verifyCloseButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerContactsScreen().getCloseButton()),
                "'Close' button hasn't been displayed");
    }
}
