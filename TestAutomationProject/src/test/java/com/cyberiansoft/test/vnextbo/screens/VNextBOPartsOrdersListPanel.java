package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOPartsOrdersListPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private WebElement listPanel;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li")
    private List<WebElement> listOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[contains(@class, 'item__title')]")
    private List<WebElement> namesListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//span[text()='Phase:']/..")
    private List<WebElement> phasesListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//div/b")
    private List<WebElement> woNumsListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//div[contains(@class, 'item__description')]/div[1]")
    private List<WebElement> woDataListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//span[text()='Stock#:']/..")
    private List<WebElement> stockNumsListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']//span[text()='VIN:']/..")
    private List<WebElement> vinNumsListOptions;

    @FindBy(xpath = "//input[@class='k-input service-oem-number-combobox']")
    private List<WebElement> oemNumsListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[text()='Stock#:']/parent::div")
    private List<WebElement> stockNumOptions;

    @FindBy(xpath = "//input[contains(@data-bind, 'estimatedTimeArrival')]")
    private List<WebElement> etaData;

    @FindBy(xpath = "//a[@class='link--underlined' and contains(text(), 'first work order')]")
    private WebElement emptyPartsListMessage;

    public VNextBOPartsOrdersListPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isPartsOrdersListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(listPanel));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public WebElement getRandomPartsOrder() {
        final int size = getPartsOrderListSize();
        try {
            if (size > 1) {
                final int random = RandomUtils.nextInt(1, size);
                System.out.println("Random number from the list of parts orders: " + random);
                return listOptions.get(random);
            } else if (size == 1) {
                return listOptions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPartsOrderListSize() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(listOptions));
            return listOptions.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public VNextBOPartsDetailsPanel clickPartsOrder(WebElement order) {
        wait.until(ExpectedConditions.elementToBeClickable(order)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOPartsDetailsPanel.class);
    }

    public List<String> getStockNumOptions() {
        return getPartsManagementOptions(stockNumOptions);
    }

    public List<String> getNamesListOptions() {
        return getPartsManagementOptions(namesListOptions);
    }

    public List<String> getPhasesListOptions() {
        return getPartsManagementOptions(phasesListOptions);
    }

    public List<String> getPartsOrdersListDataOptionsDescriptions() {
        return getPartsManagementOptions(woDataListOptions);
    }

    public String getDateFromString(String string) {
        final String updated = string.replaceAll("[(),]", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return Arrays
                .stream(updated.split(" "))
                .filter((value) -> {
                    try {
                        LocalDate.parse(value, formatter);
                        return true;
                    } catch (Exception ignored) {}
                    return false;
                })
                .collect(Collectors.toList())
                .get(0);
    }

    public List<String> getWoNumsListOptions() {
        return getPartsManagementOptions(woNumsListOptions);
    }

    public List<String> getStockNumsListOptions() {
        return getPartsManagementOptions(stockNumsListOptions);
    }

    public List<String> getVinNumsListOptions() {
        return getPartsManagementOptions(vinNumsListOptions);
    }

    public List<String> getOemNumsListOptions() {
        return getPartsManagementValues(oemNumsListOptions);
    }

    public List<String> getETADataValues() {
        return getPartsManagementValues(etaData);
    }

    private List<String> getPartsManagementOptions(List<WebElement> options) {
        wait.until(ExpectedConditions.visibilityOfAllElements(options));
        return options
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private List<String> getPartsManagementValues(List<WebElement> options) {
        wait.until(ExpectedConditions.visibilityOfAllElements(options));
        return options
                .stream()
                .map(e -> e.getAttribute("value"))
                .collect(Collectors.toList());
    }

    public String getEmptyPartsListMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(emptyPartsListMessage)).getText();
        } catch (Exception ignored) {
            return "";
        }
    }
}