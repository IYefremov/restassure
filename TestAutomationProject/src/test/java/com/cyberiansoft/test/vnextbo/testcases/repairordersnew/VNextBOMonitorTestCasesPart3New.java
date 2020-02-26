package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBONotesDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBONotesDialogValidationsNew;
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
		VNextBOROPageStepsNew.searchOrdersByOrderNumber("O-000-147163");
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
	}

/*	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItXIcon(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBORODetailsStepsNew.addServiceWithoutSaveXIcon(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), false);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItCancelButton(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setServiceDescription(data.getServiceDescription() + RandomStringUtils.randomAlphabetic(7));
		VNextBORODetailsStepsNew.addServiceWithoutSaveCancelButton(data);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getServiceDescription(), false);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.changeFlag(data.getFlag(),data.getFlagColor());
		VNextBORODetailsValidationsNew.verifyFlagIsCorrect(data.getFlagColor());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(data.getService(), data.getServiceStartedDate());
		if (data.getServiceStatuses()[1].equals("Active") || data.getServiceStatuses()[1].equals("Rework"))
			VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), "");
		else VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(data.getService(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(data.getService(), data.getServiceStatuses()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsValidationsNew.verifyServiceTableContainsCorrectColumns();
		VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getService(), true);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeVendorPriceOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceVendorPrice(data.getService(), data.getServiceVendorPrices()[0]);
		VNextBORODetailsValidationsNew.verifyServiceVendorPriceIsCorrect(data.getService(), data.getServiceVendorPrices()[0]);
		VNextBORODetailsStepsNew.setServiceVendorPrice(data.getService(), data.getServiceVendorPrices()[1]);
		VNextBORODetailsValidationsNew.verifyServiceVendorPriceIsCorrect(data.getService(), data.getServiceVendorPrices()[1]);
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeVendorTechnicianOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setServiceVendor(data.getService(), data.getVendor());
		VNextBORODetailsStepsNew.setServiceTechnician(data.getService(), data.getTechnician());
		VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getService(), data.getVendor());
		VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getService(), data.getTechnician());
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNoteOfRoService(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(7));
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.addNoteForService(data.getService(), data.getNotesMessage(), false);
		VNextBORODetailsStepsNew.openNotesForService(data.getService());
		VNextBONotesDialogValidationsNew.verifyNoteInTheNotesList(data.getNotesMessage(), false);
		VNextBONotesDialogStepsNew.closeDialogWithXIcon();
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanCreateNoteOfServiceOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(7));
		VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
		VNextBORODetailsStepsNew.addNoteForService(data.getService(), data.getNotesMessage(), true);
		VNextBORODetailsStepsNew.openNotesForService(data.getService());
		VNextBONotesDialogValidationsNew.verifyNoteInTheNotesList(data.getNotesMessage(), true);
		VNextBONotesDialogStepsNew.closeDialogWithXIcon();
		VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeMoreInformationOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(7));
		VNextBORODetailsValidationsNew.verifyMoreInfoSectionContainsCorrectFields();
	}*/

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanCheckInRO(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.checkInPhase(data.getPhase());
		VNextBORODetailsValidationsNew.verifyPhaseIsCheckedInCheckedOut(data.getPhase(), true);
		VNextBORODetailsStepsNew.checkOutPhase(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanCheckOutRO(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.checkInPhase(data.getPhase());
		VNextBORODetailsStepsNew.checkOutPhase(data.getPhase());
		VNextBORODetailsValidationsNew.verifyPhaseIsCheckedInCheckedOut(data.getPhase(), false);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeePartsOfRO(String rowID, String description, JSONObject testData) {

		VNextBORODetailsValidationsNew.verifyPartsTableIsDisplayed();
		VNextBORODetailsValidationsNew.verifyPartsServicesAreDisplayed();
	}
}