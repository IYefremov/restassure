package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorSimpleSearchTestCasesNew extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorSimpleSearchTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVIN(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getVinNum());
        VNextBOROWebPageValidationsNew.verifyVinNumbersAreCorrectInTheTable(data.getVinNum());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getVinNum());
        VNextBOROWebPageValidationsNew.verifyVinNumbersAreCorrectInTheTable(data.getVinNum());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrderNum(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getOrderNumber());
        VNextBOROWebPageValidationsNew.verifyWoNumbersAreCorrectInTheTable(data.getOrderNumber());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getOrderNumber());
        VNextBOROWebPageValidationsNew.verifyWoNumbersAreCorrectInTheTable(data.getOrderNumber());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getRoNumber());
        VNextBOROWebPageValidationsNew.verifyRoNumbersAreCorrectInTheTable(data.getRoNumber());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getRoNumber());
        VNextBOROWebPageValidationsNew.verifyRoNumbersAreCorrectInTheTable(data.getRoNumber());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByFirstName(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getFirstName());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getFirstName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getFirstName());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getFirstName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastName(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getLastName());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getLastName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getLastName());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getLastName());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmail(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getEmail());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getCustomer());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getCustomer());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getCustomer());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCompanyName(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getCompany());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getCompany());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithEnterButton(data.getCompany());
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable(data.getCompany());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByServiceOrTaskName(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getService());
        VNextBOROWebPageValidationsNew.verifySearchResultByService(data.getService());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}
