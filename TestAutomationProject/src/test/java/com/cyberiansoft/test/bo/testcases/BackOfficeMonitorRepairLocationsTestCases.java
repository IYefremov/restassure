package com.cyberiansoft.test.bo.testcases;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.MonitorWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewRepairLocationDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewTeamsDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewVendorTeamDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationClientsTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationDepartmentsTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationManagersTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationPhaseServicesTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationPhasesTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationUserSettingsTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.RepairLocationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TeamsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.VendorsTeamsWebPage;

public class BackOfficeMonitorRepairLocationsTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		try{
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
		}catch(Exception e){
			BackOfficeLogout();
			webdriverGotoWebPage(backofficeurl);
			BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
					BackOfficeLoginWebPage.class);
			loginpage.UserLogin(userName, userPassword);
			Thread.sleep(2000);
		}
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		backofficeheader.clickLogout();
		Thread.sleep(3000);
	}
	
	@Test(testName = "Test Case 15527:Monitor - Repair Locations: Search", description = "Monitor - Repair Locations: Search")
	public void testMonitorRepairLocationsSearch() throws Exception {

		final String locationname = "Time_Reports_01";
		final String locationstatus = "Active";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		
		repairlocationspage.verifyRRepairLocationsTableColumnsAreVisible();
		repairlocationspage.makeSearchPanelVisible().selectSearchStatus(locationstatus).setSearchLocation(locationname).clickFindButton();
		
		Assert.assertTrue(repairlocationspage.isRepairLocationExists(locationname));
		
	}
	
