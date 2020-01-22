package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

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
public class VNextBOROAdvancedSearchDialogNew extends VNextBOBaseWebPage {

    @FindBy(className = "advSearch")
    private WebElement advancedSearchDialog;

    @FindBy(xpath = "//button[@data-bind='click: search.start']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='advSearch']//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class='advSearch']//button[contains(text(), 'Clear')]")
    private WebElement clearButton;

    @FindBy(xpath = "//div[@class='advSearch']//button[contains(text(), 'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[@class='advSearch']//i[@class='icon-close pull-right']")
    private WebElement closeButton;

    @FindBy(xpath = "//input[@id='repairOrdersSearchHasText']")
    private WebElement hasThisTextInputField;

    @FindBy(xpath = "//input[@id='teamclientsAutocomplete']")
    private WebElement customerInputField;

    @FindBy(xpath = "//div[@id='teamclientsAutocomplete-list']//ul")
    private WebElement customerAutoCompleteList;

    @FindBy(xpath = "//input[@id='repairOrdersSearchEmployees']")
    private WebElement employeeInputField;

    @FindBy(xpath = "//div[@id='repairOrdersSearchEmployees-list']//ul")
    private WebElement employeeAutoCompleteList;

    @FindBy(xpath = "//span[@aria-owns='orderPhaseDropdown_listbox']//span[@class='k-input']")
    private WebElement phaseDropDown;

    @FindBy(xpath = "//ul[@id='orderPhaseDropdown_listbox']")
    private WebElement phaseDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderPhaseStatusDropdown_listbox']//span[@class='k-input']")
    private WebElement phaseStatusDropDown;

    @FindBy(xpath = "//ul[@id='orderPhaseStatusDropdown_listbox']")
    private WebElement phaseStatusDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderTaskDropdown_listbox']//span[@class='k-input']")
    private WebElement taskDropDown;

    @FindBy(xpath = "//ul[@id='orderTaskDropdown_listbox']")
    private WebElement taskDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderTaskDropdown_listbox']//span[@class='k-input']")
    private WebElement taskStatusDropDown;

    @FindBy(xpath = "//ul[@id='orderTaskDropdown_listbox']")
    private WebElement taskStatusDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderDepartmentDropdown_listbox']//span[@class='k-input']")
    private WebElement departmentDropDown;

    @FindBy(xpath = "//ul[@id='orderDepartmentDropdown_listbox']")
    private WebElement departmentDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderTypeDropdownlist_listbox']//span[@class='k-input']")
    private WebElement woTypeDropDown;

    @FindBy(xpath = "//ul[@id='orderTypeDropdownlist_listbox']")
    private WebElement woTypeDropDownList;

    @FindBy(xpath = "//input[@id='orderWoInput']")
    private WebElement woNumberInputField;

    @FindBy(xpath = "//input[@id='orderRoInput']")
    private WebElement roNumberInputField;

    @FindBy(xpath = "//input[@id='orderStockInput']")
    private WebElement stockNumberInputField;

    @FindBy(xpath = "//input[@id='orderVinInput']")
    private WebElement vinInputField;

    @FindBy(xpath = "//span[@aria-owns='orderTimeframeDropdown_listbox']//span[@class='k-input']")
    private WebElement timeFrameDropDown;

    @FindBy(xpath = "//ul[@id='orderTimeframeDropdown_listbox']")
    private WebElement timeFrameDropDownList;

    @FindBy(xpath = "//input[@id='advSearch_fromDate']")
    private WebElement fromInputField;

    @FindBy(xpath = "//input[@id='advSearch_toDate']")
    private WebElement toInputField;

    @FindBy(xpath = "//span[@aria-owns='orderStatusDropdownlist_listbox']//span[@class='k-input']")
    private WebElement repairStatusDropDown;

    @FindBy(xpath = "//ul[@id='orderStatusDropdownlist_listbox']")
    private WebElement repairStatusDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInPhaseDropdown_listbox']//span[@class='k-input']")
    private WebElement daysInPhaseDropDown;

    @FindBy(xpath = "//ul[@id='orderDaysInPhaseDropdown_listbox']")
    private WebElement daysInPhaseDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderDaysInProcessDropdown_listbox']//span[@class='k-input']")
    private WebElement daysInProcessDropDown;

    @FindBy(xpath = "//ul[@id='orderDaysInProcessDropdown_listbox']")
    private WebElement daysInProcessDropDownList;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInProcessFrom')]")
    private WebElement daysInProcessFromValue;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInProcessTo')]")
    private WebElement daysInProcessToValue;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInPhaseFrom')]")
    private WebElement daysInPhaseFromValue;

    @FindBy(xpath = "//input[contains(@data-bind, 'daysInPhaseTo')]")
    private WebElement daysInPhaseToValue;

    @FindBy(xpath = "//span[@aria-owns='orderFlagDropdown_listbox']//span[@class='k-input']")
    private WebElement flagDropDown;

    @FindBy(xpath = "//ul[@id='orderFlagDropdown_listbox']")
    private WebElement flagDropDownList;

    @FindBy(xpath = "//span[@aria-owns='orderSortOptionsDropdown_listbox']//span[@class='k-input']")
    private WebElement sortByDropDown;

    @FindBy(xpath = "//ul[@id='orderSortOptionsDropdown_listbox']")
    private WebElement sortByDropDownList;

    @FindBy(xpath = "//input[@id='repairOrders-search-hasProblem']")
    private WebElement hasProblemCheckBox;

    @FindBy(xpath = "//input[@id='repairOrders-search-hasProblem']")
    private WebElement searchNameInputField;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()=\"" + optionName + "\"]"));
    }

    public WebElement flagDropDownFieldOption(String flag) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//span[text()=\"" + flag + "\"]"));
    }

    public VNextBOROAdvancedSearchDialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}