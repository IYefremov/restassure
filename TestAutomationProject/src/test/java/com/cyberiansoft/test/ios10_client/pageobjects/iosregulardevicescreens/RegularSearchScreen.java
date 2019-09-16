package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularSearchScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility = "Save")
    private IOSElement saveBtn;

    @iOSXCUITFindBy(className = "XCUIElementTypeSearchField")
    private IOSElement searchFld;


    public RegularSearchScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void searchStatus(String statusValue) {
        appiumdriver.findElementByAccessibilityId("Status").click();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(statusValue)));
        wait = new WebDriverWait(appiumdriver, 10);
        WebElement statusCell = wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(statusValue)));
        statusCell.click();
    }

    public void setSearchText(String searchText) {
        searchFld.clear();
        searchFld.sendKeys(searchText);
    }

    public void clickSearchType() {
        appiumdriver.findElementByAccessibilityId("Type").click();
    }

    public void clickSearchCustomer() {
        appiumdriver.findElementByAccessibilityId("Customer").click();
    }

    public void saveSearchDialog() {
        saveBtn.click();
    }
}
