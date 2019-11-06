package com.cyberiansoft.test.vnextbo.verifications.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;
import org.testng.Assert;

public class VNextBOUsersAdvancedSearchValidations {

    public static void verifyAdvancedSearchFormIsDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been displayed\"");
    }

    public static void verifyAdvancedSearchFormIsNotDisplayed(VNextBOUsersAdvancedSearchForm advancedSearchForm) {
        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been closed");
    }

    public static void verifySearchButtonIsDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.searchButton),
                "Advanced Search button hasn't been displayed");
    }

    public static void verifyEmailFieldIsDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.emailField),
                "Email field hasn't been displayed");
    }

    public static void verifyPhoneFieldIsDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.phoneField),
                "Phone field hasn't been displayed");
    }

    public static void verifyCloseButtonIsDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.closeButton),
                "Close x-icon button hasn't been displayed");
    }
}