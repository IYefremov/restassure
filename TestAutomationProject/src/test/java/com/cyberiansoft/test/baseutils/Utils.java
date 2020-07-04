package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.waitForDropDownToBeOpened;

public class Utils {

    public static void clickElement(WebElement element) {
        try {
            WaitUtilsWebDriver.waitForElementNotToBeStale(element, 3);
            WaitUtilsWebDriver.elementShouldBeClickable(element, true, 3);
            ConditionWaiter.create(__ -> element.isEnabled()).execute();
            element.click();
        } catch (Exception ignored) {
            WaitUtilsWebDriver.waitABit(1000);
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
            new Actions(DriverBuilder.getInstance().getDriver())
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
        sendKeys(element, name);
    }

    public static void clearAndTypeWithJS(WebElement element, String value) {
        sendKeysWithJS(element, "");
        sendKeysWithJS(element, value);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void sendKeysWithEnter(WebElement element, String value) {
        clearAndSendKeys(element, value, Keys.ENTER);
    }

    public static void clearAndSendKeys(WebElement element, String value, Keys keys) {
        clear(element);
        sendKeys(element, value, keys);
    }

    public static void sendKeys(WebElement element, String value, Keys keys) {
        WaitUtilsWebDriver.elementShouldBeClickable(element, true, 1);
        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(element, value).sendKeys(keys).build().perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void sendKeys(WebElement element, String value) {
        WaitUtilsWebDriver.waitForVisibility(element, 3);
        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(element, value).build().perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void sendKeys(By by, String value) {
        WaitUtilsWebDriver.waitForVisibility(by, 3);
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        new Actions(driver).sendKeys(driver.findElement(by), value).build().perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void clear(WebElement element) {
        try {
            WaitUtilsWebDriver.elementShouldBeClickable(element, true, 7);
            element.clear();
        } catch (Exception e) {
            sendKeysWithJS(element, "");
        }
    }

    public static void clearAndType(By by, String name) {
        final WebElement element = DriverBuilder.getInstance().getDriver().findElement(by);
        clearAndType(element, name);
    }

    public static void clearAndTypeUsingKeyboard(WebElement element, String name) {
        scrollToElement(element);
        clickElement(element);
        new Actions(DriverBuilder.getInstance().getDriver())
                .sendKeys(element, Keys.HOME)
                .sendKeys(element, Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END))
                .sendKeys(element, Keys.DELETE)
                .sendKeys(element, name)
                .build()
                .perform();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void clearUsingKeyboard(WebElement element) {
        final Actions actions = new Actions(DriverBuilder.getInstance().getDriver());
        actions.sendKeys(element, Keys.END).build().perform();
        while (!Utils.getInputFieldValue(element).isEmpty()) {
            actions.sendKeys(element, Keys.DELETE).build().perform();
        }
    }

    public static void clearUsingKeyboard(By by) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        final WebElement element = driver.findElement(by);
        final Actions actions = new Actions(driver);
        actions.sendKeys(element, Keys.END).build().perform();
        while (!Utils.getInputFieldValue(element).isEmpty()) {
            actions.sendKeys(element, Keys.DELETE).build().perform();
        }
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
        WaitUtilsWebDriver.waitForElementNotToBeStale(element, 2);
        new Actions(DriverBuilder.getInstance().getDriver()).moveToElement(element).build().perform();
        return element;
    }

    public static WebElement moveToElement(By by) {
        final WebElement element = DriverBuilder.getInstance().getDriver().findElement(by);
        moveToElement(element);
        return element;
    }

    public static void refreshPage() {
        DriverBuilder.getInstance().getDriver().navigate().refresh();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void reduceZoom() {
        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT)).perform();
    }

    public static void increaseZoom() {
        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD)).perform();
    }

    public static void goToPreviousPage() {
        DriverBuilder.getInstance().getDriver().navigate().back();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox, String selection) {
        selectOption(dropDown, listBox, selection);
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown, 1);
    }

