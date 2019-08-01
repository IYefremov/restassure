package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class WaitUtilsWebDriver {

    private static WebDriver driver = DriverBuilder.getInstance().getDriver();

    public final static WebDriverWait wait = new WebDriverWait(driver, 15);
    public final static WebDriverWait waitShort = new WebDriverWait(driver, 5);
    public final static WebDriverWait waitLong = new WebDriverWait(driver, 30);
    public final static Actions actions = new Actions(driver);

    public static void waitABit(int milliSeconds) {
        if (milliSeconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliSeconds);
            } catch (InterruptedException ignored) {}
        }
    }

    public static FluentWait<WebDriver> getFluentWait() {
        return getFluentWait(Duration.ofMillis(500), Duration.ofSeconds(15));
    }

    public static FluentWait<WebDriver> getFluentWait(Duration pollingMillis, Duration timeoutSeconds) {
        return new FluentWait<>(driver)
                .pollingEvery(pollingMillis)
                .withTimeout(timeoutSeconds)
                .ignoring(WebDriverException.class);
    }

    public static void waitForLoading() {
        try {
            waitForVisibility(VNextBOBaseWebPage.loadingProcess);
        } catch (Exception ignored) {}
        try {
            waitForInvisibility(VNextBOBaseWebPage.loadingProcess);
        } catch (Exception ignored) {}
        waitABit(2000);
    }

    public static void waitForSpinnerToDisappear() {
            waitForVisibilityIgnoringException(VNextBOBaseWebPage.spinner);
        try {
            waitForInvisibility(VNextBOBaseWebPage.spinner);
        } catch (Exception ignored) {}
        waitABit(2000);
    }

    public static WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebElement element, int timeout) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForInvisibilityIgnoringException(WebElement element) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception ignored) {}
    }

    public static void waitForInvisibilityIgnoringException(WebElement element, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception ignored) {}
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementToBeClickable(WebElement element, int timeoutSeconds) {
        return new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForNewTab() {
        try {
            waitLong.until((ExpectedCondition<Boolean>) driver -> (
                    Objects
                            .requireNonNull(driver)
                            .getWindowHandles()
                            .size() != 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitForUrl() {
        try {
            await().atMost(15, TimeUnit.SECONDS)
                    .ignoreExceptions()
                    .pollInterval(500, TimeUnit.MILLISECONDS)
                    .until(driver::getCurrentUrl);
        } catch (ConditionTimeoutException ignored) {}
    }

    public static void waitForVisibilityOfAllOptions(List<WebElement> listBox) {
        waitShort.until(ExpectedConditions.visibilityOfAllElements(listBox));
    }

    public static void waitForVisibilityOfAllOptions(List<WebElement> listBox, int timeoutSeconds) {
        new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.visibilityOfAllElements(listBox));
    }

    public static void waitForVisibilityOfAllOptionsIgnoringException(List<WebElement> listBox) {
        try {
            waitForVisibilityOfAllOptions(listBox);
        } catch (Exception ignored) {}
    }

    public static void waitForVisibilityOfAllOptionsIgnoringException(List<WebElement> listBox, int timeoutSeconds) {
        try {
            waitForVisibilityOfAllOptions(listBox, timeoutSeconds);
        } catch (Exception ignored) {}
    }

    public static void waitForVisibilityIgnoringException(WebElement element) {
        try {
            waitForVisibility(element);
        } catch (Exception ignored) {}
    }

    public static void waitForVisibilityIgnoringException(WebElement element, int timeoutSeconds) {
        try {
            waitForVisibility(element, timeoutSeconds);
        } catch (Exception ignored) {}
    }

    public static void waitForDropDownToBeOpened(WebElement dropDown) {
        try {
            wait.until(ExpectedConditions.attributeToBe(dropDown, "aria-hidden", "false"));
        } catch (Exception ignored) {}
    }

    public static void waitForDropDownToBeClosed(WebElement dropDown) {
        try {
            waitShort.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.attributeToBe(dropDown, "aria-hidden", "true"));
        } catch (Exception ignored) {
            waitABit(1000);
        }
    }

    public static void waitForListToDisappear(WebElement list) {
        try {
            waitForAttributeToBe(list, "aria-hidden", "true");
        } catch (Exception e) {
            try {
                actions.moveToElement(list).sendKeys(Keys.ENTER).build().perform();
                waitForAttributeToBe(list, "aria-hidden", "true");
            } catch (Exception ignored) {
                waitABit(1000);
            }
        }
    }

    private static Boolean waitForAttributeToBe(WebElement list, String attribute, String value) {
        return wait.until(ExpectedConditions.attributeToBe(list, attribute, value));
    }
}