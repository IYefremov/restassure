package com.cyberiansoft.test.vnextbo.steps.login;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOLoginInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOLoginSteps {

    public static  void userLogin(String username, String userPsw) {
        if (VNextBOLoginValidations.isLoginFormDisplayed()) {
            WebElement loginButton = new VNextBOLoginScreenWebPage().getLoginButton();
            VNextBOLoginInteractions.setEmailField(username);
            VNextBOLoginInteractions.setPasswordField(userPsw);
            Utils.clickElement(loginButton);
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.invisibilityOf(loginButton));
            WaitUtilsWebDriver.waitForSpinnerToDisappear();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(new VNexBOLeftMenuPanel().getMenuButton()));
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
            WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        }
    }

    public static  void userLoginWithInvalidUserData(String username, String userPsw) {
        if (VNextBOLoginValidations.isLoginFormDisplayed()) {
            WebElement loginButton = new VNextBOLoginScreenWebPage().getLoginButton();
            VNextBOLoginInteractions.setEmailField(username);
            VNextBOLoginInteractions.setPasswordField(userPsw);
            Utils.clickElement(loginButton);
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
            WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        }
    }

    public static  void clearLoginFormData() {

        VNextBOLoginScreenWebPage loginScreenWebPage = new VNextBOLoginScreenWebPage();
        Utils.clear(loginScreenWebPage.getEmailField());
        Utils.clear(loginScreenWebPage.getPasswordField());
        WaitUtilsWebDriver.waitABit(2000);
    }
}