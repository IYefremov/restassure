package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOPartsManagementSearchTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOPartsManagementSearchData.json";
    private VNexBOLeftMenuPanel leftMenu;
    private VNextBOBreadCrumbPanel breadCrumbPanel;
    private VNextBODashboardPanel dashboardPanel;
    private VNextBOPartsManagementSearchPanel partsManagementSearch;
    private VNextBOPartsOrdersListPanel partsOrdersListPanel;
    private VNextBOFooterPanel footerPanel;
    private VNextBOPartsDetailsPanel partsDetailsPanel;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

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
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);

        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
        breadCrumbPanel = PageFactory.initElements(webdriver, VNextBOBreadCrumbPanel.class);
        dashboardPanel = PageFactory.initElements(webdriver, VNextBODashboardPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsOrdersListPanel = PageFactory.initElements(webdriver, VNextBOPartsOrdersListPanel.class);
        footerPanel = PageFactory.initElements(webdriver, VNextBOFooterPanel.class);
        partsDetailsPanel = PageFactory.initElements(webdriver, VNextBOPartsDetailsPanel.class);
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
    public void verifyUserCanSearchROUsingSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getType())
                .clickSearchLoupeIcon()
                .setPartsSearchText(data.getType());
        final List<String> stockNumOptions = partsOrdersListPanel.getStockNumOptions();
        final boolean containsStockNum = stockNumOptions
                .stream()
                .peek(System.out::println)
                .allMatch(stock -> stock
                        .contains(data.getType()));
        Assert.assertTrue(containsStockNum,
                "The Parts Orders list doesn't contain stock numbers corresponding to the search mask");
        Assert.assertTrue(partsManagementSearch.isSearchXIconDisplayed(), "The search X icon hasn't been displayed");
        Assert.assertEquals("Text: " + data.getType(), partsManagementSearch.getSearchOptionsInfoText(),
                "The search options info text is not displayed correctly");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getType())
                .clickSearchLoupeIcon()
                .setPartsSearchText(data.getType());
        final List<String> stockNumOptions = partsOrdersListPanel.getStockNumOptions();
        final boolean containsStockNum = stockNumOptions
                .stream()
                .peek(System.out::println)
                .allMatch(stock -> stock
                        .contains(data.getType()));
        Assert.assertTrue(containsStockNum,
                "The Parts Orders list doesn't contain stock numbers corresponding to the search mask");
        Assert.assertTrue(partsManagementSearch.isSearchXIconDisplayed(), "The search X icon hasn't been displayed");
        Assert.assertEquals("Text: " + data.getType(), partsManagementSearch.getSearchOptionsInfoText(),
                "The search options info text is not displayed correctly");

        partsManagementSearch.clickSearchXIcon();
        Assert.assertTrue(partsManagementSearch.isSearchOptionsInfoTextEmpty(),
                "The search options info text field hasn't been cleared after clicking the 'X' icon");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseAdvancedSearchForROSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog =
                partsManagementSearch.clickAdvancedSearchOption();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isCustomerFieldDisplayed(),
                "The advanced search dialog customer field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isWoNumInputFieldDisplayed(),
                "The advanced search dialog wo# input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isStockNumInputFieldDisplayed(),
                "The advanced search dialog stock# input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isEtaFromInputFieldDisplayed(),
                "The advanced search dialog ETA from input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isEtaToInputFieldDisplayed(),
                "The advanced search dialog ETA to input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isVinInputFieldDisplayed(),
                "The advanced search dialog VIN input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isOemNumInputFieldDisplayed(),
                "The advanced search dialog OEM input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isNotesInputFieldDisplayed(),
                "The advanced search dialog notes input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isFromDateInputFieldDisplayed(),
                "The advanced search dialog from date input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isToDateInputFieldDisplayed(),
                "The advanced search dialog to date input field hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isPhaseListBoxDisplayed(),
                "The advanced search dialog phase list listbox hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isWoTypeListBoxDisplayed(),
                "The advanced search dialog wo type listbox hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isOrderedFromListBoxDisplayed(),
                "The advanced search dialog Ordered from listbox hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchButtonDisplayed(),
                "The advanced search dialog Search button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isXButtonDisplayed(),
                "The advanced search dialog X button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByCustomerUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                        .clickAdvancedSearchOption();

        advancedSearchDialog.typeCustomerName(data.getType());
        Assert.assertTrue(advancedSearchDialog.isCustomerDisplayed(data.getCustomer()));
        advancedSearchDialog.selectCustomerNameFromBoxList(data.getCustomer());

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> optionsNames = partsOrdersListPanel.getNamesListOptions();
        Assert.assertEquals(optionsNames.get(0), data.getCustomer(),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByPhaseUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setPhase(data.getPhase());

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> optionsPhases = partsOrdersListPanel.getPhasesListOptions();
        Assert.assertTrue(optionsPhases.get(0).contains(data.getPhase()),
                "The list orders first option name doesn't contain the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByWONumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setWoNum(data.getWoNum())
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> optionsWONums = partsOrdersListPanel.getWONumsListOptions();
        Assert.assertEquals(optionsWONums.get(0), data.getWoNum(),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByWOTypeUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setWoType(data.getWoType());

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> optionsWONums = partsOrdersListPanel.getWONumsListOptions();
        final String firstWONum = optionsWONums.get(0);

        final VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setRepairOrdersSearchText(firstWONum)
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(firstWONum),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(firstWONum),
                "The work order type is not displayed after search by order number");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByStockNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setStockNum(data.getStockNum())
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> stockNums = partsOrdersListPanel.getStockNumsListOptions();
        System.out.println(stockNums.get(0));
        System.out.println(data.getStockNum());
        Assert.assertTrue(stockNums.get(0).contains(data.getStockNum()),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByETAFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final String currentDate = data.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .openETAFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The Search Name input field hasn't been displayed");
        Assert.assertEquals(advancedSearchDialog.getETAFromInputField(), currentDate,
                "The ETA From input field value hasn't been displayed properly");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        final List<String> etaDataValues = partsOrdersListPanel.getETADataValues();
        final String etaDate = etaDataValues.get(0);
        System.out.println("etaDate: " + etaDate);
        System.out.println("currentDate: "+ currentDate);
//        Assert.assertTrue(etaDate.equals(currentDate) || etaDate.equals("")
//                        || partsDetailsPanel.isDateAfter(etaDate, currentDate), // todo uncomment after bug #81900 fix
//            "The ETA date is neither equal to the current date, nor after it, nor empty");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByETAToUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final String currentDate = data.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .openETAToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The Search Name input field hasn't been displayed");
        Assert.assertEquals(advancedSearchDialog.getETAToInputField(), currentDate,
                "The ETA From input field value hasn't been displayed properly");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        final List<String> etaDataValues = partsOrdersListPanel.getETADataValues();
        final String etaDate = etaDataValues.get(0);
        System.out.println("etaDate: " + etaDate);
        System.out.println("currentDate: "+ currentDate);
        Assert.assertTrue(etaDate.equals("") || partsDetailsPanel.isDateBefore(etaDate, currentDate),
            "The ETA date is neither empty, nor before the current date");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByVINNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setVin(data.getVINNum())
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> vinNums = partsOrdersListPanel.getVinNumsListOptions();
        System.out.println(vinNums.get(0));
        System.out.println(data.getVINNum());
        Assert.assertTrue(vinNums.get(0).contains(data.getVINNum()),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByOEMNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setOEMNum(data.getOEMNum())
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
        final List<String> oemNums = partsOrdersListPanel.getOemNumsListOptions();
        System.out.println(oemNums.get(0));
        System.out.println(data.getOEMNum());
        Assert.assertEquals(oemNums.get(0), data.getOEMNum(),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByNotesUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setNotes(data.getNotes())
                .clickAdvancedSearchHeader();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        final List<String> optionsWONums = partsOrdersListPanel.getWONumsListOptions();
        final String firstWONum = optionsWONums.get(0);

        final VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setRepairOrdersSearchText(firstWONum)
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(firstWONum),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(firstWONum),
                "The work order type is not displayed after search by order number");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(firstWONum);
        final String partId = detailsPage.getFirstPartIdFromPartsList();
        Assert.assertNotEquals(partId, "", "The service hasn't been displayed");

        final WebElement partActionsIcon = detailsPage.clickFirstPartActionsIcon();
        final VNextBOOrderServiceNotesDialog notesDialog = detailsPage.openNotesDialogForPart(partActionsIcon);

        Assert.assertTrue(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been opened");

        final List<String> notesListValues = notesDialog.getNotesListValues();
        System.out.println(notesListValues);
        Assert.assertTrue(notesListValues.contains(data.getNotes()), "The Note hasn't been displayed");

        notesDialog.clickNotesXbutton();
        Assert.assertFalse(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByOrderedFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setOrderedFrom(data.getOrderedFrom());

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        final List<String> optionsWONums = partsOrdersListPanel.getWONumsListOptions();
        final String firstWONum = optionsWONums.get(0);

        final VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setRepairOrdersSearchText(firstWONum)
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(firstWONum),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(firstWONum),
                "The work order type is not displayed after search by order number");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(firstWONum);
        Assert.assertEquals(detailsPage.getPartsOrderedFromTableValues().get(0), data.getOrderedFrom(),
                "The Parts 'Ordered From' value is not the same as it has been set for order");
    }
}