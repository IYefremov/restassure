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
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBONotesDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBONotesDialogValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorTestCasesPart2New extends BaseTestCase {

	@BeforeClass
	public void settingUp() {

		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
		VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
		VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNotesWithXIcon(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
        VNextBOROPageStepsNew.addNoteForFirstNoteAndNotSaveWItXIcon("91411");
		VNextBOROWebPageValidationsNew.verifyNoteTextIsCorrectForFirstOrder("91411", false);
		VNextBOROPageStepsNew.openFirstOrderNotes();
		VNextBONotesDialogValidationsNew.verifyNoteInTheNotesList("91411", false);
		VNextBONotesDialogStepsNew.closeDialogWithXIcon();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStockOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		String newStockNumber = RandomStringUtils.randomAlphabetic(10);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.changeStockNumberForFirstOrder(newStockNumber);
		VNextBOROWebPageValidationsNew.verifyStockNumbersAreCorrectInTheTable(newStockNumber);
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeRoNumOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		String newRoNumber = RandomStringUtils.randomAlphabetic(10);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.changeRoNumberForFirstOrder(newRoNumber);
		VNextBOROWebPageValidationsNew.verifyRoNumbersAreCorrectInTheTable(newRoNumber);
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.reopenOrderIfNeeded();
		VNextBORODetailsStepsNew.changeOrderStatus(data.getStatus());
		VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect(data.getStatus());
		Utils.goToPreviousPage();
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotChangeStatusOfRoToDraft(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.reopenOrderIfNeeded();
		String initialOrderStatus = VNextBORODetailsStepsNew.getOrderStatus();
		VNextBORODetailsStepsNew.changeOrderStatus(data.getStatus());
		VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect(initialOrderStatus);
		Utils.goToPreviousPage();
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToClosedWithCompletedReason(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.reopenOrderIfNeeded();
		VNextBORODetailsStepsNew.closeOrderWithCompletedReason();
		VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect(data.getStatus());
		VNextBORODetailsValidationsNew.verifyPhaseTextStatusIsCorrect(data.getPhase(), "Completed");
		VNextBORODetailsStepsNew.reopenOrderIfNeeded();
		VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect("Approved");
		VNextBORODetailsValidationsNew.verifyPhaseTextStatusIsCorrect(data.getPhase(), data.getPhaseStatus());
		VNextBORODetailsStepsNew.startServiceByServiceName(data.getPhase(), data.getService());
		VNextBORODetailsStepsNew.startServicesForPhase("Detail Station");
		Utils.goToPreviousPage();
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriorityOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.changePriority(data.getPriority());
		VNextBORODetailsValidationsNew.verifyOrderPriorityIsCorrect(data.getPriority());
		Utils.goToPreviousPage();
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBOROWebPageValidationsNew.verifyPriorityIsCorrectForFirstOrder(data.getPriority());
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.addService(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), true);
		VNextBORODetailsValidationsNew.verifyServicePriceIsCorrect(data.getServiceDescription(), data.getServicePrice());
		VNextBORODetailsValidationsNew.verifyServiceQuantityIsCorrect(data.getServiceDescription(), data.getServiceQuantity());
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewMoneyService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.addService(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), true);
		VNextBORODetailsValidationsNew.verifyServicePriceIsCorrect(data.getServiceDescription(), data.getServicePrice());
		VNextBORODetailsValidationsNew.verifyServiceQuantityIsCorrect(data.getServiceDescription(), data.getServiceQuantity());
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewLaborService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.addLaborService(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), true);
		VNextBORODetailsValidationsNew.verifyServicePriceIsCorrect(data.getServiceDescription(), data.getServiceLaborRate());
		VNextBORODetailsValidationsNew.verifyServiceQuantityIsCorrect(data.getServiceDescription(), data.getServiceLaborTime() + "0 hr");
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewPartService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		int	initialPartServicesNumber = VNextBORODetailsStepsNew.getPartServicesAmount();
		VNextBORODetailsStepsNew.addPartService(data);
		VNextBORODetailsValidationsNew.verifyPartServicesAmountIsCorrect(initialPartServicesNumber + 1);
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}
}