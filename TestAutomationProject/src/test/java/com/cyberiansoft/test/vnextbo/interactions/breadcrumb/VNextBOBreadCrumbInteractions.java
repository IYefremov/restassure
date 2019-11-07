package com.cyberiansoft.test.vnextbo.interactions.breadcrumb;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBreadCrumbPanel;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOBreadCrumbInteractions {

    public static void clickFirstBreadCrumbLink() {
        Utils.clickElement(new VNextBOBreadCrumbPanel().getBreadCrumbsLink());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getLastBreadCrumbText() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(new VNextBOBreadCrumbPanel().getLastBreadCrumb(), 7).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static void setLocation(String location) {
        if (!VNextBOBreadCrumbValidations.isLocationExpanded()) {
            Utils.clickElement(new VNextBOBreadCrumbPanel().getLocationName());
        }
        selectLocation(location);
    }

    public static void setLocation(String location, boolean isSetWithEnter) {
        if (isSetWithEnter) {
            System.out.println("isSetWithEnter");
            VNextBOBreadCrumbValidations.isLocationSearched(location);
            Utils.getActions().sendKeys(Keys.ENTER);
            setLocation(location);
        } else {
            setLocation(location);
        }
    }

    private static void selectLocation(String location) {
        Utils.selectOptionInDropDown(
                new VNextBOBreadCrumbPanel().getLocationsDropDown(), new VNextBOBreadCrumbPanel().getLocationsList(), location, true);
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSelected(location), "The location hasn't been selected");
    }

    public static void closeLocationDropDown() {
        if (VNextBOBreadCrumbValidations.isLocationExpanded()) {
            Utils.clickElement(new VNextBOBreadCrumbPanel().getLocationName());
        }
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(new VNextBOBreadCrumbPanel().getLocationExpanded());
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