package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.google.common.base.CharMatcher;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBORepairOrdersWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//*[@id='reconmonitor-orders']/table")
    private WebElement repairOrdersTable;

    @FindBy(xpath = "//*[@id='repairOrdersFreeTextSearch']")
    private WebElement reapiroderssearchtextfld;

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

    @FindBy(xpath = "//ul[@data-template='repairOrders-filterInfoList-item']/li")
    private List<WebElement> searchOptions;


    public VNextBORepairOrdersWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public VNextBORepairOrdersWebPage movePointerToSearchResultsField() {
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        actions.moveToElement(searchResults).build().perform();
        return this;
    }

    public List<String> getSearchResultsList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchOptions));
        return searchOptions
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean areTableHeaderTitlesDisplayed(List<String> titles, List<String> repeaterTitles) {
        List<String> extracted = new ArrayList<>();
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

    public VNextBORepairOrdersWebPage clickLocationInDropDown(String location) {
        locationLabels.stream().filter(loc -> loc.getText().contains(location)).findFirst().ifPresent(WebElement::click);
        waitForLoading();
        return this;
    }

    public void clickSavedSearchArrow() {
        clickWithJS(savedSearchArrow);
        try {
            wait.until(ExpectedConditions.visibilityOf(savedSearchDropDown));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectSavedSearchDropDownOption(String option) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(savedSearchDropDownOptions));
            final WebElement webElement = savedSearchDropDownOptions
                    .stream()
                    .filter(o -> o.getText().equals(option))
                    .findFirst()
                    .get();
            actions.moveToElement(webElement).click().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForLoading();
    }

    public VNextBORepairOrdersWebPage setSavedSearchOption(String option) {
        clickSavedSearchArrow();
        selectSavedSearchDropDownOption(option);
        return this;
    }

    public VNextBORepairOrdersAdvancedSearchDialog clickEditIconForSavedSearch() {
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(savedSearchEditIcon);
        return PageFactory.initElements(driver, VNextBORepairOrdersAdvancedSearchDialog.class);
    }

    public VNextBORepairOrdersWebPage clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        waitForLoading();
        return this;
    }

    public VNextBORepairOrdersWebPage clickPrevButton() {
        wait.until(ExpectedConditions.elementToBeClickable(prevButton)).click();
        waitForLoading();
        return this;
    }

    public boolean isPrevButtonDisabled() {
        try {
            return waitShort.until(ExpectedConditions.attributeToBe(prevButton, "disabled", "true"));
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isSavedSearchContainerDisplayed() {
        return Utils.isElementDisplayed(savedSearchContainer);
    }

    public boolean isDepartmentDropdownDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(departmentWideDropdownActive));
            return true;
        } catch (Exception ignored) {
            try {
                wait.until(ExpectedConditions.visibilityOf(departmentNarrowDropdownActive));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public void setDepartmentsTabActive() {
        makeTabActive(departmentsWideTab, departmentsWideTabActive, departmentsNarrowTab, departmentsNarrowTabActive);
    }

    public String getAllDepartmentsSum() {
        return getCalculatedSum(allDepartmentsWideScreenValues);
    }

    public String getAllPhasesSum() {
        return getCalculatedSum(allPhasesWideScreenValues);
    }

    private String getCalculatedSum(List<WebElement> values) {
        if (areValuesDisplayed(values)) {
            String sum = values.get(0).getText();
            System.out.println("Sum: " + sum);
            return sum;
        }
        return "";
    }

    public String getDepartmentsValues() {
        return calculateSum(allDepartmentsWideScreenValues);
    }

    public String getPhasesValues() {
        return calculateSum(allPhasesWideScreenValues);
    }

    private String calculateSum(List<WebElement> values) {
        if (areValuesDisplayed(values)) {
            final List<WebElement> collection = values.subList(1, values.size());
            final String calculatedSum = collection
                    .stream()
                    .map(value -> {
                        if (value.getText().equals("")) {
                            return 0;
                        }
                        return Integer.valueOf(value.getText());
                    })
                    .collect(Collectors.toList())
                    .stream()
                    .reduce((val1, val2) -> val1 + val2)
                    .get()
                    .toString();
            System.out.println("Sum calculation: " + calculatedSum);
            return calculatedSum;
        }
        return "";
    }

    private boolean areValuesDisplayed(List<WebElement> values) {
        try {
            wait.until((ExpectedCondition<Boolean>) sum -> !values.get(0).getText().equals(""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getValue(List<WebElement> values, int order) {
        if (areValuesDisplayed(values)) {
            return values.get(order).getText();
        } else {
            return null;
        }
    }

    public String getDepartmentsValue(int order) {
        return handleTabValues(order, allDepartmentsWideScreenValues, allDepartmentsNarrowScreenValues, departmentsTabPane);
    }

    public String getPhasesValue(int order) {
        return handleTabValues(order, allPhasesWideScreenValues, allPhasesNarrowScreenValues, phasesTabPane);
    }

    private String handleTabValues(int order, List<WebElement> allPhasesWideScreenValues,
                                   List<WebElement> allPhasesNarrowScreenValues, WebElement phasesTabPane) {
        if (areValuesDisplayed(allPhasesWideScreenValues)) {
            final String value = getValue(allPhasesWideScreenValues, order);
            assert value != null;
            return value.equals("") ? "0" : value;
        } else {
            try {
                waitShort.until(ExpectedConditions.visibilityOfAllElements(allPhasesNarrowScreenValues));
            } catch (Exception ignored) {
                wait.until(ExpectedConditions.elementToBeClickable(phasesTabPane)).click();
                waitABit(1000);
            }
            String value = getValue(allPhasesNarrowScreenValues, order);
            System.out.println("VALUE: " + value + " ORDER: " + order);
            if (value == null) {
                value = "0";
            }
            System.out.println("VALUE after replacement: " + CharMatcher.inRange('0', '9').retainFrom(value));
            value = CharMatcher.inRange('0', '9').retainFrom(value);
            wait.until(ExpectedConditions.elementToBeClickable(phasesTabPane)).click();
            return value.equals("") ? "0" : value;
        }
    }

    public VNextBORepairOrdersWebPage clickDepartmentForWideScreen(String department) {
        clickTabForWideScreen(department, departmentsOptionsForWideScreen);
        return this;
    }

    public VNextBORepairOrdersWebPage clickDepartmentForNarrowScreen(String department) {
        clickOptionForNarrowScreen(department, departmentsTabPane, departmentsNarrowScreenDropDownOptions);
        return this;
    }

    public VNextBORepairOrdersWebPage clickPhaseForWideScreen(String phase) {
        clickTabForWideScreen(phase, phasesOptionsForWideScreen);
        return this;
    }

    public VNextBORepairOrdersWebPage clickPhaseForNarrowScreen(String phase) {
        clickOptionForNarrowScreen(phase, phasesTabPane, phasesNarrowScreenDropDownOptions);
        return this;
    }

    private void clickTabForWideScreen(String phase, List<WebElement> phasesOptionsForWideScreen) {
        wait.until(ExpectedConditions.visibilityOfAllElements(phasesOptionsForWideScreen));
        final WebElement element = phasesOptionsForWideScreen
                .stream()
                .filter(ph -> ph.getText().equals(phase))
                .findFirst()
                .get();
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        waitForLoading();
    }

    private void clickOptionForNarrowScreen(String phase, WebElement phasesTabPane, List<WebElement> phasesNarrowScreenDropDownOptions) {
        isDepartmentNarrowScreenClickable();
//        departmentsNarrowScreen.click();
        wait.until(ExpectedConditions.elementToBeClickable(phasesTabPane)).click();
        waitABit(1000);
        System.out.println(phase);
        final WebElement element = phasesNarrowScreenDropDownOptions
                .stream()
                .filter(dep -> dep.getText().equals(phase))
                .findFirst()
                .get();
        System.out.println("Getting text element: " + element.getText());
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        waitForLoading();
    }

    public boolean isDepartmentNarrowScreenClickable() {
        return isScreenClickable(departmentsNarrowScreen);
    }

    public boolean isPhasesNarrowScreenClickable() {
        return isScreenClickable(phasesNarrowScreen);
    }

    private boolean isScreenClickable(WebElement phasesNarrowScreen) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(phasesNarrowScreen));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public int getNumOfOrdersOnPage() {
        wait.until(ExpectedConditions.visibilityOfAllElements(ordersDisplayedOnPage));
        return ordersDisplayedOnPage.size();
    }

    public boolean isTableDisplayed() {
        try {
            waitShort.until(ExpectedConditions.visibilityOf(table));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isTextNoRecordsDisplayed() {
        try {
            waitShort.until(ExpectedConditions.visibilityOf(noRecordsFound));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isPhasesDropdownDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(phasesWideDropdownActive)).isDisplayed();
    }

    public void setPhasesTabActive() {
        makeTabActive(phasesWideTab, phasesWideTabActive, phasesNarrowTab, phasesNarrowTabActive);
    }

    public boolean isSearchInputFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(phasesWideDropdownActive)).isDisplayed();
    }

    public boolean isPhasesTabDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(phasesWideTab)).isDisplayed();
            return true;
        } catch (Exception ignored) {
            try {
                wait.until(ExpectedConditions.visibilityOf(phasesNarrowTab)).isDisplayed();
                return true;
            } catch (Exception ignored1) {
                return false;
            }
        }

    }

    public boolean isDepartmentsTabDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(departmentsWideTab)).isDisplayed();
            return true;
        } catch (Exception ignored) {
            try {
                wait.until(ExpectedConditions.visibilityOf(departmentsNarrowTab)).isDisplayed();
                return true;
            } catch (Exception ignored1) {
                return false;
            }
        }
    }

