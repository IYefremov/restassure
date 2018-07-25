package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.bo.BackOfficeOperationsTimeFrameData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.format.DateTimeFormatter;

@Listeners(VideoListener.class)
public class BackOfficeOperationsTimeFrameTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BackOfficeOperationsTimeFrameData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationWorkOrdersTimeframeSearch(String rowID, String description, JSONObject testData) {

        BackOfficeOperationsTimeFrameData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeOperationsTimeFrameData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		WorkOrdersWebPage workOrdersPage = operationsPage.clickWorkOrdersLink();
		workOrdersPage.selectSearchStatus(data.getStatusAll());
		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getCurrentDate(), workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getLastWeekEnd(), workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(data.getStartMonth(), data.getCurrentDate(), workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForAllTablePages(data.getStartLastMonth(), data.getEndLastMonth(), workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartYear(), data.getCurrentDate(), workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);

		workOrdersPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		workOrdersPage.clickFindButton();
		workOrdersPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastYear(), data.getEndLastYear(), workOrdersPage.getWorkOrdersTable(), WorkOrdersWebPage.WOTABLE_DATE_COLUMN_NAME);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInspectionsTimeframeSearch(String rowID, String description, JSONObject testData) {

        BackOfficeOperationsTimeFrameData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeOperationsTimeFrameData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backofficeHeader.clickOperationsLink();
		InspectionsWebPage inspectionspage =  operationspage.clickInspectionsLink();
		
		inspectionspage.selectSearchStatus(data.getStatusAll());
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getCurrentDate(), inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getLastWeekEnd(), inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartMonth(), data.getCurrentDate(), inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastMonth(), data.getEndLastMonth(), inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartYear(), data.getCurrentDate(), inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);

		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		inspectionspage.clickFindButton();
		inspectionspage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastYear(), data.getEndLastYear(), inspectionspage.getInspectionsTable(), InspectionsWebPage.WOTABLE_DATE_COLUMN_NAME);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationVendorBillsTimeframeSearch(String rowID, String description, JSONObject testData) {

        BackOfficeOperationsTimeFrameData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeOperationsTimeFrameData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		VendorBillsWebPage vendorBillsPage = operationsPage.clickVendorBillsLink();
		vendorBillsPage.selectSearchStatus(data.getStatusAll());
		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getCurrentDate(), vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));
		
		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getLastWeekEnd(), vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartMonth(), data.getCurrentDate(), vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastMonth(), data.getEndLastMonth(), vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartYear(), data.getCurrentDate(), vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));

		vendorBillsPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		vendorBillsPage.clickFindButton();
		vendorBillsPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastYear(), data.getEndLastYear(), vendorBillsPage.getVendorBillsTable(), VendorBillsWebPage.WOTABLE_DATE_COLUMN_NAME, DateTimeFormatter.ofPattern(BackOfficeUtils.getTheShortestDateFormat()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoicesTimeframeSearch(String rowID, String description, JSONObject testData) {

        BackOfficeOperationsTimeFrameData data = JSonDataParser.getTestDataFromJson(testData, BackOfficeOperationsTimeFrameData.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		InvoicesWebPage invoicesPage =  operationsPage.clickInvoicesLink();
		
		invoicesPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_WEEKTODATE);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getCurrentDate(), invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
		
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTWEEK);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForAllTablePages(data.getWeekStart(), data.getLastWeekEnd(), invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_MONTHTODATE);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartMonth(), data.getCurrentDate(), invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTMONTH);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastMonth(), data.getEndLastMonth(), invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartYear(), data.getCurrentDate(), invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicesPage.clickFindButton();
		invoicesPage.verifyTableDateRangeForFirstAndLastTablePages(data.getStartLastYear(), data.getEndLastYear(), invoicesPage.getInvoicesTable(), InvoicesWebPage.WOTABLE_DATE_COLUMN_NAME);
	}
}
