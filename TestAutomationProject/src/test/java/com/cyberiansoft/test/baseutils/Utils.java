package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.waitForDropDownToBeOpened;
import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.waitForElementToBeClickable;

public class Utils {

    private static Actions actions = null;

    public static Actions getActions() {
        if (actions == null) {
            return new Actions(DriverBuilder.getInstance().getDriver());
        }
        return actions;
    }
    
    public static void clickElement(WebElement element) {
        try {
            waitForElementToBeClickable(element).click();
        } catch (Exception ignored) {
            WaitUtilsWebDriver.waitABit(500);
            clickWithJS(element);
        }
    }

    public static void clickElement(WebElement element, int timeOutInSeconds) {
        try {
            WaitUtilsWebDriver.waitForElementToBeClickable(element, timeOutInSeconds).click();
        } catch (Exception ignored) {
            clickWithJS(element);
        }
    }

    public static void clickElement(By by) {
        clickElement(DriverBuilder.getInstance().getDriver().findElement(by));
    }

    public static void clickWithActions(WebElement element) {
        try {
            moveToElement(element);
            getActions()
                    .click()
                    .build()
                    .perform();
        } catch (Exception ignored) {
            scrollToElement(element);
            clickElement(element);
        }
    }

    public static void clickWithActions(By by) {
        WebElement element = DriverBuilder.getInstance().getDriver().findElement(by);
        clickWithActions(element);
    }

