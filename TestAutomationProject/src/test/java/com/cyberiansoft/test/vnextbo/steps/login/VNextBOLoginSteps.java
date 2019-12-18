package com.cyberiansoft.test.vnextbo.steps.login;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOLoginInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;

public class VNextBOLoginSteps {

    public static  void userLogin(String username, String userPsw) {
        if (VNextBOLoginValidations.isLoginFormDisplayed()) {
            VNextBOLoginInteractions.setEmailField(username);
            VNextBOLoginInteractions.setPasswordField(userPsw);
            Utils.clickElement(new VNextBOLoginScreenWebPage().getLoginButton());
            WaitUtilsWebDriver.waitForAttributeNotToContain(
                    new VNextBOLoginScreenWebPage().getBody(), "class", "loginPage", 10);
            WaitUtilsWebDriver.waitABit(500);
        }
    }

    public static  void clearLoginFormData() {

        VNextBOLoginScreenWebPage loginScreenWebPage = new VNextBOLoginScreenWebPage();
        Utils.clear(loginScreenWebPage.getEmailField());
        Utils.clear(loginScreenWebPage.getPasswordField());
        WaitUtilsWebDriver.waitABit(2000);
    }
}
