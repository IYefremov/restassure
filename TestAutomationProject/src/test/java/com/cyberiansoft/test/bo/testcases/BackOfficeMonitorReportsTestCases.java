package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOMonitorReportsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

//@Listeners(VideoListener.class)
public class BackOfficeMonitorReportsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOMonitorReportsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationInvoiceSearch(String rowID, String description, JSONObject testData) {

		BOMonitorReportsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickMonitorLink();
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		monitorWebPage.clickRepairLocationsLink();
		RepairLocationsWebPage repairLocationsWebPage = new RepairLocationsWebPage(webdriver);
		repairLocationsWebPage.addNewRepairLocation(data.getRepairLocationName(), data.getApproxRepairTime(), data.getWorkingDay(), data.getStartTime(), data.getFinishTime(), true);
		repairLocationsWebPage.makeSearchPanelVisible();
		repairLocationsWebPage.setSearchLocation(data.getRepairLocationName());
		repairLocationsWebPage.clickFindButton();
		repairLocationsWebPage.addPhaseForRepairLocation(data.getRepairLocationName(), data.getPhaseNameStart(), data.getPhaseType(), data.getApproxRepairTime(), data.getApproxRepairTime(), true);
		repairLocationsWebPage.addPhaseForRepairLocation(data.getRepairLocationName(), data.getPhaseNameOnHold(), data.getPhaseTypeOnHold(), data.getTransitionTime(), data.getRepairTime(), false);
		repairLocationsWebPage.assignServiceForRepairLocation(data.getRepairLocationName(), data.getWoType(), data.getServiceNameVacuumCleaner(), data.getPhaseNameOnHold());
		repairLocationsWebPage.assignServiceForRepairLocation(data.getRepairLocationName(), data.getWoType(), data.getServiceNameWashing(), data.getPhaseNameStart());

		monitorWebPage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		VendorsTeamsWebPage vendorsteamspage = new VendorsTeamsWebPage(webdriver);
		monitorWebPage.clickVendorsTeamsLink();
		vendorsteamspage.createNewVendorTeam(data.getVendorTeamName(), data.getVendorTimeZone(), data.getVendorDescription(), data.getVendorTimeSheetType(), data.getRepairLocationName(), data.getRepairLocationName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsAverageRepairTimeReport_Part1(String rowID, String description, JSONObject testData) {

		BOMonitorReportsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddressValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		String wonumber = serviceRequestsListInteractions.getWOForFirstServiceRequestFromList();

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.setSearchWoNumber(wonumber);
		repairorderspage.clickFindButton();

		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(wonumber));

		monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = new AverageRepairTimeReportWebPage(webdriver);
		monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		averagerepairtimereportpage.selectSearchLocation(data.getSearchLocation());

		DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MM/yyyy");

		LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
		LocalDateTime then = now.minusDays(2);
		averagerepairtimereportpage.setSearchFromDate(then.format(format));
		averagerepairtimereportpage.setSearchToDate(now.format(format));

		averagerepairtimereportpage.clickFindButton();
		Assert.assertFalse(averagerepairtimereportpage.areLocationResultsDisplayed(data.getSearchLocation()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsAverageRepairTimeReport_Part2(String rowID, String description, JSONObject testData) {

		BOMonitorReportsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = new AverageRepairTimeReportWebPage(webdriver);
		monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		averagerepairtimereportpage.selectSearchLocation(data.getSearchLocation());
		averagerepairtimereportpage.selectSearchWOType(data.getWoType());

		DateTimeFormatter formatting = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		LocalDateTime then = LocalDateTime.of(2015, 1, 3, 12, 12);
		LocalDateTime after = LocalDateTime.now(ZoneId.of("US/Pacific")).plusDays(7);
		averagerepairtimereportpage.setSearchFromDate(then.format(formatting));
		averagerepairtimereportpage.setSearchToDate(after.format(formatting));

		averagerepairtimereportpage.clickFindButton();
		averagerepairtimereportpage.verifySearchResults(data.getSearchLocation(), data.getWoType());

		averagerepairtimereportpage.checkShowDetails();
		averagerepairtimereportpage.clickFindButton();
		averagerepairtimereportpage.verifyDetailReportSearchResults(data.getSearchLocation(), data.getWoType(),
				data.getVIN(), data.getMake(), data.getModel(), data.getYear());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsRepairLocationTimeTracking_Part1(String rowID, String description, JSONObject testData) {

		BOMonitorReportsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operatonspage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operatonspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getAddressValue());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getVIN());
		serviceRequestsListInteractions.clickFindButton();
		String wonumber = serviceRequestsListInteractions.getWOForFirstServiceRequestFromList();

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.setSearchWoNumber(wonumber);
		repairorderspage.clickFindButton();
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(wonumber));

		monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		AverageRepairTimeReportWebPage averagerepairtimereportpage = new AverageRepairTimeReportWebPage(webdriver);
		monitorpage.clickRepairCycleTimeLink();
		averagerepairtimereportpage.makeSearchPanelVisible();
		averagerepairtimereportpage.selectSearchLocation(data.getSearchLocation());

		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yyyy");

		LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
		LocalDateTime before = now.minusDays(7);
		LocalDateTime after = now.plusDays(1);
		averagerepairtimereportpage.setSearchFromDate(before.format(format));
		averagerepairtimereportpage.setSearchToDate(after.format(format));

		averagerepairtimereportpage.clickFindButton();
		Assert.assertFalse(averagerepairtimereportpage.areLocationResultsDisplayed(data.getSearchLocation()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testMonitorReportsRepairLocationTimeTracking_Part2(String rowID, String description, JSONObject testData) {

		BOMonitorReportsData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
		backOfficeHeader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
		monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation(data.getSearchLocation());
		repairorderspage.selectSearchTimeframe(data.getSearchTimeFrame());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now(ZoneId.of("US/Pacific"));
		LocalDate then = LocalDate.of(2018, 4, 17);
		LocalDate after = now.plusDays(7);
		repairorderspage.setSearchFromDate(then.format(formatter));
		repairorderspage.setSearchToDate(after.format(formatter));

		repairorderspage.setSearchWoNumber(data.getWoNumber());
		repairorderspage.clickFindButton();
		Assert.assertTrue(repairorderspage.isRepairOrderPresentInTable(data.getWoNumber()));

		VendorOrderServicesWebPage vendorordersservicespage = new VendorOrderServicesWebPage(webdriver);
		repairorderspage.clickOnWorkOrderLinkInTable(data.getWoNumber());
		vendorordersservicespage.setServicesStatus(data.getServicesStatus());
		vendorordersservicespage.clickBackToROLink();

		// commented because the section Vehicle Time Tracking has been deleted
//        monitorpage = backOfficeHeader.clickMonitorLink();
//        RepairLocationTimeTrackingWebPage repairlocationtimetrackingpage = monitorpage.clickVehicleTimeTrackingLink();
//        repairlocationtimetrackingpage.makeSearchPanelVisible();
//        repairlocationtimetrackingpage.selectSearchLocation(data.getSearchLocation());
//
//		repairlocationtimetrackingpage.setSearchFromDate(then.format(formatter));
//		repairlocationtimetrackingpage.setSearchToDate(after.format(formatter));
//
//		repairlocationtimetrackingpage.clickFindButton();
//  todo uncomment after the wo will be displayed
//		Assert.assertTrue(repairlocationtimetrackingpage.searchWorkOrderInTable(data.getWoNumber()));
	}
}