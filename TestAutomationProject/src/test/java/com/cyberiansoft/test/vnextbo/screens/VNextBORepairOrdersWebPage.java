package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBORepairOrdersWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//*[@id='reconmonitor-orders']/table")
    private WebElement reapiroderstable;

    @FindBy(xpath = "//*[@id='repairOrdersFreeTextSearch']")
    private WebElement reapiroderssearchtextfld;

    @FindBy(xpath = "//h5[@id='breadcrumb']//div[@class='drop department-drop']")
    private WebElement locationExpanded;

    @FindBy(xpath = "//span[@class='menu-trigger location-name']")
    private WebElement locationElement;

    @FindBy(xpath = "//div[@id='savedSearchContainer']/span[@class='k-widget k-dropdown k-header']")
    private WebElement savedSearchContainer;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabNoWideScreen1']")
    private WebElement departmentDropdownActive;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabNoWideScreen2']")
    private WebElement phasesDropdownActive;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabwideScreen1']")
    private WebElement departmentWideDropdownActive;

    @FindBy(xpath = "//div[@class='tab-pane active' and @id='departmentTabwideScreen2']")
    private WebElement phasesWideDropdownActive;

    @FindBy(xpath = "//li[@id='departmentsdropTab']")
    private WebElement departmentsTab;

    @FindBy(xpath = "//li[@id='departmentsWideDropTab']")
    private WebElement departmentsWideTab;

    @FindBy(xpath = "//li[@id='departmentsdropTab' and @class='active']")
    private WebElement departmentsTabActive;

    @FindBy(xpath = "//li[@id='departmentsWideDropTab' and @class='active']")
    private WebElement departmentsWideTabActive;

    @FindBy(xpath = "//li[@id='phasesdropTab']")
    private WebElement phasesTab;

    @FindBy(xpath = "//li[@id='phasesWideDropTab']")
    private WebElement phasesWideTab;

    @FindBy(xpath = "//li[@id='phasesdropTab' and @class='active']")
    private WebElement phasesTabActive;

    @FindBy(xpath = "//li[@id='//li[@id='phasesWideDropTab']' and @class='active']")
    private WebElement phasesWideTabActive;

    @FindBy(xpath = "//input[@id='repairOrdersFreeTextSearch']")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@class='search-wrapper']/i[@class='caret']")
    private WebElement advancedSearchCaret;

    @FindBy(xpath = "//table[@id='roTable']//th")
    private List<WebElement> tableHeader;

    @FindBy(xpath = "//table[@id='roTable']//th/div")
    private List<WebElement> tableHeaderRepeater;

    public VNextBORepairOrdersWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		new WebDriverWait(driver, 30)
