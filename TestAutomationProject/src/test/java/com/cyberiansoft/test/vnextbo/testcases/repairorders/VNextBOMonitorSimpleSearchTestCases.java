package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorSimpleSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorSimpleSearchTD();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVIN(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getVinNum());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getVinNum());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrderNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getOrderNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getRoNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getRoNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByFirstName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getFirstName());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getFirstName());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getLastName());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Search' icon");
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchWithEnter(data.getLastName());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Enter' key");
    }

    // todo sometimes fails, bug #100796
    // https://cyb.tpondemand.com/restui/board.aspx?#page=bug/100796&appConfig=eyJhY2lkIjoiRTU0NTRFNkE4OEZBODJDQTIzRjZFQzI0Q0NBQUEyNEYifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByServiceOrTaskName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.search(data.getService());

        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageInteractions.expandServicesTable();
        VNextBORODetailsPageValidations.verifyServiceOrTaskDescriptionsContainText(data.getService());
    }
}