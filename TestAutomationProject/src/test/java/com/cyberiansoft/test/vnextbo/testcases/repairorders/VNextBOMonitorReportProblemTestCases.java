package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCompleteCurrentPhaseInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROProblemsInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCompleteCurrentPhaseValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorReportProblemTestCases extends BaseTestCase {

    /**
     * remove methods with TestNG annotations,
     * if the unique locators for the services dropdowns on the RO details page are added.
     */
    @BeforeClass
    public void login() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorReportProblemTD();
    }

    @BeforeMethod
    public void setUp() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginSteps.userLogin(userName, userPassword);
    }

    @AfterMethod
    public void clear() {
        VNextBOHeaderPanelSteps.logout();
        DriverBuilder.getInstance().getDriver().manage().deleteAllCookies();
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

        final String status = data.getServiceStatuses()[0];
        VNextBORODetailsPageSteps.setServiceStatusForServiceByServiceId(serviceId, status);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, status);
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
//        VNextBORODetailsPageSteps.setServiceStatusForServiceByServiceId(serviceId, data.getServiceStatuses()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForServiceByServiceId(serviceId, data.getServiceStatuses()[1]);
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, data.getServiceStatuses()[1]);

        VNextBORODetailsPageInteractions.clickActionsIcon(serviceId);
        Assert.assertTrue(VNextBORODetailsPageValidations.isReportProblemOptionNotDisplayedForService(serviceId),
                "The Problem icon is displayed for service after setting the 'Completed' status");

        VNextBORODetailsPageInteractions.clickServiceStatusBox(serviceId);
        Assert.assertFalse(VNextBORODetailsPageValidations.isServiceStatusPresentInOptionsList(data.getServiceStatuses()[2]),
                "The Problem option is available in the services options list");
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenCompleteCurrentPhasePopupWindowWithAllProblemServices(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        final String service = data.getService();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = data.getServiceStatuses()[0];
        VNextBORODetailsPageSteps.setServiceStatusForServiceByServiceId(serviceId, status);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, status);
        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());

        VNextBORODetailsPageSteps.setCompleteCurrentPhaseForPhase(data.getPhase());
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isServiceNameGridDisplayed(),
                "The service name grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isProblemReasonGridDisplayed(),
                "The problem reason grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isProblemDescriptionGridDisplayed(),
                "The problem description grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isActionGridDisplayed(),
                "The action grid hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isCancelButtonDisplayed(),
                "The 'Cancel' button hasn't been displayed");
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isCompleteCurrentPhaseButtonDisplayed(),
                "The 'Complete Current phase' button hasn't been displayed");

        Assert.assertEquals(VNextBOCompleteCurrentPhaseInteractions.getServiceName(service), service,
                "The service " + service + " hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCompleteCurrentPhaseWithProblemServices(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageInteractions.expandServicesTable(data.getPhase());
        final String service = data.getService();
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = data.getServiceStatuses()[0];
        VNextBORODetailsPageSteps.setServiceStatusForServiceByServiceId(serviceId, status);
        VNextBOROProblemsInteractions.clickResolveButton();
        VNextBORODetailsPageValidations.verifyStatusHasBeenSetForService(serviceId, status);
        VNextBORODetailsPageSteps.setReportProblemForService(serviceId, data.getProblemReason(), data.getProblemDescription());

        VNextBORODetailsPageSteps.setCompleteCurrentPhaseForPhase(data.getPhase());
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isServiceNameGridDisplayed(),
                "The service name grid hasn't been displayed");

        Assert.assertEquals(VNextBOCompleteCurrentPhaseInteractions.getServiceName(service), service,
                "The service " + service + " hasn't been displayed");
    }
}
