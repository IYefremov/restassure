package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Getter
public class VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@class, 'k-loading-mask')]")
    public static WebElement spinner;

    @FindBy(id = "app-progress-spinner")
    public static WebElement loadingProcess;

    public WebDriver driver;
    public static WebDriverWait wait;
    public static WebDriverWait waitLong;
    public static WebDriverWait waitShort;
    public Actions actions;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public VNextBOBaseWebPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15, 1);
        waitShort = new WebDriverWait(driver, 5, 1);
        waitLong = new WebDriverWait(driver, 30, 1);
        actions = new Actions(driver);
    }

    /* Wait For */
    public void waitABit(int milliseconds) {
        if (milliseconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException ex) {
                // Swallow exception
                ex.printStackTrace();
            }
        }
    }

    public void checkboxSelect(WebElement checkbox) {
        if (!isCheckboxChecked(checkbox))
            checkbox.click();
    }

    public void checkboxUnselect(WebElement checkbox) {
        if (isCheckboxChecked(checkbox)) {
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
        }
    }

    public boolean isCheckboxChecked(WebElement element) {
        boolean result = false;
        String selected = element.getAttribute("checked");
        if (selected != null) {
            if (selected.equals("true"))
                result = true;
        }
        return result;
    }

    public void waitForNewTab() {
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

    public String getUrl() {
        try {
            await().atMost(15, TimeUnit.SECONDS)
                    .ignoreExceptions()
                    .pollInterval(500, TimeUnit.MILLISECONDS)
                    .until(driver::getCurrentUrl);
        } catch (ConditionTimeoutException ignored) {}

        return driver.getCurrentUrl();
    }

    public void closeNewTab(String mainWindowHandle) {
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }

    public void closeMainWindow(String mainWindow) {
        driver.switchTo().window(mainWindow);
        driver.close();
        waitABit(1000);
    }

    public void closeWindows() {
        final Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            driver.switchTo().window(window);
            driver.close();
            waitABit(1000);
        }
    }

    public void waitForLoading() {
        waitForLoadingToStop(loadingProcess);
    }

    private void waitForLoadingToStop(WebElement loadingElement) {
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(loadingElement);
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(loadingElement);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public void executeJsForAddOnSettings() {
        ((JavascriptExecutor) driver).executeScript("localStorage.setItem('addons_selfSubscribedFeatures', JSON.stringify([{\"featureID\":17,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Labor Times\",\"reconProFeatureName\":\"LaborHoursByEmployeeReport\",\"pricePerMonth\":1,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:37:05.983\"},{\"featureID\":41,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Basic Services ($$, %%, Discounts, Taxes)\",\"reconProFeatureName\":\"BasicParts\",\"pricePerMonth\":0.99,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:40:25.92\"},{\"featureID\":37,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Question Forms\",\"reconProFeatureName\":\"AccessClinetInfo\",\"pricePerMonth\":99999999.22,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T03:39:25.673\"},{\"featureID\":38,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Configurable email templates for inspections/estimates and invoices +1234566 test long name long name\",\"reconProFeatureName\":\"WorkOrders\",\"pricePerMonth\":1.55,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-30T08:31:18.337\"},{\"featureID\":80,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"QuickBooks Online Accounting System (Additional) test test\",\"reconProFeatureName\":\"QuickBooksAccounts\",\"pricePerMonth\":25,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":271,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Basic Parts\",\"reconProFeatureName\":\"BasicParts\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:37:12.797\"},{\"featureID\":273,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Labor services\",\"reconProFeatureName\":\"LaborPriceType\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:40:02.247\"},{\"featureID\":272,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Inspection Supplements\",\"reconProFeatureName\":\"Supplements\",\"pricePerMonth\":10,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":297,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Splat Screens\",\"reconProFeatureName\":\"SplatScreen\",\"pricePerMonth\":15,\"isEnabled\":true,\"requestToEnable\":null,\"requestDate\":null},{\"featureID\":388,\"applicationID\":\"2d21363b-3ed8-4ee1-b357-aa9ce95ce70d\",\"featureName\":\"Parts Management - Basic\",\"reconProFeatureName\":\"Parts Management - Basic\",\"pricePerMonth\":0,\"isEnabled\":true,\"requestToEnable\":true,\"requestDate\":\"2018-08-29T00:36:59.123\"}]))");
    }

    VNextBOBaseWebPage clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    public void setAttributeWithJS(WebElement element, String attribute, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attribute, value);
        wait.until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public void sendKeysWithJS(WebElement element, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[1].value = arguments[0]; ", value, element);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, value));
        } catch (Exception ignored) {}
    }

    public void clearAndType(WebElement textField, String name) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(textField)).clear();
        } catch (Exception e) {
            Assert.fail("The text field has not been displayed", e);
        }
        wait.until(ExpectedConditions.elementToBeClickable(textField)).sendKeys(name);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void setZoom(int zoomPercentage) {
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='" + zoomPercentage + "%'", "");
    }

    public void refreshPage() {
        driver.navigate().refresh();
        waitForLoading();
    }

    public void reduceZoom() {
        actions.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT)).perform();
    }

    public void increaseZoom() {
        actions.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD)).perform();
    }

    public void goToPreviousPage() {
        driver.navigate().back();
        waitForLoading();
    }

    void selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox, String selection) {
        waitForDropDownToBeOpened(dropDown);
        waitForVisibilityOfAllOptions(listBox);
        getMatchingOptionInListBox(listBox, selection)
                .ifPresent((option) -> actions
                        .moveToElement(option)
                        .click()
                        .build()
                        .perform());
        waitForDropDownToBeClosed(dropDown);
    }

    void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            clickWithJS(element);
        }
    }

    void selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox, String selection, boolean draggable) {
        if (draggable) {
            waitForDropDownToBeOpened(dropDown);
            waitForVisibilityOfAllOptions(listBox);
            getMatchingOptionInListBox(listBox, selection)
                    .ifPresent((option) -> {
                        scrollListBoxDownWhileElementIsNotDisplayed(dropDown, listBox, option);
                        waitShort.until(ExpectedConditions.elementToBeClickable(option)).click();
                    });
            waitForDropDownToBeClosed(dropDown);
        } else {
            selectOptionInDropDown(dropDown, listBox, selection);
        }
    }

    private Optional<WebElement> getMatchingOptionInListBox(List<WebElement> listBox, String selection) {
        return listBox
                .stream()
                .filter((option) -> {
                    wait.ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.not(ExpectedConditions.stalenessOf(option)));
                    return option.getText().equals(selection);
                })
                .findFirst();
    }

    private void scrollListBoxDownWhileElementIsNotDisplayed(WebElement dropDown, List<WebElement> listBox, WebElement option) {
        for (int i = 0; i < listBox.size(); i++) {
            if (!waitShort.until(ExpectedConditions.visibilityOf(option)).isDisplayed()) {
                dropDown.sendKeys(Keys.ARROW_DOWN);
            } else {
                break;
            }
        }
    }

    private void waitForVisibilityOfAllOptions(List<WebElement> listBox) {
        try {
            waitShort.until(ExpectedConditions.visibilityOfAllElements(listBox));
        } catch (Exception ignored) {}
    }

    private void waitForDropDownToBeOpened(WebElement dropDown) {
        try {
            wait.until(ExpectedConditions.attributeToBe(dropDown, "aria-hidden", "false"));
        } catch (Exception ignored) {}
    }

    private void waitForDropDownToBeClosed(WebElement dropDown) {
        try {
            wait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.attributeToBe(dropDown, "aria-hidden", "true"));
        } catch (Exception ignored) {
            waitABit(1000);
        }
    }

    String getNewTab(String mainWindow) {
        for (String activeHandle : driver.getWindowHandles()) {
            if (!activeHandle.equals(mainWindow)) {
                driver.switchTo().window(activeHandle);
                return activeHandle;
            }
        }
        return "";
    }

    String selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox) {
        waitForDropDownToBeOpened(dropDown);
        waitForVisibilityOfAllOptions(listBox);
        final int random = RandomUtils.nextInt(0, listBox.size());
        System.out.println(random);
        final WebElement selectedValue = listBox.get(random);
        actions.moveToElement(selectedValue).click().build().perform();
        waitForDropDownToBeClosed(dropDown);
        return selectedValue.getText();
    }

    boolean isElementDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    boolean isElementNotDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    boolean areElementsDisplayed(List<WebElement> element) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(element));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDateBefore(String from, String to) {
        return getDate(from).isBefore(getDate(to));
    }

    public boolean isDateAfter(String from, String to) {
        return getDate(from).isAfter(getDate(to));
    }

    public boolean isDateEqual(String from, String to) {
        return getDate(from).isEqual(getDate(to));
    }

    private LocalDate getDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public String getInputFieldValue(WebElement inputField) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(inputField)).getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    VNextBOBaseWebPage setData(WebElement inputField, String data) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(inputField)).click();
        } catch (ElementClickInterceptedException e) {
            waitABit(1500);
            clickWithJS(inputField);
        }
        wait.until(ExpectedConditions.elementToBeClickable(inputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputField)).sendKeys(data);
        return this;
    }

    VNextBOBaseWebPage selectDataFromBoxList(List<WebElement> listBox, WebElement list, String data) {
        waitForVisibilityOfAllOptions(listBox);
        for (WebElement selected : listBox) {
            if (selected.getText().equals(data)) {
                actions.moveToElement(selected)
                        .click()
                        .build()
                        .perform();
                break;
            }
        }
        waitForListToDisappear(list);
        return this;
    }

    private void waitForListToDisappear(WebElement list) {
        try {
            wait.until(ExpectedConditions.attributeToBe(list, "aria-hidden", "true"));
        } catch (Exception e) {
            try {
                actions.moveToElement(list).sendKeys(Keys.ENTER).build().perform();
                wait.until(ExpectedConditions.attributeToBe(list, "aria-hidden", "true"));
            } catch (Exception ignored) {
                waitABit(1000);
            }
        }
    }

    boolean isDataDisplayed(List<WebElement> listBox, String data) {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfAllElements(listBox))
                    .stream()
                    .map(WebElement::getText)
                    .anyMatch(option -> option.equals(data));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}