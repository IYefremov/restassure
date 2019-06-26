package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseTypeScreenWithTabs extends BaseTypeScreen {

    public BaseTypeScreenWithTabs() {
        super();
    }

    public void switchToMyView() {
        appiumdriver.findElementByAccessibilityId("My").click();
    }

    public void switchToTeamView() {
        appiumdriver.findElementByAccessibilityId("Team").click();
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
        if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
            wait = new WebDriverWait(appiumdriver, 10);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
        }
    }
}
