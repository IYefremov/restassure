package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.bo.BOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//@Listeners(VideoListener.class)
public class BackOfficeMonitorTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOMonitorData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairOrderSearch(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();

		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorPage.clickRepairOrdersLink();
		repairorderspage.repairOrdersTableIsVisible();
		repairorderspage.verifyRepairOrdersTableColumnsAreVisible();

		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.selectSearchTimeframe(data.getSearchTimeFrame());
		repairorderspage.setSearchFromDate(data.getFromDate());
		repairorderspage.setSearchToDate(data.getToDate());
		repairorderspage.clickFindButton();
		Assert.assertEquals(data.getPage1(), repairorderspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), repairorderspage.getGoToPageFieldValue());

		String lastpagenumber = repairorderspage.getLastPageNumber();
		repairorderspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, repairorderspage.getGoToPageFieldValue());

		repairorderspage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), repairorderspage.getGoToPageFieldValue());

		repairorderspage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), repairorderspage.getGoToPageFieldValue());

		repairorderspage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), repairorderspage.getGoToPageFieldValue());

		repairorderspage.setPageSize(data.getPage1());
		Assert.assertEquals(1, repairorderspage.getRepairOrdersTableRowCount());
		//String numberofrows = repairorderspage.getLastPageNumber();

		repairorderspage.setPageSize(data.getPage999());
		Assert.assertEquals(50, repairorderspage.getRepairOrdersTableRowCount());

		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.selectSearchCustomer(data.getCompany());
		repairorderspage.setSearchVIN(data.getVIN());
		repairorderspage.setSearchWoNumber(data.getWoNumber());
		repairorderspage.clickFindButton();
		Assert.assertEquals(1, repairorderspage.getRepairOrdersTableRowCount());
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(data.getWoNumber()));
		repairorderspage.verifyTableCustomerAndVinColumnValuesAreVisible(data.getCompany(), data.getVIN());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVendorOrdersSearch(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		VendorOrdersWebPage vendororderspage = new VendorOrdersWebPage(webdriver);
		monitorPage.clickVendorOrdersLink();
		vendororderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		vendororderspage.setSearchFromDate(data.getFromDate());
		vendororderspage.setSearchToDate(data.getToDate());
		vendororderspage.selectSearchServiceStatus(data.getServiceStatus());
		vendororderspage.clickFindButton();
		vendororderspage.repairOrdersTableIsVisible();
		vendororderspage.verifyVendorOrdersTableColumnsAreVisible();
		Assert.assertEquals(data.getPage1(), vendororderspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), vendororderspage.getGoToPageFieldValue());

		String lastpagenumber = vendororderspage.getLastPageNumber();
		vendororderspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, vendororderspage.getGoToPageFieldValue());

		vendororderspage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), vendororderspage.getGoToPageFieldValue());

		vendororderspage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), vendororderspage.getGoToPageFieldValue());

		vendororderspage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), vendororderspage.getGoToPageFieldValue());

		vendororderspage.setPageSize(data.getPage1());
		Assert.assertEquals(1, vendororderspage.getRepairOrdersTableRowCount());

		vendororderspage.setPageSize(data.getPage999());
		Assert.assertEquals(50, vendororderspage.getRepairOrdersTableRowCount());

		vendororderspage.selectSearchLocation(data.getSearchLocation());
		vendororderspage.selectSearchVendor(data.getVendor());
		vendororderspage.setSearchVIN(data.getVIN());
		vendororderspage.selectSearchCustomer(data.getCompany());
		vendororderspage.setSearchWorkorderNumber(data.getWoNumber());

		vendororderspage.clickFindButton();
		Assert.assertEquals(1, vendororderspage.getRepairOrdersTableRowCount());
		Assert.assertEquals(data.getVendor(), vendororderspage.getTeamVendorForVendorOrder(data.getWoNumber()));
		Assert.assertEquals(data.getCompany(), vendororderspage.getCustomerForVendorOrder(data.getWoNumber()));

		vendororderspage.vendorOrderExists(data.getWoNumber());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsAverageRepairTimeReport(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = new AverageRepairTimeReportWebPage(webdriver);
		monitorPage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		Assert.assertTrue(averagerepairtimereportpage.searchPanelIsExpanded());
		averagerepairtimereportpage.selectSearchLocation(data.getSearchLocation());
		averagerepairtimereportpage.selectSearchWOType(data.getSearchWoType());
		averagerepairtimereportpage.setSearchFromDate(data.getFromDate());
		averagerepairtimereportpage.setSearchToDate(data.getToDate());
		averagerepairtimereportpage.clickFindButton();
		averagerepairtimereportpage.verifySearchResults(data.getSearchLocation(), data.getSearchWoType());
	}

	// The section Vehicle Time Tracking has been removed
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsRepairLocationTimeTracking(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairLocationTimeTrackingWebPage repairlocationtimetrackingpage = new RepairLocationTimeTrackingWebPage(webdriver);
		monitorPage.clickVehicleTimeTrackingLink();

		repairlocationtimetrackingpage.makeSearchPanelVisible();
		Assert.assertTrue(repairlocationtimetrackingpage.searchPanelIsExpanded());
		repairlocationtimetrackingpage.selectSearchLocation(data.getSearchLocation());
		repairlocationtimetrackingpage.setSearchFromDate(data.getFromDate());
		repairlocationtimetrackingpage.setSearchToDate(data.getToDate());

		repairlocationtimetrackingpage.clickFindButton();
		Assert.assertTrue(repairlocationtimetrackingpage.isTableIsDisplayed(), "The table is not displayed.");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsTrendingReport(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		TrendingReportWebPage trendingreportpage = new TrendingReportWebPage(webdriver);
		monitorPage.clickCycleTimeTrendingLink();

		Assert.assertTrue(trendingreportpage.searchPanelIsExpanded());
		trendingreportpage.selectSearchLocation(data.getSearchLocation());
		trendingreportpage.selectSearchWOType(data.getSearchWoType());
		trendingreportpage.setSearchFromDate(data.getFromMonth(), data.getFromYear());
		trendingreportpage.setSearchToDate(data.getToMonth(), data.getToYear());

		trendingreportpage.clickFindButton();
		Assert.assertTrue(trendingreportpage.areSearchResultsDisplayed(data.getSearchLocation(), data.getSearchWoType()),
				"The search results are not displayed.");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorRepairOrderFullDisplayVersion(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorPage.clickRepairOrdersLink();

		repairorderspage.makeSearchPanelVisible();
		repairorderspage.searchPanelIsExpanded();
		repairorderspage.searchPanelIsVisible();
		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.selectSearchTimeframe(data.getSearchTimeFrame());
		repairorderspage.clickFindButton();
		repairorderspage.openFullDisplayWOMonitorAndVerifyContent();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVerifyOnHoldReasonAtRO(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = new MonitorSettingsWebPage(webdriver);
		monitorPage.clickMonitorSettingsLink();

		monitorsettingspage.verifyOrderStatusReasonIsNotPresent(data.getOrderStatusReason());
		monitorsettingspage.createNewOrderStatusReason(data.getOrderStatus(), data.getOrderStatusReason());

		monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorPage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.selectSearchTimeframe(data.getSearchTimeFrame());
		repairorderspage.setSearchFromDate(data.getFromDate());
		repairorderspage.setSearchToDate(data.getToDate());
		repairorderspage.setSearchWoNumber(data.getRepairOrderNumber());
		repairorderspage.clickFindButton();

		VendorOrderServicesWebPage vendororderservicespage = new VendorOrderServicesWebPage(webdriver);
		repairorderspage.clickOnWorkOrderLinkInTable(data.getRepairOrderNumber());
		vendororderservicespage.selectRepairOrderStatus(data.getOrderStatus());
		vendororderservicespage.selectRepairOrderReason(data.getOrderStatusReason());
		vendororderservicespage.clickBackToROLink();

		backOfficeHeader.clickMonitorLink();
		monitorsettingspage = new MonitorSettingsWebPage(webdriver);
		monitorPage.clickMonitorSettingsLink();
		monitorsettingspage.deleteOrderStatusReason(data.getOrderStatusReason());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorVerifyClosedReasonAtRO(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = new MonitorSettingsWebPage(webdriver);
		monitorPage.clickMonitorSettingsLink();
		monitorsettingspage.verifyOrderStatusReasonIsNotPresent(data.getOrderStatusReason());
		monitorsettingspage.createNewOrderStatusReason(data.getOrderStatus(), data.getOrderStatusReason());

		monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorPage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.selectSearchTimeframe(data.getSearchTimeFrame());
		repairorderspage.setSearchFromDate(data.getFromDate());
		repairorderspage.setSearchToDate(data.getToDate());
		repairorderspage.setSearchWoNumber(data.getRepairOrderNumber());
		repairorderspage.clickFindButton();

		VendorOrderServicesWebPage vendororderservicespage = new VendorOrderServicesWebPage(webdriver);
		repairorderspage.clickOnWorkOrderLinkInTable(data.getRepairOrderNumber());
		vendororderservicespage.selectRepairOrderStatus(data.getOrderStatus());
		vendororderservicespage.selectRepairOrderReason(data.getOrderStatusReason());
		vendororderservicespage.clickBackToROLink();

		backOfficeHeader.clickMonitorLink();
		monitorsettingspage = new MonitorSettingsWebPage(webdriver);
		monitorPage.clickMonitorSettingsLink();
		monitorsettingspage.deleteOrderStatusReason(data.getOrderStatusReason());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorSettingsCRUD(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = new MonitorSettingsWebPage(webdriver);
		monitorPage.clickMonitorSettingsLink();
		monitorsettingspage.verifyOrderStatusReasonIsNotPresent(data.getOrderStatusReason());
		monitorsettingspage.createNewOrderStatusReason(data.getOrderStatus(), data.getOrderStatusReason());
		monitorsettingspage.clickEditOrderStatusReason(data.getOrderStatusReason());
		Assert.assertEquals(data.getOrderStatus(), monitorsettingspage.getNewOrderStatus());

		monitorsettingspage.setNewOrderStatusReason(data.getOrderStatusReasonEdited());
		monitorsettingspage.selectNewOrderStatus(data.getOrderStatusEdited());
		monitorsettingspage.clickNewOrderStatusReasonCancelButton();

		monitorsettingspage.clickEditOrderStatusReason(data.getOrderStatusReason());
		Assert.assertEquals(data.getOrderStatus(), monitorsettingspage.getNewOrderStatus());

		monitorsettingspage.setNewOrderStatusReason(data.getOrderStatusReasonEdited());
		monitorsettingspage.selectNewOrderStatus(data.getOrderStatusEdited());
		monitorsettingspage.clickNewOrderStatusReasonOKButton();

		monitorsettingspage.clickEditOrderStatusReason(data.getOrderStatusReasonEdited());
		Assert.assertEquals(data.getOrderStatusEdited(), monitorsettingspage.getNewOrderStatus());
		monitorsettingspage.clickNewOrderStatusReasonCancelButton();

		monitorsettingspage.deleteOrderStatusReason(data.getOrderStatusReasonEdited());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorSettingsEmployeeRoleSettings(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		MonitorSettingsWebPage monitorsettingspage = new MonitorSettingsWebPage(webdriver);
		monitorPage.clickMonitorSettingsLink();
		Assert.assertTrue(monitorsettingspage.checkPresenceOfTabs());
		Assert.assertTrue(monitorsettingspage.checkEmployeeRoleSettingsGridColumnsAndRows());
		Assert.assertTrue(monitorsettingspage.checkEmployeeRoleSettingsGridOnOfFieldsAbility());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMonitorWhiteBoardAutoRefresh(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickMonitorLink();
        MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
        monitorPage.clickWhiteBoardLink();
        WhiteBoardWebPage whiteBoardPage = new WhiteBoardWebPage(webdriver);
		whiteBoardPage.setSearchLocation(data.getSearchLocation());
		whiteBoardPage.clickSearchButton();
		Assert.assertTrue(whiteBoardPage.checkSearchResultColumns());
		Assert.assertFalse(whiteBoardPage.checkIntervalFieldLessThan(5));
		Assert.assertFalse(whiteBoardPage.checkIntervalFieldOverThan(720));
		Assert.assertFalse(whiteBoardPage.checkIntervalFieldInputSymbol("a"));
		Assert.assertTrue(whiteBoardPage.checkIntervalField(6));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMonitorReportsServiceCount(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		ServiceCountWebPage serviceCountPage = new ServiceCountWebPage(webdriver);
		monitorPage.clickWipSummaryLink();
//		Assert.assertTrue(serviceCountPage.verifySearchFields()); todo the fields are no more displayed
		serviceCountPage.clickSearchButton();
//		Assert.assertTrue(serviceCountPage.verifySearchResultGrid()); todo the fields are no more displayed
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMonitorReportsActiveVehiclesByPhaseGeneral(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickMonitorLink();
        MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		monitorPage.clickActiveVehiclesByPhaseLink();
        ActiveVechicleByPhaseWebPage activeVehicleByPhasePage = new ActiveVechicleByPhaseWebPage(webdriver);
		Assert.assertTrue(activeVehicleByPhasePage.checkSearchFields());
		activeVehicleByPhasePage.clickFindButton();
        Assert.assertTrue(activeVehicleByPhasePage.countLocationsInResultTable() > 0);
		activeVehicleByPhasePage.setLocationFilter(data.getSearchLocation());
		Assert.assertTrue(activeVehicleByPhasePage.checkTimeFrameFilter());
        activeVehicleByPhasePage.setTimeFrameFilter(data.getSearchTimeFrame());
		activeVehicleByPhasePage.clickFindButton();
        Assert.assertEquals(1, activeVehicleByPhasePage.countLocationsInResultTable());
		Assert.assertTrue(activeVehicleByPhasePage.checkPhasesInRowCheckBox());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMonitorReportsActiveVehiclesByPhaseLocationAndPhases(String rowID, String description, JSONObject testData) {

		BOMonitorData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickMonitorLink();
        MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
        monitorPage.clickServiceCountLink();
        ActiveVechicleByPhaseWebPage activeVehicleByPhasePage = new ActiveVechicleByPhaseWebPage(webdriver);
//		Assert.assertTrue(activeVehicleByPhasePage.checkSearchFields());
		activeVehicleByPhasePage.clickFindButton();
//		Assert.assertTrue(activeVehicleByPhasePage.countLocationsInResultTable() > 0);
		activeVehicleByPhasePage.setLocationFilter(data.getSearchLocation());
		activeVehicleByPhasePage.setSearchFromDate(data.getFromDate());
		activeVehicleByPhasePage.setSearchToDate(data.getToDate());
//		Assert.assertTrue(activeVehicleByPhasePage.checkTimeFrameFilter());
		activeVehicleByPhasePage.clickFindButton();
//        Assert.assertEquals(1, activeVehicleByPhasePage.countLocationsInResultTable());
//		Assert.assertTrue(activeVehicleByPhasePage.checkPhasesInRowCheckBox());
		Assert.assertTrue(activeVehicleByPhasePage.checkSearchResults(data.getSearchLocation()));
//		Assert.assertTrue(activeVehicleByPhasePage.checkGrid());
	}

	//test skipped cause of no test data
	//@Test(testName = "Test Case 65435:Monitor: Reports - Active Vehicles by Phase Subscriptions")
	public void checkMonitorReportsActiveVechiclesByPhaseSubscriptions() throws InterruptedException {
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		ActiveVechicleByPhaseWebPage activeVechicleByPhasePage = new ActiveVechicleByPhaseWebPage(webdriver);
		monitorPage.clickServiceCountLink();
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchFields());
		activeVechicleByPhasePage.setLocationFilter("ALM - Recon Facility");
		Assert.assertTrue(activeVechicleByPhasePage.checkTimeFrameField("180"));
		Assert.assertTrue(activeVechicleByPhasePage.checkPhasesInRowCheckBox());
		activeVechicleByPhasePage.clickFindButton();
		Assert.assertTrue(activeVechicleByPhasePage.checkSearchResults("ALM - Recon Facility"));

		activeVechicleByPhasePage.setPhase1("PDR Station");
		activeVechicleByPhasePage.setPhase2("PDI");
		activeVechicleByPhasePage.setStatuses1("Active");
		activeVechicleByPhasePage.setStatuses2("Queued", "Active", "Completed", "Audited", "Refused", "Rework", "Skipped");
		activeVechicleByPhasePage.clickPhasesInRow();
		activeVechicleByPhasePage.clickFindButton();
		Assert.assertTrue(activeVechicleByPhasePage.checkThatAllPhasesAreInStatus("PDR Station", "Active"));
		SubscriptionsWebPage subscriptionsPege = activeVechicleByPhasePage.clickSubscriptionsButton();
		Assert.assertTrue(subscriptionsPege.checkGrid());
		subscriptionsPege.clickAddButton();
		Assert.assertTrue(subscriptionsPege.checkAddPopUpContent());
		subscriptionsPege.setSubscriptionPopUpLocation("ALM - Recon Facility");
		subscriptionsPege.setSubscriptionPopUpPhase1("PDR Station");
		subscriptionsPege.setSubscriptionPopUpStatuses1("Active");
		subscriptionsPege.setSubscriptionPopUpPhase2("PDI");
		subscriptionsPege.setSubscriptionPopUpStatuses2("Queued", "Active", "Completed", "Audited", "Refused", "Rework", "Skipped");
		subscriptionsPege.setSubscriptionPopUpDescription("test");
		subscriptionsPege.setSubscriptionPopUpStartTime("12:00 AM");
		subscriptionsPege.clickOkAddPopUpButton();
	}
}
