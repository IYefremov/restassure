package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.monitor.repairlocations.BOEditPhaseDialogSteps;
import com.cyberiansoft.test.bo.steps.monitor.repairlocations.BOLocationPhasesWindowSteps;
import com.cyberiansoft.test.bo.steps.monitor.repairlocations.BORepairLocationsPageSteps;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
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

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorTestCasesPart3New extends BaseTestCase {

	@BeforeClass
	public void settingUp() {

		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
        final String location = "Best Location Automation";
        try {
            VNextBOHomeWebPageSteps.clickLogo();
            VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
            BOMenuSteps.open(Menu.MONITOR, SubMenu.REPAIR_LOCATIONS);
            final String mainWindow = BORepairLocationsPageSteps.openPhasesLocationDialogByLocationName(location);
            BOLocationPhasesWindowSteps.openEditDialogByPhaseName("PDR Station");
            BOEditPhaseDialogSteps.setWorkStatusTracking("Service And Phase Levels");
            BOEditPhaseDialogSteps.confirm();
            Utils.closeAllNewWindowsExceptParentTab(mainWindow);
        } catch (Exception ignored) {}
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
		VNextBOBreadCrumbInteractions.setLocation(location);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber("O-000-147163");
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        final String status = data.getServiceStatuses()[1];
        final String service = data.getService();

        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(service, data.getServiceStatuses()[0]);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitABit(3000);
        VNextBORODetailsStepsNew.setServiceStatusIfNeeded(service, status);
		WaitUtilsWebDriver.waitABit(3000);
		VNextBORODetailsValidationsNew.verifyServiceStartedDateIsCorrect(service, data.getServiceStartedDate());
		if (status.equals(OrderMonitorServiceStatuses.ACTIVE.getValue()) || status.equals(OrderMonitorServiceStatuses.REWORK.getValue())) {
            VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(service, "");
        } else {
            VNextBORODetailsValidationsNew.verifyServiceCompletedDateIsCorrect(
                    service, CustomDateProvider.getCurrentDateInFullFormat(true));
        }
		VNextBORODetailsValidationsNew.verifyServiceHelpInfoIsCorrect(service, status);
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
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanCheckInCheckOutRO(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBORODetailsStepsNew.checkInCheckOutPhase(data.getPhase());
		VNextBORODetailsStepsNew.checkInCheckOutPhase(data.getPhase());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeePartsOfRO(String rowID, String description, JSONObject testData) {

		VNextBORODetailsValidationsNew.verifyPartsTableIsDisplayed();
		VNextBORODetailsValidationsNew.verifyPartsServicesAreDisplayed();
	}
}