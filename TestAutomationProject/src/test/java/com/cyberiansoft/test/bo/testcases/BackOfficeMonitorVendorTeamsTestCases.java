package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.MonitorWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewVendorTeamDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.VendorsTeamsWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BackOfficeMonitorVendorTeamsTestCases extends BaseTestCase {

	@Test(testName = "Test Case 15294:Monitor - Vendor/Teams: Search", description = "Monitor - Vendor/Teams: Search")
	public void testMonitorVendorsTeamsSearch() {

		final String vendorteam = "Test Vendor Team";
		final String timezone = "Pacific Standard Time";
		final String deflocation = "Default Location";
		final String vendortemtype = "Vendor";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		
		vendorsteamspage.verifyVendorsTeamsTableColumnsAreVisible();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.selectSearchType(vendortemtype);
		vendorsteamspage.selectSearchTimeZone(timezone);
		
		vendorsteamspage.clickFindButton();
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(vendorsteamspage.getVendorsTeamsTableRowCount()));
		Assert.assertTrue(vendorsteamspage.isActiveVendorTeamPresent(vendorteam));
		Assert.assertEquals(deflocation, vendorsteamspage.getTableVendorTeamLocation(vendorteam));
		Assert.assertEquals(vendortemtype, vendorsteamspage.getTableVendorTeamType(vendorteam));	
	}
	
	@Test(testName = "Test Case 26712:Monitor - Vendor/Team: Add", description = "Monitor - Vendor/Team: Add")
	public void testMonitorVendorTeamAdd() throws Exception {
		
		final String vendorteam = "New Test Vendor Team";
		final String timezone = "Pacific Standard Time";
		final String vendordesc = "Test decription";
		final String vendoraccid = "1234567";
		final String vendorarea = "QA Area";
		final String vendortimesheettype = "Day Type";		
		final String deflocation = "Default Location";
		final String vendortemtype = "Vendor";
		final String additionallocation = "Best Location";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		if (vendorsteamspage.isActiveVendorTeamPresent(vendorteam)) {
			vendorsteamspage.archiveVendorTeam(vendorteam);
		}
		
		NewVendorTeamDialogWebPage newvendordialog = vendorsteamspage.clickAddVendorTeamButton();
		
		newvendordialog.setNewVendorTeamName(vendorteam);
		newvendordialog.selectNewVendorTeamTimezone(timezone);
		newvendordialog.setNewVendorTeamDescription(vendordesc);
		newvendordialog.setNewVendorTeamAccountingID(vendoraccid);
		newvendordialog.selectNewVendorTeamArea(vendorarea);
		newvendordialog.selectNewVendorTeamTimesheetType(vendortimesheettype);
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(deflocation);
		newvendordialog.selectNewVendorTeamType(vendortemtype);		
		newvendordialog.selectNewVendorTeamAdditionalRepairLocations(additionallocation);
		newvendordialog.clickCancelButton();
	}
	
	@Test(testName = "Test Case 28325:Monitor - Vendor/Teams: Edit", description = "Monitor - Vendor/Teams: Edit")
	public void testMonitorVendorTeamsEdit() throws Exception {
		
		final String vendorteam = "TestNewTeamInternal";
		final String timezone = "Pacific Standard Time";
		final String vendordesc = "Test decription";
		final String vendortimesheettype = "ClockIn / ClockOut";		
		final String deflocation = "My Location 2";
		final String additionallocation = "ALM - Recon Facility";
		final String vendortemtype = "Internal";
		
		final String vendorteamed = "TestNewTeamInternal2";
		final String timezoneed = "US Mountain Standard Time";
		final String vendortimesheettypeed = "(none)";
		final String vendortemtypeed = "Vendor";
		final String vendorcompany = "Test";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		if (vendorsteamspage.isActiveVendorTeamPresent(vendorteam)) {
			vendorsteamspage.archiveVendorTeam(vendorteam);
		}
		
		if (vendorsteamspage.isActiveVendorTeamPresent(vendorteamed)) {
			vendorsteamspage.archiveVendorTeam(vendorteamed);
		}
		vendorsteamspage.createNewVendorTeam(vendorteam, timezone, vendordesc, vendortimesheettype, deflocation, additionallocation);
				
		NewVendorTeamDialogWebPage newvendordialog = vendorsteamspage.clickEditVendorTeam(vendorteam);
		newvendordialog.setNewVendorTeamName(vendorteamed);
		newvendordialog.selectNewVendorTeamTimezone(timezoneed);
		newvendordialog.selectNewVendorTeamTimesheetType(vendortimesheettypeed);
		newvendordialog.selectNewVendorTeamType(vendortemtypeed);	
		newvendordialog.setNewVendorCompany(vendorcompany);	
		newvendordialog.clickOKButton();
		
		newvendordialog = vendorsteamspage.clickEditVendorTeam(vendorteamed);
		Assert.assertEquals(vendorteamed, newvendordialog.getNewVendorTeamName());
		Assert.assertEquals(timezoneed, newvendordialog.getNewVendorTeamTimezone());
		Assert.assertEquals(vendortimesheettypeed, newvendordialog.getNewVendorTeamTimesheetType());
		Assert.assertEquals(vendortemtypeed, newvendordialog.getNewVendorTeamType());
		Assert.assertEquals(vendorcompany, newvendordialog.getNewVendorCompany());
		
		newvendordialog.setNewVendorTeamName(vendorteam);
		newvendordialog.selectNewVendorTeamTimezone(timezone);
		newvendordialog.selectNewVendorTeamTimesheetType(vendortimesheettype);
		newvendordialog.selectNewVendorTeamType(vendortemtype);	
		newvendordialog.clickOKButton();
		
		newvendordialog = vendorsteamspage.clickEditVendorTeam(vendorteam);
		Assert.assertEquals(vendorteam, newvendordialog.getNewVendorTeamName());
		Assert.assertEquals(timezone, newvendordialog.getNewVendorTeamTimezone());
		Assert.assertEquals(vendortimesheettype, newvendordialog.getNewVendorTeamTimesheetType());
		Assert.assertEquals(vendortemtype, newvendordialog.getNewVendorTeamType());
		newvendordialog.clickCancelButton();
		vendorsteamspage.archiveVendorTeam(vendorteam);		
	}

	@Test(testName = "Test Case 26710:Monitor - Vendor/Teams: Archive", description = "Monitor - Vendor/Teams: Archive")
	public void testMonitorVendorTeamsArchive() {
		
		final String vendorTeam = "ArchiveNewTeamInternal";
		final String timeZone = "Pacific Standard Time";
		final String vendorDescription = "Test decription";
		final String vendorTimesheetType = "ClockIn / ClockOut";
		final String defLocation = "My Location 2";
		final String additionalLocation = "ALM - Recon Facility";
		
		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorPage = backOfficeHeader.clickMonitorLink();

        VendorsTeamsWebPage vendorsTeamsPage = monitorPage.clickVendorsTeamsLink();
        vendorsTeamsPage.makeSearchPanelVisible();
        vendorsTeamsPage.setSearchTeamLocation(vendorTeam);
        vendorsTeamsPage.clickFindButton();

        vendorsTeamsPage.verifyThatActiveVendorTeamExists(vendorTeam, timeZone, vendorDescription, vendorTimesheetType,
                defLocation, additionalLocation);
        int activeVendorTeamsCountBefore = vendorsTeamsPage.getActiveVendorTeamsCountOnPage(vendorTeam);

        vendorsTeamsPage.archiveVendorTeam(vendorTeam);
        Assert.assertEquals(activeVendorTeamsCountBefore - 1, vendorsTeamsPage.getActiveVendorTeamsCountOnPage(vendorTeam));
        vendorsTeamsPage.clickArchivedTab();
        int archivedVendorTeamsCountBefore = vendorsTeamsPage.getArchivedVendorTeamsCount(vendorTeam);
        vendorsTeamsPage.restoreVendorTeam(vendorTeam);
        Assert.assertEquals(archivedVendorTeamsCountBefore - 1, vendorsTeamsPage
                        .getArchivedVendorTeamsCount(vendorTeam), "The archived vendors team is still present!");

        vendorsTeamsPage.clickActiveTab();
        Assert.assertEquals(activeVendorTeamsCountBefore, vendorsTeamsPage.getActiveVendorTeamsCountOnPage(vendorTeam),
                "The archived vendors team is still present!");
	}

	@Test(testName = "Test Case 26714:Monitor - Vendor/Teams: Audit Log", description = "Monitor - Vendor/Teams: Audit Log")
	public void testMonitorVendorTeamsAuditLog() {
		
		final String vendorTeam = "ZalexTestLoc";
		final String[] auditLogsMessages = {"Team \"" + vendorTeam + "\" is restored", "Team \"" + vendorTeam + "\" is deleted"};

		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		MonitorWebPage monitorPage = backofficeHeader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsTeamsPage = monitorPage.clickVendorsTeamsLink();
		vendorsTeamsPage.makeSearchPanelVisible();
		vendorsTeamsPage.setSearchTeamLocation(vendorTeam);
		vendorsTeamsPage.clickFindButton();

        vendorsTeamsPage.verifyThatActiveVendorTeamExists(vendorTeam);

		vendorsTeamsPage.clickActiveTab();
		String mainWindowHandle = webdriver.getWindowHandle();
		vendorsTeamsPage.clickAuditLogButtonForVendorTeam(vendorTeam);
		int auditLogsRowsCount = vendorsTeamsPage.getVendorsTeamsAuditLogTableRows().size();
		vendorsTeamsPage.closeNewTab(mainWindowHandle);
		
		vendorsTeamsPage.archiveVendorTeam(vendorTeam);
		vendorsTeamsPage.clickArchivedTab();
        int archivedVendorTeamsCountBefore = vendorsTeamsPage.getArchivedVendorTeamsCount(vendorTeam);
        vendorsTeamsPage.restoreVendorTeam(vendorTeam);
        Assert.assertEquals(archivedVendorTeamsCountBefore - 1, vendorsTeamsPage
                .getArchivedVendorTeamsCount(vendorTeam), "The archived vendors team is still present!");

		vendorsTeamsPage.clickActiveTab();
		vendorsTeamsPage.clickAuditLogButtonForVendorTeam(vendorTeam);
		List<WebElement> auditLogTableRows = vendorsTeamsPage.getVendorsTeamsAuditLogTableRows();
		for (int i = 0; i < auditLogsMessages.length; i++) {
			int columnIndex = i + 1;
			Assert.assertEquals(auditLogsMessages[i],  vendorsTeamsPage
                    .getAuditLogVendorsTeamsTable()
                    .findElement(By.xpath(".//tbody/tr[" + columnIndex + "]/td[3]")).getText());
		}
		vendorsTeamsPage.closeNewTab(mainWindowHandle);
		Assert.assertEquals(auditLogsRowsCount + 2, auditLogTableRows.size());
	}
	
	@Test(testName = "Test Case 31420:Monitor - Vendor/Team: Guests Edit", description = "Monitor - Vendor/Teams: Guests Edit")
	public void testMonitorVendorTeamsGuestsEdit() {
		
		final String vendorteam = "TestTeamInternal";
		final String vendorteamemployee = "Alex Maximov";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		vendorsteamspage.clickGuestsLinkForVendorTeam(vendorteam);
		
		if(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee)) {
			vendorsteamspage.deleteTeamGuestEmployee(vendorteamemployee);
		}
		
		vendorsteamspage.selectTeamGuestEmployees(vendorteamemployee);
		vendorsteamspage.clickAddTeamGuestEmployeesButton();
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee));
		vendorsteamspage.closeNewTab(mainWindowHandle);
		
		vendorsteamspage.clickGuestsLinkForVendorTeam(vendorteam);
		Assert.assertFalse(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee));
		vendorsteamspage.selectTeamGuestEmployees(vendorteamemployee);
		vendorsteamspage.clickAddTeamGuestEmployeesButton();
		vendorsteamspage.clickUpdateTeamGuestEmployeesButton();
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee));
		vendorsteamspage.closeNewTab(mainWindowHandle);
		
		vendorsteamspage.clickGuestsLinkForVendorTeam(vendorteam);
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee));
		vendorsteamspage.deleteTeamGuestEmployee(vendorteamemployee);
		vendorsteamspage.closeNewTab(mainWindowHandle);
		
		vendorsteamspage.clickGuestsLinkForVendorTeam(vendorteam);
		Assert.assertTrue(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee));
		vendorsteamspage.deleteTeamGuestEmployee(vendorteamemployee);
		vendorsteamspage.clickUpdateTeamGuestEmployeesButton();
		vendorsteamspage.closeNewTab(mainWindowHandle);
		
		vendorsteamspage.clickGuestsLinkForVendorTeam(vendorteam);
		Assert.assertFalse(vendorsteamspage.isTeamGuestEmployeesExists(vendorteamemployee));
		vendorsteamspage.closeNewTab(mainWindowHandle);
	}
}
