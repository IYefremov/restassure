package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.*;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOLogInfoDialogValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROAdvancedSearchDialogValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorTestCasesPart4New extends BaseTestCase {

    final String TEST_ORDER_NUMBER = "O-000-147163";
    final String TEST_LOCATION = "Best Location Automation";

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeQuantityForService(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), "0");
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), data.getServiceQuantity());
        VNextBORODetailsValidationsNew.verifyServiceQuantityIsCorrect(data.getService(), data.getServiceQuantity());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEnterNegativeNumberForServiceQuantity(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), "0");
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), data.getServiceQuantity());
        VNextBORODetailsValidationsNew.verifyServiceQuantityIsCorrect(data.getService(), "0.000");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotEnterTextForServiceQuantity(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), data.getServiceQuantity());
        VNextBORODetailsStepsNew.setServiceQuantity(data.getService(), "d");
        VNextBORODetailsValidationsNew.verifyServiceQuantityIsCorrect(data.getService(), data.getServiceQuantity());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangePriceForService(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), "0");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), data.getServicePrice());
        VNextBORODetailsValidationsNew.verifyServicePriceIsCorrect(data.getService(), "$" + data.getServicePrice());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEnterNegativeNumberForServicePrice(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), "0");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), "-" + data.getServicePrice());
        VNextBORODetailsValidationsNew.verifyServicePriceIsCorrect(data.getService(), "($" + data.getServicePrice() + ")");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotEnterTextForServicePrice(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), "0");
        VNextBORODetailsStepsNew.setServicePrice(data.getService(), data.getServicePrice());
        VNextBORODetailsValidationsNew.verifyServicePriceIsCorrect(data.getService(), "$0.00");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)

    public void verifyUserCanSeePhaseOfRo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsValidationsNew.verifyPhaseIsDisplayed(data.getPhase());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeTechnicianOfRo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician());
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician1());
        VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getService(), data.getVendor());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getService(), data.getTechnician1());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeAndNotSaveWithXButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician());
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndNotSaveXIcon(data.getVendor(), data.getTechnician1());
        VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getService(), data.getVendor());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getService(), data.getTechnician());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeAndNotSaveWithCancelButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician());
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndNotSaveCancelButton(data.getVendor(), data.getTechnician1());
        VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getService(), data.getVendor());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getService(), data.getTechnician());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {

	    VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getServices()[0], data.getServiceStatuses()[1]);
        WaitUtilsWebDriver.waitABit(3000);
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getServices()[1], data.getServiceStatuses()[1]);
        VNextBORODetailsStepsNew.openChangeTechnicianDialogForPhase(data.getPhase());
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitABit(3000);
        VNextBOROPageStepsNew.openChangeTechnicianDialogForFirstOrder();
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician1());
        VNextBOROWebPageValidationsNew.verifyTechniciansAreCorrectInTheTable(data.getTechnician1());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getServices()[0], data.getTechnician1());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getServices()[1], data.getTechnician1());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySavedSearchForm(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOROPageStepsNew.searchBySavedAdvancedSearch(data.getSearchName());
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogValidationsNew.verifyAllFieldsContainCorrectValues(data, true);
        VNextBOROAdvancedSearchDialogStepsNew.closeDialog();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotEditSavedSearch(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOROPageStepsNew.searchBySavedAdvancedSearch("111");
        VNextBOROWebPageValidationsNew.verifySavedSearchEditPencilIconIsDisplayed(true);
        VNextBOROPageStepsNew.searchBySavedAdvancedSearch(data.getSearchName());
        VNextBOROWebPageValidationsNew.verifySavedSearchEditPencilIconIsDisplayed(false);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeArbitrationDate(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROWebPageValidationsNew.verifyFirstOrderArbitrationDate(data.getArbitrationDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        Utils.goToPreviousPage();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROWebPageValidationsNew.verifyFirstOrderInvoiceNumberIsCorrect(data.getInvoiceNumber());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
        WaitUtilsWebDriver.waitABit(3000);
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
        VNextBORODetailsStepsNew.openLogInfo();
        VNextBOLogInfoDialogStepsNew.openServicesTab();
        VNextBOLogInfoDialogValidationsNew.verifyFirstRecordServiceIsCorrect(data.getService());
        VNextBOLogInfoDialogValidationsNew.verifyFirstRecordPhaseIsCorrect(data.getPhase());
        VNextBOLogInfoDialogValidationsNew.verifyFirstRecordStatusIsCorrect(data.getServiceStatuses()[1]);
        VNextBOLogInfoDialogValidationsNew.verifyFirstRecordNoteIsCorrect("Status changed from: " + data.getServiceStatuses()[0] + " to: " + data.getServiceStatuses()[1]);
        VNextBOLogInfoDialogStepsNew.closeDialog();
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }
}