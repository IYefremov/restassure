package com.cyberiansoft.test.vnextbo.validations;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class VNextBOBaseWebPageValidations {

    public static void isLogoDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.logoBox), "Logo hasn't been displayed");
    }

    public static void isTimeBoxDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.timeBox), "Time box hasn't been displayed");
    }

    public static void isUserInfoBlockDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.userInfoBlock), "User info block hasn't been displayed");
    }

    public static void isLogoutButtonDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.logoutButton), "Logout button hasn't been displayed");
    }

    public static void isHelpButtonDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.helpButton), "Help button hasn't been displayed");
    }

    public static void isCopyRightTextDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.getText(baseWebPage.copyRightLabel).contains("© Copyright 2019 AutoMobile Technologies, Inc."),
                "Copyright text hasn't been displayed");
    }

    public static void isTermsAndConditionsLinkDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.termsAndConditionsLink), "Terms and Conditions link hasn't been displayed");
    }

    public static void isPrivacyPolicyLinkDisplayed()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(baseWebPage.privacyPolicyLink), "Privacy Policy link hasn't been displayed");
    }

    public static void isIntercomButtonDisplayed()
    {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(driver);
        driver.switchTo().frame(baseWebPage.intercomLauncherFrame);
        boolean isIntercomButtonDisplayed = Utils.isElementDisplayed(baseWebPage.openCloseIntercomButton);
        driver.switchTo().defaultContent();
        Assert.assertTrue(isIntercomButtonDisplayed, "Intercom button hasn't been displayed");
    }

    public static void isIntercomMessengerOpened()
    {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(driver);
        driver.switchTo().frame(baseWebPage.intercomMessengerFrame);
        boolean isMessengerOpened =  Utils.isElementDisplayed(baseWebPage.intercomNewConversionSpace);
        driver.switchTo().defaultContent();
        Assert.assertTrue(isMessengerOpened, "Intercom messenger hasn't been opened");
    }

    public static void isHelpPageOpened()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        String parentHandle = Utils.getParentTab();
        baseWebPage.waitForNewTab();
        String newWindow = Utils.getNewTab(parentHandle);
        DriverBuilder.getInstance().getDriver().switchTo().window(parentHandle);
        boolean isHelpPageOpened = false;
        if (!parentHandle.equals(newWindow)) isHelpPageOpened = true;
        Assert.assertTrue(isHelpPageOpened, "Help page hasn't been opened");
    }
}