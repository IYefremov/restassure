package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.validations.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.bo.BOCompanyEditData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BackOfficeCompanyEditTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOCompanyEditData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInsuranceCompanyInServiceRequestClaimInfoEdit(
			String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InsuranceCompaniesWebPage insurancecompaniespage = new InsuranceCompaniesWebPage(webdriver);
		companyWebPage.clickInsuranceCompaniesLink();
		if (insurancecompaniespage.insuranceCompanyExists(data.getInsuranceCompanyName())) {
			insurancecompaniespage.deleteInsuranceCompany(data.getInsuranceCompanyName());
		}
		insurancecompaniespage.clickAddInsuranceCompanyButton();
		insurancecompaniespage.createNewInsuranceCompany(data.getInsuranceCompanyName());
		OperationsWebPage operationspage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();

		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getServiceType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickClaimInfoEditButton();
		serviceRequestsListInteractions.selectServiceRequestInsurance(data.getInsuranceCompanyName());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		Assert.assertTrue(ServiceRequestsListVerifications
                .isInsuranceCompanyPresentForFirstServiceRequestFromList(data.getInsuranceCompanyName()));

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		insurancecompaniespage = new InsuranceCompaniesWebPage(webdriver);
		companyWebPage.clickInsuranceCompaniesLink();
		insurancecompaniespage.deleteInsuranceCompany(data.getInsuranceCompanyName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyUsersEdit(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();

        companyWebPage.clickUsersLink();
        UsersWebPage usersPage = new UsersWebPage(webdriver);

		usersPage.makeSearchPanelVisible();

		usersPage.setSearchUserParameter(data.getUserEmail());
		usersPage.clickFindButton();
		if (usersPage.isUserActive(data.getUserFirstNameEdited(), data.getUserLastNameEdited())) {
			usersPage.archiveUser(data.getUserFirstNameEdited(), data.getUserLastNameEdited());
		}
		if (usersPage.isUserActive(data.getUserFirstName(), data.getUserLastName())) {
			usersPage.archiveUser(data.getUserFirstName(), data.getUserLastName());
		}

		usersPage.clickUserAddButton();
		usersPage.createNewUser(data.getUserEmail(), data.getUserFirstName(), data.getUserLastName(), data.getUserRole());
		usersPage.clickEditActiveUser(data.getUserFirstName(), data.getUserLastName());
		usersPage.setNewUserCompany(data.getUserCompany());
		usersPage.setNewUserAddress(data.getUserAddress());
		usersPage.setNewUserCity(data.getUserCity());
		usersPage.setNewUserZip(data.getUserZip());
		usersPage.setNewUserPhone(data.getUserPhone());
		usersPage.setNewUserAccountingID(data.getUserAccId());
		usersPage.setNewUserComments(data.getUserComments());
		usersPage.clickNewUserOKButton();

		usersPage.clickEditActiveUser(data.getUserFirstName(), data.getUserLastName());
		Assert.assertEquals(usersPage.getNewUserMail(), data.getUserEmail());
		Assert.assertEquals(usersPage.getNewUserFirstName(), data.getUserFirstName());
		Assert.assertEquals(usersPage.getNewUserLastName(), data.getUserLastName());
		Assert.assertEquals(usersPage.getNewUserCompany(), data.getUserCompany());
		Assert.assertEquals(usersPage.getNewUserAddress(), data.getUserAddress());
		Assert.assertEquals(usersPage.getNewUserCity(), data.getUserCity());
		Assert.assertEquals(usersPage.getNewUserZip(), data.getUserZip());
		Assert.assertEquals(usersPage.getNewUserPhone(), data.getUserPhone());
		Assert.assertEquals(usersPage.getNewUserAccountingID(), data.getUserAccId());
		Assert.assertEquals(usersPage.getNewUserComments(), data.getUserComments());
		Assert.assertTrue(usersPage.isNewUserRoleSelected(data.getUserRole()));
		Assert.assertFalse(usersPage.isNewUserRoleSelected(data.getUserRoleEdited()));

		usersPage.setNewUserFirstName(data.getUserFirstNameEdited());
		usersPage.setNewUserLastName(data.getUserLastNameEdited());
		usersPage.setNewUserCompany(data.getUserCompanyEdited());
		usersPage.setNewUserAddress(data.getUserAddressEdited());
		usersPage.setNewUserCity(data.getUserCityEdited());
		usersPage.setNewUserZip(data.getUserZipEdited());
		usersPage.setNewUserPhone(data.getUserPhoneEdited());
		usersPage.setNewUserAccountingID(data.getUserAccIdEdited());
		usersPage.setNewUserComments(data.getUserCommentsEdited());
		usersPage.selectAllowCreatingSupportTickets();
		usersPage.selectNewUserRole(data.getUserRoleEdited());
		usersPage.clickNewUserOKButton();

		usersPage.setSearchUserParameter(data.getUserEmail());
		usersPage.clickFindButton();
		usersPage.clickEditActiveUser(data.getUserFirstNameEdited(), data.getUserLastNameEdited());
		Assert.assertEquals(usersPage.getNewUserMail(), data.getUserEmail());
		Assert.assertEquals(usersPage.getNewUserFirstName(), data.getUserFirstNameEdited());
		Assert.assertEquals(usersPage.getNewUserLastName(), data.getUserLastNameEdited());
		Assert.assertEquals(usersPage.getNewUserCompany(), data.getUserCompanyEdited());
		Assert.assertEquals(usersPage.getNewUserAddress(), data.getUserAddressEdited());
		Assert.assertEquals(usersPage.getNewUserCity(), data.getUserCityEdited());
		Assert.assertEquals(usersPage.getNewUserZip(), data.getUserZipEdited());
		Assert.assertEquals(usersPage.getNewUserPhone(), data.getUserPhoneEdited());
		Assert.assertEquals(usersPage.getNewUserAccountingID(), data.getUserAccIdEdited());
		Assert.assertEquals(usersPage.getNewUserComments(), data.getUserCommentsEdited());
		Assert.assertTrue(usersPage.isAllowCreatingSupportTicketsSelected());
		Assert.assertTrue(usersPage.isNewUserRoleSelected(data.getUserRole()));
		Assert.assertTrue(usersPage.isNewUserRoleSelected(data.getUserRoleEdited()));
		usersPage.clickNewUserCancelButton();

		usersPage.archiveUser(data.getUserFirstNameEdited(), data.getUserLastNameEdited());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsUsersEdit(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = new ClientsWebPage(webdriver);
		companyWebPage.clickClientsLink();

		clientspage.searchClientByName(data.getClientFirstName());
		clientspage.verifyClientIsNotPresentInActiveTab(data.getRetailCompanyName(), data.getRetailCompanyNameEdited());

		NewClientDialogWebPage newclientdialogpage = new NewClientDialogWebPage(webdriver);
		clientspage.clickAddClientButton();
		newclientdialogpage.switchToRetailCustomer();
		newclientdialogpage.setCompanyName(data.getClientName());
		newclientdialogpage.setClientFirstName(data.getClientFirstName());
		newclientdialogpage.setClientLastName(data.getClientLastName());
		newclientdialogpage.setCompanyMail(data.getClientEmail());
		newclientdialogpage.setCompanyPhone(data.getClientPhone());
		newclientdialogpage.setCompanyAccountingID(data.getClientAccId());
		newclientdialogpage.selectCompanyDefaultArea(data.getClientArea());
		newclientdialogpage.setCompanyShipToAddress(data.getClientAddress());
		newclientdialogpage.setCompanyShipToCity(data.getClientCity());
		newclientdialogpage.setCompanyShipToZip(data.getClientZip());
		newclientdialogpage.clickCopyToBillToButton();
		newclientdialogpage.clickOKButton();
		clientspage.clickEditClient(data.getRetailCompanyName());
		newclientdialogpage = new NewClientDialogWebPage(webdriver);
		Assert.assertTrue(newclientdialogpage.isCompanyRetail());
		Assert.assertEquals(newclientdialogpage.getCompanyName(), data.getClientName());
		Assert.assertEquals(newclientdialogpage.getClientFirstName(), data.getClientFirstName());
		Assert.assertEquals(newclientdialogpage.getClientLastName(), data.getClientLastName());

		newclientdialogpage.setCompanyName(data.getClientNameEdited());
		newclientdialogpage.setClientFirstName(data.getClientFirstNameEdited());
		newclientdialogpage.setClientLastName(data.getClientLastNameEdited());
		newclientdialogpage.setCompanyMail(data.getClientMailEdited());
		newclientdialogpage.setCompanyPhone(data.getClientPhoneEdited());
		newclientdialogpage.setCompanyAccountingID(data.getClientAccIdEdited());
		newclientdialogpage.selectCompanyDefaultArea(data.getClientAreaEdited());
		newclientdialogpage.setCompanyShipToAddress(data.getClientAddressEdited());
		newclientdialogpage.setCompanyShipToCity(data.getClientCityEdited());
		newclientdialogpage.setCompanyShipToZip(data.getClientZipEdited());
		newclientdialogpage.clickOKButton();

		clientspage.clickEditClient(data.getRetailCompanyNameEdited());
		newclientdialogpage = new NewClientDialogWebPage(webdriver);
		Assert.assertTrue(newclientdialogpage.isCompanyRetail());
		Assert.assertEquals(newclientdialogpage.getCompanyName(), data.getClientNameEdited());
		Assert.assertEquals(newclientdialogpage.getClientFirstName(), data.getClientFirstNameEdited());
		Assert.assertEquals(newclientdialogpage.getClientLastName(), data.getClientLastNameEdited());
		Assert.assertEquals(newclientdialogpage.getCompanyMail(), data.getClientMailEdited());
		Assert.assertEquals(newclientdialogpage.getCompanyPhone(), data.getClientPhoneEdited());
		Assert.assertEquals(newclientdialogpage.getCompanyAccountingID(), data.getClientAccIdEdited());
		Assert.assertTrue(newclientdialogpage.isCompanyOptionChecked(data.getClientAreaEdited()));
		Assert.assertEquals(newclientdialogpage.getCompanyShipToAddress(), data.getClientAddressEdited());
		Assert.assertEquals(newclientdialogpage.getCompanyShipToCity(), data.getClientCityEdited());
		Assert.assertEquals(newclientdialogpage.getCompanyShipToZip(), data.getClientZipEdited());
		newclientdialogpage.selectBillToAddress();
		Assert.assertEquals(newclientdialogpage.getCompanyBillToAddress(), data.getClientAddress());
		Assert.assertEquals(newclientdialogpage.getCompanyBillToCity(), data.getClientCity());
		Assert.assertEquals(newclientdialogpage.getCompanyBillToZip(), data.getClientZip());
		newclientdialogpage.clickCancelButton();

		clientspage.deleteClient(data.getRetailCompanyNameEdited());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyEmployeesEdit(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		EmployeesWebPage employeespage = new EmployeesWebPage(webdriver);
		companyWebPage.clickEmployeesLink();

		employeespage.makeSearchPanelVisible();
		employeespage.setSearchUserParameter(data.getEmployeeLastName());
		employeespage.clickFindButton();
		employeespage.verifyActiveEmployeeDoesNotExist(data.getEmployeeFirstName(), data.getEmployeeLastName(),
				data.getEmployeeFirstNameEdited(), data.getEmployeeLastNameEdited());

		NewEmployeeDialogWebPage newemployeedialog = new NewEmployeeDialogWebPage(webdriver);
		employeespage.clickAddEmployeeButton();
		newemployeedialog.selectNewEmployeeTeam(data.getEmployeeTeam());
		newemployeedialog.setNewEmployeeFirstName(data.getEmployeeFirstName());
		newemployeedialog.setNewEmployeeLastName(data.getEmployeeLastName());
		newemployeedialog.setNewEmployeePassword(data.getEmployeePassword());
		newemployeedialog.selectNewEmployeeHomeTeam(data.getEmployeeHomeTeam());
		newemployeedialog.setNewEmployeeAddress(data.getEmployeeAddress());
		newemployeedialog.setNewEmployeeCity(data.getEmployeeCity());
		newemployeedialog.selectNewEmployeeCountry(data.getEmployeeCountry());
		newemployeedialog.setNewEmployeeZip(data.getEmployeeZip());
		newemployeedialog.setNewEmployeePhone(data.getEmployeePhone());
		newemployeedialog.setNewEmployeeMail(data.getEmployeeEmail());
		newemployeedialog.setNewEmployeeAccountingID(data.getEmployeeAccId());
		newemployeedialog.setNewEmployeeStartingDate(data.getEmployeeStartDate());
		newemployeedialog.selectNewEmployeeType(data.getEmployeeType());
		newemployeedialog.selectEmployeeRole(data.getEmployeeRole());
		newemployeedialog.clickOKButton();

		newemployeedialog = new NewEmployeeDialogWebPage(webdriver);
		employeespage.clickEditEmployee(data.getEmployeeFirstName(), data.getEmployeeLastName());
		Assert.assertEquals(newemployeedialog.getNewEmployeeTeam(), data.getEmployeeTeam());
		Assert.assertEquals(newemployeedialog.getNewEmployeeFirstName(), data.getEmployeeFirstName());
		Assert.assertEquals(newemployeedialog.getNewEmployeeLastName(), data.getEmployeeLastName());
		Assert.assertTrue(newemployeedialog.isEmployeeRoleChecked(data.getEmployeeRole()));

		newemployeedialog.selectNewEmployeeTeam(data.getEmployeeTeamEdited());
		newemployeedialog.setNewEmployeeFirstName(data.getEmployeeFirstNameEdited());
		newemployeedialog.setNewEmployeeLastName(data.getEmployeeLastNameEdited());
		newemployeedialog.setNewEmployeePassword(data.getEmployeePasswordEdited());
		newemployeedialog.selectNewEmployeeHomeTeam(data.getEmployeeHomeTeamEdited());
		newemployeedialog.setNewEmployeeAddress(data.getEmployeeAddressEdited());
		newemployeedialog.setNewEmployeeCity(data.getEmployeeCityEdited());
		newemployeedialog.selectNewEmployeeCountry(data.getEmployeeCountryEdited()); //todo fails for Edge because of StaleReference exc.
		newemployeedialog.setNewEmployeeZip(data.getEmployeeZipEdited());
		newemployeedialog.setNewEmployeePhone(data.getEmployeePhoneEdited());
		newemployeedialog.setNewEmployeeMail(data.getEmployeeEmailEdited());
		newemployeedialog.setNewEmployeeAccountingID(data.getEmployeeAccIdEdited());
		newemployeedialog.setNewEmployeeStartingDate(data.getEmployeeStartDateEdited());
		newemployeedialog.selectNewEmployeeType(data.getEmployeeTypeEdited());
		newemployeedialog.selectEmployeeRole(data.getEmployeeRoleEdited());
		newemployeedialog.clickOKButton();

		newemployeedialog = new NewEmployeeDialogWebPage(webdriver);
		employeespage.clickEditEmployee(data.getEmployeeFirstNameEdited(), data.getEmployeeLastNameEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeTeam(), data.getEmployeeTeamEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeFirstName(), data.getEmployeeFirstNameEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeLastName(), data.getEmployeeLastNameEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeePassword(), data.getEmployeePasswordEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeHomeTeam(), data.getEmployeeHomeTeamEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeAddress(), data.getEmployeeAddressEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeCity(), data.getEmployeeCityEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeCountry(), data.getEmployeeCountryEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeZip(), data.getEmployeeZipEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeePhone(), data.getEmployeePhoneEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeMail(), data.getEmployeeEmailEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeAccountingID(), data.getEmployeeAccIdEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeStartingDate(), data.getEmployeeStartDateEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeType(), data.getEmployeeTypeEdited());
		Assert.assertTrue(newemployeedialog.isEmployeeRoleChecked(data.getEmployeeRoleEdited()));
		Assert.assertTrue(newemployeedialog.isEmployeeRoleChecked(data.getEmployeeRole()));
		newemployeedialog.clickCancelButton();

		employeespage.archiveEmployee(data.getEmployeeFirstNameEdited(), data.getEmployeeLastNameEdited());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServicesEdit(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServicesWebPage servicespage = new ServicesWebPage(webdriver);
		companyWebPage.clickServicesLink();
		//	servicespage.makeSearchPanelVisible();
		servicespage.setServiceSearchCriteria(data.getServiceName());
		servicespage.clickFindButton();

		if (servicespage.activeServiceExists(data.getServiceName())) {
			servicespage.archiveServiceForActiveAllTab(data.getServiceName());
		}
		if (servicespage.activeServiceExists(data.getServiceNameEdited())) {
			servicespage.archiveServiceForActiveAllTab(data.getServiceNameEdited());
		}
		NewServiceDialogWebPage newservicedialog = new NewServiceDialogWebPage(webdriver);
		servicespage.clickAddServiceButton();
		newservicedialog.setNewServiceName(data.getServiceName());
		newservicedialog.selectNewServiceType(data.getServiceType());
		newservicedialog.setNewServiceDescription(data.getServiceDescription());
		newservicedialog.setNewServiceAccountingID(data.getServiceAccId());
		newservicedialog.selectNewServiceAssignedTo(data.getServiceAssignedTo());
		newservicedialog.clickOKButton();

		newservicedialog = new NewServiceDialogWebPage(webdriver);
		servicespage.clickEditService(data.getServiceName());
		Assert.assertEquals(newservicedialog.getNewServiceName(), data.getServiceName());
		Assert.assertFalse(newservicedialog.isNewServiceMultipleSelected());

		newservicedialog.setNewServiceName(data.getServiceNameEdited());
		newservicedialog.selectNewServiceType(data.getServiceTypeEdited());
		newservicedialog.setNewServiceDescription(data.getServiceDescEdited());
		newservicedialog.setNewServiceAccountingID(data.getServiceAccIdEdited());
		newservicedialog.selectNewServiceAssignedTo(data.getServiceAssignedToEdited());
		newservicedialog.selectNewService(data.getMultipleCheckboxValue());
		newservicedialog.clickOKButton();

		newservicedialog = new NewServiceDialogWebPage(webdriver);
		servicespage.clickEditService(data.getServiceNameEdited());
		Assert.assertEquals(newservicedialog.getNewServiceName(), data.getServiceNameEdited());
		Assert.assertEquals(newservicedialog.getNewServiceType(), data.getServiceTypeEdited());
		Assert.assertEquals(newservicedialog.getNewServiceDescription(), data.getServiceDescEdited());
		Assert.assertEquals(newservicedialog.getNewServiceAccountingID(), data.getServiceAccIdEdited());
		Assert.assertEquals(newservicedialog.getNewServiceAssignedTo(), data.getServiceAssignedToEdited());
		Assert.assertTrue(newservicedialog.isNewServiceMultipleSelected());
		newservicedialog.clickCancelButton();

		servicespage.archiveServiceForActiveAllTab(data.getServiceNameEdited());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServiceRequestTypeServiceTypeAtServiceRequestEdit(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServiceRequestTypesWebPage servicerequesttypespage = new ServiceRequestTypesWebPage(webdriver);
		companyWebPage.clickServiceRequestTypesLink();
		if (servicerequesttypespage.isServiceRequestTypeExists(data.getServiceType())) {
			servicerequesttypespage.deleteServiceRequestType(data.getServiceType());
		}
		servicerequesttypespage.clickAddServiceRequestTypeButton();
		servicerequesttypespage.setNewServiceRequestTypeName(data.getServiceType());
		servicerequesttypespage.setNewServiceRequestTypeDescription(data.getServiceTypeDesc());
		servicerequesttypespage.selectNewServiceRequestTypeTeam(data.getServiceTypeTeam());
		servicerequesttypespage.selectNewServiceRequestTypePackage(data.getServiceTypePackage());
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();

		OperationsWebPage operationspage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getServiceType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		ServiceRequestListServiceDialog serviceDialog = new ServiceRequestListServiceDialog(webdriver);
		serviceRequestsListInteractions.clickServiceEditButton();
		serviceDialog.openServicesDropDown();
		Assert.assertEquals(serviceDialog.countAvailableServices(), 2);
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.cancelNewServiceRequest();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		servicerequesttypespage = new ServiceRequestTypesWebPage(webdriver);
		companyWebPage.clickServiceRequestTypesLink();
		servicerequesttypespage.clickEditServiceRequestType(data.getServiceType());
		servicerequesttypespage.selectNewServiceRequestTypePackage(data.getServiceTypePackageNew());
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();

		operationspage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();
		serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationspage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(data.getServiceType());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickServiceEditButton();
		Assert.assertEquals(serviceDialog.getAllAvailableServices(), "52");
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.cancelNewServiceRequest();

		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeader.clickCompanyLink();
		servicerequesttypespage = new ServiceRequestTypesWebPage(webdriver);
		companyWebPage.clickServiceRequestTypesLink();
		servicerequesttypespage.deleteServiceRequestType(data.getServiceType());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInvoiceTypeClientEdit(String rowID, String description, JSONObject testData) throws Exception {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InvoiceTypesWebPage invoicestypespage = new InvoiceTypesWebPage(webdriver);
		companyWebPage.clickInvoiceTypesLink();
		if (invoicestypespage.invoiceTypeExists(data.getInvoiceType())) {
			invoicestypespage.deleteInvoiceType(data.getInvoiceType());
		}

		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicestypespage.clickAddInvoiceTypeButton();
		newinvoicetypedialog.setInvoiceTypeName(data.getInvoiceType());
		newinvoicetypedialog.waitABit(500);
		newinvoicetypedialog.selectVisibleCheckBox();
		newinvoicetypedialog.waitABit(500);
		newinvoicetypedialog.selectRequiredCheckBox();
		newinvoicetypedialog.selectQuestionsTemplate(data.getQuestionTemplate());
		newinvoicetypedialog.selectQuestionForm(data.getQuestionForm());
		newinvoicetypedialog.clickOKAddInvoiceTypeButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicestypespage.clickClientsLinkForInvoiceType(data.getInvoiceType());
		invoicestypespage.addAssignedClient(data.getClientName());
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(data.getInvoiceType());
		Assert.assertFalse(invoicestypespage.isAssignedClientSelected(data.getClientName()));
		invoicestypespage.addAssignedClient(data.getClientName());
		invoicestypespage.clickUpdateClientsButton();
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(data.getInvoiceType());
		Assert.assertTrue(invoicestypespage.isAssignedClientSelected(data.getClientName()));
		invoicestypespage.deleteAssignedClient(data.getClientName());
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(data.getInvoiceType());
		Assert.assertTrue(invoicestypespage.isAssignedClientSelected(data.getClientName()));
		invoicestypespage.deleteAssignedClient(data.getClientName());
		invoicestypespage.clickUpdateClientsButton();
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);

		invoicestypespage.clickClientsLinkForInvoiceType(data.getInvoiceType());
		Assert.assertFalse(invoicestypespage.isAssignedClientSelected(data.getClientName()));
		invoicestypespage.closeAssignedClientsTab(mainWindowHandle);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsServicesUpdate(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = new ClientsWebPage(webdriver);
		companyWebPage.clickClientsLink();

		clientspage.makeSearchPanelVisible();
		clientspage.setClientSearchCriteria(data.getClientName());
		clientspage.clickFindButton();
		if (clientspage.isClientPresentInTable(data.getClientName())) {
			clientspage.deleteClient(data.getClientName());
		}

		NewClientDialogWebPage newclientdialog = new NewClientDialogWebPage(webdriver);
		clientspage.clickAddClientButton();
		newclientdialog.createWholesaleClient(data.getClientName());

		String mainWindowHandle = webdriver.getWindowHandle();
		clientspage.clickServicesLinkForClient(data.getClientName());
		clientspage.selectClientServicePackage(data.getServicePackage());
		List<WebElement> clienservicesselected = clientspage.getClientServicesTableRows();
		clientspage.closeClientServicesTab(mainWindowHandle);

		clientspage.clickServicesLinkForClient(data.getClientName());
		Assert.assertTrue(clienservicesselected.retainAll(clientspage.getClientServicesTableRows()));
		clientspage.selectClientServicePackage(data.getServicePackageNew());
		Assert.assertTrue(clientspage.getClientServicesTableRows().size() > 225);
		clientspage.closeClientServicesTab(mainWindowHandle);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsClientUsersSearch(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = new ClientsWebPage(webdriver);
		companyWebPage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.setClientSearchCriteria(data.getClientName());
		clientspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		clientspage.clickClientUsersLinkForClient(data.getClientName());
		clientspage.searchClientUser(data.getFirstSearchParameter());
		Assert.assertEquals(1, clientspage.getClientUsersTableRowCount());
		clientspage.searchClientUser(data.getSecondSearchParameter());
		Assert.assertEquals(1, clientspage.getClientUsersTableRowCount());
		clientspage.searchClientUser(data.getThirdSearchParameter());
		Assert.assertEquals(1, clientspage.getClientUsersTableRowCount());
		clientspage.searchClientUser(data.getLastSearchParameter());
		Assert.assertEquals(0, clientspage.getClientUsersTableRowCount());
		clientspage.closeClientServicesTab(mainWindowHandle);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySharingWorkOrder(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
			interApplicationExchangePage.deleteEntry(data.getEntry());
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry(data.getEntry());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySharingWorkOrderAddRuleTeams(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
			interApplicationExchangePage.deleteEntry(data.getEntry());
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry(data.getEntry());

		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Teams", "Teams", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Clients Include Selected)"));

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Teams", "Teams", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Teams Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Teams Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Teams Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Teams Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySharingWorkOrderAddRuleEmployees(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
			interApplicationExchangePage.deleteEntry(data.getEntry());
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry(data.getEntry());

		interApplicationExchangePage.expandFirstCompanyProfile();
		if (interApplicationExchangePage.checkRuleByName(data.getRule())) {
			interApplicationExchangePage.deleteRule(data.getRule());
		}

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Employees", "Employees", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Employees", "Employees", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Employees Include Selected)"));

		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Employees Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Employees Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySharingWorkOrderAddRuleClients(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
			interApplicationExchangePage.deleteEntry(data.getEntry());
		}
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry(data.getEntry());

		interApplicationExchangePage.expandFirstCompanyProfile();
		if (interApplicationExchangePage.checkRuleByName(data.getEntry())) {
			interApplicationExchangePage.deleteRule(data.getEntry());
		}
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Clients", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Clients", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Clients Include Selected)"));

		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Clients Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Clients Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySharingWorkOrderAddRuleServices(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
			interApplicationExchangePage.deleteEntry(data.getEntry());
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry(data.getEntry());

		interApplicationExchangePage.expandFirstCompanyProfile();

		if (interApplicationExchangePage.checkRuleByName(data.getRule())) {
			interApplicationExchangePage.deleteRule(data.getRule());
		}
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Services", "Services", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Services", "Services", "Include Selected");
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Services Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Services Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Services Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanySharingWorkOrderAddRuleVehicleParts(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
			interApplicationExchangePage.deleteEntry(data.getEntry());
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getProfileName(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry(data.getEntry());

		interApplicationExchangePage.expandFirstCompanyProfile();
		if (interApplicationExchangePage.checkRuleByName(data.getRule())) {
			interApplicationExchangePage.deleteRule(data.getRule());
		}
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Vehicle Parts", "Vehicle Parts", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Vehicle Parts", "Vehicle Parts", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Vehicle Parts Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationSharingEstimate(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter, "The changes have not been applied");

		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleClients(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		while (interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter, "The changes have not been applied");

		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");

		interApplicationExchangePage.expandFirstCompanyProfile();
		String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Clients",
				"Clients", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
				"The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Clients", "Clients", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Clients (Clients Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Clients Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Clients Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Clients Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleTeams(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");

		interApplicationExchangePage.expandFirstCompanyProfile();
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Teams", "Teams", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Clients Include Selected)"));

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Teams", "Teams", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Teams Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Teams Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Teams Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Teams Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleEmployees(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");

		interApplicationExchangePage.expandFirstCompanyProfile();
		String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Employees",
				"Employees", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
				"The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Employees", "Employees", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Employees (Employees Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Employees Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Employees Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Employees Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleServices(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");

		interApplicationExchangePage.expandFirstCompanyProfile();
		String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Services",
				"Services", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
				"The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Services", "Services", "Include Selected");
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Services Include Selected)"));

		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Services Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Services Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationSharingEstimateAddRuleVehicleParts(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
			interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("Estimate JST for Name", "_test1");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry("Estimate JST for Name (Estimation)");

		interApplicationExchangePage.expandFirstCompanyProfile();
		String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Vehicle Parts",
				"Vehicle Parts", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
				"The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillRuleBox("Include Selected Vehicle Parts", "Vehicle Parts", "Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));

		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.editRuleBoxName("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.editRuleBoxName(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName + " (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.deleteRule(newName + " (Vehicle Parts Include Selected)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationMappingWorkOrder(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)")) {
			interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name", data.getDocumentType(), "All Jay");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name", data.getDocumentType(), "All Jay");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyExchangeConfigurationMappingWorkOrderAddRuleClients(String rowID, String description, JSONObject testData) {

		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();

		if (interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)")) {
			interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
		}

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name", data.getDocumentType(), "All Jay");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)"));

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name", data.getDocumentType(), "All Jay");
		interApplicationExchangePage.clickProfileDetailsBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)"));

		String entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileNameTest());
		interApplicationExchangePage.clickProfileEditBox(data.getCancelButton());
		String entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertEquals(entryTextBefore, entryTextAfter);

		entryTextBefore = interApplicationExchangePage.getFirstEntryText();
		interApplicationExchangePage.clickEditFirstEntry();
		interApplicationExchangePage.fillProfileDetailsEdit(Long.toString(System.currentTimeMillis()));
		interApplicationExchangePage.clickProfileEditBox(data.getUpdateButton());
		entryTextAfter = interApplicationExchangePage.getFirstEntryText();
		Assert.assertNotEquals(entryTextBefore, entryTextAfter);

		interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyServicesActiveParts(String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		ServicesWebPage servicesWebPage = new ServicesWebPage(webdriver);
		companyWebPage.clickServicesLink();
		servicesWebPage.selectSearchServiceType(data.getServiceType());
		servicesWebPage.setServiceSearchCriteria(data.getServiceName());
		servicesWebPage.clickFindButton();
		servicesWebPage.verifyActiveServiceDoesNotExist(data.getServiceName());
		servicesWebPage.clickActivePartsTab();

		NewServiceDialogWebPage newServiceDialog = new NewServiceDialogWebPage(webdriver);
		servicesWebPage.clickAddServiceButton();
		newServiceDialog.setNewServiceName(data.getServiceName());
		newServiceDialog.selectNewServiceType(data.getServiceType());
		newServiceDialog.selectNewServicePriceType(data.getServicePriceType());
		newServiceDialog.clickOKButton();

		servicesWebPage.clickActiveAllTab();
		Assert.assertTrue(servicesWebPage.activeServiceExists(data.getServiceName()), "The " + data.getServiceName()
				+ "service does not exist in active tab");
		servicesWebPage.clickEditService(data.getServiceName());
		System.out.println(newServiceDialog.getSavedServicePriceType());
		System.out.println(data.getServicePriceType());
		Assert.assertEquals(newServiceDialog.getSavedServicePriceType(), data.getServicePriceType(),
				"The service Price Type has not been saved");
		newServiceDialog.clickCancelButton();
		servicesWebPage.archiveService(data.getServiceName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInterApplicationExchangeConfigMappingEstimation(
			String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickSendingTab();
		interApplicationExchangePage.expandFirstCreatedCompany();
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getEntry());
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getProfileName());
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company shouldn't be added after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickInsertButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company should have been added after clicking the \"Insert\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name shouldn't be edited after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickActiveCheckBox();
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name should have been edited after clicking the \"Update\" button");
		Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
				"The company profile is not active after clicking the \"Active\" checkbox");

		interApplicationExchangePage.deleteEntry(data.getProfileName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInterApplicationExchangeConfigMappingEstimationAddRuleClients(
			String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickSendingTab();
		interApplicationExchangePage.expandFirstCreatedCompany();
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getEntry());
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getProfileName());
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickInsertButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company should have been added after clicking the \"Insert\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickActiveCheckBox();
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name should have been edited after clicking the \"Update\" button");
		Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
				"The company profile is not active after clicking the \"Active\" checkbox");

		interApplicationExchangePage.expandCompanyProfile(data.getProfileName());
		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		int beforeSelection = interApplicationExchangePage.getNumberOfUnselectedUsers();

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickCancelButton();

		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		Assert.assertEquals(beforeSelection, interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has been changed after clicking the \"Cancel\" button");

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickInsertButton();

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		Assert.assertEquals(beforeSelection - data.getUsersToAdd(), interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has not been properly updated after clicking the \"Insert\" button");
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is displayed after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		Assert.assertTrue(interApplicationExchangePage.isEntityTypeDisabled(), "The entity type combobox is not disabled");
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is not displayed after clicking the \"Update\" button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInterApplicationExchangeConfigMappingEstimationAddRuleTeams(
			String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickSendingTab();
		interApplicationExchangePage.expandFirstCreatedCompany();
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getEntry());
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getProfileName());
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickInsertButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company should have been added after clicking the \"Insert\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickActiveCheckBox();
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name should have been edited after clicking the \"Update\" button");
		Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
				"The company profile is not active after clicking the \"Active\" checkbox");

		interApplicationExchangePage.expandCompanyProfile(data.getProfileName());
		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		int beforeSelection = interApplicationExchangePage.getNumberOfUnselectedUsers();

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickCancelButton();

		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		Assert.assertEquals(beforeSelection, interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has been changed after clicking the \"Cancel\" button");

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickInsertButton();

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		Assert.assertEquals(beforeSelection - data.getUsersToAdd(), interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has not been properly updated after clicking the \"Insert\" button");
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is displayed after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		Assert.assertTrue(interApplicationExchangePage.isEntityTypeDisabled(), "The entity type combobox is not disabled");
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is not displayed after clicking the \"Update\" button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInterApplicationExchangeConfigMappingEstimationAddRuleEmployees(
			String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickSendingTab();
		interApplicationExchangePage.expandFirstCreatedCompany();
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getEntry());
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getProfileName());
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickInsertButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company should have been added after clicking the \"Insert\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickActiveCheckBox();
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name should have been edited after clicking the \"Update\" button");
		Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
				"The company profile is not active after clicking the \"Active\" checkbox");

		interApplicationExchangePage.expandCompanyProfile(data.getProfileName());
		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		int beforeSelection = interApplicationExchangePage.getNumberOfUnselectedUsers();

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickCancelButton();

		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		Assert.assertEquals(beforeSelection, interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has been changed after clicking the \"Cancel\" button");

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickInsertButton();

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		Assert.assertEquals(beforeSelection - data.getUsersToAdd(), interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has not been properly updated after clicking the \"Insert\" button");
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is displayed after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		Assert.assertTrue(interApplicationExchangePage.isEntityTypeDisabled(), "The entity type combobox is not disabled");
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is not displayed after clicking the \"Update\" button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInterApplicationExchangeConfigMappingEstimationAddRuleServices(
			String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickSendingTab();
		interApplicationExchangePage.expandFirstCreatedCompany();
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getEntry());
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getProfileName());
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickInsertButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company should have been added after clicking the \"Insert\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickActiveCheckBox();
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name should have been edited after clicking the \"Update\" button");
		Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
				"The company profile is not active after clicking the \"Active\" checkbox");

		interApplicationExchangePage.expandCompanyProfile(data.getProfileName());
		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		int beforeSelection = interApplicationExchangePage.getNumberOfUnselectedUsers();

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickCancelButton();

		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		Assert.assertEquals(beforeSelection, interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has been changed after clicking the \"Cancel\" button");

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickInsertButton();

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		Assert.assertEquals(beforeSelection - data.getUsersToAdd(), interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has not been properly updated after clicking the \"Insert\" button");
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is displayed after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		Assert.assertTrue(interApplicationExchangePage.isEntityTypeDisabled(), "The entity type combobox is not disabled");
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is not displayed after clicking the \"Update\" button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInterApplicationExchangeConfigMappingEstimationAddRuleVehicleParts(
			String rowID, String description, JSONObject testData) {
		BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = new InterApplicationExchangeWebPage(webdriver);
		companyWebPage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickSendingTab();
		interApplicationExchangePage.expandFirstCreatedCompany();
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getEntry());
		interApplicationExchangePage.verifyCompanyDoesNotExist(data.getProfileName());
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType());
		interApplicationExchangePage.clickInsertButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
				"The company should have been added after clicking the \"Insert\" button");

		interApplicationExchangePage.clickEditEntry(data.getEntry());
		interApplicationExchangePage.fillProfileDetailsEdit(data.getProfileName());
		interApplicationExchangePage.clickActiveCheckBox();
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
				"The company name should have been edited after clicking the \"Update\" button");
		Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
				"The company profile is not active after clicking the \"Active\" checkbox");

		interApplicationExchangePage.expandCompanyProfile(data.getProfileName());
		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		int beforeSelection = interApplicationExchangePage.getNumberOfUnselectedUsers();

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickCancelButton();

		interApplicationExchangePage.clickAddRule();
		interApplicationExchangePage.fillRuleBox(data.getRuleName(), data.getRuleEntityType(), data.getRuleFilterType());
		Assert.assertEquals(beforeSelection, interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has been changed after clicking the \"Cancel\" button");

		interApplicationExchangePage.selectUsersWhileCreatingRule(data.getUsersToAdd());
		interApplicationExchangePage.clickInsertButton();

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		Assert.assertEquals(beforeSelection - data.getUsersToAdd(), interApplicationExchangePage.getNumberOfUnselectedUsers(),
				"The number of unselected users has not been properly updated after clicking the \"Insert\" button");
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		interApplicationExchangePage.clickCancelButton();
		Assert.assertFalse(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is displayed after clicking the \"Cancel\" button");

		interApplicationExchangePage.clickEditRule(data.getRuleName());
		interApplicationExchangePage.editRuleBoxName(data.getRuleNameEdited());
		Assert.assertTrue(interApplicationExchangePage.isEntityTypeDisabled(), "The entity type combobox is not disabled");
		interApplicationExchangePage.clickUpdateButton();
		Assert.assertTrue(interApplicationExchangePage.isRuleDisplayed(data.getRuleNameEdited()),
				"The edited rule name is not displayed after clicking the \"Update\" button");
	}


	//todo fix
