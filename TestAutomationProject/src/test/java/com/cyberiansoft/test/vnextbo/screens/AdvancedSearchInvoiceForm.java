package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdvancedSearchInvoiceForm extends VNextBOBaseWebPage {

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

    public AdvancedSearchInvoiceForm(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(advancedSearchForm));
    }

    public AdvancedSearchInvoiceForm setInvoiceNumber(String invoice) {
        wait.until(ExpectedConditions.elementToBeClickable(invoiceInputField)).clear();
        invoiceInputField.sendKeys(invoice);
        return this;
    }

    public AdvancedSearchInvoiceForm setStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(statusCombobox)).click();
        wait.until(ExpectedConditions.attributeContains(statusCombobox, "aria-expanded", "true"));
        wait.until(ExpectedConditions.elementToBeClickable(statusDropDown
                .findElement(By.xpath("//li[text()='" + status + "']")))).click();
        waitABit(500);
        return this;
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        waitForLoading();
    }
}
