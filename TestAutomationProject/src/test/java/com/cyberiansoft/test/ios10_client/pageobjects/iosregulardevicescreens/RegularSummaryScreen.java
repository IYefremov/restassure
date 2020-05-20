package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularSummaryScreen extends iOSRegularBaseScreen {

    public RegularSummaryScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public boolean isSummaryPDFExists() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.className("XCUIElementTypeScrollView")));
        return appiumdriver.findElementsByAccessibilityId("Generating PDF file...").size() > 0;
    }
}
