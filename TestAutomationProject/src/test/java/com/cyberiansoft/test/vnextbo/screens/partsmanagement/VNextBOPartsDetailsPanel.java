package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPartsDetailsPanel extends VNextBOBaseWebPage {

    @FindBy(id = "partsTable")
    private WebElement partsDetailsTable;

    @FindBy(xpath = "//div[contains(@data-bind, 'addPart')]")
    private WebElement addNewPartButton;

    @FindBy(id = "parts-providers-list")
    private WebElement getQuotesButton;

    @FindBy(xpath = "//div[@id='part-entity-details']//div[contains(@data-bind, 'deleteParts')]")
    private WebElement deleteSelectedPartsButton;

    @FindBy(xpath = "//div[contains(@class, 'entity-details__head__all-items-toggle  pull-left')]")
    private WebElement statusesCheckbox;

    @FindBy(xpath = "//div[@id='partsCheckAllDropDown']")
    private WebElement statusesCheckboxDropDown;

    @FindBy(id = "partsCheckAllDropDown")
    private WebElement headerDropDown;

    @FindBy(id = "parts-providers-cart")
    private WebElement shoppingCartButton;

    @FindBy(xpath = "//div[@class='parts-check-all-status']")
    private List<WebElement> headerPartsStatusesList;

    @FindBy(xpath = "//button[contains(@data-bind, 'addLaborServices')]")
    private List<WebElement> addLaborButton;

    @FindBy(xpath = "//button[contains(@data-bind, 'deleteLaborService')]")
    private List<WebElement> deleteLaborButton;

    @FindBy(xpath = "//div[@id='partsTable']//button/i[contains(@class, 'icon-arrow-down2')]")
    private List<WebElement> laborsExpander;

    @FindBy(xpath = "//div[@id='partsTable']/div[@role='option']")
    private List<WebElement> partDetails;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[contains(@data-bind, 'canDelete')]")
    private List<WebElement> partCheckboxesList;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[@title='Labor credit']")
    private List<WebElement> laborCreditInputFieldsList;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[@title='Quantity']")
    private List<WebElement> quantityInputFieldsList;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[@title='Core price']")
    private List<WebElement> corePriceInputFieldsList;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[@title='Price']")
    private List<WebElement> priceInputFieldsList;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[@title='Vendor price']")
    private List<WebElement> vendorPriceInputFieldsList;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[@title='PO#']")
    private List<WebElement> poInputFieldsList;

    @FindBy(xpath = "//div[@id='part-entity-details']//div[text()='Provider']/..//span[contains(@class, 'k-select')]")
    private WebElement providerFieldArrow;

    @FindBy(xpath = "//span[contains(@class,'service-provider-dropdown')]//span[@class='k-input']")
    private List<WebElement> partProviderInputField;

    @FindBy(xpath = "//div[@id='partsTable']//div[contains(@data-bind, 'isExpanded')]")
    private List<WebElement> partLaborsBlock;

    @FindBy(xpath = "//input[contains(@class, 'service-oem-number-combobox') and not(@data-bind)]")
    private List<WebElement> partNumberField;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'amountFormatted')]")
    private List<WebElement> partPriceField;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'vendorPriceFormatted')]")
    private List<WebElement> partVendorPriceField;

    @FindBy(xpath = "//span[contains(@class,'service-condition-dropdown')]//span[@class='k-input']")
    private List<WebElement> partConditionFields;

    @FindBy(xpath = "//span[contains(@class,'service-core-status-dropdown')]//span[@class='k-input']")
    private List<WebElement> partCoreStatusFields;

    @FindBy(xpath = "//ul[@id='partsOrderingCorePriceStatus_listbox']")
    private List<WebElement> partCoreStatusDropDown;

    @FindBy(xpath = "//span[contains(@class,'service-status-dropdown')]//span[@class='k-input']")
    private List<WebElement> partStatusFields;

    @FindBy(xpath = "//div[contains(@data-bind, 'serviceName')]")
    private List<WebElement> partNames;

    @FindBy(xpath = "//input[contains(@class, 'service-oem-number-combobox') and @role='combobox']")
    private List<WebElement> partNumberInputFields;

    @FindBy(xpath = "//span[contains(@class, 'service-oem-number-combobox')]//span[@aria-label='select']")
    private List<WebElement> partNumberArrowsList;

    @FindBy(xpath = "//div[@class='k-animation-container' and @aria-hidden='false']/div[contains(@class, 'k-list-container')]")
    private WebElement partDropDown;

    @FindBy(xpath = "//div[@aria-hidden='false']//div[contains(@class, 'k-list-optionlabel')]")
    private WebElement partDropDownEmptyField;

    @FindBy(xpath = "//div[@class='k-animation-container' and @aria-hidden='false']/div[contains(@class, 'k-list-container')]//li")
    private List<WebElement> partsListBoxOptions;

    @FindBy(xpath = "//input[contains(@data-bind, 'estimatedTimeArrival')]")
    private List<WebElement> etaFieldsList;

    @FindBy(xpath = "//div[text()='ETA']/..//span[contains(@class, 'k-i-calendar')]")
    private List<WebElement> etaFieldsCalendarButton;

    @FindBy(xpath = "//input[contains(@data-bind, 'quantityFormatted')]")
    private List<WebElement> partQuantityField;

    @FindBy(xpath = "//span[contains(@data-bind, 'laborHours')]")
    private List<WebElement> partLaborValue;

    @FindBy(xpath = "//input[contains(@data-bind, 'corePriceFormatted')]")
    private List<WebElement> partCorePriceField;

    @FindBy(xpath = "//input[contains(@data-bind, 'laborCreditFormatted')]")
    private List<WebElement> partLaborCreditField;

    @FindBy(xpath = "//div[contains(@data-bind, 'isActionsButtonVisible')]")
    private List<WebElement> actionsButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'visible: partsMenuVisible')]")
    private List<WebElement> actionsDropDownMenu;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]//label[text()='Duplicate']")
    private List<WebElement> duplicateActionButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]//label[text()='Delete']")
    private List<WebElement> deleteActionButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]//label[text()='Documents']")
    private List<WebElement> documentsActionButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'partsMenuVisible')]//label[text()='Notes']")
    private List<WebElement> notesActionButton;

    @FindBy(xpath = "//div[@id='partsTable']//div[@data-order-service-id]")
    private List<WebElement> partsList;

    @FindBy(xpath = "//div[contains(@class, 'k-animation-container') and contains(@style, 'display: block')]//td[contains(@class, 'k-today')]")
    private WebElement etaCalendarTodayCell;

    public VNextBOPartsDetailsPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getPartNumbersTitlesList() {
        return partsDetailsTable.findElements(By.xpath(".//div[text()='Part#']"));
    }

    public List<WebElement> laborsNamesListForPartByNumberInList(int partNumber) {

        return partDetails.get(partNumber).findElements(By.xpath(".//div[@class='grid-flex__row grid-flex__divider']//span[@data-bind='text: serviceName']"));
    }

    public List<WebElement> laborsListForPartByNumberInList(int partNumber) {

        return partDetails.get(partNumber).findElements(By.xpath(".//div[@class='grid-flex__row grid-flex__divider']"));
    }

    public WebElement deleteLaborButton(int partNumber, String laborServiceName) {

        return partDetails.get(partNumber).findElement(By.xpath("//span[@data-bind='text: serviceName' and contains(text(),'" +
                laborServiceName + "')]/ancestor::div[@class='grid-flex__row grid-flex__divider']//i[@class='icon-delete']"));
    }

    public WebElement statusCheckBoxDropDownItem(String status) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@class='parts-check-all-status' and contains(text(), '  " + status + "')]"));
    }

    public List<WebElement> partCheckBoxesByPartStatus(String status) {

        return DriverBuilder.getInstance().getDriver().findElements(
                By.xpath("//span[contains(@class,'service-status-dropdown')]//span[@class='k-input' and contains(., '  " +
                        status +"')]/ancestor::div[@class='grid-flex__row']//input[@class='control-checkbox']"));
    }

    public List<WebElement> getPartNamesByStatus(String status) {
        return driver.findElements(By.xpath("//input[contains(@data-text, '" + status
                + "')]/../../../../..//div[contains(@data-bind, 'serviceName')]"));
    }

    public List<WebElement> getPoInputFieldsByStatus(String status) {
        return driver.findElements(By.xpath("//input[contains(@data-text, '" + status
                + "')]/../../../../..//input[@title='PO#']"));
    }

    public List<WebElement> getDeleteStatusesList() {
        return headerDropDown.findElements(By.xpath(".//div[contains(@class, 'parts-check-all-status')]"));
    }

    public WebElement getPartInputField(int order) {
        return partNumberInputFields.get(order);
    }

    public WebElement getPartInputFieldClearIcon(WebElement partService) {
        return partService.findElement(By.xpath(".//following-sibling::span[@title='clear']"));
    }
}