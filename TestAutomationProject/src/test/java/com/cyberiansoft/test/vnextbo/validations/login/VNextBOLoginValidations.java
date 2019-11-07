package com.cyberiansoft.test.vnextbo.validations.login;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextBOLoginValidations {
    
    public static boolean isLoginFormDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getLoginForm());
    }

    public static boolean isEmailFieldDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getEmailField().getWrappedElement());
    }

    public static boolean isPasswordFieldDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getPasswordField().getWrappedElement());
    }

    public static boolean isLoginButtonDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getLoginButton());
    }

    public static boolean isForgotPasswordLinkDisplayed() {
        return Utils.isElementDisplayed(new VNextBOLoginScreenWebPage().getForgotPasswordLink());
    }
}
