package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.bo.BOOperations;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeOperationsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOOperationsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationTechnicianCommissionSearch(String rowID, String description, JSONObject testData) {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

        TechnicianCommissionsWebPage techCommissionPage = operationsPage.clickTechnicianCommissionsLink();
        techCommissionPage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
        techCommissionPage.clickFindButton();
        techCommissionPage.verifyInvoicesTableColumnsAreVisible();

        Assert.assertEquals(data.getPage1(), techCommissionPage.getCurrentlySelectedPageNumber(),
                "The current first page is not opened!");
        Assert.assertEquals(data.getPage1(), techCommissionPage.getGoToPageFieldValue());

        techCommissionPage.setPageSize(data.getPage1());
        Assert.assertEquals(data.getTableRowCount1(), techCommissionPage.getTechnicianCommissionsTableRowCount());

        String lastPageNumber = techCommissionPage.getLastPageNumber();
        techCommissionPage.clickGoToLastPage(browsertype.getBrowserTypeString());
        Assert.assertEquals(lastPageNumber, techCommissionPage.getPageFieldValue());

        techCommissionPage.clickGoToFirstPage();
        Assert.assertEquals(data.getPage1(), techCommissionPage.getGoToPageFieldValue());

        techCommissionPage.clickGoToNextPage();
        Assert.assertEquals(data.getPage2(), techCommissionPage.getGoToPageFieldValue());

        techCommissionPage.clickGoToPreviousPage();
        Assert.assertEquals(data.getPage1(), techCommissionPage.getGoToPageFieldValue());

        techCommissionPage.setPageSize(data.getPage999());
        Assert.assertEquals(techCommissionPage.MAX_TABLE_ROW_COUNT_VALUE,
                techCommissionPage.getTechnicianCommissionsTableRowCount());

        techCommissionPage.verifySearchFieldsAreVisible();

        techCommissionPage.selectSearchStatus(data.getStatus());
        techCommissionPage.selectSearchTechnician(data.getTech());
        techCommissionPage.setSearchInvoice(data.getInvoiceNumber());
        techCommissionPage.clickFindButton();

        Assert.assertEquals(data.getTableRowCount1(), techCommissionPage.getTechnicianCommissionsTableRowCount());
        techCommissionPage.verifySearchResults(data.getInvoiceNumber());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationWorkOrdersSearch(String rowID, String description, JSONObject testData) {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

        WorkOrdersWebPage wopage = operationsPage.clickWorkOrdersLink();
        wopage.clickFindButton();
        wopage.makeSearchPanelVisible();
        wopage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
        wopage.clickFindButton();
        wopage.verifyWorkOrdersTableColumnsAreVisible();

        Assert.assertEquals(data.getPage1(), wopage.getCurrentlySelectedPageNumber());
        Assert.assertEquals(data.getPage1(), wopage.getGoToPageFieldValue());

        wopage.setPageSize(data.getPage1());
        Assert.assertEquals(data.getTableRowCount1(), wopage.getWorkOrdersTableRowCount());

        String lastPageNumber = wopage.getLastPageNumber();
        wopage.clickGoToLastPage(browsertype.getBrowserTypeString());
        Assert.assertEquals(lastPageNumber, wopage.getPageFieldValue());

        wopage.clickGoToFirstPage();
        Assert.assertEquals(data.getPage1(), wopage.getGoToPageFieldValue());

        wopage.clickGoToNextPage();
        Assert.assertEquals(data.getPage2(), wopage.getGoToPageFieldValue());

        wopage.clickGoToPreviousPage();
        Assert.assertEquals(data.getPage1(), wopage.getGoToPageFieldValue());

        wopage.setPageSize(data.getPage999());
        Assert.assertEquals(wopage.getWorkOrdersTableRowCount(), data.getTableRowCount40());

        wopage.makeSearchPanelVisible();
        wopage.verifySearchFieldsAreVisible();

        wopage.selectSearchPackage(data.getSearchPackage());
        wopage.selectSearchStatus(data.getStatus());
        wopage.setSearchOrderNumber(data.getWOnum());
        wopage.unselectInvoiceFromDeviceCheckbox();
        wopage.clickFindButton();
        Assert.assertEquals(data.getTableRowCount1(), wopage.getWorkOrdersTableRowCount());
        wopage.isWorkOrderExists(data.getWOnum());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationServiceContract(String rowID, String description, JSONObject testData) throws Exception {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

        ServiceContractsWebPage serviceContractsPage = operationsPage.clickServiceContactsLink();
        Thread.sleep(2000);
        serviceContractsPage.verifyServiceContactsTableColumnsAreVisible();

        serviceContractsPage.makeSearchPanelVisible();
        serviceContractsPage.verifySearchFieldsAreVisible();
        ServiceContractTypesWebPage serviceContractTypesPage = serviceContractsPage.clickContractTypesButton();
        serviceContractTypesPage.waitServiceContractTypesPageIsLoaded();
        webdriver.navigate().back();
        Thread.sleep(1000);
        serviceContractsPage.clickContractDataButton();
        serviceContractsPage.verifyDropDownMenuIsOpened(browsertype.getBrowserTypeString());

        serviceContractsPage.clickClaimDataButton();
        serviceContractsPage.verifyDropDownMenuIsOpened(browsertype.getBrowserTypeString());

        serviceContractsPage.clickPortfolioButton();
        Thread.sleep(2000);
        serviceContractsPage.verifyPortfolioOptionsAreOpened();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInspection(String rowID, String description, JSONObject testData) throws Exception {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

        InspectionsWebPage inpectionsPage = operationsPage.clickInspectionsLink();
        inpectionsPage.makeSearchPanelVisible();
        inpectionsPage.selectSearchTimeframe(data.getTimeFrame());
        inpectionsPage.clickFindButton();
        inpectionsPage.verifyInspectionsTableColumnsAreVisible();

        Assert.assertEquals(data.getPage1(), inpectionsPage.getCurrentlySelectedPageNumber());
        Assert.assertEquals(data.getPage1(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.setPageSize(data.getPage1());
        Assert.assertEquals(data.getTableRowCount1(), inpectionsPage.getInspectionsTableRowCount());

        String lastpagenumber = inpectionsPage.getLastPageNumber();
        inpectionsPage.clickGoToLastPage(browsertype.getBrowserTypeString());

        inpectionsPage.clickGoToFirstPage();
        Assert.assertEquals(data.getPage1(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.clickGoToNextPage();
        Assert.assertEquals(data.getPage2(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.clickGoToPreviousPage();
        Assert.assertEquals(data.getPage1(), inpectionsPage.getGoToPageFieldValue());

        inpectionsPage.setPageSize(String.valueOf(data.getPageInt50()));
        if (Integer.valueOf(lastpagenumber) < data.getPageInt50()) {
            Assert.assertEquals(Integer.valueOf(lastpagenumber),
                    Integer.valueOf(inpectionsPage.getInspectionsTableRowCount()));
        } else {
            Assert.assertEquals(data.getPageInt50(), inpectionsPage.getInspectionsTableRowCount());
        }

        inpectionsPage.makeSearchPanelVisible();
        inpectionsPage.verifySearchFieldsAreVisible();

        inpectionsPage.selectSearchCustomer(data.getCustomer());
        inpectionsPage.selectSearchTechnician(data.getTechnician(), data.getTechnicianFull());
        inpectionsPage.selectSearchStatus(data.getStatus());
        inpectionsPage.setInspectionNumberSearchCriteria(data.getInspNum());
        inpectionsPage.clickFindButton();
        Assert.assertEquals(data.getTableRowCount1(), inpectionsPage.getInspectionsTableRowCount());
        Thread.sleep(2000);
        inpectionsPage.inspectionExists(data.getInspNum());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationVendorBill(String rowID, String description, JSONObject testData) {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

        VendorBillsWebPage vendorBillsPage = operationsPage.clickVendorBillsLink();
        vendorBillsPage.verifyVendorBillsTableColumnsAreVisible();

        vendorBillsPage.makeSearchPanelVisible();
        vendorBillsPage.verifySearchFieldsAreVisible();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationNewServiceRequest(String rowID, String description, JSONObject testData) {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

        ServiceRequestsListWebPage serviceRequestsListPage = operationsPage.clickNewServiceRequestList();
        serviceRequestsListPage.makeSearchPanelVisible();

        serviceRequestsListPage.verifySearchFieldsAreVisible();

        serviceRequestsListPage.selectSearchTeam(data.getTeamName());
        serviceRequestsListPage.setSearchFreeText(data.getTextSearchParameter());
        serviceRequestsListPage.clickFindButton();
        serviceRequestsListPage.verifySearchResultsByServiceName(data.getTextSearchParameter());

        serviceRequestsListPage.selectAddServiceRequestsComboboxValue(data.getSRtype());
        serviceRequestsListPage.clickAddServiceRequestButton();
        serviceRequestsListPage.clickGeneralInfoEditButton();
        serviceRequestsListPage.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(),
                data.getPOnum(), data.getROnum());
        serviceRequestsListPage.clickDoneButton();

        serviceRequestsListPage.clickCustomerEditButton();
        serviceRequestsListPage.selectServiceRequestCustomer(data.getNewServiceRequest());
        serviceRequestsListPage.clickDoneButton();

        serviceRequestsListPage.clickVehicleInforEditButton();
        serviceRequestsListPage.setServiceRequestVIN(data.getVIN());
        serviceRequestsListPage.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
        serviceRequestsListPage.clickDoneButton();

        serviceRequestsListPage.clickClaimInfoEditButton();
        serviceRequestsListPage.selectServiceRequestInsurance(data.getInsurance());
        serviceRequestsListPage.clickDoneButton();

        serviceRequestsListPage.setServiceRequestLabel(data.getLabel());
        serviceRequestsListPage.setServiceRequestDescription(data.getLabel());

        serviceRequestsListPage.saveNewServiceRequest();

        serviceRequestsListPage.makeSearchPanelVisible();
        serviceRequestsListPage.setSearchFreeText(data.getNewServiceRequest());
        serviceRequestsListPage.clickFindButton();
        Assert.assertTrue(serviceRequestsListPage.verifySearchResultsByServiceName(data.getNewServiceRequest()));
        Assert.assertTrue(serviceRequestsListPage.verifySearchResultsByModelIN(data.getMake(), data.getModel(), data.getYear(), data.getVIN()));

        serviceRequestsListPage.acceptFirstServiceRequestFromList();
        serviceRequestsListPage.closeFirstServiceRequestFromTheList();

        serviceRequestsListPage.makeSearchPanelVisible();
        serviceRequestsListPage.setSearchFreeText(data.getNewServiceRequest());
        serviceRequestsListPage.clickFindButton();
        Assert.assertTrue(serviceRequestsListPage.verifySearchResultsByModelIN(data.getMake(), data.getModel(), data.getYear(), data.getVIN()));
        Assert.assertEquals(data.getStatus(), serviceRequestsListPage.getFirstServiceRequestStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWorkOrderSearchOperation(String rowID, String description, JSONObject testData) {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
        WorkOrdersWebPage workOrderPage = operationsPage.clickWorkOrdersLink();

        Assert.assertTrue(workOrderPage.checkWorkOrdersInfo());
        Assert.assertTrue(workOrderPage.checkWorkOrdersPagination());
        Assert.assertTrue(workOrderPage.checkWorkOrdersSearchFields());
        Assert.assertTrue(workOrderPage.checkWorkOrdersSearchResults(data.getWOnum()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditDuplicateByRO(String rowID, String description, JSONObject testData) {

        BOOperations data = JSonDataParser.getTestDataFromJson(testData, BOOperations.class);
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = operationsPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        String inspection = inspectionsWebPage.findInspectionFromList();
        System.out.println(inspection);
        inspectionsWebPage.searchInspectionByNumber(inspection);
        Assert.assertTrue(inspectionsWebPage.inspectionExists(inspection));
//        VIN: 77777777777777777
//        E-010-00065
//        E-010-00064
//        E-010-00063
//        E-010-00062
//        E-010-00060
//        E-010-00061 todo finish after the TC will be updated
    }
}
//package com.cyberiansoft.test.inhouse.utils;
//
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.cyberiansoft.test.extentreportproviders.ExtentManager;
//import com.cyberiansoft.test.extentreportproviders.ExtentTestManager;
//import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
//import com.cyberiansoft.test.vnext.listeners.TestNG_ConsoleRunner;
//import org.apache.commons.lang3.StringUtils;
//import org.testng.*;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.util.Calendar;
//import java.util.Date;
//
//public class ExtentTestNGIReporterListener extends TestListenerAdapter implements IInvokedMethodListener {
//
//	private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
//
//    @Override
//	public synchronized void onStart(ITestContext context) {
//    	ExtentManager.createInstance(InHouseConfigInfo.getInstance().getReportFolderPath() +
//                InHouseConfigInfo.getInstance().getReportFileName());
//    	//ExtentTest parent = extent.createTest(getClass().getName());
//        //parentTest.set(parent);
//	}
//
//	@Override
//	public synchronized void onFinish(ITestContext context) {
//		ExtentManager.getInstance().flush();
//	}
//
//	@Override
//	public synchronized void onTestStart(ITestResult result) {
//		ExtentTest extent;
//		if (ExtentTestManager.getTest() == null) {
//			ExtentTestManager.createTest(result.getTestClass().getName());
//		}
//		if ( getTestParams(result).isEmpty() ) {
//			extent = ExtentTestManager.getTest().createNode(result.getMethod().getMethodName());
//        }
//
//        else {
//            if ( getTestParams(result).split(",")[0].contains(result.getMethod().getMethodName()) ) {
//            	extent = ExtentTestManager.getTest().createNode(getTestParams(result).split(",")[0], getTestParams(result).split(",")[1]);
//            }
//
//            else {
//            	extent = ExtentTestManager.getTest().createNode(result.getMethod().getMethodName(), getTestParams(result).split(",")[1]);
//            }
//        }
//
//		extent.getModel().setStartTime(getTime(result.getStartMillis()));
//
//		extentTest.set(extent);
//		//ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName());
//        //test.set(child);
//	}
//
//	@Override
//	public synchronized void onTestSuccess(ITestResult result) {
//		extentTest.get().log(Status.PASS, "<font color=#00af00>" + Status.PASS.toString().toUpperCase() + "</font>");
//		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
//	}
//
//	@Override
//	public synchronized void onTestFailure(ITestResult result) {
//		extentTest.get().log(Status.FAIL, "<font color=#F7464A>" + Status.FAIL.toString().toUpperCase() + "</font>");
//		extentTest.get().log(Status.INFO, "EXCEPTION = [" + result.getThrowable().getMessage() + "]");
//		if ( !getTestParams(result).isEmpty() ) {
//			extentTest.get().log(Status.INFO, "STACKTRACE" + getStrackTrace(result));
//		}
//		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
//	}
//
//	@Override
//	public synchronized void onTestSkipped(ITestResult result) {
//		extentTest.get().log(Status.SKIP, "<font color=#2196F3>" + Status.SKIP.toString().toUpperCase() + "</font>");
//		extentTest.get().log(Status.INFO, "EXCEPTION = [" + result.getThrowable().getMessage() + "]");
//		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
//	}
//
//	@Override
//	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//
//	}
//
//	private String getStrackTrace(ITestResult result) {
//        Writer writer = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(writer);
//        result.getThrowable().printStackTrace(printWriter);
//
//        return "<br/>\n" + writer.toString().replace(System.lineSeparator(), "<br/>\n");
//    }
//
//	private String getTestParams(ITestResult tr) {
//        TestNG_ConsoleRunner runner = new TestNG_ConsoleRunner();
//
//        return runner.getTestParams(tr);
//    }
//
//	private Date getTime(long millis) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(millis);
//
//        return calendar.getTime();
//    }
//
//	@Override
//	public void afterInvocation(IInvokedMethod method, ITestResult result) {
//		AfterClass testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AfterClass.class);
//		if (testAnnotation != null) {
//			ExtentTestManager.getTest().getModel().setEndTime(getTime(result.getEndMillis()));
//		}
//
//	}
//
//	@Override
//	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
//		BeforeClass testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(BeforeClass.class);
//		if (testAnnotation != null) {
//			if (!StringUtils.isEmpty(method.getTestMethod().getDescription()))
//				ExtentTestManager.createTest(method.getTestMethod().getDescription());
//			else
//				ExtentTestManager.createTest(result.getMethod().getTestClass().getName());
//			ExtentTestManager.getTest().getModel().setStartTime(getTime(result.getStartMillis()));
//		}
//	}
//}
