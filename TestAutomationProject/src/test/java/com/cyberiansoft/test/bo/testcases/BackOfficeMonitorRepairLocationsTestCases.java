package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOMonitorRepairLocationsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(VideoListener.class)
public class BackOfficeMonitorRepairLocationsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOMonitorRepairLocationsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsSearch(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();

		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();

		repairlocationspage.verifyRRepairLocationsTableColumnsAreVisible();
		repairlocationspage.makeSearchPanelVisible().selectSearchStatus(data.getLocationStatus()).setSearchLocation(data.getLocationName()).clickFindButton();

		Assert.assertTrue(repairlocationspage.repairLocationExists(data.getLocationName()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsAdd(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.setNewRepairLocationName(data.getRepairLocationName()).selectNewRepairLocationStatus(data.getRepairLocationStatus())
			.setNewRepairLocationApproxRepairTime(data.getRepairLocationApproxRepairTime())
			.setNewRepairLocationWorkingHours(data.getRepairLocationWorkDay1(), data.getRepairLocationStartTime1(), data.getRepairLocationEndTime1())
			.setNewRepairLocationWorkingHours(data.getRepairLocationWorkDay2(), data.getRepairLocationStartTime2(), data.getRepairLocationEndTime2())
			.selectPhaseEnforcementOption().selectAddressInfoTab().selectWorkingHoursTab().clickOKButton();

		repairlocationspage.deleteRepairLocationAndCancelDeleting(data.getRepairLocationName());
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationInTeamDefaultRepairLocationEdit(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());

		monitorpage = backOfficeHeader.clickMonitorLink();
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible().setSearchTeamLocation(data.getVendorTeam()).clickFindButton();
		NewVendorTeamDialogWebPage newvendordialog = vendorsteamspage.clickEditVendorTeam(data.getVendorTeam());
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(data.getRepairLocationName());
		newvendordialog.clickCancelButton();

		monitorpage = backOfficeHeader.clickMonitorLink();
		repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsDepartmentEdit(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());

		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationDepartmentsTabWebPage repairlocationdepartmentstab = repairlocationspage.clickRepairLocationDepartmentsLink(data.getRepairLocationName());
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
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();

		RepairLocationPhasesTabWebPage repairlocationphasestab = repairlocationspage.clickRepairLocationPhasesLink(data.getRepairLocationName());
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
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationClientsTabWebPage repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		repairlocationclientstab.selectRepairLocationClient(data.getRepairLocationClient()).clickAddRepairLocationClientButton().clickUpdateClientsButton();
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationclientstab.repairLocationClientExists(data.getRepairLocationClient()));
		repairlocationclientstab.deleteRepairLocationClient(data.getRepairLocationClient());
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationclientstab.repairLocationClientExists(data.getRepairLocationClient()));
		repairlocationclientstab.deleteRepairLocationClient(data.getRepairLocationClient());
		repairlocationclientstab.clickUpdateClientsButton();
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(data.getRepairLocationName());
		Assert.assertFalse(repairlocationclientstab.repairLocationClientExists(data.getRepairLocationClient()));
		repairlocationclientstab.closeNewTab(mainWindowHandle);

		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsServicesEdit(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationPhasesTabWebPage repairlocationphasestab = repairlocationspage.clickRepairLocationPhasesLink(data.getRepairLocationName());
		repairlocationphasestab.clickAddPhasesButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(data.getPhaseClosed());
		repairlocationphasestab.selectNewRepairLocationPhaseType(data.getPhaseClosedType());
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();

		repairlocationphasestab.clickAddPhasesButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(data.getPhaseStarted());
		repairlocationphasestab.selectNewRepairLocationPhaseType(data.getPhaseStartedType());
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		repairlocationphasestab.closeNewTab(mainWindowHandle);

		RepairLocationPhaseServicesTabWebPage repairlocationphaseservicestab = repairlocationspage.clickRepairLocationServicesLink(data.getRepairLocationName());
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

		repairlocationphaseservicestab = repairlocationspage.clickRepairLocationServicesLink(data.getRepairLocationName());
		repairlocationphaseservicestab.selectWOType(data.getWoType());
		Assert.assertEquals(servicescoint, repairlocationphaseservicestab.getPhaseServicesTableRows().size());
		Assert.assertEquals(servicescoint-1, repairlocationphaseservicestab.getNumberOfPhaseServicesWithSelectedPhaseValue(data.getPhaseClosed()).size());
		Assert.assertEquals(1, repairlocationphaseservicestab.getNumberOfPhaseServicesWithSelectedPhaseValue(data.getPhaseStarted()).size());
		repairlocationphaseservicestab.closeNewTab(mainWindowHandle);
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsManagersEdit(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();

		RepairLocationManagersTabWebPage repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		repairlocationmanagerstab.selectTeam(data.getTeam()).selectRepairLocationManager(data.getManager()).clickAddManagerButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertFalse(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.selectTeam(data.getTeam()).selectRepairLocationManager(data.getManager()).clickAddManagerButton().clickUpdateManagersButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.deleteRepairLocationManager(data.getManager());
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertTrue(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.deleteRepairLocationManager(data.getManager());
		repairlocationmanagerstab.clickUpdateManagersButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);

		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(data.getRepairLocationName());
		Assert.assertFalse(repairlocationmanagerstab.isRepairLocationManagerExists(data.getManager()));
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsUserSettingsEdit(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());

		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());
		String mainWindowHandle = webdriver.getWindowHandle();

		RepairLocationUserSettingsTabWebPage repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		Assert.assertEquals(data.getCheckboxesAmount(), repairlocationusersettingstab.getAllUserSettingsCheckboxes().size());
		repairlocationusersettingstab.checkAllUserSettingsCheckboxes();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesUnchecked();
		repairlocationusersettingstab.checkAllUserSettingsCheckboxes().clickUpdateSettingButton();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesChecked();
		repairlocationusersettingstab.uncheckAllUserSettingsCheckboxes();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesChecked();
		repairlocationusersettingstab.uncheckAllUserSettingsCheckboxes().clickUpdateSettingButton();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(data.getRepairLocationName());
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesUnchecked();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);

		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMonitorRepairLocationsNotActiveStatusForDefaultRepairLocation(String rowID, String description, JSONObject testData) {

        BOMonitorRepairLocationsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorRepairLocationsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        MonitorWebPage monitorpage = backOfficeHeader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(data.getRepairLocationName());
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(data.getRepairLocationName(), data.getRepairLocationStatus(), data.getRepairLocationTimeZone());

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		TeamsWebPage teamspage = companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible().setTeamLocationSearchCriteria(data.getTeamName()).clickFindButton();
		teamspage.deleteTeamIfExists(data.getTeamName());
		NewTeamsDialogWebPage newteamsdialog = teamspage.clickAddTeamButton();
		newteamsdialog.setNewTeamName(data.getTeamName()).selectTeamDefaultRepairLocation(data.getTeamDefaultLocation()).clickAddTeamOKButton();

		monitorpage = backOfficeHeader.clickMonitorLink();
		repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		NewRepairLocationDialogWebPage newlocationdialog = repairlocationspage.clickEditRepairLocation(data.getRepairLocationName());
		newlocationdialog.selectNewRepairLocationStatus(data.getRepairLocationStatusNotActive()).clickOKButton();

		companypage = backOfficeHeader.clickCompanyLink();
		teamspage = companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible().setTeamLocationSearchCriteria(data.getTeamName()).clickFindButton();
		newteamsdialog = teamspage.clickEditTeam(data.getTeamName());
		newteamsdialog.selectTeamDefaultRepairLocation(data.getTeamDefaultLocation()).clickAddTeamOKButton();
		teamspage.deleteTeam(data.getTeamName());

		monitorpage = backOfficeHeader.clickMonitorLink();
		repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(data.getRepairLocationName()).clickFindButton();
		repairlocationspage.deleteRepairLocation(data.getRepairLocationName());
	}
}
