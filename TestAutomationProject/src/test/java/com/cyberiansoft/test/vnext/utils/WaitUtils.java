package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WaitUtils {

    private static final int durationInSeconds = 45;
    private static final int pullingIntervalInMils = 500;

    public static void collectionSizeIsGreaterThan(List<?> list, Integer expectedSize) {
        WaitUtils.getGeneralFluentWait().until(driver -> list.size() > expectedSize);
    }

    public static boolean isElementPresent(By locator) {
        try {
            return getGeneralFluentWait()
                    .withTimeout(Duration.ofSeconds(2))
                    .until((appiumDriver) -> appiumDriver.findElement(locator).isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementPresent(WebElement webElement) {
        try {
            return getGeneralFluentWait()
                    .withTimeout(Duration.ofSeconds(2))
                    .until((appiumDriver) -> webElement.isDisplayed());
        } catch (ElementNotFoundException | StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            return false;
        }
    }

    public static void elementShouldBeVisible(WebElement element, Boolean shoulBeVisible) {
        WaitUtils.getGeneralFluentWait()
                .withTimeout(Duration.ofSeconds(60))
                .until((webDriver) -> {
                    if (shoulBeVisible)
                        try {
                            return element.isDisplayed();
                        } catch (NoSuchElementException ex) {
                            return false;
                        }
                    else {
                        try {
                            return !element.isDisplayed();
                        } catch (NoSuchElementException ex) {
                            return true;
                        }
                    }
                });
    }

    public static WebElement waitUntilElementIsClickable(final By locator) {
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.elementToBeClickable(locator));
        return ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(locator);
    }

    public static void waitUntilElementIsClickable(final By locator, WebDriver appiumdriver) {
        WaitUtils.waitUntilElementIsClickable(locator);
    }

    public static WebElement waitUntilElementIsClickable(final WebElement webElement) {
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.elementToBeClickable(webElement));
        return webElement;
    }

    //click(Webelement) cannot be called in this case, because element will not be searched again after fail, because its not proxy
    //TODO: convert locator to PROXY web element and call click(Webelement)
    public static void click(final By locator) {
        WaitUtils.getGeneralFluentWait().until((webdriver) -> {
            ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(locator).click();
            return true;
        });
    }

    public static void click(final WebElement webElement) {
        WaitUtils.getGeneralFluentWait().until((webdriver) -> {
            webElement.click();
            return true;
        });
    }

    public static void waitUntilElementInvisible(final By locator) {
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void assertEquals(Object expected, Object actual) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(expected,
                    actual
            );
            return true;
        });
    }

    //TODO: timeout and polling should be readed from some .prop file
    public static FluentWait<WebDriver> getGeneralFluentWait() {
        return
                new FluentWait<>(ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                        .withTimeout(Duration.ofSeconds(durationInSeconds))
                        .pollingEvery(Duration.ofMillis(pullingIntervalInMils))
                        .ignoring(ElementClickInterceptedException.class)
                        .ignoring(WebDriverException.class)
                        .ignoring(AssertionError.class)
                        .ignoring(StaleElementReferenceException.class)
                        .ignoring(RuntimeException.class);
    }

    public static FluentWait<WebDriver> getGeneralFluentWait(int timeout, int pollingEvery) {
        return
                new FluentWait<>(ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                        .withTimeout(Duration.ofSeconds(timeout))
                        .pollingEvery(Duration.ofMillis(pollingEvery))
                        .ignoring(ElementClickInterceptedException.class)
                        .ignoring(WebDriverException.class)
                        .ignoring(AssertionError.class)
                        .ignoring(StaleElementReferenceException.class)
                        .ignoring(RuntimeException.class);
    }
}
