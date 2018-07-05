package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BackOfficeMonitorTestCases extends BaseTestCase {

	@Test(testName = "Test Case 15266:Monitor-Repair Order: Search", description = "Monitor-Repair Order: Search")
	public void testMonitorRepairOrderSearch() throws Exception {

		final String wonumber = "O-000-149273";
		final String searchLocation = "Default Location";
		final String searchTimeFrame = "Last Year";
		final String company = "000 15.11 Companey";
		final String VIN = "1GCPKSE70CF109506";
		final String page1 = "1";
		final String page2 = "2";
		final String page999 = "999";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();

		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.repairOrdersTableIsVisible();
		repairorderspage.verifyRepairOrdersTableColumnsAreVisible();

		repairorderspage.selectSearchLocation(searchLocation);
		repairorderspage.selectSearchTimeframe(searchTimeFrame);
		repairorderspage.clickFindButton();
		Assert.assertEquals(page1, repairorderspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(page1, repairorderspage.getGoToPageFieldValue());

		String lastpagenumber = repairorderspage.getLastPageNumber();
		repairorderspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, repairorderspage.getGoToPageFieldValue());

		repairorderspage.clickGoToFirstPage();
		Assert.assertEquals(page1, repairorderspage.getGoToPageFieldValue());

		repairorderspage.clickGoToNextPage();
		Assert.assertEquals(page2, repairorderspage.getGoToPageFieldValue());

		repairorderspage.clickGoToPreviousPage();
		Assert.assertEquals(page1, repairorderspage.getGoToPageFieldValue());

		repairorderspage.setPageSize(page1);
		Assert.assertEquals(1, repairorderspage.getRepairOrdersTableRowCount());
		//String numberofrows = repairorderspage.getLastPageNumber();

		repairorderspage.setPageSize(page999);
		Assert.assertEquals(50, repairorderspage.getRepairOrdersTableRowCount());

		repairorderspage.selectSearchLocation(searchLocation);
		Thread.sleep(1000);
		repairorderspage.selectSearchCustomer(company);
		repairorderspage.setSearchVIN(VIN);
		repairorderspage.setSearchWoNumber(wonumber);
		repairorderspage.clickFindButton();
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(repairorderspage.getRepairOrdersTableRowCount()));
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(wonumber));
		repairorderspage.verifyTableCustomerAndVinColumnValuesAreVisible(company, VIN);
	}

	@Test(testName = "Test Case 15724:Monitor - Vendor Orders: Search", description = "Monitor - Vendor Orders: Search")
	public void testMonitorVendorOrdersSearch() {

		final String wonumber = "O-000-149273";
		final String company = "000 15.11 Companey";
		final String VIN = "1GCPKSE70CF109506";
		final String vendor = "Test Team";
		final String searchLocation = "Default Location";

		final String serviceStatus = "All Services";

        final String page1 = "1";
        final String page2 = "2";
        final String page999 = "999";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);

		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();

		VendorOrdersWebPage vendororderspage = monitorpage.clickVendorOrdersLink();
		vendororderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		vendororderspage.selectSearchServiceStatus(serviceStatus);
		vendororderspage.clickFindButton();
		vendororderspage.repairOrdersTableIsVisible();
		vendororderspage.verifyVendorOrdersTableColumnsAreVisible();
		Assert.assertEquals(page1, vendororderspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(page1, vendororderspage.getGoToPageFieldValue());

		String lastpagenumber = vendororderspage.getLastPageNumber();
		vendororderspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, vendororderspage.getGoToPageFieldValue());

		vendororderspage.clickGoToFirstPage();
		Assert.assertEquals(page1, vendororderspage.getGoToPageFieldValue());

		vendororderspage.clickGoToNextPage();
		Assert.assertEquals(page2, vendororderspage.getGoToPageFieldValue());

		vendororderspage.clickGoToPreviousPage();
		Assert.assertEquals(page1, vendororderspage.getGoToPageFieldValue());

		vendororderspage.setPageSize(page1);
		Assert.assertEquals(1, vendororderspage.getRepairOrdersTableRowCount());

		vendororderspage.setPageSize(page999);
		Assert.assertEquals(50, vendororderspage.getRepairOrdersTableRowCount());
		
		vendororderspage.selectSearchLocation(searchLocation);
		vendororderspage.selectSearchVendor(vendor);
		vendororderspage.setSearchVIN(VIN); 
		vendororderspage.selectSearchCustomer(company);
		vendororderspage.setSearchWorkorderNumber(wonumber);
		
		vendororderspage.clickFindButton();
		Assert.assertEquals(1, vendororderspage.getRepairOrdersTableRowCount());
		Assert.assertEquals(vendor, vendororderspage.getTeamVendorForVendorOrder(wonumber));
		Assert.assertEquals(company, vendororderspage.getCustomerForVendorOrder(wonumber));
		
		vendororderspage.vendorOrderExists(wonumber);
	}

	@Test(testName = "Test Case 15726:Monitor- Reports - Average Repair Time Report",
            description = "Monitor- Reports - Average Repair Time Report")
	public void testMonitorReportsAverageRepairTimeReport() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
        final String searchLocation = "VD_Location";
        final String searchWoType = "VD_WOT_TEST";
        final String fromDate = "4/30/2017";
        final String toDate = "5/7/2018";

		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		AverageRepairTimeReportWebPage averagerepairtimereportpage = monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		Assert.assertTrue(averagerepairtimereportpage.searchPanelIsExpanded());
		averagerepairtimereportpage.selectSearchLocation(searchLocation);
		averagerepairtimereportpage.selectSearchWOType(searchWoType);
		averagerepairtimereportpage.setSearchFromDate(fromDate);
		averagerepairtimereportpage.setSearchToDate(toDate);
		averagerepairtimereportpage.clickFindButton();
		averagerepairtimereportpage.verifySearchResults(searchLocation, searchWoType);
	}

	@Test(testName = "Test Case 15727:Monitor- Reports - Repair Location Time Tracking",
            description = "Monitor- Reports - Repair Location Time Tracking")
	public void testMonitorReportsRepairLocationTimeTracking() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
        final String searchLocation = "VD_Location";
        final String fromDate = "4/30/2017";
        final String toDate = "5/7/2018";
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		RepairLocationTimeTrackingWebPage repairlocationtimetrackingpage = monitorpage.clickVehicleTimeTrackingLink();
		
		repairlocationtimetrackingpage.makeSearchPanelVisible();
		Assert.assertTrue(repairlocationtimetrackingpage.searchPanelIsExpanded());
		repairlocationtimetrackingpage.selectSearchLocation(searchLocation);
		repairlocationtimetrackingpage.setSearchFromDate(fromDate);
		repairlocationtimetrackingpage.setSearchToDate(toDate);

		repairlocationtimetrackingpage.clickFindButton();
		Assert.assertTrue(repairlocationtimetrackingpage.isTableIsDisplayed(), "The table is not displayed.");
	}

	@Test(testName = "Test Case 15728:Monitor- Reports - Trending Report", description = "Monitor- Reports - Trending Report")
	public void testMonitorReportsTrendingReport() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);

        final String searchLocation = "VD_Location";
        final String searchWoType = "VD_WOT_TEST";
        final String fromMonth = "Apr";
        final String toMonth = "May";
        final String fromYear = "2017";
        final String toYear = "2018";

		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		
		TrendingReportWebPage trendingreportpage = monitorpage.clickCycleTimeTrendingLink();
		
		Assert.assertTrue(trendingreportpage.searchPanelIsExpanded());
		trendingreportpage.selectSearchLocation(searchLocation);
		trendingreportpage.selectSearchWOType(searchWoType);
		trendingreportpage.setSearchFromDate(fromMonth, fromYear);
		trendingreportpage.setSearchToDate(toMonth, toYear);
		
		trendingreportpage.clickFindButton();
		Assert.assertTrue(trendingreportpage.areSearchResultsDisplayed(searchLocation, searchWoType),
                "The search results are not displayed.");
	}

	@Test(description = "Test Case 15948:Monitor-Repair Order: Full Display Version")
	public void testMonitorRepairOrderFullDisplayVersion() {

		final String searchLocation = "Default Location";
		final String searchTimeFrame = "Last Year";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.searchPanelIsExpanded();
		repairorderspage.searchPanelIsVisible();
		repairorderspage.selectSearchLocation(searchLocation);
		repairorderspage.selectSearchTimeframe(searchTimeFrame);
		repairorderspage.clickFindButton();
		repairorderspage.openFullDisplayWOMonitorAndVerifyContent();
	}
	
	@Test(testName = "Test Case 28379:Monitor - Verify \"On Hold\" Reason at RO", description = "Monitor - Verify \"On Hold\" Reason at RO")
	public void testMonitorVerifyOnHoldReasonAtRO() {
		
		final String orderStatus = "On Hold";
		final String orderStatusReason = "testreason";
		final String searchLocation = "Default Location";
		final String searchTimeFrame = "Last Year";
		final String repairOrderNumber = "O-062-00168";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = monitorpage.clickMonitorSettingsLink();	
		if (monitorsettingspage.isOrderStatusReasonExists(orderStatusReason)) {
			monitorsettingspage.deleteOrderStatusReason(orderStatusReason);
		}		
		monitorsettingspage.createNewOrderStatusReason(orderStatus, orderStatusReason);
		
		monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(searchLocation);
		repairorderspage.selectSearchTimeframe(searchTimeFrame);
		repairorderspage.setSearchWoNumber(repairOrderNumber);
		repairorderspage.clickFindButton();
		
		VendorOrderServicesWebPage vendororderservicespage = repairorderspage.clickOnWorkOrderLinkInTable(repairOrderNumber);
		vendororderservicespage.selectRepairOrderStatus(orderStatus);
		vendororderservicespage.selectRepairOrderReason(orderStatusReason);
		vendororderservicespage.clickBackToROLink();
		
		backofficeheader.clickMonitorLink();
		monitorsettingspage = monitorpage.clickMonitorSettingsLink();
		monitorsettingspage.deleteOrderStatusReason(orderStatusReason);
	}

	@Test(testName = "Test Case 28380:Monitor - Verify \"Closed\" Reason at RO", description = "Monitor - Verify \"Closed\" Reason at RO")
	public void testMonitorVerifyClosedReasonAtRO() {
		
		final String orderStatus = "Closed";
		final String orderStatusReason = "testreason";
		final String searchLocation = "Default Location";
		final String searchTimeFrame = "Last Year";
		final String repairOrderNumber = "O-062-00168";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = monitorpage.clickMonitorSettingsLink();	
		if (monitorsettingspage.isOrderStatusReasonExists(orderStatusReason)) {
			monitorsettingspage.deleteOrderStatusReason(orderStatusReason);
		}		
		monitorsettingspage.createNewOrderStatusReason(orderStatus, orderStatusReason);
		
		monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(searchLocation);
		repairorderspage.selectSearchTimeframe(searchTimeFrame);
		repairorderspage.setSearchWoNumber(repairOrderNumber);
		repairorderspage.clickFindButton();
		
		VendorOrderServicesWebPage vendororderservicespage = repairorderspage.clickOnWorkOrderLinkInTable(repairOrderNumber);
		vendororderservicespage.selectRepairOrderStatus(orderStatus);
		vendororderservicespage.selectRepairOrderReason(orderStatusReason);
		vendororderservicespage.clickBackToROLink();
		
		backofficeheader.clickMonitorLink();
		monitorsettingspage = monitorpage.clickMonitorSettingsLink();
		monitorsettingspage.deleteOrderStatusReason(orderStatusReason);
	}
	
	@Test(testName = "Test Case 28378:Monitor - Monitor Settings: CRUD", description = "Monitor - Monitor Settings: CRUD")
	public void testMonitorSettingsCRUD() {
		
		final String orderStatus = "On Hold";
		final String orderStatusReason = "testreasoncrud";
		final String orderStatusEdited = "Closed";
		final String orderStatusReasonEdited = "testreasoncrud2";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = monitorpage.clickMonitorSettingsLink();
		if (monitorsettingspage.isOrderStatusReasonExists(orderStatusReason)) {
			monitorsettingspage.deleteOrderStatusReason(orderStatusReason);
		}	
		if (monitorsettingspage.isOrderStatusReasonExists(orderStatusReasonEdited)) {
			monitorsettingspage.deleteOrderStatusReason(orderStatusReasonEdited);
		}
		monitorsettingspage.createNewOrderStatusReason(orderStatus, orderStatusReason);
		monitorsettingspage.clickEditOrderStatusReason(orderStatusReason);
		Assert.assertEquals(orderStatus, monitorsettingspage.getNewOrderStatus());
		
		monitorsettingspage.setNewOrderStatusReason(orderStatusReasonEdited);
		monitorsettingspage.selectNewOrderStatus(orderStatusEdited);
		monitorsettingspage.clickNewOrderStatusReasonCancelButton();
		
		monitorsettingspage.clickEditOrderStatusReason(orderStatusReason);
		Assert.assertEquals(orderStatus, monitorsettingspage.getNewOrderStatus());
		
		monitorsettingspage.setNewOrderStatusReason(orderStatusReasonEdited);
		monitorsettingspage.selectNewOrderStatus(orderStatusEdited);
		monitorsettingspage.clickNewOrderStatusReasonOKButton();
		
		monitorsettingspage.clickEditOrderStatusReason(orderStatusReasonEdited);
		Assert.assertEquals(orderStatusEdited, monitorsettingspage.getNewOrderStatus());
		monitorsettingspage.clickNewOrderStatusReasonCancelButton();
		
		monitorsettingspage.deleteOrderStatusReason(orderStatusReasonEdited);
	}
	
	@Test(testName = "Test Case 64914:Monitor - Monitor Settings: Employee Role Settings")
	public void testMonitorSettingsEmployeeRoleSettings(){
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = monitorpage.clickMonitorSettingsLink();
		Assert.assertTrue(monitorsettingspage.checkPresenceOfTabs());
		Assert.assertTrue(monitorsettingspage.checkEmployeeRoleSettingsGridColumnsAndRows());
		Assert.assertTrue(monitorsettingspage.checkEmployeeRoleSettingsGridOnOfFieldsAbility());
	}

	@Test(testName = "Test Case 64965:Monitor - Kanban: Auto Refresh ON OFF")
	public void checkMonitorKanbanAutoRefresh() {
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        final String searchLocation = "Default Location";

        MonitorWebPage monitorPage = backofficeHeader.clickMonitorLink();
		WhiteBoardWebPage whiteBoardPage = monitorPage.clickWhiteBoardLink();
		whiteBoardPage.setSearchLocation(searchLocation);
		whiteBoardPage.clickSearchButton();
		Assert.assertTrue(whiteBoardPage.checkSearhResultColumns());
		Assert.assertFalse(whiteBoardPage.checkIntervalFieldLessThan(5));
		Assert.assertFalse(whiteBoardPage.checkIntervalFieldOverThan(720));
		Assert.assertFalse(whiteBoardPage.checkIntervalFieldInputSymbol("a"));
		Assert.assertTrue(whiteBoardPage.checkIntervalField(6));
	}

	//test skipped cause of no test data
	//@Test(testName = "Test Case 65435:Monitor: Reports - Active Vehicles by Phase Subscriptions")
	public void checkMonitorReportsActiveVechiclesByPhaseSubscriptions() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		ActiveVechicleByPhaseWebPage activeVechicleByPhasePage = monitorpage.clickActiveVehiclesByPhaseLink();
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchFields());
		activeVechicleByPhasePage.setLocationFilter("ALM - Recon Facility");
		Assert.assertTrue(activeVechicleByPhasePage.checkTimeFrameField("180"));
		Assert.assertTrue(activeVechicleByPhasePage.checkPhasesInRowCheckBox());
		activeVechicleByPhasePage.clickFindButton();
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchResults("ALM - Recon Facility"));
		
		activeVechicleByPhasePage.setPhase1("PDR Station");
		activeVechicleByPhasePage.setPhase2("PDI");
		activeVechicleByPhasePage.setStatuses1("Active");
		activeVechicleByPhasePage.setStatuses2("Queued","Active","Completed","Audited","Refused","Rework","Skipped");
		activeVechicleByPhasePage.clickPhasesInRow();
		activeVechicleByPhasePage.clickFindButton();
		Assert.assertTrue(activeVechicleByPhasePage.checkThatAllPhasesAreInStatus("PDR Station","Active"));
		SubscriptionsWebPage subscriptionsPege = activeVechicleByPhasePage.clickSubscriptionsButton();
		Assert.assertTrue(subscriptionsPege.checkGrid());
		subscriptionsPege.clickAddButton();
		Assert.assertTrue(subscriptionsPege.checkAddPopUpContent());
		subscriptionsPege.setSubscriptionPopUpLocation("ALM - Recon Facility");
		subscriptionsPege.setSubscriptionPopUpPhase1("PDR Station");
		subscriptionsPege.setSubscriptionPopUpStatuses1("Active");
		subscriptionsPege.setSubscriptionPopUpPhase2("PDI");
		subscriptionsPege.setSubscriptionPopUpStatuses2("Queued","Active","Completed","Audited","Refused","Rework","Skipped");
		subscriptionsPege.setSubscriptionPopUpDescription("test");
		subscriptionsPege.setSubscriptionPopUpStartTime("12:00 AM");
		subscriptionsPege.clickOkAddPopUpButton();
	}
	
	@Test(testName = "Test Case 65432:Monitor: Reports - Service Count")
	public void checkMonitorReportsServiceCount() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		ServiceCountWebPage serviceCountPage = monitorpage.clickServiceCountLink();
		Assert.assertTrue(serviceCountPage.verifySearchFields());
		serviceCountPage.clickSearchButton();
		Assert.assertTrue(serviceCountPage.verifySearchResultGrid());
	}
	
	@Test(testName = "Test Case 65433:Monitor: Reports - Active Vehicles by Phase General")
	public void checkMonitorReportsActiveVechiclesByPhaseGeneral(){
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
        final String searchLocation = "ALM - Recon Facility";

        MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		ActiveVechicleByPhaseWebPage activeVechicleByPhasePage = monitorpage.clickActiveVehiclesByPhaseLink();
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchFields());
		activeVechicleByPhasePage.clickFindButton();
		Assert.assertTrue(activeVechicleByPhasePage.countLocationsInResultTable() > 0);
		activeVechicleByPhasePage.setLocationFilter(searchLocation);
		Assert.assertTrue(activeVechicleByPhasePage.checkTimeFrameFilter());
		activeVechicleByPhasePage.clickFindButton();
        Assert.assertEquals(1, activeVechicleByPhasePage.countLocationsInResultTable());
		Assert.assertTrue(activeVechicleByPhasePage.checkPhasesInRowCheckBox());
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchResults(searchLocation));
	}
	
	@Test(testName = "Test Case 65434:Monitor: Reports - Active Vehicles by Phase Location and Phases")
	public void checkMonitorReportsActiveVechiclesByPhaseLocationAndPhases() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);

        final String searchLocation = "ALM - Recon Facility";
        final String phase1 = "PDR Station";
        final String status1 = "Active";
        final String phase2 = "PDI";
        final String[] statuses2 = { "Queued", "Active", "Completed", "Audited", "Refused", "Rework", "Skipped" };

        MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		ActiveVechicleByPhaseWebPage activeVechicleByPhasePage = monitorpage.clickActiveVehiclesByPhaseLink();
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchFields());
		activeVechicleByPhasePage.clickFindButton();
		Assert.assertTrue(activeVechicleByPhasePage.countLocationsInResultTable() > 0);
		activeVechicleByPhasePage.setLocationFilter(searchLocation);
		Assert.assertTrue(activeVechicleByPhasePage.checkTimeFrameFilter());
		activeVechicleByPhasePage.clickFindButton();
        Assert.assertEquals(1, activeVechicleByPhasePage.countLocationsInResultTable());
		Assert.assertTrue(activeVechicleByPhasePage.checkPhasesInRowCheckBox());
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchResults(searchLocation));
		Assert.assertTrue(activeVechicleByPhasePage.checkGrid());
		
		activeVechicleByPhasePage.setPhase1(phase1);
		activeVechicleByPhasePage.setStatuses1(status1);
		activeVechicleByPhasePage.setPhase2(phase2);
		activeVechicleByPhasePage.setStatuses2(statuses2);
		activeVechicleByPhasePage.clickFindButton();
	}
}
