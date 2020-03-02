package com.cyberiansoft.test.vnextbo.interactions.breadcrumb;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBreadCrumbPanel;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class VNextBOBreadCrumbInteractions {

    public static void clickFirstBreadCrumbLink() {
        Utils.clickElement(new VNextBOBreadCrumbPanel().getBreadCrumbsLink());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static String getLastBreadCrumbText() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(new VNextBOBreadCrumbPanel().getLastBreadCrumb(), 7).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static void setLocation(String location) {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        if (!VNextBOBreadCrumbValidations.isLocationSet(location, 1)) {
            openLocationDropDown();
            selectLocation(location);
        }
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickLocationName() {
        Utils.clickElement(new VNextBOBreadCrumbPanel().getLocationName());
    }

    public static void setLocation(String location, boolean isSetWithEnter) {
        if (isSetWithEnter) {
            VNextBOBreadCrumbValidations.isLocationSearched(location);
            new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(Keys.ENTER);
        }
        setLocation(location);
    }

    private static void selectLocation(String location) {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        final VNextBOBreadCrumbPanel breadCrumbPanel = new VNextBOBreadCrumbPanel();
        Utils.selectOptionInDropDown(breadCrumbPanel.getLocationsDropDown(),
                breadCrumbPanel.getLocationsList(), location, true);
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSelected(location), "The location hasn't been selected");
    }

    public static void closeLocationDropDown() {
        if (VNextBOBreadCrumbValidations.isLocationExpanded()) {
            clickLocationName();
        }
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOBreadCrumbPanel().getLocationExpanded(), false);
    }

    public static void openLocationDropDown() {
        if (VNextBOBreadCrumbValidations.isLocationCollapsed()) {
            clickLocationName();
        }
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOBreadCrumbPanel().getLocationExpanded(), true);
    }

    public static String getActiveLocationValue() {
        return Utils.getText(new VNextBOBreadCrumbPanel().getActiveLocation());
    }

    public static int clearAndTypeLocation(String searchLocation) {
        final VNextBOBreadCrumbPanel breadCrumbPanel = new VNextBOBreadCrumbPanel();
        Utils.clickElement(breadCrumbPanel.getLocationSearchInput());
        final int locationsNum = WaitUtilsWebDriver
                .waitForVisibilityOfAllOptions(breadCrumbPanel.getLocationLabels()).size();
        Utils.clearAndType(breadCrumbPanel.getLocationSearchInput(), searchLocation);
        return locationsNum;
    }

    public static void clickLocationInDropDown(String location) {
        new VNextBOBreadCrumbPanel().getLocationLabels().stream().filter(loc -> loc.getText().contains(location))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForLoading();
    }
}