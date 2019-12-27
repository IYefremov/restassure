package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOCompanyClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeCompanyClientsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOCompanyClientsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsSearch(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.verifyEmployeesTableColumnsAreVisible();

		Assert.assertEquals("1", clientsPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", clientsPage.getGoToPageFieldValue());

		clientsPage.setPageSize("1");
		Assert.assertEquals(1, clientsPage.getClientsTableRowsCount());

		String lastPageNumber = clientsPage.getLastPageNumber();
		clientsPage.clickGoToLastPage();
		Assert.assertEquals(lastPageNumber, clientsPage.getGoToPageFieldValue().replace(",", ""));

		clientsPage.clickGoToFirstPage();
		Assert.assertEquals("1", clientsPage.getGoToPageFieldValue());

		clientsPage.clickGoToNextPage();
		Assert.assertEquals("2", clientsPage.getGoToPageFieldValue());

		clientsPage.clickGoToPreviousPage();
		Assert.assertEquals("1", clientsPage.getGoToPageFieldValue());

		clientsPage.verifyEmployeesTableColumnsAreVisible();

		clientsPage.setPageSize("999");
		Assert.assertEquals(clientsPage.getClientsTableRowsCount(), clientsPage.MAX_TABLE_ROW_COUNT_VALUE);

		clientsPage.makeSearchPanelVisible();
		clientsPage.selectSearchType(data.getClientType());
		clientsPage.setClientSearchCriteria(data.getClientName().substring(0, 4).toLowerCase());
		clientsPage.clickFindButton();

		clientsPage.isClientPresentInTable(data.getClientName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void
	testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForNewClient(
			String rowID, String description, JSONObject testData) {
		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();

		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.searchClientByName(data.getCompanyName());
		if (clientsPage.isClientPresentInTable(data.getCompanyName())) {
			clientsPage.deleteClient(data.getCompanyName());
		}

		clientsPage.clickAddClientButton();
		newClientDialogWebPage.setCompanyName(data.getCompanyName());
		newClientDialogWebPage.setCompanyNotes(data.getCompanyNote());
		newClientDialogWebPage.clickOKButton();

		clientsPage.searchClientByName(data.getCompanyName());

		clientsPage.clickEditClient(data.getCompanyName());
		Assert.assertEquals(data.getCompanyName(), newClientDialogWebPage.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), newClientDialogWebPage.getCompanyNotes());
		newClientDialogWebPage.clickCancelButton();
		clientsPage.deleteClient(data.getCompanyName());
		clientsPage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientsPage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForExistingClient(
			String rowID, String description, JSONObject testData) {
		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.searchClientByName(data.getCompanyName());
		while (clientsPage.isClientPresentInTable(data.getCompanyName())) {
			clientsPage.deleteClient(data.getCompanyName());
		}

		clientsPage.clickAddClientButton();
		newClientDialogWebPage.setCompanyName(data.getCompanyName());
		newClientDialogWebPage.clickOKButton();

		clientsPage.searchClientByName(data.getCompanyName());

		clientsPage.clickEditClient(data.getCompanyName());
		Assert.assertEquals(data.getCompanyName(), newClientDialogWebPage.getCompanyName());
		newClientDialogWebPage.setCompanyNotes(data.getCompanyNote());
		newClientDialogWebPage.clickOKButton();
		clientsPage.clickEditClient(data.getCompanyName());
		Assert.assertEquals(data.getCompanyName(), newClientDialogWebPage.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), newClientDialogWebPage.getCompanyNotes());
		newClientDialogWebPage.clickCancelButton();

		clientsPage.deleteClient(data.getCompanyName());
		clientsPage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientsPage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsVerifyThatAddedNotesOnClientDetailsScreenAreVisibleAsAPopupOnClientsGrid(
			String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.searchClientByName(data.getCompanyName());
		if (clientsPage.isClientPresentInTable(data.getCompanyName())) {
			clientsPage.deleteClient(data.getCompanyName());
		}

		clientsPage.clickAddClientButton();
		newClientDialogWebPage.setCompanyName(data.getCompanyName());
		newClientDialogWebPage.setCompanyNotes(data.getCompanyNote());
		newClientDialogWebPage.clickOKButton();

		clientsPage.searchClientByName(data.getCompanyName());
		String notext = clientsPage.moveToClientNotesGridAndGetNoteContent(data.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), notext);

		clientsPage.deleteClient(data.getCompanyName());
		clientsPage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientsPage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsVerifyThatClientNotesAreImportedFromCSVFile(
			String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.searchClientByName(data.getCompanyName());
		if (clientsPage.isClientPresentInTable(data.getCompanyName())) {
			clientsPage.deleteClient(data.getCompanyName());
		}
		clientsPage.importClients(data.getImportFile());
		clientsPage.searchClientByName(data.getCompanyName());
		String notetxt = clientsPage.moveToClientNotesGridAndGetNoteContent(data.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), notetxt);

		clientsPage.deleteClient(data.getCompanyName());
		clientsPage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientsPage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsArchive(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.searchClientByName(data.getCompanyName());
		clientsPage.verifyClientIsPresentInActiveTab(data.getCompanyName());
		for (int i = 0; i < 3; i++) {
			clientsPage.deleteClient(data.getCompanyName());
			clientsPage.searchClientByName(data.getCompanyName());
			Assert.assertFalse(clientsPage.isClientPresentInTable(data.getCompanyName()));

			clientsPage.clickArchivedTab();
			clientsPage.searchClientByName(data.getCompanyName());
			Assert.assertTrue(clientsPage.clientExistsInArchivedTable(data.getCompanyName()));
			clientsPage.restoreClient(data.getCompanyName());
			Assert.assertFalse(clientsPage.clientExistsInArchivedTable(data.getCompanyName()));
			clientsPage.clickActiveTab();

			clientsPage.searchClientByName(data.getCompanyName());
			Assert.assertTrue(clientsPage.isClientPresentInTable(data.getCompanyName()));
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsClientUsersUpdate(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.verifyEmployeesTableColumnsAreVisible();
		clientsPage.searchClientByName(data.getClientName());
		Assert.assertTrue(clientsPage.isClientPresentInTable(data.getClientName()));

		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		if (clientUsersWebPage.isClientUserPresentInTable(data.getUserFirstName(), data.getUserLastName())) {
			clientUsersWebPage.clickDeleteClientUser(data.getUserFirstName());
		}

		clientUsersWebPage.clickAddUserBtn();
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage.createUserWithRequiredFields(
				data.getUserEmail(), data.getUserFirstName(), data.getUserLastName());
		Assert.assertTrue(clientUsersWebPage1.
				isClientUserPresentInTable(data.getUserFirstName(), data.getUserLastName()));
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage1 = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage1.clickEditClientUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserEmail(), addclientUsersDialogWebPage1.getUserEmail());
		Assert.assertEquals(data.getUserFirstName(), addclientUsersDialogWebPage1.getUserFirstName());
		Assert.assertEquals(data.getUserLastName(), addclientUsersDialogWebPage1.getUserLastName());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientChkboxChecked());

		ClientUsersWebPage clientUsersWebPage2 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage1.
				editUserWithAlldFields(data.getUserFirstName(), data.getUserLastName(),
						data.getUserCompany(), data.getUserAddress(), data.getUserCity(), data.getUserCountry(),
						data.getUserStateProvince(), data.getUserZipPostCode(), data.getUserPhone(), data.getUserAccountId());

		AddEditClientUsersDialogWebPage addClientUsersDialogWebPage2 = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage2.clickEditClientUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserEmail(), addClientUsersDialogWebPage2.getUserEmail());
		Assert.assertEquals(data.getUserFirstName(), addClientUsersDialogWebPage2.getUserFirstName());
		Assert.assertEquals(data.getUserLastName(), addClientUsersDialogWebPage2.getUserLastName());
		Assert.assertEquals(data.getUserCompany(), addClientUsersDialogWebPage2.getUserCompanyName());
		Assert.assertEquals(data.getUserAddress(), addClientUsersDialogWebPage2.getUserAddress());
		Assert.assertEquals(data.getUserCity(), addClientUsersDialogWebPage2.getUserCity());
		Assert.assertEquals(data.getUserCountry(), addClientUsersDialogWebPage2.getCountry());
		Assert.assertEquals(data.getUserStateProvince(), addClientUsersDialogWebPage2.getStateProvince());
		Assert.assertEquals(data.getUserZipPostCode(), addClientUsersDialogWebPage2.getUserZipPostCode());
		Assert.assertEquals(data.getUserPhone(), addClientUsersDialogWebPage2.getUserPhone());
		Assert.assertEquals(data.getUserAccountId(), addClientUsersDialogWebPage2.getUserAccountId());
		Assert.assertTrue(addclientUsersDialogWebPage1.isAllowCreatChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientAccountChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientMonManagChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientInspectChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isSalesPersonChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isSalesPersonMonManagChkboxChecked());

		ClientUsersWebPage clientUsersWebPage3 = new ClientUsersWebPage(webdriver);
		addClientUsersDialogWebPage2.closeUsersDialogWebPageWithoutEdit();
		clientUsersWebPage3.clickDeleteClientUser(data.getUserFirstName());
		Assert.assertFalse(clientUsersWebPage3.isClientUserPresentInTable(data.getUserFirstName(), data.getUserLastName()));
		clientUsersWebPage3.closePage();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsHoursOfOperationAddWholesale(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.verifyEmployeesTableColumnsAreVisible();
		clientsPage.searchClientByName(data.getTimeFrame());

		if (clientsPage.isClientPresentInTable(data.getTimeFrame())) {
			clientsPage.deleteClient(data.getTimeFrame());
		}

		clientsPage.clickAddClientButton();
		Assert.assertTrue(newClientDialogWebPage.isCompanyWholesale());

		newClientDialogWebPage.setCompanyName(data.getTimeFrame());

		newClientDialogWebPage.clickToLinkInClientsEdit("Working Hours");
		newClientDialogWebPage.setWorkHoursStart(data.getWorkHoursStart());
		newClientDialogWebPage.setWorkHoursFinish(data.getWorkHoursFinish());

		newClientDialogWebPage.clickOKButton();
		clientsPage.searchClientByName(data.getTimeFrame());
//		Assert.assertEquals(data.getTimeFrame(), clientsPage.moveToClientNotesGridAndGetNoteContent(data.getTimeFrame())); todo uncomment, if timeframe appears
		clientsPage.deleteClient(data.getTimeFrame());
		Assert.assertFalse(clientsPage.isClientPresentInTable(data.getTimeFrame()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsHoursOfOperationAddRetail(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.verifyEmployeesTableColumnsAreVisible();
		clientsPage.clickAddClientButton();
		Assert.assertTrue(newClientDialogWebPage.isCompanyWholesale());
		newClientDialogWebPage.setCompanyName(data.getCompanyName());
		newClientDialogWebPage.switchToRetailCustomer();

		Assert.assertEquals(data.getCompanyName(), newClientDialogWebPage.getCompanyName());
		Assert.assertFalse(newClientDialogWebPage.isWorkHoursStartVisible());
		Assert.assertFalse(newClientDialogWebPage.isWorkHoursFinishtVisible());
		newClientDialogWebPage.clickCancelButton();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsContactsVerificationDisable(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientContactsWebPage clientContactsWebPage = new ClientContactsWebPage(webdriver);
		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage =
				new AddEditClientUsersContactsDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.verifyEmployeesTableColumnsAreVisible();
		clientsPage.searchClientByName(data.getClientName());
		Assert.assertTrue(clientsPage.isClientPresentInTable(data.getClientName()));
		clientsPage.unclickContactVerifDisableChkbox();
		String mainwindow = webdriver.getWindowHandle();
		clientsPage.clickContactsLinkForClientOpenDialogWindow(data.getClientName());

		//delete contact1 if such exists
		if (clientContactsWebPage.isClientContactPresentInTable(data.getContactFirstName(), data.getContactLastName())) {
			clientContactsWebPage.clickDeleteClientContact(data.getContactFirstName());
		}


		clientContactsWebPage.clickAddUserBtn();
		Assert.assertTrue(addEditClientUsersContactsDialogWebPage.isNotificationVisible());
		Assert.assertEquals(data.getNotification(), addEditClientUsersContactsDialogWebPage.getNotification());
		ClientContactsWebPage clientContactsWebPage1 = new ClientContactsWebPage(webdriver);
		addEditClientUsersContactsDialogWebPage.createContactWithRequiredField(data.getContactFirstName(),
				data.getContactLastName(), data.getContactEmail());

		Assert.assertEquals(data.getPendingSendEmailMess(),
				clientContactsWebPage1.getClientStatusText(data.getContactFirstName()));

		clientContactsWebPage1.waitForMessageChange(data.getContactFirstName(), data.getNotVerifiedMess());

		// verify message text and color for contact1
		Assert.assertEquals(data.getNotVerifiedMess(),
				clientContactsWebPage1.getClientStatusText(data.getContactFirstName()));
		if (browserType.getBrowserTypeString().equalsIgnoreCase("edge")) {
			Assert.assertEquals(data.getColorRed(), clientContactsWebPage1.getClientStatusTextColor(data.getContactFirstName()));
		} else {
			Assert.assertEquals(data.getRed(), clientContactsWebPage1.getClientStatusTextColor(data.getContactFirstName()));
		}

		clientContactsWebPage1.closeNewTab(mainwindow);

		//Click on Contact Verification Disable and create contact 2 for client
		clientsPage.clickContactVerifDisableChkbox();
		ClientContactsWebPage clientContactsWebPage2 = new ClientContactsWebPage(webdriver);
		clientsPage.clickContactsLinkForClientOpenDialogWindow(data.getClientName());

		//delete contact2 if such exists
		if (clientContactsWebPage2.isClientContactPresentInTable(data.getContactFirstName2(), data.getContactLastName2())) {
			clientContactsWebPage2.clickDeleteClientContact(data.getContactFirstName2());
		}

		AddEditClientUsersContactsDialogWebPage dialogWebPage = new AddEditClientUsersContactsDialogWebPage(webdriver);
		clientContactsWebPage2.clickAddUserBtn();
		Assert.assertFalse(dialogWebPage.isNotificationVisible());
		ClientContactsWebPage clientContactsWebPage3 = new ClientContactsWebPage(webdriver);
		dialogWebPage.createContactWithRequiredField(
				data.getContactFirstName2(), data.getContactLastName2(), data.getContactEmail2());

		// verify message text and color for contact2
		Assert.assertEquals(data.getVerifiedMess(), clientContactsWebPage3.getClientStatusText(data.getContactFirstName2()));
		if (browserType.getBrowserTypeString().equalsIgnoreCase("edge")) {
			Assert.assertEquals(data.getColorGreen(), clientContactsWebPage3.getClientStatusTextColor(data.getContactFirstName2()));
		} else {
			Assert.assertEquals(data.getGreen(), clientContactsWebPage3.getClientStatusTextColor(data.getContactFirstName2()));
		}
//        Assert.assertEquals(data.getColorGreen(), clientContactsWebPage3.getClientStatusTextColor(data.getContactFirstName2()));

		//delete all created contacts
		clientContactsWebPage3.clickDeleteClientContact(data.getContactFirstName());
		clientContactsWebPage3.clickDeleteClientContact(data.getContactFirstName2());

		Assert.assertFalse(clientContactsWebPage3.isClientContactPresentInTable(data.getContactFirstName(), data.getContactLastName()));
		Assert.assertFalse(clientContactsWebPage3.isClientContactPresentInTable(data.getContactFirstName2(), data.getContactLastName2()));
		clientContactsWebPage3.closeNewTab(mainwindow);
		clientsPage.unclickContactVerifDisableChkbox();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsContactsUpdate(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.verifyEmployeesTableColumnsAreVisible();
		clientsPage.searchClientByName(data.getClientName());
		Assert.assertTrue(clientsPage.isClientPresentInTable(data.getClientName()));
		clientsPage.unclickContactVerifDisableChkbox();
		ClientContactsWebPage clientContactsWebPage = new ClientContactsWebPage(webdriver);
		clientsPage.clickContactsLinkForClientOpenDialogWindow(data.getClientName());

		if (clientContactsWebPage.isClientContactPresentInTable(data.getUserFirstName(), data.getUserLastName())) {
			clientContactsWebPage.clickDeleteClientContact(data.getUserFirstName());
		}

		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage =
				new AddEditClientUsersContactsDialogWebPage(webdriver);
		clientContactsWebPage.clickAddUserBtn();

		ClientContactsWebPage clientContactsWebPage1 = new ClientContactsWebPage(webdriver);
		addEditClientUsersContactsDialogWebPage.
				createContactWithRequiredFieldAndSkipValidation(data.getUserFirstName(), data.getUserLastName(), data.getContactEmail());

		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage1 =
				new AddEditClientUsersContactsDialogWebPage(webdriver);
		clientContactsWebPage1.clickEditContactUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserFirstName(), addEditClientUsersContactsDialogWebPage1.getContactFirstName());
		Assert.assertEquals(data.getUserLastName(), addEditClientUsersContactsDialogWebPage1.getContactLastName());
		Assert.assertEquals(data.getContactEmail(), addEditClientUsersContactsDialogWebPage1.getContactEmail());

		ClientContactsWebPage clientContactsWebPage2 = new ClientContactsWebPage(webdriver);
		addEditClientUsersContactsDialogWebPage1.createContactWithAllFields(data);

		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage2 =
				new AddEditClientUsersContactsDialogWebPage(webdriver);
		clientContactsWebPage2.clickEditContactUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserFirstName(), addEditClientUsersContactsDialogWebPage2.getContactFirstName());
		Assert.assertEquals(data.getUserLastName(), addEditClientUsersContactsDialogWebPage2.getContactLastName());
		Assert.assertEquals(data.getContactEmail(), addEditClientUsersContactsDialogWebPage2.getContactEmail());
		Assert.assertEquals(data.getCompanyName(), addEditClientUsersContactsDialogWebPage2.getContactCompany());
		Assert.assertEquals(data.getContactPhone(), addEditClientUsersContactsDialogWebPage2.getContactPhone());
		Assert.assertEquals(data.getUserAddress(), addEditClientUsersContactsDialogWebPage2.getContactAdress());
		Assert.assertEquals(data.getUserAddress2(), addEditClientUsersContactsDialogWebPage2.getContactAdress2());
		Assert.assertEquals(data.getUserCity(), addEditClientUsersContactsDialogWebPage2.getContactCity());
		Assert.assertEquals(data.getUserCountry(), addEditClientUsersContactsDialogWebPage2.getCountry());
		Assert.assertEquals(data.getUserStateProvince(), addEditClientUsersContactsDialogWebPage2.getStateProvince());
		Assert.assertEquals(data.getUserZipPostCode(), addEditClientUsersContactsDialogWebPage2.getContactZipPostCode());

		ClientContactsWebPage clientContactsWebPage3 = new ClientContactsWebPage(webdriver);
		addEditClientUsersContactsDialogWebPage2.closeContactsDialogWebPageWithoutEdit();
		clientContactsWebPage3.clickDeleteClientContact(data.getUserFirstName());
		Assert.assertFalse(clientContactsWebPage3.isClientContactPresentInTable(data.getUserFirstName(), data.getUserLastName()));

		clientContactsWebPage3.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserAddClient(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.setClientSearchCriteria(data.getClientName());
		clientsPage.clickFindButton();
		clientsPage.scrollDownToText(data.getClientName());
		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		while (clientUsersWebPage.isClientUserPresentInTable(data.getUserFirstName(), data.getUserFirstName())) {
			clientUsersWebPage.clickDeleteClientUser(data.getUserFirstName());
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientMonManagReadOnlyChkbox();
		Assert.assertFalse(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserSalesPersonMonitorManager(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.setClientSearchCriteria(data.getClientName());
		clientsPage.clickFindButton();
		clientsPage.scrollDownToText(data.getClientName());
		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		while (clientUsersWebPage.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName())) {
			clientUsersWebPage.clickDeleteClientUser(data.getUserName());
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickSalesPersonMonManagChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage.createUserWithRequiredFields
				(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserSalesPerson(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.setClientSearchCriteria(data.getClientName());
		clientsPage.clickFindButton();
		clientsPage.scrollDownToText(data.getClientName());
		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickSalesPersonChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserClientInspector(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.setClientSearchCriteria(data.getClientName());
		clientsPage.clickFindButton();
		clientsPage.scrollDownToText(data.getClientName());
		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());


		clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientInspectChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserClientMonitorManager(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.setClientSearchCriteria(data.getClientName());
		clientsPage.clickFindButton();
		clientsPage.scrollDownToText(data.getClientName());
		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientMonManagChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserClientAccountant(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		ClientUsersWebPage clientUsersWebPage = new ClientUsersWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.makeSearchPanelVisible();
		clientsPage.setClientSearchCriteria(data.getClientName());
		clientsPage.clickFindButton();
		clientsPage.scrollDownToText(data.getClientName());
		clientsPage.clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
		AddEditClientUsersDialogWebPage editClientUsersDialogWebPage = new AddEditClientUsersDialogWebPage(webdriver);
		clientUsersWebPage.clickAddUserBtn();
		editClientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(editClientUsersDialogWebPage.checkAddUserPopUp());
		editClientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(editClientUsersDialogWebPage.checkAllPossibleValidators());
		editClientUsersDialogWebPage.clickClientAccountChkbox();
		Assert.assertTrue(editClientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage clientUsersWebPage1 = new ClientUsersWebPage(webdriver);
		editClientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()),
				"The client user is not displayed in the table!");
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyWorkOrderCanBeAssignedToWholesaleClients(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		String randomClientName = data.getRandomName();

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.clickAddClientButton();
		newClientDialogWebPage.switchToWholesaleCustomer();
		newClientDialogWebPage.setCompanyName(randomClientName);
		newClientDialogWebPage.clickOtherTab();
		newClientDialogWebPage.clickSingleWOtypeCheckbox();
		newClientDialogWebPage.selectRandomSingleWOtype();
		newClientDialogWebPage.clickOKButton();
		clientsPage.makeSearchPanelVisible();
		clientsPage.selectSearchType(data.getClientType());
		clientsPage.setClientSearchCriteria(randomClientName);
		clientsPage.clickFindButton();

		Assert.assertTrue(clientsPage.isClientPresentInTable(randomClientName), "The client has not been found");
		clientsPage.verifyOneClientIsFound();
		Assert.assertTrue(clientsPage.isFirstWholeSaleCheckboxChecked(),
				"The Wholesale checkbox is not checked for the created company");
		Assert.assertTrue(clientsPage.isFirstSingleWOtypeCheckboxChecked(),
				"The single WO type checkbox is not checked for the created company");
		clientsPage.archiveFirstClient();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyWorkOrderTypeIsNotChangedAfterClickingTheCancelButton(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);

		String randomClientName = data.getRandomName();

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.clickAddClientButton();
		newClientDialogWebPage.switchToWholesaleCustomer();
		newClientDialogWebPage.setCompanyName(randomClientName);
		newClientDialogWebPage.clickOtherTab();
		newClientDialogWebPage.clickSingleWOtypeCheckbox();
		newClientDialogWebPage.selectRandomSingleWOtype();
		newClientDialogWebPage.clickOKButton();
		clientsPage.makeSearchPanelVisible();
		clientsPage.selectSearchType(data.getClientType());
		clientsPage.setClientSearchCriteria(randomClientName);
		clientsPage.clickFindButton();

		Assert.assertTrue(clientsPage.isClientPresentInTable(randomClientName), "The client has not been found");
		NewClientDialogWebPage newClientDialogPage = new NewClientDialogWebPage(webdriver);
		clientsPage.verifyOneClientIsFound();
		clientsPage.clickEditClient(randomClientName);
		newClientDialogPage.clickOtherTab();
		String singleWOtypeBefore = newClientDialogPage.getSingleWOtype();
		newClientDialogPage.selectRandomSingleWOtype();
		newClientDialogPage.clickCancelButton();
		clientsPage.clickEditClient(randomClientName);
		newClientDialogPage.clickOtherTab();
		Assert.assertEquals(singleWOtypeBefore, newClientDialogPage.getSingleWOtype(),
				"The single WO type has been changed after clicking the 'Cancel' button");
		newClientDialogPage.clickCancelButton();
		clientsPage.archiveFirstClient();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyWOtypesOptionIsNotShownForTheSingleWOTypeClient(String rowID, String description, JSONObject testData) {

		BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
		String randomClientName = data.getRandomName();

		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		ClientsWebPage clientsPage = new ClientsWebPage(webdriver);
		NewClientDialogWebPage newClientDialogWebPage = new NewClientDialogWebPage(webdriver);

		backOfficeHeader.clickCompanyLink();
		companyWebPage.clickClientsLink();
		clientsPage.clickAddClientButton();
		newClientDialogWebPage.switchToWholesaleCustomer();
		newClientDialogWebPage.setCompanyName(randomClientName);
		newClientDialogWebPage.clickOtherTab();
		newClientDialogWebPage.clickSingleWOtypeCheckbox();
		newClientDialogWebPage.selectRandomSingleWOtype();
		newClientDialogWebPage.clickOKButton();
		clientsPage.makeSearchPanelVisible();
		clientsPage.selectSearchType(data.getClientType());
		clientsPage.setClientSearchCriteria(randomClientName);
		clientsPage.clickFindButton();

		Assert.assertTrue(clientsPage.isClientPresentInTable(randomClientName), "The client has not been found");
		clientsPage.verifyOneClientIsFound();
		Assert.assertFalse(clientsPage.isWOtypeForFirstClientDisplayed(),
				"WO Types option is displayed for the single WO type Wholesale client");
		clientsPage.archiveFirstClient();
	}
}