package com.cyberiansoft.test.bo.testcases;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.MonitorWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewVendorTeamDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.VendorsTeamsWebPage;

public class BackOfficeMonitorVendorTeamsTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
		//Thread.sleep(3000);
	}
	
	@Test(testName = "Test Case 15294:Monitor - Vendor/Teams: Search", description = "Monitor - Vendor/Teams: Search")
	public void testMonitorVendorsTeamsSearch() throws Exception {

		final String vendorteam = "Test Vendor Team";
		final String timezone = "Pacific Standard Time";
		final String deflocation = "Default Location";
		final String vendortemtype = "Vendor";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		
		vendorsteamspage.verifyVendorsTeamsTableColumnsAreVisible();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.selectSearchType(vendortemtype);
		vendorsteamspage.selectSearchTimeZone(timezone);
		
		vendorsteamspage.clickFindButton();
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(vendorsteamspage.getVendorsTeamsTableRowCount()));
		Assert.assertTrue(vendorsteamspage.isVendorTeamExists(vendorteam));
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
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		if (vendorsteamspage.isVendorTeamExists(vendorteam)) {
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
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		if (vendorsteamspage.isVendorTeamExists(vendorteam)) {
			vendorsteamspage.archiveVendorTeam(vendorteam);
		}
		
		if (vendorsteamspage.isVendorTeamExists(vendorteamed)) {
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
	public void testMonitorVendorTeamsArchive() throws Exception {
		
		final String vendorteam = "ArchiveNewTeamInternal";
		final String timezone = "Pacific Standard Time";
		final String vendordesc = "Test decription";
		final String vendortimesheettype = "ClockIn / ClockOut";		
		final String deflocation = "My Location 2";
		final String additionallocation = "ALM - Recon Facility";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		if (!vendorsteamspage.isVendorTeamExists(vendorteam)) {
			vendorsteamspage.createNewVendorTeam(vendorteam, timezone, vendordesc, vendortimesheettype, deflocation, additionallocation);
		}		
		
		for (int i = 0; i < 3; i ++) {
			vendorsteamspage.archiveVendorTeam(vendorteam);			
			vendorsteamspage.clickArchivedTab();
			//TODO no archive
			Assert.assertTrue(vendorsteamspage.isArchivedVendorTeamExists(vendorteam));
			vendorsteamspage.restoreVendorTeam(vendorteam);
			//fix deletion
			Assert.assertFalse(vendorsteamspage.isArchivedVendorTeamExists(vendorteam));
			vendorsteamspage.clickActiveTab();
		
			Assert.assertTrue(vendorsteamspage.isVendorTeamExists(vendorteam));
		}
	}
	
	@Test(testName = "Test Case 26714:Monitor - Vendor/Teams: Audit Log", description = "Monitor - Vendor/Teams: Audit Log")
	public void testMonitorVendorTeamsAuditLog() throws Exception {
		
		final String vendorteam = "ZalexTestLoc";
		final String[] auditlogsmessages = {"Team \"" + vendorteam + "\" is restored", "Team \"" + vendorteam + "\" is deleted"};
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible();
		vendorsteamspage.setSearchTeamLocation(vendorteam);
		vendorsteamspage.clickFindButton();
		//TODO no archive
		if (!vendorsteamspage.isVendorTeamExists(vendorteam)) {
			vendorsteamspage.clickArchivedTab();
			vendorsteamspage.restoreVendorTeam(vendorteam);
		}
		vendorsteamspage.clickActiveTab();
		String mainWindowHandle = webdriver.getWindowHandle();
		vendorsteamspage.clickAuditLogButtonForVendorTeam(vendorteam);
		int auditlogsrowscount = vendorsteamspage.getVendorsTeamsAuditLogTableRows().size();
		vendorsteamspage.closeNewTab(mainWindowHandle);
		
		vendorsteamspage.archiveVendorTeam(vendorteam);
		vendorsteamspage.clickArchivedTab();
		Assert.assertTrue(vendorsteamspage.isArchivedVendorTeamExists(vendorteam));
		vendorsteamspage.restoreVendorTeam(vendorteam);
		Assert.assertFalse(vendorsteamspage.isArchivedVendorTeamExists(vendorteam));
		
		vendorsteamspage.clickActiveTab();
		vendorsteamspage.clickAuditLogButtonForVendorTeam(vendorteam);
		List<WebElement> auditlogtablerows = vendorsteamspage.getVendorsTeamsAuditLogTableRows();
		for (int i = 0; i < auditlogsmessages.length; i++) {
			int columnindex = i +1;
			Assert.assertEquals(auditlogsmessages[i],  vendorsteamspage.getAuditLogVendorsTeamsTable().findElement(By.xpath(".//tbody/tr[" + columnindex + "]/td[3]")).getText());
		}
		vendorsteamspage.closeNewTab(mainWindowHandle);
		Assert.assertEquals(auditlogsrowscount+2, auditlogtablerows.size());
		
	}
	
	@Test(testName = "Test Case 31420:Monitor - Vendor/Team: Guests Edit", description = "Monitor - Vendor/Teams: Guests Edit")
	public void testMonitorVendorTeamsGuestsEdit() throws Exception {
		
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
