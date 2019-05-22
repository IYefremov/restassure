package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOPartsManagementAdvancedSearchDialog extends VNextBOBaseWebPage {

    @FindBy(id = "advSearchPartsOrdering-form")
    private WebElement advancedSearchDialog;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//h6[text()='Advanced Search']")
    private WebElement advancedSearchPartsOrderingHeader;

    @FindBy(id = "advSearchPartsOrdering-customer")
    private WebElement customerInputField;

    @FindBy(id = "advSearchPartsOrdering-customer-list")
    private WebElement customerAutoCompleteList;

    @FindBy(xpath = "//ul[@id='advSearchPartsOrdering-customer_listbox']/li")
    private List<WebElement> customerListBoxOptions;

    @FindBy(id = "partsOrdersSearchWoInput")
    private WebElement woNumInputField;

    @FindBy(id = "partsOrdersSearchStockInput")
    private WebElement stockNumInputField;

    @FindBy(id = "partsOrdersSearchEtaFromInput")
    private WebElement etaFromInputField;

    @FindBy(id = "partsOrdersSearchEtaToInput")
    private WebElement etaToInputField;

    @FindBy(id = "partsOrdersSearchVinInput")
    private WebElement vinInputField;

    @FindBy(id = "partsOrdersSearchOemInput")
    private WebElement oemNumInputField;

    @FindBy(id = "partsOrdersSearchNotesInput")
    private WebElement notesInputField;

    @FindBy(id = "advSearchPartsOrdering-fromDate")
    private WebElement fromDateInputField;

    @FindBy(id = "advSearchPartsOrdering-toDate")
    private WebElement toDateInputField;

    @FindBy(id = "advSearchPartsOrdering-searchName")
    private WebElement searchNameInputField;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchPhaseInput_listbox']")
    private WebElement phaseListBox;

    @FindBy(id = "partsOrdersSearchPhaseInput-list")
    private WebElement phaseDropDown;

    @FindBy(xpath = "//ul[@id='partsOrdersSearchPhaseInput_listbox']/li")
    private List<WebElement> phaseListBoxOptions;

    @FindBy(xpath = "//ul[@id='advSearchPartsOrdering-orderedFrom_listbox']/li")
    private List<WebElement> orderedFromListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchWOTypesInput_listbox']")
    private WebElement woTypeListBox;

    @FindBy(id = "partsOrdersSearchWOTypesInput_listbox")
    private WebElement woTypeDropDown;

    @FindBy(xpath = "//ul[@id='partsOrdersSearchWOTypesInput_listbox']/li")
    private List<WebElement> woTypeListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='advSearchPartsOrdering-orderedFrom_listbox']")
    private WebElement orderedFromListBox;

    @FindBy(id = "advSearchPartsOrdering-orderedFrom-list")
    private WebElement orderedFromDropDown;

    @FindBy(xpath = "//span[@aria-controls='partsOrdersSearchEtaFromInput_dateview']")
    private WebElement etaFromDateCalendarButton;

    @FindBy(xpath = "//span[@aria-controls='partsOrdersSearchEtaToInput_dateview']")
    private WebElement etaToDateCalendarButton;

    @FindBy(id = "partsOrdersSearchEtaFromInput_dateview")
    private WebElement fromDateCalendarWidget;

    @FindBy(id = "partsOrdersSearchEtaToInput_dateview")
    private WebElement toDateCalendarWidget;

    @FindBy(xpath = "//div[@id='partsOrdersSearchEtaFromInput_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement focusedDateInFromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='partsOrdersSearchEtaToInput_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement focusedDateInToDateCalendarWidget;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//button[contains(text(), 'Search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//i[@class='icon-close pull-right']")
    private WebElement xButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Clear']")
    private WebElement clearButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//form[@id='advSearchPartsOrdering-form']//span[text()='Delete']")
    private WebElement deleteButton;

    public VNextBOPartsManagementAdvancedSearchDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        return isElementDisplayed(advancedSearchDialog);
    }

    public boolean isAdvancedSearchDialogNotDisplayed() {
        return wait.until(ExpectedConditions.invisibilityOf(advancedSearchDialog));
    }

    public boolean isCustomerFieldDisplayed() {
        return isElementDisplayed(customerInputField);
    }

    public boolean isWoNumInputFieldDisplayed() {
        return isElementDisplayed(woNumInputField);
    }

    public boolean isStockNumInputFieldDisplayed() {
        return isElementDisplayed(stockNumInputField);
    }

    public boolean isEtaFromInputFieldDisplayed() {
        return isElementDisplayed(etaFromInputField);
    }

    public boolean isEtaToInputFieldDisplayed() {
        return isElementDisplayed(etaToInputField);
    }

    public boolean isVinInputFieldDisplayed() {
        return isElementDisplayed(vinInputField);
    }

    public boolean isOemNumInputFieldDisplayed() {
        return isElementDisplayed(oemNumInputField);
    }

    public boolean isNotesInputFieldDisplayed() {
        return isElementDisplayed(notesInputField);
    }

    public boolean isFromDateInputFieldDisplayed() {
        return isElementDisplayed(fromDateInputField);
    }

    public boolean isToDateInputFieldDisplayed() {
        return isElementDisplayed(toDateInputField);
    }

    public boolean isSearchNameInputFieldDisplayed() {
        return isElementDisplayed(searchNameInputField);
    }

    public boolean isPhaseListBoxDisplayed() {
        return isElementDisplayed(phaseListBox);
    }

    public boolean isWoTypeListBoxDisplayed() {
        return isElementDisplayed(woTypeListBox);
    }

    public boolean isOrderedFromListBoxDisplayed() {
        return isElementDisplayed(orderedFromListBox);
    }

    public boolean isSearchButtonDisplayed() {
        return isElementDisplayed(searchButton);
    }

    public boolean isXButtonDisplayed() {
        return isElementDisplayed(xButton);
    }

    public boolean isClearButtonDisplayed() {
        return isElementDisplayed(clearButton);
    }

    public boolean isSaveButtonDisplayed() {
        return isElementDisplayed(saveButton);
    }

    public boolean isDeleteButtonDisplayed() {
        return isElementDisplayed(deleteButton);
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForLoading();
    }

    private VNextBOPartsManagementAdvancedSearchDialog typeData(WebElement inputField, WebElement autoCompleteList, String data) {
        setData(inputField, data);
        wait.until(ExpectedConditions.attributeToBe(autoCompleteList, "aria-hidden", "false"));
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectDataFromBoxList(List<WebElement> listBox, WebElement list, String data) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(listBox));
        } catch (Exception ignored) {}
        for (WebElement selected : listBox) {
            if (selected.getText().equals(data)) {
                new Actions(driver)
                        .moveToElement(selected)
                        .click()
                        .build()
                        .perform();
                break;
            }
        }
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
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog clickPhaseBox() {
        wait.until(ExpectedConditions.elementToBeClickable(phaseListBox)).click();
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog clickOrderedFromBox() {
        wait.until(ExpectedConditions.elementToBeClickable(orderedFromListBox)).click();
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog clickWoTypeBox() {
        wait.until(ExpectedConditions.elementToBeClickable(woTypeListBox)).click();
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectPhase(String phase) {
        selectOptionInDropDown(phaseDropDown, phaseListBoxOptions, phase);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectOrderedFrom(String orderedFrom) {
        selectOptionInDropDown(orderedFromDropDown, orderedFromListBoxOptions, orderedFrom);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectWoType(String woType) {
        selectOptionInDropDown(woTypeDropDown, woTypeListBoxOptions, woType);
        return this;
    }

    VNextBOPartsManagementAdvancedSearchDialog setData(WebElement inputField, String data) {
        wait.until(ExpectedConditions.elementToBeClickable(inputField)).click();
        inputField.clear();
        inputField.sendKeys(data);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog selectCustomerNameFromBoxList(String customer) {
        return selectDataFromBoxList(customerListBoxOptions, customerAutoCompleteList, customer);
    }

    public void typeCustomerName(String customer) {
        typeData(customerInputField, customerAutoCompleteList, customer);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setCustomer(String customer) {
        typeCustomerName(customer);
        selectCustomerNameFromBoxList(customer);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setPhase(String phase) {
        clickPhaseBox();
        selectPhase(phase);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setOrderedFrom(String orderedFrom) {
        clickOrderedFromBox();
        selectOrderedFrom(orderedFrom);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setWoType(String woType) {
        clickWoTypeBox();
        selectWoType(woType);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setWoNum(String woNum) {
        return setData(woNumInputField, woNum);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setStockNum(String stockNum) {
        return setData(stockNumInputField, stockNum);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setETAFrom(String etaFrom) {
        return setData(etaFromInputField, etaFrom);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setETATo(String etaTo) {
        return setData(etaToInputField, etaTo);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setVin(String vinNum) {
        return setData(vinInputField, vinNum);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setOEMNum(String oemNum) {
        return setData(oemNumInputField, oemNum);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setNotes(String notes) {
        return setData(notesInputField, notes);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setFromDate(String fromDate) {
        return setData(fromDateInputField, fromDate);
    }

    public VNextBOPartsManagementAdvancedSearchDialog setToDate(String toDate) {
        return setData(toDateInputField, toDate);
    }

    public String getETAFromInputField() {
        return getETAValue(etaFromInputField);
    }

    public String getETAToInputField() {
        return getETAValue(etaToInputField);
    }

    private String getETAValue(WebElement etaToInputField) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(etaToInputField)).getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean isCustomerDisplayed(String customer) {
        return isDataDisplayed(customerListBoxOptions, customer);
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickAdvancedSearchHeader() {
        wait.until(ExpectedConditions.elementToBeClickable(advancedSearchPartsOrderingHeader)).click();
        waitABit(1000);
        return this;
    }

    private boolean isDataDisplayed(List<WebElement> listBox, String data) {
        try {
            return wait
                    .until(ExpectedConditions.visibilityOfAllElements(listBox))
                    .stream()
                    .map(WebElement::getText)
                    .anyMatch(e -> e.equals(data));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public VNextBOPartsManagementAdvancedSearchDialog openETAFromCalendarWidget() {
        return openCalendarWidget(etaFromDateCalendarButton, fromDateCalendarWidget);
    }

    public VNextBOPartsManagementAdvancedSearchDialog openETAToCalendarWidget() {
        return openCalendarWidget(etaToDateCalendarButton, toDateCalendarWidget);
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickFocusedDateInFromDateCalendarWidget() {
        clickFocusedDate(fromDateCalendarWidget, focusedDateInFromDateCalendarWidget);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickFocusedDateInToDateCalendarWidget() {
        clickFocusedDate(toDateCalendarWidget, focusedDateInToDateCalendarWidget);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog openCalendarWidget(WebElement calendarButton, WebElement widget) {
        wait.until(ExpectedConditions.elementToBeClickable(calendarButton)).click();
        wait.until(ExpectedConditions.visibilityOf(widget));
        return this;
    }

    private void clickFocusedDate(WebElement calendarWidget, WebElement focusedDateInCalendarWidget) {
        wait.until(ExpectedConditions.elementToBeClickable(focusedDateInCalendarWidget)).click();
        wait.until(ExpectedConditions.invisibilityOf(calendarWidget));
    }
}