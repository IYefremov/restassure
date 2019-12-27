package com.cyberiansoft.test.vnextbo.validations.login;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextBOLoginValidations {
    
    public static boolean isLoginFormDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOLoginScreenWebPage().getLoginForm(), true, 10);
    }

    public static boolean isEmailFieldDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOLoginScreenWebPage().getEmailField(), true, 5);
    }

    public static boolean isPasswordFieldDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOLoginScreenWebPage().getPasswordField(), true, 5);
    }

    public static boolean isLoginButtonDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOLoginScreenWebPage().getLoginButton(), true, 5);
    }

    public static boolean isForgotPasswordLinkDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOLoginScreenWebPage().getForgotPasswordLink(), true, 5);
    }
}
