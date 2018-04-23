package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BackOfficeOperationsTimeFrameTestCases extends BaseTestCase {
	
	private final LocalDate currentdate = LocalDate.now().plusDays(1);
//	private final LocalDate weekStart = BackOfficeUtils.getWeekStartDate().minusDays(1);
	private final LocalDate weekStart = LocalDate.now().minusDays(8);
	private final LocalDate lastweekstart = BackOfficeUtils.getLastWeekStartDate().minusDays(1);
	private final LocalDate lastweekend = BackOfficeUtils.getLastWeekEndDate().plusDays(2);
//	private final LocalDate lastweekend = BackOfficeUtils.getLastWeekEndDate(lastweekstart).plusDays(2);
	private final LocalDate startmonth = BackOfficeUtils.getMonthStartDate().minusDays(1);
	private final LocalDate startlastmonth = BackOfficeUtils.getLastMonthStartDate().minusDays(1);
	private final LocalDate endlastmonth = BackOfficeUtils.getLastMonthEndDate().plusDays(2);
	private final LocalDate startyear = BackOfficeUtils.getYearStartDate().minusDays(1);
	private final LocalDate startlastyear = BackOfficeUtils.getLastYearStartDate().minusDays(1);
	private final LocalDate endlastyear = BackOfficeUtils.getLastYearEndDate().plusDays(1);

	@Test(testName = "Test Case 31966:Operation - Work Orders: timeframe search", description = "Operation - Work Orders: timeframe search")
	public void testOperationWorkOrdersTimeframeSearch() {
		
		final String statusall = "All";
		
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		WorkOrdersWebPage workOrdersPage = operationsPage.clickWorkOrdersLink();
		workOrdersPage.selectSearchStatus(statusall);
		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(startmonth, currentdate, workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(startlastmonth, endlastmonth, workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
	
	@Test(testName = "Test Case 31968:Operation - Inspections: timeframe search", description = "Operation - Inspections: timeframe search")
	public void testOperationInspectionsTimeframeSearch() {
		
		final String statusall = "All active";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage =  operationspage.clickInspectionsLink();
		
		inspectionspage.selectSearchStatus(statusall);
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);
	}

	@Test(testName = "Test Case 31973:Operation - Vendor Bills: timeframe search", description = "Operation - Vendor Bills: timeframe search")
	public void testOperationVendorBillsTimeframeSearch() {
		
		final String statusall = "All";
		
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		VendorBillsWebPage vendorBillsPage = operationsPage.clickVendorBillsLink();
		vendorBillsPage.selectSearchStatus(statusall);
		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));
		
		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));
	}
	
	@Test(testName = "Test Case 31974:Operation - Invoices: timeframe search", description = "Operation - Invoices: timeframe search")
	public void testOperationInvoicesTimeframeSearch() {
			
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		InvoicesWebPage invoicesPage =  operationsPage.clickInvoicesLink();
		
		invoicesPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
}
