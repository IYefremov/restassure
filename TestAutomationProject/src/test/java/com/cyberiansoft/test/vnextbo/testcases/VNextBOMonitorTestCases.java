package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOMonitorData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNexBOLeftMenuPanel leftMenu;

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
                VNextBOHeaderPanel.class);
        if (headerpanel.logOutLinkExists())
            headerpanel.userLogout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());

        Assert.assertTrue(repairOrdersPage.isSavedSearchContainerDisplayed(),
                "The search container isn't displayed");

//        repairOrdersPage.makeDepartmentsTabActive(); todo add verifies for narrow screen, if necessary
//        Assert.assertTrue(repairOrdersPage.isDepartmentsTabDisplayed(),
//                "The department tab isn't displayed");

        Assert.assertTrue(repairOrdersPage.isDepartmentDropdownDisplayed(),
                "The department dropdown is not displayed");

        repairOrdersPage.clickPhasesWide();
        Assert.assertTrue(repairOrdersPage.isPhasesDropdownDisplayed(),
                "The phases dropdown is not displayed");

//        repairOrdersPage.makePhasesTabActive();
//        Assert.assertTrue(repairOrdersPage.isPhasesTabDisplayed(),
//                "The phases tab isn't displayed");

        Assert.assertTrue(repairOrdersPage.isSearchInputFieldDisplayed(),
                "The search input field isn't displayed");
        Assert.assertTrue(repairOrdersPage.isAdvancedSearchCaretDisplayed(),
                "The advanced search caret isn't displayed");
        Assert.assertTrue(repairOrdersPage.areTableHeaderTitlesDisplayed(data.getTitles(), data.getTitlesRepeater()),
                "The table header titles aren't displayed");
//        Assert.assertTrue(repairOrdersPage.isIntercomLauncherDisplayed(),
//                "The Intercom launcher is not displayed");
        Assert.assertTrue(repairOrdersPage.isFooterDisplayed(),
                "The footer is not displayed");
        Assert.assertTrue(repairOrdersPage.footerContains(data.getCopyright()),
                "The footer doesn't contain text " + data.getCopyright());
        Assert.assertTrue(repairOrdersPage.footerContains(data.getAMT()),
                "The footer doesn't contain text " + data.getAMT());
        Assert.assertTrue(repairOrdersPage.isTermsAndConditionsLinkDisplayed(),
                "The footer doesn't contain Terms and Conditions link");
        Assert.assertTrue(repairOrdersPage.isPrivacyPolicyLinkDisplayed(),
                "The footer doesn't contain Privacy Policy link");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseTermsAndConditions(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .clickTermsAndConditionsLink()
                .scrollTermsAndConditionsDown()
                .rejectTermsAndConditions()
                .clickTermsAndConditionsLink()
                .acceptTermsAndConditions();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .clickPrivacyPolicyLink()
                .scrollPrivacyPolicyDown()
                .rejectPrivacyPolicy()
                .clickPrivacyPolicyLink()
                .acceptPrivacyPolicy();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .openIntercom()
                .closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLocationUsingSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        Assert.assertTrue(repairOrdersPage.isLocationSearched(data.getSearchLocation()),
                "The location is not searched");
        repairOrdersPage.clickLocationInDropDown(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSelected(data.getLocation()), "The location hasn't been selected");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUsePaging(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
        repairOrdersPage.clickNextButton();
        Assert.assertFalse(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
        repairOrdersPage.clickPrevButton();
        Assert.assertTrue(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVIN(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getVinNum())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getVinNum())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrderNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getRoNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getRoNumber())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByFirstName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getFirstName())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getFirstName())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getLastName())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getLastName())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmail(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getEmail())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByEmail(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by email after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getEmail())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByEmail(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by email after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCompanyName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getCompany())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByCompanyName(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by company name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getCompany())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByCompanyName(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by company name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog.typeCustomerName(data.getCustomer());
        Assert.assertTrue(advancedSearchDialog.isCustomerDisplayed(data.getCustomer()));
        advancedSearchDialog
                .selectCustomerNameFromBoxList(data.getCustomer())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmployee(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .typeEmployeeName(data.getEmployee());

        Assert.assertTrue(advancedSearchDialog.isEmployeeDisplayed(data.getEmployee()));
        advancedSearchDialog
                .selectEmployeeNameFromBoxList(data.getEmployee())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setPhase(data.getPhase())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDepartment(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setDepartment(data.getDepartment())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoType(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setWoType(data.getWoType())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setWoNum(data.getWoNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setRoNum(data.getRoNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStockNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setStockNum(data.getStockNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVinNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setVinNum(data.getVinNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeDaysNumForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeDaysNumForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeDaysNumForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeDaysNumForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeDaysNumForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeDaysNumForDaysInPhaseFromInput(data.getDaysNumStart())
                .typeDaysNumForDaysInPhaseToInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeDaysNumForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeDaysNumForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        final VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog = repairOrdersPage
                .setLocation(data.getLocation())
                .clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeDaysNumForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }
}