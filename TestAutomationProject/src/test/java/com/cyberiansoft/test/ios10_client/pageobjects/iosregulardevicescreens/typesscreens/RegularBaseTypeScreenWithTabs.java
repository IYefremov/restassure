package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        if (appiumdriver.findElements(MobileBy.AccessibilityId("Close")).size() > 0)
            appiumdriver.findElement(MobileBy.AccessibilityId("Close")).click();
    }
}
