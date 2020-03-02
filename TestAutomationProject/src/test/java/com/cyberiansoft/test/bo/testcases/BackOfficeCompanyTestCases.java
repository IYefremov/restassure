package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOCompanyData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BackOfficeCompanyTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOCompanyData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyUsersSearch(String rowID, String description, JSONObject testData) {

		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		UsersWebPage usersPage = new UsersWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickUsersLink();

		usersPage.verifyTabsAreVisible();
		usersPage.verifyUsersTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), usersPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), usersPage.getGoToPageFieldValue());

		usersPage.setPageSize(data.getPage1());
		Assert.assertEquals(1, usersPage.getUsersTableRowCount());

		String lastpagenumber = usersPage.getLastPageNumber();
		usersPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, usersPage.getGoToPageFieldValue());

		usersPage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), usersPage.getGoToPageFieldValue());

		usersPage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), usersPage.getGoToPageFieldValue());

		usersPage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), usersPage.getGoToPageFieldValue());

		usersPage.setPageSize(data.getPage999());
		Assert.assertEquals(usersPage.MAX_TABLE_ROW_COUNT_VALUE, usersPage.getUsersTableRowCount());

		List<String> usernamesact = usersPage.getActiveUserNames();
		usersPage.clickArchivedTab();
		Assert.assertEquals(data.getEmptyString(), usersPage.verifyUserNamesDuplicatesArchived(usernamesact));
		usersPage.clickActiveTab();

		usersPage.makeSearchPanelVisible();
		usersPage.setSearchUserParameter(data.getUserFirstName());
		usersPage.clickFindButton();
		usersPage.archiveUser(data.getUserFirstName(), data.getUserLastName());
		usersPage.clickArchivedTab();
		Assert.assertTrue(usersPage.isUserArchived(data.getUserFirstName(), data.getUserLastName()));
		usersPage.unarchiveUser(data.getUserFirstName(), data.getUserLastName());
		usersPage.clickActiveTab();
		Assert.assertTrue(usersPage.isUserActive(data.getUserFirstName(), data.getUserLastName()));

		usersPage.makeSearchPanelVisible();
		usersPage.setSearchUserParameter(data.getUserFirstName().substring(0, 4));
		usersPage.clickFindButton();

		Assert.assertTrue(usersPage.getUsersTableRowCount() > 0);
		usersPage.isUserActive(data.getUserFirstName(), data.getUserLastName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyEmployeesSearch(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		EmployeesWebPage employeesPage = new EmployeesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickEmployeesLink();

		employeesPage.verifyTabsAreVisible();
		employeesPage.verifyEmployeesTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), employeesPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), employeesPage.getGoToPageFieldValue());

		employeesPage.setPageSize(data.getPage1());
		Assert.assertEquals(1, employeesPage.getEmployeesTableRowCount());

		String lastpagenumber = employeesPage.getLastPageNumber();
		employeesPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, employeesPage.getGoToPageFieldValue());

		employeesPage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), employeesPage.getGoToPageFieldValue());

		employeesPage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), employeesPage.getGoToPageFieldValue());

		employeesPage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), employeesPage.getGoToPageFieldValue());

		employeesPage.setPageSize(data.getPage999());
		Assert.assertEquals(employeesPage.MAX_TABLE_ROW_COUNT_VALUE, employeesPage.getEmployeesTableRowCount());

		employeesPage.verifyActiveEmployeeDoesNotExist(data.getEmployeeName());

		employeesPage.archiveEmployee(data.getEmployeeName());
		employeesPage.clickArchivedTab();
		employeesPage.makeSearchPanelVisible();
		employeesPage.setSearchUserParameter(data.getEmployeeLastName());
		employeesPage.clickFindButton();
		Assert.assertTrue(employeesPage.archivedEmployeeExists(data.getEmployeeName()));
		employeesPage.unarchiveEmployee(data.getEmployeeName());
		employeesPage.clickActiveTab();
		Assert.assertTrue(employeesPage.activeEmployeeExists(data.getEmployeeName()));

		employeesPage.makeSearchPanelVisible();
		employeesPage.selectSearchTeam(data.getTeamName());
		employeesPage.setSearchUserParameter(data.getEmployeeName().substring(0, 4).toLowerCase());
		employeesPage.clickFindButton();

		Assert.assertTrue(employeesPage.getEmployeesTableRowCount() > 0);
		employeesPage.activeEmployeeExists(data.getEmployeeName());
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServicesSearch(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		ServicesWebPage servicesPage = new ServicesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickServicesLink();

		servicesPage.verifyServicesTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), servicesPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), servicesPage.getGoToPageFieldValue());

		servicesPage.setPageSize(data.getPage1());
		Assert.assertEquals(1, servicesPage.getServicesTableRowsCount());

		String lastpagenumber = servicesPage.getLastPageNumber();
		servicesPage.clickGoToLastPage();
        Assert.assertEquals(lastpagenumber, servicesPage.getPageFieldValue());

		servicesPage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), servicesPage.getGoToPageFieldValue());

		servicesPage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), servicesPage.getGoToPageFieldValue());

		servicesPage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), servicesPage.getGoToPageFieldValue());

		servicesPage.setPageSize(data.getPage999());
		Assert.assertEquals(50, servicesPage.getServicesTableRowsCount());

