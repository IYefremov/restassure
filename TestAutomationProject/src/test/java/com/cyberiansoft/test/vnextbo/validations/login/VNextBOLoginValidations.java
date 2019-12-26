package com.cyberiansoft.test.vnextbo.validations.login;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOLoginValidations {
    
    public static boolean isLoginFormDisplayed() {
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(new VNextBOLoginScreenWebPage().getLoginForm()));
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getLoginForm());
    }

    public static boolean isEmailFieldDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getEmailField());
    }

    public static boolean isPasswordFieldDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getPasswordField());
    }

    public static boolean isLoginButtonDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getLoginButton());
    }

    public static boolean isForgotPasswordLinkDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getForgotPasswordLink());
    }
}
