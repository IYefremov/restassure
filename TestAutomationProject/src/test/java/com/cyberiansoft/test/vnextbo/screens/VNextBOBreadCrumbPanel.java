package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOBreadCrumbPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
    private WebElement locationExpanded;

    @FindBy(xpath = "//ul[@class='scroll-pane-locations']//label")
    private List<WebElement> locationsList;

    @FindBy(xpath = "//div[@class='drop department-drop']")
    private WebElement locationsDropDown;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']/ul[@class='scroll-pane-locations']//label")
    private List<WebElement> locationLabels;

    @FindBy(id = "locSearchInput")
    private WebElement locationSearchInput;

    @FindBy(xpath = "//span[contains(@class, 'location-name')]")
    private WebElement locationName;

    @FindBy(className = "breadcrumbs")
    private WebElement mainBreadCrumbsLink;

    @FindBy(xpath = "//strong[contains(@data-bind, 'breadcrumb.last')]")
    private WebElement lastBreadCrumb;

    public VNextBOBreadCrumbPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isMainBreadCrumbClickable() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(mainBreadCrumbsLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLastBreadCrumbDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(lastBreadCrumb));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public String getLastBreadCrumbText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(lastBreadCrumb)).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public boolean isBreadCrumbClickable() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(mainBreadCrumbsLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public VNextBOBaseWebPage setLocation(String location) {
        if (!isLocationExpanded()) {
            Utils.clickElement(locationName);
        }
        selectLocation(location);
        return this;
    }

    public VNextBOBaseWebPage setLocation(String location, boolean isSetWithEnter) {
        if (isSetWithEnter) {
            System.out.println("isSetWithEnter");
            isLocationSearched(location);
            actions.sendKeys(Keys.ENTER);
            setLocation(location);
        } else {
            setLocation(location);
        }
        return this;
    }

    public boolean isLocationSet(String location) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(locationName, location));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private void selectLocation(String location) {
        Utils.selectOptionInDropDown(locationsDropDown, locationsList, location, true);
        Assert.assertTrue(isLocationSelected(location), "The location hasn't been selected");
    }

    public void closeLocationDropDown() {
        if (isLocationExpanded()) {
            Utils.clickElement(locationName);
        }
        try {
            WaitUtilsWebDriver.waitForInvisibility(locationExpanded);
        } catch (Exception ignored) {}
    }

    public boolean isLocationSelected(String location) {
        try {
            wait
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
            return WaitUtilsWebDriver.waitForVisibility(locationExpanded, 5).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    public int clearAndTypeLocation(String searchLocation) {
        Utils.clickElement(locationSearchInput);
        final int locationsNum = WaitUtilsWebDriver.waitForVisibilityOfAllOptions(locationLabels).size();
        clearAndType(locationSearchInput, searchLocation);
        return locationsNum;
    }

    public boolean isLocationSearched(String searchLocation) {
        try {
            WaitUtilsWebDriver.waitForVisibility(locationExpanded);
        } catch (Exception e) {
            Utils.clickElement(locationName);
        }
        final int locationsNum = clearAndTypeLocation(searchLocation);
        try {
            waitShort.until((ExpectedCondition<Boolean>) driver -> locationLabels.size() != locationsNum);
        } catch (Exception e) {
            waitABit(2000);
        }

        return locationLabels
                .stream()
                .allMatch(label -> label
                        .getText()
                        .toLowerCase()
                        .contains(searchLocation.toLowerCase()));
    }

    public VNextBOBaseWebPage clickLocationInDropDown(String location) {
        locationLabels.stream().filter(loc -> loc.getText().contains(location)).findFirst().ifPresent(WebElement::click);
        waitForLoading();
        return this;
    }
}