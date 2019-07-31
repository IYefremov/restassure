package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VNextBOAdvancedSearchInvoiceForm extends VNextBOBaseWebPage {

    @FindBy(id = "advSearchInvoice-form")
    private WebElement advancedSearchForm;

    @FindBy(id = "advSearchInvoice-Invoice")
    private WebElement invoiceInputField;

    @FindBy(xpath = "//span[@aria-owns='advSearchInvoice-status_listbox']")
    private WebElement statusCombobox;

    @FindBy(id = "advSearchInvoice-status_listbox")
    private WebElement statusDropDown;

    @FindBy(xpath = "//form[@id='advSearchInvoice-form']//button[@type=\"submit\"]")
    private WebElement submitButton;

    @FindBy(id = "orderTimeframeDropdown_listbox")
    private WebElement timeFrameDropDown;

    @FindBy(xpath = "//span[@aria-owns='advSearchInvoice-timeframe_listbox']")
    private WebElement timeFrameListBox;

    @FindBy(id = "advSearchInvoice-fromDate")
    private WebElement fromDateField;

    @FindBy(id = "advSearchInvoice-toDate")
    private WebElement toDateField;

    @FindBy(xpath = "//ul[@id='advSearchInvoice-timeframe_listbox']/li")
    private List<WebElement> timeFrameListBoxOptions;

    public VNextBOAdvancedSearchInvoiceForm(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(advancedSearchForm));
    }

    public VNextBOAdvancedSearchInvoiceForm setInvoiceNumber(String invoice) {
        Utils.clearAndType(invoiceInputField, invoice);
        return this;
    }

    public VNextBOAdvancedSearchInvoiceForm setStatus(String status) {
        Utils.clickElement(statusCombobox);
        try {
            wait.until(ExpectedConditions.attributeContains(statusCombobox, "aria-expanded", "true"));
            WaitUtilsWebDriver.waitABit(500);
        } catch (Exception ignored) {}
        Utils.clickElement(statusDropDown.findElement(By.xpath("//li[text()='" + status + "']")));
        try {
            wait.until(ExpectedConditions.attributeContains(statusCombobox, "aria-expanded", "false"));
        } catch (Exception ignored) {}
        WaitUtilsWebDriver.waitABit(1000);
        return this;
    }

    public VNextBOInvoicesWebPage clickSearchButton() {
        Utils.clickElement(submitButton);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        return PageFactory.initElements(driver, VNextBOInvoicesWebPage .class);
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(advancedSearchForm)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public VNextBOAdvancedSearchInvoiceForm setTimeFrame(String timeFrame) {
        clickTimeFrameBox();
        selectTimeFrame(timeFrame);
        return this;
    }

    private VNextBOAdvancedSearchInvoiceForm clickTimeFrameBox() {
        wait.until(ExpectedConditions.elementToBeClickable(timeFrameListBox)).click();
        return this;
    }

    private VNextBOAdvancedSearchInvoiceForm selectTimeFrame(String timeFrame) {
        selectOptionInDropDown(timeFrameDropDown, timeFrameListBoxOptions, timeFrame, true);
        return this;
    }

    public VNextBOAdvancedSearchInvoiceForm setFromDate(String date) {
        return setDate(fromDateField, date);
    }

    public VNextBOAdvancedSearchInvoiceForm setToDate(String date) {
        return setDate(toDateField, date);
    }

    public VNextBOAdvancedSearchInvoiceForm setDate(WebElement toDateField, String date) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(toDateField)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        toDateField.sendKeys(date);
        waitABit(1000);
        return this;
    }

    public boolean isAdvancedSearchDialogNotDisplayed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOf(advancedSearchForm));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}