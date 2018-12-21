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

public class VNextBORepairOrdersAdvancedSearchDialog extends VNextBOBaseWebPage  {

    @FindBy(className = "advSearch")
    private WebElement advancedSearchDialog;

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

    @FindBy(id = "orderDaysInPhaseDropdown_listbox")
    private WebElement daysInPhaseDropDown;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInProcessDropdown_listbox']")
    private WebElement daysInProcessListBox;

    @FindBy(id = "orderDaysInProcessDropdown_listbox")
    private WebElement daysInProcessDropDown;

    @FindBy(xpath = "//ul[@id='teamclientsAutocomplete_listbox']/li")
    private List<WebElement> customerAutoCompleteListBox;

    @FindBy(xpath = "//ul[@id='repairOrdersSearchEmployees_listbox']/li")
    private List<WebElement> employeeAutoCompleteListBox;

    @FindBy(xpath = "//ul[@id='orderPhaseDropdown_listbox']/li")
    private List<WebElement> phaseAutoCompleteListBox;

    @FindBy(xpath = "//ul[@id='orderDepartmentDropdown_listbox']/li")
    private List<WebElement> departmentAutoCompleteListBox;

    @FindBy(xpath = "//ul[@id='orderTypeDropdownlist_listbox']/li")
    private List<WebElement> woTypeAutoCompleteListBox;

    @FindBy(xpath = "//ul[@id='orderDaysInPhaseDropdown_listbox']/li")
    private List<WebElement> daysInPhaseAutoCompleteListBox;

    @FindBy(xpath = "//ul[@id='orderDaysInProcessDropdown_listbox']/li")
    private List<WebElement> daysInProcessAutoCompleteListBox;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInPhaseFrom')]")
    private WebElement daysInPhaseFromInput;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInPhaseTo')]")
    private WebElement daysInPhaseToInput;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInProcessFrom')]")
    private WebElement daysInProcessFromInput;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInProcessTo')]")
    private WebElement daysInProcessToInput;

    @FindBy(xpath = "//button[@class='btn-black pull-right']")
    private WebElement searchButton;

