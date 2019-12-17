package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebDriver;

public class VNextBOBaseWebPageSteps {

    public static void clickLogo()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.logoBox);
    }

    public static void openUserProfile()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.userInfoBlock);
    }

    public static void logOut()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.logoutButton);
    }

    public static void openHelpPage()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.helpButton);
    }

    public static void clickTermsAndConditionsLink()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.termsAndConditionsLink);
        //WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickPrivacyPolicyLink()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.privacyPolicyLink);
        //WaitUtilsWebDriver.waitForLoading();
    }

    public static void openIntercomMessenger()
    {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(driver);
        driver.switchTo().frame(baseWebPage.intercomLauncherFrame);
        Utils.clickElement(baseWebPage.openCloseIntercomButton);
        driver.switchTo().defaultContent();
    }

    public static void closeIntercom()
    {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(driver);
        driver.switchTo().frame(baseWebPage.intercomLauncherFrame);
        Utils.clickElement(baseWebPage.openCloseIntercomButton);
        driver.switchTo().defaultContent();
    }
}