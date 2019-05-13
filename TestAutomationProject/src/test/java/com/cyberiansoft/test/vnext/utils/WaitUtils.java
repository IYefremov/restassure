package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitUtils {
    public static void elementShouldBeVisible(WebElement element, Boolean shoulBeVisible){
        FluentWait wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class)
                .ignoring(InvalidElementStateException.class);
        wait.until((webDriver) -> {
            if (shoulBeVisible)
                return element.isDisplayed();
            else
                return !element.isDisplayed();
        });
    }

    public static WebElement waitUntilElementIsClickable(final By locator) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class)
                .ignoring(InvalidElementStateException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return element;
    }

    ;

    public static WebElement waitUntilElementIsClickable(final By locator, AppiumDriver<MobileElement> appiumdriver) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(appiumdriver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class)
                .ignoring(InvalidElementStateException.class);

        WebElement element = wait.until(driver -> driver.findElement(locator));

        return element;
    }

    ;

    public static WebElement waitUntilElementIsClickable(final WebElement webElement) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class);

        WebElement element = wait.until(driver -> webElement);

        return element;
    }

    ;

    public static WebElement waitUntilElementIsPresent(final WebElement webElement) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });

        return element;
    }

    ;

    public static void click(final By locator) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class)
                .ignoring(InvalidElementStateException.class);

        Boolean element = wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                driver.findElement(locator).click();
                return true;
            }
        });
    }

    ;

    public static void click(final WebElement webElement) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class);

        Boolean element = wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                webElement.click();
                return true;
            }
        });
    }

    ;

    public static void waitUntilElementInvisible(final By locator) {
        DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 30).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}
