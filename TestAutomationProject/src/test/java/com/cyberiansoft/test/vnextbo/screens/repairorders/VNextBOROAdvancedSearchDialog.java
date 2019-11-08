package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOCalendarWidgetDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

@Getter
public class VNextBOROAdvancedSearchDialog extends VNextBOBaseWebPage {

    @FindBy(className = "advSearch")
    private WebElement advancedSearchDialog;

    @FindBy(xpath = "//div[@id='customSearchContainer']//div[@id='customPopup']")
    private WebElement advancedSearchDialogContainer;

    @FindBy(id = "teamclientsAutocomplete")
    private WebElement customerInputField;

    @FindBy(id = "orderWoInput")
    private WebElement woInputField;

    @FindBy(id = "orderRoInput")
    private WebElement roInputField;

    @FindBy(id = "orderStockInput")
    private WebElement stockInputField;

    @FindBy(id = "orderVinInput")
    private WebElement vinInputField;

    @FindBy(id = "orderSearchNameInput")
    private WebElement searchNameInputField;

    @FindBy(id = "teamclientsAutocomplete-list")
    private WebElement customerAutoCompleteList;

    @FindBy(id = "repairOrdersSearchEmployees")
    private WebElement employeeInputField;

    @FindBy(id = "repairOrdersSearchEmployees-list")
    private WebElement employeeAutoCompleteList;

    @FindBy(xpath = "//span[@aria-owns='orderPhaseDropdown_listbox']")
    private WebElement phaseListBox;

    @FindBy(id = "orderPhaseDropdown_listbox")
    private WebElement phaseDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderPhaseStatusDropdown_listbox']")
    private WebElement phaseStatusListBox;

    @FindBy(id = "orderPhaseStatusDropdown_listbox")
    private WebElement phaseStatusDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderDepartmentDropdown_listbox']")
    private WebElement departmentListBox;

    @FindBy(id = "orderDepartmentDropdown-list")
    private WebElement departmentDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderTypeDropdownlist_listbox']")
    private WebElement woTypeListBox;

    @FindBy(id = "orderTypeDropdownlist_listbox")
    private WebElement woTypeDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInPhaseDropdown_listbox']")
    private WebElement daysInPhaseListBox;

    @FindBy(xpath = "//span[@aria-owns='orderFlagDropdown_listbox']")
    private WebElement flagsListBox;

    @FindBy(id = "orderDaysInPhaseDropdown_listbox")
    private WebElement daysInPhaseDropDown;

    @FindBy(id = "orderFlagDropdown_listbox")
    private WebElement flagDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderTimeframeDropdown_listbox']")
    private WebElement timeFrameListBox;

    @FindBy(id = "orderTimeframeDropdown_listbox")
    private WebElement timeFrameDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderStatusDropdownlist_listbox']")
    private WebElement repairStatusListBox;

    @FindBy(id = "orderStatusDropdownlist_listbox")
    private WebElement repairStatusDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderStatusDropdownlist_listbox']//span[@class='k-input']")
    private WebElement repairStatusValue;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInProcessDropdown_listbox']")
    private WebElement daysInProcessListBox;

    @FindBy(id = "orderDaysInProcessDropdown_listbox")
    private WebElement daysInProcessDropDown;

    @FindBy(xpath = "//ul[@id='teamclientsAutocomplete_listbox']/li")
    private List<WebElement> customerListBoxOptions;

    @FindBy(xpath = "//ul[@id='repairOrdersSearchEmployees_listbox']/li")
    private List<WebElement> employeeListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderPhaseDropdown_listbox']/li")
    private List<WebElement> phaseListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderPhaseStatusDropdown_listbox']/li")
    private List<WebElement> phaseStatusListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderDepartmentDropdown_listbox']/li")
    private List<WebElement> departmentListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderTypeDropdownlist_listbox']/li")
    private List<WebElement> woTypeListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderDaysInPhaseDropdown_listbox']/li")
    private List<WebElement> daysInPhaseListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderFlagDropdown_listbox']/li//span[not(@class)]")
    private List<WebElement> flagsListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderDaysInProcessDropdown_listbox']/li")
    private List<WebElement> daysInProcessListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderTimeframeDropdown_listbox']/li")
    private List<WebElement> timeFrameListBoxOptions;

