package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInspectionsScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class ServiceRequestdetailsScreen extends iOSHDBaseScreen {

    public ServiceRequestdetailsScreen(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Service Request")));
    }

    public TeamInspectionsScreen clickServiceRequestSummaryInspectionsButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("ServiceRequestSummaryInspectionsButton")));
        appiumdriver.findElementByAccessibilityId("ServiceRequestSummaryInspectionsButton").click();
        return new TeamInspectionsScreen(appiumdriver);
    }

    public ServiceRequestsScreen clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
        //TouchAction action = new TouchAction(appiumdriver);
        //action.press(appiumdriver.findElementByAccessibilityId("Back")).waitAction(waitOptions(ofSeconds(2))).release().perform();
        return new ServiceRequestsScreen(appiumdriver);
    }
}
