package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOLocationComponent extends VNextBOBaseWebPage {

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
    private WebElement locationExpanded;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']/ul[@class='scroll-pane-locations']//label")
    private List<WebElement> locationLabels;

    @FindBy(id = "locSearchInput")
    private WebElement locationSearchInput;

    @FindBy(xpath = "//span[contains(@class, 'location-name')]")
    private WebElement locationElement;

    public VNextBOLocationComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public VNextBOBaseWebPage setLocation(String location) {
        if (isLocationExpanded()) {
            selectLocation(location);
        } else {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locationElement)).click();
            } catch (Exception ignored) {
                waitABit(2000);
                wait.until(ExpectedConditions.elementToBeClickable(locationElement)).click();
            }
            selectLocation(location);
        }
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
            wait.until(ExpectedConditions.textToBePresentInElement(locationElement, location));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private void selectLocation(String location) {
        wait.until(ExpectedConditions
                .elementToBeClickable(locationExpanded.findElement(By.xpath(".//label[text()='" + location + "']"))))
                .click();
        waitForLoading();
        Assert.assertTrue(isLocationSelected(location), "The location hasn't been selected");
        closeLocationDropDown();
    }

    public void closeLocationDropDown() {
        try {
            wait.until(ExpectedConditions.invisibilityOf(locationExpanded));
        } catch (Exception e) {
            locationElement.click();
            wait.until(ExpectedConditions.invisibilityOf(locationExpanded));
        }
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
            return waitShort.until(ExpectedConditions.visibilityOf(locationExpanded)).isDisplayed();
        } catch (Exception ignored) {
            return false;
        }
    }

    public int clearAndTypeLocation(String searchLocation) {
        wait.until(ExpectedConditions.elementToBeClickable(locationSearchInput)).click();
        final int locationsNum = wait.until(ExpectedConditions.visibilityOfAllElements(locationLabels)).size();
        clearAndType(locationSearchInput, searchLocation);
        return locationsNum;
    }

    public boolean isLocationSearched(String searchLocation) {
        try {
            wait.until(ExpectedConditions.visibilityOf(locationExpanded));
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(locationElement)).click();
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