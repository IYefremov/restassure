package com.cyberiansoft.test.bo.testcases;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.EmailTemplatesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InsuranceCompaniesWePpage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoiceTypesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.JobsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ManageLicencesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewInvoiceTypeDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewServicePackageDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewTeamsDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.PriceMatricesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.PrintServersWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceAdvisorsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceContractTypesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestTypesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TeamsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetTypesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.WorkOrderTypesWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;

public class BackOfficeCompanyCRUDTestCases extends BaseTestCase {
	
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
		Thread.sleep(3000);
	}
	
	@Test(testName = "Test Case 27871:Company- Insurance Company: CRUD", description = "Company- Insurance Company: CRUD" )
	public void testCompanyInsuranceCompanyCRUD() throws Exception {

		final String insurancecompany = "testinsurancecompany";
		final String insurancecompanyaddress = "First streen valley 23/75, New York";
		final String insurancecompanyemail = "olexandr.kramar@cyberiansoft.com";
		final String insurancecompanyphone = "654654654";
		final String insurancecompanyaccountingid = "testID";
		final String insurancecompanyaccountingid2 = "testID2";
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		InsuranceCompaniesWePpage insurancecompaniespage = companypage.clickInsuranceCompaniesLink();
		if (insurancecompaniespage.isInsuranceCompanyExists(insurancecompany)) {
			insurancecompaniespage.deleteInsuranceCompany(insurancecompany);
		}
		
		insurancecompaniespage.clickAddInsuranceCompanyButton();
		insurancecompaniespage.createNewInsuranceCompany(insurancecompany);
		
		insurancecompaniespage.clickEditInsuranceCompany(insurancecompany);
		Assert.assertEquals(insurancecompany, insurancecompaniespage.getNewInsuranceCompanyName());
		final String insurancecompanyedited = insurancecompany + "edited";
		insurancecompaniespage.setNewInsuranceCompanyName(insurancecompanyedited);
		insurancecompaniespage.setNewInsuranceCompanyAddress(insurancecompanyaddress);
		insurancecompaniespage.setNewInsuranceCompanyEmail(insurancecompanyemail);
		insurancecompaniespage.setNewInsuranceCompanyPhone(insurancecompanyphone);
		insurancecompaniespage.setNewInsuranceCompanyAccountingID(insurancecompanyaccountingid);
		insurancecompaniespage.setNewInsuranceCompanyAccountingID2(insurancecompanyaccountingid2);
		insurancecompaniespage.clickAddInsuranceCompanyCancelButton();
		
		Assert.assertEquals("", insurancecompaniespage.getTableInsuranceCompanyAddress(insurancecompany).trim());
		Assert.assertEquals("", insurancecompaniespage.getTableInsuranceCompanyEmail(insurancecompany).trim());
		Assert.assertEquals("", insurancecompaniespage.getTableInsuranceCompanyPhone(insurancecompany).trim());
		
		insurancecompaniespage.clickEditInsuranceCompany(insurancecompany);
		Thread.sleep(1000);
		Assert.assertEquals(insurancecompany, insurancecompaniespage.getNewInsuranceCompanyName());
		insurancecompaniespage.setNewInsuranceCompanyName(insurancecompanyedited);
		insurancecompaniespage.setNewInsuranceCompanyAddress(insurancecompanyaddress);
		insurancecompaniespage.setNewInsuranceCompanyEmail(insurancecompanyemail);
		insurancecompaniespage.setNewInsuranceCompanyPhone(insurancecompanyphone);
		insurancecompaniespage.setNewInsuranceCompanyAccountingID(insurancecompanyaccountingid);
		insurancecompaniespage.setNewInsuranceCompanyAccountingID2(insurancecompanyaccountingid2);
		insurancecompaniespage.clickAddInsuranceCompanyOKButton();

		Assert.assertEquals(insurancecompanyaddress, insurancecompaniespage.getTableInsuranceCompanyAddress(insurancecompanyedited));
		Assert.assertEquals(insurancecompanyemail, insurancecompaniespage.getTableInsuranceCompanyEmail(insurancecompanyedited));
		Assert.assertEquals(insurancecompanyphone, insurancecompaniespage.getTableInsuranceCompanyPhone(insurancecompanyedited));
		
		insurancecompaniespage.deleteInsuranceCompanyAndCancelDeleting(insurancecompanyedited);
		insurancecompaniespage.deleteInsuranceCompany(insurancecompanyedited);
		Assert.assertFalse(insurancecompaniespage.isInsuranceCompanyExists(insurancecompanyedited));
	}

	@Test(testName = "Test Case 27876:Company-Teams: CRUD", description = "Company-Teams: CRUD" )
	public void testCompanyTeamsCRUD() throws Exception {

		final String team = "Testteam";
		final String teamdesc = "Test team description";
		final String teamid = "testID";
		final String teamtimezone = "(UTC+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius";
		final String teamarea = "QA Area";
		final String teamtimesheettype = "Day Type";
		final String teamdefaultlocation = "Time_Reports_01";
		final String teamadditionallocation = "Default Location";
		final String teamtype = "Vendor";
		
		final String teamcompany= "SyberianSoft";
		final String teamaddress = "First streen valley 23/75, New York";
		final String teamcity= "New York";
		final String teamcountry= "United States";
		final String teamstate= "Florida";
		final String teamzip = "39061";
		final String teamemail = "olexandr.kramar@cyberiansoft.com";
		final String teamphone = "654654654";
		final String teamedited = team + "edited";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		TeamsWebPage teamspage = companypage.clickTeamsLink();
		teamspage.makeSearchPanelVisible().setTeamLocationSearchCriteria(team).clickFindButton();
		teamspage.deleteTeamIfExists(team);
		teamspage.deleteTeamIfExists(teamedited);
		teamspage.createNewTeam(team , "Default area");	
		teamspage.setTeamLocationSearchCriteria(team).clickFindButton();
		
		NewTeamsDialogWebPage newteamsdialog = teamspage.clickEditTeam(team);
		Assert.assertEquals(team, newteamsdialog.getNewTeamName());
		
		newteamsdialog.setNewTeamName(teamedited).selectTeamTimezone(teamtimezone).setNewTeamDescription(teamdesc)
			.setNewTeamAccountingID(teamid).selectTeamArea(teamarea)
			.selectTeamTimesheetType(teamtimesheettype)
			.selectTeamDefaultRepairLocation(teamdefaultlocation).
			selectTeamAdditionalRepairLocation(teamadditionallocation)
			.selectTeamType(teamtype).setNewTeamCompany(teamcompany).setNewTeamAddress(teamaddress).setNewTeamCity(teamcity)
			.selectTeamCountry(teamcountry).selectTeamState(teamstate).setNewTeamZip(teamzip).setNewTeamEmail(teamemail)
			.setNewTeamPhone(teamphone).clickAddTeamCancelButton();	
		
		Assert.assertEquals("Internal", teamspage.getTableTeamType(team).trim());
		Assert.assertEquals("", teamspage.getTableTeamLocation(team).trim());
		Assert.assertEquals("Default area", teamspage.getTableTeamArea(team).trim());
		Assert.assertEquals("", teamspage.getTableTeamTimesheetType(team).trim());
		Assert.assertEquals("(UTC-08:00) Pacific Time (US & Canada)", teamspage.getTableTeamTimeZone(team).trim());
		Assert.assertEquals("", teamspage.getTableTeamDescription(team).trim());

		newteamsdialog = teamspage.clickEditTeam(team);
		Thread.sleep(1000);
		newteamsdialog.setNewTeamName(teamedited).selectTeamTimezone(teamtimezone).setNewTeamDescription(teamdesc).setNewTeamAccountingID(teamid)
			.selectTeamArea(teamarea).selectTeamTimesheetType(teamtimesheettype).selectTeamDefaultRepairLocation(teamdefaultlocation)
			.selectTeamAdditionalRepairLocation(teamadditionallocation).selectTeamType(teamtype).setNewTeamCompany(teamcompany)
			.setNewTeamAddress(teamaddress).setNewTeamCity(teamcity).selectTeamCountry(teamcountry).selectTeamState(teamstate)
			.setNewTeamZip(teamzip).setNewTeamEmail(teamemail).setNewTeamPhone(teamphone).clickAddTeamOKButton();
		
		Assert.assertEquals(teamtype, teamspage.getTableTeamType(teamedited).trim());
		Assert.assertEquals(teamdefaultlocation, teamspage.getTableTeamLocation(teamedited).trim());
		Assert.assertEquals(teamarea, teamspage.getTableTeamArea(teamedited).trim());
		Assert.assertEquals(teamtimesheettype, teamspage.getTableTeamTimesheetType(teamedited).trim());
		Assert.assertEquals(teamtimezone, teamspage.getTableTeamTimeZone(teamedited).trim());
		Assert.assertEquals(teamdesc, teamspage.getTableTeamDescription(teamedited).trim());
		
		
		teamspage.deleteTeamAndCancelDeleting(teamedited);
		teamspage.deleteTeam(teamedited);
		Assert.assertFalse(teamspage.isTeamExists(teamedited));
	}
	
	@Test(testName = "Test Case 27877:Company- Jobs: CRUD", description = "Company- Jobs: CRUD")
	public void testCompanyJobsCRUD() throws Exception {

		
		final String _job = "Test job";
		final String jobdesc = "Test job description";
		final String customer = "001 - Test Company";
		final String parentcustomer = "002 - Test Company";
		final String jobaccid = "testID";
		final String jobacc2id = "testID2";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		JobsWebPage jobspage = companypage.clickJobsLink();
		if (jobspage.isJobExists(_job)) {
			jobspage.deleteJob(_job);
		}		
		
		jobspage.createNewJob(_job);
		jobspage.clickEditJob(_job);
		Assert.assertEquals(_job, jobspage.getNewJobName());
		final String jobedited = _job + "edited";
		jobspage.setNewJobName(jobedited);
		jobspage.setNewJobDescription(jobdesc);
		jobspage.selectJobClient(customer);
		jobspage.selectJobParentClient(parentcustomer);
		jobspage.setNewJobStartDate(BackOfficeUtils.getCurrentDateFormatted());
		jobspage.setNewJobEndDate(BackOfficeUtils.getTomorrowDateFormatted());
		jobspage.setNewJobAccountingID(jobaccid);
		jobspage.setNewJobAccountingID(jobacc2id);
			
		jobspage.clickAddJobCancelButton();

		Assert.assertEquals("", jobspage.getTableJobDescription(_job).trim());
		Assert.assertEquals("", jobspage.getTableJobClient(_job).trim());
		Assert.assertEquals("", jobspage.getTableJobStartDate(_job).trim());
		Assert.assertEquals("", jobspage.getTableJobEndDate(_job).trim());
		Assert.assertEquals("", jobspage.getTableJobAccountingID(_job).trim());
		Assert.assertEquals("", jobspage.getTableJobAccountingID2(_job).trim());

		jobspage.clickEditJob(_job);
		Thread.sleep(2000);
		jobspage.setNewJobName(jobedited);
		jobspage.setNewJobDescription(jobdesc);
		jobspage.selectJobClient(customer);
		jobspage.selectJobParentClient(parentcustomer);
		jobspage.setNewJobStartDate(BackOfficeUtils.getCurrentDateFormatted());
		jobspage.setNewJobEndDate(BackOfficeUtils.getTomorrowDateFormatted());
		jobspage.setNewJobAccountingID(jobaccid);
		jobspage.setNewJobAccountingID2(jobacc2id);	
		jobspage.clickAddJobOKButton();

		Assert.assertEquals(jobdesc, jobspage.getTableJobDescription(jobedited).trim());
		Assert.assertEquals(customer, jobspage.getTableJobClient(jobedited).trim());
		Assert.assertEquals(BackOfficeUtils.getShortCurrentDateFormatted(), jobspage.getTableJobStartDate(jobedited).trim());
		Assert.assertEquals(BackOfficeUtils.getShortTomorrowDateFormatted(), jobspage.getTableJobEndDate(jobedited).trim());
		Assert.assertEquals(jobaccid, jobspage.getTableJobAccountingID(jobedited).trim());
		Assert.assertEquals(jobacc2id, jobspage.getTableJobAccountingID2(jobedited).trim());;
		
		
		jobspage.deleteJobAndCancelDeleting(jobedited);
		jobspage.deleteJob(jobedited);
		Assert.assertFalse(jobspage.isJobExists(jobedited));
	}
	
	@Test(testName = "Test Case 27878:Company- Service Advisors: CRUD", description = "Company- Service Advisors: CRUD" )
	public void testCompanyServiceAdvisorsCRUD() throws Exception {


		final String email = "test123CD@domain.com";
		final String psw = "111aaa";
		final String customer = "001 - Test Company";
		final String firstname = "test123CDF";
		final String lastname = "test123CDF";
		final String role = "SalesPerson";
		
		final String serviceadvisorcompany= "SyberianSoft";
		final String serviceadvisoraddress = "First streen valley 23/75";
		final String serviceadvisorcity= "New York";
		final String serviceadvisorcountry= "United States";
		final String serviceadvisorstate= "Florida";
		final String serviceadvisorzip = "39061";
		final String serviceadvisorphone = "654654654";
		final String serviceadvisoraccid= "65";
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceAdvisorsWebPage serviceadvisorspage = companypage.clickServiceAdvisorsLink();
		serviceadvisorspage.makeSearchPanelVisible();
		serviceadvisorspage.setUserSearchCriteria(firstname + " " + lastname);
		serviceadvisorspage.clickFindButton();
		
		if (serviceadvisorspage.isServiceAdvisorExists(firstname, lastname)) {
			serviceadvisorspage.deleteServiceAdvisor(firstname, lastname);
		}
		serviceadvisorspage.clickServiceAdvisorAddButton();
		serviceadvisorspage.createNewServiceAdvisor(email, firstname, lastname, customer, role);
		serviceadvisorspage.clickEditServiceAdvisor(firstname, lastname);
		serviceadvisorspage.setNewServiceAdvisorCompany(serviceadvisorcompany);
		serviceadvisorspage.setNewServiceAdvisorAddress(serviceadvisoraddress);
		serviceadvisorspage.setNewServiceAdvisorCity(serviceadvisorcity);
		serviceadvisorspage.selectNewServiceAdvisorCountry(serviceadvisorcountry);
		serviceadvisorspage.selectNewServiceAdvisorState(serviceadvisorstate);
		serviceadvisorspage.setNewServiceAdvisorZip(serviceadvisorzip);
		serviceadvisorspage.setNewServiceAdvisorPhone(serviceadvisorphone);
		serviceadvisorspage.setNewServiceAdvisorAccountingID(serviceadvisoraccid);
		serviceadvisorspage.clickNewServiceAdvisorCancelButton();
		
		Assert.assertEquals(firstname + " " + lastname, serviceadvisorspage.getTableServiceAdvisorFullName(firstname, lastname));
		Assert.assertEquals(email, serviceadvisorspage.getTableServiceAdvisorEmail(firstname, lastname));
		Assert.assertEquals("..., NT", serviceadvisorspage.getTableServiceAdvisorAddress(firstname, lastname).trim());
		Assert.assertEquals("", serviceadvisorspage.getTableServiceAdvisorPhone(firstname, lastname).trim());
		Assert.assertEquals(role, serviceadvisorspage.getTableServiceAdvisorRoles(firstname, lastname));
		Assert.assertEquals("", serviceadvisorspage.getTableServiceAdvisorAccountingID(firstname, lastname).trim());
		
		serviceadvisorspage.clickEditServiceAdvisor(firstname, lastname);
		serviceadvisorspage.setNewServiceAdvisorCompany(serviceadvisorcompany);
		serviceadvisorspage.setNewServiceAdvisorAddress(serviceadvisoraddress);
		serviceadvisorspage.setNewServiceAdvisorCity(serviceadvisorcity);
		serviceadvisorspage.selectNewServiceAdvisorCountry(serviceadvisorcountry);
		serviceadvisorspage.selectNewServiceAdvisorState(serviceadvisorstate);
		serviceadvisorspage.setNewServiceAdvisorZip(serviceadvisorzip);
		serviceadvisorspage.setNewServiceAdvisorPhone(serviceadvisorphone);
		serviceadvisorspage.setNewServiceAdvisorAccountingID(serviceadvisoraccid);
		serviceadvisorspage.clickNewServiceAdvisorOKButton();
		
		Assert.assertEquals(firstname + " " + lastname, serviceadvisorspage.getTableServiceAdvisorFullName(firstname, lastname));
		Assert.assertEquals(email, serviceadvisorspage.getTableServiceAdvisorEmail(firstname, lastname));
		final String fulladdress = serviceadvisoraddress + ", " + serviceadvisorcity + ", FL, " + serviceadvisorzip;
		Assert.assertEquals(fulladdress, serviceadvisorspage.getTableServiceAdvisorAddress(firstname, lastname).trim());
		Assert.assertEquals(serviceadvisorphone, serviceadvisorspage.getTableServiceAdvisorPhone(firstname, lastname).trim());
		Assert.assertEquals(role, serviceadvisorspage.getTableServiceAdvisorRoles(firstname, lastname));
		Assert.assertEquals(serviceadvisoraccid, serviceadvisorspage.getTableServiceAdvisorAccountingID(firstname, lastname).trim());
		
		serviceadvisorspage.deleteServiceAdvisorAndCancelDeleting(firstname, lastname);
		serviceadvisorspage.deleteServiceAdvisor(firstname, lastname);
		serviceadvisorspage.setUserSearchCriteria(firstname + " " + lastname);
		serviceadvisorspage.clickFindButton();
		Assert.assertFalse(serviceadvisorspage.isServiceAdvisorExists(firstname, lastname));		
	}
	
	@Test(testName = "Test Case 28114:Company- Service Contract Types: CRUD", description = "Company- Service Contract Types: CRUD")
	public void testCompanyServiceContractTypesCRUD() throws Exception {


		final String contracttype = "test123CD";
				
		final String contracttypeedited = "test123CD Edited";
		final String contracttypedesc = "Test description";
		final String contracttypeaccid = "123";
		final String contracttypeaccid2 = "456";
		final String contracttypeprice = "112";
		final String contracttypesalesprice = "223";
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceContractTypesWebPage servicecontracttypespage = companypage.clickServiceContractTypesLink();
		if (servicecontracttypespage.isServiceContractTypeExists(contracttype)) {
			servicecontracttypespage.deleteServiceContractType(contracttype);
		}
		if (servicecontracttypespage.isServiceContractTypeExists(contracttypeedited)) {
			servicecontracttypespage.deleteServiceContractType(contracttypeedited);
		}
		servicecontracttypespage.clickAddServiceContractTypeButton();
		servicecontracttypespage.createNewServiceContractType(contracttype);
		servicecontracttypespage.clickEditServiceContractType(contracttype);
		servicecontracttypespage.setNewServiceContractTypeName(contracttypeedited);
		servicecontracttypespage.setNewServiceContractTypeDescription(contracttypedesc);
		servicecontracttypespage.setNewServiceContractTypeAccountingID(contracttypeaccid);
		servicecontracttypespage.setNewServiceContractTypeAccountingID2(contracttypeaccid2);
		servicecontracttypespage.setNewServiceContractTypePrice(contracttypeprice);
		servicecontracttypespage.setNewServiceContractTypeSalesPrice(contracttypesalesprice);
		servicecontracttypespage.clickNewServiceContractTypeCancelButton();
		
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeDescription(contracttype).trim());
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypePrice(contracttype).trim());
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeSalesPrice(contracttype).trim());
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeAccID(contracttype).trim());
		Assert.assertEquals("", servicecontracttypespage.getTableServiceContractTypeAccID2(contracttype).trim());
		
		servicecontracttypespage.clickEditServiceContractType(contracttype);
		servicecontracttypespage.setNewServiceContractTypeName(contracttypeedited);
		servicecontracttypespage.setNewServiceContractTypeDescription(contracttypedesc);
		servicecontracttypespage.setNewServiceContractTypeAccountingID(contracttypeaccid);
		servicecontracttypespage.setNewServiceContractTypeAccountingID2(contracttypeaccid2);
		servicecontracttypespage.setNewServiceContractTypePrice(contracttypeprice);
		servicecontracttypespage.setNewServiceContractTypeSalesPrice(contracttypesalesprice);
		servicecontracttypespage.clickNewServiceContractTypeOKButton();
		
		Assert.assertEquals(contracttypedesc, servicecontracttypespage.getTableServiceContractTypeDescription(contracttypeedited).trim());
		Assert.assertEquals(BackOfficeUtils.getFullPriceRepresentation(contracttypeprice), servicecontracttypespage.getTableServiceContractTypePrice(contracttypeedited).trim());
		Assert.assertEquals(BackOfficeUtils.getFullPriceRepresentation(contracttypesalesprice), servicecontracttypespage.getTableServiceContractTypeSalesPrice(contracttypeedited).trim());
		Assert.assertEquals(contracttypeaccid, servicecontracttypespage.getTableServiceContractTypeAccID(contracttypeedited).trim());
		Assert.assertEquals(contracttypeaccid2, servicecontracttypespage.getTableServiceContractTypeAccID2(contracttypeedited).trim());
		
		servicecontracttypespage.deleteServiceContractTypeAndCancelDeleting(contracttypeedited);
		servicecontracttypespage.deleteServiceContractType(contracttypeedited);
		Assert.assertFalse(servicecontracttypespage.isServiceContractTypeExists(contracttype));
	}
	
	@Test(testName = "Test Case 28119:Company- Price Matrix: CRUD", description = "Company- Price Matrix: CRUD")
	public void testCompanyPriceMatrixCRUD() throws Exception {


		final String pricematrixname = "test123CD";
				
		final String pricematrixnameedited = "test123CD Edited";
		final String pricematrixservice = "VD_PriceMatrix";
		final String pricematrixtype = "Labor";
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PriceMatricesWebPage pricematricespage = companypage.clickPriceMatricesLink();
		
		if (pricematricespage.isPriceMatrixExists(pricematrixname)) {
			pricematricespage.deletePriceMatrix(pricematrixname);
		}
		
		pricematricespage.clickAddPriceMarixButton();
		pricematricespage.setPriceMarixName(pricematrixname);
		pricematricespage.saveNewPriceMatrix();
		
		pricematricespage.clickEditPriceMatrix(pricematrixname);
		pricematricespage.setPriceMarixName(pricematrixnameedited);
		pricematricespage.selectPriceMarixService(pricematrixservice);
		pricematricespage.selectPriceMarixType(pricematrixtype);
		pricematricespage.clickCancelNewPriceMatrix();
		Assert.assertEquals("_testStas1", pricematricespage.getTablePriceMatrixService(pricematrixname));
		Assert.assertEquals("Money", pricematricespage.getTablePriceMatrixType(pricematrixname));
		
		pricematricespage.clickEditPriceMatrix(pricematrixname);
		pricematricespage.setPriceMarixName(pricematrixnameedited);
		pricematricespage.selectPriceMarixService(pricematrixservice);
		pricematricespage.selectPriceMarixType(pricematrixtype);
		pricematricespage.saveNewPriceMatrix();
		Assert.assertEquals(pricematrixservice, pricematricespage.getTablePriceMatrixService(pricematrixnameedited));
		Assert.assertEquals(pricematrixtype, pricematricespage.getTablePriceMatrixType(pricematrixnameedited));
		
		pricematricespage.deletePriceMatrixAndCancelDeleting(pricematrixnameedited);
		pricematricespage.deletePriceMatrix(pricematrixnameedited);
		Assert.assertFalse(pricematricespage.isPriceMatrixExists(pricematrixnameedited));
	}
	
	@Test(testName = "Test Case 28122:Company - Invoice type: CRUD", description = "Company- Invoice type: CRUD")
	public void testCompanyInvoiceTypeCRUD() throws Exception {


		final String invoicetype = "test123CD";
		final String invoicetypeedited = "test123CD edited";		
		final String invoicetypedesc = "Test description";
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InvoiceTypesWebPage invoicestypespage = companypage.clickInvoiceTypesLink();
		
		if(invoicestypespage.isInvoiceTypeExists(invoicetype)) {
			invoicestypespage.deleteInvoiceType(invoicetype);
		}
		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicestypespage.clickAddInvoiceTypeButton();
		newinvoicetypedialog.createInvoiceType(invoicetype);
		newinvoicetypedialog = invoicestypespage.clickEditInvoiceType(invoicetype);
		newinvoicetypedialog.setInvoiceTypeName(invoicetypeedited);
		newinvoicetypedialog.setInvoiceTypeDescription(invoicetypedesc);
		newinvoicetypedialog.clickCancelAddInvoiceTypeButton();
		Assert.assertEquals("", invoicestypespage.getTableInvoiceTypeDescription(invoicetype).trim());
		
		newinvoicetypedialog = invoicestypespage.clickEditInvoiceType(invoicetype);
		newinvoicetypedialog.setInvoiceTypeName(invoicetypeedited);
		newinvoicetypedialog.setInvoiceTypeDescription(invoicetypedesc);
		newinvoicetypedialog.clickOKAddInvoiceTypeButton();
		Assert.assertEquals(invoicetypedesc, invoicestypespage.getTableInvoiceTypeDescription(invoicetypeedited).trim());
		
		invoicestypespage.deleteInvoiceTypeAndCancelDeleting(invoicetypeedited);
		invoicestypespage.deleteInvoiceType(invoicetypeedited);
		Assert.assertFalse(invoicestypespage.isInvoiceTypeExists(invoicetypeedited));
	}
	
	@Test(testName = "Test Case 28130:Company - Service Request Type: CRUD", description = "Company- Service Request Type: CRUD")
	public void testCompanyServiceRequestTypeCRUD() throws Exception {


		final String srtype = "test123CD";
		final String srtypeedited = "test123CD edited";		
		final String srtypedesc = "Test description";
		final String srtypeteam = "01_TimeRep_team";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage servicerequesttypespage= companypage.clickServiceRequestTypesLink();
		if (servicerequesttypespage.isServiceRequestTypeExists(srtype)) {
			servicerequesttypespage.deleteServiceRequestType(srtype);
		}
		servicerequesttypespage.clickAddServiceRequestTypeButton();
		servicerequesttypespage.createNewServiceRequestType(srtype);
		
		servicerequesttypespage.clickEditServiceRequestType(srtype);
		servicerequesttypespage.setNewServiceRequestTypeName(srtypeedited);
		servicerequesttypespage.setNewServiceRequestTypeDescription(srtypedesc);
		servicerequesttypespage.selectNewServiceRequestTypeTeam(srtypeteam);
		servicerequesttypespage.clickNewServiceRequestTypeCancelButton();
		Assert.assertEquals("", servicerequesttypespage.getTableServiceRequestTypeDescription(srtype).trim());
		
		
		servicerequesttypespage.clickEditServiceRequestType(srtype);
		servicerequesttypespage.setNewServiceRequestTypeName(srtypeedited);
		servicerequesttypespage.setNewServiceRequestTypeDescription(srtypedesc);
		servicerequesttypespage.selectNewServiceRequestTypeTeam(srtypeteam);
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();
		Assert.assertEquals(srtypedesc, servicerequesttypespage.getTableServiceRequestTypeDescription(srtypeedited));
		
		servicerequesttypespage.deleteServiceRequestTypeAndCancelDeleting(srtypeedited);
		servicerequesttypespage.deleteServiceRequestType(srtypeedited);
		Assert.assertFalse(servicerequesttypespage.isServiceRequestTypeExists(srtypeedited));
	}
	
	@Test(testName = "Test Case 28182:Company - Email Templates: CRUD", description = "Company- Email Templates: CRUD")
	public void testCompanyEmailTemplatesCRUD() throws Exception {


		final String templatename = "test template";
		final String templatenameedited = "new test template";
		final String templatesubject = "test subject";	
		final String templatesubjectedited = "new test subject";
		final String templatebody = "Test/nbody";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		EmailTemplatesWebPage emailtemplatespage = companypage.clickEmailTemplatesLink();
		if (emailtemplatespage.isEmailTemplateExists(templatename)) {
			emailtemplatespage.deleteEmailTemplate(templatename);
		}
		
		emailtemplatespage.clickAddMailTemplateButton();
		emailtemplatespage.createNewEmailTemplate(templatename, templatesubject);
		emailtemplatespage.clickEditEmailTemplate(templatename);
		emailtemplatespage.setNewEmailTemplateName(templatenameedited);
		emailtemplatespage.setNewEmailTemplateSubject(templatesubjectedited);
		emailtemplatespage.setNewEmailTemplateBody(templatebody);
		emailtemplatespage.clickNewEmailTemplateCancelButton();
		Assert.assertEquals(templatesubject, emailtemplatespage.getTableEmailTemplateSubject(templatename).trim());
		
		emailtemplatespage.clickEditEmailTemplate(templatename);
		emailtemplatespage.createNewEmailTemplate(templatenameedited, templatesubjectedited, templatebody);
		Assert.assertEquals(templatesubjectedited, emailtemplatespage.getTableEmailTemplateSubject(templatenameedited).trim());
		
		emailtemplatespage.deleteEmailTemplateAndCancelDeleting(templatenameedited);
		emailtemplatespage.deleteEmailTemplate(templatenameedited);
		Assert.assertFalse(emailtemplatespage.isEmailTemplateExists(templatenameedited));
	}
	
	@Test(testName = "Test Case 28218:Company - Print Server: CRUD", description = "Company- Print Server: CRUD")
	public void testCompanyPrintServerCRUD() throws Exception {


		final String printsrvname = "test print server";
		final String printsrvnameedited = "new test print server";
		final String printsrvdesc = "print server description";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();		
		PrintServersWebPage printserverspage = companypage.clickPrintServersLink();
		if (printserverspage.isPrintServerExists(printsrvname)) {
			printserverspage.deletePrintServer(printsrvname);
		}
		printserverspage.clickAddPrintServerButton();
		printserverspage.addNewPrintServer(printsrvname);
		printserverspage.clickEditPrintServer(printsrvname);
		
		printserverspage.setPrintServerName(printsrvnameedited);
		printserverspage.setPrintServerDescription(printsrvdesc);
		printserverspage.clickNewPrintServerCancelButton();
		Assert.assertEquals("", printserverspage.getTablePrintServerDescription(printsrvname).trim());
		
		
		printserverspage.clickEditPrintServer(printsrvname);
		printserverspage.addNewPrintServer(printsrvnameedited, printsrvdesc);
		Assert.assertEquals(printsrvdesc, printserverspage.getTablePrintServerDescription(printsrvnameedited).trim());
		
		printserverspage.deletePrintServerAndCancelDeleting(printsrvnameedited);
		printserverspage.deletePrintServer(printsrvnameedited);
		Assert.assertFalse(printserverspage.isPrintServerExists(printsrvnameedited));
	}

