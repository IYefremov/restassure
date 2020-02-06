package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.MonitorWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewVendorTeamDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.VendorsTeamsWebPage;
import com.cyberiansoft.test.dataclasses.bo.BOMonitorVendorTeamsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

//@Listeners(VideoListener.class)
public class BackOfficeMonitorVendorTeamsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOMonitorVendorTeamsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorsTeamsSearch(String rowID, String description, JSONObject testData) {

		BOMonitorVendorTeamsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorVendorTeamsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		VendorsTeamsWebPage vendorsteamspage = new VendorsTeamsWebPage(webdriver);
		monitorpage.clickVendorsTeamsLink();

		vendorsteamspage.verifyVendorsTeamsTableColumnsAreVisible();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(data.getVendorTeam());
		vendorsteamspage.selectSearchType(data.getVendorType());
		vendorsteamspage.selectSearchTimeZone(data.getTimeZone());

		vendorsteamspage.clickFindButton();
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(vendorsteamspage.getVendorsTeamsTableRowCount()));
		Assert.assertTrue(vendorsteamspage.isActiveVendorTeamPresent(data.getVendorTeam()));
		Assert.assertEquals(data.getDefaultLocation(), vendorsteamspage.getTableVendorTeamLocation(data.getVendorTeam()));
		Assert.assertEquals(data.getVendorType(), vendorsteamspage.getTableVendorTeamType(data.getVendorTeam()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorTeamAdd(String rowID, String description, JSONObject testData) {

		BOMonitorVendorTeamsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorVendorTeamsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		VendorsTeamsWebPage vendorsteamspage = new VendorsTeamsWebPage(webdriver);
		monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(data.getVendorTeam());
		vendorsteamspage.clickFindButton();
		if (vendorsteamspage.isActiveVendorTeamPresent(data.getVendorTeam())) {
			vendorsteamspage.archiveVendorTeam(data.getVendorTeam());
		}

		NewVendorTeamDialogWebPage newvendordialog = vendorsteamspage.clickAddVendorTeamButton();

		newvendordialog.setNewVendorTeamName(data.getVendorTeam());
		newvendordialog.selectNewVendorTeamTimezone(data.getTimeZone());
		newvendordialog.setNewVendorTeamDescription(data.getVendorDescription());
		newvendordialog.setNewVendorTeamAccountingID(data.getVendorAccountingId());
		newvendordialog.selectNewVendorTeamArea(data.getVendorArea());
		newvendordialog.selectNewVendorTeamTimesheetType(data.getVendorTimeSheetType());
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(data.getDefaultLocation());
		newvendordialog.selectNewVendorTeamType(data.getVendorType());
		newvendordialog.selectNewVendorTeamAdditionalRepairLocations(data.getAdditionalLocation());
		newvendordialog.clickCancelButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorTeamsEdit(String rowID, String description, JSONObject testData) {

		BOMonitorVendorTeamsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorVendorTeamsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		VendorsTeamsWebPage vendorsteamspage = new VendorsTeamsWebPage(webdriver);
		monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(data.getVendorTeam());
		vendorsteamspage.clickFindButton();
		if (vendorsteamspage.isActiveVendorTeamPresent(data.getVendorTeam())) {
			vendorsteamspage.archiveVendorTeam(data.getVendorTeam());
		}

		if (vendorsteamspage.isActiveVendorTeamPresent(data.getVendorTeamEdited())) {
			vendorsteamspage.archiveVendorTeam(data.getVendorTeamEdited());
		}
		vendorsteamspage.createNewVendorTeam(data.getVendorTeam(), data.getTimeZone(), data.getVendorDescription(), data.getVendorTimeSheetType(), data.getDefaultLocation(), data.getAdditionalLocation());

		NewVendorTeamDialogWebPage newvendordialog = new NewVendorTeamDialogWebPage(webdriver);
		vendorsteamspage.clickEditVendorTeam(data.getVendorTeam());
		newvendordialog.setNewVendorTeamName(data.getVendorTeamEdited());
		newvendordialog.selectNewVendorTeamTimezone(data.getTimeZoneEdited());
		newvendordialog.selectNewVendorTeamTimesheetType(data.getVendorTimeSheetTypeEdited());
		newvendordialog.selectNewVendorTeamType(data.getVendorTypeEdited());
		newvendordialog.setNewVendorCompany(data.getVendorCompany());
		newvendordialog.clickOKButton();

		newvendordialog = new NewVendorTeamDialogWebPage(webdriver);
		vendorsteamspage.clickEditVendorTeam(data.getVendorTeamEdited());
		Assert.assertEquals(data.getVendorTeamEdited(), newvendordialog.getNewVendorTeamName());
		Assert.assertEquals(data.getTimeZoneEdited(), newvendordialog.getNewVendorTeamTimezone());
        Assert.assertEquals(data.getVendorTimeSheetTypeEdited(), newvendordialog.getNewVendorTeamTimesheetType());
		Assert.assertEquals(data.getVendorTypeEdited(), newvendordialog.getNewVendorTeamType());
		Assert.assertEquals(data.getVendorCompany(), newvendordialog.getNewVendorCompany());

		newvendordialog.setNewVendorTeamName(data.getVendorTeam());
		newvendordialog.selectNewVendorTeamTimezone(data.getTimeZone());
		newvendordialog.selectNewVendorTeamTimesheetType(data.getVendorTimeSheetType());
		newvendordialog.selectNewVendorTeamType(data.getVendorType());
		newvendordialog.clickOKButton();

		newvendordialog = new NewVendorTeamDialogWebPage(webdriver);
		vendorsteamspage.clickEditVendorTeam(data.getVendorTeam());
		Assert.assertEquals(data.getVendorTeam(), newvendordialog.getNewVendorTeamName());
		Assert.assertEquals(data.getTimeZone(), newvendordialog.getNewVendorTeamTimezone());
		Assert.assertEquals(data.getVendorTimeSheetType(), newvendordialog.getNewVendorTeamTimesheetType());
		Assert.assertEquals(data.getVendorType(), newvendordialog.getNewVendorTeamType());
		newvendordialog.clickCancelButton();
		vendorsteamspage.archiveVendorTeam(data.getVendorTeam());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorTeamsArchive(String rowID, String description, JSONObject testData) {

		BOMonitorVendorTeamsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorVendorTeamsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		VendorsTeamsWebPage vendorsTeamsPage = new VendorsTeamsWebPage(webdriver);
		monitorpage.clickVendorsTeamsLink();
		vendorsTeamsPage.makeSearchPanelVisible();
		vendorsTeamsPage.setSearchTeamLocation(data.getVendorTeam());
		vendorsTeamsPage.clickFindButton();

		vendorsTeamsPage.verifyThatActiveVendorTeamExists(data.getVendorTeam(), data.getTimeZone(), data.getVendorDescription(), data.getVendorTimeSheetType(),
				data.getDefaultLocation(), data.getAdditionalLocation());
		int activeVendorTeamsCountBefore = vendorsTeamsPage.getActiveVendorTeamsCountOnPage(data.getVendorTeam());

		vendorsTeamsPage.archiveVendorTeam(data.getVendorTeam());
		Assert.assertEquals(activeVendorTeamsCountBefore - 1, vendorsTeamsPage.getActiveVendorTeamsCountOnPage(data.getVendorTeam()));
		vendorsTeamsPage.clickArchivedTab();
		int archivedVendorTeamsCountBefore = vendorsTeamsPage.getArchivedVendorTeamsCount(data.getVendorTeam());
		vendorsTeamsPage.restoreVendorTeam(data.getVendorTeam());
		Assert.assertEquals(archivedVendorTeamsCountBefore - 1, vendorsTeamsPage
				.getArchivedVendorTeamsCount(data.getVendorTeam()), "The archived vendors team is still present!");

		vendorsTeamsPage.clickActiveTab();
		Assert.assertEquals(activeVendorTeamsCountBefore, vendorsTeamsPage.getActiveVendorTeamsCountOnPage(data.getVendorTeam()),
				"The archived vendors team is still present!");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorTeamsAuditLog(String rowID, String description, JSONObject testData) {

		BOMonitorVendorTeamsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorVendorTeamsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		VendorsTeamsWebPage vendorsTeamsPage = new VendorsTeamsWebPage(webdriver);
		monitorPage.clickVendorsTeamsLink();
		vendorsTeamsPage.makeSearchPanelVisible();
		vendorsTeamsPage.setSearchTeamLocation(data.getVendorTeam());
		vendorsTeamsPage.clickFindButton();

		vendorsTeamsPage.verifyThatActiveVendorTeamExists(data.getVendorTeam());

		vendorsTeamsPage.clickActiveTab();
		String mainWindowHandle = webdriver.getWindowHandle();
		vendorsTeamsPage.clickAuditLogButtonForVendorTeam(data.getVendorTeam());
		int auditLogsRowsCount = vendorsTeamsPage.getVendorsTeamsAuditLogTableRows().size();
		vendorsTeamsPage.closeNewTab(mainWindowHandle);

		vendorsTeamsPage.archiveVendorTeam(data.getVendorTeam());
		vendorsTeamsPage.clickArchivedTab();
		int archivedVendorTeamsCountBefore = vendorsTeamsPage.getArchivedVendorTeamsCount(data.getVendorTeam());
		vendorsTeamsPage.restoreVendorTeam(data.getVendorTeam());
		Assert.assertEquals(archivedVendorTeamsCountBefore - 1, vendorsTeamsPage
				.getArchivedVendorTeamsCount(data.getVendorTeam()), "The archived vendors team is still present!");

		vendorsTeamsPage.clickActiveTab();
		vendorsTeamsPage.clickAuditLogButtonForVendorTeam(data.getVendorTeam());
		List<WebElement> auditLogTableRows = vendorsTeamsPage.getVendorsTeamsAuditLogTableRows();
		Assert.assertEquals(data.getAuditLogsMessageRestored(), vendorsTeamsPage.getAuditLogVendorsTeamsTableLine1Text());
		Assert.assertEquals(data.getAuditLogsMessageDeleted(), vendorsTeamsPage.getAuditLogVendorsTeamsTableLine2Text());
		vendorsTeamsPage.closeNewTab(mainWindowHandle);
		Assert.assertEquals(auditLogsRowsCount + 2, auditLogTableRows.size());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorTeamsGuestsEdit(String rowID, String description, JSONObject testData) {

		BOMonitorVendorTeamsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorVendorTeamsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		VendorsTeamsWebPage vendorsteamspage = new VendorsTeamsWebPage(webdriver);
		monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(data.getVendorTeam());
		vendorsteamspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		vendorsteamspage.clickGuestsLinkForVendorTeam(data.getVendorTeam());

		if (vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee())) {
			vendorsteamspage.deleteTeamGuestEmployee(data.getVendorTeamEmployee());
		}

		vendorsteamspage.selectTeamGuestEmployees(data.getVendorTeamEmployee());
		vendorsteamspage.clickAddTeamGuestEmployeesButton();
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee()));
		vendorsteamspage.closeNewTab(mainWindowHandle);

		vendorsteamspage.clickGuestsLinkForVendorTeam(data.getVendorTeam());
		Assert.assertFalse(vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee()));
		vendorsteamspage.selectTeamGuestEmployees(data.getVendorTeamEmployee());
		vendorsteamspage.clickAddTeamGuestEmployeesButton();
		vendorsteamspage.clickUpdateTeamGuestEmployeesButton();
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee()));
		vendorsteamspage.closeNewTab(mainWindowHandle);

		vendorsteamspage.clickGuestsLinkForVendorTeam(data.getVendorTeam());
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee()));
		vendorsteamspage.deleteTeamGuestEmployee(data.getVendorTeamEmployee());
		vendorsteamspage.closeNewTab(mainWindowHandle);

		vendorsteamspage.clickGuestsLinkForVendorTeam(data.getVendorTeam());
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee()));
		vendorsteamspage.deleteTeamGuestEmployee(data.getVendorTeamEmployee());
		vendorsteamspage.clickUpdateTeamGuestEmployeesButton();
		vendorsteamspage.closeNewTab(mainWindowHandle);

		vendorsteamspage.clickGuestsLinkForVendorTeam(data.getVendorTeam());
		Assert.assertFalse(vendorsteamspage.isTeamGuestEmployeesExists(data.getVendorTeamEmployee()));
		vendorsteamspage.closeNewTab(mainWindowHandle);
	}
}
