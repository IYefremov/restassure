package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class BackOfficeOperationsTestCases extends BaseTestCase {

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

	@Test(description = "Test Case 15295:Operations - Technician Commission: Search")
	public void testOperationTechnicianCommissionSearch() throws Exception {
		
		final String invoiceNumber = "I-000-00646";
		final String status = "New";
		final String tech = "1111 2222";

		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

		TechnicianCommissionsWebPage techCommissionPage = operationsPage.clickTechnicianCommissionsLink();
		techCommissionPage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		techCommissionPage.clickFindButton();
		techCommissionPage.verifyInvoicesTableColumnsAreVisible();

		Assert.assertEquals("1", techCommissionPage.getCurrentlySelectedPageNumber(),
                "The current first page is not opened!");
		Assert.assertEquals("1", techCommissionPage.getGoToPageFieldValue());

		techCommissionPage.setPageSize("1");
		Assert.assertEquals(1, techCommissionPage.getTechnicianCommissionsTableRowCount());

		String lastPageNumber = techCommissionPage.getLastPageNumber();
		techCommissionPage.clickGoToLastPage(browsertype.getBrowserTypeString());
		Assert.assertEquals(lastPageNumber, techCommissionPage.getGoToPageFieldValue().replace(",", ""));

		techCommissionPage.clickGoToFirstPage();
		Assert.assertEquals("1", techCommissionPage.getGoToPageFieldValue());

		techCommissionPage.clickGoToNextPage();
		Assert.assertEquals("2", techCommissionPage.getGoToPageFieldValue());

		techCommissionPage.clickGoToPreviousPage();
		Assert.assertEquals("1", techCommissionPage.getGoToPageFieldValue());

		techCommissionPage.setPageSize("999");
		Assert.assertEquals(techCommissionPage.MAX_TABLE_ROW_COUNT_VALUE,
				Integer.valueOf(techCommissionPage.getTechnicianCommissionsTableRowCount()));

		techCommissionPage.verifySearchFieldsAreVisible();

		techCommissionPage.selectSearchStatus(status);
		techCommissionPage.selectSearchTechnician(tech);
		techCommissionPage.setSearchInvoice(invoiceNumber);
		techCommissionPage.clickFindButton();

		Assert.assertEquals(1, techCommissionPage.getTechnicianCommissionsTableRowCount());
		techCommissionPage.verifySearchResults(invoiceNumber);
	}

	@Test(description = "Test Case 15386:Operations - Work Orders: Search")
	public void testOperationWorkOrdersSearch() throws Exception {

		final String wonum = "O-000-148090";
		final String _package = "ALM - Recon Facility";
		final String status = "New";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage wopage = operationspage.clickWorkOrdersLink();
		wopage.clickFindButton();
		wopage.makeSearchPanelVisible();
		wopage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		wopage.clickFindButton();
		wopage.verifyWorkOrdersTableColumnsAreVisible();

		Assert.assertEquals("1", wopage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", wopage.getGoToPageFieldValue());

		wopage.setPageSize("1");
		Assert.assertEquals(1, wopage.getWorkOrdersTableRowCount());

		String lastPageNumber = wopage.getLastPageNumber();
		wopage.clickGoToLastPage(browsertype.getBrowserTypeString());
		Assert.assertEquals(lastPageNumber, wopage.getGoToPageFieldValue().replace(",", ""));

		wopage.clickGoToFirstPage();
		Assert.assertEquals("1", wopage.getGoToPageFieldValue());

		wopage.clickGoToNextPage();
		Assert.assertEquals("2", wopage.getGoToPageFieldValue());

		wopage.clickGoToPreviousPage();
		Assert.assertEquals("1", wopage.getGoToPageFieldValue());

		wopage.setPageSize("999");
		Assert.assertEquals(wopage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(wopage.getWorkOrdersTableRowCount()));

		wopage.makeSearchPanelVisible();
		wopage.verifySearchFieldsAreVisible();

		wopage.selectSearchPackage(_package);
		wopage.selectSearchStatus(status);
		wopage.setSearchOrderNumber(wonum);
		wopage.unselectInvoiceFromDeviceCheckbox();
		wopage.clickFindButton();
		Assert.assertEquals(1, wopage.getWorkOrdersTableRowCount());
		wopage.isWorkOrderExists(wonum);
	}

	/*
	 * @Test(description = "Test Case 17279:Operations - Service Request")
	 * public void testOperationServiceRequestSearch() throws Exception {
	 * 
	 * BackOfficeHeaderPanel backofficeheader =
	 * PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
	 * OperationsWebPage operationspage =
	 * backofficeheader.clickOperationsLink();
	 * 
	 * ServiceRequestsWebPage servicerequestspage =
	 * operationspage.clickServiceRequestsLink();
	 * servicerequestspage.verifyInvoicesTableColumnsAreVisible();
	 * 
	 * 
	 * Assert.assertEquals("1",
	 * servicerequestspage.getCurrentlySelectedPageNumber());
	 * Assert.assertEquals("1", servicerequestspage.getGoToPageFieldValue());
	 * 
	 * servicerequestspage.setPageSize("1"); Assert.assertEquals(1,
	 * servicerequestspage.getTechnicianCommissionsTableRowCount());
	 * 
	 * String lastpagenumber = servicerequestspage.getLastPageNumber();
	 * servicerequestspage.clickGoToLastPage();
	 * //Assert.assertEquals(lastpagenumber,
	 * servicerequestspage.getGoToPageFieldValue());
	 * 
	 * servicerequestspage.clickGoToFirstPage(); Assert.assertEquals("1",
	 * servicerequestspage.getGoToPageFieldValue());
	 * 
	 * servicerequestspage.clickGoToNextPage(); Assert.assertEquals("2",
	 * servicerequestspage.getGoToPageFieldValue());
	 * 
	 * servicerequestspage.clickGoToPreviousPage(); Assert.assertEquals("1",
	 * servicerequestspage.getGoToPageFieldValue());
	 * 
	 * servicerequestspage.setPageSize("999"); Thread.sleep(1000);
	 * Assert.assertEquals(servicerequestspage.MAX_TABLE_ROW_COUNT_VALUE,
	 * Integer.valueOf(servicerequestspage.getTechnicianCommissionsTableRowCount
	 * ()));
	 * 
	 * servicerequestspage.makeSearchPanelVisible();
	 * servicerequestspage.verifySearchFieldsAreVisible();
	 * 
	 * servicerequestspage.selectSearchStatus("Dispatched");
	 * servicerequestspage.setSearchText("asfdfasdf");
	 * servicerequestspage.selectSearchLabel("111");
	 * servicerequestspage.clickFindButton();
	 * 
	 * Assert.assertEquals(Integer.valueOf(1),
	 * Integer.valueOf(servicerequestspage.getTechnicianCommissionsTableRowCount
	 * ())); servicerequestspage.
	 * verifySearchResultsByVehicleInfo("2002 Ford F-150 SDN"); }
	 */

	@Test(description = "Test Case 17280:Operations - Service Contract")
	public void testOperationServiceContract() throws Exception {

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

	@Test(description = "Test Case 17281:Operations - Inspection")
	public void testOperationInspection() throws Exception {

		final String timeframe = "Last Year";

		final String inspnum = "E-062-00119";
		final String customer = "000 My Company";

		final String inspstatus = "Approved";
		final String technician = "Serhii Toropov";
		final String technicianfull = "Serhii Toropov";

		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

		InspectionsWebPage inpectionsPage = operationsPage.clickInspectionsLink();
		inpectionsPage.makeSearchPanelVisible();
		inpectionsPage.selectSearchTimeframe(timeframe);
		inpectionsPage.clickFindButton();
		inpectionsPage.verifyInspectionsTableColumnsAreVisible();

		Assert.assertEquals("1", inpectionsPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", inpectionsPage.getGoToPageFieldValue());

		inpectionsPage.setPageSize("1");
		Assert.assertEquals(1, inpectionsPage.getInspectionsTableRowCount());

		String lastpagenumber = inpectionsPage.getLastPageNumber();
		inpectionsPage.clickGoToLastPage(browsertype.getBrowserTypeString());

		inpectionsPage.clickGoToFirstPage();
		Assert.assertEquals("1", inpectionsPage.getGoToPageFieldValue());

		inpectionsPage.clickGoToNextPage();
		Assert.assertEquals("2", inpectionsPage.getGoToPageFieldValue());

		inpectionsPage.clickGoToPreviousPage();
		Assert.assertEquals("1", inpectionsPage.getGoToPageFieldValue());

		inpectionsPage.setPageSize("50");
		if (Integer.valueOf(lastpagenumber) < 50) {
			Assert.assertEquals(Integer.valueOf(lastpagenumber),
					Integer.valueOf(inpectionsPage.getInspectionsTableRowCount()));
		} else {
			Assert.assertEquals(50, inpectionsPage.getInspectionsTableRowCount());
		}

		inpectionsPage.makeSearchPanelVisible();
		inpectionsPage.verifySearchFieldsAreVisible();

		inpectionsPage.selectSearchCustomer(customer);
		inpectionsPage.selectSearchTechnician(technician, technicianfull);
		inpectionsPage.selectSearchStatus(inspstatus);
		inpectionsPage.setInspectionNumberSearchCriteria(inspnum);
		inpectionsPage.clickFindButton();
		Assert.assertEquals(1, inpectionsPage.getInspectionsTableRowCount());
		Thread.sleep(2000);
		inpectionsPage.isInspNumberExists(inspnum);
	}

	@Test(description = "Test Case 17282:Operations - Vendor Bill")
	public void testOperationVendorBill() {

		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();

		VendorBillsWebPage vendorBillsPage = operationsPage.clickVendorBillsLink();
		vendorBillsPage.verifyVendorBillsTableColumnsAreVisible();

		/*
		 * Assert.assertEquals("1",
		 * inpectionspage.getCurrentlySelectedPageNumber());
		 * Assert.assertEquals("1", inpectionspage.getGoToPageFieldValue());
		 * 
		 * inpectionspage.setPageSize(1); Assert.assertEquals(1,
		 * inpectionspage.getInspectionsTableRowCount());
		 * 
		 * String lastpagenumber = inpectionspage.getLastPageNumber();
		 * inpectionspage.clickGoToLastPage();
		 * //Assert.assertEquals(lastpagenumber,
		 * servicerequestspage.getGoToPageFieldValue());
		 * 
		 * inpectionspage.clickGoToFirstPage(); Assert.assertEquals("1",
		 * inpectionspage.getGoToPageFieldValue());
		 * 
		 * inpectionspage.clickGoToNextPage(); Assert.assertEquals("2",
		 * inpectionspage.getGoToPageFieldValue());
		 * 
		 * inpectionspage.clickGoToPreviousPage(); Assert.assertEquals("1",
		 * inpectionspage.getGoToPageFieldValue());
		 * 
		 * inpectionspage.setPageSize(50); if (Integer.valueOf(lastpagenumber) <
		 * 50) { Assert.assertEquals(Integer.valueOf(lastpagenumber),
		 * Integer.valueOf(inpectionspage.getInspectionsTableRowCount())); }
		 * else { Assert.assertEquals(Integer.valueOf(50),
		 * Integer.valueOf(inpectionspage.getInspectionsTableRowCount())); }
		 */
		vendorBillsPage.makeSearchPanelVisible();
		vendorBillsPage.verifySearchFieldsAreVisible();

		/*
		 * inpectionspage.selectSearchCustomer("002 - Test Company");
		 * inpectionspage.selectSearchType("Atrem test");
		 * inpectionspage.setStockSearchCriteria("7979");
		 * inpectionspage.selectSearchTechnician("Artem Galyuk");
		 * inpectionspage.selectSearchStatus("Approved");
		 * inpectionspage.setInspectionNumberSearchCriteria("E-10074-00143");
		 * inpectionspage.clickFindButton();
		 * 
		 * Assert.assertEquals(Integer.valueOf(1),
		 * Integer.valueOf(inpectionspage.getInspectionsTableRowCount()));
		 * inpectionspage.verifySearchResultsByInspNumber("E-10074-00143");
		 */
	}

	@Test(description = "Test Case 18475:Operation - New service request")
	public void testOperationNewServiceRequest() throws Exception {

		final String srtype = "Vit_All_Services";

		final String teamname = "Default team";
		final String textsearchparameter = "Alex SASHAZ";
		final String newservicerequest = "Alex SASHAZ";

		final String assignedto = "Igor Baluev";
		final String ponum = "D525";
		final String ronum = "Dfg 25";

		final String VIN = "1HGCG55691A267167";
		final String _make = "Honda";
		final String _model = "Accord";
		final String _year = "2001";

		final String insurance = "Oranta";
		final String _label = "test";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage serviceRequestsListPage = operationspage.clickNewServiceRequestLink();
		serviceRequestsListPage.makeSearchPanelVisible();

		serviceRequestsListPage.verifySearchFieldsAreVisible();

		serviceRequestsListPage.selectSearchTeam(teamname);
		serviceRequestsListPage.setSearchFreeText(textsearchparameter);
		serviceRequestsListPage.clickFindButton();
		serviceRequestsListPage.verifySearchResultsByServiceName(textsearchparameter);

		serviceRequestsListPage.selectAddServiceRequestsComboboxValue(srtype);
		serviceRequestsListPage.clickAddServiceRequestButton();
		serviceRequestsListPage.clickGeneralInfoEditButton();
		serviceRequestsListPage.setServiceRequestGeneralInfo(teamname, assignedto, ponum, ronum);
		serviceRequestsListPage.clickDoneButton();

		serviceRequestsListPage.clickCustomerEditButton();
		serviceRequestsListPage.selectServiceRequestCustomer(newservicerequest);
		serviceRequestsListPage.clickDoneButton();

		serviceRequestsListPage.clickVehicleInforEditButton();
		serviceRequestsListPage.setServiceRequestVIN(VIN);
		serviceRequestsListPage.decodeAndVerifyServiceRequestVIN(_make, _model);
		serviceRequestsListPage.clickDoneButton();

		serviceRequestsListPage.clickClaimInfoEditButton();
		serviceRequestsListPage.selectServiceRequesInsurance(insurance);
		serviceRequestsListPage.clickDoneButton();

		serviceRequestsListPage.setServiceRequestLabel(_label);
		serviceRequestsListPage.setServiceRequestDescription(_label);

		serviceRequestsListPage.saveNewServiceRequest();

		serviceRequestsListPage.makeSearchPanelVisible();
		serviceRequestsListPage.setSearchFreeText(newservicerequest);
		serviceRequestsListPage.clickFindButton();
		Assert.assertTrue(serviceRequestsListPage.verifySearchResultsByServiceName(newservicerequest));
		Assert.assertTrue(serviceRequestsListPage.verifySearchResultsByModelIN(_make, _model, _year, VIN));

		serviceRequestsListPage.acceptFirstServiceRequestFromList();
		serviceRequestsListPage.closeFirstServiceRequestFromTheList();

		serviceRequestsListPage.makeSearchPanelVisible();
		serviceRequestsListPage.setSearchFreeText(newservicerequest);
		serviceRequestsListPage.clickFindButton();
		Assert.assertTrue(serviceRequestsListPage.verifySearchResultsByModelIN(_make, _model, _year, VIN));
		Assert.assertEquals("Closed", serviceRequestsListPage.getFirstServiceRequestStatus());
	}

	/*
	 * @Test(description = "Test Case 18476:Operations- New Inspection") public
	 * void testOperationNewInspection() throws Exception {
	 * 
	 * final String customertype= "Retail"; final String customername=
	 * "Small Pixie";
	 * 
	 * final String VIN = "1FAFP56SX3G732276"; final String servicename =
	 * "AMoneyFlatFee_VacuumCleaner"; final String inspectiondescription =
	 * "test description";
	 * 
	 * 
	 * BackOfficeHeaderPanel backofficeheader =
	 * PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
	 * OperationsWebPage operationspage =
	 * backofficeheader.clickOperationsLink();
	 * 
	 * NewInspectionWebPage newinspectionpage =
	 * operationspage.clickNewInspectionLink();
	 * newinspectionpage.selectCustomerType(customertype);
	 * newinspectionpage.selectCustomer(customername);
	 * newinspectionpage.clickSelectCustomerButton();
	 * newinspectionpage.clickNextButton(); newinspectionpage.clickNextButton();
	 * 
	 * newinspectionpage.setVINAnddecode(VIN);
	 * newinspectionpage.clickNextButton(); newinspectionpage.clickNextButton();
	 * 
	 * newinspectionpage.selectService(servicename);
	 * newinspectionpage.clickNextButton(); newinspectionpage.clickNextButton();
	 * 
	 * newinspectionpage.setInspectionDescription(inspectiondescription); String
	 * inspnumber = newinspectionpage.getNewInspectionNumber();
	 * newinspectionpage.clickSaveInspectionButton(); backofficeheader =
	 * PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
	 * backofficeheader.clickOperationsLink(); InspectionsWebPage
	 * inspectionspage = operationspage.clickInspectionsLink();
	 * inspectionspage.makeSearchPanelVisible();
	 * 
	 * inspectionspage.setInspectionNumberSearchCriteria(inspnumber);
	 * inspectionspage.clickFindButton();
	 * 
	 * inspectionspage.verifySearchResultsByInspNumber(inspnumber);
	 * inspectionspage.deleteFirstInspection(); Assert.assertEquals(0,
	 * inspectionspage.getInspectionsTableRowCount()); }
	 */

//	@Test(testName = "Test Case 24668:Operations: Automatic WO Creation after Inspection Approval (Approval required - ON)", description = "Operations: Automatic WO Creation after Inspection Approval (Approval required - ON)")
	public void testOperationAutomaticWOCreationAfterInspectionApproval_ApprovalRequiredON() throws Exception {

		final String customername = "003 - Test Company";

		final String VIN = "WDDGF4HB2EA917703";
		final String servicename1 = "Wheel repair";
		final String servicename2 = "VPS1";
		final String inspectiontype = "Inspection_for_auto_WO_appr_req";
		final String timeframe = "Year to date";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		NewInspectionWebPage newinspectionpage = operationspage.clickNewInspectionLink();
		newinspectionpage.selectCustomer(customername);
		newinspectionpage.clickSelectCustomerButton();
		newinspectionpage.clickNextButton();
		newinspectionpage.selectInspectionType(inspectiontype);
		newinspectionpage.clickNextButton();
		newinspectionpage.setVINAnddecode(VIN);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectService(servicename1);
		newinspectionpage.selectService(servicename2);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectActionAfterInspectionCreation("Go to Inspections");
		String inspnumber = newinspectionpage.getNewInspectionNumber();
		newinspectionpage.clickSaveInspectionButton();
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();

		inspectionspage.selectSearchType(inspectiontype);
		inspectionspage.selectSearchTimeframe(timeframe);
		inspectionspage.clickFindButton();

		inspectionspage.isInspNumberExists(inspnumber);
		inspectionspage.approveInspectionByNumber(inspnumber);
		Assert.assertTrue(inspectionspage.isInspectionApproved(inspnumber));

		operationspage = backofficeheader.clickOperationsLink();
	}

//	@Test(testName = "Test Case 24670:Operations: Automatic WO Creation after Inspection Approval (Line approval - ON)", description = "Operations: Automatic WO Creation after Inspection Approval (Line approval - ON)")
	public void testOperationAutomaticWOCreationAfterInspectionApproval_LineApprovalON() throws Exception {

		final String customername = "003 - Test Company";

		final String VIN = "5FNRL5H26CB081058";
		final String servicename1 = "Wheel repair";
		final String servicename2 = "VPS1";
		final String inspectiontype = "Inspection_for_auto_WO_line_appr";
		final String timeframe = "Year to date";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		NewInspectionWebPage newinspectionpage = operationspage.clickNewInspectionLink();
		newinspectionpage.selectCustomer(customername);
		newinspectionpage.clickSelectCustomerButton();
		newinspectionpage.clickNextButton();
		newinspectionpage.selectInspectionType(inspectiontype);
		newinspectionpage.clickNextButton();
		newinspectionpage.setVINAnddecode(VIN);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectService(servicename1);
		newinspectionpage.selectService(servicename2);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectActionAfterInspectionCreation("Go to Inspections");
		String inspnumber = newinspectionpage.getNewInspectionNumber();
		newinspectionpage.clickSaveInspectionButton();
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchType(inspectiontype);
		inspectionspage.selectSearchTimeframe(timeframe);
		inspectionspage.clickFindButton();

		inspectionspage.isInspNumberExists(inspnumber);
		inspectionspage.approveInspectionLinebylineApprovalByNumberWithAllServicesApproval(inspnumber);
		Assert.assertTrue(inspectionspage.isInspectionApproved(inspnumber));
		operationspage = backofficeheader.clickOperationsLink();
	}

//	@Test(testName = "Test Case 24671:Operations: Automatic WO Creation after Inspection Approval (Enable Multiselect - ON)", description = "Operations: Automatic WO Creation after Inspection Approval (Enable Multiselect - ON)")
	public void testOperationAutomaticWOCreationAfterInspectionApproval_EnableMultiselectON() throws Exception {

		final String customername = "003 - Test Company";

		final String VIN = "KL5JJ56Z25K502238";
		final String servicename1 = "Wheel repair";
		final String servicename2 = "VPS1";
		final String inspectiontype = "Insp_for_auto_WO_line_appr_multiselect";
		final String timeframe = "Year to date";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		NewInspectionWebPage newinspectionpage = operationspage.clickNewInspectionLink();
		newinspectionpage.selectCustomer(customername);
		newinspectionpage.clickSelectCustomerButton();
		newinspectionpage.clickNextButton();
		newinspectionpage.selectInspectionType(inspectiontype);
		newinspectionpage.clickNextButton();
		newinspectionpage.setVINAnddecode(VIN);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectService(servicename1);
		newinspectionpage.selectService(servicename2);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectActionAfterInspectionCreation("Go to Inspections");
		String inspnumber = newinspectionpage.getNewInspectionNumber();
		newinspectionpage.clickSaveInspectionButton();
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();

		inspectionspage.selectSearchType(inspectiontype);
		inspectionspage.selectSearchTimeframe(timeframe);
		inspectionspage.clickFindButton();

		inspectionspage.isInspNumberExists(inspnumber);
		inspectionspage.approveInspectionByNumbeApproveAll(inspnumber);
		Assert.assertTrue(inspectionspage.isInspectionApproved(inspnumber));

		operationspage = backofficeheader.clickOperationsLink();
	}

//	@Test(testName = "Test Case 24672:Operations: Automatic WO Creation after Inspection Approval (Enable Simple Approval - ON)", description = "Operations: Automatic WO Creation after Inspection Approval (Enable Simple Approval - ON)")
	public void testOperationAutomaticWOCreationAfterInspectionApproval_EnableSimpleApprovalON() throws Exception {

		final String customername = "003 - Test Company";

		final String VIN = "5FNRL5H26CB081058";
		final String servicename1 = "Wheel repair";
		final String servicename2 = "VPS1";
		final String inspectiontype = "Insp_for_auto_WO_line_appr_simple";
		final String timeframe = "Year to date";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		NewInspectionWebPage newinspectionpage = operationspage.clickNewInspectionLink();
		newinspectionpage.selectCustomer(customername);
		newinspectionpage.clickSelectCustomerButton();
		newinspectionpage.clickNextButton();
		newinspectionpage.selectInspectionType(inspectiontype);
		newinspectionpage.clickNextButton();
		newinspectionpage.setVINAnddecode(VIN);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectService(servicename1);
		newinspectionpage.selectService(servicename2);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectActionAfterInspectionCreation("Go to Inspections");
		String inspnumber = newinspectionpage.getNewInspectionNumber();
		newinspectionpage.clickSaveInspectionButton();
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchType(inspectiontype);
		inspectionspage.selectSearchTimeframe(timeframe);
		inspectionspage.clickFindButton();

		inspectionspage.isInspNumberExists(inspnumber);
		inspectionspage.approveInspectionByNumberApproveAndSubmit(inspnumber);
		Assert.assertTrue(inspectionspage.isInspectionApproved(inspnumber));

		operationspage = backofficeheader.clickOperationsLink();
	}

//	@Test(testName = "Test Case 24674:Operations: Automatic WO Creation after Inspection Approval (Approval required - OFF)", description = "Operations: Automatic WO Creation after Inspection Approval (Approval required - OFF)")
	public void testOperationAutomaticWOCreationAfterInspectionApproval_EnableSimpleApprovalOFF() throws Exception {

		final String customername = "003 - Test Company";

		final String VIN = "1G6CD1153K4442903";
		final String servicename1 = "Wheel repair";
		final String servicename2 = "VPS1";
		final String inspectiontype = "Insp_appr_req_OFF";
		final String timeframe = "Year to date";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		NewInspectionWebPage newinspectionpage = operationspage.clickNewInspectionLink();
		newinspectionpage.selectCustomer(customername);
		newinspectionpage.clickSelectCustomerButton();
		newinspectionpage.clickNextButton();
		newinspectionpage.selectInspectionType(inspectiontype);
		newinspectionpage.clickNextButton();
		newinspectionpage.setVINAnddecode(VIN);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectService(servicename1);
		newinspectionpage.selectService(servicename2);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectActionAfterInspectionCreation("Go to Inspections");
		String inspnumber = newinspectionpage.getNewInspectionNumber();
		newinspectionpage.clickSaveInspectionButton();
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchType(inspectiontype);
		inspectionspage.selectSearchTimeframe(timeframe);
		inspectionspage.clickFindButton();

		inspectionspage.isInspNumberExists(inspnumber);

		Assert.assertTrue(inspectionspage.isInspectionApproved(inspnumber));

		operationspage = backofficeheader.clickOperationsLink();
	}

//	@Test(testName = "Test Case 24676:Operations: WO is not created after Inspection Decline (Approval required - ON)", description = "Operations: WO is not created after Inspection Decline (Approval required - ON)")
	public void testOperationWOIsNotCreatedAfterInspectionDecline_ApprovalRequiredON() throws Exception {

		final String customername = "003 - Test Company";

		final String VIN = "1D7HW48K56S011416";
		final String servicename1 = "Wheel repair";
		final String servicename2 = "VPS1";
		final String inspectiontype = "Inspection_for_auto_WO_appr_req";
		final String timeframe = "Year to date";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		NewInspectionWebPage newinspectionpage = operationspage.clickNewInspectionLink();
		newinspectionpage.selectCustomer(customername);
		newinspectionpage.clickSelectCustomerButton();
		newinspectionpage.clickNextButton();
		newinspectionpage.selectInspectionType(inspectiontype);
		newinspectionpage.clickNextButton();
		newinspectionpage.setVINAnddecode(VIN);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectService(servicename1);
		newinspectionpage.selectService(servicename2);
		newinspectionpage.clickNextButton();
		newinspectionpage.clickNextButton();

		newinspectionpage.selectActionAfterInspectionCreation("Go to Inspections");
		String inspnumber = newinspectionpage.getNewInspectionNumber();
		newinspectionpage.clickSaveInspectionButton();
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchType(inspectiontype);
		inspectionspage.selectSearchTimeframe(timeframe);
		inspectionspage.clickFindButton();

		inspectionspage.isInspNumberExists(inspnumber);
		inspectionspage.declineInspectionByNumber(inspnumber);
		Assert.assertFalse(inspectionspage.isInspectionExists(inspnumber));
		backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage wopage = operationspage.clickWorkOrdersLink();
		wopage.makeSearchPanelVisible();
		wopage.setSearchVIN(VIN);
		wopage.clickFindButton();
		wopage.verifySearchResultsByVIN(VIN);
	}

	/*
	 * public void waitForWindow(String title){ FluentWait<WebDriver> wait = new
	 * FluentWait<WebDriver>(webdriver) .withTimeout(30, TimeUnit.SECONDS) //How
	 * long should WebDriver wait for new window .pollingEvery(5,
	 * TimeUnit.SECONDS) //How often should it check if a searched window is
	 * present .ignoring(NoSuchWindowException.class);
	 * 
	 * wait.until(new Open_PopUp(title)); //Here 'title' is an actual title of
	 * the window, we are trying to find and switch to. }
	 */

	/*
	 * private class Open_PopUp implements ExpectedCondition<String> { private
	 * String windowTitle;
	 * 
	 * public Open_PopUp(String windowTitle){ this.windowTitle = windowTitle; }
	 * 
	 * @Override public String apply(WebDriver driver) {
	 * 
	 * for(String windowHandle: driver.getWindowHandles()){
	 * driver.switchTo().window(windowHandle); if
	 * (driver.getTitle().equals(windowTitle)) return driver.getWindowHandle();
	 * }
	 * 
	 * return null; } }
	 */
	
//	@Test(testName = "Test Case 60616:Operations - Work Orders: Search operation")
	public void testWorkOrderSearchOperation() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workOrderPage = operationspage.clickWorkOrdersLink();
		
		Assert.assertTrue(workOrderPage.checkWorkOrdersInfo());
		Assert.assertTrue(workOrderPage.checkWorkOrdersPagination());
		Assert.assertTrue(workOrderPage.checkWorkOrdersSearchFIelds());
		Assert.assertTrue(workOrderPage.checkWorkOrdersSearchResults());
	}

}
