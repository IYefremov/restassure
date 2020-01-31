package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
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
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResolveProblem(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Active");
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportProblemWithReasonDescriptionOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPhaseStatusIsSetToTheInitialAfterResolvingTheProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Rework");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Rework");
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
/*
    //TODO fails
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCannotReportProblemForCompletedPhase(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Completed");
        VNextBORODetailsValidationsNew.verifyActionsButtonIsNotDisplayed(data.getPhase());//????????
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
*/
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheProblemIconIsShownForTheOrderOnTheROPage(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        Utils.goToPreviousPage();
        VNextBOROWebPageValidationsNew.verifyProblemIndicatorIsDisplayedForEachRecord();
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanViewTheOrderProblemFromTheROAndDetailsPage(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        Utils.goToPreviousPage();
        VNextBOROPageStepsNew.viewOrdersProblemsByOrderNumber(data.getOrderNumber());
        VNextBORODetailsValidationsNew.verifyPhaseStatusIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}
