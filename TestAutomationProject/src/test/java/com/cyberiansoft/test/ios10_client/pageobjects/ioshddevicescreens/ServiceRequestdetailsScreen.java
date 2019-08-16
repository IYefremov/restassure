package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServiceRequestdetailsScreen extends iOSHDBaseScreen {

    public ServiceRequestdetailsScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

    }

    public void waitServiceRequestdetailsScreenLoad() {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Service Request")));
    }

    public TeamInspectionsScreen clickServiceRequestSummaryInspectionsButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ServiceRequestSummaryInspectionsButton")));
        appiumdriver.findElementByAccessibilityId("ServiceRequestSummaryInspectionsButton").click();
        return new TeamInspectionsScreen();
    }

    public TeamWorkOrdersScreen clickServiceRequestSummaryOrdersButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Work Orders")));
        appiumdriver.findElementByAccessibilityId("Work Orders").click();
        return new TeamWorkOrdersScreen();
    }

    public ServiceRequestsScreen clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
        //TouchAction action = new TouchAction(appiumdriver);
        //action.press(appiumdriver.findElementByAccessibilityId("Back")).waitAction(waitOptions(ofSeconds(2))).release().perform();
        return new ServiceRequestsScreen();
    }
}
