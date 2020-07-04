package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.validations.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.bo.BOOperationsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeOperationsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOOperationsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationTechnicianCommissionSearch(String rowID, String description, JSONObject testData) {

		BOOperationsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);

        operationsPage.clickTechnicianCommissionsLink();
        TechnicianCommissionsWebPage techCommissionPage = new TechnicianCommissionsWebPage(webdriver);
		techCommissionPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		techCommissionPage.setSearchFromDate(data.getFromTime());
		techCommissionPage.setSearchToDate(CustomDateProvider.getCurrentDateInShortFormat());

		techCommissionPage.clickFindButton();
		techCommissionPage.verifyInvoicesTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), techCommissionPage.getCurrentlySelectedPageNumber(),
				"The current first page is not opened!");
		Assert.assertEquals(data.getPage1(), techCommissionPage.getGoToPageFieldValue());

		techCommissionPage.setPageSize(data.getPage1());
		Assert.assertEquals(data.getTableRowCount1(), techCommissionPage.getTechnicianCommissionsTableRowCount());

		String lastPageNumber = techCommissionPage.getLastPageNumber();
		techCommissionPage.clickGoToLastPage(browserType.getBrowserTypeString());
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

		techCommissionPage.setPageSize(data.getPage20());
		techCommissionPage.selectSearchStatus(data.getStatus());
		techCommissionPage.selectSearchTechnician(data.getTech());
		techCommissionPage.setSearchInvoice(data.getInvoiceNumber());
		techCommissionPage.clickFindButton();

		Assert.assertEquals(data.getTableRowCount1(), techCommissionPage.getTechnicianCommissionsTableRowCount());
		techCommissionPage.verifySearchResults(data.getInvoiceNumber());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationWorkOrdersSearch(String rowID, String description, JSONObject testData) {

		BOOperationsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		WorkOrdersWebPage wopage = new WorkOrdersWebPage(webdriver);
		operationsPage.clickWorkOrdersLink();
		wopage.clickFindButton();
		wopage.makeSearchPanelVisible();
		wopage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		wopage.setSearchFromDate(data.getFromTime());
		wopage.setSearchToDate(CustomDateProvider.getCurrentDateInShortFormat());
		wopage.clickFindButton();
		wopage.verifyWorkOrdersTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), wopage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), wopage.getGoToPageFieldValue());

		wopage.setPageSize(data.getPage1());
		Assert.assertEquals(data.getTableRowCount1(), wopage.getWorkOrdersTableRowCount());

		String lastPageNumber = wopage.getLastPageNumber();
		wopage.clickGoToLastPage(browserType.getBrowserTypeString());
		Assert.assertEquals(lastPageNumber, wopage.getPageFieldValue());

		wopage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), wopage.getGoToPageFieldValue());

		wopage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), wopage.getGoToPageFieldValue());

		wopage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), wopage.getGoToPageFieldValue());

		wopage.setPageSize(data.getPage999());
