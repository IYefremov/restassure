package com.cyberiansoft.test.vnextbo.validations;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class VNextBOBaseWebPageValidations {

    public static void verifyLogoIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.logoBox), "Logo hasn't been displayed");
    }

    public static void verifyTimeBoxIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.timeBox), "Time box hasn't been displayed");
    }

    public static void verifyUserInfoBlockIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.userInfoBlock), "User info block hasn't been displayed");
    }

    public static void verifyLogoutButtonIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.logoutButton), "Logout button hasn't been displayed");
    }

    public static void verifyHelpButtonIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.helpButton), "Help button hasn't been displayed");
    }

    public static void verifyCopyRightTextIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.getText(baseWebPage.copyRightLabel).contains("© Copyright 2019 AutoMobile Technologies, Inc."),
                "Copyright text hasn't been displayed");
    }

    public static void verifyTermsAndConditionsLinkIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.termsAndConditionsLink), "Terms and Conditions link hasn't been displayed");
    }

    public static void verifyPrivacyPolicyLinkIsDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.privacyPolicyLink), "Privacy Policy link hasn't been displayed");
    }

    public static void verifyIntercomButtonIsDisplayed()
    {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(driver);
        driver.switchTo().frame(baseWebPage.intercomLauncherFrame);
        boolean isIntercomButtonDisplayed = Utils.isElementDisplayed(baseWebPage.openCloseIntercomButton);
        driver.switchTo().defaultContent();
        Assert.assertTrue(isIntercomButtonDisplayed, "Intercom button hasn't been displayed");
    }

    public static void verifyIntercomMessengerIsOpened()
    {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(driver);
        driver.switchTo().frame(baseWebPage.intercomMessengerFrame);
        boolean isMessengerOpened =  Utils.isElementDisplayed(baseWebPage.intercomNewConversionSpace);
        driver.switchTo().defaultContent();
        Assert.assertTrue(isMessengerOpened, "Intercom messenger hasn't been opened");
    }

    public static void verifyHelpPageIsOpened()
    {
        String parentHandle = Utils.getParentTab();
        WaitUtilsWebDriver.waitForNewTab();
        String newWindow = Utils.getNewTab(parentHandle);
        DriverBuilder.getInstance().getDriver().switchTo().window(parentHandle);
        boolean isHelpPageOpened = false;
        if (!parentHandle.equals(newWindow)) isHelpPageOpened = true;
        Assert.assertTrue(isHelpPageOpened, "Help page hasn't been opened");
    }
}