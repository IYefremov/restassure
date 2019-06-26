package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularCarHistoryWOsAndInvoicesScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility = "Invoices")
    private IOSElement invoicesmenu;

    @iOSXCUITFindBy(accessibility = "Work Orders")
    private IOSElement myworkordersmenumenu;

    public RegularCarHistoryWOsAndInvoicesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.xpath("//XCUIElementTypeButton[@name='viewTitle' and @label='History']")));
    }

    public RegularCarHistoryScreen clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
        return new RegularCarHistoryScreen();
    }

    public RegularMyInvoicesScreen clickCarHistoryInvoices() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(invoicesmenu)).click();
        return new RegularMyInvoicesScreen();
    }

    public void clickCarHistoryMyWorkOrders() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(myworkordersmenumenu)).click();
    }
}
