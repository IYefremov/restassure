package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebDriver;

public class VNextBOBaseWebPageSteps {

    public static void clickLogo()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.logoBox);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void openUserProfile()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.userInfoBlock);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static String openHelpPage()
    {
        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        Utils.clickElement(new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver()).getHelpButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        final String actualHelpPageUrl = Utils.getUrl();
        Utils.closeNewTab(mainWindow);
        return actualHelpPageUrl;
    }

    public static String openLearnPage()
    {
        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.moveToElement(baseWebPage.getHelpButton());
        Utils.clickElement(baseWebPage.getHelpLearnButton());
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        final String actualLearnPageUrl = Utils.getUrl();
        Utils.closeNewTab(mainWindow);
        return actualLearnPageUrl;
    }

    public static void clickTermsAndConditionsLink()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.termsAndConditionsLink);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clickPrivacyPolicyLink()
    {
        VNextBOBaseWebPage baseWebPage = new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(baseWebPage.privacyPolicyLink);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
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