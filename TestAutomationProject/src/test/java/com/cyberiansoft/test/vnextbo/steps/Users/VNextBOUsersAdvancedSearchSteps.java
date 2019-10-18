package com.cyberiansoft.test.vnextbo.steps.Users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Users.VNextBOUsersAdvancedSearchForm;

public class VNextBOUsersAdvancedSearchSteps {

    public static void clickSearchButton()
    {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.searchButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickCloseButton()
    {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.closeButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setEmailField(String email)
    {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clearAndType(advancedSearchForm.emailField, email);
    }

    public static void setPhoneField(String phone)
    {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clearAndType(advancedSearchForm.phoneField, phone);
    }
}