    public VNextBORepairOrdersAdvancedSearchDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(advancedSearchDialog)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAdvancedSearchDialogNotDisplayed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOf(advancedSearchDialog));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private VNextBORepairOrdersAdvancedSearchDialog typeData(WebElement inputField, WebElement autoCompleteList, String data) {
        setData(inputField, data);
        wait.until(ExpectedConditions.attributeToBe(autoCompleteList, "aria-hidden", "false"));
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setWoNum(String woNum) {
        return setData(woInputField, woNum);
    }

    public VNextBORepairOrdersAdvancedSearchDialog setRoNum(String roNum) {
        return setData(roInputField, roNum);
    }

    public VNextBORepairOrdersAdvancedSearchDialog setStockNum(String stockNum) {
        return setData(stockInputField, stockNum);
    }

    public VNextBORepairOrdersAdvancedSearchDialog setVinNum(String vinNum) {
        return setData(vinInputField, vinNum);
    }

    private VNextBORepairOrdersAdvancedSearchDialog setData(WebElement woInputField, String data) {
        wait.until(ExpectedConditions.elementToBeClickable(woInputField)).click();
        woInputField.sendKeys(data);
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

    private VNextBORepairOrdersAdvancedSearchDialog selectDataFromBoxList(List<WebElement> listBox, WebElement list, String data) {
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
            list.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.attributeToBe(list, "aria-hidden", "true"));
        }
        return this;
    }

    public boolean isCustomerDisplayed(String customer) {
        return isDataDisplayed(customerAutoCompleteListBox, customer);
    }

    public boolean isEmployeeDisplayed(String employee) {
        return isDataDisplayed(employeeAutoCompleteListBox, employee);
    }

    public VNextBORepairOrdersAdvancedSearchDialog selectCustomerNameFromBoxList(String customer) {
        return selectDataFromBoxList(customerAutoCompleteListBox, customerAutoCompleteList, customer);
    }

    public VNextBORepairOrdersAdvancedSearchDialog selectEmployeeNameFromBoxList(String employee) {
        return selectDataFromBoxList(employeeAutoCompleteListBox, employeeAutoCompleteList, employee);
    }

    public void typeEmployeeName(String employee) {
        typeData(employeeInputField, employeeAutoCompleteList, employee);
    }

    public void typeCustomerName(String customer) {
        typeData(customerInputField, customerAutoCompleteList, customer);
    }

    public VNextBORepairOrdersAdvancedSearchDialog setCustomer(String customer) {
        typeCustomerName(customer);
        selectCustomerNameFromBoxList(customer);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setEmployee(String employee) {
        typeEmployeeName(employee);
        selectEmployeeNameFromBoxList(employee);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setPhase(String phase) {
        clickPhaseBox();
        selectPhase(phase);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setDepartment(String department) {
        clickDepartmentBox();
        selectDepartment(department);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setWoType(String woType) {
        clickWoTypeBox();
        selectWoType(woType);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setDaysInPhase(String daysInPhase) {
        clickDaysInPhaseBox();
        selectDaysInPhase(daysInPhase);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog setDaysInProcess(String daysInProcess) {
        clickDaysInProcessBox();
        selectDaysInProcess(daysInProcess);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog typeDaysNumForDaysInPhaseFromInput(String days) {
        return typeDays(daysInPhaseFromInput, days);
    }

    public VNextBORepairOrdersAdvancedSearchDialog typeDaysNumForDaysInPhaseToInput(String days) {
        return typeDays(daysInPhaseToInput, days);
    }

    public VNextBORepairOrdersAdvancedSearchDialog typeDaysNumForDaysInProcessFromInput(String days) {
        return typeDays(daysInProcessFromInput, days);
    }

    public VNextBORepairOrdersAdvancedSearchDialog typeDaysNumForDaysInProcessToInput(String days) {
        return typeDays(daysInProcessToInput, days);
    }

    private VNextBORepairOrdersAdvancedSearchDialog typeDays(WebElement daysInPhaseToInput, String days) {
        wait.until(ExpectedConditions.visibilityOf(daysInPhaseToInput));
        wait.until(ExpectedConditions.elementToBeClickable(daysInPhaseToInput)).clear();
        daysInPhaseToInput.sendKeys(days);
        return this;
    }

    public VNextBORepairOrdersWebPage clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrdersWebPage.class);
    }

    private VNextBORepairOrdersAdvancedSearchDialog clickPhaseBox() {
        wait.until(ExpectedConditions.elementToBeClickable(phaseListBox)).click();
        return this;
    }

    private VNextBORepairOrdersAdvancedSearchDialog clickDepartmentBox() {
        wait.until(ExpectedConditions.elementToBeClickable(departmentListBox)).click();
        return this;
    }

    private VNextBORepairOrdersAdvancedSearchDialog clickWoTypeBox() {
        wait.until(ExpectedConditions.elementToBeClickable(woTypeListBox)).click();
        return this;
    }

    private VNextBORepairOrdersAdvancedSearchDialog clickDaysInPhaseBox() {
        wait.until(ExpectedConditions.elementToBeClickable(daysInPhaseListBox)).click();
        return this;
    }

    private VNextBORepairOrdersAdvancedSearchDialog clickDaysInProcessBox() {
        wait.until(ExpectedConditions.elementToBeClickable(daysInProcessListBox)).click();
        return this;
    }

    private VNextBORepairOrdersAdvancedSearchDialog selectPhase(String phase) {
        return selectOptionInDropDown(phaseDropDown, phaseAutoCompleteListBox, phase);
    }

    private VNextBORepairOrdersAdvancedSearchDialog selectDepartment(String phase) {
        return selectOptionInDropDown(departmentDropDown, departmentAutoCompleteListBox, phase);
    }

    private VNextBORepairOrdersAdvancedSearchDialog selectWoType(String woType) {
        return selectOptionInDropDown(woTypeDropDown, woTypeAutoCompleteListBox, woType);
    }

    private VNextBORepairOrdersAdvancedSearchDialog selectDaysInPhase(String daysInPhase) {
        return selectOptionInDropDown(daysInPhaseDropDown, daysInPhaseAutoCompleteListBox, daysInPhase);
    }

    private VNextBORepairOrdersAdvancedSearchDialog selectDaysInProcess(String daysInProcess) {
        return selectOptionInDropDown(daysInProcessDropDown, daysInProcessAutoCompleteListBox, daysInProcess);
    }

    private VNextBORepairOrdersAdvancedSearchDialog selectOptionInDropDown(WebElement dropDown, List<WebElement> listBox,
                                                                          String phase) {
        wait.until(ExpectedConditions.attributeToBe(dropDown, "aria-hidden", "false"));
        wait.until(ExpectedConditions.visibilityOfAllElements(listBox));
        for (WebElement option : listBox) {
            if (option.getText().equals(phase)) {
                new Actions(driver).moveToElement(option).click().build().perform();
                wait.until(ExpectedConditions.attributeToBe(dropDown, "aria-hidden", "true"));
                break;
            }
        }
        return this;
    }
}