    @FindBy(xpath = "//ul[@id='orderStatusDropdownlist_listbox']/li")
    private List<WebElement> repairStatusListBoxOptions;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInPhaseFrom')]")
    private WebElement daysInPhaseFromInput;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInPhaseTo')]")
    private WebElement daysInPhaseToInput;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInProcessFrom')]")
    private WebElement daysInProcessFromInput;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInProcessTo')]")
    private WebElement daysInProcessToInput;

    @FindBy(id = "advSearch_fromDate")
    private WebElement customTimeFrameFromInput;

    @FindBy(xpath = "advSearch_toDate")
    private WebElement customTimeFrameToInput;

    @FindBy(xpath = "//span[@aria-controls='advSearch_fromDate_dateview']")
    private WebElement fromDateButton;

    @FindBy(xpath = "//span[@aria-controls='advSearch_toDate_dateview']")
    private WebElement toDateButton;

    @FindBy(xpath = "//span[@aria-owns='orderFlagDropdown_listbox']//span[@class='k-input']")
    private WebElement flagDisplayed;

    @FindBy(xpath = "//button[@class='btn-black pull-right']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='advSearch']//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class='advSearch']//button[contains(text(), 'Clear')]")
    private WebElement clearButton;

    @FindBy(xpath = "//div[@class='advSearch']//button[contains(text(), 'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[@class='advSearch']//i[@class='icon-close pull-right']")
    private WebElement advancedSearchCloseButton;

    @FindBy(xpath = "//ul[@id='teamclientsAutocomplete_listbox']/li")
    private WebElement customerSelection;

    @FindBy(xpath = "//ul[@id='repairOrdersSearchEmployees_listbox']/li")
    private WebElement employeeSelection;

    @FindBy(xpath = "//span[@aria-owns='orderPhaseDropdown_listbox']//span[@class='k-input']")
    private WebElement phaseSelection;

    @FindBy(xpath = "//span[@aria-owns='orderPhaseStatusDropdown_listbox']//span[@class='k-input']")
    private WebElement phaseStatusSelection;

    @FindBy(xpath = "//span[@aria-owns='orderTypeDropdownlist_listbox']//span[@class='k-input']")
    private WebElement woTypeSelection;

    @FindBy(xpath = "//span[@aria-owns='orderTimeframeDropdown_listbox']//span[@class='k-input']")
    private WebElement timeFrameSelection;

    @FindBy(xpath = "//span[@aria-owns='orderDepartmentDropdown_listbox']//span[@class='k-input']")
    private WebElement departmentSelection;

    @FindBy(xpath = "//span[@aria-owns='orderStatusDropdownlist_listbox']//span[@class='k-input']")
    private WebElement repairStatusSelection;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInProcessDropdown_listbox']//span[@class='k-input']")
    private WebElement daysInProcessSelection;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInPhaseDropdown_listbox']//span[@class='k-input']")
    private WebElement daysInPhaseSelection;

    @FindBy(xpath = "//span[@aria-owns='orderFlagDropdown_listbox']//span[@class='k-input']")
    private WebElement flagSelection;

    @FindBy(xpath = "//span[@aria-owns='orderSortOptionsDropdown_listbox']//span[@class='k-input']")
    private WebElement sortBySelection;

    public VNextBOROAdvancedSearchDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(advancedSearchDialog)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAdvancedSearchDialogElements() {
        final String text = customerSelection.getText();
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver
                    -> !text.equals(customerSelection.getText()));
        } catch (Exception ignored) {}
        return Arrays.asList(customerSelection.getText(), employeeSelection.getText(), phaseSelection.getText(),
                timeFrameSelection.getText(), departmentSelection.getText(), repairStatusSelection.getText(),
                daysInProcessSelection.getText(), daysInPhaseSelection.getText(), flagSelection.getText());
    }

    public String getCustomerInputFieldValue() {
        return customerInputField.getText();
    }

    public String getEmployeeInputFieldValue() {
        return employeeInputField.getText();
    }

    public String getPhaseInputFieldValue() {
        return phaseSelection.getText();
    }

    public String getPhaseStatusInputFieldValue() {
        return phaseStatusSelection.getText();
    }

    public String getDepartmentInputFieldValue() {
        return departmentSelection.getText();
    }

    public String getWoTypeInputFieldValue() {
        return woTypeSelection.getText();
    }

    public String getWoNumInputFieldValue() {
        return woInputField.getText();
    }

    public String getRoNumInputFieldValue() {
        return roInputField.getText();
    }

    public String getStockInputFieldValue() {
        return stockInputField.getText();
    }

    public String getVinInputFieldValue() {
        return vinInputField.getText();
    }

    public String getTimeFrameInputFieldValue() {
        return timeFrameSelection.getText();
    }

    public String getRepairStatusInputFieldValue() {
        return repairStatusSelection.getText();
    }

    public String getDaysInProcessInputFieldValue() {
        return daysInProcessSelection.getText();
    }

    public String getDaysInPhaseInputFieldValue() {
        return daysInPhaseSelection.getText();
    }

    public String getFlagInputFieldValue() {
        return flagSelection.getText();
    }

    public String getSortByInputFieldValue() {
        return sortBySelection.getText();
    }

    public String getSearchNameInputFieldValue() {
        return searchNameInputField.getAttribute("value");
    }

    public boolean isAdvancedSearchDialogNotDisplayed() {
        return Utils.isElementNotDisplayed(advancedSearchDialog);
    }

    public VNextBOROAdvancedSearchDialog setWoNum(String woNum) {
        Utils.clearAndType(woInputField, woNum);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setRoNum(String roNum) {
        Utils.clearAndType(roInputField, roNum);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setStockNum(String stockNum) {
        Utils.clearAndType(stockInputField, stockNum);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setVinNum(String vinNum) {
        Utils.clearAndType(vinInputField, vinNum);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setSearchName(String searchName) {
        Utils.clearAndType(searchNameInputField, searchName);
        return this;
    }

    public boolean isCustomerDisplayed(String customer) {
        return isDataDisplayed(customerListBoxOptions, customer);
    }

    public boolean isEmployeeDisplayed(String employee) {
        return isDataDisplayed(employeeListBoxOptions, employee);
    }

    public VNextBOROAdvancedSearchDialog selectCustomerNameFromBoxList(String customer) {
        Utils.selectDataFromBoxList(customerListBoxOptions, customerAutoCompleteList, customer);
        return this;
    }

    public VNextBOROAdvancedSearchDialog selectEmployeeNameFromBoxList(String employee) {
        Utils.selectDataFromBoxList(employeeListBoxOptions, employeeAutoCompleteList, employee);
        return this;
    }

    public void typeEmployeeName(String employee) {
        Utils.clearAndType(employeeInputField, employee);
    }

    public void typeCustomerName(String customer) {
        Utils.clearAndType(customerInputField, customer);
    }

    public VNextBOROAdvancedSearchDialog setCustomer(String customer) {
        typeCustomerName(customer);
        selectCustomerNameFromBoxList(customer);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setEmployee(String employee) {
        typeEmployeeName(employee);
        selectEmployeeNameFromBoxList(employee);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setPhase(String phase) {
        clickPhaseBox();
        selectPhase(phase);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setDepartment(String department) {
        clickDepartmentBox();
        selectDepartment(department);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setWoType(String woType) {
        clickWoTypeBox();
        selectWoType(woType);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setDaysInPhase(String daysInPhase) {
        clickDaysInPhaseBox();
        selectDaysInPhase(daysInPhase);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setFlag(String flag) {
        clickFlagBox();
        selectFlag(flag);
        return this;
    }

    public String getFlagSelected() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(flagDisplayed)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public VNextBOROAdvancedSearchDialog setDaysInProcess(String daysInProcess) {
        clickDaysInProcessBox();
        selectDaysInProcess(daysInProcess);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setTimeFrame(String timeFrame) {
        clickTimeFrameBox();
        selectTimeFrame(timeFrame);
        return this;
    }

    public VNextBOROAdvancedSearchDialog setRepairStatus(String repairStatus) {
        clickRepairStatusBox();
        selectRepairStatus(repairStatus);
        return this;
    }

    public VNextBOROAdvancedSearchDialog typeNumberOfDaysForDaysInPhaseFromInput(String days) {
        return typeDays(daysInPhaseFromInput, days);
    }

    public VNextBOROAdvancedSearchDialog typeNumberOfDaysForDaysInPhaseToInput(String days) {
        return typeDays(daysInPhaseToInput, days);
    }

    public VNextBOROAdvancedSearchDialog typeNumberOfDaysForDaysInProcessFromInput(String days) {
        return typeDays(daysInProcessFromInput, days);
    }

    public VNextBOROAdvancedSearchDialog typeNumberOfDaysForDaysInProcessToInput(String days) {
        return typeDays(daysInProcessToInput, days);
    }

    private VNextBOROAdvancedSearchDialog typeDays(WebElement daysInPhaseToInput, String days) {
        wait.until(ExpectedConditions.visibilityOf(daysInPhaseToInput));
        wait.until(ExpectedConditions.elementToBeClickable(daysInPhaseToInput)).clear();
        daysInPhaseToInput.sendKeys(days);
        return this;
    }

    public VNextBOCalendarWidgetDialog clickFromDateButton() {
        return openCalendarWidget(fromDateButton);
    }

    public VNextBOCalendarWidgetDialog clickToDateButton() {
        return openCalendarWidget(toDateButton);
    }

    private VNextBOCalendarWidgetDialog openCalendarWidget(WebElement button) {
        Utils.clickElement(button);
        waitABit(500);
        return PageFactory.initElements(driver, VNextBOCalendarWidgetDialog.class);
    }

    public VNextBOROWebPage clickSearchButton() {
        Utils.clickElement(searchButton);
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOROWebPage.class);
    }

    public VNextBOROWebPage clickSaveButton() {
        Utils.clickElement(saveButton);
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOROWebPage.class);
    }

    public VNextBOROWebPage clickClearButton() {
        Utils.clickElement(clearButton);
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOROWebPage.class);
    }

    public VNextBOConfirmationDialog clickDeleteButton() {
        Utils.clickElement(deleteButton);
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
    }

    private VNextBOROAdvancedSearchDialog clickPhaseBox() {
        Utils.clickElement(phaseListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickDepartmentBox() {
        Utils.clickElement(departmentListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickWoTypeBox() {
        Utils.clickElement(woTypeListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickDaysInPhaseBox() {
        Utils.clickElement(daysInPhaseListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickFlagBox() {
        Utils.clickElement(flagsListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickDaysInProcessBox() {
        Utils.clickElement(daysInProcessListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickTimeFrameBox() {
        Utils.clickElement(timeFrameListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog clickRepairStatusBox() {
        Utils.clickElement(repairStatusListBox);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectPhase(String phase) {
        Utils.selectOptionInDropDown(phaseDropDown, phaseListBoxOptions, phase);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectDepartment(String phase) {
        Utils.selectOptionInDropDown(departmentDropDown, departmentListBoxOptions, phase);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectWoType(String woType) {
        Utils.selectOptionInDropDown(woTypeDropDown, woTypeListBoxOptions, woType);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectDaysInPhase(String daysInPhase) {
        Utils.selectOptionInDropDown(daysInPhaseDropDown, daysInPhaseListBoxOptions, daysInPhase);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectFlag(String flag) {
//        wait.until(ExpectedConditions.visibilityOf(flagDropDown));
//        final Select select = new Select(flagDropDown);
//        select.selectByVisibleText(flag);
        Utils.selectOptionInDropDown(flagDropDown, flagsListBoxOptions, flag, true);
        return this;
    }

    public VNextBOROWebPage clickAdvancedSearchCloseButton() {
        Utils.clickElement(advancedSearchCloseButton);
        return PageFactory.initElements(driver, VNextBOROWebPage.class);
    }

    private VNextBOROAdvancedSearchDialog selectDaysInProcess(String daysInProcess) {
        Utils.selectOptionInDropDown(daysInProcessDropDown, daysInProcessListBoxOptions, daysInProcess);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectTimeFrame(String timeFrame) {
        Utils.selectOptionInDropDown(timeFrameDropDown, timeFrameListBoxOptions, timeFrame, true);
        return this;
    }

    private VNextBOROAdvancedSearchDialog selectRepairStatus(String repairStatus) {
        Utils.selectOptionInDropDown(repairStatusDropDown, repairStatusListBoxOptions, repairStatus);
        return this;
    }

    public String getRepairStatusOption() {
        return WaitUtilsWebDriver.waitForVisibility(repairStatusValue).getText();
    }
}