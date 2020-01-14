package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCompleteCurrentPhaseDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROProblemsInteractions;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCompleteCurrentPhaseDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextBOMonitorReportProblemTestCases extends BaseTestCase {

    @BeforeClass
    public void setUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorReportProblemTD();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanReportAndResolveProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), OrderMonitorServiceStatuses.ACTIVE.getValue());
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanInsertReasonDescriptionWhileReportingProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyPhaseStatusIsSetToTheInitialAfterResolvingTheProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), OrderMonitorServiceStatuses.REWORK.getValue());
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(
                data.getPhase(), OrderMonitorServiceStatuses.REWORK.getValue());
        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(data.getPhase(),
                OrderMonitorServiceStatuses.PROBLEM.getValue());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(
                data.getPhase(), OrderMonitorServiceStatuses.REWORK.getValue());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyTheUserCannotReportProblemForCompletedPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.setPhaseStatus(
                data.getPhase(), OrderMonitorServiceStatuses.COMPLETED.getValue());
        VNextBORODetailsPageValidations.verifyPhaseStatusIsDisplayed(
                data.getPhase(), OrderMonitorServiceStatuses.COMPLETED.getValue());

        VNextBORODetailsPageValidations.verifyActionsMenuIconIsHiddenForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyTheProblemIconIsShownForTheOrderOnTheROPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), data.getServiceStatuses()[0]);
        WaitUtilsWebDriver. waitForLoading();
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), OrderMonitorServiceStatuses.ACTIVE.getValue());
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyTheUserCanViewTheOrderProblemFromTheROAndDetailsPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), OrderMonitorServiceStatuses.ACTIVE.getValue());
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyTheUserCanReportAndResolveProblemOnTheServiceLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = OrderMonitorServiceStatuses.ACTIVE.getValue();
        VNextBORODetailsPageSteps.setServiceStatusByServiceId(serviceId, status);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, status);
        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForService(serviceId);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyTheUserCanViewTheServicesFromROPageAndSystemExpandsThePhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
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
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyTheUserCannotReportProblemForTheCompletedService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");
//        VNextBORODetailsPageSteps.setServiceStatusByServiceId(serviceId, data.getServiceStatuses()[0]);
        VNextBORODetailsPageSteps.setServiceStatusByServiceId(
                serviceId, OrderMonitorServiceStatuses.COMPLETED.getValue());
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(
                serviceId, OrderMonitorServiceStatuses.COMPLETED.getValue());

        VNextBORODetailsPageInteractions.clickActionsIcon(serviceId);
        Assert.assertTrue(VNextBORODetailsPageValidations.isReportProblemOptionNotDisplayedForService(serviceId),
                "The Problem icon is displayed for service after setting the 'Completed' status");

        VNextBORODetailsPageInteractions.clickServiceStatusBox(serviceId);
        Assert.assertFalse(VNextBORODetailsPageValidations.isServiceStatusPresentInOptionsList(
                OrderMonitorServiceStatuses.PROBLEM.getValue()),
                "The Problem option is available in the services options list");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyTheUserCanChangeServicesAttributesAfterReportingTheProblemOnThePhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        VNextBORODetailsPageInteractions.setPhaseStatus(data.getPhase(), OrderMonitorServiceStatuses.ACTIVE.getValue());
        Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(data.getPhase()),
                "The phase actions trigger hasn't been displayed");

        VNextBORODetailsPageSteps.setReportProblemForPhase(data.getPhase(), data.getProblemReason(), data.getProblemDescription());
        VNextBORODetailsPageSteps.setResolveProblemForPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanOpenCompleteCurrentPhasePopupWindowWithAllProblemServices(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        final String service = data.getService();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = OrderMonitorServiceStatuses.ACTIVE.getValue();
        VNextBORODetailsPageSteps.setServiceStatusByServiceId(serviceId, status);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, status);
        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());

        VNextBORODetailsPageSteps.setCompleteCurrentPhaseForPhaseWithProblem(data.getPhase());
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isServiceNameGridDisplayed(),
                "The service name grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isProblemReasonGridDisplayed(),
                "The problem reason grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isProblemDescriptionGridDisplayed(),
                "The problem description grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isActionGridDisplayed(),
                "The action grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isCancelButtonDisplayed(),
                "The 'Cancel' button hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isCompleteCurrentPhaseButtonDisplayed(),
                "The 'Complete Current phase' button hasn't been displayed");

        Assert.assertEquals(VNextBOCompleteCurrentPhaseDialogInteractions.getServiceName(service), service,
                "The service " + service + " hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanCompleteCurrentPhaseWithProblemServices(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        final String service = data.getService();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = OrderMonitorServiceStatuses.ACTIVE.getValue();
        VNextBORODetailsPageSteps.setServiceStatusByServiceId(serviceId, status);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, status);
        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());

        VNextBORODetailsPageSteps.setCompleteCurrentPhaseForPhaseWithProblem(data.getPhase());
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isServiceNameGridDisplayed(),
                "The service name grid hasn't been displayed");

        Assert.assertEquals(VNextBOCompleteCurrentPhaseDialogInteractions.getServiceName(service), service,
                "The service " + service + " hasn't been displayed");

        VNextBOCompleteCurrentPhaseDialogInteractions.clickResolveButtonForService(service);
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isResolvedButtonDisplayedForService(service),
                "The 'Resolved' button hasn't been displayed for service after clicking 'Resolve'");
        VNextBOCompleteCurrentPhaseDialogInteractions.clickResolveButtonForService(service);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanCloseCompleteCurrentPhasePopupWindowWithoutProblemResolving(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        final String service = data.getService();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String activeStatus = OrderMonitorServiceStatuses.ACTIVE.getValue();
        VNextBORODetailsPageSteps.setServiceStatusByServiceId(serviceId, activeStatus);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, activeStatus);
        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());

        VNextBORODetailsPageSteps.setCompleteCurrentPhaseForPhaseWithProblem(data.getPhase());
        VNextBOCompleteCurrentPhaseDialogInteractions.cancelCompletingCurrentPhase();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(
                serviceId, OrderMonitorServiceStatuses.PROBLEM.getValue());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanResolveProblemUsingCompleteCurrentPhaseWithoutCompletingCurrentPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandPhasesTable(data.getPhase());
        final String service = data.getService();
        final List<String> allServicesId = VNextBORODetailsPageInteractions.getAllServicesId(service);
        Assert.assertTrue(!allServicesId.isEmpty(), "The service hasn't been displayed");

        final String activeStatus = OrderMonitorServiceStatuses.ACTIVE.getValue();
        VNextBORODetailsPageSteps.setServiceStatusForMultipleServicesByServiceId(allServicesId, activeStatus);
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForAllServices(allServicesId, activeStatus);
        VNextBORODetailsPageSteps.setReportProblemForMultipleServices(
                allServicesId, data.getProblemReason(), data.getProblemDescription());

        VNextBORODetailsPageSteps.setCompleteCurrentPhaseForPhaseWithProblem(data.getPhase());
        VNextBOCompleteCurrentPhaseDialogInteractions.resolveServices(service, service);

        VNextBOCompleteCurrentPhaseDialogInteractions.cancelCompletingCurrentPhase();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForAllServices(
                allServicesId, OrderMonitorServiceStatuses.ACTIVE.getValue());
    }
}