//        Assert.assertEquals(data.getTableRowCount189(), wopage.getWorkOrdersTableRowCount());

		wopage.makeSearchPanelVisible();
		wopage.verifySearchFieldsAreVisible();
		wopage.setPageSize(data.getPage20());

		wopage.selectSearchPackage(data.getSearchPackage());
		wopage.selectSearchStatus(data.getStatus());
		wopage.setSearchOrderNumber(data.getWOnum());
		wopage.unselectInvoiceFromDeviceCheckbox();
		wopage.clickFindButton();
		Assert.assertEquals(data.getTableRowCount1(), wopage.getWorkOrdersTableRowCount());
		wopage.workOrderExists(data.getWOnum());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationServiceContract(String rowID, String description, JSONObject testData) throws Exception {

		BOOperationsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);

        operationsPage.clickServiceContactsLink();
        ServiceContractsWebPage serviceContractsPage = new ServiceContractsWebPage(webdriver);
		serviceContractsPage.verifyServiceContactsTableColumnsAreVisible();

		serviceContractsPage.makeSearchPanelVisible();
		serviceContractsPage.verifySearchFieldsAreVisible();
		ServiceContractTypesWebPage serviceContractTypesPage = serviceContractsPage.clickContractTypesButton();
		serviceContractTypesPage.waitServiceContractTypesPageIsLoaded();
		webdriver.navigate().back();
		Thread.sleep(1000);
		serviceContractsPage.clickContractDataButton();
		serviceContractsPage.verifyDropDownMenuIsOpened(browserType.getBrowserTypeString());

		serviceContractsPage.clickClaimDataButton();
		serviceContractsPage.verifyDropDownMenuIsOpened(browserType.getBrowserTypeString());

		serviceContractsPage.clickPortfolioButton();
		Thread.sleep(2000);
		serviceContractsPage.verifyPortfolioOptionsAreOpened();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationVendorBill(String rowID, String description, JSONObject testData) {

		BOOperationsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		VendorBillsWebPage vendorBillsPage = new VendorBillsWebPage(webdriver);
		operationsPage.clickVendorBillsLink();
		vendorBillsPage.verifyVendorBillsTableColumnsAreVisible();

		vendorBillsPage.makeSearchPanelVisible();
		vendorBillsPage.verifySearchFieldsAreVisible();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOperationNewServiceRequest(String rowID, String description, JSONObject testData) {

		BOOperationsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsPage.clickNewServiceRequestList();

		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getServiceType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();


		serviceRequestsListInteractions.clickGeneralInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getServiceRequestGeneralInfo());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.saveNewServiceRequest();

		serviceRequestsListInteractions.makeSearchPanelVisible();

		ServiceRequestsListVerifications.verifySearchFieldsAreVisible();

		serviceRequestsListInteractions.selectSearchTeam(data.getTeamName());
		serviceRequestsListInteractions.setSearchFreeText(data.getTextSearchParameter());
		serviceRequestsListInteractions.setServiceRequestType(data.getServiceType());
		serviceRequestsListInteractions.clickFindButton();
		Assert.assertTrue(ServiceRequestsListVerifications.isServiceNamePresentInFirstSR(data.getTextSearchParameter()));

		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getServiceTypeVit());
		serviceRequestsListInteractions.clickAddServiceRequestButtonWithoutSaving();
		serviceRequestsListInteractions.clickGeneralInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestGeneralInfo(data.getTeamName(), data.getAssignedTo(), data.getPOnum(), data.getROnum());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(data.getVIN());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(data.getMake(), data.getModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsurance());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.setServiceRequestLabel(data.getLabel());
		serviceRequestsListInteractions.setServiceRequestDescription(data.getLabel());

		serviceRequestsListInteractions.saveNewServiceRequest();

		serviceRequestsListInteractions.makeSearchPanelVisible();
        serviceRequestsListInteractions.selectSearchTeam(data.getTeamName());
        serviceRequestsListInteractions.setServiceRequestType(data.getServiceTypeVit());
        serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();
		Assert.assertTrue(ServiceRequestsListVerifications.isServiceNamePresentInFirstSR(data.getNewServiceRequest()));
		Assert.assertTrue(ServiceRequestsListVerifications.verifySearchResultsByModelIN(data.getMake(), data.getModel(), data.getYear(), data.getVIN()));

		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		serviceRequestsListInteractions.closeFirstServiceRequestFromTheList();

		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.setSearchFreeText(data.getNewServiceRequest());
		serviceRequestsListInteractions.clickFindButton();
		Assert.assertTrue(ServiceRequestsListVerifications.verifySearchResultsByModelIN(data.getMake(), data.getModel(), data.getYear(), data.getVIN()));
		Assert.assertEquals(serviceRequestsListInteractions.getFirstServiceRequestStatus(), data.getStatus());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWorkOrderSearchOperation(String rowID, String description, JSONObject testData) {

		BOOperationsData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		operationsPage.clickWorkOrdersLink();
        WorkOrdersWebPage workOrderPage = new WorkOrdersWebPage(webdriver);

		Assert.assertTrue(workOrderPage.checkWorkOrdersInfo());
		Assert.assertTrue(workOrderPage.checkWorkOrdersPagination());
		Assert.assertTrue(workOrderPage.checkWorkOrdersSearchFields());
		Assert.assertTrue(workOrderPage.checkWorkOrdersSearchResults(data.getWOnum(), data.getSearchData()));
	}
}