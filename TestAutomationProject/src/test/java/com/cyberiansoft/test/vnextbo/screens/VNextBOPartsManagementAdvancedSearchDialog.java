package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

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

    @FindBy(xpath = "//span[@aria-controls='advSearchPartsOrdering-fromDate_dateview']")
    private WebElement fromDateCalendarButton;

    @FindBy(xpath = "//span[@aria-controls='advSearchPartsOrdering-toDate_dateview']")
    private WebElement toDateCalendarButton;

    @FindBy(id = "partsOrdersSearchEtaFromInput_dateview")
    private WebElement etaFromDateCalendarWidget;

    @FindBy(id = "partsOrdersSearchEtaToInput_dateview")
    private WebElement etaToDateCalendarWidget;

    @FindBy(id = "advSearchPartsOrdering-fromDate_dateview")
    private WebElement fromDateCalendarWidget;

    @FindBy(id = "advSearchPartsOrdering-toDate_dateview")
    private WebElement toDateCalendarWidget;

    @FindBy(xpath = "//div[@id='partsOrdersSearchEtaFromInput_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement focusedDateInETAFromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='partsOrdersSearchEtaToInput_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement focusedDateInETAToDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearchPartsOrdering-fromDate_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement focusedDateInFromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearchPartsOrdering-toDate_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement focusedDateInToDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearchPartsOrdering-fromDate_dateview']//a[@aria-label='Previous']")
    private WebElement previousMonthButtonInFromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearchPartsOrdering-toDate_dateview']//a[@aria-label='Previous']")
    private WebElement previousMonthButtonInToDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearchPartsOrdering-toDate_dateview']//td[contains(@id, 'cell_selected')]")
    private WebElement nextMonthInToDateCalendarWidget;

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

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchPhaseInput_listbox']//span[@class='k-input']")
    private WebElement selectedPhaseOption;

    @FindBy(xpath = "//span[@aria-owns='partsOrdersSearchWOTypesInput_listbox']//span[@class='k-input']")
    private WebElement selectedWoTypeOption;

    @FindBy(xpath = "//span[@aria-owns='advSearchPartsOrdering-orderedFrom_listbox']//span[@class='k-input']")
    private WebElement selectedOrderedFromOption;

    public String getPhaseOption() {
        return wait.until(ExpectedConditions.visibilityOf(selectedPhaseOption)).getText();
    }

    public String getWoTypeOption() {
        return wait.until(ExpectedConditions.visibilityOf(selectedWoTypeOption)).getText();
    }

    public String getOrderedFromOption() {
        return wait.until(ExpectedConditions.visibilityOf(selectedOrderedFromOption)).getText();
    }

    public VNextBOPartsManagementAdvancedSearchDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        return Utils.isElementDisplayed(advancedSearchDialog);
    }

    public boolean isAdvancedSearchDialogNotDisplayed() {
        return wait.until(ExpectedConditions.invisibilityOf(advancedSearchDialog));
    }

    public boolean isCustomerFieldDisplayed() {
        return Utils.isElementDisplayed(customerInputField);
    }

    public boolean isWoNumInputFieldDisplayed() {
        return Utils.isElementDisplayed(woNumInputField);
    }

    public boolean isStockNumInputFieldDisplayed() {
        return Utils.isElementDisplayed(stockNumInputField);
    }

    public boolean isEtaFromInputFieldDisplayed() {
        return Utils.isElementDisplayed(etaFromInputField);
    }

    public boolean isEtaToInputFieldDisplayed() {
        return Utils.isElementDisplayed(etaToInputField);
    }

    public boolean isVinInputFieldDisplayed() {
        return Utils.isElementDisplayed(vinInputField);
    }

    public boolean isOemNumInputFieldDisplayed() {
        return Utils.isElementDisplayed(oemNumInputField);
    }

    public boolean isNotesInputFieldDisplayed() {
        return Utils.isElementDisplayed(notesInputField);
    }

    public boolean isFromDateInputFieldDisplayed() {
        return Utils.isElementDisplayed(fromDateInputField);
    }

    public boolean isToDateInputFieldDisplayed() {
        return Utils.isElementDisplayed(toDateInputField);
    }

    public boolean isSearchNameInputFieldDisplayed() {
        return Utils.isElementDisplayed(searchNameInputField);
    }

    public boolean isPhaseListBoxDisplayed() {
        return Utils.isElementDisplayed(phaseListBox);
    }

    public boolean isWoTypeListBoxDisplayed() {
        return Utils.isElementDisplayed(woTypeListBox);
    }

    public boolean isOrderedFromListBoxDisplayed() {
        return Utils.isElementDisplayed(orderedFromListBox);
    }

    public boolean isSearchButtonDisplayed() {
        return Utils.isElementDisplayed(searchButton);
    }

    public boolean isXButtonDisplayed() {
        return Utils.isElementDisplayed(xButton);
    }

    public boolean isClearButtonDisplayed() {
        return Utils.isElementDisplayed(clearButton);
    }

    public boolean isSaveButtonDisplayed() {
        return Utils.isElementDisplayed(saveButton);
    }

    public boolean isDeleteButtonDisplayed() {
        return Utils.isElementDisplayed(deleteButton);
    }

    public String getCustomerInputFieldValue() {
        return Utils.getInputFieldValue(customerInputField);
    }

    public String getWoInputFieldValue() {
        return Utils.getInputFieldValue(woNumInputField);
    }

    public String getStockInputFieldValue() {
        return Utils.getInputFieldValue(stockNumInputField);
    }

    public String getVinInputFieldValue() {
        return Utils.getInputFieldValue(vinInputField);
    }

    public String getOemInputFieldValue() {
        return Utils.getInputFieldValue(oemNumInputField);
    }

    public String getNotesInputFieldValue() {
        return Utils.getInputFieldValue(notesInputField);
    }

    public String getSearchNameInputFieldValue() {
        return Utils.getInputFieldValue(searchNameInputField);
    }

    public String getETAFromInputField() {
        return Utils.getInputFieldValue(etaFromInputField);
    }

    public String getETAToInputField() {
        return Utils.getInputFieldValue(etaToInputField);
    }

    public String getFromInputField() {
        return Utils.getInputFieldValue(fromDateInputField);
    }

    public String getToInputField() {
        return Utils.getInputFieldValue(toDateInputField);
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForLoading();
    }

    public void clickXButton() {
        wait.until(ExpectedConditions.elementToBeClickable(xButton)).click();
    }

    public void clickClearButton() {
        wait.until(ExpectedConditions.elementToBeClickable(clearButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(clearButton));
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        waitForLoading();
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickDeleteSavedSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        waitForLoading();
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog typeData(WebElement inputField, WebElement autoCompleteList, String data) {
        setData(inputField, data);
        WaitUtilsWebDriver.waitForAttributeToBe(autoCompleteList, "aria-hidden", "false");
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog clickPhaseBox() {
        Utils.clickElement(phaseListBox);
        waitABit(1000);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog clickOrderedFromBox() {
        Utils.clickElement(orderedFromListBox);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog clickWoTypeBox() {
        Utils.clickElement(woTypeListBox);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectPhase(String phase) {
        Utils.selectOptionInDropDown(phaseDropDown, phaseListBoxOptions, phase, true);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectOrderedFrom(String orderedFrom) {
        Utils.selectOptionInDropDown(orderedFromDropDown, orderedFromListBoxOptions, orderedFrom, true);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog selectWoType(String woType) {
        Utils.selectOptionInDropDown(woTypeDropDown, woTypeListBoxOptions, woType, true);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog selectCustomerNameFromBoxList(String customer) {
        Utils.selectOptionInDropDown(customerAutoCompleteList, customerListBoxOptions, customer, true);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog typeCustomerName(String customer) {
        Utils.clearAndType(customerInputField, customer);
        return this;
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
       Utils.clearAndType(woNumInputField, woNum);
       return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setStockNum(String stockNum) {
        Utils.clearAndType(stockNumInputField, stockNum);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setETAFrom(String etaFrom) {
        Utils.clearAndType(etaFromInputField, etaFrom);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setETATo(String etaTo) {
        Utils.clearAndType(etaToInputField, etaTo);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setVin(String vinNum) {
        Utils.clearAndType(vinInputField, vinNum);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setOEMNum(String oemNum) {
        Utils.clearAndType(oemNumInputField, oemNum);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setNotes(String notes) {
        Utils.clearAndType(notesInputField, notes);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setFromDate(String fromDate) {
        Utils.clearAndType(fromDateInputField, fromDate);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setToDate(String toDate) {
        Utils.clearAndType(toDateInputField, toDate);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog setSearchName(String searchName) {
        Utils.clearAndType(searchNameInputField, searchName);
        return this;
    }

    public boolean isCustomerDisplayed(String customer) {
        return isDataDisplayed(customerListBoxOptions, customer);
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickAdvancedSearchHeader() {
        Utils.clickElement(advancedSearchPartsOrderingHeader);
        waitABit(1000);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog openETAFromCalendarWidget() {
        return openCalendarWidget(etaFromDateCalendarButton, etaFromDateCalendarWidget);
    }

    public VNextBOPartsManagementAdvancedSearchDialog openETAToCalendarWidget() {
        return openCalendarWidget(etaToDateCalendarButton, etaToDateCalendarWidget);
    }

    public VNextBOPartsManagementAdvancedSearchDialog openFromCalendarWidget() {
        return openCalendarWidget(fromDateCalendarButton, fromDateCalendarWidget);
    }

    public VNextBOPartsManagementAdvancedSearchDialog openToCalendarWidget() {
        return openCalendarWidget(toDateCalendarButton, toDateCalendarWidget);
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickFocusedDateInETAFromDateCalendarWidget() {
        clickFocusedDate(etaFromDateCalendarWidget, focusedDateInETAFromDateCalendarWidget);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickFocusedDateInETAToDateCalendarWidget() {
        clickFocusedDate(etaToDateCalendarWidget, focusedDateInETAToDateCalendarWidget);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickFocusedDateInFromDateCalendarWidget() {
        clickFocusedDate(fromDateCalendarWidget, focusedDateInFromDateCalendarWidget);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog navigateLeftMinusMonthInFromDateCalendarWidget() {
        wait.until(ExpectedConditions.elementToBeClickable(previousMonthButtonInFromDateCalendarWidget)).click();
        waitABit(500);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog navigateLeftMinusMonthInToDateCalendarWidget() {
        wait.until(ExpectedConditions.elementToBeClickable(previousMonthButtonInToDateCalendarWidget)).click();
        waitABit(500);
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickFocusedDateInToDateCalendarWidget() {
        clickFocusedDate(toDateCalendarWidget, focusedDateInToDateCalendarWidget);
        return this;
    }

    private VNextBOPartsManagementAdvancedSearchDialog openCalendarWidget(WebElement calendarButton, WebElement widget) {
        wait.until(ExpectedConditions.elementToBeClickable(calendarButton)).click();
        wait.until(ExpectedConditions.visibilityOf(widget));
        waitABit(1000);
        return this;
    }

    private void clickFocusedDate(WebElement calendarWidget, WebElement focusedDateInCalendarWidget) {
        wait.until(ExpectedConditions.elementToBeClickable(focusedDateInCalendarWidget)).click();
        wait.until(ExpectedConditions.invisibilityOf(calendarWidget));
    }

    public void verifyAdvancedSearchFieldsValues(String customer, String phase, String woType, String woNum, String stockNum, String currentDate,
                       String vinNum, String oemNum, String notes, String orderedFrom, String searchName) {
        Assert.assertEquals(customer, getCustomerInputFieldValue(),
                "The customer name hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(phase, getPhaseOption(),
                "The Phase option hasn't been displayed properly after closing and opening the Advanced Search dialog");
        Assert.assertEquals(woType, getWoTypeOption(),
                "The WO type option hasn't been displayed properly after closing and opening the Advanced Search dialog");
        Assert.assertEquals(woNum, getWoInputFieldValue(),
                "The wo# hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(stockNum, getStockInputFieldValue(),
                "The stock# hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(currentDate, getETAFromInputField(),
                "The ETA From hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(currentDate, getETAToInputField(),
                "The ETA To hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(vinNum, getVinInputFieldValue(),
                "The VIN# hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(oemNum, getOemInputFieldValue(),
                "The OEM# hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(notes, getNotesInputFieldValue(),
                "The Notes value hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(currentDate, getFromInputField(),
                "The From date hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(currentDate, getToInputField(),
                "The To date hasn't been displayed after closing and opening the Advanced Search dialog");
        Assert.assertEquals(orderedFrom, getOrderedFromOption(),
                "The Ordered From option hasn't been displayed properly after closing and opening the Advanced Search dialog");
        Assert.assertEquals(searchName, getSearchNameInputFieldValue(),
                "The search name hasn't been displayed after closing and opening the Advanced Search dialog");
    }

    public void verifyAdvancedSearchFieldsValues(String defaultValue, String phase, String woType) {
        verifyAdvancedSearchFieldsValues(defaultValue, phase, woType, defaultValue, defaultValue, defaultValue,
                defaultValue, defaultValue, defaultValue, defaultValue, defaultValue);
    }
}