//		servicesPage.makeSearchPanelVisible();
		servicesPage.selectSearchServiceType(data.getServiceType());
		servicesPage.selectSearchPriceType(data.getPriceType());
		servicesPage.setServiceSearchCriteria(data.getServiceName().substring(0, 4).toLowerCase());
		servicesPage.clickFindButton();

		servicesPage.activeServiceExists(data.getServiceName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyTeamsSearch(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		TeamsWebPage teamsPage = new TeamsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickTeamsLink();

		teamsPage.verifyTeamsTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), teamsPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), teamsPage.getGoToPageFieldValue());

		teamsPage.setPageSize(data.getPage1());
		Assert.assertEquals(1, teamsPage.getTeamsTableRowsCount());

		String lastpagenumber = teamsPage.getLastPageNumber();
		teamsPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, teamsPage.getGoToPageFieldValue());

		teamsPage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), teamsPage.getGoToPageFieldValue());

		teamsPage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), teamsPage.getGoToPageFieldValue());

		teamsPage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), teamsPage.getGoToPageFieldValue());

		if (Integer.parseInt(lastpagenumber) < 50) {
			teamsPage.setPageSize(lastpagenumber);
			Assert.assertEquals(Integer.valueOf(lastpagenumber), Integer.valueOf(teamsPage.getTeamsTableRowsCount()));
		} else {
			teamsPage.setPageSize(data.getPage999());
			Assert.assertEquals(teamsPage.MAX_TABLE_ROW_COUNT_VALUE, teamsPage.getTeamsTableRowsCount());
		}

		teamsPage.makeSearchPanelVisible();
		teamsPage.setTeamLocationSearchCriteria(data.getTeamLocation().substring(0, 8).toLowerCase());
		teamsPage.selectSearchType(data.getType());
		teamsPage.selectSearchTimeZone(data.getTimeZone());
		teamsPage.clickFindButton();

		Assert.assertTrue(teamsPage.getTeamsTableRowsCount() >= 1);
		teamsPage.verifySearchResultsByTeamLocation(data.getTeamLocation());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyJobsSearch(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		JobsWebPage jobsPage = new JobsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickJobsLink();

		jobsPage.verifyJobsTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), jobsPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), jobsPage.getGoToPageFieldValue());

		jobsPage.setPageSize(data.getPage1());
		Assert.assertEquals(1, jobsPage.getJobsTableRowsCount());

		String lastpagenumber = jobsPage.getLastPageNumber();
		jobsPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, jobsPage.getGoToPageFieldValue());

		jobsPage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), jobsPage.getGoToPageFieldValue());

		jobsPage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), jobsPage.getGoToPageFieldValue());

		jobsPage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), jobsPage.getGoToPageFieldValue());
		if (Integer.parseInt(lastpagenumber) < 50) {
			jobsPage.setPageSize(lastpagenumber);
			Assert.assertEquals(Integer.valueOf(lastpagenumber), Integer.valueOf(jobsPage.getJobsTableRowsCount()));
		} else {
			jobsPage.setPageSize(data.getPage999());
			Assert.assertEquals(jobsPage.MAX_TABLE_ROW_COUNT_VALUE, jobsPage.getJobsTableRowsCount());
		}

		jobsPage.makeSearchPanelVisible();
		jobsPage.setJobSearchCriteria(data.getJob().substring(0, 2).toLowerCase());
		jobsPage.selectSearchCustomer(data.getCustomer());
		jobsPage.clickFindButton();

		Assert.assertEquals(1, jobsPage.getJobsTableRowsCount());
		jobsPage.isJobPresent(data.getJob());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyJobsInsuranceCompanies(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		InsuranceCompaniesWebPage insuranceCompaniesPage = new InsuranceCompaniesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickInsuranceCompaniesLink();

		insuranceCompaniesPage.verifyInsuranceCompaniesTableColumnsAreVisible();
		Assert.assertTrue(insuranceCompaniesPage.addInsuranceCompanyButtonExists());

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServiceAdvisors(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		ServiceAdvisorsWebPage serviceadvisorspage = new ServiceAdvisorsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickServiceAdvisorsLink();


		serviceadvisorspage.verifyServiceAdvisorsTableColumnsAreVisible();

		Assert.assertEquals(data.getPage1(), serviceadvisorspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals(data.getPage1(), serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.setPageSize(data.getPage1());
		Assert.assertEquals(1, serviceadvisorspage.getServiceAdvisorsTableRowsCount());

		String lastpagenumber = serviceadvisorspage.getLastPageNumber();
		serviceadvisorspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.clickGoToFirstPage();
		Assert.assertEquals(data.getPage1(), serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.clickGoToNextPage();
		Assert.assertEquals(data.getPage2(), serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.clickGoToPreviousPage();
		Assert.assertEquals(data.getPage1(), serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.setPageSize(data.getPage999());
		Assert.assertEquals(serviceadvisorspage.MAX_TABLE_ROW_COUNT_VALUE, serviceadvisorspage.getServiceAdvisorsTableRowsCount());


		serviceadvisorspage.makeSearchPanelVisible();
		serviceadvisorspage.setUserSearchCriteria(data.getUserFirstName());
		serviceadvisorspage.selectSearchClient(data.getClient());
		serviceadvisorspage.clickFindButton();

		Assert.assertTrue(serviceadvisorspage.getServiceAdvisorsTableRowsCount() > 0);
		serviceadvisorspage.serviceAdvisorExists(data.getUserFirstName(), data.getUserLastName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyQuestionForms(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		QuestionsFormsWebPage questionsFormsWebPage = new QuestionsFormsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickQuestionsFormsLink();

		questionsFormsWebPage.verifyQuestionFormsTableColumnsAreVisible();
		questionsFormsWebPage.verifyQuestionSectionsTableColumnsAreVisible();
		questionsFormsWebPage.verifyPrintTemplatesTableColumnsAreVisible();
		questionsFormsWebPage.createQuestionSection(data.getQuestionSectionName());
		questionsFormsWebPage.addQuestionForQuestionSection(data.getQuestionSectionName(), data.getQuestionName());

		while (questionsFormsWebPage.isQuestionFormExists(data.getQuestionFormName())) {
			questionsFormsWebPage.deleteQuestionForm(data.getQuestionFormName());
		}
		questionsFormsWebPage.createQuestionForm(data.getQuestionFormName());
		questionsFormsWebPage.editAndAssignSectionToQuestionForm(data.getQuestionFormName(), data.getQuestionSectionName());
		questionsFormsWebPage.verifyQuestionIsAssignedToQuestionFormViaPreview(data.getQuestionFormName(), data.getQuestionName());

		questionsFormsWebPage.deleteQuestionForm(data.getQuestionFormName());
		Assert.assertFalse(questionsFormsWebPage.isQuestionFormExists(data.getQuestionFormName()));
		questionsFormsWebPage.deleteQuestionSections(data.getQuestionSectionName());
		Assert.assertFalse(questionsFormsWebPage.isQuestionSectionExists(data.getQuestionSectionName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySupplies(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		SuppliesWebPage suppliesPage = new SuppliesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickSuppliesLink();

		suppliesPage.verifySuppliesTableColumnsAreVisible();
		suppliesPage.createNewSupply(data.getSupplyName());
		suppliesPage.setSupplyNewName(data.getSupplyName(), data.getSupplyNameEdited());
		suppliesPage.deleteSupply(data.getSupplyNameEdited());
		Assert.assertFalse(suppliesPage.isSupplyExists(data.getSupplyNameEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExpensesTypes(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		ExpensesTypesWebPage expensesTypesPage = new ExpensesTypesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickExpensesTypesLink();

		expensesTypesPage.verifyExpensesTypesColumnsAreVisible();
		expensesTypesPage.createNewExpenseType(data.getExpenseType());
		expensesTypesPage.setExpenseTypeNewName(data.getExpenseType(), data.getNewExpenseTypeName());
		expensesTypesPage.deleteExpenseType(data.getNewExpenseTypeName());
		Assert.assertFalse(expensesTypesPage.isExpenseTypeExists(data.getNewExpenseTypeName()));
		if (expensesTypesPage.isExpenseTypeExists(data.getExpenseType())) {
			expensesTypesPage.deleteExpenseType(data.getExpenseType());
			Assert.assertFalse(expensesTypesPage.isExpenseTypeExists(data.getExpenseType()));
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyVehicleParts(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		VehiclePartsWebPage vehiclePartsPage = new VehiclePartsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickVehiclePartsLink();

		vehiclePartsPage.verifyVehiclePartsColumnsAreVisible();
		vehiclePartsPage.createNewVehicleParWithAllServicesSelected(data.getVehiclePart());
		vehiclePartsPage.setVehiclePartNewName(data.getVehiclePart(), data.getNewVehiclePartName());
		vehiclePartsPage.deleteVehiclePart(data.getNewVehiclePartName());
		Assert.assertFalse(vehiclePartsPage.isVehiclePartExists(data.getNewVehiclePartName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyEmployeesArchive(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		EmployeesWebPage employeesPage = new EmployeesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickEmployeesLink();

		employeesPage.makeSearchPanelVisible();
		employeesPage.setSearchUserParameter(data.getEmployeeFirstName().substring(0, 5));
		employeesPage.clickFindButton();
		employeesPage.verifyEmployeeIsActive(data.getEmployeeName());
		for (int i = 0; i < 3; i++) {
			employeesPage.archiveEmployee(data.getEmployeeName());
			employeesPage.clickArchivedTab();

			employeesPage.setSearchUserParameter(data.getEmployeeFirstName().substring(0, 5));
			employeesPage.clickFindButton();
			Assert.assertTrue(employeesPage.archivedEmployeeExists(data.getEmployeeName()));
			employeesPage.unarchiveEmployee(data.getEmployeeName());
			employeesPage.clickActiveTab();

			employeesPage.setSearchUserParameter(data.getEmployeeName().substring(0, 5));
			employeesPage.clickFindButton();
			Assert.assertTrue(employeesPage.activeEmployeeExists(data.getEmployeeName()));
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServicesArchive(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		ServicesWebPage servicesPage = new ServicesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickServicesLink();

//		servicesPage.makeSearchPanelVisible();
		servicesPage.setServiceSearchCriteria(data.getServiceName());
		servicesPage.clickFindButton();

		for (int i = 0; i < 3; i++) {
			servicesPage.archiveServiceForActiveAllTab(data.getServiceName());
			servicesPage.clickArchivedTab();
			Assert.assertTrue(servicesPage.archivedServiceExists(data.getServiceName()));
			servicesPage.unarchiveService(data.getServiceName());

			servicesPage.clickActiveAllTab();
			Assert.assertTrue(servicesPage.activeServiceExists(data.getServiceName()));
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyUsersArchive(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		UsersWebPage usersPage = new UsersWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickUsersLink();

		usersPage.makeSearchPanelVisible();
		usersPage.setSearchUserParameter(data.getUserLastName());
		usersPage.clickFindButton();
		if (!usersPage.isUserActive(data.getUserFirstName(), data.getUserLastName())) {
			usersPage.clickArchivedTab();
			usersPage.unarchiveUser(data.getUserFirstName(), data.getUserLastName());
		}

		for (int i = 0; i < 3; i++) {
			usersPage.archiveUser(data.getUserFirstName(), data.getUserLastName());
			usersPage.clickArchivedTab();
			Assert.assertTrue(usersPage.isUserArchived(data.getUserFirstName(), data.getUserLastName()));
			usersPage.unarchiveUser(data.getUserFirstName(), data.getUserLastName());

			usersPage.clickActiveTab();
			Assert.assertTrue(usersPage.isUserActive(data.getUserFirstName(), data.getUserLastName()));
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDependencyBetweenVisibleAndRequiredOptions(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		InvoiceTypesWebPage invoicestypespage = new InvoiceTypesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickInvoiceTypesLink();

		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicestypespage.clickAddInvoiceTypeButton();
		Assert.assertFalse(newinvoicetypedialog.isRequiredCheckBoxVisible());
		newinvoicetypedialog.selectVisibleCheckBox();
		Assert.assertTrue(newinvoicetypedialog.isRequiredCheckBoxVisible());
		newinvoicetypedialog.unselectVisibleCheckBox();
		Assert.assertFalse(newinvoicetypedialog.isRequiredCheckBoxVisible());
		newinvoicetypedialog.clickCancelAddInvoiceTypeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAdminCanSeeOnlyServicesSelectedOnVehicleParts(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		VehiclePartsWebPage vehiclePartsPage = new VehiclePartsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickVehiclePartsLink();

		vehiclePartsPage.clickEditButtonForVehiclePart(data.getVehiclePart());
		Assert.assertEquals(data.getVehiclePart(), vehiclePartsPage.getVehiclePartNameField().getAttribute("value"));

		vehiclePartsPage.verifyThatAssignedServicesListIsEmpty();
		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		PriceMatricesWebPage pricematriceswebpage = new PriceMatricesWebPage(webdriver);
		companyWebPage.clickPriceMatricesLink();
		pricematriceswebpage.clickPricesForPriceMatrix(data.getPriceMatrix());
		vehiclePartsPage = pricematriceswebpage.clickPricesVehiclePartLink(data.getVehiclePart());
		Assert.assertEquals(0, vehiclePartsPage.getAssignedServicesList().size());
		vehiclePartsPage.cancelNewVehiclePart();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		vehiclePartsPage = new VehiclePartsWebPage(webdriver);
		companyWebPage.clickVehiclePartsLink();
		vehiclePartsPage.clickEditButtonForVehiclePart(data.getVehiclePart());
		data.getServiceNames().forEach(vehiclePartsPage::assignServiceForVehiclePart);
		vehiclePartsPage.saveNewVehiclePart();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		pricematriceswebpage = new PriceMatricesWebPage(webdriver);
		companyWebPage.clickPriceMatricesLink();
		pricematriceswebpage.clickPricesForPriceMatrix(data.getPriceMatrix());
		vehiclePartsPage = pricematriceswebpage.clickPricesVehiclePartLink(data.getVehiclePart());
		Assert.assertEquals(2, vehiclePartsPage.getAvailableServicesList().size());
		data.getServiceNames().forEach(vehiclePartsPage::selectAvailableServiceForVehiclePart);
		vehiclePartsPage.cancelNewVehiclePart();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		vehiclePartsPage = new VehiclePartsWebPage(webdriver);
		companyWebPage.clickVehiclePartsLink();
		vehiclePartsPage.clickEditButtonForVehiclePart(data.getVehiclePart());
		data.getServiceNames().forEach(vehiclePartsPage::unassignServiceForVehiclePart);
		vehiclePartsPage.saveNewVehiclePart();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAllSelectedServicesOnVehiclePartWillBeAssignedToMatrixPanel(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		VehiclePartsWebPage vehiclePartsPage = new VehiclePartsWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickVehiclePartsLink();

		vehiclePartsPage.clickEditButtonForVehiclePart(data.getVehiclePart());
		Assert.assertEquals(data.getVehiclePart(), vehiclePartsPage.getVehiclePartNameField().getAttribute("value"));
		data.getServiceNames().forEach(vehiclePartsPage::selectAssignedServiceForVehiclePart);
		vehiclePartsPage.saveNewVehiclePart();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		PriceMatricesWebPage pricematriceswebpage = new PriceMatricesWebPage(webdriver);
		companyWebPage.clickPriceMatricesLink();
		pricematriceswebpage.clickAddPriceMarixButton();
		pricematriceswebpage.setPriceMarixName(data.getPriceMatrix());
		pricematriceswebpage.selectPriceMatrixService(data.getPriceMatrixService());
		data.getDamageSeverities().forEach(pricematriceswebpage::assignPriceMatrixDamageSeverity);
		data.getDamageSizes().forEach(pricematriceswebpage::assignPriceMatrixDamageSize);
		pricematriceswebpage.assignPriceMatrixVehiclePart(data.getPriceMatrixVehiclePart());
		pricematriceswebpage.saveNewPriceMatrix();

		pricematriceswebpage.clickPricesForPriceMatrix(data.getPriceMatrix());
		vehiclePartsPage = pricematriceswebpage.clickPricesVehiclePartLink(data.getVehiclePart());
		data.getServiceNames().forEach(vehiclePartsPage::selectAssignedServiceForVehiclePart);

		data.getServiceNames().forEach(vehiclePartsPage::selectAssignedServiceForVehiclePart);
		vehiclePartsPage.verifyThatAvailableServicesListIsEmpty();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		pricematriceswebpage = new PriceMatricesWebPage(webdriver);
		companyWebPage.clickPriceMatricesLink();
		pricematriceswebpage.deletePriceMatrix(data.getPriceMatrix());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testReassignEmployeeForTeam(String rowID, String description, JSONObject testData) {

		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		EmployeesWebPage employeesPage = new EmployeesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickEmployeesLink();
		employeesPage.makeSearchPanelVisible();
		employeesPage.setSearchUserParameter(data.getEmployeeName());
		employeesPage.clickFindButton();
		employeesPage.verifyEmployeesTableColumnsAreVisible();
		employeesPage.verifyEmployeeIsActive(data.getEmployeeName());

		NewEmployeeDialogWebPage newEmployeeDialog = new NewEmployeeDialogWebPage(webdriver);
		employeesPage.clickEditEmployeeFromTeam(data.getEmployeeName(), data.getTeamName());
		InfoContentDialogWebPage infoContentDialog = new InfoContentDialogWebPage(webdriver);
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.verifyInfoContentDialogIsDisplayed(),
				"The Info Content Dialog has not been opened");
		infoContentDialog.chooseEmployeeToReassign(data.getEmployeeName2());
		infoContentDialog.reassignEmployee();
		newEmployeeDialog.selectNewEmployeeTeam(data.getTeamName2());
		newEmployeeDialog.clickOKButton();

		employeesPage.clickEditEmployee(data.getEmployeeName());
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.verifyInfoContentDialogIsDisplayed(),
				"The Info Content Dialog has not been opened for the rollback of employee's data");
		infoContentDialog.chooseEmployeeToReassign(data.getEmployeeName());
		infoContentDialog.reassignEmployee();
		newEmployeeDialog.selectNewEmployeeTeam(data.getTeamName());
		newEmployeeDialog.clickOKButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testReassignEmployeeForSingleUserInTeamDisabled(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		EmployeesWebPage employeesPage = new EmployeesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickEmployeesLink();
		employeesPage.makeSearchPanelVisible();
		employeesPage.selectSearchTeam(data.getTeamName());
		employeesPage.setSearchUserParameter(data.getSearchEmployee());
		employeesPage.clickFindButton();
		employeesPage.verifyEmployeesTableColumnsAreVisible();
		employeesPage.verifyEmployeeIsActive(data.getEmployeeFullName());
		employeesPage.archiveEmployee(data.getEmployeeFullName());
		employeesPage.setSearchUserParameter(data.getEmployeeName());
		employeesPage.clickFindButton();

		NewEmployeeDialogWebPage newEmployeeDialog = new NewEmployeeDialogWebPage(webdriver);
		employeesPage.clickEditEmployee(data.getEmployeeName());
		InfoContentDialogWebPage infoContentDialog = new InfoContentDialogWebPage(webdriver);
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.isEmployeeListDisabled(), "The employees list is not disabled");
		Assert.assertTrue(infoContentDialog.isReassignButtonDisabled(), "The \"Reassign\" button is not disabled");
		newEmployeeDialog.clickCancelButton();
		employeesPage.setSearchUserParameter(data.getSearchEmployee());
		employeesPage.clickFindButton();
		employeesPage.clickArchivedTab();
		employeesPage.unarchiveEmployee(data.getEmployeeFullName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testReassignEmployeeVerifyText(String rowID, String description, JSONObject testData) {
		BOCompanyData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		EmployeesWebPage employeesPage = new EmployeesWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickEmployeesLink();
		employeesPage.makeSearchPanelVisible();
		employeesPage.selectSearchTeam(data.getTeamName());
		employeesPage.setSearchUserParameter(data.getSearchEmployee());
		employeesPage.clickFindButton();
		employeesPage.verifyEmployeesTableColumnsAreVisible();
		employeesPage.verifyEmployeeIsActive(data.getEmployeeFullName());

		NewEmployeeDialogWebPage newEmployeeDialog = new NewEmployeeDialogWebPage(webdriver);
		employeesPage.clickEditEmployee(data.getEmployeeFullName());
		InfoContentDialogWebPage infoContentDialog = new InfoContentDialogWebPage(webdriver);
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.isTopBubbleInfoDisplayedWithReassign(data.getTopBubbleInfoWithReassign()),
				"The notification is not displayed at the top of the popup window with reassign");
		newEmployeeDialog.clickCancelButton();

		employeesPage.archiveEmployee(data.getEmployeeFullName());
		employeesPage.selectSearchTeam(data.getTeamName());
		employeesPage.setSearchUserParameter(data.getEmployeeName());
		employeesPage.clickFindButton();
		employeesPage.clickEditEmployee(data.getEmployeeName());
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.isTopBubbleInfoDisplayed(data.getTopBubbleInfo()),
				"The notification is not displayed at the top of the popup window");
		Assert.assertTrue(infoContentDialog.isEmployeeListDisabled(), "The employees list is not disabled");
		Assert.assertTrue(infoContentDialog.isReassignButtonDisabled(), "The \"Reassign\" button is not disabled");
		newEmployeeDialog.clickCancelButton();

		employeesPage.selectSearchTeam(data.getTeamName());
		employeesPage.setSearchUserParameter(data.getSearchEmployee());
		employeesPage.clickFindButton();
		employeesPage.clickArchivedTab();
		employeesPage.unarchiveEmployee(data.getEmployeeFullName());
		employeesPage.selectSearchTeam(data.getTeamName());
		employeesPage.setSearchUserParameter(data.getEmployeeName());
		employeesPage.clickFindButton();
		employeesPage.clickActiveTab();
		employeesPage.clickEditEmployee(data.getEmployeeName());
		newEmployeeDialog.clickInfoBubble();
		Assert.assertTrue(infoContentDialog.isTopBubbleInfoDisplayedWithReassign(data.getTopBubbleInfoWithReassign()),
				"The notification is not displayed at the top of the popup window with reassign");
		Assert.assertFalse(infoContentDialog.isEmployeeListDisabled(), "The employees list is disabled");
		Assert.assertFalse(infoContentDialog.isReassignButtonDisabled(), "The \"Reassign\" button is disabled");
		newEmployeeDialog.clickCancelButton();
	}
}