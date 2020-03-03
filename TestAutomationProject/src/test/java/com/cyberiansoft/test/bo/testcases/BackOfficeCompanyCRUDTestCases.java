package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.bo.BOCompanyCRUDData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//@Listeners(VideoListener.class)
public class BackOfficeCompanyCRUDTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOCompanyCRUDData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInsuranceCompanyCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();

		InsuranceCompaniesWebPage insurancecompaniespage = new InsuranceCompaniesWebPage(webdriver);
		companyWebPage.clickInsuranceCompaniesLink();
		insurancecompaniespage.verifyInsuranceCompaniesDoNotExist(data.getInsuranceCompany(), data.getInsuranceCompanyEdited());

		insurancecompaniespage.clickAddInsuranceCompanyButton();
		insurancecompaniespage.createNewInsuranceCompany(data.getInsuranceCompany());

		insurancecompaniespage.clickEditInsuranceCompany(data.getInsuranceCompany());
		Assert.assertEquals(data.getInsuranceCompany(), insurancecompaniespage.getNewInsuranceCompanyName());
		insurancecompaniespage.setNewInsuranceCompanyName(data.getInsuranceCompanyEdited());
		insurancecompaniespage.setNewInsuranceCompanyAddress(data.getInsuranceCompanyAddress());
		insurancecompaniespage.setNewInsuranceCompanyEmail(data.getInsuranceCompanyEmail());
		insurancecompaniespage.setNewInsuranceCompanyPhone(data.getInsuranceCompanyPhone());
		insurancecompaniespage.setNewInsuranceCompanyAccountingID(data.getInsuranceCompanyAccountingId());
		insurancecompaniespage.setNewInsuranceCompanyAccountingID2(data.getInsuranceCompanyAccountingId2());
		insurancecompaniespage.clickAddInsuranceCompanyCancelButton();

		Assert.assertEquals("", insurancecompaniespage.getTableInsuranceCompanyAddress(data.getInsuranceCompany()));
		Assert.assertEquals("", insurancecompaniespage.getTableInsuranceCompanyEmail(data.getInsuranceCompany()));
		Assert.assertEquals("", insurancecompaniespage.getTableInsuranceCompanyPhone(data.getInsuranceCompany()));

		insurancecompaniespage.clickEditInsuranceCompany(data.getInsuranceCompany());
		Assert.assertEquals(data.getInsuranceCompany(), insurancecompaniespage.getNewInsuranceCompanyName());
		insurancecompaniespage.setNewInsuranceCompanyName(data.getInsuranceCompanyEdited());
		insurancecompaniespage.setNewInsuranceCompanyAddress(data.getInsuranceCompanyAddress());
		insurancecompaniespage.setNewInsuranceCompanyEmail(data.getInsuranceCompanyEmail());
		insurancecompaniespage.setNewInsuranceCompanyPhone(data.getInsuranceCompanyPhone());
		insurancecompaniespage.setNewInsuranceCompanyAccountingID(data.getInsuranceCompanyAccountingId());
		insurancecompaniespage.setNewInsuranceCompanyAccountingID2(data.getInsuranceCompanyAccountingId2());
		insurancecompaniespage.clickAddInsuranceCompanyOKButton();

		Assert.assertEquals(data.getInsuranceCompanyAddress(),
				insurancecompaniespage.getTableInsuranceCompanyAddress(data.getInsuranceCompanyEdited()));
		Assert.assertEquals(data.getInsuranceCompanyEmail(),
				insurancecompaniespage.getTableInsuranceCompanyEmail(data.getInsuranceCompanyEdited()));
		Assert.assertEquals(data.getInsuranceCompanyPhone(),
				insurancecompaniespage.getTableInsuranceCompanyPhone(data.getInsuranceCompanyEdited()));

		insurancecompaniespage.deleteInsuranceCompanyAndCancelDeleting(data.getInsuranceCompanyEdited());
		insurancecompaniespage.deleteInsuranceCompany(data.getInsuranceCompanyEdited());
		Assert.assertFalse(insurancecompaniespage.insuranceCompanyExists(data.getInsuranceCompanyEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyTeamsCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();

		TeamsWebPage teamspage = new TeamsWebPage(webdriver);
		companyWebPage.clickTeamsLink();
		teamspage.makeSearchPanelVisible();
		teamspage.setTeamLocationSearchCriteria(data.getTeam()).clickFindButton();
		teamspage.verifyTeamsDoNotExist(data.getTeam(), data.getTeamEdited());

		teamspage.createNewTeam(data.getTeam(), "Default area");
		teamspage.setTeamLocationSearchCriteria(data.getTeam()).clickFindButton();

		NewTeamsDialogWebPage newteamsdialog = new NewTeamsDialogWebPage(webdriver);
		teamspage.clickEditTeam(data.getTeam());
		Assert.assertEquals(data.getTeam(), newteamsdialog.getNewTeamName());

		newteamsdialog.setNewTeamName(data.getTeamEdited());
		newteamsdialog.selectTeamTimezone(data.getTeamTimeZone());
		newteamsdialog.setNewTeamDescription(data.getTeamDesc());
		newteamsdialog.setNewTeamAccountingID(data.getTeamId());
		newteamsdialog.selectTeamArea(data.getTeamArea());
		newteamsdialog.selectTeamTimesheetType(data.getTeamTimeSheetType());
		newteamsdialog.selectTeamDefaultRepairLocation(data.getTeamDefaultLocation());
		newteamsdialog.selectTeamAdditionalRepairLocation(data.getTeamAdditionalLocation());
		newteamsdialog.selectTeamType(data.getTeamType());
		newteamsdialog.setNewTeamCompany(data.getTeamCompany());
		newteamsdialog.setNewTeamAddress(data.getTeamAddress());
		newteamsdialog.setNewTeamCity(data.getTeamCity());
		newteamsdialog.selectTeamCountry(data.getTeamCountry());
		newteamsdialog.selectTeamState(data.getTeamState());
		newteamsdialog.setNewTeamZip(data.getTeamZip());
		newteamsdialog.setNewTeamEmail(data.getTeamEmail());
		newteamsdialog.setNewTeamPhone(data.getTeamPhone());
		newteamsdialog.clickAddTeamCancelButton();

		Assert.assertEquals("Internal", teamspage.getTableTeamType(data.getTeam()).trim());
		Assert.assertEquals("", teamspage.getTableTeamLocation(data.getTeam()).trim());
		Assert.assertEquals("Default area", teamspage.getTableTeamArea(data.getTeam()).trim());
		Assert.assertEquals("", teamspage.getTableTeamTimesheetType(data.getTeam()).trim());
		Assert.assertEquals("(UTC-08:00) Pacific Time (US & Canada)", teamspage.getTableTeamTimeZone(data.getTeam()).trim());
		Assert.assertEquals("", teamspage.getTableTeamDescription(data.getTeam()).trim());

		newteamsdialog = new NewTeamsDialogWebPage(webdriver);
		teamspage.clickEditTeam(data.getTeam());
		newteamsdialog.setNewTeamName(data.getTeamEdited());
		newteamsdialog.selectTeamTimezone(data.getTeamTimeZone());
		newteamsdialog.setNewTeamDescription(data.getTeamDesc());
		newteamsdialog.setNewTeamAccountingID(data.getTeamId());
		newteamsdialog.selectTeamArea(data.getTeamArea());
		newteamsdialog.selectTeamTimesheetType(data.getTeamTimeSheetType());
		newteamsdialog.selectTeamDefaultRepairLocation(data.getTeamDefaultLocation());
		newteamsdialog.selectTeamAdditionalRepairLocation(data.getTeamAdditionalLocation());
		newteamsdialog.selectTeamType(data.getTeamType());
		newteamsdialog.setNewTeamCompany(data.getTeamCompany());
		newteamsdialog.setNewTeamAddress(data.getTeamAddress());
		newteamsdialog.setNewTeamCity(data.getTeamCity());
		newteamsdialog.selectTeamCountry(data.getTeamCountry());
		newteamsdialog.selectTeamState(data.getTeamState());
		newteamsdialog.setNewTeamZip(data.getTeamZip());
		newteamsdialog.setNewTeamEmail(data.getTeamEmail());
		newteamsdialog.setNewTeamPhone(data.getTeamPhone());
		newteamsdialog.clickAddTeamOKButton();

		Assert.assertEquals(data.getTeamType(), teamspage.getTableTeamType(data.getTeamEdited()).trim());
		Assert.assertEquals(data.getTeamDefaultLocation(), teamspage.getTableTeamLocation(data.getTeamEdited()).trim());
		Assert.assertEquals(data.getTeamArea(), teamspage.getTableTeamArea(data.getTeamEdited()).trim());
		Assert.assertEquals(data.getTeamTimeSheetType(), teamspage.getTableTeamTimesheetType(data.getTeamEdited()).trim());
		Assert.assertEquals(data.getTeamTimeZone(), teamspage.getTableTeamTimeZone(data.getTeamEdited()).trim());
		Assert.assertEquals(data.getTeamDesc(), teamspage.getTableTeamDescription(data.getTeamEdited()).trim());


		teamspage.deleteTeamAndCancelDeleting(data.getTeamEdited());
		teamspage.deleteTeam(data.getTeamEdited());
		Assert.assertFalse(teamspage.teamExists(data.getTeamEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyJobsCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();

		companyWebPage.clickJobsLink();
		JobsWebPage jobsPage = new JobsWebPage(webdriver);
		if (jobsPage.isJobPresent(data.getJob())) {
			jobsPage.deleteJob(data.getJob());
		}

		jobsPage.createNewJob(data.getJob());
		jobsPage.clickEditJob(data.getJob());
		Assert.assertEquals(data.getJob(), jobsPage.getNewJobName());
		jobsPage.setNewJobName(data.getJobEdited());
		jobsPage.setNewJobDescription(data.getJobDesc());
		jobsPage.selectJobClient(data.getCustomer());
		jobsPage.selectJobParentClient(data.getParentCustomer());
		jobsPage.setNewJobStartDate(CustomDateProvider.getCurrentDateInShortFormat());
		jobsPage.setNewJobEndDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		jobsPage.setNewJobAccountingID(data.getJobAccId());
		jobsPage.setNewJobAccountingID(data.getJobAcc2Id());

		jobsPage.clickAddJobCancelButton();

		Assert.assertEquals("", jobsPage.getTableJobDescription(data.getJob()).trim());
		Assert.assertEquals("", jobsPage.getTableJobClient(data.getJob()).trim());
		Assert.assertEquals("", jobsPage.getTableJobStartDate(data.getJob()).trim());
		Assert.assertEquals("", jobsPage.getTableJobEndDate(data.getJob()).trim());
		Assert.assertEquals("", jobsPage.getTableJobAccountingID(data.getJob()).trim());
		Assert.assertEquals("", jobsPage.getTableJobAccountingID2(data.getJob()).trim());

		jobsPage.clickEditJob(data.getJob());
		jobsPage.setNewJobName(data.getJobEdited());
		jobsPage.setNewJobDescription(data.getJobDesc());
		jobsPage.selectJobClient(data.getCustomer());
		jobsPage.selectJobParentClient(data.getParentCustomer());
		jobsPage.setNewJobStartDate(CustomDateProvider.getCurrentDateInShortFormat());
		jobsPage.setNewJobEndDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		jobsPage.setNewJobAccountingID(data.getJobAccId());
		jobsPage.setNewJobAccountingID2(data.getJobAcc2Id());
		jobsPage.clickAddJobOKButton();

		Assert.assertEquals(data.getJobDesc(), jobsPage.getTableJobDescription(data.getJobEdited()));
		Assert.assertEquals(data.getCustomer(), jobsPage.getTableJobClient(data.getJobEdited()));
		Assert.assertEquals(CustomDateProvider.getLocalizedCurrentDateInTheShortestFormat(), jobsPage.getTableJobStartDate(data.getJobEdited()));
		Assert.assertEquals(CustomDateProvider.getTomorrowLocalizedDateFormattedTheShortest(), jobsPage.getTableJobEndDate(data.getJobEdited()));
		Assert.assertEquals(data.getJobAccId(), jobsPage.getTableJobAccountingID(data.getJobEdited()));
		Assert.assertEquals(data.getJobAcc2Id(), jobsPage.getTableJobAccountingID2(data.getJobEdited()));

		jobsPage.deleteJobAndCancelDeleting(data.getJobEdited());
		jobsPage.deleteJob(data.getJobEdited());
		Assert.assertFalse(jobsPage.isJobPresent(data.getJobEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServiceAdvisorsCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServiceAdvisorsWebPage serviceadvisorspage = new ServiceAdvisorsWebPage(webdriver);
		companyWebPage.clickServiceAdvisorsLink();
		serviceadvisorspage.makeSearchPanelVisible();
		serviceadvisorspage.setUserSearchCriteria(data.getFirstName() + " " + data.getLastName());
		serviceadvisorspage.clickFindButton();

		while (serviceadvisorspage.serviceAdvisorExists(data.getFirstName(), data.getLastName())) {
			serviceadvisorspage.deleteServiceAdvisor(data.getFirstName(), data.getLastName());
		}
		serviceadvisorspage.clickServiceAdvisorAddButton();
		serviceadvisorspage.createNewServiceAdvisor(data.getEmail(), data.getFirstName(), data.getLastName(), data.getCustomer(), data.getRole());
		serviceadvisorspage.clickEditServiceAdvisor(data.getFirstName(), data.getLastName());
		serviceadvisorspage.setNewServiceAdvisorCompany(data.getServiceAdvisorCompany());
		serviceadvisorspage.setNewServiceAdvisorAddress(data.getServiceAdvisorAddress());
		serviceadvisorspage.setNewServiceAdvisorCity(data.getServiceAdvisorCity());
		serviceadvisorspage.selectNewServiceAdvisorCountry(data.getServiceAdvisorCountry());
		serviceadvisorspage.selectNewServiceAdvisorState(data.getServiceAdvisorState());
		serviceadvisorspage.setNewServiceAdvisorZip(data.getServiceAdvisorZip());
		serviceadvisorspage.setNewServiceAdvisorPhone(data.getServiceAdvisorPhone());
		serviceadvisorspage.setNewServiceAdvisorAccountingID(data.getServiceAdvisorAccId());
		serviceadvisorspage.clickNewServiceAdvisorCancelButton();

		Assert.assertEquals(data.getFirstName() + " " + data.getLastName(), serviceadvisorspage.getTableServiceAdvisorFullName(data.getFirstName(), data.getLastName()));
		Assert.assertEquals(data.getEmail(), serviceadvisorspage.getTableServiceAdvisorEmail(data.getFirstName(), data.getLastName()));
		Assert.assertEquals("..., AZ", serviceadvisorspage.getTableServiceAdvisorAddress(data.getFirstName(), data.getLastName()).trim());
		Assert.assertEquals("", serviceadvisorspage.getTableServiceAdvisorPhone(data.getFirstName(), data.getLastName()).trim());
		Assert.assertEquals(data.getRole(), serviceadvisorspage.getTableServiceAdvisorRoles(data.getFirstName(), data.getLastName()));
		Assert.assertEquals("", serviceadvisorspage.getTableServiceAdvisorAccountingID(data.getFirstName(), data.getLastName()).trim());

		serviceadvisorspage.clickEditServiceAdvisor(data.getFirstName(), data.getLastName());
		serviceadvisorspage.setNewServiceAdvisorCompany(data.getServiceAdvisorCompany());
		serviceadvisorspage.setNewServiceAdvisorAddress(data.getServiceAdvisorAddress());
		serviceadvisorspage.setNewServiceAdvisorCity(data.getServiceAdvisorCity());
		serviceadvisorspage.selectNewServiceAdvisorCountry(data.getServiceAdvisorCountry());
		serviceadvisorspage.selectNewServiceAdvisorState(data.getServiceAdvisorState());
		serviceadvisorspage.setNewServiceAdvisorZip(data.getServiceAdvisorZip());
		serviceadvisorspage.setNewServiceAdvisorPhone(data.getServiceAdvisorPhone());
		serviceadvisorspage.setNewServiceAdvisorAccountingID(data.getServiceAdvisorAccId());
		serviceadvisorspage.clickNewServiceAdvisorOKButton();

		Assert.assertEquals(data.getFirstName() + " " + data.getLastName(), serviceadvisorspage.getTableServiceAdvisorFullName(data.getFirstName(), data.getLastName()));
		Assert.assertEquals(data.getEmail(), serviceadvisorspage.getTableServiceAdvisorEmail(data.getFirstName(), data.getLastName()));
		final String fulladdress = data.getServiceAdvisorAddress() + ", " + data.getServiceAdvisorCity() + ", FL, " + data.getServiceAdvisorZip();
		Assert.assertEquals(fulladdress, serviceadvisorspage.getTableServiceAdvisorAddress(data.getFirstName(), data.getLastName()).trim());
		Assert.assertEquals(data.getServiceAdvisorPhone(), serviceadvisorspage.getTableServiceAdvisorPhone(data.getFirstName(), data.getLastName()).trim());
		Assert.assertEquals(data.getRole(), serviceadvisorspage.getTableServiceAdvisorRoles(data.getFirstName(), data.getLastName()));
		Assert.assertEquals(data.getServiceAdvisorAccId(), serviceadvisorspage.getTableServiceAdvisorAccountingID(data.getFirstName(), data.getLastName()).trim());

		serviceadvisorspage.deleteServiceAdvisorAndCancelDeleting(data.getFirstName(), data.getLastName());
		serviceadvisorspage.deleteServiceAdvisor(data.getFirstName(), data.getLastName());
		serviceadvisorspage.setUserSearchCriteria(data.getFirstName() + " " + data.getLastName());
		serviceadvisorspage.clickFindButton();
		Assert.assertFalse(serviceadvisorspage.serviceAdvisorExists(data.getFirstName(), data.getLastName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServiceContractTypesCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServiceContractTypesWebPage servicecontracttypespage = new ServiceContractTypesWebPage(webdriver);
		companyWebPage.clickServiceContractTypesLink();
		servicecontracttypespage.verifyServiceContractTypesDoNotExist(data.getContractType(), data.getContractTypeEdited());

		servicecontracttypespage.clickAddServiceContractTypeButton();
		servicecontracttypespage.createNewServiceContractType(data.getContractType());
		servicecontracttypespage.clickEditServiceContractType(data.getContractType());
		servicecontracttypespage.setNewServiceContractTypeName(data.getContractTypeEdited());
		servicecontracttypespage.setNewServiceContractTypeDescription(data.getContractTypeDesc());
		servicecontracttypespage.setNewServiceContractTypeAccountingID(data.getContractTypeAccId());
		servicecontracttypespage.setNewServiceContractTypeAccountingID2(data.getContractTypeAccId2());
		servicecontracttypespage.setNewServiceContractTypePrice(data.getContractTypePrice());
		servicecontracttypespage.setNewServiceContractTypeSalesPrice(data.getContractTypeSalesPrice());
		servicecontracttypespage.clickNewServiceContractTypeCancelButton();

		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeDescription(data.getContractType()));
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypePrice(data.getContractType()));
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeSalesPrice(data.getContractType()));
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeAccID(data.getContractType()));
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeAccID2(data.getContractType()));

		servicecontracttypespage.clickEditServiceContractType(data.getContractType());
		servicecontracttypespage.setNewServiceContractTypeName(data.getContractTypeEdited());
		servicecontracttypespage.setNewServiceContractTypeDescription(data.getContractTypeDesc());
		servicecontracttypespage.setNewServiceContractTypeAccountingID(data.getContractTypeAccId());
		servicecontracttypespage.setNewServiceContractTypeAccountingID2(data.getContractTypeAccId2());
		servicecontracttypespage.setNewServiceContractTypePrice(data.getContractTypePrice());
		servicecontracttypespage.setNewServiceContractTypeSalesPrice(data.getContractTypeSalesPrice());
		servicecontracttypespage.clickNewServiceContractTypeOKButton();

		Assert.assertEquals(data.getContractTypeDesc(),
				servicecontracttypespage.getTableServiceContractTypeDescription(data.getContractTypeEdited()).trim());
		Assert.assertEquals(BackOfficeUtils.getFullPriceRepresentation(data.getContractTypePrice()),
				servicecontracttypespage.getTableServiceContractTypePrice(data.getContractTypeEdited()).trim());
		Assert.assertEquals(BackOfficeUtils.getFullPriceRepresentation(data.getContractTypeSalesPrice()),
				servicecontracttypespage.getTableServiceContractTypeSalesPrice(data.getContractTypeEdited()).trim());
		Assert.assertEquals(data.getContractTypeAccId(),
				servicecontracttypespage.getTableServiceContractTypeAccID(data.getContractTypeEdited()).trim());
		Assert.assertEquals(data.getContractTypeAccId2(),
				servicecontracttypespage.getTableServiceContractTypeAccID2(data.getContractTypeEdited()).trim());

		servicecontracttypespage.deleteServiceContractTypeAndCancelDeleting(data.getContractTypeEdited());
		servicecontracttypespage.deleteServiceContractType(data.getContractTypeEdited());
		Assert.assertFalse(servicecontracttypespage.isServiceContractTypeExists(data.getContractType()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyPriceMatrixCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		PriceMatricesWebPage pricematricespage = new PriceMatricesWebPage(webdriver);
		companyWebPage.clickPriceMatricesLink();
		pricematricespage.verifyPriceMatricesDoNotExist(data.getPriceMatrixName(), data.getPriceMatrixNameEdited());

		pricematricespage.clickAddPriceMarixButton();
		pricematricespage.setPriceMarixName(data.getPriceMatrixName());
		pricematricespage.saveNewPriceMatrix();

		pricematricespage.clickEditPriceMatrix(data.getPriceMatrixName());
		pricematricespage.setPriceMarixName(data.getPriceMatrixNameEdited());
		pricematricespage.selectPriceMatrixService(data.getPriceMatrixService());
		pricematricespage.selectPriceMarixType(data.getPriceMatrixType());
		pricematricespage.clickCancelNewPriceMatrix();
		Assert.assertEquals("_testStas13234", pricematricespage.getTablePriceMatrixService(data.getPriceMatrixName()));
		Assert.assertEquals("Money", pricematricespage.getTablePriceMatrixType(data.getPriceMatrixName()));

		pricematricespage.clickEditPriceMatrix(data.getPriceMatrixName());
		pricematricespage.setPriceMarixName(data.getPriceMatrixNameEdited());
		pricematricespage.selectPriceMatrixService(data.getPriceMatrixService());
		pricematricespage.selectPriceMarixType(data.getPriceMatrixType());
		pricematricespage.saveNewPriceMatrix();
		Assert.assertEquals(data.getPriceMatrixService(),
				pricematricespage.getTablePriceMatrixService(data.getPriceMatrixNameEdited()));
		Assert.assertEquals(data.getPriceMatrixType(),
				pricematricespage.getTablePriceMatrixType(data.getPriceMatrixNameEdited()));

		pricematricespage.deletePriceMatrixAndCancelDeleting(data.getPriceMatrixNameEdited());
		pricematricespage.deletePriceMatrix(data.getPriceMatrixNameEdited());
		Assert.assertFalse(pricematricespage.isPriceMatrixPresent(data.getPriceMatrixNameEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInvoiceTypeCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InvoiceTypesWebPage invoicestypespage = new InvoiceTypesWebPage(webdriver);
		companyWebPage.clickInvoiceTypesLink();

		invoicestypespage.verifyInvoiceTypesDoNotExist(data.getInvoiceType(), data.getInvoiceTypeEdited());

		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicestypespage.clickAddInvoiceTypeButton();
		newinvoicetypedialog.createInvoiceType(data.getInvoiceType());
		newinvoicetypedialog = invoicestypespage.clickEditInvoiceType(data.getInvoiceType());
		newinvoicetypedialog.setInvoiceTypeName(data.getInvoiceTypeEdited());
		newinvoicetypedialog.setInvoiceTypeDescription(data.getInvoiceTypeDesc());
		newinvoicetypedialog.clickCancelAddInvoiceTypeButton();
		Assert.assertEquals("", invoicestypespage.getTableInvoiceTypeDescription(data.getInvoiceType()));

		newinvoicetypedialog = invoicestypespage.clickEditInvoiceType(data.getInvoiceType());
		newinvoicetypedialog.setInvoiceTypeName(data.getInvoiceTypeEdited());
		newinvoicetypedialog.setInvoiceTypeDescription(data.getInvoiceTypeDesc());
		newinvoicetypedialog.clickOKAddInvoiceTypeButton();
		Assert.assertEquals(data.getInvoiceTypeDesc(),
				invoicestypespage.getTableInvoiceTypeDescription(data.getInvoiceTypeEdited()));

		invoicestypespage.deleteInvoiceTypeAndCancelDeleting(data.getInvoiceTypeEdited());
		invoicestypespage.deleteInvoiceType(data.getInvoiceTypeEdited());
		Assert.assertFalse(invoicestypespage.invoiceTypeExists(data.getInvoiceTypeEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServiceRequestTypeCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage servicerequesttypespage = new ServiceRequestTypesWebPage(webdriver);
		companyWebPage.clickServiceRequestTypesLink();
		servicerequesttypespage.verifyServiceRequestsTypesDoNotExist(data.getServiceType(), data.getServiceTypeEdited());
		servicerequesttypespage.clickAddServiceRequestTypeButton();
		servicerequesttypespage.createNewServiceRequestType(data.getServiceType());

		servicerequesttypespage.clickEditServiceRequestType(data.getServiceType());
		servicerequesttypespage.setNewServiceRequestTypeName(data.getServiceTypeEdited());
		servicerequesttypespage.setNewServiceRequestTypeDescription(data.getServiceTypeDesc());
		servicerequesttypespage.selectNewServiceRequestTypeTeam(data.getServiceTypeTeam());
		servicerequesttypespage.clickNewServiceRequestTypeCancelButton();
		Assert.assertEquals("", servicerequesttypespage.getTableServiceRequestTypeDescription(data.getServiceType()));

		servicerequesttypespage.clickEditServiceRequestType(data.getServiceType());
		servicerequesttypespage.setNewServiceRequestTypeName(data.getServiceTypeEdited());
		servicerequesttypespage.setNewServiceRequestTypeDescription(data.getServiceTypeDesc());
		servicerequesttypespage.selectNewServiceRequestTypeTeam(data.getServiceTypeTeam());
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();
		Assert.assertEquals(data.getServiceTypeDesc(),
				servicerequesttypespage.getTableServiceRequestTypeDescription(data.getServiceTypeEdited()));

		servicerequesttypespage.deleteServiceRequestTypeAndCancelDeleting(data.getServiceTypeEdited());
		servicerequesttypespage.deleteServiceRequestType(data.getServiceTypeEdited());
		Assert.assertFalse(servicerequesttypespage.isServiceRequestTypeExists(data.getServiceTypeEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyEmailTemplatesCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		EmailTemplatesWebPage emailtemplatespage = new EmailTemplatesWebPage(webdriver);
		companyWebPage.clickEmailTemplatesLink();
		emailtemplatespage.verifyEmailTemplatesDoNoExist(data.getTemplateName(), data.getTemplateNameEdited());

		emailtemplatespage.clickAddMailTemplateButton();
		emailtemplatespage.createNewEmailTemplate(data.getTemplateName(), data.getTemplateSubject());
		emailtemplatespage.clickEditEmailTemplate(data.getTemplateName());
		emailtemplatespage.setNewEmailTemplateName(data.getTemplateNameEdited());
		emailtemplatespage.setNewEmailTemplateSubject(data.getTemplateSubjectEdited());
		emailtemplatespage.setNewEmailTemplateBody(data.getTemplateBody());
		emailtemplatespage.clickNewEmailTemplateCancelButton();
		Assert.assertEquals(data.getTemplateSubject(),
				emailtemplatespage.getTableEmailTemplateSubject(data.getTemplateName()).trim());

		emailtemplatespage.clickEditEmailTemplate(data.getTemplateName());
		emailtemplatespage.createNewEmailTemplate(data.getTemplateNameEdited(),
				data.getTemplateSubjectEdited(), data.getTemplateBody());
		Assert.assertEquals(data.getTemplateSubjectEdited(),
				emailtemplatespage.getTableEmailTemplateSubject(data.getTemplateNameEdited()).trim());

		emailtemplatespage.deleteEmailTemplateAndCancelDeleting(data.getTemplateNameEdited());
		emailtemplatespage.deleteEmailTemplate(data.getTemplateNameEdited());
		Assert.assertFalse(emailtemplatespage.isEmailTemplateExists(data.getTemplateNameEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyPrintServerCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		PrintServersWebPage printserverspage = new PrintServersWebPage(webdriver);
		companyWebPage.clickPrintServersLink();
		printserverspage.verifyPrintServersDoNotExist(data.getPrintServerName(), data.getPrintServerNameEdited());

		printserverspage.clickAddPrintServerButton();
		printserverspage.addNewPrintServer(data.getPrintServerName());
		printserverspage.clickEditPrintServer(data.getPrintServerName());

		printserverspage.setPrintServerName(data.getPrintServerNameEdited());
		printserverspage.setPrintServerDescription(data.getPrintServerDesc());
		printserverspage.clickNewPrintServerCancelButton();
		Assert.assertEquals("", printserverspage.getTablePrintServerDescription(data.getPrintServerName()));

		printserverspage.clickEditPrintServer(data.getPrintServerName());
		printserverspage.addNewPrintServer(data.getPrintServerNameEdited(), data.getPrintServerDesc());
		Assert.assertEquals(data.getPrintServerDesc(),
				printserverspage.getTablePrintServerDescription(data.getPrintServerNameEdited()).trim());

		printserverspage.deletePrintServerAndCancelDeleting(data.getPrintServerNameEdited());
		printserverspage.deletePrintServer(data.getPrintServerNameEdited());
		Assert.assertFalse(printserverspage.printServerExists(data.getPrintServerNameEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyLicenceCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ManageLicencesWebPage managelicencespage = new ManageLicencesWebPage(webdriver);
		companyWebPage.clickManageLicencesLink();
		managelicencespage.makeSearchPanelVisible();
		managelicencespage.selectLicenceSearchApplicationParameter(data.getLicenceApp());
		managelicencespage.clickFindButton();

		if (managelicencespage.licenceApplicationExists(data.getLicenceApp())) {
			managelicencespage.deleteLicenceApplication(data.getLicenceApp());
		}

		managelicencespage.clickAddManageLicenceButton();
//		managelicencespage.selectNewLicenceApplication(data.getLicenceApp());
		String deflicencetype = managelicencespage.getNewLicenceType();
		managelicencespage.clickNewLicenceOKButton();

		managelicencespage.clickEditLicenceApplication(data.getLicenceApp());
		managelicencespage.selectNewLicenceType(data.getLicenceType());
		managelicencespage.clickNewLicenceCancelButton();
		Assert.assertEquals(deflicencetype, managelicencespage.getTableLicenceType(data.getLicenceApp()));

		managelicencespage.clickEditLicenceApplication(data.getLicenceApp());
		managelicencespage.selectNewLicenceType(data.getLicenceType());
		managelicencespage.clickNewLicenceOKButton();
		Assert.assertEquals(data.getLicenceType(), managelicencespage.getTableLicenceType(data.getLicenceApp()));

		managelicencespage.deleteLicenceApplicationAndCancelDeleting(data.getLicenceApp());
		managelicencespage.deleteLicenceApplication(data.getLicenceApp());
		Assert.assertFalse(managelicencespage.licenceApplicationExists(data.getLicenceApp()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyTimesheetTypesCRUD(String rowID, String description, JSONObject testData) throws Exception {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		TimesheetTypesWebPage timesheettypespage = new TimesheetTypesWebPage(webdriver);
		companyWebPage.clickTimesheetTypesLink();
		timesheettypespage.verifyTimeSheetsTypeDoNoExist(data.getTimeSheetType(), data.getTimeSheetTypeEdited());

		timesheettypespage.clickAddTimesheetTypeButton();
		String tsdefaultentrytype = timesheettypespage.createNewTimesheetType(data.getTimeSheetType());

		timesheettypespage.clickEditTimesheetType(data.getTimeSheetType());
		timesheettypespage.setNewTimesheetTypeName(data.getTimeSheetTypeEdited());
		timesheettypespage.setNewTimesheetTypeDescription(data.getTimeSheetTypeDesc());
		timesheettypespage.selectNewTimesheetTypeEntryType(data.getTimeSheetTypeEntryType());
		timesheettypespage.clickNewTimesheetTypeCancelButton();
		Assert.assertEquals("", timesheettypespage.getTableTimesheetTypeDescription(data.getTimeSheetType()).trim());
		Assert.assertEquals(tsdefaultentrytype, timesheettypespage.getTableTimesheetTypeEntryType(data.getTimeSheetType()).trim());

		timesheettypespage.clickEditTimesheetType(data.getTimeSheetType());
		timesheettypespage.setNewTimesheetTypeName(data.getTimeSheetTypeEdited());
		timesheettypespage.setNewTimesheetTypeDescription(data.getTimeSheetTypeDesc());
		timesheettypespage.selectNewTimesheetTypeEntryType(data.getTimeSheetTypeEntryType());
		timesheettypespage.clickNewTimesheetTypeOKButton();
		Assert.assertEquals(data.getTimeSheetTypeDesc(),
				timesheettypespage.getTableTimesheetTypeDescription(data.getTimeSheetTypeEdited()).trim());
		//Assert.assertEquals(data.getTimeSheetTypeEntryType(),  timesheettypespage.getTableTimesheetTypeEntryType(data.getTimeSheetTypeEdited()).trim());

		timesheettypespage.deleteTimesheetTypeAndCancelDeleting(data.getTimeSheetTypeEdited());
		timesheettypespage.deleteTimesheetType(data.getTimeSheetTypeEdited());
		Assert.assertFalse(timesheettypespage.isTimesheetTypeExists(data.getTimeSheetTypeEdited()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServicePackagesCRUD(String rowID, String description, JSONObject testData) {

		BOCompanyCRUDData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyCRUDData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServicePackagesWebPage servicepackagespage = new ServicePackagesWebPage(webdriver);
		companyWebPage.clickServicePackagesLink();
		servicepackagespage.verifyServicePackagesDoNotExist(data.getServicePackageName(), data.getServicePackageNameEdited());

		NewServicePackageDialogWebPage newservicepackagedialog = servicepackagespage.clickAddServicePackageButton();
		newservicepackagedialog.setNewServicePackageName(data.getServicePackageName());
		newservicepackagedialog.selectNewServicePackageType(data.getServicePackageType());
		newservicepackagedialog.selectNewServicePackageFormType(data.getServicePackageFormType());
		newservicepackagedialog.setNewServicePackageTechnicianCommissions(data.getServicePackageEchComm());
		newservicepackagedialog.setNewServicePackageAdvisorCommissions(data.getServicePackageAdvComm());
		newservicepackagedialog.clickOKButton();

		Assert.assertEquals(servicepackagespage.getTableServicePackageType(data.getServicePackageName()),
				data.getServicePackageType());
		Assert.assertEquals(servicepackagespage.getTableServicePackageFormType(data.getServicePackageName()),
				data.getServicePackageFormType());
		servicepackagespage.clickEditServicePackage(data.getServicePackageName());

		newservicepackagedialog.setNewServicePackageName(data.getServicePackageNameEdited());
		newservicepackagedialog.clickCancelButton();

		Assert.assertEquals(servicepackagespage.getTableServicePackageType(data.getServicePackageName()),
				data.getServicePackageType());
		Assert.assertEquals(servicepackagespage.getTableServicePackageFormType(data.getServicePackageName()),
				data.getServicePackageFormType());
		servicepackagespage.clickEditServicePackage(data.getServicePackageName());
		Assert.assertEquals(newservicepackagedialog.getNewServicePackageName(), data.getServicePackageName());

		newservicepackagedialog.setNewServicePackageName(data.getServicePackageNameEdited());
		newservicepackagedialog.clickOKButton();
		Assert.assertEquals(servicepackagespage.getTableServicePackageType(data.getServicePackageNameEdited()),
				data.getServicePackageType());
		Assert.assertEquals(servicepackagespage.getTableServicePackageFormType(data.getServicePackageNameEdited()),
				data.getServicePackageFormType());

		servicepackagespage.deleteServicePackageAndCancelDeleting(data.getServicePackageNameEdited());
		servicepackagespage.deleteServicePackage(data.getServicePackageNameEdited());
	}

	//@Test(testName = "Test Case 28992:Company- Work Order Types: CRUD", description = "Company- Work Order Types: CRUD")
	public void testCompanyWorkOrderTypesCRUD() throws Exception {


		final String wotype = "test wotype";
		final String wotypeservicepkg = "Internal";
		final String wotypedesc = "wotype description";
		final String wotypealiasname = "xxxx";
		final String newwotypeservicepkg = "Used Car";
		final String wotypegroupby = "Panels";
		final String wotypepriceaccess = "Hidden";
		final String wotypesharingtype = "data.getTeam() Sharing";
		final String[] wotypeoptions = {"Approval Required", "Allow Delete", "Block Identical VIN", "Block Identical Services",
				"Vehicle History Enforced", "Total Sale Field Required", "Status Reason Required"};

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		WorkOrderTypesWebPage workordertypespage = new WorkOrderTypesWebPage(webdriver);
		companyWebPage.clickWorkOrderTypesLink();
		if (workordertypespage.isWorkOrderTypeExists(wotype)) {
			workordertypespage.deleteWorkOrderType(wotype);
		}
		workordertypespage.clickAddWorkOrderTypeButton();
		final String defaultwotypepriceaccess = workordertypespage.createNewWorkOrderType(wotype, wotypeservicepkg);

		workordertypespage.clickEditWorkOrderType(wotype);
		workordertypespage.setNewWorkOrderTypeDescription(wotypedesc);
		workordertypespage.setNewWorkOrderTypeAliasName(wotypealiasname);
		workordertypespage.selectNewWorkOrderyTypeServicePackage(newwotypeservicepkg);
		workordertypespage.selectNewWorkOrderyTypeGroupServicesBy(wotypegroupby);
		workordertypespage.selectNewWorkOrderyTypePriceAccess(wotypepriceaccess);
		workordertypespage.selectNewWorkOrderyTypeSharing(wotypesharingtype);
		for (String wotypeoption : wotypeoptions) {
			workordertypespage.chechWOTypeOption(wotypeoption);
		}
		workordertypespage.setNewWorkOrderTypeMonitorRepairingInformation(true, true, "10");
		workordertypespage.clickNewWorkOrderTypeCancelButton();

		Assert.assertEquals("", workordertypespage.getTableWorkOrderTypeDescription(wotype).trim());
		Assert.assertEquals(wotypeservicepkg, workordertypespage.getTableWorkOrderTypeServicePackage(wotype).trim());
		Assert.assertEquals(defaultwotypepriceaccess, workordertypespage.getTableWorkOrderTypePriceAccess(wotype));

		workordertypespage.clickEditWorkOrderType(wotype);
		workordertypespage.setNewWorkOrderTypeDescription(wotypedesc);
		workordertypespage.setNewWorkOrderTypeAliasName(wotypealiasname);
		workordertypespage.selectNewWorkOrderyTypeServicePackage(newwotypeservicepkg);
		workordertypespage.selectNewWorkOrderyTypeGroupServicesBy(wotypegroupby);
		workordertypespage.selectNewWorkOrderyTypePriceAccess(wotypepriceaccess);
		workordertypespage.selectNewWorkOrderyTypeSharing(wotypesharingtype);
		for (String wotypeoption : wotypeoptions) {
			workordertypespage.chechWOTypeOption(wotypeoption);
		}
		workordertypespage.setNewWorkOrderTypeMonitorRepairingInformation(true, true, "10");
		workordertypespage.clickNewWorkOrderTypeOKButton();

		Assert.assertEquals(wotypedesc, workordertypespage.getTableWorkOrderTypeDescription(wotype).trim());
		Assert.assertEquals(newwotypeservicepkg, workordertypespage.getTableWorkOrderTypeServicePackage(wotype).trim());
		Assert.assertEquals(wotypepriceaccess, workordertypespage.getTableWorkOrderTypePriceAccess(wotype));

		workordertypespage.deleteWorkOrderTypeAndCancelDeleting(wotype);
		workordertypespage.deleteWorkOrderType(wotype);
		Assert.assertFalse(workordertypespage.isWorkOrderTypeExists(wotype));
	}
}