//    public boolean isIntercomLauncherDisplayed() {
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(closeIntercomButton));
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public VNextBORepairOrdersWebPage openIntercom() {
        wait.until(ExpectedConditions.visibilityOf(intercomFrame));
        driver.switchTo().frame(intercomFrame);
        wait.until(ExpectedConditions.elementToBeClickable(intercom)).click();
        wait.until(ExpectedConditions.visibilityOf(intercomOpen));
        return this;
    }

    public VNextBORepairOrdersWebPage closeIntercom() {
        wait.until(ExpectedConditions.elementToBeClickable(closeIntercomButton)).click();
        wait.until(ExpectedConditions.visibilityOf(intercom));
        driver.switchTo().window(driver.getWindowHandle());
        return this;
    }

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

    private void makeTabActive(WebElement wideTab, WebElement wideTabActive, WebElement narrowTab, WebElement narrowTabActive) {
        try {
            waitShort.until(ExpectedConditions.visibilityOf(wideTabActive));
        } catch (Exception ignored) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(wideTab)).click();
                waitShort.until(ExpectedConditions.visibilityOf(wideTabActive));
            } catch (Exception e) {
                wait.until(ExpectedConditions.elementToBeClickable(narrowTab)).click();
                waitShort.until(ExpectedConditions.visibilityOf(narrowTabActive));
            }
        }
    }

    public VNextBORepairOrdersWebPage clickPhasesWide() {
        waitShort.until(ExpectedConditions.elementToBeClickable(phasesWideTab)).click();
        return this;
    }

    public boolean isAdvancedSearchCaretDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(advancedSearchCaret)).isDisplayed();
    }

    public VNextBORepairOrdersAdvancedSearchDialog clickAdvancedSearchCaret() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(advancedSearchCaret)).click();
        } catch (TimeoutException ignored) {
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(advancedSearchCaret)).click();
        }
        return PageFactory.initElements(driver, VNextBORepairOrdersAdvancedSearchDialog.class);
    }

    public void searchRepairOrderByNumber(String roNumber) {
        setRepairOrdersSearchText(roNumber);
        clickSearchIcon();
    }

    public VNextBORepairOrdersWebPage setRepairOrdersSearchText(String repairOrderText) {
        wait.until(ExpectedConditions.visibilityOf(reapiroderssearchtextfld));
        reapiroderssearchtextfld.clear();
        reapiroderssearchtextfld.sendKeys(repairOrderText);
        waitABit(500);
        return this;
    }

    public VNextBORepairOrdersWebPage clickSearchIcon() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        } catch (Exception e) {
            e.printStackTrace();
        }
        actions.moveToElement(searchIcon).click().build().perform();
        waitForLoading();
        return this;
    }

    public void clickEnterToSearch() {
        actions.sendKeys(searchInput, Keys.ENTER).build().perform();
        waitForLoading();
    }

    public boolean isWorkOrderDisplayedByVin(String vin) {
        return isWorkOrderDisplayed(vin);
    }

    private boolean isWorkOrderDisplayed(String text) {
        try {
            return wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(tableBody
                            .findElement(By.xpath(".//strong[text()=\"" + text + "\"]"))))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTableTitleDisplayed(int titleHeaderNumber) {
        wait.until(ExpectedConditions.visibilityOfAllElements(tableHeader));
        return tableHeader.get(titleHeaderNumber).getText();
    }

    private boolean isWorkOrderDisplayedByPartialText(String text) {
        try {
            return wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(tableBody
                            .findElement(By.xpath(".//strong[contains(text(), '" + text + "')]"))))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoteForWorkOrderDisplayed(String woNumber) {
//        return isArrowDisplayed(woNumber, "']/../../..//div[@class='dark box']");//strong[text()='O-000-152073']/../../..
        return isArrowDisplayed(woNumber, "/../../..//div[@class='dark box']");//strong[text()='O-000-152073']/../../..
    }

    public VNextBORepairOrdersWebPage closeNoteForWorkOrder(String woNumber) {
        if (isNoteForWorkOrderDisplayed(woNumber)) {
            System.out.println("Yes, displayed");
            waitForLoading();
            clickNoteForWo(woNumber);
            System.out.println("clicked");
        } else {
            System.out.println("not displayed");
        }
        return this;
    }

    public VNextBORepairOrdersWebPage openNoteForWorkOrder(String woNumber) {
        if (!isNoteForWorkOrderDisplayed(woNumber)) {
            clickNoteForWo(woNumber);
        }
        return this;
    }

    public VNextBORepairOrdersWebPage clickXIconToCloseNoteForWorkOrder(String woNumber) {
        if (isNoteForWorkOrderDisplayed(woNumber)) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//strong[text()='" + woNumber
                    + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']//a[@class='x']")))
                    .click();
        }
        return this;
    }

    private void clickNoteForWo(String woNumber) {
        wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(driver
                        .findElement(By.xpath("//strong[text()='" + woNumber
                                + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']"))))
                .click();
    }

    public VNextBORepairOrderDetailsPage clickWoLink(String woNumber) {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(By
                .xpath("//a[@class='order-no']/strong[text()='" + woNumber + "']/..")))
                .click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public boolean isWoTypeDisplayed(String woType) {
        wait.until(ExpectedConditions.visibilityOf(tableBody));
        return tableBody.findElements(By.xpath(".//div"))
                .stream()
                .anyMatch(e -> e
                        .getText()
                        .contains(woType));
    }

    public boolean isArrowDownDisplayed(String wo) {
        return isArrowDisplayed(wo, "//i[@class='icon-arrow-down']");
    }

    public boolean isArrowUpDisplayed(String wo) {
        return isArrowDisplayed(wo, "//i[@class='icon-arrow-up']");
    }

    private boolean isArrowDisplayed(String wo, String arrow) {
        try {
            waitShort.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='" +
                            wo + "']/../../.." + arrow)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public VNextBOBaseWebPage setWoDepartment(String woNumber, String department) {
        closeNoteForWorkOrder(woNumber);
        wait.until(ExpectedConditions.elementToBeClickable(tableBody.findElement(By.xpath("//a[@class='order-no']" +
                "/strong[text()='" + woNumber + "']/../following-sibling::div/i[@class='menu-trigger']")))).click();
        final WebElement departmentDropDown = tableBody.findElement(By.xpath("//a[@class='order-no']/strong[text()='"
                + woNumber + "']/../..//div[@class='drop department-drop']"));
        wait.until(ExpectedConditions.visibilityOf(departmentDropDown));
        final List<WebElement> dropDownOptions = departmentDropDown.findElements(By.xpath(".//label"));
        wait.until(ExpectedConditions.visibilityOfAllElements(dropDownOptions));
        dropDownOptions
                .stream()
                .filter(e -> e.getText().equals(department))
                .findFirst()
                .ifPresent(WebElement::click);
        wait.until(ExpectedConditions.invisibilityOf(departmentDropDown));
        waitABit(1000);
        return this;
    }

    public String getWoDepartment(String woNumber) {
        return wait.until(ExpectedConditions
                .visibilityOf(tableBody.findElement(By.xpath("//a[@class='order-no']" +
                        "/strong[text()='" + woNumber + "']/../following-sibling::div/i[@class='menu-trigger']"))))
                .getText();
    }

    public boolean isWorkOrderDisplayedByOrderNumber(String orderNumber) {
        return isWorkOrderDisplayed(orderNumber);
    }

    public boolean isWorkOrderDisplayedByRoNumber(String roNumber) {
        return isWorkOrderDisplayed(roNumber);
    }

    public boolean isWorkOrderDisplayedByFirstName(String firstName) {
        return isWorkOrderDisplayedByPartialText(firstName);
    }

    public boolean isWorkOrderDisplayedByLastName(String lastName) {
        return isWorkOrderDisplayedByPartialText(lastName);
    }

    public boolean isWorkOrderDisplayedByName(String name) {
        return isWorkOrderDisplayed(name);
    }

    public boolean isWorkOrderDisplayedAfterSearchByEmail(String name) {
        return isWorkOrderDisplayed(name);
    }

    public boolean isWorkOrderDisplayedAfterSearchByCompanyName(String name) {
        return isWorkOrderDisplayed(name);
    }

    public VNextBORepairOrdersWebPage clickCancelSearchIcon() {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(cancelSearchIcon);
        waitForLoading();
        return this;
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

    public VNextBORepairOrderDetailsPage clickRepairOrderInTable(String repairOrder) {
        WebElement roTableRow = getTableRowWithWorkOrder(repairOrder);
        System.out.println(roTableRow);
        waitShort.until(ExpectedConditions.elementToBeClickable(roTableRow
                .findElement(By.xpath("//a[contains(@href, '" + repairOrder + "')]"))))
                .click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public VNextBOInvoicesDescriptionWindow clickInvoiceNumberInTable(String ro, String invoice) {
        WebElement roTableRow = getTableRowWithWorkOrder(ro);
        System.out.println(roTableRow);
        waitShort.until(ExpectedConditions.elementToBeClickable(roTableRow
                .findElement(By.xpath("//a[@class='stockRo__invoiceNo' and text()='" + invoice + "']"))))
                .click();
        waitForLoading();
        waitForNewTab();
        return PageFactory.initElements(driver, VNextBOInvoicesDescriptionWindow.class);
    }

    public VNextBOOtherDialog openOtherDropDownMenu(String ro) {
        WebElement roTableRow = getTableRowWithWorkOrder(ro);
        final WebElement icon = roTableRow
                .findElement(By.xpath("//td[@class='grid__actions']//i[contains(@class, 'icon-arrow')]"));
        wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
        wait.until(ExpectedConditions.visibilityOf(otherDropDown));
        return PageFactory.initElements(driver, VNextBOOtherDialog.class);
    }

    public boolean isRepairOrderPresentInTable(String orderNumber) {
        return getRepairOrdersTableBody().findElements(By.xpath(".//strong[text()='" + orderNumber + "']")).size() > 0;
    }

    public WebElement getRepairOrdersTableBody() {
        return wait.until(ExpectedConditions.visibilityOf(tableBody));
    }

    public VNextBORepairOrderDetailsPage openWorkOrderDetailsPage(String orderNumber) {
        WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
        wotablerow.findElement(By.xpath(".//a/strong[text()='" + orderNumber + "']")).click();
        return new VNextBORepairOrderDetailsPage(driver);
    }

    public void clickSearchTextToCloseLocationDropDown() {
        wait.until(ExpectedConditions.elementToBeClickable(searchText)).click();
        wait.until(ExpectedConditions.invisibilityOf(locationExpanded));
    }

    public boolean isPoNumClickable() {
        try {
            waitShort.until(ExpectedConditions.elementToBeClickable(poNumTitle));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