//		  .until(ExpectedConditions.visibilityOf(reapiroderstable));
    }

    public boolean areTableHeaderTitlesDisplayed(List<String> titles, List<String> titlesRepeater) {
        List<String> extracted = new ArrayList<>();
        waitShort
                .until(ExpectedConditions.visibilityOfAllElements(tableHeader))
                .forEach((title) -> extracted.add(title.getText()));
        tableHeaderRepeater.forEach(title -> extracted.add(title.getText()));
        extracted.forEach(System.out::println);
        return titles.containsAll(titlesRepeater);
    }

    public VNextBORepairOrdersWebPage setLocation(String location) {
        try {
            wait.until(ExpectedConditions.visibilityOf(locationExpanded));
            wait.until(ExpectedConditions
                    .elementToBeClickable(locationExpanded.findElement(By.xpath(".//label[text()='" + location + "']"))))
                    .click();
            waitForLoading();
            Assert.assertTrue(isLocationSelected(location), "The location hasn't been selected");
            closeLocationDropDown();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(locationElement));
            wait.until(ExpectedConditions.visibilityOf(locationExpanded));
        }
        return this;
    }

    private void closeLocationDropDown() {
        try {
            wait.until(ExpectedConditions.invisibilityOf(locationExpanded));
        } catch (Exception e) {
            locationElement.click();
            wait.until(ExpectedConditions.invisibilityOf(locationExpanded));
        }
    }

    private boolean isLocationSelected(String location) {
        try {
            wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions
                            .presenceOfElementLocated(By
                                    .xpath("//div[@class='add-notes-item menu-item active']//label[text()='" + location + "']")));
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }

    public boolean isSavedSearchContainerDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(savedSearchContainer)).isDisplayed();
    }

    public boolean isDepartmentDropdownDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(departmentWideDropdownActive)).isDisplayed();
    }

    public void makeDepartmentsTabActive() {
        makeTabActive(departmentsWideTab, departmentsWideTabActive);
    }

    public boolean isPhasesDropdownDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(phasesWideDropdownActive)).isDisplayed();
    }

    public void makePhasesTabActive() {
        makeTabActive(phasesWideTab, phasesWideTabActive);
    }

    public boolean isSearchInputFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(phasesWideDropdownActive)).isDisplayed();
    }

    public boolean isPhasesTabDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(phasesWideTab)).isDisplayed();
    }

    public boolean isDepartmentsTabDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(departmentsWideTab)).isDisplayed();
    }


    private void makeTabActive(WebElement tab, WebElement tabActive) {
        try {
            waitShort.until(ExpectedConditions.visibilityOf(tabActive));
        } catch (Exception ignored) {
            wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
            waitShort.until(ExpectedConditions.visibilityOf(tabActive));
        }
    }

    public VNextBORepairOrdersWebPage clickPhasesWide() {
        waitShort.until(ExpectedConditions.elementToBeClickable(phasesWideTab)).click();
        return this;
    }

    public boolean isAdvancedSearchCaretDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(advancedSearchCaret)).isDisplayed();
    }

    public void searchRepairOrderByNumber(String repairorderNumber) {
        setRepairOrdersSearchText(repairorderNumber);
        clickSearchIcon();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='k-loading-mask']")));
    }

    public void setRepairOrdersSearchText(String repairordertext) {
        reapiroderssearchtextfld.clear();
        reapiroderssearchtextfld.sendKeys(repairordertext);
    }

    public void clickSearchIcon() {
        driver.findElement(By.xpath("//div[@class='search-wrapper']")).findElement(By.xpath(".//*[@class='icon-search']")).click();
    }

    public WebElement getTableRowWithWorkOrder(String orderNumber) {
        WebElement wotablerow = null;
        List<WebElement> wotablerows = getRepairOrdersTableBody().findElements(By.xpath("./tr"));
        for (WebElement row : wotablerows)
            if (row.findElement(By.xpath(".//*[@class='order-no']/strong")).getText().trim().equals(orderNumber)) {
                wotablerow = row;
                break;
            }
        return wotablerow;
    }

    public String getWorkOrderActivePhaseValue(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        return wotablerow.findElement(By.xpath(".//td[@class='phase']")).getText().trim();
    }

    public String getWorkOrderDaysInProgressValue(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        return wotablerow.findElement(By.xpath(".//td[@class='days']/div/p")).getText().trim();
    }

    public void clickWorkOrderOtherMenuButton(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        wotablerow.findElement(By.xpath(".//i[@data-bind='click: dropMenuClicked']")).click();
    }

    public void clickStartRoForWorkOrder(String orderNumber) {
        clickWorkOrderOtherMenuButton(orderNumber);
        driver.findElement(By.xpath("//i[@class='icon-start-ro']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 15, 1);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='k-loading-mask']")));
    }

    public void clickWorkOrderOtherPhaseMenu(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        wotablerow.findElement(By.xpath(".//i[@data-bind='click: phaseMenuClicked']")).click();
    }

    public void completeWorkOrderServiceStatus(String orderNumber, String serviceName) {
        clickWorkOrderOtherPhaseMenu(orderNumber);
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        List<WebElement> services = wotablerow.findElements(
                By.xpath(".//*[@data-bind='click: changeServiceStatus']"));
        for (WebElement srv : services)
            if (srv.getText().trim().contains(serviceName)) {
                srv.click();
                break;
            }

        WebDriverWait wait = new WebDriverWait(driver, 15, 1);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='k-loading-mask']")));
    }

    public String getCompletedWorkOrderValue(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        return wotablerow.findElement(
                By.xpath("./td[8]/span")).getText();
    }

    public boolean isRepairOrderPresentInTable(String orderNumber) {
        return getRepairOrdersTableBody().findElements(By.xpath(".//strong[text()='" + orderNumber + "']")).size() > 0;
    }

    public WebElement getRepairOrdersTableBody() {
        return reapiroderstable.findElement(By.xpath(".//*[@id='tableBody']"));
    }

    public VNextBORepairOrderDetailsPage openWorkOrderDetailsPage(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        wotablerow.findElement(By.xpath(".//a/strong[text()='" + orderNumber + "']")).click();
        return new VNextBORepairOrderDetailsPage(driver);
    }

}
