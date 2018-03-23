package com.cyberiansoft.test.bo.testcases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.VendorBillsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.WorkOrdersWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;

public class BackOfficeOperationsTimeFrameTestCases extends BaseTestCase {
	
	final LocalDate currentdate = LocalDate.now().plusDays(1);
	final LocalDate weekStart = BackOfficeUtils.getWeekStartDate().minusDays(1);
	final LocalDate lastweekstart = BackOfficeUtils.getLastWeekStartDate().minusDays(1);
	final LocalDate lastweekend = BackOfficeUtils.getLastWeekEndDate().plusDays(2);
	final LocalDate startmonth = BackOfficeUtils.getMonthStartDate().minusDays(1);
	final LocalDate startlastmonth = BackOfficeUtils.getLastMonthStartDate().minusDays(1);
	final LocalDate endlastmonth = BackOfficeUtils.getLastMonthEndDate().plusDays(1);
	final LocalDate startyear = BackOfficeUtils.getYearStartDate().minusDays(1);
	final LocalDate startlastyear = BackOfficeUtils.getLastYearStartDate().minusDays(1);
	final LocalDate endlastyear = BackOfficeUtils.getLastYearEndDate().plusDays(1);
	
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);
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
	}
	
	@Test(testName = "Test Case 31966:Operation - Work Orders: timeframe search", description = "Operation - Work Orders: timeframe search")
	public void testOperationWorkOrdersTimeframeSearch() throws InterruptedException {
		
		final String statusall = "All";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.selectSearchStatus(statusall);
		workorderspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		workorderspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(startmonth, currentdate, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(startlastmonth, endlastmonth, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
	
	@Test(testName = "Test Case 31968:Operation - Inspections: timeframe search", description = "Operation - Inspections: timeframe search")
	public void testOperationInspectionsTimeframeSearch() throws InterruptedException {
		
		final String statusall = "All active";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage =  operationspage.clickInspectionsLink();
		
		inspectionspage.selectSearchStatus(statusall);
		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
	
	@Test(testName = "Test Case 31973:Operation - Vendor Bills: timeframe search", description = "Operation - Vendor Bills: timeframe search")
	public void testOperationVendorBillsTimeframeSearch() throws InterruptedException {
		
		final String statusall = "All";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		VendorBillsWebPage vendorbillspage = operationspage.clickVendorBillsLink();
		vendorbillspage.selectSearchStatus(statusall);
		vendorbillspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));
		
		vendorbillspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));	
	}
	
	@Test(testName = "Test Case 31974:Operation - Invoices: timeframe search", description = "Operation - Invoices: timeframe search")
	public void testOperationInvoicesTimeframeSearch() throws InterruptedException {
			
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage =  operationspage.clickInvoicesLink();
		
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
}
