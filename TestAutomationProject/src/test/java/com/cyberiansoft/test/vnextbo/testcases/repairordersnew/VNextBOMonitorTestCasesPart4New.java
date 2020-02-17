package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOChangeTechnicianDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorTestCasesPart4New extends BaseTestCase {

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
        VNextBOROPageStepsNew.searchOrdersByOrderNumber("O-000-147163");
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
/*
//todo blocker, needs update
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeStartedPrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeStartedVendorPrice(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySavedSearchForm(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotEditSavedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

//    TODO the TC blocker - the locations are not loaded for the given
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyCompletedWork(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    //todo continue after the blocker is resolved
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyWorkQueue(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResolveProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeArbitrationDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUsePaginationFilter(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}
 */
}