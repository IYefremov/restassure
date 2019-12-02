package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAdvancedSearchDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']")
    private WebElement advancedSearchForm;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//i[contains(@class, 'icon-close')]")
    private WebElement closeButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Clear']")
    private WebElement clearButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Delete']")
    private WebElement deleteButton;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-customer']")
    private WebElement customerField;

    @FindBy(xpath = "//ul[@id='advSearchPartsOrdering-customer_listbox']")
    private WebElement customerFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchPhaseInput_listbox']//span[@class='k-input']")
    private WebElement phaseField;

    @FindBy(xpath = "//ul[@id='partsOrdersSearchPhaseInput_listbox']")
    private WebElement phaseFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchWOTypesInput_listbox']//span[@class='k-input']")
    private WebElement woTypeField;

    @FindBy(xpath = "//ul[@id='partsOrdersSearchWOTypesInput_listbox']")
    private WebElement woTypeFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersRepairStatusDropdownlist_listbox']//span[@class='k-input']")
    private WebElement repairStatusField;

    @FindBy(xpath = "//ul[@id='partsOrdersRepairStatusDropdownlist_listbox']")
    private WebElement repairStatusFieldDropDown;

    @FindBy(xpath = "//input[@id='partsOrdersSearchWoInput']")
    private WebElement woNumberField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchStockInput']")
    private WebElement stockNumberField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchEtaFromInput']")
    private WebElement etaFromDateField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchEtaToInput']")
    private WebElement etaToDateField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchVinInput']")
    private WebElement vinField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchOemInput']")
    private WebElement partNumberField;

    @FindBy(xpath = "//input[@id='partsOrdersSearchNotesInput']")
    private WebElement notesField;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-fromDate']")
    private WebElement fromDateField;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-toDate']")
    private WebElement toDateField;

    @FindBy(xpath = "//span[@aria-owns='advSearchPartsOrdering-orderedFrom_listbox']//span[@class='k-input']")
    private WebElement orderedFromField;

    @FindBy(xpath = "//ul[@id='advSearchPartsOrdering-orderedFrom_listbox']")
    private WebElement orderedFromFieldDropDown;

    @FindBy(xpath = "//input[@data-automation-id='partsOrderingCorePrice']")
    private WebElement corePriceCheckBox;

    @FindBy(xpath = "//input[@data-automation-id='partsOrderingLaborCredit']")
    private WebElement laborCreditCheckBox;

    @FindBy(xpath = "//span[@aria-owns='partsOrderingCorePriceStatus_listbox']//span[@class='k-input']")
    private WebElement coreStatusField;

    @FindBy(xpath = "//ul[@id='partsOrderingCorePriceStatus_listbox']")
    private WebElement coreStatusFieldDropDown;

    @FindBy(xpath = "//input[@id='advSearchPartsOrdering-searchName']")
    private WebElement searchNameField;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()=\"" + optionName + "\"]"));
    }

    public VNextBOAdvancedSearchDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}