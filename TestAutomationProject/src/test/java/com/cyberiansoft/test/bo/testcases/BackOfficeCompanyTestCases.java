package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class BackOfficeCompanyTestCases extends BaseTestCase {

	@Test(description = "Test Case 15245:Company-Users: Search")
	public void testCompanyUsersSearch() throws Exception {

		final String userFirstName = "Delete";
		final String userLastName = "Test";

		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companyPage = backofficeHeader.clickCompanyLink();

		UsersWebPage usersPage = companyPage.clickUsersLink();

		usersPage.verifyTabsAreVisible();
		usersPage.verifyUsersTableColumnsAreVisible();

		Assert.assertEquals("1", usersPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", usersPage.getGoToPageFieldValue());

		usersPage.setPageSize("1");
		Assert.assertEquals(1, usersPage.getUsersTableRowCount());

		String lastpagenumber = usersPage.getLastPageNumber();
		usersPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, usersPage.getGoToPageFieldValue());

		usersPage.clickGoToFirstPage();
		Thread.sleep(1000);
		Assert.assertEquals("1", usersPage.getGoToPageFieldValue());

		usersPage.clickGoToNextPage();
		Assert.assertEquals("2", usersPage.getGoToPageFieldValue());

		usersPage.clickGoToPreviousPage();
		Assert.assertEquals("1", usersPage.getGoToPageFieldValue());

		usersPage.setPageSize("999");
		Assert.assertEquals(usersPage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(usersPage.getUsersTableRowCount()));

		List<String> usernamesact = usersPage.getActiveUserNames();
		usersPage.clickArchivedTab();
		Assert.assertEquals("", usersPage.verifyUserNamesDuplicatesArchived(usernamesact));
		usersPage.clickActiveTab();

		usersPage.makeSearchPanelVisible();
		usersPage.setSearchUserParameter(userFirstName);
		usersPage.clickFindButton();
		usersPage.archiveUser(userFirstName, userLastName);
		usersPage.clickArchivedTab();
		Assert.assertTrue(usersPage.isUserArchived(userFirstName, userLastName));
		usersPage.unarchiveUser(userFirstName, userLastName);
		usersPage.clickActiveTab();
		Assert.assertTrue(usersPage.isUserActive(userFirstName, userLastName));

		usersPage.makeSearchPanelVisible();
		usersPage.setSearchUserParameter(userFirstName.substring(0, 4));
		usersPage.clickFindButton();

		Assert.assertTrue(Integer.valueOf(usersPage.getUsersTableRowCount()) > 0);
		usersPage.isUserActive(userFirstName, userLastName);
	}

	@Test(description = "Test Case 15265:Company-Employees: Search")
	public void testCompanyEmployeesSearch() throws Exception {

		final String employeefirstname = "+++";
		final String employeelastname = "---";
		final String team = "Test Team";
		final String employeename = employeefirstname + " " + employeelastname;
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		EmployeesWebPage employeespage = companypage.clickEmployeesLink();

		employeespage.verifyTabsAreVisible();
		employeespage.verifyEmployeesTableColumnsAreVisible();

		Assert.assertEquals("1", employeespage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", employeespage.getGoToPageFieldValue());

		employeespage.setPageSize("1");
		Assert.assertEquals(1, employeespage.getEmployeesTableRowCount());

		String lastpagenumber = employeespage.getLastPageNumber();
		employeespage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, employeespage.getGoToPageFieldValue());

		employeespage.clickGoToFirstPage();
		Thread.sleep(1000);
		Assert.assertEquals("1", employeespage.getGoToPageFieldValue());

		employeespage.clickGoToNextPage();
		Assert.assertEquals("2", employeespage.getGoToPageFieldValue());

		employeespage.clickGoToPreviousPage();
		Assert.assertEquals("1", employeespage.getGoToPageFieldValue());

		employeespage.setPageSize("999");
		Assert.assertEquals(employeespage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(employeespage.getEmployeesTableRowCount()));

		employeespage.archiveEmployee(employeefirstname, employeelastname);
		employeespage.clickArchivedTab();
		employeespage.makeSearchPanelVisible();
		employeespage.setSearchUserParameter(employeelastname);
		employeespage.clickFindButton();
		Assert.assertTrue(employeespage.archivedEmployeeExists(employeefirstname, employeelastname));
		employeespage.unarchiveEmployee(employeefirstname, employeelastname);
		employeespage.clickActiveTab();
		Assert.assertTrue(employeespage.activeEmployeeExists(employeefirstname, employeelastname));

		employeespage.makeSearchPanelVisible();
		employeespage.selectSearchTeam(team);
		employeespage.setSearchUserParameter(employeename.substring(0, 4).toLowerCase());
		employeespage.clickFindButton();

		Assert.assertTrue(employeespage.getEmployeesTableRowCount() > 0);
		employeespage.activeEmployeeExists(employeefirstname, employeelastname);
	}


	@Test(description = "Test Case 15323:Company- Services: Search")
	public void testCompanyServicesSearch() throws Exception {

		final String pricetype = "Percentage";
		final String servicetype = "Detail";
		final String servicename = "Marg_For_Bundle";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ServicesWebPage servicespage = companypage.clickServicesLink();

		servicespage.verifyServicesTableColumnsAreVisible();

		Assert.assertEquals("1", servicespage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", servicespage.getGoToPageFieldValue());

		servicespage.setPageSize("1");
		Assert.assertEquals(1, servicespage.getServicesTableRowsCount());

		String lastpagenumber = servicespage.getLastPageNumber();
		servicespage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, servicespage.getGoToPageFieldValue());

		servicespage.clickGoToFirstPage();
		Thread.sleep(1000);
		Assert.assertEquals("1", servicespage.getGoToPageFieldValue());

		servicespage.clickGoToNextPage();
		Assert.assertEquals("2", servicespage.getGoToPageFieldValue());

		servicespage.clickGoToPreviousPage();
		Assert.assertEquals("1", servicespage.getGoToPageFieldValue());

		servicespage.setPageSize("999");
		Assert.assertEquals(new Integer(50), Integer.valueOf(servicespage.getServicesTableRowsCount()));

//		servicespage.makeSearchPanelVisible();
		servicespage.selectSearchServiceType(servicetype);
		servicespage.selectSearchPriceType(pricetype);
		servicespage.setServiceSearchCriteria(servicename.substring(0, 4).toLowerCase());
		servicespage.clickFindButton();

		servicespage.activeServiceExists(servicename);
	}

	@Test(description = "Test Case 15539:Company - Teams: Search")
	public void testCompanyTeamsSearch() throws Exception {

		final String teamlocation = "Test Team";
		final String _type = "Internal";
		final String timezone = "Pacific Standard Time";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		TeamsWebPage teamspage = companypage.clickTeamsLink();

		teamspage.verifyTeamsTableColumnsAreVisible();

		Assert.assertEquals("1", teamspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", teamspage.getGoToPageFieldValue());

		teamspage.setPageSize("1");
		Assert.assertEquals(1, teamspage.getTeamsTableRowsCount());

		String lastpagenumber = teamspage.getLastPageNumber();
		teamspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, teamspage.getGoToPageFieldValue());

		teamspage.clickGoToFirstPage();
		Assert.assertEquals("1", teamspage.getGoToPageFieldValue());

		teamspage.clickGoToNextPage();
		Assert.assertEquals("2", teamspage.getGoToPageFieldValue());

		teamspage.clickGoToPreviousPage();
		Assert.assertEquals("1", teamspage.getGoToPageFieldValue());

		if (Integer.valueOf(lastpagenumber) < 50) {
			teamspage.setPageSize(lastpagenumber);
			Assert.assertEquals(Integer.valueOf(lastpagenumber), Integer.valueOf(teamspage.getTeamsTableRowsCount()));
		} else {
			teamspage.setPageSize("999");
			Assert.assertEquals(teamspage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(teamspage.getTeamsTableRowsCount()));
		}

		teamspage.makeSearchPanelVisible();
		teamspage.setTeamLocationSearchCriteria(teamlocation.substring(0, 8).toLowerCase());
		teamspage.selectSearchType(_type);
		teamspage.selectSearchTimeZone(timezone);
		teamspage.clickFindButton();

		Assert.assertTrue(Integer.valueOf(teamspage.getTeamsTableRowsCount()) >= 1);
		teamspage.verifySearchResultsByTeamLocation(teamlocation);
	}

	@Test(description = "Test Case 15541:Company - Jobs: Search")
	public void testCompanyJobsSearch() throws Exception {

		final String customer = "001 - Test Company";
		final String _job = "Alex2";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		JobsWebPage jobspage = companypage.clickJobsLink();

		jobspage.verifyJobsTableColumnsAreVisible();

		Assert.assertEquals("1", jobspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", jobspage.getGoToPageFieldValue());

		jobspage.setPageSize("1");
		Assert.assertEquals(1, jobspage.getJobsTableRowsCount());

		String lastpagenumber = jobspage.getLastPageNumber();
		jobspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, jobspage.getGoToPageFieldValue());

		jobspage.clickGoToFirstPage();
		Assert.assertEquals("1", jobspage.getGoToPageFieldValue());

		jobspage.clickGoToNextPage();
		Assert.assertEquals("2", jobspage.getGoToPageFieldValue());

		jobspage.clickGoToPreviousPage();
		Assert.assertEquals("1", jobspage.getGoToPageFieldValue());
		if (Integer.valueOf(lastpagenumber) < 50) {
			jobspage.setPageSize(lastpagenumber);
			Assert.assertEquals(Integer.valueOf(lastpagenumber), Integer.valueOf(jobspage.getJobsTableRowsCount()));
		} else {
			jobspage.setPageSize("999");
			Assert.assertEquals(jobspage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(jobspage.getJobsTableRowsCount()));
		}

		jobspage.makeSearchPanelVisible();
		jobspage.setJobSearchCriteria(_job.substring(0, 2).toLowerCase());
		jobspage.selectSearchCustomer(customer);
		jobspage.clickFindButton();

		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(jobspage.getJobsTableRowsCount()));
		jobspage.isJobPresent(_job);
	}

	@Test(description = "Test Case 17284:Company - Insurance Companies")
	public void testCompanyJobsInsuranceCompanies() throws Exception {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		InsuranceCompaniesWePpage insurancecompaniespage = companypage.clickInsuranceCompaniesLink();

		insurancecompaniespage.verifyInsuranceCompaniesTableColumnsAreVisible();
		Assert.assertTrue(insurancecompaniespage.isAddInsuranceCompanyButtonExists());

	}

	@Test(description = "Test Case 18092:Company -Service Advisors")
	public void testCompanyServiceAdvisors() throws Exception {

		final String _client = "Sterling Collision";
		final String firstname = "Cameron";
		final String lastname = "Minor";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ServiceAdvisorsWebPage serviceadvisorspage = companypage.clickServiceAdvisorsLink();

		serviceadvisorspage.verifyServiceAdvisorsTableColumnsAreVisible();

		Assert.assertEquals("1", serviceadvisorspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.setPageSize("1");
		Assert.assertEquals(1, serviceadvisorspage.getServiceAdvisorsTableRowsCount());

		String lastpagenumber = serviceadvisorspage.getLastPageNumber();
		serviceadvisorspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.clickGoToFirstPage();
		Assert.assertEquals("1", serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.clickGoToNextPage();
		Assert.assertEquals("2", serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.clickGoToPreviousPage();
		Assert.assertEquals("1", serviceadvisorspage.getGoToPageFieldValue());

		serviceadvisorspage.setPageSize("999");
		Assert.assertEquals(serviceadvisorspage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(serviceadvisorspage.getServiceAdvisorsTableRowsCount()));


		serviceadvisorspage.makeSearchPanelVisible();
		serviceadvisorspage.setUserSearchCriteria(firstname);
		serviceadvisorspage.selectSearchClient(_client);
		serviceadvisorspage.clickFindButton();

		Assert.assertTrue(serviceadvisorspage.getServiceAdvisorsTableRowsCount() > 0);
		serviceadvisorspage.serviceAdvisorExists(firstname, lastname);
	}

	@Test(description = "Test Case 18799:Company- Question Forms")
	public void testCompanyQuestionForms() throws Exception {

		final String questionsectionname = "Test Question Section";
		final String questionname = "New Question";
		final String questionformname = "Test Question Form";


		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		QuestionsFormsWebPage questionsformspage = companypage.clickQuestionsFormsLink();
		questionsformspage.verifyQuestionFormsTableColumnsAreVisible();
		questionsformspage.verifyQuestionSectionsTableColumnsAreVisible();
		questionsformspage.verifyPrintTemplatesTableColumnsAreVisible();
		questionsformspage.createQuestionSection(questionsectionname);
		questionsformspage.addQuestionForQuestionSection(questionsectionname, questionname);

		while (questionsformspage.isQuestionFormExists(questionformname)) {
			questionsformspage.deleteQuestionForm(questionformname);
		}
		questionsformspage.createQuestionForm(questionformname);
		questionsformspage.editAndAssignSectionToQuestionForm(questionformname, questionsectionname);
		questionsformspage.verifyQuestionIsAssignedToQuestionFormViaPreview(questionformname, questionname);

		questionsformspage.deleteQuestionForm(questionformname);
		Assert.assertFalse(questionsformspage.isQuestionFormExists(questionformname));
		questionsformspage.deleteQuestionSections(questionsectionname);
		Assert.assertFalse(questionsformspage.isQuestionSectionExists(questionsectionname));
	}

	@Test(description = "Test Case 18800:Company -Supplies")
	public void testCompanySupplies() throws Exception {

		final String supplyname = "Test Supply";
		final String supplynameedited = "Test Supply Edited";


		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		SuppliesWebPage suppliespage = companypage.clickSuppliesLink();

		suppliespage.verifySuppliesTableColumnsAreVisible();
		suppliespage.createNewSupply(supplyname);
		suppliespage.setSupplyNewName(supplyname, supplynameedited);
		suppliespage.deleteSupply(supplynameedited);
		Assert.assertFalse(suppliespage.isSupplyExists(supplynameedited));
	}

	@Test(description = "Test Case 18801:Company -Expenses Types")
	public void testCompanyExpensesTypes() throws Exception {

		final String expensetype = "Test Type";
		final String newexpensetypename = "Test Type Edited";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ExpensesTypesWebPage expensestypespage = companypage.clickExpensesTypesLink();

		expensestypespage.verifyExpensesTypesColumnsAreVisible();
		expensestypespage.createNewExpenseType(expensetype);
		expensestypespage.setExpenseTypeNewName(expensetype, newexpensetypename);
		expensestypespage.deleteExpenseType(newexpensetypename);
		Assert.assertFalse(expensestypespage.isExpenseTypeExists(newexpensetypename));
		if (expensestypespage.isExpenseTypeExists(expensetype)) {
			expensestypespage.deleteExpenseType(expensetype);
			Assert.assertFalse(expensestypespage.isExpenseTypeExists(expensetype));
		}
	}

	@Test(description = "Test Case 18802:Company -Vehicle Parts")
	public void testCompanyVehicleParts() throws Exception {

		final String vehiclepart = "Test Part";
		final String newvehiclepartname = "Test Vehicle Part";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		VehiclePartsWebPage vehiclepartspage = companypage.clickVehiclePartsLink();
		vehiclepartspage.verifyVehiclePartsColumnsAreVisible();
		vehiclepartspage.createNewVehicleParWithAllServicesSelected(vehiclepart);
		vehiclepartspage.setVehiclePartNewName(vehiclepart, newvehiclepartname);
		vehiclepartspage.deleteVehiclePart(newvehiclepartname);
		Assert.assertFalse(vehiclepartspage.isVehiclePartExists(newvehiclepartname));
	}

	@Test(testName = "Test Case 26726:Company- Employees: Archive", description = "Company- Employees: Archive")
	public void testCompanyEmployeesArchive() throws Exception {

		final String employeefirstname = "archive";
		final String employeelastname = "unarchive";
		final String employeename = employeefirstname + " " + employeelastname;

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		EmployeesWebPage employeespage = companypage.clickEmployeesLink();

		employeespage.makeSearchPanelVisible();
		employeespage.setSearchUserParameter(employeename.substring(0, 5));
		employeespage.clickFindButton();
        employeespage.verifyEmployeeIsActive(employeefirstname, employeelastname);
        for (int i = 0; i < 3; i++) {
			employeespage.archiveEmployee(employeefirstname, employeelastname);
			employeespage.clickArchivedTab();

			employeespage.setSearchUserParameter(employeename.substring(0, 5));
			employeespage.clickFindButton();
			Assert.assertTrue(employeespage.archivedEmployeeExists(employeefirstname, employeelastname));
			employeespage.unarchiveEmployee(employeefirstname, employeelastname);
			employeespage.clickActiveTab();

			employeespage.setSearchUserParameter(employeename.substring(0, 5));
			employeespage.clickFindButton();
			Assert.assertTrue(employeespage.activeEmployeeExists(employeefirstname, employeelastname));
		}
	}

    @Test(testName = "Test Case 26727:Company- Services: Archive", description = "Company- Services: Archive")
	public void testCompanyServicesArchive() throws Exception {

		final String servicename = "test12";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ServicesWebPage servicespage = companypage.clickServicesLink();
//		servicespage.makeSearchPanelVisible();
		servicespage.setServiceSearchCriteria(servicename);
		servicespage.clickFindButton();

		for (int i = 0; i < 3; i++) {
			Thread.sleep(1000);
			servicespage.archiveService(servicename);
			servicespage.clickArchivedTab();
			Assert.assertTrue(servicespage.isArchivedServiceExists(servicename));
			servicespage.unarchiveService(servicename);

			servicespage.clickActiveTab();
			Assert.assertTrue(servicespage.activeServiceExists(servicename));
		}
	}

	@Test(testName = "Test Case 26730:Company- Users: Archive", description = "Company- Users: Archive")
	public void testCompanyUsersArchive() throws Exception {

		final String userfirstname = "Archive";
		final String userlastname = "User";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		UsersWebPage userspage = companypage.clickUsersLink();
		userspage.makeSearchPanelVisible();
		userspage.setSearchUserParameter(userlastname);
		userspage.clickFindButton();
		if (!userspage.isUserActive(userfirstname, userlastname)) {
			userspage.clickArchivedTab();
			userspage.unarchiveUser(userfirstname, userlastname);
		}


		for (int i = 0; i < 3; i++) {
			userspage.archiveUser(userfirstname, userlastname);
			userspage.clickArchivedTab();
			Assert.assertTrue(userspage.isUserArchived(userfirstname, userlastname));
			userspage.unarchiveUser(userfirstname, userlastname);

			userspage.clickActiveTab();
			Assert.assertTrue(userspage.isUserActive(userfirstname, userlastname));
		}
	}

	@Test(testName = "Test Case 27455:Company - Invoice type: Verify dependency bwn Visible and Required options", description = "Company - Invoice type: Verify dependency bwn Visible and Required options")
	public void testCompanyInvoiceTypeVerifyDependencyBetweenVisibleAndRequiredOptions() throws Exception {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		InvoiceTypesWebPage invoicestypespage = companypage.clickInvoiceTypesLink();
		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicestypespage.clickAddInvoiceTypeButton();
		Thread.sleep(1000);
		Assert.assertFalse(newinvoicetypedialog.isRequiredCheckBoxVisible());
		newinvoicetypedialog.waitABit(500);
		newinvoicetypedialog.selectVisibleCheckBox();
		Assert.assertTrue(newinvoicetypedialog.isRequiredCheckBoxVisible());
		newinvoicetypedialog.unselectVisibleCheckBox();
		Assert.assertFalse(newinvoicetypedialog.isRequiredCheckBoxVisible());
		newinvoicetypedialog.clickCancelAddInvoiceTypeButton();
	}

	@Test(testName = "Test Case 24998:Company - Price Matrix: Verify that on Matrix panel Admin can see in Available services only services selected on Vehicle parts", description = "Company - Price Matrix: Verify that on Matrix panel Admin can see in Available services only services selected on Vehicle parts")
	public void testCompanyPriceMatrixVerifyThatOnMatrixPanelAdminCanSeeInAvailableServicesOnlyServicesSelectedOnVehicleParts() {
        final String vehiclePart = "Vehicle Part 1";
        final String priceMatrix = "Test Matrix mobile1";
        final List<String> serviceNames = Arrays.asList("Test service zayats", "VPS1");

		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		VehiclePartsWebPage vehiclePartsPage = companyPage.clickVehiclePartsLink();
		vehiclePartsPage.clickEditButtonForVehiclePart(vehiclePart);
		Assert.assertEquals(vehiclePart, vehiclePartsPage.getVehiclePartNameField().getAttribute("value"));

        vehiclePartsPage.verifyThatAssignedServicesListIsEmpty();
		companyPage = backOfficeHeader.clickCompanyLink();
		PriceMatricesWebPage pricematriceswebpage = companyPage.clickPriceMatricesLink();
		pricematriceswebpage.clickPricesForPriceMatrix(priceMatrix);
		vehiclePartsPage = pricematriceswebpage.clickPricesVehiclePartLink(vehiclePart);
		Assert.assertEquals(0, vehiclePartsPage.getAssignedServicesList().size());
		vehiclePartsPage.cancelNewVehiclePart();

		companyPage = backOfficeHeader.clickCompanyLink();
		vehiclePartsPage = companyPage.clickVehiclePartsLink();
		vehiclePartsPage.clickEditButtonForVehiclePart(vehiclePart);
        serviceNames.forEach(vehiclePartsPage::assignServiceForVehiclePart);
        vehiclePartsPage.saveNewVehiclePart();

		companyPage = backOfficeHeader.clickCompanyLink();
		pricematriceswebpage = companyPage.clickPriceMatricesLink();
		pricematriceswebpage.clickPricesForPriceMatrix(priceMatrix);
		vehiclePartsPage = pricematriceswebpage.clickPricesVehiclePartLink(vehiclePart);
		Assert.assertEquals(2, vehiclePartsPage.getAvailableServicesList().size());
		serviceNames.forEach(vehiclePartsPage::selectAvailableServiceForVehiclePart);
		vehiclePartsPage.cancelNewVehiclePart();

		companyPage = backOfficeHeader.clickCompanyLink();
		vehiclePartsPage = companyPage.clickVehiclePartsLink();
		vehiclePartsPage.clickEditButtonForVehiclePart(vehiclePart);
        serviceNames.forEach(vehiclePartsPage::unassignServiceForVehiclePart);
		vehiclePartsPage.saveNewVehiclePart();
	}

	@Test(testName = "Test Case 25004:Company - Price Matrix: verify that By default all selected services on Vehicle Part will be assigned to Matrix Panel", description = "Company - Price Matrix: verify that By default all selected services on Vehicle Part will be assigned to Matrix Panel")
	public void testCompanyPriceMatrixVerifyThatByDefaultAllSelectedServicesOnVehiclePartWillBeAssignedToMatrixPanel() {
        final List<String> serviceNames = Arrays.asList("Dye", "VPS1", "Wheel Repair1");
        final List<String> damageSeverities = Arrays.asList("LIGHT", "MEDIUM");
        final List<String> damageSizes = Arrays.asList("DIME", "NKL");

		final String vehiclePart = "VP with assigned services";
		final String priceMatrix = "New Matrix with assigned services";
		final String priceMatrixService = "Matrix Service";
		final String priceMatrixVehiclePart = "VP with assigned services";

		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
		VehiclePartsWebPage vehiclePartsPage = companyPage.clickVehiclePartsLink();

		vehiclePartsPage.clickEditButtonForVehiclePart(vehiclePart);
		Assert.assertEquals(vehiclePart, vehiclePartsPage.getVehiclePartNameField().getAttribute("value"));
		serviceNames.forEach(vehiclePartsPage::selectAssignedServiceForVehiclePart);
		vehiclePartsPage.saveNewVehiclePart();

		companyPage = backOfficeHeader.clickCompanyLink();
		PriceMatricesWebPage pricematriceswebpage = companyPage.clickPriceMatricesLink();
		pricematriceswebpage.clickAddPriceMarixButton();
		pricematriceswebpage.setPriceMarixName(priceMatrix);
		pricematriceswebpage.selectPriceMatrixService(priceMatrixService);
		damageSeverities.forEach(pricematriceswebpage::assignPriceMatrixDamageSeverity);
        damageSizes.forEach(pricematriceswebpage::assignPriceMatrixDamageSize);
		pricematriceswebpage.assignPriceMatrixVehiclePart(priceMatrixVehiclePart);
		pricematriceswebpage.saveNewPriceMatrix();

		pricematriceswebpage.clickPricesForPriceMatrix(priceMatrix);
		vehiclePartsPage = pricematriceswebpage.clickPricesVehiclePartLink(vehiclePart);
        serviceNames.forEach(vehiclePartsPage::selectAssignedServiceForVehiclePart);

        serviceNames.forEach(vehiclePartsPage::selectAssignedServiceForVehiclePart);
        vehiclePartsPage.verifyThatAvailableServicesListIsEmpty();

		companyPage = backOfficeHeader.clickCompanyLink();
		pricematriceswebpage = companyPage.clickPriceMatricesLink();
		pricematriceswebpage.deletePriceMatrix(priceMatrix);
	}
}