@Test(testName = "Test Case 26707:Monitor - Repair Locations: Add", description = "Monitor - Repair Locations: Add")
	public void testMonitorRepairLocationsAdd() throws Exception {
		
		final String repairlocationname = "test_loc";
		final String repairlocationstatus = "Active";
		final String repairlocationapproxrepaittime = "3.4";
		final String repairlocationworkday1 = "Monday";
		final String repairlocationstarttime1 = "8:00 AM";
		final String repairlocationendtime1 = "5:00 PM";
		final String repairlocationworkday2 = "Sunday";
		final String repairlocationstarttime2 = "9:00 AM";
		final String repairlocationendtime2 = "13:00 PM";
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.setNewRepairLocationName(repairlocationname).selectNewRepairLocationStatus(repairlocationstatus)
			.setNewRepairLocationApproxRepairTime(repairlocationapproxrepaittime)
			.setNewRepairLocationWorkingHours(repairlocationworkday1, repairlocationstarttime1, repairlocationendtime1)
			.setNewRepairLocationWorkingHours(repairlocationworkday2, repairlocationstarttime2, repairlocationendtime2)
			.selectPhaseEnforcementOption().selectAddressInfoTab().selectWorkingHoursTab().clickOKButton();
		
		repairlocationspage.deleteRepairLocationAndCancelDeleting(repairlocationname);
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 28180:Monitor: Repair Location - in Team default Repair location edit", description = "Monitor: Repair Location - in Team default Repair location edit")
	public void testMonitorRepairLocationInTeamDefaultRepairLocationEdit() throws Exception {
		
		final String repairlocationname = "test_loc";
		final String repairlocationstatus = "Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		final String vendorteam = "TestTeamInternal";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		
		monitorpage = backofficeheader.clickMonitorLink();
		VendorsTeamsWebPage vendorsteamspage = monitorpage.clickVendorsTeamsLink();
		vendorsteamspage.makeSearchPanelVisible().setSearchTeamLocation(vendorteam).clickFindButton();
		NewVendorTeamDialogWebPage newvendordialog = vendorsteamspage.clickEditVendorTeam(vendorteam);
		newvendordialog.selectNewVendorTeamDefaultRepairLocation(repairlocationname);
		newvendordialog.clickCancelButton();
		
		monitorpage = backofficeheader.clickMonitorLink();
		repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 29848:Monitor - Repair Locations: Department Edit", description = "Monitor - Repair Locations: Department Edit")
	public void testMonitorRepairLocationsDepartmentEdit() throws Exception {
		
		final String repairlocationname = "TestLoc";
		final String repairlocationstatus = "Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		final String repairlocationdepartmentdef = "Default";
		final String repairlocationdepartment = "mydepartment";
		final String repairlocationdepartmentdesc = "Test description";
		final String repairlocationdepartmentnew = "mydepartment new";
		final String repairlocationdepartmentdescnew = "Test description new";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationDepartmentsTabWebPage repairlocationdepartmentstab = repairlocationspage.clickRepairLocationDepartmentsLink(repairlocationname);
		repairlocationdepartmentstab.clickAddDepartmentButton();
		repairlocationdepartmentstab.setNewRepairLocationDepartmentName(repairlocationdepartment);
		repairlocationdepartmentstab.setNewRepairLocationDepartmentDescription(repairlocationdepartmentdesc);
		repairlocationdepartmentstab.clickNewRepairLocationDepartmentOKButton();
		repairlocationdepartmentstab.setRepairLocationDepartmentAsdefault(repairlocationdepartment);
		
		repairlocationdepartmentstab.clickEditRepairLocationDepartment(repairlocationdepartment);
		Assert.assertEquals(repairlocationdepartmentdesc, repairlocationdepartmentstab.getNewRepairLocationDepartmentDescription());
		repairlocationdepartmentstab.setNewRepairLocationDepartmentName(repairlocationdepartmentnew);
		repairlocationdepartmentstab.setNewRepairLocationDepartmentDescription(repairlocationdepartmentdescnew);
		repairlocationdepartmentstab.clickNewRepairLocationDepartmentOKButton();
		
		repairlocationdepartmentstab.clickEditRepairLocationDepartment(repairlocationdepartmentnew);
		Assert.assertEquals(repairlocationdepartmentdescnew, repairlocationdepartmentstab.getNewRepairLocationDepartmentDescription());
		repairlocationdepartmentstab.clickNewRepairLocationDepartmentCancelButton();
		
		repairlocationdepartmentstab.setRepairLocationDepartmentAsdefault(repairlocationdepartmentdef);
		repairlocationdepartmentstab.deleteRepairLocationDepartment(repairlocationdepartmentnew);
		repairlocationdepartmentstab.closeNewTab(mainWindowHandle);
		
		repairlocationspage.deleteRepairLocation(repairlocationname);		
	}
	
	@Test(testName = "Test Case 29849:Monitor - Repair Locations: Phases Edit", description = "Monitor - Repair Locations: Phases Edit")
	public void testMonitorRepairLocationsPhasesEdit() throws Exception {
		
		final String repairlocationname = "TestLoc";
		final String repairlocationstatus = "Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		
		final String repairlocationphase = "Test Phase";
		final String repairlocationphasedesc = "Test Description";
		final String repairlocationphasetype = "In Progress";
		final String repairlocationphasecheckouttype = "Optional";
		final String approxtranstime = "2.0";
		final String approxrepairtime = "8.0";
		
		
		final String repairlocationphasenew = "Test Phase New";
		final String repairlocationphasedescnew = "Test Description New";
		final String repairlocationphasetypenew = "On Hold";
		final String repairlocationphasecheckouttypenew = "Required";
		final String approxtranstimenew = "3.0";
		final String approxrepairtimenew = "5.0";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		String mainWindowHandle = webdriver.getWindowHandle();
		
		RepairLocationPhasesTabWebPage repairlocationphasestab = repairlocationspage.clickRepairLocationPhasesLink(repairlocationname);
		repairlocationphasestab.clickAddPhasetButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(repairlocationphase);
		repairlocationphasestab.setNewRepairLocationPhaseDescription(repairlocationphasedesc);
		repairlocationphasestab.selectNewRepairLocationPhaseType(repairlocationphasetype);
		repairlocationphasestab.selectNewRepairLocationPhaseCheckOutType(repairlocationphasecheckouttype);
		repairlocationphasestab.setNewRepairLocationPhaseApproxTransitionTime(approxtranstime);
		repairlocationphasestab.setNewRepairLocationPhaseApproxRepairTime(approxrepairtime);
		repairlocationphasestab.selectDoNotTrackIndividualServiceStatuses();
		repairlocationphasestab.selectStartServiceRequired();
		repairlocationphasestab.selectQCRequired();
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		
		repairlocationphasestab.clickEditRepairLocationPhase(repairlocationphase);
		Assert.assertEquals(repairlocationphasedesc, repairlocationphasestab.getNewRepairLocationPhaseDescription());
		Assert.assertEquals(repairlocationphasetype, repairlocationphasestab.getNewRepairLocationPhaseType());
		Assert.assertEquals(repairlocationphasecheckouttype, repairlocationphasestab.getNewRepairLocationPhaseCheckOutType());
		Assert.assertEquals(approxtranstime, repairlocationphasestab.getNewRepairLocationPhaseApproxTransitionTime());
		Assert.assertEquals(approxrepairtime, repairlocationphasestab.getNewRepairLocationPhaseApproxRepairTime());
		Assert.assertTrue(repairlocationphasestab.isDoNotTrackIndividualServiceStatusesSelected());
		Assert.assertTrue(repairlocationphasestab.isStartServiceRequiredSelected());
		Assert.assertTrue(repairlocationphasestab.isQCRequiredSelected());
		
		repairlocationphasestab.setNewRepairLocationPhaseName(repairlocationphasenew);
		repairlocationphasestab.setNewRepairLocationPhaseDescription(repairlocationphasedescnew);
		repairlocationphasestab.selectNewRepairLocationPhaseType(repairlocationphasetypenew);
		repairlocationphasestab.selectNewRepairLocationPhaseCheckOutType(repairlocationphasecheckouttypenew);
		repairlocationphasestab.setNewRepairLocationPhaseApproxTransitionTime(approxtranstimenew);
		repairlocationphasestab.setNewRepairLocationPhaseApproxRepairTime(approxrepairtimenew);
		repairlocationphasestab.unselectDoNotTrackIndividualServiceStatuses();
		repairlocationphasestab.unselectStartServiceRequired();
		repairlocationphasestab.unselectQCRequired();
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		
		repairlocationphasestab.clickEditRepairLocationPhase(repairlocationphasenew);
		Assert.assertEquals(repairlocationphasedescnew, repairlocationphasestab.getNewRepairLocationPhaseDescription());
		Assert.assertEquals(repairlocationphasetypenew, repairlocationphasestab.getNewRepairLocationPhaseType());
		Assert.assertEquals(repairlocationphasecheckouttypenew, repairlocationphasestab.getNewRepairLocationPhaseCheckOutType());
		Assert.assertEquals(approxtranstimenew, repairlocationphasestab.getNewRepairLocationPhaseApproxTransitionTime());
		Assert.assertEquals(approxrepairtimenew, repairlocationphasestab.getNewRepairLocationPhaseApproxRepairTime());
		Assert.assertFalse(repairlocationphasestab.isDoNotTrackIndividualServiceStatusesSelected());
		Assert.assertFalse(repairlocationphasestab.isStartServiceRequiredSelected());
		Assert.assertFalse(repairlocationphasestab.isQCRequiredSelected());
		repairlocationphasestab.clickNewRepairLocationPhaseCancelButton();
		
		repairlocationphasestab.deleteRepairLocationPhase(repairlocationphasenew);
		repairlocationphasestab.closeNewTab(mainWindowHandle);
		
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 30729:Monitor - Repair Locations: Clients Edit", description = "Monitor - Repair Locations: Clients Edit")
	public void testMonitorRepairLocationsClientsEdit() throws Exception {
		
		final String repairlocationname = "TestLoc";
		final String repairlocationstatus = "Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		final String repairlocationclient = "005 - Test Company";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationClientsTabWebPage repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(repairlocationname);
		repairlocationclientstab.selectRepairLocationClient(repairlocationclient).clickAddRepairLocationClientButton().clickUpdateClientsButton();
		repairlocationclientstab.closeNewTab(mainWindowHandle);
		
		repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(repairlocationname);
		Assert.assertTrue(repairlocationclientstab.isRepairLocationClientExists(repairlocationclient));
		repairlocationclientstab.deleteRepairLocationClient(repairlocationclient);
		repairlocationclientstab.closeNewTab(mainWindowHandle);
		
		repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(repairlocationname);
		Assert.assertTrue(repairlocationclientstab.isRepairLocationClientExists(repairlocationclient));
		repairlocationclientstab.deleteRepairLocationClient(repairlocationclient);
		repairlocationclientstab.clickUpdateClientsButton();
		repairlocationclientstab.closeNewTab(mainWindowHandle);
		
		repairlocationclientstab = repairlocationspage.clickRepairLocationClientsLink(repairlocationname);
		Assert.assertFalse(repairlocationclientstab.isRepairLocationClientExists(repairlocationclient));
		repairlocationclientstab.closeNewTab(mainWindowHandle);
		
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 30801:Monitor - Repair Locations: Services Edit", description = "Monitor - Repair Locations: Services Edit")
	public void testMonitorRepairLocationsServicesEdit() throws Exception {
		
		final String repairlocationname = "TestLoc";
		final String repairlocationstatus = "Active";		
		
		final String wotype = "02_Old";
		final String phaseclosed = "Closed";
		final String phaseclosedtype = "Completed";
		final String phasestarted = "Started";
		final String phasestartedtype = "In Progress";
		final String repairlocationtomezone = "Pacific Standard Time";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		String mainWindowHandle = webdriver.getWindowHandle();
		RepairLocationPhasesTabWebPage repairlocationphasestab = repairlocationspage.clickRepairLocationPhasesLink(repairlocationname);
		repairlocationphasestab.clickAddPhasetButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(phaseclosed);
		repairlocationphasestab.selectNewRepairLocationPhaseType(phaseclosedtype);
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		
		repairlocationphasestab.clickAddPhasetButton();
		repairlocationphasestab.setNewRepairLocationPhaseName(phasestarted);
		repairlocationphasestab.selectNewRepairLocationPhaseType(phasestartedtype);
		repairlocationphasestab.clickNewRepairLocationPhaseOKButton();
		repairlocationphasestab.closeNewTab(mainWindowHandle);
		
		RepairLocationPhaseServicesTabWebPage repairlocationphaseservicestab = repairlocationspage.clickRepairLocationServicesLink(repairlocationname);
		repairlocationphaseservicestab.selectWOType(wotype);		
		repairlocationphaseservicestab.selectPhase(phaseclosed);
		final int servicescoint = repairlocationphaseservicestab.getPhaseServicesTableRows().size();
		repairlocationphaseservicestab.selectAllServicesInTable();
		Assert.assertEquals(servicescoint, repairlocationphaseservicestab.getNumberOfSelectedServicesInTable());
		repairlocationphaseservicestab.clickAssignToSelectedservicesButton();
		List<WebElement> rows = repairlocationphaseservicestab.getPhaseServicesTableRows();
		for (WebElement row : rows) {
			Assert.assertEquals(phaseclosed, repairlocationphaseservicestab.getTableRowPhaseValue(row));
		}
		final String servicestartedphase = repairlocationphaseservicestab.getTableRowPhaseServiceName(repairlocationphaseservicestab.getPhaseServicesTableRows().get(0));
		repairlocationphaseservicestab.selectServicePhaseValue(servicestartedphase, phasestarted);
		repairlocationphaseservicestab.closeNewTab(mainWindowHandle);
		
		repairlocationphaseservicestab = repairlocationspage.clickRepairLocationServicesLink(repairlocationname);
		repairlocationphaseservicestab.selectWOType(wotype);		
		Assert.assertEquals(servicescoint, repairlocationphaseservicestab.getPhaseServicesTableRows().size());
		Assert.assertEquals(servicescoint-1, repairlocationphaseservicestab.getNumberOfPhaseServicesWithSelectedPhaseValue(phaseclosed).size());
		Assert.assertEquals(1, repairlocationphaseservicestab.getNumberOfPhaseServicesWithSelectedPhaseValue(phasestarted).size());
		repairlocationphaseservicestab.closeNewTab(mainWindowHandle);
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 31404:Monitor - Repair Locations: Managers Edit", description = "Monitor - Repair Locations: Managers Edit")
	public void testMonitorRepairLocationsManagersEdit() throws Exception {
		
		final String repairlocationname = "TestLoc";
		final String repairlocationstatus = "Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		final String _team = "Team 1";
		final String manager = "Marta Moe";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		String mainWindowHandle = webdriver.getWindowHandle();
		
		RepairLocationManagersTabWebPage repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(repairlocationname);
		repairlocationmanagerstab.selectTeam(_team).selectRepairLocationManager(manager).clickAddManagerButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);
		
		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(repairlocationname);
		Assert.assertFalse(repairlocationmanagerstab.isRepairLocationManagerExists(manager));
		repairlocationmanagerstab.selectTeam(_team).selectRepairLocationManager(manager).clickAddManagerButton().clickUpdateManagersButton();		
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);
		
		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(repairlocationname);
		Assert.assertTrue(repairlocationmanagerstab.isRepairLocationManagerExists(manager));
		repairlocationmanagerstab.deleteRepairLocationManager(manager);		
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);
		
		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(repairlocationname);
		Assert.assertTrue(repairlocationmanagerstab.isRepairLocationManagerExists(manager));
		repairlocationmanagerstab.deleteRepairLocationManager(manager);
		repairlocationmanagerstab.clickUpdateManagersButton();
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);
		
		repairlocationmanagerstab = repairlocationspage.clickRepairLocationManagersLink(repairlocationname);
		Assert.assertFalse(repairlocationmanagerstab.isRepairLocationManagerExists(manager));
		repairlocationmanagerstab.closeNewTab(mainWindowHandle);	
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 31405:Monitor - Repair Locations: User Settings Edit", description = "Monitor - Repair Locations: User Settings Edit")
	public void testMonitorRepairLocationsUserSettingsEdit() throws Exception {
		
		final String repairlocationname = "TestLoc";
		final String repairlocationstatus = "Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		final int checkboxesamount = 28;
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		String mainWindowHandle = webdriver.getWindowHandle();
		
		RepairLocationUserSettingsTabWebPage repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(repairlocationname);
		Assert.assertEquals(checkboxesamount, repairlocationusersettingstab.getAllUserSettingsCheckboxes().size());
		repairlocationusersettingstab.checkAllUserSettingsCheckboxes();	
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);
		
		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(repairlocationname);
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesUnchecked();
		repairlocationusersettingstab.checkAllUserSettingsCheckboxes().clickUpdateSettingButton();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);
		
		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(repairlocationname);
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesChecked();
		repairlocationusersettingstab.uncheckAllUserSettingsCheckboxes();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);
		
		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(repairlocationname);
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesChecked();
		repairlocationusersettingstab.uncheckAllUserSettingsCheckboxes().clickUpdateSettingButton();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);
		
		repairlocationusersettingstab = repairlocationspage.clickRepairLocationUserSettingsLink(repairlocationname);
		repairlocationusersettingstab.verifyAllUserSettingsCheckboxesUnchecked();
		repairlocationusersettingstab.closeNewTab(mainWindowHandle);
		
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}
	
	@Test(testName = "Test Case 30726:Monitor - Repair Locations: not active status for default repair location", description = "Monitor - Repair Locations: not active status for default repair location")
	public void testMonitorRepairLocationsNotActiveStatusForDefaultRepairLocation() throws Exception {
		
		final String teamname = "TestTeamNotActiveStatus";
		final String teamdefaultlocation = "Not active Loc";
		
		final String repairlocationname = "TestLocNotActive";
		final String repairlocationstatus = "Active";
		final String repairlocationstatusnotactive = "Not Active";
		final String repairlocationtomezone = "Pacific Standard Time";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairLocationsWebPage repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocationifExists(repairlocationname);
		NewRepairLocationDialogWebPage newrepairlocdialog = repairlocationspage.clickAddRepairLocationButton();
		newrepairlocdialog.createNewRepairLocation(repairlocationname, repairlocationstatus, repairlocationtomezone);
		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		TeamsWebPage teamspage = companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible().setTeamLocationSearchCriteria(teamname).clickFindButton();
		teamspage.deleteTeamIfExists(teamname);
		NewTeamsDialogWebPage newteamsdialog = teamspage.clickAddTeamButton();
		newteamsdialog.setNewTeamName(teamname).selectTeamDefaultRepairLocation(teamdefaultlocation).clickAddTeamOKButton();
		
		monitorpage = backofficeheader.clickMonitorLink();
		repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		NewRepairLocationDialogWebPage newlocationdialog = repairlocationspage.clickEditRepairLocation(repairlocationname);
		newlocationdialog.selectNewRepairLocationStatus(repairlocationstatusnotactive).clickOKButton();
		
		companypage = backofficeheader.clickCompanyLink();
		teamspage = companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible().setTeamLocationSearchCriteria(teamname).clickFindButton();
		newteamsdialog = teamspage.clickEditTeam(teamname);
		newteamsdialog.selectTeamDefaultRepairLocation(teamdefaultlocation).clickAddTeamOKButton();
		teamspage.deleteTeam(teamname);
		
		monitorpage = backofficeheader.clickMonitorLink();
		repairlocationspage = monitorpage.clickRepairLocationsLink();
		repairlocationspage.makeSearchPanelVisible().setSearchLocation(repairlocationname).clickFindButton();
		repairlocationspage.deleteRepairLocation(repairlocationname);
	}

}
