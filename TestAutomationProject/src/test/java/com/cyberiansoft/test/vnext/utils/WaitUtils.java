package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    public static void elementShouldBeVisible(WebElement element, Boolean shoulBeVisible) {
        WaitUtils.getGeneralFluentWait().
                until((webDriver) -> {
                    if (shoulBeVisible)
                        return element.isDisplayed();
                    else
                        return !element.isDisplayed();
                });
    }

    public static WebElement waitUntilElementIsClickable(final By locator) {
        WebElement element = DriverBuilder.getInstance().getAppiumDriver().findElement(locator);
        WaitUtils.waitUntilElementIsClickable(element);
        return element;
    }

    public static void waitUntilElementIsClickable(final By locator, AppiumDriver<MobileElement> appiumdriver) {
        WaitUtils.waitUntilElementIsClickable(locator);
    }

    public static WebElement waitUntilElementIsClickable(final WebElement webElement) {
        WaitUtils.getGeneralWebdriverWait().until(ExpectedConditions.elementToBeClickable(webElement));
        return webElement;
    }

    public static void click(final By locator) {
        WaitUtils.click(DriverBuilder.getInstance().getAppiumDriver().findElement(locator));
    }

    public static void click(final WebElement webElement) {
        WaitUtils.getGeneralFluentWait().until((webdriver) -> {
            webElement.click();
            return true;
        });
    }

    public static void waitUntilElementInvisible(final By locator) {
        WaitUtils.getGeneralWebdriverWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    //TODO: timeout and polling should be readed from some .prop file
    public static FluentWait<WebDriver> getGeneralFluentWait() {
        return
                new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofMillis(300))
                        .ignoring(WebDriverException.class);
    }

    public static WebDriverWait getGeneralWebdriverWait() {
        return new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 60);
    }
}
