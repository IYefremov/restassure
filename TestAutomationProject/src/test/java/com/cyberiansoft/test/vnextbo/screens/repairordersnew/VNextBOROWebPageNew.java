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
public class VNextBOROWebPageNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='reconmonitor-orders']/table")
    private WebElement repairOrdersTable;

    @FindBy(xpath = "//tr[contains(@data-bind,'orders.result')]/th")
    private List<WebElement> repairOrdersTableColumnsTitles;

    @FindBy(xpath = "//div[@id='reconmonitor-orders']//p[@class='search-results text-red']")
    private WebElement noRecordsFoundMessage;

    @FindBy(xpath = "//div[@data-bind='text: savedSearch.value.search.name']")
    private WebElement savedSearchDropDownField;

    @FindBy(xpath = "//span[@data-bind='click: savedSearch.click, visible: savedSearch.isEditable']")
    private WebElement editSavedSearchPencilIcon;

    @FindBy(xpath = "//span[@class='savedSearch']")
    private List<WebElement> savedSearchDropDownList;

    @FindBy(xpath = "//div[@id='reconmonitor-orders']/table/tbody/tr")
    private List<WebElement> repairOrdersTableRowsList;

    @FindBy(xpath = "//strong[@data-bind='text: clientName']")
    private List<WebElement> ordersCustomersList;

    @FindBy(xpath = "//div[@data-bind='click: changeTechnicians']")
    private List<WebElement> ordersTechniciansList;

    @FindBy(xpath = "//i[@class='truncated' and @data-bind='click: phaseMenuClicked']")
    private List<WebElement> ordersPhasesList;

    @FindBy(xpath = "//div[@class='rel department']/i[contains(@data-bind,'departmentName')]")
    private List<WebElement> ordersDepartmentsList;

    @FindBy(xpath = "//span[@data-bind='text: orderTypeName']")
    private List<WebElement> woTypesList;

    @FindBy(xpath = "//strong[@data-bind='text: orderNo']")
    private List<WebElement> woNumbersList;

    @FindBy(xpath = "//input[contains(@data-bind, 'value: roNo')]")
    private List<WebElement> roNumbersList;

    @FindBy(xpath = "//input[contains(@data-bind, 'value: stockNo')]")
    private List<WebElement> stockNumbersList;

    @FindBy(xpath = "//td[@class='stockRo']/p/strong")
    private List<WebElement> vinNumbersList;

    @FindBy(xpath = "//input[contains(@data-bind, 'value: poNo')]")
    private List<WebElement> poNumbersList;

    @FindBy(xpath = "//i[@class='icon-problem-indicator']")
    private List<WebElement> problemIndicatorsList;

    @FindBy(xpath = "//b[contains(@data-bind,'text: orderDateF')]")
    private List<WebElement> startDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'red')]/ancestor::tr//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> highPriorityOrdersStartDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'none')]/ancestor::tr//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> normalPriorityOrdersStartDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'green')]/ancestor::tr//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> lowPriorityOrdersStartDatesList;

    @FindBy(xpath = "//tr[@data-id]//strong[contains(@data-bind, 'arbitrationDateDisplay')]")
    private List<WebElement> arbitrationDatesList;

    @FindBy(xpath = "//a[@class='stockRo__invoiceNo']")
    private List<WebElement> invoiceNumbersList;

    @FindBy(xpath = "//div[contains(@data-bind,'click: orderSeeProblems')]")
    private WebElement viewProblemsActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: phaseCheckIn')]")
    private WebElement checkInActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: phaseCheckOut')]")
    private WebElement checkOutActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: closeRO')]")
    private WebElement closeRoActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: showNotes')]")
    private WebElement notesActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: uncloseRO')]")
    private WebElement reopenRoActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: completeActivePhase')]")
    private WebElement completeCurrentPhaseActionButton;

    @FindBy(xpath = "//div[contains(@data-bind,'click: startActivePhase')]")
    private WebElement startPhaseServicesActionButton;

    @FindBy(xpath = "//p/div[@class='menu-item']")
    private List<WebElement> currentPhaseServiceRecords;

    @FindBy(xpath = "//i[@class='no-records']")
    private WebElement currentPhaseNoServicesMessage;

    @FindBy(xpath = "//li[@id='departmentsdropTab']/a")
    private WebElement departmentsSwitcherTab;

    @FindBy(xpath = "//li[@id='phasesdropTab']/a")
    private WebElement phasesSwitcherTab;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen1']//span[@class='k-input']")
    private WebElement departmentsDropdown;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen2']//span[@class='k-input']")
    private WebElement phasesDropdown;

    @FindBy(xpath = "//ul[@id='departmentsdrop_listbox' and @aria-hidden='false']/li/span[@class='pull-left']")
    private List<WebElement> departmentsList;

    @FindBy(xpath = "//ul[@id='departmentsdrop_listbox' and @aria-hidden='false']/li/span[@class='pull-right']")
    private List<WebElement> ordersAmountThroughDepartmentsList;

    @FindBy(xpath = "//ul[@id='phasedrop_listbox' and @aria-hidden='false']/li/span[@class='pull-left']")
    private List<WebElement> phasesList;

    @FindBy(xpath = "//ul[@id='phasesdrop_listbox' and @aria-hidden='false']/li/span[@class='pull-right']")
    private List<WebElement> ordersAmountThroughPhasesList;

    @FindBy(xpath = "//div[@data-bind='visible: orderDescriptionDisplay']")
    private WebElement orderNoteIcon;

    @FindBy(xpath = "//span[contains(@data-bind, 'text: orderDescriptionMini')]")
    private WebElement orderNoteText;

    @FindBy(xpath = "//a[@data-bind='click: onDescriptionClicked']")
    private WebElement orderNoteXIcon;

    @FindBy(xpath = "//div[contains(@class,'priority-icon') and @style='background: green;']/i[@class='icon-arrow-down']")
    private WebElement lowPriorityIcon;

    @FindBy(xpath = "//div[contains(@class,'priority-icon') and @style='background: red;']/i[@class='icon-arrow-up']")
    private WebElement highPriorityIcon;

    public WebElement orderColumnByOrderNumber(String orderNumber) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//strong[@data-bind='text: orderNo' and text()='" + orderNumber + "']/ancestor::td"));
    }

    public WebElement orderRowByOrderNumber(String orderNumber) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//strong[@data-bind='text: orderNo' and text()='" + orderNumber + "']/ancestor::tr"));
    }

    public WebElement savedSearchOptionByName(String searchName) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@class='savedSearch' and text()='" + searchName + "']"));
    }

    public WebElement actionsButtonByOrderNumber(String orderNumber) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//strong[text()='" + orderNumber + "']/ancestor::tr//i[@class='icon-list menu-trigger']"));
    }

    public WebElement flagIconByFlagTitle(String flagTitle) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@title='" + flagTitle + "']/a"));
    }

    public WebElement priorityIconByFlagColor(String color) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[contains(@data-bind,\"priorityColor == '" + color + "'\")]"));
    }

    public WebElement priorityArrowIconByColor(String flagTitle) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@title='" + flagTitle + "']/a"));
    }

    public WebElement problemIndicatorByOrderNumber(String orderNumber) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//strong[text()='" + orderNumber + "']/ancestor::td//i[@class='icon-problem-indicator']"));
    }

    public WebElement departmentFilterDropDownOption(String optionText) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//ul[@id='departmentsdrop_listbox' and @aria-hidden='false']/li/span[text()='" + optionText + "']"));
    }

    public WebElement phaseFilterDropDownOption(String optionText) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//ul[@id='phasesdrop_listbox' and @aria-hidden='false']/li/span[text()='" + optionText + "']"));
    }

    public WebElement orderDepartmentDropDownOption(String department) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//label[@data-bind='text: departmentName' and text()='" + department + "']"));
    }

    public WebElement ordersAmountForDepartment(String department) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//ul[@id='departmentsdrop_listbox' and @aria-hidden='false']//span[text()='" + department + "']/ancestor::li/span[@class='pull-right']"));
    }

    public WebElement ordersAmountForPhaseInTable(String phase) {

        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[@id='departmentTabwideScreen2']//span[text()='" + phase + "']/ancestor::div/span[@class='pull-right']"));
    }

    public VNextBOROWebPageNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}