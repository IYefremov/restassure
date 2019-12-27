package com.cyberiansoft.test.vnextbo.steps.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.users.VNextBOUsersAdvancedSearchForm;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOUsersAdvancedSearchSteps {

    public static void clickSearchButton() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Utils.clickElement(advancedSearchForm.searchButton);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clickCloseButton() {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Utils.clickElement(advancedSearchForm.closeButton);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(advancedSearchForm.closeButton));
    }

    public static void setEmailField(String email) {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Utils.clearAndType(advancedSearchForm.emailField, email);
    }

    public static void setPhoneField(String phone) {
        VNextBOUsersAdvancedSearchForm advancedSearchForm =
                new VNextBOUsersAdvancedSearchForm();
        Utils.clearAndType(advancedSearchForm.phoneField, phone);
    }
}