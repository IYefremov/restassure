package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorSimpleSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorSimpleSearchTD();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSearchByVIN(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getVinNum());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum(), true),
                "The work order is not displayed after search by VIN after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getVinNum());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum(), true),
                "The work order is not displayed after search by VIN after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSearchByOrderNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber(), true),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getOrderNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber(), true),
                "The work order is not displayed after search by order number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getRoNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByRoNumber(data.getRoNumber(), true),
                "The work order is not displayed after search by RO number after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getRoNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByRoNumber(data.getRoNumber(), true),
                "The work order is not displayed after search by RO number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanSearchByFirstName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getFirstName());
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getFirstName());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByFirstName(data.getFirstName(), true),
                "The work order is not displayed after search by first name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanSearchByLastName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getLastName());
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getLastName());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByLastName(data.getLastName(), true),
                "The work order is not displayed after search by last name after clicking the 'Enter' key");
    }

    // todo sometimes fails, bug #100796
    // https://cyb.tpondemand.com/restui/board.aspx?#page=bug/100796&appConfig=eyJhY2lkIjoiRTU0NTRFNkE4OEZBODJDQTIzRjZFQzI0Q0NBQUEyNEYifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSearchByServiceOrTaskName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.search(data.getService());

        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageInteractions.expandPhasesTable();
        VNextBORODetailsPageValidations.verifyServiceOrTaskDescriptionsContainText(data.getService());
    }
}