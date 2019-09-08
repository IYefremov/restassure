package com.cyberiansoft.test.vnextbo.interactions.breadcrumb;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBreadCrumbPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOBreadCrumbInteractions {

    private VNextBOBreadCrumbPanel breadCrumbPanel;

    public VNextBOBreadCrumbInteractions() {
        breadCrumbPanel = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOBreadCrumbPanel.class);
    }

    public boolean isBreadCrumbClickable() {
        return Utils.isElementClickable(breadCrumbPanel.getBreadCrumbsLink());
    }

    public void clickFirstBreadCrumbLink() {
        Utils.clickElement(breadCrumbPanel.getBreadCrumbsLink());
        WaitUtilsWebDriver.waitForLoading();
    }

    public boolean isLastBreadCrumbDisplayed() {
        return Utils.isElementDisplayed(breadCrumbPanel.getLastBreadCrumb());
    }

    public String getLastBreadCrumbText() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(breadCrumbPanel.getLastBreadCrumb(), 7).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public void setLocation(String location) {
        if (!isLocationExpanded()) {
            Utils.clickElement(breadCrumbPanel.getLocationName());
        }
        selectLocation(location);
    }

    public void setLocation(String location, boolean isSetWithEnter) {
        if (isSetWithEnter) {
            System.out.println("isSetWithEnter");
            isLocationSearched(location);
            Utils.getActions().sendKeys(Keys.ENTER);
            setLocation(location);
        } else {
            setLocation(location);
        }
    }

    public boolean isLocationSet(String location) {
        return Utils.isTextDisplayed(breadCrumbPanel.getLocationName(), location);
    }

    private void selectLocation(String location) {
        Utils.selectOptionInDropDown(
                breadCrumbPanel.getLocationsDropDown(), breadCrumbPanel.getLocationsList(), location, true);
        Assert.assertTrue(isLocationSelected(location), "The location hasn't been selected");
    }

    public void closeLocationDropDown() {
        if (isLocationExpanded()) {
            Utils.clickElement(breadCrumbPanel.getLocationName());
        }
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(breadCrumbPanel.getLocationExpanded());
    }

    public boolean isLocationSelected(String location) {
        try {
            WaitUtilsWebDriver.getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions
                            .presenceOfElementLocated(By
                                    .xpath("//div[@class='add-notes-item menu-item active']//label[text()='" + location + "']")));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isLocationExpanded() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(breadCrumbPanel.getLocationExpanded(), 5).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    public int clearAndTypeLocation(String searchLocation) {
        Utils.clickElement(breadCrumbPanel.getLocationSearchInput());
        final int locationsNum = WaitUtilsWebDriver
                .waitForVisibilityOfAllOptions(breadCrumbPanel.getLocationLabels()).size();
        Utils.clearAndType(breadCrumbPanel.getLocationSearchInput(), searchLocation);
        return locationsNum;
    }

    public boolean isLocationSearched(String searchLocation) {
        try {
            WaitUtilsWebDriver.waitForVisibility(breadCrumbPanel.getLocationExpanded());
        } catch (Exception e) {
            Utils.clickElement(breadCrumbPanel.getLocationName());
        }
        final int locationsNum = clearAndTypeLocation(searchLocation);
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver -> breadCrumbPanel
                    .getLocationLabels()
                    .size() != locationsNum);
        } catch (Exception e) {
            WaitUtilsWebDriver.waitABit(2000);
        }

        return breadCrumbPanel.getLocationLabels()
                .stream()
                .allMatch(label -> label
                        .getText()
                        .toLowerCase()
                        .contains(searchLocation.toLowerCase()));
    }

    public void clickLocationInDropDown(String location) {
        breadCrumbPanel.getLocationLabels().stream().filter(loc -> loc.getText().contains(location))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForLoading();
    }
}