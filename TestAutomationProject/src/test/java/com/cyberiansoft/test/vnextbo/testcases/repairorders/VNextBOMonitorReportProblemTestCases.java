package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorReportProblemTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorReportProblemTD();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportAndResolveProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanInsertReasonDescriptionWhileReportingProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPhaseStatusIsSetToTheInitialAfterResolvingTheProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(data.getPhase(), data.getServiceStatuses()[0]);
        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(data.getPhase(), data.getServiceStatuses()[1]);
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(data.getPhase(), data.getServiceStatuses()[0]);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCannotReportProblemForCompletedPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(data.getPhase(), data.getServiceStatuses()[0]);

        VNextBORODetailsPageValidations.verifyActionsMenuIconIsHiddenForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheProblemIconIsShownForTheOrderOnTheROPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isProblemIndicatorDisplayedForOrder(data.getOrderNumber()),
                "The Problem indicator hasn't been displayed");

        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(data.getPhase()),
                "The Problem icon is not displayed for phase after reporting the problem");
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanViewTheOrderProblemFromTheROAndDetailsPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isProblemIndicatorDisplayedForOrder(data.getOrderNumber()),
                "The Problem indicator hasn't been displayed");

        VNextBOROPageSteps.selectViewProblemsOptionInOtherDropDown(data.getOrderNumber());

        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(data.getPhase()),
                "The Problem icon is not displayed for phase after reporting the problem");
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanReportAndResolveProblemOnTheServiceLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForService(serviceId);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanViewTheServicesFromROPageAndSystemExpandsThePhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());
        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isProblemIndicatorDisplayedForOrder(data.getOrderNumber()),
                "The Problem indicator hasn't been displayed");

        VNextBOROPageSteps.selectViewProblemsOptionInOtherDropDown(data.getOrderNumber());

        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForService(serviceId),
                "The Problem icon is not displayed for service after reporting the problem");

        VNextBORODetailsPageSteps.setResolveProblemForService(serviceId);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCannotReportProblemForTheCompletedService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");
        VNextBORODetailsPageSteps.setServiceStatusForServiceByServiceId(serviceId, data.getServiceStatuses()[1]);

        VNextBORODetailsPageInteractions.clickActionsIcon(serviceId);
        Assert.assertTrue(VNextBORODetailsPageValidations.isReportProblemOptionNotDisplayedForService(serviceId),
                "The Problem icon is displayed for service after setting the 'Completed' status");

        VNextBORODetailsPageInteractions.clickServiceStatusBox(serviceId);
        Assert.assertFalse(VNextBORODetailsPageValidations.isServiceStatusPresentInOptionsList(data.getServiceStatuses()[2]));
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getPhase(), data.getServiceStatuses()[0]);
        VNextBORODetailsPageSteps.setResolveProblemForService(serviceId);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanChangeServicesAttributesAfterReportingTheProblemOnThePhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }
}
