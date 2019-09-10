package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularBaseTypeScreenWithTabs extends RegularBaseTypeScreen  {

    public RegularBaseTypeScreenWithTabs() {
        super();
    }

    public void switchToMyView() {
        appiumdriver.findElementByAccessibilityId("My").click();
    }

    public void switchToTeamView() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Team"))).click();
        if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
            wait = new WebDriverWait(appiumdriver, 10);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
        }
    }
}
