package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
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

    @FindBy(xpath = ".//div[@class='intercom-launcher-close-icon']")
    private WebElement intercomClosed;

    @FindBy(id = "footer")
    private WebElement footer;

    @FindBy(xpath = "//a[@data-bind='click: showTermsAndConditions']")
    private WebElement termsAndConditions;

    @FindBy(xpath = "//a[@data-bind='click: showPrivacyPolicy']")
    private WebElement privacyPolicy;

    public VNextBORepairOrdersWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean areTableHeaderTitlesDisplayed(List<String> titles, List<String> repeaterTitles) {
        List<String> extracted = new ArrayList<>();
        List<String> copy = new ArrayList<>();
        waitShort
                .until(ExpectedConditions.visibilityOfAllElements(tableHeader))
                .forEach((title) -> extracted.add(title.getText()));

        final String[] strings = extracted.get(3).split("\n");
        extracted.remove(3);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(extracted.get(i));
            System.out.println(titles.get(i));
            System.out.println(extracted.get(i).equals(titles.get(i)));
            System.out.println("&&&&&&&&&&&&\n");
        }

        return extracted.containsAll(titles) && Arrays.asList(strings).containsAll(repeaterTitles);
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

//    public boolean isIntercomLauncherDisplayed() {
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(intercomClosed));
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public boolean isFooterDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(footer)).isDisplayed();
    }

    public boolean footerContains(String text) {
        return footerContains(footer, text);
    }

    public boolean isTermsAndConditionsLinkDisplayed() {
        return footerContains(termsAndConditions, "Terms and Conditions");
    }

    public boolean isPrivacyPolicyLinkDisplayed() {
        return footerContains(privacyPolicy, "Privacy Policy");
    }

    public boolean footerContains(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().contains(text);
    }

    public VNextBOTermsAndConditionsDialog clickTermsAndConditionsLink() {
        wait.until(ExpectedConditions.visibilityOf(termsAndConditions));
        scrollToElement(termsAndConditions);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(termsAndConditions)).click();
        } catch (WebDriverException e) {
            waitABit(1000);
            clickWithJS(termsAndConditions);
        }
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOTermsAndConditionsDialog.class);
    }

    public VNextBOPrivacyPolicyDialog clickPrivacyPolicyLink() {
        wait.until(ExpectedConditions.visibilityOf(privacyPolicy));
        scrollToElement(privacyPolicy);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(privacyPolicy)).click();
        } catch (WebDriverException e) {
            waitABit(1000);
            clickWithJS(privacyPolicy);
        }
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOPrivacyPolicyDialog.class);
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
