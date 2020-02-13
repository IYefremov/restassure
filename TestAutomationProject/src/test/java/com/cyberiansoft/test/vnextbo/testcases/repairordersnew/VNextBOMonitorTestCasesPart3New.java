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
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VNextBOMonitorTestCasesPart3New extends BaseTestCase {

	@BeforeClass
	public void settingUp() {

		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
		VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
		VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItXIcon(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.addServiceWithoutSaveXIcon(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), false);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItCancelButton(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.addServiceWithoutSaveCancelButton(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), false);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.changeFlag(data.getFlag(),data.getFlagColor());
		VNextBORODetailsValidationsNew.verifyFlagIsCorrect(data.getFlagColor());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToActive(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), "");
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToCompleted(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToAudited(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToRefused(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToRework(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), "");
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToSkipped(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
		Utils.goToPreviousPage();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}
/*
	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeVendorPriceOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeVendorTechnicianOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanCreateNoteOfServiceOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeTargetDateOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeMoreInformationOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNoteOfRoService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

	}*/
}