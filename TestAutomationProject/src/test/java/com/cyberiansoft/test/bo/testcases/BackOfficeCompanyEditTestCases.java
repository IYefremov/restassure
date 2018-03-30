package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.Retry;
import com.cyberiansoft.test.ios_client.utils.MailChecker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class BackOfficeCompanyEditTestCases extends BaseTestCase {

	private String userName;
	private String userPassword;

	@BeforeMethod
	public void BackOfficeLogin(Method method) {
        System.out.printf("\n* Starting test : %s Method : %s\n", getClass(), method.getName());
        userName = BOConfigInfo.getInstance().getUserName();
        userPassword = BOConfigInfo.getInstance().getUserPassword();
        WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
        BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginPage.UserLogin(userName, userPassword);
	}

	@AfterMethod
	public void BackOfficeLogout() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
	}

	@Test(testName = "Test Case 27888:Company- Insurance Company: in Service Request Claim info Edit",
            description = "Company- Insurance Company: in Service Request Claim info Edit", retryAnalyzer = Retry.class)
	public void testCompanyInsuranceCompanyInServiceRequestClaimInfoEdit() throws InterruptedException {

		final String srcompany = "Alex SASHAZ";
		final String insurancecompanyname = "Oranta";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InsuranceCompaniesWePpage insurancecompaniespage = companypage.clickInsuranceCompaniesLink();
		if (insurancecompaniespage.isInsuranceCompanyExists(insurancecompanyname)) {
			insurancecompaniespage.deleteInsuranceCompany(insurancecompanyname);
		}
		insurancecompaniespage.clickAddInsuranceCompanyButton();
		insurancecompaniespage.createNewInsuranceCompany(insurancecompanyname);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestLink();

		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequesInsurance(insurancecompanyname);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		Assert.assertTrue(
				servicerequestslistpage.isInsuranceCompanyPresentForFirstServiceRequestFromList(insurancecompanyname));

		companypage = backofficeheader.clickCompanyLink();
		insurancecompaniespage = companypage.clickInsuranceCompaniesLink();
		insurancecompaniespage.deleteInsuranceCompany(insurancecompanyname);
	}

	// @Test(testName = "Test Case 27891:Company- Service Advisors:
	// Authentication", description = "Company- Service Advisors:
	// Authentication",retryAnalyzer=Retry.class)
	public void testCompanyServiceAdvisorsAuthentication() throws InterruptedException, IOException {

		// final String email = "test123CD@domain.com";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String confirmpsw = "111aaa";
		final String customer = "001 - Test Company";
		final String firstname = "test123CDF";
		final String lastname = "test123CDF";
		final String role = "SalesPerson";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceAdvisorsWebPage serviceadvisorspage = companypage.clickServiceAdvisorsLink();
		serviceadvisorspage.makeSearchPanelVisible();
		serviceadvisorspage.setUserSearchCriteria(firstname + " " + lastname);
		serviceadvisorspage.clickFindButton();
		if (serviceadvisorspage.isServiceAdvisorExists(firstname, lastname)) {
			serviceadvisorspage.deleteServiceAdvisor(firstname, lastname);
		}
		serviceadvisorspage.clickServiceAdvisorAddButton();
		long currtime = java.lang.System.currentTimeMillis();
		String usermail = "test.cyberiansoft+" + currtime + "@gmail.com";
		serviceadvisorspage.createNewServiceAdvisor(usermail, firstname, lastname, customer, role);

		backofficeheader.clickLogout();

		boolean search = false;
		String mailmessage = "";
		for (int i = 0; i < 4; i++) {
			if (!MailChecker.searchEmail("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro: REGISTRATION",
					"ReconPro@cyberianconcepts.com", "Please click link below to complete the registration process.")) {
				serviceadvisorspage.waitABit(60 * 500);
			} else {
				mailmessage = MailChecker.searchEmailAndGetMailMessage("test.cyberiansoft@gmail.com", "ZZzz11!!",
						"ReconPro: REGISTRATION", "ReconPro@cyberianconcepts.com");
				if (mailmessage.length() > 3) {
					search = true;
					break;
				}
			}
		}

		String confirmationurl = "";
		if (search) {

			confirmationurl = mailmessage.substring(mailmessage.indexOf("'") + 1, mailmessage.lastIndexOf("'"));

		}
		System.out.println("++++++" + confirmationurl);
		serviceadvisorspage.waitABit(60 * 1000);
		webdriver.get(confirmationurl);
		ConfirmPasswordWebPage confirmpasswordpage = PageFactory.initElements(webdriver, ConfirmPasswordWebPage.class);

		BackOfficeLoginWebPage loginpage = confirmpasswordpage.confirmUserPassword(confirmpsw);
		loginpage.UserLogin(usermail, confirmpsw);
		backofficeheader.clickHomeLink();

		backofficeheader.clickLogout();
		loginpage.UserLogin(userName, userPassword);
		companypage = backofficeheader.clickCompanyLink();
		serviceadvisorspage = companypage.clickServiceAdvisorsLink();
		serviceadvisorspage.makeSearchPanelVisible();
		serviceadvisorspage.setUserSearchCriteria(firstname + " " + lastname);
		serviceadvisorspage.clickFindButton();
		serviceadvisorspage.deleteServiceAdvisor(firstname, lastname);
	}

	@Test(testName = "Test Case 29764:Company- Users : Edit", description = "Company- Users : Edit", retryAnalyzer=Retry.class)
	public void testCompanyUsersEdit() throws Exception {

		final String usermail = "zakaulov.admin@cyberiansoft.com";
		final String userpsw = "test12345";
		final String userfirstname = "zalex";
		final String userlastname = "valex";
		final String usercompany = "CyberianSoft";
		final String userphone = "123123";
		final String useraccid = "8000001F-1370864710";
		final String useraddress = "5815 E. La Palma Ave.";
		final String usercity = "L'viv";
		final String userzip = "79031";
		final String usercomments = "Test User Comments";
		final String userrole = "Administrator";

		// Edited user
		final String userfirstnameed = "valex";
		final String userlastnameed = "zalex";
		final String usercompanyed = "CyberianSoftConcept";
		final String userphoneed = "123145";
		final String useraccided = "8999991F-1370864710";
		final String useraddressed = "5815 E. La Prima Ave.";
		final String usercityed = "New York";
		final String userziped = "79042";
		final String usercommentsed = "Test User Comments New";
		final String userroleed = "Work Manager";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		UsersWebPage userspage = companypage.clickUsersLink();

		userspage.makeSearchPanelVisible();

		userspage.setSearchUserParameter(usermail);
		userspage.clickFindButton();
		if (userspage.isUserActive(userfirstnameed, userlastnameed)) {
			userspage.archiveUser(userfirstnameed, userlastnameed);
		}
		if (userspage.isUserActive(userfirstname, userlastname)) {
			userspage.archiveUser(userfirstname, userlastname);
		}

		userspage.clickUserAddButton();
		userspage.createNewUser(usermail, userfirstname, userlastname, userrole);
		userspage.clickEditActiveUser(userfirstname, userlastname);
		userspage.setNewUserCompany(usercompany);
		userspage.setNewUserAddress(useraddress);
		userspage.setNewUserCity(usercity);
		userspage.setNewUserZip(userzip);
		userspage.setNewUserPhone(userphone);
		userspage.setNewUserAccountingID(useraccid);
		userspage.setNewUserComments(usercomments);
		userspage.clickNewUserOKButton();

		userspage.clickEditActiveUser(userfirstname, userlastname);
		Assert.assertEquals(userspage.getNewUserMail(), usermail);
		Assert.assertEquals(userspage.getNewUserFirstName(), userfirstname);
		Assert.assertEquals(userspage.getNewUserLastName(), userlastname);
		Assert.assertEquals(userspage.getNewUserCompany(), usercompany);
		Assert.assertEquals(userspage.getNewUserAddress(), useraddress);
		Assert.assertEquals(userspage.getNewUserCity(), usercity);
		Assert.assertEquals(userspage.getNewUserZip(), userzip);
		Assert.assertEquals(userspage.getNewUserPhone(), userphone);
		Assert.assertEquals(userspage.getNewUserAccountingID(), useraccid);
		Assert.assertEquals(userspage.getNewUserComments(), usercomments);
		Assert.assertTrue(userspage.isNewUserRoleSelected(userrole));
		Assert.assertFalse(userspage.isNewUserRoleSelected(userroleed));

		userspage.setNewUserFirstName(userfirstnameed);
		userspage.setNewUserLastName(userlastnameed);
		userspage.setNewUserCompany(usercompanyed);
		userspage.setNewUserAddress(useraddressed);
		userspage.setNewUserCity(usercityed);
		userspage.setNewUserZip(userziped);
		userspage.setNewUserPhone(userphoneed);
		userspage.setNewUserAccountingID(useraccided);
		userspage.setNewUserComments(usercommentsed);
		userspage.selectAllowCreatingSupportTickets();
		userspage.selectNewUserRole(userroleed);
		userspage.clickNewUserOKButton();

		userspage.setSearchUserParameter(usermail);
		userspage.clickFindButton();
		userspage.clickEditActiveUser(userfirstnameed, userlastnameed);
		Assert.assertEquals(userspage.getNewUserMail(), usermail);
		Assert.assertEquals(userspage.getNewUserFirstName(), userfirstnameed);
		Assert.assertEquals(userspage.getNewUserLastName(), userlastnameed);
		Assert.assertEquals(userspage.getNewUserCompany(), usercompanyed);
		Assert.assertEquals(userspage.getNewUserAddress(), useraddressed);
		Assert.assertEquals(userspage.getNewUserCity(), usercityed);
		Assert.assertEquals(userspage.getNewUserZip(), userziped);
		Assert.assertEquals(userspage.getNewUserPhone(), userphoneed);
		Assert.assertEquals(userspage.getNewUserAccountingID(), useraccided);
		Assert.assertEquals(userspage.getNewUserComments(), usercommentsed);
		Assert.assertTrue(userspage.isAllowCreatingSupportTicketsSelected());
		Assert.assertTrue(userspage.isNewUserRoleSelected(userrole));
		Assert.assertTrue(userspage.isNewUserRoleSelected(userroleed));
		userspage.clickNewUserCancelButton();

		userspage.archiveUser(userfirstnameed, userlastnameed);
	}

	@Test(testName = "Test Case 28316:Company - Clients : Edit", description = "Company - Clients : Edit")
	public void testCompanyClientsUsersEdit() throws Exception {

		final String clientmail = "123123213@domain.com";
		final String clientname = "Anaheim Hills Lexus";
		final String clientfirstname = "zalex";
		final String clientlastname = "valex";
		final String clientphone = "123123";
		final String clientaccid = "8000001F-1287569622";
		final String clientaddress = "5815 E. La Palma Ave.";
		final String clientcity = "asdfasd";
		final String clientzip = "12345";
		final String clientarea = "Default area";

		// Edited client
		final String clientmailed = "123123213@domaintest.com";
		final String clientnameed = "Anaheim Hills Ford";
		final String clientfirstnameed = "zalex2";
		final String clientlastnameed = "valex2";
		final String clientphoneed = "1";
		final String clientaccided = "8000001F-1370864711";
		final String clientaddressed = "E. La Palma Ave.";
		final String clientcityed = "L'viv";
		final String clientziped = "79031";
		final String clientareaed = "QA Area";

		final String retailcompanyname = clientfirstname + " " + clientlastname;
		final String retailcompanynameed = clientfirstnameed + " " + clientlastnameed;

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();

		clientspage.searchClientByName(clientfirstname);

		if (clientspage.isClientPresentInTable(retailcompanyname)) {
			clientspage.deleteClient(retailcompanyname);
		}

		if (clientspage.isClientPresentInTable(retailcompanynameed)) {
			clientspage.deleteClient(retailcompanynameed);
		}

		NewClientDialogWebPage newclientdialogpage = clientspage.clickAddClientButton();
		newclientdialogpage.switchToRetailCustomer();
		newclientdialogpage.setCompanyName(clientname);
		newclientdialogpage.setClientFirstName(clientfirstname);
		newclientdialogpage.setClientLastName(clientlastname);
		newclientdialogpage.setCompanyMail(clientmail);
		newclientdialogpage.setCompanyPhone(clientphone);
		newclientdialogpage.setCompanyAccountingID(clientaccid);
		newclientdialogpage.selectCompanyDefaultArea(clientarea);
		newclientdialogpage.setCompanyShipToAddress(clientaddress);
		newclientdialogpage.setCompanyShipToCity(clientcity);
		newclientdialogpage.setCompanyShipToZip(clientzip);
		newclientdialogpage.clickCopyToBillToButton();
		newclientdialogpage.clickOKButton();
		clientspage.waitABit(3000);
		newclientdialogpage = clientspage.clickEditClient(retailcompanyname);
		Assert.assertTrue(newclientdialogpage.isCompanyRetail());
		Assert.assertEquals(newclientdialogpage.getCompanyName(), clientname);
		Assert.assertEquals(newclientdialogpage.getClientFirstName(), clientfirstname);
		Assert.assertEquals(newclientdialogpage.getClientLastName(), clientlastname);

		newclientdialogpage.setCompanyName(clientnameed);
		newclientdialogpage.setClientFirstName(clientfirstnameed);
		newclientdialogpage.setClientLastName(clientlastnameed);
		newclientdialogpage.setCompanyMail(clientmailed);
		newclientdialogpage.setCompanyPhone(clientphoneed);
		newclientdialogpage.setCompanyAccountingID(clientaccided);
		newclientdialogpage.selectCompanyDefaultArea(clientareaed);
		newclientdialogpage.setCompanyShipToAddress(clientaddressed);
		newclientdialogpage.setCompanyShipToCity(clientcityed);
		newclientdialogpage.setCompanyShipToZip(clientziped);
		newclientdialogpage.clickOKButton();

		newclientdialogpage = clientspage.clickEditClient(retailcompanynameed);
		Assert.assertTrue(newclientdialogpage.isCompanyRetail());
		Assert.assertEquals(newclientdialogpage.getCompanyName(), clientnameed);
		Assert.assertEquals(newclientdialogpage.getClientFirstName(), clientfirstnameed);
		Assert.assertEquals(newclientdialogpage.getClientLastName(), clientlastnameed);
		Assert.assertEquals(newclientdialogpage.getCompanyMail(), clientmailed);
		Assert.assertEquals(newclientdialogpage.getCompanyPhone(), clientphoneed);
		Assert.assertEquals(newclientdialogpage.getCompanyAccountingID(), clientaccided);
		Assert.assertTrue(newclientdialogpage.isCompanyOptionChecked(clientareaed));
		Assert.assertEquals(newclientdialogpage.getCompanyShipToAddress(), clientaddressed);
		Assert.assertEquals(newclientdialogpage.getCompanyShipToCity(), clientcityed);
		Assert.assertEquals(newclientdialogpage.getCompanyShipToZip(), clientziped);
		newclientdialogpage.selectBillToAddress();
		Assert.assertEquals(newclientdialogpage.getCompanyBillToAddress(), clientaddress);
		Assert.assertEquals(newclientdialogpage.getCompanyBillToCity(), clientcity);
		Assert.assertEquals(newclientdialogpage.getCompanyBillToZip(), clientzip);
		newclientdialogpage.clickCancelButton();

		clientspage.deleteClient(retailcompanynameed);
	}

	@Test(testName = "Test Case 28319:Company - Employees : Edit", description = "Company - Employees : Edit", retryAnalyzer=Retry.class)
	public void testCompanyEmployeesEdit() throws Exception {

		final String employeeteam = "01_TimeRep_team";
		final String employeefirstname = "azalex";
		final String employeelastname = "avalex";
		final String employeepsw = "123123";
		final String employeehometeam = "Test Team";
		final String employeeaddress = "5815 E. La Palma Ave.";
		final String employeecity = "asdfasd";
		final String employeecountry = "United States";
		final String employeezip = "12345";
		final String employeephone = "123123";
		final String employeemail = "123123213@domain.com";
		final String employeeaccid = "8000001F-1287569622";
		final String employeestartdate = "10/28/2015";
		final String employeetype = "Employee";
		final String employeerole = "Employee";

		// Edited employee
		final String employeeteamed = "Default team";
		final String employeefirstnameed = "azalex2";
		final String employeelastnameed = "avalex2";
		final String employeepswed = "1234";
		final String employeehometeamed = "Team 2";
		final String employeeaddressed = "E. La Palma Ave.";
		final String employeecityed = "L'viv";
		final String employeecountryed = "Uruguay";
		final String employeeziped = "79031";
		final String employeephoneed = "1";
		final String employeemailed = "123123213@domaintest.com";
		final String employeeaccided = "8000001F-1287569655";
		final String employeestartdateed = "10/28/2014";
		final String employeetypeed = "Manager";
		final String employeeroleed = "Manager";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		EmployeesWebPage employeespage = companypage.clickEmployeesLink();

		employeespage.makeSearchPanelVisible();
		employeespage.setSearchUserParameter(employeelastname);
		employeespage.clickFindButton();

		if (employeespage.isActiveEmployeeExists(employeefirstname, employeelastname)) {
			employeespage.archiveEmployee(employeefirstname, employeelastname);
		}

		if (employeespage.isActiveEmployeeExists(employeefirstnameed, employeelastnameed)) {
			employeespage.archiveEmployee(employeefirstnameed, employeelastnameed);
		}

		NewEmployeeDialogWebPage newemployeedialog = employeespage.clickAddEmployeeButton();
		newemployeedialog.selectNewEmployeeTeam(employeeteam);
		newemployeedialog.setNewEmployeeFirstName(employeefirstname);
		newemployeedialog.setNewEmployeeLastName(employeelastname);
		newemployeedialog.setNewEmployeePassword(employeepsw);
		newemployeedialog.selectNewEmployeeHomeTeam(employeehometeam);
		newemployeedialog.setNewEmployeeAddress(employeeaddress);
		newemployeedialog.setNewEmployeeCity(employeecity);
		newemployeedialog.selectNewEmployeeCountry(employeecountry);
		newemployeedialog.setNewEmployeeZip(employeezip);
		newemployeedialog.setNewEmployeePhone(employeephone);
		newemployeedialog.setNewEmployeeMail(employeemail);
		newemployeedialog.setNewEmployeeAccountingID(employeeaccid);
		newemployeedialog.setNewEmployeeStartingDate(employeestartdate);
		newemployeedialog.selectNewEmployeeType(employeetype);
		newemployeedialog.selectEmployeeRole(employeerole);
		newemployeedialog.clickOKButton();

		newemployeedialog = employeespage.clickEditEmployee(employeefirstname, employeelastname);
		Assert.assertEquals(newemployeedialog.getNewEmployeeTeam(), employeeteam);
		Assert.assertEquals(newemployeedialog.getNewEmployeeFirstName(), employeefirstname);
		Assert.assertEquals(newemployeedialog.getNewEmployeeLastName(), employeelastname);
		Assert.assertTrue(newemployeedialog.isEmployeeRoleChecked(employeerole));

		newemployeedialog.selectNewEmployeeTeam(employeeteamed);
		newemployeedialog.setNewEmployeeFirstName(employeefirstnameed);
		newemployeedialog.setNewEmployeeLastName(employeelastnameed);
		newemployeedialog.setNewEmployeePassword(employeepswed);
		newemployeedialog.selectNewEmployeeHomeTeam(employeehometeamed);
		newemployeedialog.setNewEmployeeAddress(employeeaddressed);
		newemployeedialog.setNewEmployeeCity(employeecityed);
		newemployeedialog.selectNewEmployeeCountry(employeecountryed);
		newemployeedialog.setNewEmployeeZip(employeeziped);
		newemployeedialog.setNewEmployeePhone(employeephoneed);
		newemployeedialog.setNewEmployeeMail(employeemailed);
		newemployeedialog.setNewEmployeeAccountingID(employeeaccided);
		newemployeedialog.setNewEmployeeStartingDate(employeestartdateed);
		newemployeedialog.selectNewEmployeeType(employeetypeed);
		newemployeedialog.selectEmployeeRole(employeeroleed);
		newemployeedialog.clickOKButton();

		newemployeedialog = employeespage.clickEditEmployee(employeefirstnameed, employeelastnameed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeTeam(), employeeteamed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeFirstName(), employeefirstnameed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeLastName(), employeelastnameed);
		Assert.assertEquals(newemployeedialog.getNewEmployeePassword(), employeepswed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeHomeTeam(), employeehometeamed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeAddress(), employeeaddressed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeCity(), employeecityed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeCountry().trim(), employeecountryed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeZip(), employeeziped);
		Assert.assertEquals(newemployeedialog.getNewEmployeePhone(), employeephoneed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeMail(), employeemailed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeAccountingID(), employeeaccided);
		Assert.assertEquals(newemployeedialog.getNewEmployeeStartingDate(), employeestartdateed);
		Assert.assertEquals(newemployeedialog.getNewEmployeeType(), employeetypeed);
		Assert.assertTrue(newemployeedialog.isEmployeeRoleChecked(employeeroleed));
		Assert.assertTrue(newemployeedialog.isEmployeeRoleChecked(employeerole));
		newemployeedialog.clickCancelButton();

		employeespage.archiveEmployee(employeefirstnameed, employeelastnameed);
	}

	@Test(testName = "Test Case 28321:Company - Services: Edit", description = "Company - Services : Edit")
	public void testCompanyServicesEdit() throws Exception {

		final String servicename = "Test Bundle Service";
		final String servicetype = "Detail";
		final String servicedesc = "Test service description";
		final String serviceaccid = "8000001F-1287569622";
		final String serviceassignedto = "Custom Technician";

		// Edited employee
		final String servicenameed = "Test Bundle Service2";
		final String servicetypeed = "Dye";
		final String servicedesced = "New service description";
		final String serviceaccided = "12345";
		final String serviceassignedtoed = "(None)";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServicesWebPage servicespage = companypage.clickServicesLink();
	//	servicespage.makeSearchPanelVisible();
		servicespage.setServiceSearchCriteria(servicename);
		servicespage.clickFindButton();

		if (servicespage.isActiveServiceExists(servicename)) {
			servicespage.archiveService(servicename);
		}
		if (servicespage.isActiveServiceExists(servicenameed)) {
			servicespage.archiveService(servicenameed);
		}
		NewServiceDialogWebPage newservicedialog = servicespage.clickAddServiceButton();
		newservicedialog.setNewServiceName(servicename);
		newservicedialog.selectNewServiceType(servicetype);
		newservicedialog.setNewServiceDescription(servicedesc);
		newservicedialog.setNewServiceAccountingID(serviceaccid);
		newservicedialog.selectNewServiceAssignedTo(serviceassignedto);
		newservicedialog.clickOKButton();

		newservicedialog = servicespage.clickEditService(servicename);
		Assert.assertEquals(newservicedialog.getNewServiceName(), servicename);
		Assert.assertFalse(newservicedialog.isNewServiceMultipleSelected());

		newservicedialog.setNewServiceName(servicenameed);
		newservicedialog.selectNewServiceType(servicetypeed);
		newservicedialog.setNewServiceDescription(servicedesced);
		newservicedialog.setNewServiceAccountingID(serviceaccided);
		newservicedialog.selectNewServiceAssignedTo(serviceassignedtoed);
		newservicedialog.selectNewServiceMultiple();
		newservicedialog.clickOKButton();

		newservicedialog = servicespage.clickEditService(servicenameed);
		Assert.assertEquals(newservicedialog.getNewServiceName(), servicenameed);
		Assert.assertEquals(newservicedialog.getNewServiceType(), servicetypeed);
		Assert.assertEquals(newservicedialog.getNewServiceDescription(), servicedesced);
		Assert.assertEquals(newservicedialog.getNewServiceAccountingID(), serviceaccided);
		Assert.assertEquals(newservicedialog.getNewServiceAssignedTo(), serviceassignedtoed);
		Assert.assertTrue(newservicedialog.isNewServiceMultipleSelected());
		newservicedialog.clickCancelButton();

		servicespage.archiveService(servicenameed);
	}

	@Test(testName = "Test Case 28342:Company - Service Request Type: Service type at Service Request Edit", description = "Company - Service Request Type: Service type at Service Request Edit")
	public void testCompanyServiceRequestTypeServiceTypeAtServiceRequestEdit() throws Exception {

		final String srtype = "ta SR Type";
		final String srtypedesc = "Test description";
		final String srtypeteam = "01_TimeRep_team";
		final String srtypepackage = "TimeZone_Test";
		final String srtypepackagenew = "Test Service Package";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage servicerequesttypespage = companypage.clickServiceRequestTypesLink();
		if (servicerequesttypespage.isServiceRequestTypeExists(srtype)) {
			servicerequesttypespage.deleteServiceRequestType(srtype);
		}
		servicerequesttypespage.clickAddServiceRequestTypeButton();
		servicerequesttypespage.setNewServiceRequestTypeName(srtype);
		servicerequesttypespage.setNewServiceRequestTypeDescription(srtypedesc);
		servicerequesttypespage.selectNewServiceRequestTypeTeam(srtypeteam);
		servicerequesttypespage.selectNewServiceRequestTypePackage(srtypepackage);
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage srlistpage = operationspage.clickNewServiceRequestLink();
		srlistpage.selectAddServiceRequestsComboboxValue(srtype);
		srlistpage.clickAddServiceRequestButton();

		srlistpage.clickServiceEditButton();
		Assert.assertEquals(srlistpage.countAvailableServices(), 2);
		srlistpage.clickDoneButton();
		srlistpage.cancelNewServiceRequest();

		companypage = backofficeheader.clickCompanyLink();
		servicerequesttypespage = companypage.clickServiceRequestTypesLink();
		servicerequesttypespage.clickEditServiceRequestType(srtype);
		servicerequesttypespage.selectNewServiceRequestTypePackage(srtypepackagenew);
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();

		operationspage = backofficeheader.clickOperationsLink();
		srlistpage = operationspage.clickNewServiceRequestLink();
		srlistpage.selectAddServiceRequestsComboboxValue(srtype);
		srlistpage.clickAddServiceRequestButton();

		srlistpage.clickServiceEditButton();
		Assert.assertEquals(srlistpage.getAllAvailableServices(), "52");
		srlistpage.clickDoneButton();
		srlistpage.cancelNewServiceRequest();

		companypage = backofficeheader.clickCompanyLink();
		servicerequesttypespage = companypage.clickServiceRequestTypesLink();
		servicerequesttypespage.deleteServiceRequestType(srtype);
	}

	@Test(testName = "Test Case 29017:Company - Invoice Type: Client Edit", description = "Company - Invoice Type: Client Edit", retryAnalyzer=Retry.class)
	public void testCompanyInvoiceTypeClientEdit() throws Exception {

		final String invoicetype = "TestIntInvTeam";
		final String questiontemplate = "PrintIntForm";
		final String questionform = "IntForm";
		final String clientname = "002 - Test Company";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InvoiceTypesWebPage invoicestypespage = companypage.clickInvoiceTypesLink();
		if (invoicestypespage.isInvoiceTypeExists(invoicetype)) {
			invoicestypespage.deleteInvoiceType(invoicetype);
		}

		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicestypespage.clickAddInvoiceTypeButton();
		newinvoicetypedialog.setInvoiceTypeName(invoicetype);
		newinvoicetypedialog.waitABit(500);
		newinvoicetypedialog.selectVisibleCheckBox();
		newinvoicetypedialog.waitABit(500);
		newinvoicetypedialog.selectRequiredCheckBox();
		newinvoicetypedialog.selectQuestionsTemplate(questiontemplate);
		newinvoicetypedialog.selectQuestionForm(questionform);
		newinvoicetypedialog.clickOKAddInvoiceTypeButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicestypespage.clickClientsLinkForInvoiceType(invoicetype);
		invoicestypespage.addAssignedClient(clientname);
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(invoicetype);
		Assert.assertFalse(invoicestypespage.isAssignedClientSelected(clientname));
		invoicestypespage.addAssignedClient(clientname);
		invoicestypespage.clickUpdateClientsButton();
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(invoicetype);
		Assert.assertTrue(invoicestypespage.isAssignedClientSelected(clientname));
		invoicestypespage.deleteAssignedClient(clientname);
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(invoicetype);
		Assert.assertTrue(invoicestypespage.isAssignedClientSelected(clientname));
		invoicestypespage.deleteAssignedClient(clientname);
		invoicestypespage.clickUpdateClientsButton();
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(invoicetype);
		Assert.assertFalse(invoicestypespage.isAssignedClientSelected(clientname));
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);
	}

	@Test(testName = "Test Case 29759:Company- Clients: Services Update", description = "Company- Clients: Services Update", retryAnalyzer=Retry.class)
	public void testCompanyClientsServicesUpdate() throws Exception {

		final String clientname = "TestAlExTest";
		final String servicepackage = "Demo Service Package";
		final String servicepackagenew = "All Services";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();

		clientspage.makeSearchPanelVisible();
		clientspage.setClientSearchCriteria(clientname);
		clientspage.clickFindButton();
		if (clientspage.isClientPresentInTable(clientname)) {
			clientspage.deleteClient(clientname);
		}

		NewClientDialogWebPage newclientdialog = clientspage.clickAddClientButton();
		newclientdialog.createWholesaleClient(clientname);

		String mainWindowHandle = webdriver.getWindowHandle();
		clientspage.clickServicesLinkForClient(clientname);
		clientspage.selectClientServicePackage(servicepackage);
		List<WebElement> clienservicesselected = clientspage.getClientServicesTableRows();
		clientspage.closeClientServicesTab(mainWindowHandle);

		clientspage.clickServicesLinkForClient(clientname);
		Assert.assertTrue(clienservicesselected.retainAll(clientspage.getClientServicesTableRows()));
		clientspage.selectClientServicePackage(servicepackagenew);
		Assert.assertTrue(clientspage.getClientServicesTableRows().size() > 225);
		clientspage.closeClientServicesTab(mainWindowHandle);
	}

	@Test(testName = "Test Case 29761:Company- Clients: Client Users Search", description = "Company- Clients: Client Users Search", retryAnalyzer=Retry.class )
	public void testCompanyClientsClientUsersSearch() throws Exception {

		final String clientname = "AlExTest";
		final String firstsearchparemeter = "name";
		final String secondsearchparemeter = "test2";
		final String thirdsearchparemeter = "name@testmail.com";
		final String lastsearchparemeter = "test3";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.setClientSearchCriteria(clientname);
		clientspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		clientspage.clickClientUsersLinkForClient(clientname);
		clientspage.searchClientUser(firstsearchparemeter);
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 1);
		clientspage.searchClientUser(secondsearchparemeter);
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 1);
		clientspage.searchClientUser(thirdsearchparemeter);
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 1);
		clientspage.searchClientUser(lastsearchparemeter);
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 0);
		clientspage.closeClientServicesTab(mainWindowHandle);
	}

	@Test(testName = "Test Case 62293:Company: Inter Application Exchange Configuration - Sharing Work Order")
	public void testCompanySharingWorkOrder() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
			}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
	}
	
	@Test(testName = "Test Case 62296:Company: Inter Application Exchange Configuration - Sharing Work Order Add Rule Teams")
	public void testCompanySharingWorkOrderAddRuleTeams() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
			}
				
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Clients Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Teams Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Teams Include Selected)");
	}

	//todo fails
	@Test(testName = "Test Case 62297:Company: Inter Application Exchange Configuration - Sharing Work Order Add Rule Employees")
	public void testCompanySharingWorkOrderAddRuleEmployees() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
			}
				
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees","Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Employees (Employees Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees","Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Employees (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Employees Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Employees Include Selected)");
	}

	//todo fails
	@Test(testName = "Test Case 62295:Company: Inter Application Exchange Configuration - Sharing Work Order Add Rule Clients")
	public void testCompanySharingWorkOrderAddRuleClients() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
			}
				
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Clients (Clients Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Clients (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Clients Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Clients Include Selected)");
	}

	//todo fails
	@Test(testName = "Test Case 62298:Company: Inter Application Exchange Configuration - Sharing Work Order Add Rule Services")
	public void testCompanySharingWorkOrderAddRuleServices () throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
			}
				
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services","Services","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services","Services","Include Selected");
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Services Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Services Include Selected)");
	}

	//todo test fails
	@Test(testName = "Test Case 62299:Company: Inter Application Exchange Configuration - Sharing Work Order Add Rule Vehicle Parts")
	public void testCompanySharingWorkOrderAddRuleVehicleParts() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
			}
				
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST", "Work Order", "test2");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST (Work Order)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts","Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Vehicle Parts (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts","Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Vehicle Parts (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Vehicle Parts (Vehicle Parts Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Vehicle Parts (Vehicle Parts Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Vehicle Parts Include Selected)");
	}
	
	@Test(testName = "Test Case 62300:Company: Inter Application Exchange Configuration - Sharing Estimate")
	public void testCompanyExchangeConfigurationSharingEstimate() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)")){
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
			}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter, "The changes have not been applied");
		
		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
	} 

	@Test(testName = "Test Case 62301:Company: Inter Application Exchange Configuration - Sharing Estimate Add Rule Clients")
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleClients() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();
		
		if (interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
        Assert.assertNotEquals(entryTextBefore, entryTextAfter, "The changes have not been applied");

        interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");

        interApplicationExchangePage.expandFirstCompanyProfile();
        String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
        interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients",
                "Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The rule has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients","Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Clients (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Clients Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Clients Include Selected)");
	}
	
	@Test(testName = "Test Case 62302:Company: Inter Application Exchange Configuration - Sharing Estimate Add Rule Teams")
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleTeams() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)")){
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
			}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Clients Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Teams Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Teams Include Selected)");
	}

	@Test(testName = "Test Case 62303:Company: Inter Application Exchange Configuration - Sharing Estimate Add Rule Employees")
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleEmployees() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
        String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
        interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees",
                "Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
        Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The rule has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees","Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Employees (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Employees Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Employees Include Selected)");
	}

	@Test(testName = "Company: Inter Application Exchange Configuration - Sharing Estimate  Add Rule Services")
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleServices() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();
		
		if (interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
        String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
        interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services",
                "Services","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The rule has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services","Services","Include Selected");
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Services Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Services Include Selected)");
	}

	@Test(testName = "Test Case 62305:Company: Inter Application Exchange Configuration - Sharing Estimate Add Rule Vehicle Parts")
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleVehicleParts() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("Estimate JST for Name (Estimation)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		
		interApplicationExchangePage.expandFirstCompanyProfile();
        String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
        interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts",
                "Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Cancel");
        Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The rule has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts","Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Vehicle Parts (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Vehicle Parts (Vehicle Parts Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Vehicle Parts (Vehicle Parts Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox("Update");
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Vehicle Parts Include Selected)");
	}
	
	@Test(testName = "Test Case 62786:Company: Inter Application Exchange Configuration - Mapping Work Order")
	public void testCompanyExchangeConfigurationMappingWorkOrder() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();
		
		if (interApplicationExchangePage.checkEntryByName("WO JST for Name (Work Order)")) {
		interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name","Work Order","All Jay");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");   
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST for Name (Work Order)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name","Work Order","All Jay");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST for Name (Work Order)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
	}
	
	@Test(testName = "Test Case 62787:Company: Inter Application Exchange Configuration - Mapping Work Order Add Rule Clients")
	public void testCompanyExchangeConfigurationMappingWorkOrderAddRuleClients() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab("Sending");
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.checkEntryByName("WO JST for Name (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name","Work Order","All Jay");
		interApplicationExchangePage.clickProfileDetailsBox("Cancel");
		Assert.assertFalse(interApplicationExchangePage.checkEntryByName("WO JST for Name (Work Order)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name","Work Order","All Jay");
		interApplicationExchangePage.clickProfileDetailsBox("Insert");
		Assert.assertTrue(interApplicationExchangePage.checkEntryByName("WO JST for Name (Work Order)"));
		
		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit("test");
		interApplicationExchangePage.clickProfileEditBox("Cancel");
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);
		
		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox("Update");
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);
		
		interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
	}
}