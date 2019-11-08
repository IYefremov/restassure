package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsAdvancedSearchValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOClientsSearchTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsSearchTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        VNextBOLeftMenuInteractions.selectClientsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchClients(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("jack");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "jack");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickSearchWithEmptyAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchValidations.verifyAllElementsDisplayed();
        VNextBOClientsAdvancedSearchSteps.clickSearchButton();
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("10");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseAdvancedSearchPopUp(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchForm advancedSearchForm =
                new VNextBOClientsAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.clickCloseButton();
        VNextBOClientsAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(advancedSearchForm);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchWindowSavesSearchParameters(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByName("testName");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Name: testName");
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
        VNextBOClientsAdvancedSearchValidations.doesNameFieldContainExpectedText("testName");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchWindowFromActiveAndArchivedTabs(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchValidations.verifyAllElementsDisplayed();
        VNextBOClientsPageSteps.openActiveTab();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchValidations.verifyAllElementsDisplayed();
        VNextBOClientsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchWindowSavesEnteredValuesWithoutSearching(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setNameField("testName");
        VNextBOClientsAdvancedSearchSteps.setAddressField("testAddress");
        VNextBOClientsAdvancedSearchSteps.setEmailField("testEmail@com");
        VNextBOClientsAdvancedSearchSteps.setPhoneField("03867676767");
        VNextBOClientsAdvancedSearchSteps.clickCloseButton();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchValidations.doesNameFieldContainExpectedText("testName");
        VNextBOClientsAdvancedSearchValidations.doesAddressFieldContainExpectedText("testAddress");
        VNextBOClientsAdvancedSearchValidations.doesEmailFieldContainExpectedText("testEmail@com");
        VNextBOClientsAdvancedSearchValidations.doesPhoneFieldContainExpectedText("03867676767");
        VNextBOClientsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByNameNegative(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByName("abracadabra");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Name: abracadabra");
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByNamePositive(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByName("TEST");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Name: TEST");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "TEST");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmail(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByEmail("test@test.com");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Email: test@test.com");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Email", "test@test.com");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhone(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByPhone("1111111");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Phone: 1111111");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Phone", "1111111");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByAddress(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByAddress("Hollywood");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Address: Hollywood");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Address", "Hollywood");
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillInAllAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOClientsAdvancedSearchSteps.setNameField("testName");
        VNextBOClientsAdvancedSearchSteps.setAddressField("testAddress");
        VNextBOClientsAdvancedSearchSteps.setEmailField("testEmail@com");
        VNextBOClientsAdvancedSearchSteps.setPhoneField("03867676767");
        VNextBOClientsAdvancedSearchSteps.setTypeDropDownField("Retail");
        VNextBOClientsAdvancedSearchValidations.doesNameFieldContainExpectedText("testName");
        VNextBOClientsAdvancedSearchValidations.doesAddressFieldContainExpectedText("testAddress");
        VNextBOClientsAdvancedSearchValidations.doesEmailFieldContainExpectedText("testEmail@com");
        VNextBOClientsAdvancedSearchValidations.doesPhoneFieldContainExpectedText("03867676767");
        VNextBOClientsAdvancedSearchValidations.doesTypeFieldContainExpectedText("Retail");
        VNextBOClientsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTypeRetail(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByType("Retail");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Client Type: Retail");
        VNextBOClientsPageSteps.getColumnValuesFromColumnWithCheckBoxes("Wholesale");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Wholesale", false);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTypeWholesale(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.searchClientByType("Wholesale");
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Client Type: Wholesale");
        VNextBOClientsPageSteps.getColumnValuesFromColumnWithCheckBoxes("Wholesale");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Wholesale", true);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}