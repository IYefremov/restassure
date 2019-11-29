package com.cyberiansoft.test.vnextbo.screens.repairorders;

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

    @FindBy(id = "repairOrders-search-hasProblem")
    private WebElement hasProblemsCheckbox;

    public VNextBOROAdvancedSearchDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getTimeFrameOption(String timeFrame) {
        return driver.findElement(By.xpath("//ul[@id='orderTimeframeDropdown_listbox']/li[text()='" + timeFrame + "']"));
    }
}