    public static void clearAndType(WebElement element, String name) {
        clear(element);
        Utils.getActions().sendKeys(element, name).build().perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void sendKeysWithEnter(WebElement element, String name) {
        clear(element);
        Utils.getActions().sendKeys(element, name).sendKeys(Keys.ENTER).build().perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void clear(WebElement element) {
        try {
            waitForElementToBeClickable(element).clear();
        } catch (Exception e) {
            try {
                scrollToElement(element);
                waitForElementToBeClickable(element).clear();
            } catch (Exception ignored) {
                Assert.fail("The text field has not been displayed", e);
            }
        }
    }

    public static void clearAndType(By by, String name) {
        final WebElement element = DriverBuilder.getInstance().getDriver().findElement(by);
        clearAndType(element, name);
    }

    public static void clearAndTypeUsingKeyboard(WebElement element, String name) {
        scrollToElement(element);
        Utils.clickElement(element);
        Utils.getActions()
                .sendKeys(element, Keys.HOME)
                .sendKeys(element, Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END))
                .sendKeys(element, Keys.DELETE)
                .sendKeys(element, name)
                .build()
                .perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("arguments[0].click();", element);
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void setZoom(int zoomPercentage) {
        ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("document.body.style.zoom='" + zoomPercentage + "%'", "");
    }

    public static WebElement moveToElement(WebElement element) {
        getActions().moveToElement(element).build().perform();
        return element;
    }

    public static WebElement moveToElement(By by) {
        final WebElement element = DriverBuilder.getInstance().getDriver().findElement(by);
        getActions().moveToElement(element).build().perform();
        return element;
    }

    public static void refreshPage() {
        DriverBuilder.getInstance().getDriver().navigate().refresh();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void reduceZoom() {
        getActions().sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT)).perform();
    }

    public static void increaseZoom() {
        getActions().sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD)).perform();
    }

    public static void goToPreviousPage() {
        DriverBuilder.getInstance().getDriver().navigate().back();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox, String selection) {
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(listBox);
        final Actions actions = getActions();
        getMatchingOptionInListBox(listBox, selection)
                .ifPresent((option) -> {
                    actions.moveToElement(option).build().perform();
                    Utils.clickElement(option);
                });
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown);
    }

    public static void selectOptionInDropDownWithJs(WebElement dropDown, WebElement option) {
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibility(option);
        Utils.clickWithJS(option);
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown);
    }

    public static void selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox, String selection, boolean draggable) {
        if (draggable) {
            waitForDropDownToBeOpened(dropDown);
            WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(listBox);
            getMatchingOptionInListBox(listBox, selection)
                    .ifPresent((option) -> {
                        scrollListBoxDownWhileElementIsNotDisplayed(dropDown, listBox, option);
                        Utils.clickElement(option, 5);
                    });
            WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown);
        } else {
            selectOptionInDropDown(dropDown, listBox, selection);
        }
    }

    private static Optional<WebElement> getMatchingOptionInListBox(List<WebElement> listBox, String selection) {
        return listBox
                .stream()
                .filter((option) -> {
                    WaitUtilsWebDriver
                            .getWait()
                            .ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.not(ExpectedConditions.stalenessOf(option)));
                    return option.getText().equals(selection);
                })
                .findFirst();
    }

    private static void scrollListBoxDownWhileElementIsNotDisplayed(WebElement dropDown, List<WebElement> listBox, WebElement option) {
        for (int i = 0; i < listBox.size(); i++) {
            if (Utils.isElementNotDisplayed(option, 5)) {
                dropDown.sendKeys(Keys.ARROW_DOWN);
            } else {
                break;
            }
        }
    }

    public static String getNewTab(String mainWindow) {
        for (String activeHandle : DriverBuilder.getInstance().getDriver().getWindowHandles()) {
            if (!activeHandle.equals(mainWindow)) {
                DriverBuilder.getInstance().getDriver().switchTo().window(activeHandle);
                return activeHandle;
            }
        }
        return "";
    }

    public static String getParentTab() {
        return DriverBuilder.getInstance().getDriver().getWindowHandle();
    }

    public static String selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox) {
        final int minOptionNumber = 1;
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(listBox);
        final int random = RandomUtils.nextInt(minOptionNumber, listBox.size());
        String selectedText = listBox.get(random).getText();
        System.out.println("random value: " + random + "\n" + "selected option: " + selectedText);
        selectOptionInDropDown(dropDown, listBox, selectedText, true);
        return selectedText;
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            WaitUtilsWebDriver.waitForVisibility(element);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isElementDisplayed(By by) {
        try {
            WaitUtilsWebDriver.waitForVisibility(DriverBuilder.getInstance().getDriver().findElement(by));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isElementDisplayed(WebElement element, int timeoutInSeconds) {
        try {
            WaitUtilsWebDriver.waitForVisibility(element, timeoutInSeconds);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isElementNotDisplayed(WebElement element) {
        try {
            WaitUtilsWebDriver.waitForInvisibility(element);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isElementNotDisplayed(WebElement element, int timeoutSeconds) {
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(500), Duration.ofSeconds(timeoutSeconds))
                    .ignoring(NoSuchElementException.class)
                    .until(ExpectedConditions.invisibilityOf(element));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean areElementsDisplayed(List<WebElement> elements) {
        try {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(elements);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean areElementsDisplayed(List<WebElement> elements, int timeoutSeconds) {
        try {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(elements, timeoutSeconds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDataDisplayed(List<WebElement> listBox, String data) {
        try {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox, 10);
            return listBox
                    .stream()
                    .map(WebElement::getText)
                    .anyMatch(option -> option.equals(data));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isElementClickable(WebElement element) {
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementClickable(WebElement element, int timeoutSeconds) {
        try {
            new WebDriverWait(DriverBuilder.getInstance().getDriver(), timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getInputFieldValue(WebElement inputField) {
        try {
            WaitUtilsWebDriver.waitForVisibility(inputField);
            return inputField.getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getInputFieldValue(WebElement inputField, int timeOut) {
        try {
            WaitUtilsWebDriver.waitForVisibility(inputField, timeOut);
            return inputField.getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void selectDataFromBoxList(List<WebElement> listBox, WebElement list, String data) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(listBox);
        for (WebElement selected : listBox) {
            if (selected.getText().equals(data)) {
                getActions().moveToElement(selected)
                        .click()
                        .build()
                        .perform();
                break;
            }
        }
        WaitUtilsWebDriver.waitForListToDisappear(list);
    }

    public static void closeMainWindow(String mainWindow) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.switchTo().window(mainWindow);
        driver.close();
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void closeWindows() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        final Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            driver.switchTo().window(window);
            driver.close();
            WaitUtilsWebDriver.waitABit(1000);
        }
    }

    public static void closeNewWindow(String mainWindow) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        final Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                driver.close();
                WaitUtilsWebDriver.waitABit(1000);
            }
        }
    }

    public static void executeJsForAddOnSettings() {
        ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("localStorage.setItem('addons_selfSubscribedFeatures', JSON.stringify([{\"featureID\":17,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Labor Times\",\"reconProFeatureName\":\"LaborHoursByEmployeeReport\",\"pricePerMonth\":1,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:37:05.983\"},{\"featureID\":41,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Basic Services ($$, %%, Discounts, Taxes)\",\"reconProFeatureName\":\"BasicParts\",\"pricePerMonth\":0.99,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:40:25.92\"},{\"featureID\":37,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Question Forms\",\"reconProFeatureName\":\"AccessClinetInfo\",\"pricePerMonth\":99999999.22,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T03:39:25.673\"},{\"featureID\":38,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Configurable email templates for inspections/estimates and invoices +1234566 test long name long name\",\"reconProFeatureName\":\"WorkOrders\",\"pricePerMonth\":1.55,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-30T08:31:18.337\"},{\"featureID\":80,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"QuickBooks Online Accounting System (Additional) test test\",\"reconProFeatureName\":\"QuickBooksAccounts\",\"pricePerMonth\":25,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":271,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Basic Parts\",\"reconProFeatureName\":\"BasicParts\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:37:12.797\"},{\"featureID\":273,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Labor services\",\"reconProFeatureName\":\"LaborPriceType\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:40:02.247\"},{\"featureID\":272,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Inspection Supplements\",\"reconProFeatureName\":\"Supplements\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":297,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Splat Screens\",\"reconProFeatureName\":\"SplatScreen\",\"pricePerMonth\":15,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":388,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Parts Management - Basic\",\"reconProFeatureName\":\"Parts Management - Basic\",\"pricePerMonth\":0,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:36:59.123\"}]))");
    }

    public static void setAttributeWithJS(WebElement element, String attribute, String value) {
        ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attribute, value);
        WaitUtilsWebDriver.waitForAttributeToBe(element, attribute, value);
    }

    public static void sendKeysWithJS(WebElement element, String value) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        ((JavascriptExecutor) driver).executeScript("arguments[1].value = arguments[0]; ", value, element);
        try {
            WaitUtilsWebDriver.waitForTextToBePresentInElement(element, value, 3);
        } catch (Exception ignored) {}
    }

    public static boolean isTextDisplayed(WebElement element, String text) {
        try {
            WaitUtilsWebDriver.waitForTextToBePresentInElement(element, text);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void closeNewTab(String mainWindowHandle) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.close();
        WaitUtilsWebDriver.waitABit(1000);
        driver.switchTo().window(mainWindowHandle);
    }

    public static WebElement getElement(WebElement element) {
        WaitUtilsWebDriver.waitForVisibility(element);
        return element;
    }

    public static String getUrl() {
        WaitUtilsWebDriver.waitForUrl();
        return DriverBuilder.getInstance().getDriver().getCurrentUrl();
    }

    public static boolean isElementWithAttributeContainingValueDisplayed(WebElement element, String attribute, String value, int timeout) {
        try {
            return WaitUtilsWebDriver.waitForAttributeToContain(element, attribute, value, timeout);
        } catch (Exception ignored) {
            return false;
        }
    }

    public static String getText(WebElement element) {
        try {
            return WaitUtilsWebDriver.waitForVisibility(element).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getText(By by) {
        try {
            return WaitUtilsWebDriver.waitForVisibility(DriverBuilder.getInstance().getDriver().findElement(by)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static List<String> getText(List<WebElement> list) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(list);
        return list
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static List<String> getTextByValue(List<WebElement> list) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(list);
        return list
                .stream()
                .map(e -> e.getAttribute("value"))
                .collect(Collectors.toList());
    }

    public static void acceptAlertIfPresent() {
        try {
            WaitUtilsWebDriver.getShortWait().ignoring(Exception.class).until(ExpectedConditions.alertIsPresent()).accept();
            WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        } catch (TimeoutException ignored) {}
    }

    public static void switchToFrame(WebElement frame) {
        DriverBuilder.getInstance().getDriver().switchTo().frame(frame);
    }

    public static boolean elementContainsText(WebElement element, String text) {
        return WaitUtilsWebDriver.waitForVisibility(element).getText().contains(text);
    }
}