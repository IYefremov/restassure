package com.cyberiansoft.test.vnextbo.verifications.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;
import org.testng.Assert;

public class VNextBOUsersAdvancedSearchValidations {

    public static void isAdvancedSearchFormDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUsersAdvancedSearchForm().advancedSearchFormContent),
                "Advanced search form hasn't been displayed\"");
    }

    public static void isAdvancedSearchFormNotDisplayed(VNextBOUsersAdvancedSearchForm advancedSearchForm) {

        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been closed");
    }

    public static void isSearchButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUsersAdvancedSearchForm().searchButton),
                "Advanced Search button hasn't been displayed");
    }

    public static void isEmailFieldDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUsersAdvancedSearchForm().emailField),
                "Email field hasn't been displayed");
    }

    public static void isPhoneFieldDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUsersAdvancedSearchForm().phoneField),
                "Phone field hasn't been displayed");
    }

    public static void isCloseButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUsersAdvancedSearchForm().closeButton),
                "Close x-icon button hasn't been displayed");
    }
}