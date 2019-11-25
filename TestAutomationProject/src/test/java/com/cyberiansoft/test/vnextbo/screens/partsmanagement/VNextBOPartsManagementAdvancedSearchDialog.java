package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextBOPartsManagementAdvancedSearchDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']")
    public WebElement advancedSearchForm;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//button[@type='submit']")
    public WebElement searchButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//i[contains(@class, 'icon-close')]")
    public WebElement closeButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Clear']")
    public WebElement clearButton;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-customer']")
    public WebElement customerField;

    @FindBy(xpath = "//ul[@id='advSearchPartsOrdering-customer_listbox']")
    public WebElement customerFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchPhaseInput_listbox']//span[@class='k-input']")
    public WebElement phaseField;

    @FindBy(xpath = "//ul[@id='partsOrdersSearchPhaseInput_listbox']")
    public WebElement phaseFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchWOTypesInput_listbox']//span[@class='k-input']")
    public WebElement woTypeField;

    @FindBy(xpath = "//ul[@id='partsOrdersSearchWOTypesInput_listbox']")
    public WebElement woTypeFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersRepairStatusDropdownlist_listbox']//span[@class='k-input']")
    public WebElement repairStatusField;

    @FindBy(xpath = "//ul[@id='partsOrdersRepairStatusDropdownlist_listbox']")
    public WebElement repairStatusFieldDropDown;

    @FindBy(xpath = "//input[@id='partsOrdersSearchWoInput']")
    public WebElement woNumberField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchStockInput']")
    public WebElement stockNumberField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchEtaFromInput']")
    public WebElement etaFromDateField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchEtaToInput']")
    public WebElement etaToDateField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchVinInput']")
    public WebElement vinField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchOemInput']")
    public WebElement partNumberField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchNotesInput']")
    public WebElement notesField;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-fromDate']")
    public WebElement fromDateField;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-toDate']")
    public WebElement toDateField;

    @FindBy(xpath = "//span[@aria-owns='advSearchPartsOrdering-orderedFrom_listbox']//span[@class='k-input']")
    public WebElement orderedFromField;

    @FindBy(xpath = "//ul[@id='advSearchPartsOrdering-orderedFrom_listbox']")
    public WebElement orderedFromFieldDropDown;

    @FindBy(xpath = "//input[@data-automation-id='partsOrderingCorePrice']")
    public WebElement corePriceCheckBox;

    @FindBy(xpath = "//input[@data-automation-id='partsOrderingLaborCredit']")
    public WebElement laborCreditCheckBox;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-searchName']")
    public WebElement searchNameField;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()='" + optionName + "']"));
    }

    public VNextBOPartsManagementAdvancedSearchDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}