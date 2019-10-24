package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;

public class VNextBOUsersAdvancedSearchSteps {

    public static void clickSearchButton() {

        Utils.clickElement(new VNextBOUsersAdvancedSearchForm().searchButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickCloseButton() {

        Utils.clickElement(new VNextBOUsersAdvancedSearchForm().closeButton);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setEmailField(String email) {

        Utils.clearAndType(new VNextBOUsersAdvancedSearchForm().emailField, email);
    }

    public static void setPhoneField(String phone) {

        Utils.clearAndType(new VNextBOUsersAdvancedSearchForm().phoneField, phone);
    }
}