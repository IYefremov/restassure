package com.cyberiansoft.test.vnext.validations.customers;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomerOptionsScreen;
import org.testng.Assert;

public class CustomerOptionsScreenValidation {

    public static void verifyViewButtonIsDisplayed() {

        ConditionWaiter.create(__ -> new VNextCustomerOptionsScreen().getViewButton().isDisplayed()).execute();
        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerOptionsScreen().getViewButton()),
                "'View' button hasn't been displayed");
    }

    public static void verifySetAsDefaultButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerOptionsScreen().getSetAsDefaultButton()),
                "'Set as Default' button hasn't been displayed");
    }

    public static void verifyCustomerContactsButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextCustomerOptionsScreen().getCustomerContactsButton()),
                "'Customer Contacts' button hasn't been displayed");
    }
}
