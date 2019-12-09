package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOROWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//*[@id='reconmonitor-orders']/table")
    private WebElement repairOrdersTable;

    @FindBy(xpath = "//*[@id='repairOrdersFreeTextSearch']")
    private WebElement repairOrdersSearchTextField;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
    private WebElement locationExpanded;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']/ul[@class='scroll-pane-locations']//label")
    private List<WebElement> locationLabels;

    @FindBy(id = "locSearchInput")
    private WebElement locationSearchInput;

    @FindBy(xpath = "//span[contains(@class, 'location-name')]")
    private WebElement locationElement;

    @FindBy(xpath = "//div[@id='reconmonitor-saved-search']/div[@class='dropdown']")
    private WebElement savedSearchContainer;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabNoWideScreen1']")
    private WebElement departmentNarrowDropdownActive;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabNoWideScreen2']")
    private WebElement phasesNarrowDropdownActive;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabwideScreen1']")
    private WebElement departmentWideDropdownActive;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabwideScreen2']")
    private WebElement phasesWideDropdownActive;

    @FindBy(xpath = "//li[@id='departmentsdropTab']")
    private WebElement departmentsNarrowTab;

    @FindBy(xpath = "//li[@id='departmentsWideDropTab']")
    private WebElement departmentsWideTab;

    @FindBy(xpath = "//li[@id='departmentsdropTab' and @class='active']")
    private WebElement departmentsTabActive;

    @FindBy(xpath = "//div[@id='departmentTabwideScreen1']//span[@class='pull-right']")
    private List<WebElement> allDepartmentsWideScreenValues;

    @FindBy(xpath = "//div[@id='departmentTabwideScreen2']//span[@class='pull-right']")
    private List<WebElement> allPhasesWideScreenValues;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen1']")
    private WebElement departmentsTabPane;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen2']")
    private WebElement phasesTabPane;

    @FindBy(xpath = "//div[@class='k-animation-container']//ul[@id='departmentsdrop_listbox']//span[@class='pull-right']")
    private List<WebElement> allDepartmentsNarrowScreenValues;

    @FindBy(xpath = "//div[@id='phasesdrop-list']//span[@class='pull-right']")
    private List<WebElement> allPhasesNarrowScreenValues;

    @FindBy(xpath = "//li[@id='departmentsWideDropTab' and @class='active']")
    private WebElement departmentsWideTabActive;

    @FindBy(xpath = "//li[@id='departmentsdropTab' and @class='active']")
    private WebElement departmentsNarrowTabActive;

    @FindBy(xpath = "//li[@id='phasesdropTab']")
    private WebElement phasesNarrowTab;

    @FindBy(xpath = "//li[@id='phasesWideDropTab']")
    private WebElement phasesWideTab;

    @FindBy(xpath = "//li[@id='phasesdropTab' and @class='active']")
    private WebElement phasesNarrowTabActive;

    @FindBy(xpath = "//li[@id='phasesWideDropTab' and @class='active']")
    private WebElement phasesWideTabActive;

    @FindBy(xpath = "//input[@id='repairOrdersFreeTextSearch']")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@class='search-wrapper']/i[@class='caret']")
    private WebElement advancedSearchCaret;

    @FindBy(xpath = "//table[@id='roTable']//th")
    private List<WebElement> tableHeader;

    @FindBy(xpath = "//table[@id='roTable']//th/div")
    private List<WebElement> tableHeaderRepeater;

    @FindBy(xpath = "//div[@aria-label='Close Intercom Messenger']")
    private WebElement closeIntercomButton;

    @FindBy(xpath = "//div[contains(@class, 'intercom-launcher')]")
    private WebElement intercom;

    @FindBy(xpath = "//iframe[@name='intercom-launcher-frame']")
    private WebElement intercomFrame;

    @FindBy(xpath = "//div[contains(@class, 'intercom-launcher' ) and contains (@class, 'active')]")
    private WebElement intercomOpen;

    @FindBy(id = "footer")
    private WebElement footer;

    @FindBy(xpath = "//a[@data-bind='click: showTermsAndConditions']")
    private WebElement termsAndConditions;

    @FindBy(xpath = "//a[@data-bind='click: showPrivacyPolicy']")
    private WebElement privacyPolicy;

    @FindBy(xpath = "//div[@id='reconmonitor-orders']//tfoot//button[contains(@data-bind, 'pager.nextPage')]")
    private WebElement nextButton;

    @FindBy(xpath = "//div[@id='reconmonitor-orders']//tfoot//button[contains(@data-bind, 'pager.previousPage')]")
    private WebElement prevButton;

    @FindBy(xpath = "//div[@class='search-wrapper']//i[@class='icon-search']")
    private WebElement searchIcon;

    @FindBy(xpath = "//div[@id='customSearchContainer']//i[@class='icon-cancel-circle']")
    private WebElement cancelSearchIcon;

    @FindBy(xpath = "//p[text()='Search']")
    private WebElement searchText;

    @FindBy(xpath = "//div[@id='departmentTabwideScreen1']//span[@title][not(@title='ALL')]")
    private List<WebElement> departmentsOptionsForWideScreen;

    @FindBy(xpath = "//div[@id='departmentTabwideScreen2']//span[@title][not(@title='ALL')]")
    private List<WebElement> phasesOptionsForWideScreen;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen1']//span[@title]")
    private WebElement departmentsNarrowScreen;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen2']//span[@title]")
    private WebElement phasesNarrowScreen;

    @FindBy(xpath = "departmentTabNoWideScreen1")
    private WebElement narrowScreen;

    @FindBy(xpath = "//div[@id='departmentTabNoWideScreen1' and contains(@class, 'active')]")
    private WebElement wideScreen;

    @FindBy(xpath = "//ul[@id='departmentsdrop_listbox' and @aria-hidden='false']//span[@class='pull-left']")
    private List<WebElement> departmentsNarrowScreenDropDownOptions;

    @FindBy(xpath = "//ul[@id='phasesdrop_listbox' and @aria-hidden='false']//span[@class='pull-left']")
    private List<WebElement> phasesNarrowScreenDropDownOptions;

    @FindBy(xpath = "//tbody[@id='tableBody' and @data-template='rowTemplate']/tr")
    private List<WebElement> ordersDisplayedOnPage;

    @FindBy(xpath = "//table[@id='roTable']")
    private WebElement table;

    @FindBy(xpath = "//p[contains(@data-bind, 'orders.result') and contains(text(), 'No records')]")
    private WebElement noRecordsFound;

    @FindBy(xpath = "//tbody[@id='tableBody' and contains(@data-bind, 'orders.result')]")
    private WebElement tableBody;

    @FindBy(xpath = "//div[@id='reconmonitor-saved-search']//i[@class='caret']")
    private WebElement savedSearchArrow;

    @FindBy(xpath = "//div[@data-template='reconmonitor-saved-search-select-item-template']//span[@class='savedSearch']")
    private List<WebElement> savedSearchDropDownOptions;

    @FindBy(xpath = "//div[@id='reconmonitor-saved-search']//div[@class='dropdown__list']")
    private WebElement savedSearchDropDown;

    @FindBy(xpath = "//div[@id='reconmonitor-saved-search']/span[@class='icon-pencil']")
    private WebElement savedSearchEditIcon;

    @FindBy(className = "searchResults")
    private WebElement searchResults;

    @FindBy(className = "//input[@title='PO #']")
    private WebElement poNumTitle;

    @FindBy(xpath = "//div[contains(@data-bind, 'menuVisible')]")
    private WebElement otherDropDown;

    @FindBy(xpath = "//i[@class='icon-arrow-down']")
    private WebElement arrowDown;

    @FindBy(xpath = "//i[@class='icon-arrow-up']")
    private WebElement arrowUp;

    @FindBy(className = "breadcrumbs")
    private WebElement mainBreadCrumbsLink;

    @FindBy(className = "order-notes")
    private WebElement roNotesLink;

    @FindBy(xpath = "//label[text()='View Problems']")
    private WebElement viewProblemsLink;

    @FindBy(xpath = "//a[@class='order-no']")
    private List<WebElement> woNumbersList;

    @FindBy(xpath = "//ul[@data-template='repairOrders-filterInfoList-item']/li")
    private List<WebElement> searchOptions;

    @FindBy(xpath = "//b[contains(@data-bind, 'orderDateF')]")
    private List<WebElement> startDateOrdersList;

    @FindBy(xpath = "//b[contains(@data-bind, 'targetDateF')]")
    private List<WebElement> targetDateOrdersList;

    @FindBy(xpath = "//td[@class='phase']//i[contains(@class, 'truncated')]")
    private List<WebElement> ordersCurrentPhaseList;

    @FindBy(xpath = "//tbody[@id='tableBody']//i[@class='icon-problem-indicator']")
    private List<WebElement> problemIndicatorsList;

    @FindBy(xpath = "//input[@title='RO #']")
    private List<WebElement> roNumbersList;

    @FindBy(xpath = "//tr[@data-id]//strong[contains(@data-bind, 'arbitrationDateDisplay')]")
    private List<WebElement> arbitrationDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon')]")
    private List<WebElement> priorityIconsList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'red')]/../..//b[contains(@data-bind, 'targetDateF')]")
    private List<WebElement> highPriorityOrdersTargetDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'none')]/../..//b[contains(@data-bind, 'targetDateF')]")
    private List<WebElement> normalPriorityOrdersTargetDatesList;

    @FindBy(xpath = "//tbody[@id='tableBody']//div[contains(@class, 'priority-icon') and contains(@style, 'green')]/../..//b[contains(@data-bind, 'targetDateF')]")
    private List<WebElement> lowPriorityOrdersTargetDatesList;

    public VNextBOROWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getRandomOrderNumber() {
        final List<WebElement> orders =
                WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(woNumbersList);
        return orders != null ? orders.get(RandomUtils.nextInt(0, orders.size())) : null;
    }

    public WebElement getTableRowWithWorkOrder(String orderNumber) {
        WebElement woTableRow = null;
        List<WebElement> woTableRows = getRepairOrdersTableBody().findElements(By.xpath("./tr"));
        for (WebElement row : woTableRows)
            if (row.findElement(By.xpath(".//*[@class='order-no']/strong")).getText().trim().equals(orderNumber)) {
                woTableRow = row;
                break;
            }
        return woTableRow;
    }

    public WebElement getRepairOrdersTableBody() {
        return WaitUtilsWebDriver.waitForVisibility(tableBody);
    }

    public WebElement getTechniciansFieldForWO(String woNumber) {
        return tableBody.findElement(By.xpath("//strong[text()=\"" + woNumber
                + "\"]/../../../parent::tr//div[contains(@data-bind, \"changeTechnicians\")]"));
    }

    public WebElement getProblemIndicatorForOrder(String order) {
        return driver.findElement(By.xpath("//a[@class='order-no']/strong[text()='"
                + order + "']/../../i[@class='icon-problem-indicator']"));
    }
}