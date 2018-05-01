package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularPriceMatricesScreen extends iOSRegularBaseScreen {

    public RegularPriceMatricesScreen(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Price Matrices")));
    }

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Back"))).click();
    }
}
