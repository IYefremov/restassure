package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;

public class WaitUtilsWebDriver {

    public static WebDriverWait getWebDriverWait(int timeout) {
        return new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeout);
    }

    public static WebDriverWait getWait() {
        return getWebDriverWait(15);
    }

    public static WebDriverWait getShortWait() {
        return getWebDriverWait(5);
    }

    public static WebDriverWait getLongWait() {
        return getWebDriverWait(30);
    }

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
        return new FluentWait<>(DriverBuilder.getInstance().getDriver())
                .pollingEvery(pollingMillis)
                .withTimeout(timeoutSeconds)
                .ignoring(WebDriverException.class);
    }

    @Deprecated
    public static void waitForLoading() {
        try {
            waitForVisibility(VNextBOBaseWebPage.loadingProcess);
        } catch (Exception ignored) {}
        try {
            waitForInvisibility(VNextBOBaseWebPage.loadingProcess);
        } catch (Exception ignored) {}
    }

    public static void waitForSpinnerToDisappear() {
        try {
            getWebDriverWait(4).until(ExpectedConditions.visibilityOf(VNextBOBaseWebPage.spinner));
            WebElement spinner = DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[contains(@class, 'k-loading-image')]"));
            getWait().until(ExpectedConditions.invisibilityOf(spinner));
        } catch (Exception ex) {

        }

    }

    public static WebElement waitForVisibility(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebElement element, int timeout) {
        return new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeout).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By xpath) {
        return getWait().until(ExpectedConditions.visibilityOf(DriverBuilder.getInstance().getDriver().findElement(xpath)));
    }

    public static void waitForInvisibility(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForInvisibility(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeoutInSeconds).until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForInvisibility(By by) {
        getWait().until(ExpectedConditions.invisibilityOf(DriverBuilder.getInstance().getDriver().findElement(by)));
    }

    public static void waitForInvisibility(By by, int timeoutInSeconds) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOf(driver.findElement(by)));
    }

    public static void waitForInvisibilityIgnoringException(WebElement element) {
        try {
            getWait().until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception ignored) {}
    }

    public static void waitForInvisibilityIgnoringException(WebElement element, int timeoutSeconds) {
        try {
            new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeoutSeconds).until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception ignored) {}
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementToBeClickable(WebElement element, int timeoutSeconds) {
        return new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForNewTab() {
        try {
            getLongWait().until((ExpectedCondition<Boolean>) driver -> (
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
                    .until(DriverBuilder.getInstance().getDriver()::getCurrentUrl);
        } catch (ConditionTimeoutException ignored) {}
    }

    public static List<WebElement> waitForVisibilityOfAllOptions(List<WebElement> listBox) {
        getShortWait().until(ExpectedConditions.visibilityOfAllElements(listBox));
        return listBox;
    }

    public static List<WebElement> waitForVisibilityOfAllOptions(List<WebElement> listBox, int timeoutSeconds) {
        new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeoutSeconds).until(ExpectedConditions.visibilityOfAllElements(listBox));
        return listBox;
    }

    public static List<WebElement> waitForVisibilityOfAllOptionsIgnoringException(List<WebElement> listBox) {
        try {
            waitForVisibilityOfAllOptions(listBox);
            return listBox;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<WebElement> waitForVisibilityOfAllOptionsIgnoringException(List<WebElement> listBox, int timeoutSeconds) {
        try {
            waitForVisibilityOfAllOptions(listBox, timeoutSeconds);
            return listBox;
        } catch (Exception ignored) {
            return null;
        }
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
            WaitUtilsWebDriver.waitForAttributeToBe(dropDown, "aria-hidden", "false", 2);
        } catch (Exception ignored) {}
    }

    public static void waitForDropDownToBeClosed(WebElement dropDown) {
        waitForDropDownToBeClosed(dropDown, 5);
    }

    public static void waitForDropDownToBeClosed(WebElement dropDown, int timeout) {
        try {
            waitForElementNotToBeStale(dropDown, timeout);
            waitForAttributeToBe(dropDown, "aria-hidden", "true");
        } catch (Exception ignored) {}
    }

    public static void waitForListToDisappear(WebElement list) {
        try {
            waitForAttributeToBe(list, "aria-hidden", "true");
        } catch (Exception e) {
            try {
                Utils.getActions().moveToElement(list).sendKeys(Keys.ENTER).build().perform();
                waitForAttributeToBe(list, "aria-hidden", "true");
            } catch (Exception ignored) {
                waitABit(1000);
            }
        }
    }

    public static boolean waitForAttributeToBe(WebElement element, String attribute, String value) {
        return getWait().until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebElement element, String attribute, String value, int timeout) {
        return getWebDriverWait(timeout).until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static boolean waitForAttributeToContain(WebElement element, String attribute, String value) {
        return getWait().until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public static boolean waitForAttributeToContain(WebElement element, String attribute, String value, int timeout) {
        return getWebDriverWait(timeout).until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public static boolean waitForAttributeNotToContain(WebElement element, String attribute, String value, int timeout) {
        return getWebDriverWait(timeout)
                .until(ExpectedConditions.not(ExpectedConditions.attributeContains(element, attribute, value)));
    }

    public static boolean waitForAttributeToContainIgnoringException(WebElement element, String attribute, String value) {
        try {
            return waitForAttributeToContain(element, attribute, value);
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean waitForAttributeToContainIgnoringException(WebElement element, String attribute, String value, int timeOut) {
        try {
            return waitForAttributeToContain(element, attribute, value, timeOut);
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void waitForInputFieldValueIgnoringException(WebElement element, String value) {
        try {
            new WebDriverWait(DriverBuilder.getInstance().getDriver(), 10)
                    .until(ExpectedConditions.attributeToBe(element, "value", value));
        } catch (Exception e) {}
    }

    public static void waitForTextToBePresentInElement(WebElement element, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void waitForTextToBePresentInElement(WebElement element, String text, int timeOut) {
        getWebDriverWait(timeOut).until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static WebElement waitForElementNotToBeStale(WebElement element) {
        return waitForElementNotToBeStale(element, 10);
    }

    public static WebElement waitForElementNotToBeStale(WebElement element, int timeOut) {
        try {
            getWebDriverWait(timeOut).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
        } catch (Exception ignored) {}
        return element;
    }

    public static WebElement waitForElementNotToBeStale(By by) {
        return waitForElementNotToBeStale(DriverBuilder.getInstance().getDriver().findElement(by));
    }

    public static void waitForPendingRequestsToComplete() {

        boolean requestsAreCompleted = false;
        try {
            do {
                requestsAreCompleted = (boolean) ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("return angular.element(document.body).injector().get('$http').pendingRequests.length === 0");
            } while (!requestsAreCompleted);
        } catch (Exception ignored) {
            waitABit(1500);
        }
    }

    public static void waitUntilPageIsLoadedWithJs() {
        new WebDriverWait(DriverBuilder.getInstance().getDriver(), 5).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForNumberOfElementsToBe(By by, int elementsNumber) {
        getWait().until(ExpectedConditions.numberOfElementsToBe(by, elementsNumber));
    }

    public static boolean elementShouldBeVisible(WebElement element, boolean shouldBeVisible) {
        return elementShouldBeVisible(element, shouldBeVisible, 15);
    }

    public static boolean elementShouldBeVisible(By by, boolean shouldBeVisible) {
        return elementShouldBeVisible(DriverBuilder.getInstance().getDriver().findElement(by), shouldBeVisible, 15);
    }

    public static boolean elementShouldBeVisible(By by, boolean shouldBeVisible, int timeOut) {
        return elementShouldBeVisible(DriverBuilder.getInstance().getDriver().findElement(by), shouldBeVisible, timeOut);
    }

    public static boolean elementShouldBeVisible(WebElement element, boolean shouldBeVisible, int timeOut) {
        try {
            return getWebDriverWait(timeOut)
                    .until((webDriver) -> {
                        if (shouldBeVisible)
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
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean elementShouldBeClickable(WebElement element, boolean condition, int timeOut) {
        final WebDriverWait wait = WaitUtilsWebDriver.getWebDriverWait(timeOut);
        if (condition) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}