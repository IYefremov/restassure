package com.cyberiansoft.test.vnextbo.steps.homepage;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import org.openqa.selenium.By;
import org.testng.Assert;

public class VNextBOHomeWebPageSteps extends VNextBOBaseWebPageSteps {

    public static void openRepairOrdersMenuWithLocation(String location) {
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(location);
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(location), "The location hasn't been set");
    }

    public static void openRepairOrdersMenuWithLocation(String location, boolean isSetWithEnter) {
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(location, isSetWithEnter);
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(location), "The location hasn't been set");
    }

    public static String openAccessClientPortalWindow() {

        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        Utils.clickElement(new VNextBOHomeWebPage().getAccessClientPortalLink());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        final String clientPortalPageUrl = Utils.getUrl();
        Utils.closeNewTab(mainWindow);
        return clientPortalPageUrl;
    }

    public static String openSupportForBOWindow() {

        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        Utils.clickElement(new VNextBOHomeWebPage().getSupportForBOButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        final String supportForBoPageUrl = Utils.getUrl();
        Utils.closeNewTab(mainWindow);
        return supportForBoPageUrl;
    }

    public static String openSupportForMobileAppWindow() {

        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        Utils.clickElement(new VNextBOHomeWebPage().getSupportForMobileAppButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        final String supportForMobileAppPageUrl = Utils.getUrl();
        Utils.closeNewTab(mainWindow);
        return supportForMobileAppPageUrl;
    }

    public static String openAccessReconProBOWindow() {

        clickAccessReconProBOLink();
        //Certification error handling
        try {
            if (DriverBuilder.getInstance().getDriver().findElement(By.xpath("//button[@id='details-button']")).isDisplayed()) {
                Utils.clickElement(By.xpath("//button[@id='details-button']"));
                Utils.clickElement(By.xpath("//a[@id='proceed-link']"));
                WaitUtilsWebDriver.waitForSpinnerToDisappear();
            }
        } catch (Exception ex) {

        }
        final String reconProBoPageUrl = Utils.getUrl();
        Utils.goToPreviousPage();
        return reconProBoPageUrl;
    }

    public static void clickAccessReconProBOLink() {
        Utils.clickElement(new VNextBOHomeWebPage().getAccessR360BOLink());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilTitleContainsIgnoringException("ReconPro", 4);
    }
}