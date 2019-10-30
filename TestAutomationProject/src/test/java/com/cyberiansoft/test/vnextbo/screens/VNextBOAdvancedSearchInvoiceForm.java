package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
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

    @FindBy(xpath = "//ul[@id='advSearchInvoice-status_listbox']/li")
    private List<WebElement> statusListBoxOptions;

    @FindBy(xpath = "//ul[@id='advSearchInvoice-timeframe_listbox']/li")
    private List<WebElement> timeFrameListBoxOptions;

    public VNextBOAdvancedSearchInvoiceForm() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}