package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOPartsOrdersListPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private WebElement partsOrdersListPanel;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li")
    private List<WebElement> partsOrdersListOptions;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[contains(@class, 'item__title')]")
    private List<WebElement> partsOrdersListOptionsNames;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[text()='Phase:']/..")
    private List<WebElement> partsOrdersListOptionsPhases;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//div[contains(@class, 'item__description')]/div/b")
    private List<WebElement> partsOrdersListOptionsWONums;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//div[contains(@class, 'item__description')]/div")
    private List<WebElement> partsOrdersListOptionsDescription;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']/li//span[text()='Stock#:']/parent::div")
    private List<WebElement> stockNumOptions;

    public VNextBOPartsOrdersListPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isPartsOrdersListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(partsOrdersListPanel));
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
                return partsOrdersListOptions.get(random);
            } else if (size == 1) {
                return partsOrdersListOptions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPartsOrderListSize() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(partsOrdersListOptions));
            return partsOrdersListOptions.size();
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

    public List<String> getPartsOrdersListOptionsNames() {
        return getPartsManagementOptions(partsOrdersListOptionsNames);
    }

    public List<String> getPartsOrdersListOptionsPhases() {
        return getPartsManagementOptions(partsOrdersListOptionsPhases);
    }

    public List<String> getPartsOrdersListOptionsDescriptions() {
        return getPartsManagementOptions(partsOrdersListOptionsDescription);
    }

    public List<String> getPartsOrdersListOptionsWONums() {
        return getPartsManagementOptions(partsOrdersListOptionsWONums);
    }

    private List<String> getPartsManagementOptions(List<WebElement> partsOrdersListOptionsPhases) {
        wait.until(ExpectedConditions.visibilityOfAllElements(partsOrdersListOptionsPhases));
        return partsOrdersListOptionsPhases
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}