@Test(testName = "Test Case 28406:Company - Licence: CRUD", description = "Company- Licence: CRUD")
	public void testCompanyLicenceCRUD() throws Exception {


		final String licenceapp = "tf145";
		final String licencetype = "Support";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ManageLicencesWebPage managelicencespage = companypage.clickManageLicencesLink();
		managelicencespage.makeSearchPanelVisible();
		managelicencespage.selectLicenceSearchApplicationParameter(licenceapp);
		managelicencespage.clickFindButton();
		
		if (managelicencespage.isLicenceApplicationExists(licenceapp)) {
			managelicencespage.deleteLicenceApplication(licenceapp);
		}
		
		managelicencespage.clickAddManageLicenceButton();
//		managelicencespage.selectNewLicenceApplication(licenceapp);
		String deflicencetype = managelicencespage.getNewLicenceType();
		managelicencespage.clickNewLicenceOKButton();
		
		managelicencespage.clickEditLicenceApplication(licenceapp);
		managelicencespage.selectNewLicenceType(licencetype);
		managelicencespage.clickNewLicenceCancelButton();
		Assert.assertEquals(deflicencetype, managelicencespage.getTableLicenceType(licenceapp));
		
		managelicencespage.clickEditLicenceApplication(licenceapp);
		managelicencespage.selectNewLicenceType(licencetype);
		managelicencespage.clickNewLicenceOKButton();
		Assert.assertEquals(licencetype, managelicencespage.getTableLicenceType(licenceapp));
		
		managelicencespage.deleteLicenceApplicationAndCancelDeleting(licenceapp);
		managelicencespage.deleteLicenceApplication(licenceapp);
		Assert.assertFalse(managelicencespage.isLicenceApplicationExists(licenceapp));
	}
	
	@Test(testName = "Test Case 28422:Company- Timesheet Types: CRUD", description = "Company- Timesheet Types: CRUD")
	public void testCompanyTimesheetTypesCRUD() throws Exception {


		final String timesheettype = "test timesheet type";
		final String timesheettypeedited = "new test timesheet type";
		final String timesheettypedesc = "timesheet type description";	
		final String timesheettypeentrytype = "Time Code";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		TimesheetTypesWebPage timesheettypespage = companypage.clickTimesheetTypesLink();
		if (timesheettypespage.isTimesheetTypeExists(timesheettype)) {
			timesheettypespage.deleteTimesheetType(timesheettype);
		}
		timesheettypespage.clickAddTimesheetTypeButton();
		String tsdefaultentrytype = timesheettypespage.createNewTimesheetType(timesheettype);
		
		timesheettypespage.clickEditTimesheetType(timesheettype);
		timesheettypespage.setNewTimesheetTypeName(timesheettypeedited);
		timesheettypespage.setNewTimesheetTypeDescription(timesheettypedesc);
		timesheettypespage.selectNewTimesheetTypeEntryType(timesheettypeentrytype);
		timesheettypespage.clickNewTimesheetTypeCancelButton();
		Assert.assertEquals("",  timesheettypespage.getTableTimesheetTypeDescription(timesheettype).trim());
		Assert.assertEquals(tsdefaultentrytype,  timesheettypespage.getTableTimesheetTypeEntryType(timesheettype).trim());
		
		timesheettypespage.clickEditTimesheetType(timesheettype);
		timesheettypespage.setNewTimesheetTypeName(timesheettypeedited);
		timesheettypespage.setNewTimesheetTypeDescription(timesheettypedesc);
		timesheettypespage.selectNewTimesheetTypeEntryType(timesheettypeentrytype);
		timesheettypespage.clickNewTimesheetTypeOKButton();
		Assert.assertEquals(timesheettypedesc,  timesheettypespage.getTableTimesheetTypeDescription(timesheettypeedited).trim());
		//Assert.assertEquals(timesheettypeentrytype,  timesheettypespage.getTableTimesheetTypeEntryType(timesheettypeedited).trim());
		
		timesheettypespage.deleteTimesheetTypeAndCancelDeleting(timesheettypeedited);
		timesheettypespage.deleteTimesheetType(timesheettypeedited);
		Assert.assertFalse(timesheettypespage.isTimesheetTypeExists(timesheettypeedited));
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
		final String wotypesharingtype = "Team Sharing";
		final String[] wotypeoptions = {"Approval Required", "Allow Delete", "Block Identical VIN", "Block Identical Services", 
				"Vehicle History Enforced", "Total Sale Field Required", "Status Reason Required"};
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		WorkOrderTypesWebPage workordertypespage = companypage.clickWorkOrderTypesLink();
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
		for (int i = 0; i < wotypeoptions.length; i++) {
			workordertypespage.chechWOTypeOption(wotypeoptions[i]);
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
		for (int i = 0; i < wotypeoptions.length; i++) {
			workordertypespage.chechWOTypeOption(wotypeoptions[i]);
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
	
	@Test(testName = "Test Case 31504:Company - Service Packages: CRUD", description = "Company- Service Packages: CRUD")
	public void testCompanyServicePackagesCRUD() throws Exception {

		final String servicepackagename = "testpackage";
		final String servicepackagetype = "Package";
		final String servicepackageformtype = "Order";	
		final String servicepackagetechcomm = "2.00";	
		final String servicepackageadvcomm = "5.00";
		
		final String servicepackagenameed = "testpackage2";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServicePackagesWebPage servicepackagespage = companypage.clickServicePackagesLink();
		if (servicepackagespage.isServicePackageExists(servicepackagename)) {
			servicepackagespage.deleteServicePackage(servicepackagename);
		}
		if (servicepackagespage.isServicePackageExists(servicepackagenameed)) {
			servicepackagespage.deleteServicePackage(servicepackagenameed);
		}

		NewServicePackageDialogWebPage newservicepackagedialog = servicepackagespage.clickAddServicePackageButton();
		newservicepackagedialog.setNewServicePackageName(servicepackagename);
		newservicepackagedialog.selectNewServicePackageType(servicepackagetype);
		newservicepackagedialog.selectNewServicePackageFormType(servicepackageformtype);
		newservicepackagedialog.setNewServicePackageTechnicianCommissions(servicepackagetechcomm);
		newservicepackagedialog.setNewServicePackageAdvisorCommissions(servicepackageadvcomm);
		newservicepackagedialog.clickOKButton();
		
		Assert.assertEquals(servicepackagespage.getTableServicePackageType(servicepackagename), servicepackagetype);
		Assert.assertEquals(servicepackagespage.getTableServicePackageFormType(servicepackagename), servicepackageformtype);
		servicepackagespage.clickEditServicePackage(servicepackagename);
		
		newservicepackagedialog.setNewServicePackageName(servicepackagenameed);
		newservicepackagedialog.clickCancelButton();
		
		Assert.assertEquals(servicepackagespage.getTableServicePackageType(servicepackagename), servicepackagetype);
		Assert.assertEquals(servicepackagespage.getTableServicePackageFormType(servicepackagename), servicepackageformtype);
		servicepackagespage.clickEditServicePackage(servicepackagename);
		Assert.assertEquals(newservicepackagedialog.getNewServicePackageName(), servicepackagename);
		
		newservicepackagedialog.setNewServicePackageName(servicepackagenameed);
		newservicepackagedialog.clickOKButton();
		Assert.assertEquals(servicepackagespage.getTableServicePackageType(servicepackagenameed), servicepackagetype);
		Assert.assertEquals(servicepackagespage.getTableServicePackageFormType(servicepackagenameed), servicepackageformtype);
		
		servicepackagespage.deleteServicePackageAndCancelDeleting(servicepackagenameed);
		servicepackagespage.deleteServicePackage(servicepackagenameed);

	}
}