//    //	 @Test(testName = "Test Case 27891:Company- Service Advisors: Authentication",
////             description = "Company- Service Advisors: Authentication",
////             dataProvider = "getUserData", dataProviderClass = DataProviderPool.class,
////             retryAnalyzer=Retry.class)
//    public void testCompanyServiceAdvisorsAuthentication(String userName, String userPassword) throws InterruptedException, IOException {
//
//        // final String email = "test123CD@domain.com";
//        final String usermailprefix = "test.cyberiansoft+";
//        final String usermailpostbox = "@gmail.com";
//        final String confirmpsw = "111aaa";
//        final String customer = "001 - Test Company";
//        final String firstname = "test123CDF";
//        final String lastname = "test123CDF";
//        final String role = "SalesPerson";
//
//        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//        CompanyWebPage companyWebPage = backOfficeHeader.clickCompanyLink();
//        ServiceAdvisorsWebPage serviceadvisorspage = companyWebPage.clickServiceAdvisorsLink();
//        serviceadvisorspage.makeSearchPanelVisible();
//        serviceadvisorspage.setUserSearchCriteria(firstname + " " + lastname);
//        serviceadvisorspage.clickFindButton();
//        if (serviceadvisorspage.serviceAdvisorExists(firstname, lastname)) {
//            serviceadvisorspage.deleteServiceAdvisor(firstname, lastname);
//        }
//        serviceadvisorspage.clickServiceAdvisorAddButton();
//        long currtime = java.lang.System.currentTimeMillis();
//        String usermail = "test.cyberiansoft+" + currtime + "@gmail.com";
//        serviceadvisorspage.createNewServiceAdvisor(usermail, firstname, lastname, customer, role);
//
//        backOfficeHeader.clickLogout();
//
//        boolean search = false;
//        String mailmessage = "";
//        for (int i = 0; i < 4; i++) {
//            if (!MailChecker.searchEmail("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro: REGISTRATION",
//                    "ReconPro@cyberianconcepts.com", "Please click link below to complete the registration process.")) {
//                serviceadvisorspage.waitABit(60 * 500);
//            } else {
//                mailmessage = MailChecker.searchEmailAndGetMailMessage("test.cyberiansoft@gmail.com", "ZZzz11!!",
//                        "ReconPro: REGISTRATION", "ReconPro@cyberianconcepts.com");
//                if (mailmessage.length() > 3) {
//                    search = true;
//                    break;
//                }
//            }
//        }
//
//        String confirmationurl = "";
//        if (search) {
//
//            confirmationurl = mailmessage.substring(mailmessage.indexOf("'") + 1, mailmessage.lastIndexOf("'"));
//
//        }
//        System.out.println("++++++" + confirmationurl);
//        serviceadvisorspage.waitABit(60 * 1000);
//        webdriver.get(confirmationurl);
//        ConfirmPasswordWebPage confirmpasswordpage = PageFactory.initElements(webdriver, ConfirmPasswordWebPage.class);
//
//        BackOfficeLoginWebPage loginpage = confirmpasswordpage.confirmUserPassword(confirmpsw);
//        loginpage.userLogin(userName, userPassword);
//        backOfficeHeader.clickHomeLink();
//
//        backOfficeHeader.clickLogout();
//        loginpage.userLogin(userName, userPassword);
//        companyWebPage = backOfficeHeader.clickCompanyLink();
//        serviceadvisorspage = companyWebPage.clickServiceAdvisorsLink();
//        serviceadvisorspage.makeSearchPanelVisible();
//        serviceadvisorspage.setUserSearchCriteria(firstname + " " + lastname);
//        serviceadvisorspage.clickFindButton();
//        serviceadvisorspage.deleteServiceAdvisor(firstname, lastname);
//    }
}