package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBONotesPageVerifications;
import org.apache.commons.lang3.RandomUtils;
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
    private VNextBOLeftMenuInteractions leftMenuInteractions;
    private VNextBOBreadCrumbInteractions breadCrumbInteractions;
    private VNextBOPartsManagementSearchPanel partsManagementSearch;
    private VNextBOPartsOrdersListPanel partsOrdersListPanel;
    private VNextBOPartsDetailsPanel partsDetailsPanel;
    private VNextBOROWebPage repairOrdersPage;

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

        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsOrdersListPanel = PageFactory.initElements(webdriver, VNextBOPartsOrdersListPanel.class);
        partsDetailsPanel = PageFactory.initElements(webdriver, VNextBOPartsDetailsPanel.class);
        repairOrdersPage = PageFactory.initElements(webdriver, VNextBOROWebPage.class);
        leftMenuInteractions = new VNextBOLeftMenuInteractions();
        breadCrumbInteractions = new VNextBOBreadCrumbInteractions();
    }

    @AfterMethod
    public void BackOfficeLogout() {
        new VNextBOHeaderPanelSteps().logout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROUsingSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getType())
                .clickSearchLoupeIcon()
                .setPartsSearchText(data.getType());
        final List<String> stockNumOptions = partsOrdersListPanel.getStockNumOptions();
        final boolean containsStockNum = stockNumOptions
                .stream()
                .peek(System.out::println)
                .anyMatch(stock -> stock
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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getType())
                .clickSearchLoupeIcon()
                .setPartsSearchText(data.getType());
        final List<String> stockNumOptions = partsOrdersListPanel.getStockNumOptions();
        final boolean containsStockNum = stockNumOptions
                .stream()
                .peek(System.out::println)
                .anyMatch(stock -> stock
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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType());
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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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
        final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
        Assert.assertEquals(optionsWONums.get(0), data.getWoNum(),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByWOTypeUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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
        final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
        final String firstWONum = optionsWONums.get(0);

        leftMenuInteractions.selectRepairOrdersMenu();
        new VNextBOROSimpleSearchSteps().searchByText(firstWONum);

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(firstWONum),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(firstWONum),
                "The work order type is not displayed after search by order number");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByStockNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final String currentDate = CustomDateProvider.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final String currentDate = CustomDateProvider.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
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

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setVin(data.getVinNum())
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
        System.out.println(data.getVinNum());
        Assert.assertTrue(vinNums.get(0).contains(data.getVinNum()),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByOEMNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setOEMNum(data.getOemNum())
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
        System.out.println(data.getOemNum());
        Assert.assertEquals(oemNums.get(0), data.getOemNum(),
                "The list orders first option name doesn't correspond to the search parameter");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByNotesUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);
        final VNextBONotesPageVerifications notesPageVerifications = new VNextBONotesPageVerifications();

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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

        final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
        final String firstWONum = optionsWONums.get(0);

        leftMenuInteractions.selectRepairOrdersMenu();
        new VNextBOROSimpleSearchSteps().searchByText(firstWONum);
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(firstWONum),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(firstWONum),
                "The work order type is not displayed after search by order number");

        final VNextBORODetailsPage detailsPage = repairOrdersPage.clickWoLink(firstWONum);
        final String partId = detailsPage.getFirstPartIdFromPartsList();
        Assert.assertNotEquals(partId, "", "The service hasn't been displayed");

        final WebElement partActionsIcon = detailsPage.clickPartActionsIconForPart(data.getPart());
        final VNextBOOrderServiceNotesDialog notesDialog = new VNextBOOrderServiceNotesDialog();
                detailsPage.openNotesDialogForPart(partActionsIcon);

        Assert.assertTrue(notesPageVerifications.isEditOrderServiceNotesBlockDisplayed(), "The notes dialog hasn't been opened");

        final List<String> notesListValues = new VNextBORONotesPageInteractions().getRepairNotesListValues();
        //todo fails here. Needs clarifications from V.Dubinenko or changing the steps of the test
        Assert.assertTrue(notesListValues.contains(data.getNotes()), "The Note hasn't been displayed");

        new VNextBORONotesPageInteractions().clickRepairNotesXButton();
        Assert.assertFalse(notesPageVerifications.isEditOrderServiceNotesBlockDisplayed(), "The notes dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByOrderedFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

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

        final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
        final String firstWONum = optionsWONums.get(0);

        leftMenuInteractions.selectRepairOrdersMenu();
        new VNextBOROSimpleSearchSteps().searchByText(firstWONum);

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(firstWONum),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(firstWONum),
                "The work order type is not displayed after search by order number");

        final VNextBORODetailsPage detailsPage = repairOrdersPage.clickWoLink(firstWONum);
        Assert.assertEquals(detailsPage.getPartsOrderedFromTableValues().get(0), data.getOrderedFrom(),
                "The Parts 'Ordered From' value is not the same as it has been set for order");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByFromOptionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .openFromCalendarWidget()
                .navigateLeftMinusMonthInFromDateCalendarWidget()
                .navigateLeftMinusMonthInFromDateCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget();

        final String fromDateValue = advancedSearchDialog.getFromInputField();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        final List<String> optionsWOData = partsOrdersListPanel.getPartsOrdersListDataOptionsDescriptions();
        final String randomWONumData = optionsWOData.get(RandomUtils.nextInt(0, optionsWOData.size()));

        final String dateOfWOCreation = partsOrdersListPanel.getDateFromString(randomWONumData);
        System.out.println("From date: " + fromDateValue);
        System.out.println("Displayed WO creation date: " + dateOfWOCreation);

        Assert.assertTrue(advancedSearchDialog.isDateEqual(dateOfWOCreation, fromDateValue)
                        || advancedSearchDialog.isDateAfter(dateOfWOCreation, fromDateValue),
                "The date of WO creation is neither equal nor after the from date value");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByToOptionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .openToCalendarWidget()
                .navigateLeftMinusMonthInToDateCalendarWidget()
                .navigateLeftMinusMonthInToDateCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget();

        final String toDateValue = advancedSearchDialog.getToInputField();

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The Search Name input field hasn't been displayed");

        advancedSearchDialog.clickSearchButton();
        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        final List<String> optionsWOData = partsOrdersListPanel.getPartsOrdersListDataOptionsDescriptions();
        final String randomWONumData = optionsWOData.get(RandomUtils.nextInt(0, optionsWOData.size()));

        final String dateOfWOCreation = partsOrdersListPanel.getDateFromString(randomWONumData);
        System.out.println("To date: " + toDateValue);
        System.out.println("Displayed WO creation date: " + dateOfWOCreation);

        Assert.assertTrue(advancedSearchDialog.isDateEqual(dateOfWOCreation, toDateValue)
                        || advancedSearchDialog.isDateBefore(dateOfWOCreation, toDateValue),
                "The date of WO creation is neither equal nor after the from date value");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillAllFieldsOfAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType());
        Assert.assertTrue(advancedSearchDialog.isCustomerDisplayed(data.getCustomer()));
        advancedSearchDialog.selectCustomerNameFromBoxList(data.getCustomer());

        Assert.assertTrue(advancedSearchDialog.isClearButtonDisplayed(),
                "The advanced search dialog Clear button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSaveButtonDisplayed(),
                "The advanced search dialog Save button hasn't been displayed");
        Assert.assertTrue(advancedSearchDialog.isSearchNameInputFieldDisplayed(),
                "The advanced search dialog Search Name input field hasn't been displayed");

        advancedSearchDialog
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchDataIsNotClearedAfterClosingByClickingXIconAndOutsideSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final String currentDate = CustomDateProvider.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickXButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        partsManagementSearch
                .clickSearchCaret()
                .clickAdvancedSearchOption();

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getCustomer(), data.getPhase(), data.getWoType(),
                data.getWoNum(), data.getStockNum(), currentDate, data.getVinNum(), data.getOemNum(),
                data.getNotes(), data.getOrderedFrom(), data.getSearchName());

        partsManagementSearch.clickSearchSection();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog hasn't been closed");

        partsManagementSearch
                .clickSearchCaret()
                .clickAdvancedSearchOption();

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getCustomer(), data.getPhase(), data.getWoType(),
                data.getWoNum(), data.getStockNum(), currentDate, data.getVinNum(), data.getOemNum(),
                data.getNotes(), data.getOrderedFrom(), data.getSearchName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearAllEnteredFieldsOfAdvancedSearchDialog(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchDisplayedInDropDown(),
                "Advanced Search option is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.isAdvancedSearchSettingsIconDisplayed(),
                "Advanced Search Settings icon is not displayed in dropdown");
        Assert.assertTrue(partsManagementSearch.areSavedSearchNamesDisplayed(),
                "Not all saved search names are displayed in dropdown");

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickClearButton();

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getDefaultValue(), data.getPhase(), data.getWoType());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveAllOptionsOfAdvancedSearchDialog(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        partsManagementSearch.verifySearchOptionIsNotDisplayedInDropDown(data.getSearchName());

        final String currentDate = CustomDateProvider.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickSaveButton();

        Assert.assertEquals(partsOrdersListPanel.getEmptyPartsListMessage(), data.getEmptyPartsListMessage(),
                "The empty parts list message hasn't been displayed");
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));

        partsManagementSearch.clickEditForSearchOptionInDropDown(data.getSearchName());

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getCustomer(), data.getPhase(), data.getWoType(),
                data.getWoNum(), data.getStockNum(), currentDate, data.getVinNum(), data.getOemNum(),
                data.getNotes(), data.getOrderedFrom(), data.getSearchName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROUsingOptionsOfAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        partsManagementSearch.verifySearchOptionIsNotDisplayedInDropDown(data.getSearchName());

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickSearchButton();

        if (partsOrdersListPanel.getEmptyPartsListMessage().isEmpty()) {
            final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
            Assert.assertEquals(optionsWONums.get(0), data.getWoNum(),
                    "The list orders first option name doesn't correspond to the search parameter");
        } else {
            Assert.assertEquals(partsOrdersListPanel.getEmptyPartsListMessage(), data.getEmptyPartsListMessage(),
                    "The empty parts list message hasn't been displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROUsingSavedOptionsOfAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        partsManagementSearch.verifySearchOptionIsNotDisplayedInDropDown(data.getSearchName());

        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickSaveButton();

        if (partsOrdersListPanel.getEmptyPartsListMessage().isEmpty()) {
            final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
            Assert.assertEquals(optionsWONums.get(0), data.getWoNum(),
                    "The list orders first option name doesn't correspond to the search parameter");
        } else {
            Assert.assertEquals(partsOrdersListPanel.getEmptyPartsListMessage(), data.getEmptyPartsListMessage(),
                    "The empty parts list message hasn't been displayed");
        }
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));

        partsManagementSearch.clickEditForSearchOptionInDropDown(data.getSearchName());

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getCustomer(), data.getPhase(), data.getWoType(),
                data.getWoNum(), data.getStockNum(), data.getDefaultValue(), data.getVinNum(), data.getOemNum(),
                data.getNotes(), data.getOrderedFrom(), data.getSearchName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeValuesOfTheSavedAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        partsManagementSearch.verifySearchOptionIsNotDisplayedInDropDown(data.getSearchName());
        partsManagementSearch.verifySearchOptionIsNotDisplayedInDropDown(data.getSearchNameChanged());

        final String currentDate = CustomDateProvider.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickSaveButton();

        if (partsOrdersListPanel.getEmptyPartsListMessage().isEmpty()) {
            final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
            Assert.assertEquals(optionsWONums.get(0), data.getWoNum(),
                    "The list orders first option name doesn't correspond to the search parameter");
        } else {
            Assert.assertEquals(partsOrdersListPanel.getEmptyPartsListMessage(), data.getEmptyPartsListMessage(),
                    "The empty parts list message hasn't been displayed");
        }
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));

        partsManagementSearch
                .clickEditForSearchOptionInDropDown(data.getSearchName())
                .typeCustomerName(data.getTypeChanged())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhaseChanged())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoTypeChanged())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchNameChanged())
                .clickSaveButton();

        if (partsOrdersListPanel.getEmptyPartsListMessage().isEmpty()) {
            final List<String> optionsWONums = partsOrdersListPanel.getWoNumsListOptions();
            Assert.assertEquals(optionsWONums.get(0), data.getWoNum(),
                    "The list orders first option name doesn't correspond to the search parameter");
        } else {
            Assert.assertEquals(partsOrdersListPanel.getEmptyPartsListMessage(), data.getEmptyPartsListMessage(),
                    "The empty parts list message hasn't been displayed");
        }
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchNameChanged()));

        partsManagementSearch.clickEditForSearchOptionInDropDown(data.getSearchNameChanged());

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getCustomer(), data.getPhaseChanged(), data.getWoTypeChanged(),
                data.getWoNum(), data.getStockNum(), currentDate, data.getVinNum(), data.getOemNum(),
                data.getNotes(), data.getOrderedFrom(), data.getSearchNameChanged());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickSavedAdvancedSearchModalDialogDeleteButtons(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

        leftMenuInteractions.selectPartsManagementMenu();
        breadCrumbInteractions.setLocation(data.getLocation());

        partsManagementSearch.clickSearchCaret();
        partsManagementSearch.verifySearchOptionIsNotDisplayedInDropDown(data.getSearchName());

        final String currentDate = CustomDateProvider.getCurrentDate(false);
        final VNextBOPartsManagementAdvancedSearchDialog advancedSearchDialog = partsManagementSearch
                .clickAdvancedSearchOption()
                .typeCustomerName(data.getType())
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setPhase(data.getPhase())
                .clickAdvancedSearchHeader()
                .setWoType(data.getWoType())
                .clickAdvancedSearchHeader()
                .setWoNum(data.getWoNum())
                .setStockNum(data.getStockNum())
                .openETAFromCalendarWidget()
                .clickFocusedDateInETAFromDateCalendarWidget()
                .openETAToCalendarWidget()
                .clickFocusedDateInETAToDateCalendarWidget()
                .setVin(data.getVinNum())
                .setOEMNum(data.getOemNum())
                .setNotes(data.getNotes())
                .openFromCalendarWidget()
                .clickFocusedDateInFromDateCalendarWidget()
                .openToCalendarWidget()
                .clickFocusedDateInToDateCalendarWidget()
                .setOrderedFrom(data.getOrderedFrom())
                .setSearchName(data.getSearchName());
        advancedSearchDialog.clickSaveButton();

        Assert.assertEquals(partsOrdersListPanel.getEmptyPartsListMessage(), data.getEmptyPartsListMessage(),
                "The empty parts list message hasn't been displayed");
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));

        partsManagementSearch.clickEditForSearchOptionInDropDown(data.getSearchName());

        advancedSearchDialog.verifyAdvancedSearchFieldsValues(data.getCustomer(), data.getPhase(), data.getWoType(),
                data.getWoNum(), data.getStockNum(), currentDate, data.getVinNum(), data.getOemNum(),
                data.getNotes(), data.getOrderedFrom(), data.getSearchName());

        advancedSearchDialog.clickDeleteSavedSearchButton();
        partsManagementSearch.clickXIconForDeletingSearchName();
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));

        partsManagementSearch.clickEditForSearchOptionInDropDown(data.getSearchName());
        advancedSearchDialog.clickDeleteSavedSearchButton();
        partsManagementSearch.clickCancelDeletingSearchNameButton();
        partsManagementSearch.clickSearchCaret();
        Assert.assertTrue(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));

        partsManagementSearch.clickEditForSearchOptionInDropDown(data.getSearchName());
        advancedSearchDialog.clickDeleteSavedSearchButton();
        partsManagementSearch.clickConfirmDeletingSearchNameButton();
        partsManagementSearch.clickSearchCaret();
        Assert.assertFalse(partsManagementSearch.isSearchOptionDisplayed(data.getSearchName()));
    }
}