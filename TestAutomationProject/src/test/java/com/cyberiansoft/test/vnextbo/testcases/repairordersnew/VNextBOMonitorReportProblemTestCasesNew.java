package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorReportProblemTestCasesNew extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorReportProblemTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
        VNextBOROPageStepsNew.searchOrdersByOrderNumber("O-000-147163");
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResolveProblem(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Active");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportProblemWithReasonDescriptionOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithDescription(data.getPhase(), data.getProblemReason(), data.getProblemDesc());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPhaseStatusIsSetToTheInitialAfterResolvingTheProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Rework");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Rework");
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCannotReportProblemForCompletedPhase(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Completed");
        VNextBORODetailsValidationsNew.verifyActionsButtonIsNotDisplayedForPhase(data.getPhase());
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheProblemIconIsShownForTheOrderOnTheROPage(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROWebPageValidationsNew.verifyProblemIndicatorIsDisplayedForOrder(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanViewTheOrderProblemFromTheROAndDetailsPage(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        Utils.goToPreviousPage();
        VNextBOROPageStepsNew.viewOrdersProblemsByOrderNumber(data.getOrderNumber());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanReportProblemOnServiceLevelSelectReasonFromList(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForService(data.getService());
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(data.getService(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanResolveTheProblemServiceLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        WaitUtilsWebDriver.waitABit(3000);
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(data.getService(), "Active");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanReportProblemOnTheServiceLevelWithProblemDescription(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithDescription(data.getService(), data.getProblemReason(), data.getProblemDesc());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForService(data.getService());
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(data.getService(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanViewTheServiceProblemFromROPageAndSystemExpandsThePhase(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        Utils.goToPreviousPage();
        VNextBOROPageStepsNew.viewOrdersProblemsByOrderNumber(data.getOrderNumber());
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(data.getService(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCannotReportProblemForTheCompletedService(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Completed");
        VNextBORODetailsValidationsNew.verifyReportProblemActionButtonIsNotDisplayedForCompletedService(data.getService());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }
}
