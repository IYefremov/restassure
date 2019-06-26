package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularPriceMatricesScreen extends iOSRegularBaseScreen {

    public RegularPriceMatricesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Price Matrices")));
    }

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Back"))).click();
    }

    public RegularPriceMatrixScreen selectPriceMatrice(String pricematrice) {
        if (!appiumdriver.findElementByName(pricematrice).isDisplayed()) {
            swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + pricematrice + "']/..")));
        }
        appiumdriver.findElement(MobileBy.AccessibilityId(pricematrice)).click();
        return new RegularPriceMatrixScreen();
    }
}
