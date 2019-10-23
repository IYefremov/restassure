package com.cyberiansoft.test.vnextbo.verifications.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;
import org.testng.Assert;

public class VNextBOUsersAdvancedSearchValidations {

    public static void isAdvancedSearchFormDisplayed()
    {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been displayed\"");
    }

    public static void isAdvancedSearchFormNotDisplayed(VNextBOUsersAdvancedSearchForm advancedSearchForm) {
        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been closed");
    }

    public static void isSearchButtonDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.searchButton),
                "Advanced Search button hasn't been displayed");
    }

    public static void isEmailFieldDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.emailField),
                "Email field hasn't been displayed");
    }

    public static void isPhoneFieldDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.phoneField),
                "Phone field hasn't been displayed");
    }

    public static void isCloseButtonDisplayed() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.closeButton),
                "Close x-icon button hasn't been displayed");
    }
}