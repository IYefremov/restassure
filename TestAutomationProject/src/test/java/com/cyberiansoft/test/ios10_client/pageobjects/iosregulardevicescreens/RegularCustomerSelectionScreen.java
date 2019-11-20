package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularBaseAppScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularCustomerSelectionScreen extends RegularBaseAppScreen {

    public RegularCustomerSelectionScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    @iOSXCUITFindBy(accessibility = "CustomerSelector")
    private IOSElement customerSelectorNevBar;

    @iOSXCUITFindBy(accessibility = "CustomerSelectorTable")
    private IOSElement customerSelectorTable;

    @iOSXCUITFindBy(accessibility = "Toolbar")
    private IOSElement toolbar;


    public void selectCustomer(AppCustomer appCustomer) {

        if (!customerSelectorTable.findElementByAccessibilityId(appCustomer.getFullName()).isDisplayed()) {
            customerSelectorNevBar.findElementByAccessibilityId("Search").click();
            WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSearchField"))).sendKeys(appCustomer.getFullName());
        }
        customerSelectorTable.findElementByAccessibilityId(appCustomer.getFullName()).click();
    }

    public void switchToWholeSaleMode() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("CustomerSelectorTable")));
        if (toolbar.findElementsByAccessibilityId("btnRetail").size() > 0)
            toolbar.findElementByAccessibilityId("btnRetail").click();
    }

    public void switchToRetailMode() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("CustomerSelectorTable")));
        if (toolbar.findElementsByAccessibilityId("btnWholesale").size() > 0)
            toolbar.findElementByAccessibilityId("btnWholesale").click();
    }

}
