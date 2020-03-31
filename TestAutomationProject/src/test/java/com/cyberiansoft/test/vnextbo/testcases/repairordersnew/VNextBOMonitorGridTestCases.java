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
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOChangeTechnicianDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorGridTestCases extends BaseTestCase {

    final String TEST_ORDER_NUMBER = "O-000-152551";
    final String TEST_LOCATION = "Rozstalnoy_location";

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorGridTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseRO(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.closeOrder(TEST_ORDER_NUMBER, data.getProblemReason());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect("Closed");
        VNextBORODetailsValidationsNew.verifyOrderCloseReasonIsCorrect(data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyPhaseTextStatusIsCorrect(data.getPhase(), "Skipped");
        VNextBORODetailsStepsNew.reopenOrderIfNeeded();
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeFlagOfRo(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Test");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(128, 0, 128)", "purple");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Blue");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(72, 124, 184)", "blue");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Green");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(0, 166, 81)", "green");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Yellow");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(255, 242, 0)", "yellow");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Low priority");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(255, 140, 90)", "orange");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Important");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(237, 28, 36)", "red");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "White");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "0px none rgb(70, 70, 70)", "white");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartCompleteServicesAndPhasesFromTheMainRosPage(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber("O-368-00007");
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROWebPageValidationsNew.verifyStartPhaseServicesActionButtonIsDisplayed(true);
        VNextBOROWebPageValidationsNew.verifyFirstServiceIconInTheCurrentPhaseDropdown(0,"icon-start-ro");
        VNextBOROPageStepsNew.startFirstServiceForTheCurrentPhase();
        VNextBOROWebPageValidationsNew.verifyFirstServiceIconInTheCurrentPhaseDropdown(0,"icon-checkmark_thin");
        VNextBOROPageStepsNew.startPhaseServicesForFirstOrder();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROWebPageValidationsNew.verifyStartPhaseServicesActionButtonIsDisplayed(false);
        VNextBOROWebPageValidationsNew.verifyFirstServiceIconInTheCurrentPhaseDropdown(1, "icon-checkmark_thin");
        VNextBOROPageStepsNew.completeCurrentPhaseForFirstOrder();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("Completed");
        VNextBOROWebPageValidationsNew.verifyCurrentPhaseDoesNotContainServices();
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded("rozstalnoy_enable_money", "Active");
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded("rozstalnoy_disable_money", "Active");
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded("api task", "Active");
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded("Note1568105975480", "Active");
        VNextBORODetailsStepsNew.resetStartDateIfNeededByServiceName(data.getPhase(), "rozstalnoy_enable_money");
        VNextBORODetailsStepsNew.resetStartDateIfNeededByServiceName(data.getPhase(), "rozstalnoy_disable_money");
        VNextBORODetailsStepsNew.resetStartDateIfNeededByServiceName(data.getPhase(), "api task");
        VNextBORODetailsStepsNew.resetStartDateIfNeededByServiceName(data.getPhase(), "Note1568105975480");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanPrioritizeRepairOrder(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.changeOrderPriority(TEST_ORDER_NUMBER, "red");
        VNextBOROWebPageValidationsNew.verifyPriorityIsCorrectForFirstOrder("High");
        VNextBOROPageStepsNew.changeOrderPriority(TEST_ORDER_NUMBER, "green");
        VNextBOROWebPageValidationsNew.verifyPriorityIsCorrectForFirstOrder("Low");
        VNextBOROPageStepsNew.changeOrderPriority(TEST_ORDER_NUMBER, "none");
        VNextBOROWebPageValidationsNew.verifyPriorityIsCorrectForFirstOrder("Normal");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseUnCloseWorkOrder(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.closeOrder(TEST_ORDER_NUMBER, data.getProblemReason());
        VNextBOROPageStepsNew.reopenOrder(TEST_ORDER_NUMBER);
        VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable(data.getPhase());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect("Approved");
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeMoreThanOneTechniciansLinkedToOnePhase(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROPageStepsNew.openChangeTechnicianDialogForFirstOrder();
        VNextBOChangeTechnicianDialogStepsNew.changeTechnicianAndSave(data.getVendor(), data.getTechnician());
        VNextBOROWebPageValidationsNew.verifyTechniciansAreCorrectInTheTable(data.getTechnician());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceTechnician("roz_dis_part_money", "Drake Ramores");
        VNextBORODetailsStepsNew.setServiceTechnician("roz_en_part_money", "Eric Meahan");
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROWebPageValidationsNew.verifyTechniciansAreCorrectInTheTable("Drake Ramores");
        VNextBOROWebPageValidationsNew.verifyTechniciansAreCorrectInTheTable("Eric Meahan");
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndCreateNotes(String rowID, String description, JSONObject testData) {

        final String NOTE_TEXT = RandomStringUtils.randomAlphabetic(6);
        VNextBOROPageStepsNew.addNoteForFirstOrderFromOthersMenu(TEST_ORDER_NUMBER, NOTE_TEXT);
        VNextBOROWebPageValidationsNew.verifyNoteTextIsCorrectForFirstOrder(NOTE_TEXT, true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStockRoPoInvoiceColumn(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROPageStepsNew.changeStockNumberForFirstOrder("111");
        VNextBOROPageStepsNew.changeRoNumberForFirstOrder("111");
        VNextBOROPageStepsNew.changePoNumberForFirstOrder("111");
        VNextBOROPageStepsNew.changeStockNumberForFirstOrder(data.getStockNum());
        VNextBOROPageStepsNew.changeRoNumberForFirstOrder(data.getRoNum());
        VNextBOROPageStepsNew.changePoNumberForFirstOrder(data.getPoNum());
        VNextBOROWebPageValidationsNew.verifyStockNumbersAreCorrectInTheTable(data.getStockNum());
        VNextBOROWebPageValidationsNew.verifyRoNumbersAreCorrectInTheTable(data.getRoNum());
        VNextBOROWebPageValidationsNew.verifyPoNumbersAreCorrectInTheTable(data.getPoNum());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}