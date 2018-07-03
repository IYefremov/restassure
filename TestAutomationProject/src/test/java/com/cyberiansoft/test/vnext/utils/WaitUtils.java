package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitUtils {

    public static WebElement waitUntilElementIsClickable(final By locator) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return  element;
    };

    public static WebElement waitUntilElementIsClickable(final WebElement webElement) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });

        return  element;
    };

    public static void click(final By locator) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverBuilder.getInstance().getAppiumDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(WebDriverException.class);

        Boolean element = wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                driver.findElement(locator).click();
                return true;
            }
        });
    };

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
    };

    public static void waitUntilElementInvisible(final By locator) {
        DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 30).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
