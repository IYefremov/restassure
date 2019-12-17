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

    @FindBy(xpath = "//div[@id='part-entity-details']//div[contains(@data-bind, 'deleteParts')]")
    private WebElement deleteSelectedPartsButton;

    @FindBy(xpath = "//div[contains(@class, 'entity-details__head__all-items-toggle  pull-left')]")
    private WebElement statusesCheckbox;

    @FindBy(xpath = "//div[@id='partsCheckAllDropDown']")
    private WebElement statusesCheckboxDropDown;

    @FindBy(xpath = "//div[@class='parts-check-all-status']")
    private List<WebElement> partsStatusesList;

    @FindBy(xpath = "//button[contains(@data-bind, 'addLaborServices')]")
    private List<WebElement> addLaborButton;

    @FindBy(xpath = "//div[@data-bind='text: serviceName']")
    private List<WebElement> serviceName;

    @FindBy(xpath = "//button[contains(@data-bind, 'deleteLaborService')]")
    private List<WebElement> deleteLaborButton;

    @FindBy(xpath = "//div[@id='partsTable']//button/i[contains(@class, 'icon-arrow-down2')]")
    private List<WebElement> laborsExpander;

    @FindBy(xpath = "//div[@id='partsTable']/div[@role='option']")
    private List<WebElement> partDetails;

    @FindBy(xpath = "//div[@id='part-entity-details']//input[contains(@data-bind, 'canDelete')]")
    private List<WebElement> partCheckbox;

    @FindBy(xpath = "//div[@id='partsTable']//div[contains(@data-bind, 'isExpanded')]")
    private List<WebElement> partLaborsBlock;

    @FindBy(xpath = "//input[contains(@class, 'service-oem-number-combobox') and not(@data-bind)]")
    private List<WebElement> partNumberField;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'amountFormatted')]")
    private List<WebElement> partPriceField;

    @FindBy(xpath = "//div[@id='partsTable']//input[contains(@data-bind, 'vendorPriceFormatted')]")
    private List<WebElement> partVendorPriceField;

    @FindBy(xpath = "//span[contains(@class,'service-condition-dropdown')]//span[@class='k-input']")
    private List<WebElement> partConditionField;

    @FindBy(xpath = "//span[contains(@class,'service-core-status-dropdown')]//span[@class='k-input']")
    private List<WebElement> partCoreStatusField;

    @FindBy(xpath = "//ul[@id='partsOrderingCorePriceStatus_listbox']")
    private List<WebElement> partCoreStatusDropDown;

    @FindBy(xpath = "//span[contains(@class,'service-status-dropdown')]//span[@class='k-input']")
    private List<WebElement> partStatusField;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[contains(@class, 'k-list-container')]")
    private WebElement partStatusDropDown;

    @FindBy(xpath = "//div[@class='k-animation-container']/div[contains(@class, 'k-list-container')]//li")
    private List<WebElement> partStatusListBoxOptions;

    @FindBy(xpath = "//span[contains(@class,'service-provider-dropdown')]//span[@class='k-input']")
    private List<WebElement> partProviderField;

    @FindBy(xpath = "//input[contains(@data-bind, 'estimatedTimeArrival')]")
    private List<WebElement> partEtaField;

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

    public VNextBOPartsDetailsPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}