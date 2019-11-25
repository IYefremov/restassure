package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPartsManagementSearchTestCases extends BaseTestCase {


    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementSearchTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseAdvancedSearchForROSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByCustomerUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByPhaseUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByWONumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByWOTypeUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByStockNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByETAFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByETAToUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByVINNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByOEMNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByNotesUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByOrderedFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByFromOptionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROByToOptionUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillAllFieldsOfAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAdvancedSearchDataIsNotClearedAfterClosingByClickingXIconAndOutsideSearchWindow(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearAllEnteredFieldsOfAdvancedSearchDialog(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveAllOptionsOfAdvancedSearchDialog(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROUsingOptionsOfAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchROUsingSavedOptionsOfAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeValuesOfTheSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickSavedAdvancedSearchModalDialogDeleteButtons(String rowID, String description, JSONObject testData) {

    }
}