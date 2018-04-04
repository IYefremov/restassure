package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BackOfficeOperationsTimeFrameTestCases extends BaseTestCase {
	
	private final LocalDate currentdate = LocalDate.now().plusDays(1);
	private final LocalDate weekStart = BackOfficeUtils.getWeekStartDate().minusDays(1);
	private final LocalDate lastweekstart = BackOfficeUtils.getLastWeekStartDate().minusDays(1);
	private final LocalDate lastweekend = BackOfficeUtils.getLastWeekEndDate().plusDays(2);
	private final LocalDate startmonth = BackOfficeUtils.getMonthStartDate().minusDays(1);
	private final LocalDate startlastmonth = BackOfficeUtils.getLastMonthStartDate().minusDays(1);
	private final LocalDate endlastmonth = BackOfficeUtils.getLastMonthEndDate().plusDays(1);
	private final LocalDate startyear = BackOfficeUtils.getYearStartDate().minusDays(1);
	private final LocalDate startlastyear = BackOfficeUtils.getLastYearStartDate().minusDays(1);
	private final LocalDate endlastyear = BackOfficeUtils.getLastYearEndDate().plusDays(1);

    @BeforeMethod
    public void BackOfficeLogin(Method method) {
        System.out.printf("\n* Starting test : %s Method : %s\n", getClass(), method.getName());
        WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
        BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginPage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
    }

    @AfterMethod
    public void BackOfficeLogout() {
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        backOfficeHeader.clickLogout();
    }
	
	@Test(testName = "Test Case 31966:Operation - Work Orders: timeframe search", description = "Operation - Work Orders: timeframe search")
	public void testOperationWorkOrdersTimeframeSearch() {
		
		final String statusall = "All";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.selectSearchStatus(statusall);
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(startmonth, currentdate, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForAllTablePages(startlastmonth, endlastmonth, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		workorderspage.clickFindButton();
		workorderspage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, workorderspage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
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
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		VendorBillsWebPage vendorbillspage = operationspage.clickVendorBillsLink();
		vendorbillspage.selectSearchStatus(statusall);
		vendorbillspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));
		
		vendorbillspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorbillspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		vendorbillspage.clickFindButton();
		vendorbillspage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, vendorbillspage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));	
	}
	
	@Test(testName = "Test Case 31974:Operation - Invoices: timeframe search", description = "Operation - Invoices: timeframe search")
	public void testOperationInvoicesTimeframeSearch() {
			
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage =  operationspage.clickInvoicesLink();
		
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForAllTablePages(weekStart, currentdate, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForAllTablePages(lastweekstart, lastweekend, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startmonth, currentdate, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startlastmonth, endlastmonth, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startyear, currentdate, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.clickFindButton();
		invoicespage.verifyTableDateRangeForFirstAndLastTablePages(startlastyear, endlastyear, invoicespage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
}
