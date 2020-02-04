package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROCompleteCurrentPhaseDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOToasterNotificationValidations;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROCompleteCurrentPhaseDialogValidationsNew;
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
        VNextBORODetailsValidationsNew.verifyPhaseStatusInDropdownFieldIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResolveProblem(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusInDropdownFieldIsCorrect(data.getPhase(), "Active");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReportProblemWithReasonDescriptionOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithDescription(data.getPhase(), data.getProblemReason(), data.getProblemDesc());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusInDropdownFieldIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPhaseStatusIsSetToTheInitialAfterResolvingTheProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Rework");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyProblemIndicatorIsDisplayedForPhase(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusInDropdownFieldIsCorrect(data.getPhase(), "Problem");
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
        VNextBORODetailsValidationsNew.verifyPhaseStatusInDropdownFieldIsCorrect(data.getPhase(), "Rework");
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
        VNextBORODetailsValidationsNew.verifyPhaseStatusInDropdownFieldIsCorrect(data.getPhase(), "Problem");
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanChangeServicesAttributesAfterReportingTheProblemOnThePhaseLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.setPhaseStatusIfNeeded(data.getPhase(), "Active");
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), "0");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), "0");
        VNextBORODetailsStepsNew.setServiceVendorPrice(data.getService(), "0");
        VNextBORODetailsStepsNew.reportProblemOnPhaseLevelWithoutDescription(data.getPhase(), data.getProblemReason());
        String initialTotalPrice = VNextBORODetailsStepsNew.getPhaseTotalPrice(data.getPhase());
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), data.getServiceQuantity());
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), data.getServicePrice());
        VNextBORODetailsStepsNew.setServiceVendorPrice(data.getService(), data.getServiceVendorPrice());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBORODetailsValidationsNew.verifyPhaseTotalPriceHasBeenChanged(data.getPhase(), initialTotalPrice);
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.resolveProblemOnPhaseLevel(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheUserCanChangeServicesAttributesAfterReportingTheProblemOnTheServiceLevel(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), "0");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), "0");
        VNextBORODetailsStepsNew.setServiceVendorPrice(data.getService(), "0");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        String initialTotalPrice = VNextBORODetailsStepsNew.getPhaseTotalPrice(data.getPhase());
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), data.getServiceQuantity());
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), data.getServicePrice());
        VNextBORODetailsStepsNew.setServiceVendorPrice(data.getService(), data.getServiceVendorPrice());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBORODetailsValidationsNew.verifyPhaseTotalPriceHasBeenChanged(data.getPhase(), initialTotalPrice);
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenCompleteCurrentPhasePopupWindowWithAllProblemServices(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        VNextBORODetailsStepsNew.openCompleteCurrentPhaseDialog(data.getPhase());
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyCancelButtonIsDisplayed();
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyColumnsTitlesAreCorrect();
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyCompleteCurrentPhaseButtonIsDisplayed();
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyServiceIsPresentedInTheTable(data.getService());
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyResolveButtonIsDisplayedForService(data.getService());
        VNextBOROCompleteCurrentPhaseDialogStepsNew.closeDialogWithCancelButton();
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCompleteCurrentPhaseButtonIsDisabledIfServiceHasProblemStatus(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        VNextBORODetailsStepsNew.openCompleteCurrentPhaseDialog(data.getPhase());
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyCompleteCurrentPhaseButtonIsClickable(false);
        VNextBOROCompleteCurrentPhaseDialogStepsNew.closeDialogWithCancelButton();
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseCompleteCurrentPhasePopupWindowWithoutProblemResolving(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        VNextBORODetailsStepsNew.openCompleteCurrentPhaseDialog(data.getPhase());
        VNextBOROCompleteCurrentPhaseDialogStepsNew.closeDialogWithCancelButton();
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyDialogIsDisplayed(false);
        VNextBORODetailsStepsNew.resolveProblemForService(data.getService());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResolveProblemUsingCompleteCurrentPhaseWithoutCompletingCurrentPhase(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        VNextBORODetailsStepsNew.openCompleteCurrentPhaseDialog(data.getPhase());
        VNextBOROCompleteCurrentPhaseDialogStepsNew.resolveProblemForService(data.getService());
        VNextBOToasterNotificationValidations.verifyMessageTextIsCorrect("Success. Problem resolved.");
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyResolvedIconIsDisplayedForService(data.getService());
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyCompleteCurrentPhaseButtonIsClickable(true);
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyResolveButtonIsClickableForService(data.getService(), false);
        VNextBOROCompleteCurrentPhaseDialogStepsNew.closeDialogWithCancelButton();
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyDialogIsDisplayed(false);
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(data.getService(), "Active");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCompleteCurrentPhaseWithProblemServices(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.reportProblemForServiceWithoutDescription(data.getService(), data.getProblemReason());
        VNextBORODetailsStepsNew.openCompleteCurrentPhaseDialog(data.getPhase());
        VNextBOROCompleteCurrentPhaseDialogStepsNew.resolveProblemForService(data.getService());
        VNextBOROCompleteCurrentPhaseDialogStepsNew.completeCurrentPhase();
        VNextBOROCompleteCurrentPhaseDialogValidationsNew.verifyDialogIsDisplayed(false);
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(data.getService(), "Completed");
        VNextBORODetailsValidationsNew.verifyPhaseTextStatusIsCorrect(data.getPhase(), "Completed");
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }
}