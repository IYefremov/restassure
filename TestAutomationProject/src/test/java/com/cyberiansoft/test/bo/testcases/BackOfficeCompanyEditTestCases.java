package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOCompanyEditData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.utils.MailChecker;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BackOfficeCompanyEditTestCases extends BaseTestCase {
    
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOCompanyEditData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyInsuranceCompanyInServiceRequestClaimInfoEdit(
	        String rowID, String description, JSONObject testData) {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InsuranceCompaniesWePpage insurancecompaniespage = companypage.clickInsuranceCompaniesLink();
		if (insurancecompaniespage.insuranceCompanyExists(data.getInsuranceCompanyName())) {
			insurancecompaniespage.deleteInsuranceCompany(data.getInsuranceCompanyName());
		}
		insurancecompaniespage.clickAddInsuranceCompanyButton();
		insurancecompaniespage.createNewInsuranceCompany(data.getInsuranceCompanyName());
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();

		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickClaimInfoEditButton();
		servicerequestslistpage.selectServiceRequestInsurance(data.getInsuranceCompanyName());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		Assert.assertTrue(
				servicerequestslistpage.isInsuranceCompanyPresentForFirstServiceRequestFromList(data.getInsuranceCompanyName()));

		companypage = backofficeheader.clickCompanyLink();
		insurancecompaniespage = companypage.clickInsuranceCompaniesLink();
		insurancecompaniespage.deleteInsuranceCompany(data.getInsuranceCompanyName());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyUsersEdit(String rowID, String description, JSONObject testData) throws Exception {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        
        CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		UsersWebPage userspage = companypage.clickUsersLink();

		userspage.makeSearchPanelVisible();

		userspage.setSearchUserParameter(data.getUserEmail());
		userspage.clickFindButton();
		if (userspage.isUserActive(data.getUserFirstNameEdited(), data.getUserLastNameEdited())) {
			userspage.archiveUser(data.getUserFirstNameEdited(), data.getUserLastNameEdited());
		}
		if (userspage.isUserActive(data.getUserFirstName(), data.getUserLastName())) {
			userspage.archiveUser(data.getUserFirstName(), data.getUserLastName());
		}

		userspage.clickUserAddButton();
		userspage.createNewUser(data.getUserEmail(), data.getUserFirstName(), data.getUserLastName(), data.getUserRole());
		userspage.clickEditActiveUser(data.getUserFirstName(), data.getUserLastName());
		userspage.setNewUserCompany(data.getUserCompany());
		userspage.setNewUserAddress(data.getUserAddress());
		userspage.setNewUserCity(data.getUserCity());
		userspage.setNewUserZip(data.getUserZip());
		userspage.setNewUserPhone(data.getUserPhone());
		userspage.setNewUserAccountingID(data.getUserAccId());
		userspage.setNewUserComments(data.getUserComments());
		userspage.clickNewUserOKButton();

		userspage.clickEditActiveUser(data.getUserFirstName(), data.getUserLastName());
		Assert.assertEquals(userspage.getNewUserMail(), data.getUserEmail());
		Assert.assertEquals(userspage.getNewUserFirstName(), data.getUserFirstName());
		Assert.assertEquals(userspage.getNewUserLastName(), data.getUserLastName());
		Assert.assertEquals(userspage.getNewUserCompany(), data.getUserCompany());
		Assert.assertEquals(userspage.getNewUserAddress(), data.getUserAddress());
		Assert.assertEquals(userspage.getNewUserCity(), data.getUserCity());
		Assert.assertEquals(userspage.getNewUserZip(), data.getUserZip());
		Assert.assertEquals(userspage.getNewUserPhone(), data.getUserPhone());
		Assert.assertEquals(userspage.getNewUserAccountingID(), data.getUserAccId());
		Assert.assertEquals(userspage.getNewUserComments(), data.getUserComments());
		Assert.assertTrue(userspage.isNewUserRoleSelected(data.getUserRole()));
		Assert.assertFalse(userspage.isNewUserRoleSelected(data.getUserRoleEdited()));

		userspage.setNewUserFirstName(data.getUserFirstNameEdited());
		userspage.setNewUserLastName(data.getUserLastNameEdited());
		userspage.setNewUserCompany(data.getUserCompanyEdited());
		userspage.setNewUserAddress(data.getUserAddressEdited());
		userspage.setNewUserCity(data.getUserCityEdited());
		userspage.setNewUserZip(data.getUserZipEdited());
		userspage.setNewUserPhone(data.getUserPhoneEdited());
		userspage.setNewUserAccountingID(data.getUserAccIdEdited());
		userspage.setNewUserComments(data.getUserCommentsEdited());
		userspage.selectAllowCreatingSupportTickets();
		userspage.selectNewUserRole(data.getUserRoleEdited());
		userspage.clickNewUserOKButton();

		userspage.setSearchUserParameter(data.getUserEmail());
		userspage.clickFindButton();
		userspage.clickEditActiveUser(data.getUserFirstNameEdited(), data.getUserLastNameEdited());
		Assert.assertEquals(userspage.getNewUserMail(), data.getUserEmail());
		Assert.assertEquals(userspage.getNewUserFirstName(), data.getUserFirstNameEdited());
		Assert.assertEquals(userspage.getNewUserLastName(), data.getUserLastNameEdited());
		Assert.assertEquals(userspage.getNewUserCompany(), data.getUserCompanyEdited());
		Assert.assertEquals(userspage.getNewUserAddress(), data.getUserAddressEdited());
		Assert.assertEquals(userspage.getNewUserCity(), data.getUserCityEdited());
		Assert.assertEquals(userspage.getNewUserZip(), data.getUserZipEdited());
		Assert.assertEquals(userspage.getNewUserPhone(), data.getUserPhoneEdited());
		Assert.assertEquals(userspage.getNewUserAccountingID(), data.getUserAccIdEdited());
		Assert.assertEquals(userspage.getNewUserComments(), data.getUserCommentsEdited());
		Assert.assertTrue(userspage.isAllowCreatingSupportTicketsSelected());
		Assert.assertTrue(userspage.isNewUserRoleSelected(data.getUserRole()));
		Assert.assertTrue(userspage.isNewUserRoleSelected(data.getUserRoleEdited()));
		userspage.clickNewUserCancelButton();

		userspage.archiveUser(data.getUserFirstNameEdited(), data.getUserLastNameEdited());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyClientsUsersEdit(String rowID, String description, JSONObject testData) {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        
        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();

		clientspage.searchClientByName(data.getClientFirstName());
        clientspage.verifyClientIsNotPresentInActiveTab(data.getRetailCompanyName(), data.getRetailCompanyNameEdited());

		NewClientDialogWebPage newclientdialogpage = clientspage.clickAddClientButton();
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
		newclientdialogpage = clientspage.clickEditClient(data.getRetailCompanyName());
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

		newclientdialogpage = clientspage.clickEditClient(data.getRetailCompanyNameEdited());
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
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		EmployeesWebPage employeespage = companypage.clickEmployeesLink();

		employeespage.makeSearchPanelVisible();
		employeespage.setSearchUserParameter(data.getEmployeeLastName());
		employeespage.clickFindButton();
        employeespage.verifyActiveEmployeeDoesNotExist(data.getEmployeeFirstName(), data.getEmployeeLastName(), 
                data.getEmployeeFirstNameEdited(), data.getEmployeeLastNameEdited());

		NewEmployeeDialogWebPage newemployeedialog = employeespage.clickAddEmployeeButton();
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

		newemployeedialog = employeespage.clickEditEmployee(data.getEmployeeFirstName(), data.getEmployeeLastName());
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
		newemployeedialog.selectNewEmployeeCountry(data.getEmployeeCountryEdited());
		newemployeedialog.setNewEmployeeZip(data.getEmployeeZipEdited());
		newemployeedialog.setNewEmployeePhone(data.getEmployeePhoneEdited());
		newemployeedialog.setNewEmployeeMail(data.getEmployeeEmailEdited());
		newemployeedialog.setNewEmployeeAccountingID(data.getEmployeeAccIdEdited());
		newemployeedialog.setNewEmployeeStartingDate(data.getEmployeeStartDateEdited());
		newemployeedialog.selectNewEmployeeType(data.getEmployeeTypeEdited());
		newemployeedialog.selectEmployeeRole(data.getEmployeeRoleEdited());
		newemployeedialog.clickOKButton();

		newemployeedialog = employeespage.clickEditEmployee(data.getEmployeeFirstNameEdited(), data.getEmployeeLastNameEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeTeam(), data.getEmployeeTeamEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeFirstName(), data.getEmployeeFirstNameEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeLastName(), data.getEmployeeLastNameEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeePassword(), data.getEmployeePasswordEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeHomeTeam(), data.getEmployeeHomeTeamEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeAddress(), data.getEmployeeAddressEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeCity(), data.getEmployeeCityEdited());
		Assert.assertEquals(newemployeedialog.getNewEmployeeCountry().trim(), data.getEmployeeCountryEdited());
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
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServicesWebPage servicespage = companypage.clickServicesLink();
	//	servicespage.makeSearchPanelVisible();
		servicespage.setServiceSearchCriteria(data.getServiceName());
		servicespage.clickFindButton();

		if (servicespage.activeServiceExists(data.getServiceName())) {
			servicespage.archiveServiceForActiveAllTab(data.getServiceName());
		}
		if (servicespage.activeServiceExists(data.getServiceNameEdited())) {
			servicespage.archiveServiceForActiveAllTab(data.getServiceNameEdited());
		}
		NewServiceDialogWebPage newservicedialog = servicespage.clickAddServiceButton();
		newservicedialog.setNewServiceName(data.getServiceName());
		newservicedialog.selectNewServiceType(data.getServiceType());
		newservicedialog.setNewServiceDescription(data.getServiceDescription());
		newservicedialog.setNewServiceAccountingID(data.getServiceAccId());
		newservicedialog.selectNewServiceAssignedTo(data.getServiceAssignedTo());
		newservicedialog.clickOKButton();

		newservicedialog = servicespage.clickEditService(data.getServiceName());
		Assert.assertEquals(newservicedialog.getNewServiceName(), data.getServiceName());
		Assert.assertFalse(newservicedialog.isNewServiceMultipleSelected());

		newservicedialog.setNewServiceName(data.getServiceNameEdited());
		newservicedialog.selectNewServiceType(data.getServiceTypeEdited());
		newservicedialog.setNewServiceDescription(data.getServiceDescEdited());
		newservicedialog.setNewServiceAccountingID(data.getServiceAccIdEdited());
		newservicedialog.selectNewServiceAssignedTo(data.getServiceAssignedToEdited());
		newservicedialog.selectNewService(data.getMultipleCheckboxValue());
		newservicedialog.clickOKButton();

		newservicedialog = servicespage.clickEditService(data.getServiceNameEdited());
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
    public void testCompanyServiceRequestTypeServiceTypeAtServiceRequestEdit(String rowID, String description, JSONObject testData) throws Exception {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServiceRequestTypesWebPage servicerequesttypespage = companypage.clickServiceRequestTypesLink();
		if (servicerequesttypespage.isServiceRequestTypeExists(data.getServiceType())) {
			servicerequesttypespage.deleteServiceRequestType(data.getServiceType());
		}
		servicerequesttypespage.clickAddServiceRequestTypeButton();
		servicerequesttypespage.setNewServiceRequestTypeName(data.getServiceType());
		servicerequesttypespage.setNewServiceRequestTypeDescription(data.getServiceTypeDesc());
		servicerequesttypespage.selectNewServiceRequestTypeTeam(data.getServiceTypeTeam());
		servicerequesttypespage.selectNewServiceRequestTypePackage(data.getServiceTypePackage());
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();

		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage srlistpage = operationspage.clickNewServiceRequestList();
		srlistpage.selectAddServiceRequestsComboboxValue(data.getServiceType());
		srlistpage.clickAddServiceRequestButton();

		srlistpage.clickServiceEditButton();
		Assert.assertEquals(srlistpage.countAvailableServices(), 2);
		srlistpage.clickDoneButton();
		srlistpage.cancelNewServiceRequest();

		companypage = backofficeheader.clickCompanyLink();
		servicerequesttypespage = companypage.clickServiceRequestTypesLink();
		servicerequesttypespage.clickEditServiceRequestType(data.getServiceType());
		servicerequesttypespage.selectNewServiceRequestTypePackage(data.getServiceTypePackageNew());
		servicerequesttypespage.clickNewServiceRequestTypeOKButton();

		operationspage = backofficeheader.clickOperationsLink();
		srlistpage = operationspage.clickNewServiceRequestList();
		srlistpage.selectAddServiceRequestsComboboxValue(data.getServiceType());
		srlistpage.clickAddServiceRequestButton();

		srlistpage.clickServiceEditButton();
		Assert.assertEquals(srlistpage.getAllAvailableServices(), "52");
		srlistpage.clickDoneButton();
		srlistpage.cancelNewServiceRequest();

		companypage = backofficeheader.clickCompanyLink();
		servicerequesttypespage = companypage.clickServiceRequestTypesLink();
		servicerequesttypespage.deleteServiceRequestType(data.getServiceType());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyInvoiceTypeClientEdit(String rowID, String description, JSONObject testData) throws Exception {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InvoiceTypesWebPage invoicestypespage = companypage.clickInvoiceTypesLink();
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
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();

		clientspage.makeSearchPanelVisible();
		clientspage.setClientSearchCriteria(data.getClientName());
		clientspage.clickFindButton();
		if (clientspage.isClientPresentInTable(data.getClientName())) {
			clientspage.deleteClient(data.getClientName());
		}

		NewClientDialogWebPage newclientdialog = clientspage.clickAddClientButton();
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
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.setClientSearchCriteria(data.getClientName());
		clientspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		clientspage.clickClientUsersLinkForClient(data.getClientName());
		clientspage.searchClientUser(data.getFirstSearchParameter());
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 1);
		clientspage.searchClientUser(data.getSecondSearchParameter());
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 1);
		clientspage.searchClientUser(data.getThirdSearchParameter());
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 1);
		clientspage.searchClientUser(data.getLastSearchParameter());
		Assert.assertEquals(clientspage.getClientUsersTableRowCount(), 0);
		clientspage.closeClientServicesTab(mainWindowHandle);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanySharingWorkOrder(String rowID, String description, JSONObject testData) {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
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
    public void testCompanySharingWorkOrderAddRuleTeams(String rowID, String description, JSONObject testData) throws InterruptedException {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.isCompanyDisplayed(data.getEntry())){
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
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Clients Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Teams Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Teams Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanySharingWorkOrderAddRuleEmployees(String rowID, String description, JSONObject testData) throws InterruptedException {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
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
        if(interApplicationExchangePage.checkRuleByName(data.getRule())) {
            interApplicationExchangePage.deleteRule(data.getRule());
        }

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees","Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees","Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Employees Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Employees Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanySharingWorkOrderAddRuleClients(String rowID, String description, JSONObject testData) throws InterruptedException {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();
		
		if(interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
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
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Clients Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Clients Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanySharingWorkOrderAddRuleServices (String rowID, String description, JSONObject testData) throws InterruptedException {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.isCompanyDisplayed(data.getEntry())){
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

        if(interApplicationExchangePage.checkRuleByName(data.getRule())){
            interApplicationExchangePage.deleteRule(data.getRule());
        }
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services","Services","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services","Services","Include Selected");
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Services Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Services Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanySharingWorkOrderAddRuleVehicleParts(String rowID, String description, JSONObject testData) throws InterruptedException {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.isCompanyDisplayed(data.getEntry())) {
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
        interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts","Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts","Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Vehicle Parts Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyExchangeConfigurationSharingEstimate(String rowID, String description, JSONObject testData) {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")){
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
    public void testCompanyExchangeConfigurationSharingEstimateAddRuleClients(String rowID, String description, JSONObject testData) throws InterruptedException{

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
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

        interApplicationExchangePage.expandFirstCompanyProfile();
        String ruleByNumberBeforeChange = interApplicationExchangePage.getRuleNameByNumber(1);
        interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients",
                "Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Clients","Clients","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Clients (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Clients Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Clients (Clients Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Clients Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Clients Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyExchangeConfigurationSharingEstimateAddRuleTeams(String rowID, String description, JSONObject testData) throws InterruptedException{

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
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
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Clients Include Selected)"));
		
		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Teams","Teams","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Teams (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Teams Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Teams (Teams Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Teams Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Teams Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyExchangeConfigurationSharingEstimateAddRuleEmployees(String rowID, String description, JSONObject testData) throws InterruptedException{

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
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
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees",
                "Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
        Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Employees","Employees","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Employees (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Employees Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Employees (Employees Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Employees Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Employees Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyExchangeConfigurationSharingEstimateAddRuleServices(String rowID, String description, JSONObject testData) throws InterruptedException{

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
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
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services",
                "Services","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
		Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Services","Services","Include Selected");
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName("Include Selected Services (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Services Include Selected)"));
		
		interApplicationExchangePage.editRule("Include Selected Services (Services Include Selected)");
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Services Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Services Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyExchangeConfigurationSharingEstimateAddRuleVehicleParts(String rowID, String description, JSONObject testData) throws InterruptedException{

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();		
		
		if(interApplicationExchangePage.isCompanyDisplayed("Estimate JST for Name (Estimation)")) {
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
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts",
                "Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getCancelButton());
        Assert.assertEquals(ruleByNumberBeforeChange, interApplicationExchangePage.getRuleNameByNumber(1),
                "The data.getRule() has been added although the \"Cancel\" button was clicked");

		interApplicationExchangePage.clickAddRuleToFirstProfile();
		interApplicationExchangePage.fillFilterRuleBox("Include Selected Vehicle Parts","Vehicle Parts","Include Selected");
		interApplicationExchangePage.selectUsersWhileCreatingRule(3);
		interApplicationExchangePage.clickAddRuleBox(data.getInsertButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(data.getRule()));
		
		interApplicationExchangePage.editRule(data.getRule());
		interApplicationExchangePage.fillRuleBoxEdit("testAO");
		interApplicationExchangePage.clickEditRuleBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.checkRuleByName("testAO (Vehicle Parts Include Selected)"));
		
		interApplicationExchangePage.editRule(data.getRule());
		String newName = Long.toString(System.currentTimeMillis());
		interApplicationExchangePage.fillRuleBoxEdit(newName);
		interApplicationExchangePage.clickEditRuleBox(data.getUpdateButton());
		Assert.assertTrue(interApplicationExchangePage.checkRuleByName(newName+" (Vehicle Parts Include Selected)"));

		interApplicationExchangePage.deleteRule(newName+" (Vehicle Parts Include Selected)");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyExchangeConfigurationMappingWorkOrder(String rowID, String description, JSONObject testData) {

        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();
		
		if (interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)")) {
		interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name",data.getDocumentType(),"All Jay");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());   
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name",data.getDocumentType(),"All Jay");
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
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InterApplicationExchangeWebPage interApplicationExchangePage = companypage.clickInterApplicationExchangeLink();
		interApplicationExchangePage.clickTab(data.getTabName());
		interApplicationExchangePage.expandFirstCreatedCompany();	
		
		if(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)")){
			interApplicationExchangePage.deleteEntry("WO JST for Name (Work Order)");
		}
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name",data.getDocumentType(),"All Jay");
		interApplicationExchangePage.clickProfileDetailsBox(data.getCancelButton());
		Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed("WO JST for Name (Work Order)"));
		
		interApplicationExchangePage.clickAddProfileButton();
		interApplicationExchangePage.fillProfileDetails("WO JST for Name",data.getDocumentType(),"All Jay");
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

    //	 @Test(testName = "Test Case 27891:Company- Service Advisors: Authentication",
//             description = "Company- Service Advisors: Authentication",
//             dataProvider = "getUserData", dataProviderClass = DataProviderPool.class,
//             retryAnalyzer=Retry.class)
    public void testCompanyServiceAdvisorsAuthentication(String userName, String userPassword) throws InterruptedException {

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
        if (serviceadvisorspage.serviceAdvisorExists(firstname, lastname)) {
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
        loginpage.UserLogin(userName, userPassword);
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyServicesActiveParts(String rowID, String description, JSONObject testData) {
        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
        ServicesWebPage servicesWebPage = companyPage
                .clickServicesLink()
                .selectSearchServiceType(data.getServiceType())
                .setServiceSearchCriteria(data.getServiceName())
                .clickFindButton()
                .verifyActiveServiceDoesNotExist(data.getServiceName())
                .clickActivePartsTab();

        NewServiceDialogWebPage newServiceDialog = servicesWebPage
                .clickAddServiceButton()
                .setNewServiceName(data.getServiceName())
                .selectNewServiceType(data.getServiceType())
                .selectNewServicePriceType(data.getServicePriceType())
                .clickOKButton();

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
    public void testCompanyInterApplicationExchangeConfigMappingEstimation(String rowID, String description, JSONObject testData) {
        BOCompanyEditData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyEditData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        CompanyWebPage companyPage = backOfficeHeader.clickCompanyLink();
        InterApplicationExchangeWebPage interApplicationExchangePage = companyPage
                .clickInterApplicationExchangeLink()
                .clickSendingTab()
                .expandFirstCreatedCompany()
                .verifyCompanyDoesNotExist(data.getEntry())
                .verifyCompanyDoesNotExist(data.getProfileName())
                .clickAddProfileButton()
                .fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType())
                .clickCancelButton();
        Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
                "The company shouldn't be added after clicking the \"Cancel\" button");

        interApplicationExchangePage
                .clickAddProfileButton()
                .fillProfileDetails(data.getEntry(), data.getDocumentType(), data.getEntityType())
                .clickInsertButton();
        Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getEntry()),
                "The company should have been added after clicking the \"Insert\" button");

        interApplicationExchangePage
                .clickEditEntry(data.getEntry())
                .fillProfileDetailsEdit(data.getProfileName())
                .clickCancelButton();
        Assert.assertFalse(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
                "The company name shouldn't be edited after clicking the \"Cancel\" button");

        interApplicationExchangePage
                .clickEditEntry(data.getEntry())
                .fillProfileDetailsEdit(data.getProfileName())
                .clickActiveCheckBox()
                .clickUpdateButton();
        Assert.assertTrue(interApplicationExchangePage.isCompanyDisplayed(data.getProfileName()),
                "The company name should have been edited after clicking the \"Update\" button");
        Assert.assertTrue(interApplicationExchangePage.isEntryActive(data.getProfileName()),
                "The company profile is not active after clicking the \"Active\" checkbox");

        interApplicationExchangePage.deleteEntry(data.getProfileName());
    }
}