    public static void selectOption(WebElement dropDown, List<WebElement> listBox, String selection) {
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox, 1);
        clickMatchingOptionInDropDown(listBox, selection);
    }

    public static void clickMatchingOptionInDropDown(List<WebElement> listBox, String selection) {
        getMatchingOptionInListBox(listBox, selection)
                .ifPresent((option) -> {
                    moveToElement(option);
                    clickElement(option);
                });
    }

    public static String selectOption(WebElement dropDown, List<WebElement> listBox) {
        if (listBox.size() == 0) {
            return null;
        } else {
            final String selection = getText(listBox.get(RandomUtils.nextInt(0, listBox.size())));
            selectOption(dropDown, listBox, selection);
            return selection;
        }
    }

    public static void selectOptionInDropDownWithJs(WebElement dropDown, List<WebElement> listBox, String selection) {
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox, 1);
        getMatchingOptionInListBox(listBox, selection).ifPresent(Utils::clickWithJS);
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown, 1);
    }

    public static void selectOptionInDropDownWithJs(List<WebElement> listBox, String selection) {
        getMatchingOptionInListBox(listBox, selection).ifPresent(Utils::clickWithJS);
        WaitUtilsWebDriver.waitForPageToBeLoaded(3);
    }

    public static void selectOptionInDropDownWithJs(WebElement dropDown, WebElement option) {
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibility(option);
        ConditionWaiter.create(__ -> option.isEnabled()).execute();
        clickWithJS(option);
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown, 1);
    }

    public static void selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox, String selection, boolean draggable) {
        if (draggable) {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox, 1);
            final WebElement webElement = listBox
                    .stream()
                    .filter(option -> getText(option).equals(selection))
                    .findAny()
                    .orElseThrow(() -> new NoSuchElementException("The option " + selection + " hasn't been found"));
            clickElement(webElement);
        } else {
            selectOptionInDropDown(dropDown, listBox, selection);
        }
    }

    public static Optional<WebElement> getMatchingOptionInListBox(List<WebElement> listBox, String selection) {
        final List<String> options = getText(listBox);
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).trim().equals(selection)) {
                return Optional.ofNullable(listBox.get(i));
            }
        }
        return Optional.empty();
    }

    public static void selectOptionInDropDownWithJsScroll(String optionName) {

        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
        javascriptExecutor.executeScript("document.querySelector(\"div[aria-hidden='false'] div.k-list-scroller\").scrollTop+=-1000");
        boolean isOptionDisplayed = false;
        int scrollingNumber = 0;
        do {
            try {
                isOptionDisplayed = isElementDisplayed(driver.findElement(By.xpath("//div[@aria-hidden='false']//li[contains(.,'  " + optionName + "')]")));
            } catch (Exception ex) {
                javascriptExecutor.executeScript("document.querySelector(\"div[aria-hidden='false'] div.k-list-scroller\").scrollTop+=100");
                scrollingNumber++;
            }
        } while ((!isOptionDisplayed) || (scrollingNumber == 3));
        clickWithJS(driver.findElement(By.xpath("//div[@aria-hidden='false']//li[contains(.,'  " + optionName + "')]")));
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

    public static void openTab(String window) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.getWindowHandles()
                .stream()
                .map(handle -> handle.equals(window))
                .findFirst()
                .ifPresent(e -> driver.switchTo().window(window));
    }

    public static void switchToWindow(String windowHandle) {

        DriverBuilder.getInstance().getDriver().switchTo().window(windowHandle);
    }

    public static String getParentTab() {
        return DriverBuilder.getInstance().getDriver().getWindowHandle();
    }

    public static String selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox) {
        final int minOptionNumber = 1;
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox, 1);
        final int random = RandomUtils.nextInt(minOptionNumber, listBox.size());
        String selectedText = getText(listBox.get(random));
        getMatchingOptionInListBox(listBox, selectedText)
                .ifPresent((option) -> {
                    WaitUtilsWebDriver.waitForElementNotToBeStale(option, 3);
                    moveToElement(option);
                    clickElement(option);
                });
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown, 1);
        return selectedText;
    }

    public static String selectOptionInDropDownWithJs(WebElement dropDown, List<WebElement> listBox) {
        final int minOptionNumber = 1;
        waitForDropDownToBeOpened(dropDown);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox, 1);
        final int random = RandomUtils.nextInt(minOptionNumber, listBox.size());
        String selectedText = getText(listBox.get(random));
        getMatchingOptionInListBox(listBox, selectedText).ifPresent(Utils::clickWithJS);
        WaitUtilsWebDriver.waitForDropDownToBeClosed(dropDown, 1);
        System.out.println("random value: " + random + "\n" + "selected option: " + selectedText);
        return selectedText;
    }

    public static boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public static boolean isElementInvisible(WebElement element) {
        try {
            return !element.isDisplayed();
        } catch (StaleElementReferenceException | NoSuchElementException ignored) {
            return true;
        }
    }

    @Deprecated
    public static boolean isElementDisplayed(By by) {
        try {
            WaitUtilsWebDriver.waitForVisibility(DriverBuilder.getInstance().getDriver().findElement(by));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Deprecated
    public static boolean isElementDisplayed(WebElement element, int timeoutInSeconds) {
        try {
            WaitUtilsWebDriver.waitForVisibility(element, timeoutInSeconds);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Deprecated
    public static boolean isElementNotDisplayed(WebElement element) {
        try {
            WaitUtilsWebDriver.waitForInvisibility(element);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Deprecated
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
        return getInputFieldValue(inputField, 15);
    }

    public static String getInputFieldValue(WebElement inputField, int timeOut) {
        try {
            return WaitUtilsWebDriver.waitForVisibility(inputField, timeOut).getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void selectDataFromBoxList(List<WebElement> listBox, WebElement list, String data) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(listBox);
        for (WebElement selected : listBox) {
            if (selected.getText().equals(data)) {
                new Actions(DriverBuilder.getInstance().getDriver()).moveToElement(selected)
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

    public static void closeAllNewWindowsExceptParentTab(String mainWindow) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        final Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                driver.close();
                WaitUtilsWebDriver.waitABit(1000);
            }
        }
        driver.switchTo().window(mainWindow);
    }

    public static void openNewTab(String url) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        ((JavascriptExecutor) DriverBuilder.getInstance().getDriver()).executeScript("window.open();");
        final Set<String> windowHandles = driver.getWindowHandles();
        final String newTab = windowHandles
                .stream()
                .reduce((prev, next) -> next)
                .orElseThrow(() -> new RuntimeException("The tab hasn't been opened"));
        driver.switchTo().window(newTab);
        WebDriverUtils.webdriverGotoWebPage(url);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
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
            WaitUtilsWebDriver.waitForTextToBePresentInElement(element, value, 2);
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

    public static boolean isTextDisplayed(WebElement element, String text, int timeOut) {
        try {
            WaitUtilsWebDriver.elementShouldBeVisible(element, true, 5);
            WaitUtilsWebDriver.waitForTextToBePresentInElement(element, text, timeOut);
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
            WaitUtilsWebDriver.waitForElementNotToBeStale(element);
            return WaitUtilsWebDriver.waitForVisibility(element).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getText(WebElement element, int timeout) {
        try {
            WaitUtilsWebDriver.waitForElementNotToBeStale(element);
            return WaitUtilsWebDriver.waitForVisibility(element, timeout).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getText(By by) {
        return getText(by, 10);
    }

    public static String getText(By by, int timeout) {
        try {
            final WebElement element = DriverBuilder.getInstance().getDriver().findElement(by);
            WaitUtilsWebDriver.waitForElementNotToBeStale(element);
            return WaitUtilsWebDriver.waitForVisibility(element, timeout).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static List<String> getText(List<WebElement> list) {
        return getText(list, 5);
    }

    public static List<String> getText(List<WebElement> list, int timeOut) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(list, timeOut);
        return list
                .stream()
                .map(WaitUtilsWebDriver::waitForElementNotToBeStale)
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static List<String> getTextByValue(List<WebElement> list) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(list);
        return list
                .stream()
                .map(WaitUtilsWebDriver::waitForElementNotToBeStale)
                .map(e -> e.getAttribute("value"))
                .collect(Collectors.toList());
    }

    public static boolean isChecked(WebElement option) {
        return option.isSelected();
    }

    public static boolean isChecked(WebElement option, int timeout) {
        return WaitUtilsWebDriver.waitForVisibility(option, timeout).isSelected();
    }

    public static boolean attributeContains(WebElement element, String attribute, String value) {
        try {
            return element.getAttribute(attribute).contains(value);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean attributeContains(List<WebElement> elementsList, String attribute, String value) {
        return elementsList.stream().allMatch(element -> attributeContains(element, attribute, value));
    }

    public static boolean attributeEquals(WebElement element, String attribute, String value) {
        try {
            return element.getAttribute(attribute).equals(value);
        } catch (Exception e) {
            return false;
        }
    }

    public static String getAttribute(WebElement element, String attribute) {
        try {
            return element.getAttribute(attribute);
        } catch (Exception e) {
            return "";
        }
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
        return WaitUtilsWebDriver.waitForVisibility(element, 3).getText().contains(text);
    }

    public static int getRandomNumber(int startInclusive, int endExclusive) {
        return RandomUtils.nextInt(startInclusive, endExclusive);
    }

    public static void hoverElement(WebElement element) {

        Actions action = new Actions(DriverBuilder.getInstance().getDriver());
        action.moveToElement(element).perform();
    }
}