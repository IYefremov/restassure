package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOMonitorRepairLocationsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

//@Listeners(VideoListener.class)
public class BackOfficeMonitorRepairLocationsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOMonitorRepairLocationsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsSearch(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();

		repairlocationspage.verifyRepairLocationsTableColumnsAreVisible();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.selectSearchStatus(data.getLocationStatus());
		repairlocationspage.setSearchLocation(data.getLocationName());
		repairlocationspage.clickFindButton();

		Assert.assertTrue(repairlocationspage.repairLocationExists(data.getLocationName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsAdd(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.setNewRepairLocationName(data.getRepairLocationName());
		newrepairlocdialog.selectNewRepairLocationStatus(data.getRepairLocationStatus());
		newrepairlocdialog.setNewRepairLocationApproxRepairTime(data.getRepairLocationApproxRepairTime());
		newrepairlocdialog.setNewRepairLocationWorkingHours(data.getRepairLocationWorkDay1(), data.getRepairLocationStartTime1(), data.getRepairLocationEndTime1());
		newrepairlocdialog.setNewRepairLocationWorkingHours(data.getRepairLocationWorkDay2(), data.getRepairLocationStartTime2(), data.getRepairLocationEndTime2());
		newrepairlocdialog.selectAddressInfoTab();
		newrepairlocdialog.selectWorkingHoursTab();
		newrepairlocdialog.clickOKButton();

		repairlocationspage.deleteRepairLocationAndCancelDeleting(data.getRepairLocationName());
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationInTeamDefaultRepairLocationEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());

		monitorWebPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		VendorsTeamsWebPage vendorsteamspage = new VendorsTeamsWebPage(webdriver);
		monitorWebPage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible().setSearchTeamLocation(data.getVendorTeam()).clickFindButton();
		NewVendorTeamDialogWebPage newvendordialog = new NewVendorTeamDialogWebPage(webdriver);
		vendorsteamspage.clickEditVendorTeam(data.getVendorTeam());
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(data.getRepairLocationName());
		newvendordialog.clickCancelButton();

		monitorWebPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsDepartmentEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());

		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationDepartmentsTabWebPage repairlocationdepartmentstab = new RepairLocationDepartmentsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationDepartmentsLink(data.getRepairLocationName());
		repairlocationdepartmentstab.clickAddDepartmentButton();
		repairlocationdepartmentstab.setNewRepairLocationDepartmentName(data.getRepairLocationDepartment());
		repairlocationdepartmentstab.setNewRepairLocationDepartmentDescription(data.getRepairLocationDepartmentDescription());
		repairlocationdepartmentstab.clickNewRepairLocationDepartmentOKButton();
		repairlocationdepartmentstab.setRepairLocationDepartmentAsdefault(data.getRepairLocationDepartment());

		repairlocationdepartmentstab.clickEditRepairLocationDepartment(data.getRepairLocationDepartment());
		Assert.assertEquals(data.getRepairLocationDepartmentDescription(), repairlocationdepartmentstab.getNewRepairLocationDepartmentDescription());
		repairlocationdepartmentstab.setNewRepairLocationDepartmentName(data.getRepairLocationDepartmentNew());
		repairlocationdepartmentstab.setNewRepairLocationDepartmentDescription(data.getRepairLocationDepartmentDescriptionNew());
		repairlocationdepartmentstab.clickNewRepairLocationDepartmentOKButton();

		repairlocationdepartmentstab.clickEditRepairLocationDepartment(data.getRepairLocationDepartmentNew());
		Assert.assertEquals(data.getRepairLocationDepartmentDescriptionNew(), repairlocationdepartmentstab.getNewRepairLocationDepartmentDescription());
		repairlocationdepartmentstab.clickNewRepairLocationDepartmentCancelButton();

		repairlocationdepartmentstab.setRepairLocationDepartmentAsdefault(data.getRepairLocationDepartmentDefault());
		repairlocationdepartmentstab.deleteRepairLocationDepartment(data.getRepairLocationDepartmentNew());
		repairlocationdepartmentstab.closeNewTab(mainWindowHandle);

		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsPhasesEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();

		RepairLocationPhasesTabWebPage repairlocationphasestab = new RepairLocationPhasesTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationPhasesLink(data.getRepairLocationName());
		repairlocationphasestab.clickAddPhasesButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(data.getRepairLocationPhase());
		repairlocationphasestab.setNewRepairLocationPhaseDescription(data.getRepairLocationPhaseDescription());
		repairlocationphasestab.selectNewRepairLocationPhaseType(data.getRepairLocationPhaseType());
		repairlocationphasestab.selectNewRepairLocationPhaseCheckOutType(data.getRepairLocationPhaseCheckoutType());
		repairlocationphasestab.setNewRepairLocationPhaseApproxTransitionTime(data.getApproxTransTime());
		repairlocationphasestab.setNewRepairLocationPhaseApproxRepairTime(data.getApproxRepairTime());
		repairlocationphasestab.selectDoNotTrackIndividualServiceStatuses();
		repairlocationphasestab.selectStartServiceRequired();
		repairlocationphasestab.selectQCRequired();
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();

		repairlocationphasestab.clickEditRepairLocationPhase(data.getRepairLocationPhase());
		Assert.assertEquals(data.getRepairLocationPhaseDescription(), repairlocationphasestab.getNewRepairLocationPhaseDescription());
		Assert.assertEquals(data.getRepairLocationPhaseType(), repairlocationphasestab.getNewRepairLocationPhaseType());
		Assert.assertEquals(data.getRepairLocationPhaseCheckoutType(), repairlocationphasestab.getNewRepairLocationPhaseCheckOutType());
		Assert.assertEquals(data.getApproxTransTime(), repairlocationphasestab.getNewRepairLocationPhaseApproxTransitionTime());
		Assert.assertEquals(data.getApproxRepairTime(), repairlocationphasestab.getNewRepairLocationPhaseApproxRepairTime());
		Assert.assertTrue(repairlocationphasestab.isDoNotTrackIndividualServiceStatusesSelected());
		Assert.assertTrue(repairlocationphasestab.isStartServiceRequiredSelected());
		Assert.assertTrue(repairlocationphasestab.isQCRequiredSelected());

		repairlocationphasestab.setNewRepairLocationPhaseName(data.getRepairLocationPhaseNew());
		repairlocationphasestab.setNewRepairLocationPhaseDescription(data.getRepairLocationPhaseDescriptionNew());
		repairlocationphasestab.selectNewRepairLocationPhaseType(data.getRepairLocationPhaseTypeNew());
		repairlocationphasestab.selectNewRepairLocationPhaseCheckOutType(data.getRepairLocationPhaseCheckoutTypeNew());
		repairlocationphasestab.setNewRepairLocationPhaseApproxTransitionTime(data.getApproxTransTimeNew());
		repairlocationphasestab.setNewRepairLocationPhaseApproxRepairTime(data.getApproxRepairTimeNew());
		repairlocationphasestab.unselectDoNotTrackIndividualServiceStatuses();
		repairlocationphasestab.unselectStartServiceRequired();
		repairlocationphasestab.unselectQCRequired();
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();

		repairlocationphasestab.clickEditRepairLocationPhase(data.getRepairLocationPhaseNew());
		Assert.assertEquals(data.getRepairLocationPhaseDescriptionNew(), repairlocationphasestab.getNewRepairLocationPhaseDescription());
		Assert.assertEquals(data.getRepairLocationPhaseTypeNew(), repairlocationphasestab.getNewRepairLocationPhaseType());
		Assert.assertEquals(data.getRepairLocationPhaseCheckoutTypeNew(), repairlocationphasestab.getNewRepairLocationPhaseCheckOutType());
		Assert.assertEquals(data.getApproxTransTimeNew(), repairlocationphasestab.getNewRepairLocationPhaseApproxTransitionTime());
		Assert.assertEquals(data.getApproxRepairTimeNew(), repairlocationphasestab.getNewRepairLocationPhaseApproxRepairTime());
		Assert.assertFalse(repairlocationphasestab.isDoNotTrackIndividualServiceStatusesSelected());
		Assert.assertFalse(repairlocationphasestab.isStartServiceRequiredSelected());
		Assert.assertFalse(repairlocationphasestab.isQCRequiredSelected());
		repairlocationphasestab.clickNewRepairLocationPhaseCancelButton();

		repairlocationphasestab.deleteRepairLocationPhase(data.getRepairLocationPhaseNew());
		repairlocationphasestab.closeNewTab(mainWindowHandle);

		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsClientsEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationClientsTabWebPage repairlocationclientstab = new RepairLocationClientsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		repairlocationclientstab.selectRepairLocationClient(data.getRepairLocationClient()).clickAddRepairLocationClientButton().clickUpdateClientsButton();
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationclientstab = new RepairLocationClientsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationclientstab.repairLocationClientExists(data.getRepairLocationClient()));
		repairlocationclientstab.deleteRepairLocationClient(data.getRepairLocationClient());
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationclientstab = new RepairLocationClientsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationclientstab.repairLocationClientExists(data.getRepairLocationClient()));
		repairlocationclientstab.deleteRepairLocationClient(data.getRepairLocationClient());
		repairlocationclientstab.clickUpdateClientsButton();
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationclientstab = new RepairLocationClientsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		Assert.assertFalse(repairlocationclientstab.repairLocationClientExists(data.getRepairLocationClient()));
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsServicesEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationPhasesTabWebPage repairlocationphasestab = new RepairLocationPhasesTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationPhasesLink(data.getRepairLocationName());
		repairlocationphasestab.clickAddPhasesButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(data.getPhaseClosed());
		repairlocationphasestab.selectNewRepairLocationPhaseType(data.getPhaseClosedType());
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();

		repairlocationphasestab.clickAddPhasesButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(data.getPhaseStarted());
		repairlocationphasestab.selectNewRepairLocationPhaseType(data.getPhaseStartedType());
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		repairlocationphasestab.closeNewTab(mainWindowHandle);

		RepairLocationPhaseServicesTabWebPage repairlocationphaseservicestab = new RepairLocationPhaseServicesTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationServicesLink(data.getRepairLocationName());
		repairlocationphaseservicestab.selectWOType(data.getWoType());
		repairlocationphaseservicestab.selectPhase(data.getPhaseClosed());
		final int servicescoint = repairlocationphaseservicestab.getPhaseServicesTableRows().size();
		repairlocationphaseservicestab.selectAllServicesInTable();
		Assert.assertEquals(servicescoint, repairlocationphaseservicestab.getNumberOfSelectedServicesInTable());
		repairlocationphaseservicestab.clickAssignToSelectedservicesButton();
		List<WebElement> rows = repairlocationphaseservicestab.getPhaseServicesTableRows();
		for (WebElement row : rows) {
			Assert.assertEquals(data.getPhaseClosed(), repairlocationphaseservicestab.getTableRowPhaseValue(row));
		}
		final String servicestartedphase = repairlocationphaseservicestab.getTableRowPhaseServiceName(repairlocationphaseservicestab.getPhaseServicesTableRows().get(0));
		repairlocationphaseservicestab.selectServicePhaseValue(servicestartedphase, data.getPhaseStarted());
		repairlocationphaseservicestab.closeNewTab(mainWindowHandle);

		repairlocationphaseservicestab = new RepairLocationPhaseServicesTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationServicesLink(data.getRepairLocationName());
		repairlocationphaseservicestab.selectWOType(data.getWoType());
		Assert.assertEquals(servicescoint, repairlocationphaseservicestab.getPhaseServicesTableRows().size());
		Assert.assertEquals(servicescoint - 1, repairlocationphaseservicestab.getNumberOfPhaseServicesWithSelectedPhaseValue(data.getPhaseClosed()).size());
		Assert.assertEquals(1, repairlocationphaseservicestab.getNumberOfPhaseServicesWithSelectedPhaseValue(data.getPhaseStarted()).size());
		repairlocationphaseservicestab.closeNewTab(mainWindowHandle);
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsManagersEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();

		RepairLocationManagersTabWebPage repairlocationmanagerstab = new RepairLocationManagersTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		repairlocationmanagerstab.selectTeam(data.getTeam()).selectRepairLocationManager(data.getManager()).clickAddManagerButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = new RepairLocationManagersTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertFalse(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.selectTeam(data.getTeam()).selectRepairLocationManager(data.getManager()).clickAddManagerButton().clickUpdateManagersButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = new RepairLocationManagersTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.deleteRepairLocationManager(data.getManager());
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = new RepairLocationManagersTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.deleteRepairLocationManager(data.getManager());
		repairlocationmanagerstab.clickUpdateManagersButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = new RepairLocationManagersTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertFalse(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsUserSettingsEdit(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();

		RepairLocationUserSettingsTabWebPage repairlocationusersettingstab = new RepairLocationUserSettingsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		Assert.assertEquals(data.getCheckboxesAmount(), repairlocationusersettingstab.getAllUserSettingsCheckboxes().size());
		repairlocationusersettingstab.checkAllUserSettingsCheckboxes();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = new RepairLocationUserSettingsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesUnchecked();
		repairlocationusersettingstab.checkAllUserSettingsCheckboxes().clickUpdateSettingButton();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = new RepairLocationUserSettingsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesChecked();
		repairlocationusersettingstab.uncheckAllUserSettingsCheckboxes();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = new RepairLocationUserSettingsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesChecked();
		repairlocationusersettingstab.uncheckAllUserSettingsCheckboxes().clickUpdateSettingButton();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = new RepairLocationUserSettingsTabWebPage(webdriver);
		repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesUnchecked();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairLocationsNotActiveStatusForDefaultRepairLocation(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocationIfExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());

		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		TeamsWebPage teamspage = new TeamsWebPage(webdriver);
		companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible();
		teamspage.setTeamLocationSearchCriteria(data.getTeamName());
		teamspage.clickFindButton();
		teamspage.deleteTeamIfExists(data.getTeamName());
		NewTeamsDialogWebPage newteamsdialog = new NewTeamsDialogWebPage(webdriver);
		teamspage.clickAddTeamButton();
		newteamsdialog.setNewTeamName(data.getTeamName());
		newteamsdialog.selectTeamDefaultRepairLocation(data.getTeamDefaultLocation());
		newteamsdialog.clickAddTeamOKButton();

		monitorWebPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		NewRepairLocationDialogWebPage newlocationdialog = new NewRepairLocationDialogWebPage(webdriver);
		repairlocationspage.clickEditRepairLocation(data.getRepairLocationName());
		newlocationdialog.selectNewRepairLocationStatus(data.getRepairLocationStatusNotActive());
		newlocationdialog.clickOKButton();

		companypage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		teamspage = new TeamsWebPage(webdriver);
		companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible();
		teamspage.setTeamLocationSearchCriteria(data.getTeamName());
		teamspage.clickFindButton();
		newteamsdialog = new NewTeamsDialogWebPage(webdriver);
		teamspage.clickEditTeam(data.getTeamName());
		newteamsdialog.selectTeamDefaultRepairLocation(data.getTeamDefaultLocation());
		newteamsdialog.clickAddTeamOKButton();
		teamspage.deleteTeam(data.getTeamName());

		monitorWebPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		repairlocationspage = new RepairLocationsWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible();
		repairlocationspage.setSearchLocation(data.getRepairLocationName());
		repairlocationspage.clickFindButton();
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyCompletionOfRepairOrderQCPhaseAfterInspectionApproval(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		String randomLocationName = data.getRandomLocationName();

		backOfficeHeader.clickMonitorLink();
		monitorWebPage.clickRepairLocationsLink();
		RepairLocationsWebPage repairLocationsPage = new RepairLocationsWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		monitorWebPage.clickRepairLocationsLink();
		repairLocationsPage.clickAddRepairLocationButton();
		NewRepairLocationDialogWebPage newRepairLocationDialogWebPage = new NewRepairLocationDialogWebPage(webdriver);
		newRepairLocationDialogWebPage.setNewRepairLocationName(randomLocationName);
		newRepairLocationDialogWebPage.clickOKButton();
		String mainWindowHandle = webdriver.getWindowHandle();

		repairLocationsPage.makeSearchPanelVisible();
		repairLocationsPage.setSearchLocation(randomLocationName);
		repairLocationsPage.clickFindButton();
		Assert.assertTrue(repairLocationsPage.repairLocationExists(randomLocationName));
		RepairLocationPhasesTabWebPage phasesTab = new RepairLocationPhasesTabWebPage(webdriver);
		repairLocationsPage.clickRepairLocationPhasesLink(randomLocationName);
		phasesTab.clickEditRepairLocationPhase(data.getPhase());
		phasesTab.selectAutoComplete();
		phasesTab.clickNewRepairLocationPhaseCancelButton();

		phasesTab.clickEditRepairLocationPhase(data.getPhase());

		Assert.assertTrue(phasesTab.isCheckoutOptionDisabled(),
				"The checkout option is not disabled after clicking the Cancel button");
		Assert.assertTrue(phasesTab.isStartServiceRequiredEnabled(),
				"The start service required option is not enabled after clicking the Cancel button");

		phasesTab.selectAutoComplete()
				.clickNewRepairLocationPhaseOKButton()
				.clickEditRepairLocationPhase(data.getPhase());

		Assert.assertTrue(phasesTab.isCheckoutOptionDisabled(),
				"The checkout option is not disabled after clicking the Ok button");
		Assert.assertTrue(phasesTab.isStartServiceRequiredDisabled(),
				"The start service required option is not disabled after clicking the Ok button");

		phasesTab.clickNewRepairLocationPhaseCancelButton();
		repairLocationsPage.closeNewTab(mainWindowHandle);
		repairLocationsPage.deleteRepairLocation(randomLocationName);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAutoCompletionOfRepairOrderNewlyCreatedPhaseAfterInspectionApproval(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		String randomLocationName = data.getRandomLocationName();

		RepairLocationsWebPage repairLocationsPage = new RepairLocationsWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		monitorWebPage.clickRepairLocationsLink();
		repairLocationsPage.clickAddRepairLocationButton();
		NewRepairLocationDialogWebPage newRepairLocationDialogWebPage = new NewRepairLocationDialogWebPage(webdriver);
		newRepairLocationDialogWebPage.setNewRepairLocationName(randomLocationName);
		newRepairLocationDialogWebPage.clickOKButton();
		String mainWindowHandle = webdriver.getWindowHandle();

		repairLocationsPage.makeSearchPanelVisible();
		repairLocationsPage.setSearchLocation(randomLocationName);
		repairLocationsPage.clickFindButton();
		Assert.assertTrue(repairLocationsPage.repairLocationExists(randomLocationName));
		RepairLocationPhasesTabWebPage phasesTab = new RepairLocationPhasesTabWebPage(webdriver);
		repairLocationsPage.clickRepairLocationPhasesLink(randomLocationName);
		phasesTab.clickAddPhasesButton();
		phasesTab.setNewRepairLocationPhaseName(data.getPhase());
		phasesTab.clickNewRepairLocationPhaseOKButton();
		phasesTab.clickEditRepairLocationPhase(data.getPhase());
		phasesTab.selectAutoComplete();
		phasesTab.clickNewRepairLocationPhaseCancelButton();
		phasesTab.clickEditRepairLocationPhase(data.getPhase());

		Assert.assertTrue(phasesTab.isCheckoutOptionEnabled(),
				"The checkout option is not enabled after clicking the Cancel button");
		Assert.assertTrue(phasesTab.isStartServiceRequiredEnabled(),
				"The start service required option is not enabled after clicking the Cancel button");

		phasesTab.selectAutoComplete()
				.clickNewRepairLocationPhaseOKButton()
				.clickEditRepairLocationPhase(data.getPhase());

		Assert.assertTrue(phasesTab.isCheckoutOptionDisabled(),
				"The checkout option is not disabled after clicking the Ok button");
		Assert.assertTrue(phasesTab.isStartServiceRequiredDisabled(),
				"The start service required option is not disabled after clicking the Ok button");

		phasesTab.clickNewRepairLocationPhaseCancelButton();
		repairLocationsPage.closeNewTab(mainWindowHandle);
		repairLocationsPage.deleteRepairLocation(randomLocationName);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAutoCompletionOfInvoicedRepairOrdersIsConfigured(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		String randomLocationName = data.getRandomLocationName();

		RepairLocationsWebPage repairLocationsPage = new RepairLocationsWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		monitorWebPage.clickRepairLocationsLink();
		repairLocationsPage.clickAddRepairLocationButton();
		NewRepairLocationDialogWebPage newRepairLocationDialogWebPage = new NewRepairLocationDialogWebPage(webdriver);
		newRepairLocationDialogWebPage.setNewRepairLocationName(randomLocationName);
		newRepairLocationDialogWebPage.clickOKButton();

		repairLocationsPage.makeSearchPanelVisible();
		repairLocationsPage.setSearchLocation(randomLocationName);
		repairLocationsPage.clickFindButton();
		Assert.assertTrue(repairLocationsPage.repairLocationExists(randomLocationName));
		NewRepairLocationDialogWebPage newRepairLocationDialog = new NewRepairLocationDialogWebPage(webdriver);
		repairLocationsPage.clickEditRepairLocation(randomLocationName);
		Assert.assertFalse(newRepairLocationDialog.isCompleteInvoicesROsOptionChecked(),
				"The Complete invoiced ROs option is checked, but should be unchecked");
		newRepairLocationDialog.clickCompleteInvoicesROsCheckbox();
		newRepairLocationDialog.clickOKButton();
		repairLocationsPage.clickEditRepairLocation(randomLocationName);

		Assert.assertTrue(newRepairLocationDialog.isCompleteInvoicesROsOptionChecked(),
				"The Complete invoiced ROs option is NOT checked, but should be checked");
		newRepairLocationDialog.clickCancelButton();
		repairLocationsPage.deleteRepairLocation(randomLocationName);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAutoCompletionOfInvoicedRepairOrdersIsNotSavedAfterClickingCancelButton(String rowID, String description, JSONObject testData) {

		BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		String randomLocationName = data.getRandomLocationName();

		RepairLocationsWebPage repairLocationsPage = new RepairLocationsWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		monitorWebPage.clickRepairLocationsLink();
		repairLocationsPage.clickAddRepairLocationButton();
		NewRepairLocationDialogWebPage newRepairLocationDialogWebPage = new NewRepairLocationDialogWebPage(webdriver);
		newRepairLocationDialogWebPage.setNewRepairLocationName(randomLocationName);
		newRepairLocationDialogWebPage.clickOKButton();

		repairLocationsPage.makeSearchPanelVisible();
		repairLocationsPage.setSearchLocation(randomLocationName);
		repairLocationsPage.clickFindButton();
		Assert.assertTrue(repairLocationsPage.repairLocationExists(randomLocationName));
		NewRepairLocationDialogWebPage newRepairLocationDialog = new NewRepairLocationDialogWebPage(webdriver);
		repairLocationsPage.clickEditRepairLocation(randomLocationName);
		Assert.assertFalse(newRepairLocationDialog.isCompleteInvoicesROsOptionChecked(),
				"The Complete invoiced ROs option is checked, but should be unchecked");
		newRepairLocationDialog.clickCompleteInvoicesROsCheckbox();
		newRepairLocationDialog.clickCancelButton();
		repairLocationsPage.clickEditRepairLocation(randomLocationName);

		Assert.assertFalse(newRepairLocationDialog.isCompleteInvoicesROsOptionChecked(),
				"The Complete invoiced ROs option is NOT checked, but should be checked");
		newRepairLocationDialog.clickCancelButton();
		repairLocationsPage.deleteRepairLocation(randomLocationName);
	}
}