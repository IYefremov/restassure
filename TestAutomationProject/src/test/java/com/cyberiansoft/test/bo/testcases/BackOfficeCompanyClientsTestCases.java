package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOCompanyClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@SuppressWarnings("LossyEncoding")
@Listeners(VideoListener.class)
public class BackOfficeCompanyClientsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOCompanyClientsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsSearch(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);

        ClientsWebPage clientspage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink();

        clientspage.verifyEmployeesTableColumnsAreVisible();

        Assert.assertEquals("1", clientspage.getCurrentlySelectedPageNumber());
        org.testng.Assert.assertEquals("1", clientspage.getGoToPageFieldValue());

        clientspage.setPageSize("1");
        Assert.assertEquals(1, clientspage.getClientsTableRowsCount());

        String lastpagenumber = clientspage.getLastPageNumber();
        clientspage.clickGoToLastPage();
        Assert.assertEquals(lastpagenumber, clientspage.getGoToPageFieldValue().replace(",", ""));

        clientspage.clickGoToFirstPage();
        Assert.assertEquals("1", clientspage.getGoToPageFieldValue());

        clientspage.clickGoToNextPage();
        Assert.assertEquals("2", clientspage.getGoToPageFieldValue());

        clientspage.clickGoToPreviousPage();
        Assert.assertEquals("1", clientspage.getGoToPageFieldValue());

        clientspage.verifyEmployeesTableColumnsAreVisible();

        clientspage.setPageSize("999");
        Assert.assertEquals(clientspage.getClientsTableRowsCount(), clientspage.MAX_TABLE_ROW_COUNT_VALUE);

        clientspage.makeSearchPanelVisible();
        clientspage.selectSearchType(data.getClientType());
        clientspage.setClientSearchCriteria(data.getClientName().substring(0, 4).toLowerCase());
        clientspage.clickFindButton();

        clientspage.isClientPresentInTable(data.getClientName());
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void
    testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForNewClient(
            String rowID, String description, JSONObject testData) {
        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);

		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(data.getCompanyName());
		if (clientspage.isClientPresentInTable(data.getCompanyName())) {
			clientspage.deleteClient(data.getCompanyName());
		}

		NewClientDialogWebPage newclientdlg = clientspage.clickAddClientButton();
		newclientdlg.setCompanyName(data.getCompanyName());
		newclientdlg.setCompanyNotes(data.getCompanyNote());
		newclientdlg.clickOKButton();

		clientspage.searchClientByName(data.getCompanyName());

		newclientdlg = clientspage.clickEditClient(data.getCompanyName());
		Assert.assertEquals(data.getCompanyName(), newclientdlg.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), newclientdlg.getCompanyNotes());
		newclientdlg.clickCancelButton();
		clientspage.deleteClient(data.getCompanyName());
		clientspage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientspage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForExistingClient(
            String rowID, String description, JSONObject testData) {
        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);

		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(data.getCompanyName());
		while (clientspage.isClientPresentInTable(data.getCompanyName())) {
			clientspage.deleteClient(data.getCompanyName());
		}

		NewClientDialogWebPage newclientdlg = clientspage.clickAddClientButton();
		newclientdlg.setCompanyName(data.getCompanyName());
		newclientdlg.clickOKButton();

		clientspage.searchClientByName(data.getCompanyName());

		newclientdlg = clientspage.clickEditClient(data.getCompanyName());
		Assert.assertEquals(data.getCompanyName(), newclientdlg.getCompanyName());
		newclientdlg.setCompanyNotes(data.getCompanyNote());
		newclientdlg.clickOKButton();
		newclientdlg = clientspage.clickEditClient(data.getCompanyName());
		Assert.assertEquals(data.getCompanyName(), newclientdlg.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), newclientdlg.getCompanyNotes());
		newclientdlg.clickCancelButton();

		clientspage.deleteClient(data.getCompanyName());
		clientspage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientspage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsVerifyThatAddedNotesOnClientDetailsScreenAreVisibleAsAPopupOnClientsGrid(
            String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(data.getCompanyName());
		if (clientspage.isClientPresentInTable(data.getCompanyName())) {
			clientspage.deleteClient(data.getCompanyName());
		}

		NewClientDialogWebPage newclientdlg = clientspage.clickAddClientButton();
		newclientdlg.setCompanyName(data.getCompanyName());
		newclientdlg.setCompanyNotes(data.getCompanyNote());
		newclientdlg.clickOKButton();

		clientspage.searchClientByName(data.getCompanyName());
		String notext = clientspage.mouseMoveToClientNotesGridAndGetNoteContent(data.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), notext);

		clientspage.deleteClient(data.getCompanyName());
		clientspage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientspage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsVerifyThatClientNotesAreImportedFromCSVFile(
	        String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(data.getCompanyName());
		if (clientspage.isClientPresentInTable(data.getCompanyName())) {
			clientspage.deleteClient(data.getCompanyName());
		}
		clientspage.importClients(data.getImportFile());
		clientspage.searchClientByName(data.getCompanyName());
		String notetxt = clientspage.mouseMoveToClientNotesGridAndGetNoteContent(data.getCompanyName());
		Assert.assertEquals(data.getCompanyNote(), notetxt);

		clientspage.deleteClient(data.getCompanyName());
		clientspage.searchClientByName(data.getCompanyName());
		Assert.assertFalse(clientspage.isClientPresentInTable(data.getCompanyName()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsArchive(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(data.getCompanyName());
		clientspage.verifyClientIsPresentInActiveTab(data.getCompanyName());
		for (int i = 0; i < 3; i ++) {
			clientspage.deleteClient(data.getCompanyName());
			clientspage.searchClientByName(data.getCompanyName());
			Assert.assertFalse(clientspage.isClientPresentInTable(data.getCompanyName()));

			clientspage.clickArchivedTab();
			clientspage.searchClientByName(data.getCompanyName());
			Assert.assertTrue(clientspage.clientExistsInArchivedTable(data.getCompanyName()));
			clientspage.restoreClient(data.getCompanyName());
			Assert.assertFalse(clientspage.clientExistsInArchivedTable(data.getCompanyName()));
			clientspage.clickActiveTab();

			clientspage.searchClientByName(data.getCompanyName());
			Assert.assertTrue(clientspage.isClientPresentInTable(data.getCompanyName()));
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsClientUsersUpdate(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.verifyEmployeesTableColumnsAreVisible();
		clientspage.searchClientByName(data.getClientName());
		Assert.assertTrue(clientspage.isClientPresentInTable(data.getClientName()));

		ClientUsersWebPage  clientUsersWebPage = clientspage.
				clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

		if (clientUsersWebPage.isClientUserPresentInTable(data.getUserFirstName(), data.getUserLastName())){
			clientUsersWebPage.clickDeleteClientUser(data.getUserFirstName());
		}

		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = clientUsersWebPage.clickAddUserBtn();
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.createUserWithRequiredFields(
		        data.getUserEmail(), data.getUserFirstName(), data.getUserLastName());
		Assert.assertTrue(clientUsersWebPage1.
                isClientUserPresentInTable(data.getUserFirstName(), data.getUserLastName()));
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage1 =
				clientUsersWebPage1.clickEditClientUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserEmail(), addclientUsersDialogWebPage1.getUserEmail());
		Assert.assertEquals(data.getUserFirstName(), addclientUsersDialogWebPage1.getUserFirstName());
		Assert.assertEquals(data.getUserLastName(), addclientUsersDialogWebPage1.getUserLastName());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientChkboxChecked());

		ClientUsersWebPage  clientUsersWebPage2 = addclientUsersDialogWebPage1.
				editUserWithAlldFields(data.getUserFirstName(), data.getUserLastName(),
                        data.getUserCompany(), data.getUserAddress(), data.getUserCity(), data.getUserCountry(),
						data.getUserStateProvince(), data.getUserZipPostCode(), data.getUserPhone(), data.getUserAccountId());

		AddEditClientUsersDialogWebPage addClientUsersDialogWebPage2 =
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

		ClientUsersWebPage clientUsersWebPage3 = addClientUsersDialogWebPage2.closeUsersDialogWebPageWithoutEdit();
				clientUsersWebPage3.clickDeleteClientUser(data.getUserFirstName());
		Assert.assertFalse(clientUsersWebPage3.isClientUserPresentInTable(data.getUserFirstName(), data.getUserLastName()));
		clientUsersWebPage3.closePage();
	}


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompanyClientsHoursOfOperationAddWholesale(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientsWebPage clientspage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink();
		clientspage.verifyEmployeesTableColumnsAreVisible();
		clientspage.searchClientByName(data.getTimeFrame());

		if (clientspage.isClientPresentInTable(data.getTimeFrame())) {
			clientspage.deleteClient(data.getTimeFrame());
		}

		NewClientDialogWebPage newClientDialogWebPage = clientspage.clickAddClientButton();
		Assert.assertTrue(newClientDialogWebPage.isCompanyWholesale());

		newClientDialogWebPage.setCompanyName(data.getTimeFrame());

		newClientDialogWebPage.clickToLinkInClientsEdit("Working Hours");
		newClientDialogWebPage.setWorkHoursStart(data.getWorkHoursStart());
		newClientDialogWebPage.setWorkHoursFinish(data.getWorkHoursFinish());

		newClientDialogWebPage.clickOKButton();
		clientspage.searchClientByName(data.getTimeFrame());
//		Assert.assertEquals(data.getTimeFrame(), clientspage.mouseMoveToClientNotesGridAndGetNoteContent(data.getTimeFrame())); todo uncomment, if timeframe appears
		clientspage.deleteClient(data.getTimeFrame());
		Assert.assertFalse(clientspage.isClientPresentInTable(data.getTimeFrame()));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsHoursOfOperationAddRetail(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.verifyEmployeesTableColumnsAreVisible();
        NewClientDialogWebPage newClientDialogWebPage = clientspage.clickAddClientButton();
		Assert.assertTrue(newClientDialogWebPage.isCompanyWholesale());
		newClientDialogWebPage.setCompanyName(data.getCompanyName());
		newClientDialogWebPage.switchToRetailCustomer();

		Assert.assertEquals(data.getCompanyName(), newClientDialogWebPage.getCompanyName());
		Assert.assertFalse(newClientDialogWebPage.isWorkHoursStartVisible());
		Assert.assertFalse(newClientDialogWebPage.isWorkHoursFinishtVisible());
		newClientDialogWebPage.clickCancelButton();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsContactsVerificationDisable (String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.verifyEmployeesTableColumnsAreVisible();
		clientspage.searchClientByName(data.getClientName());
		Assert.assertTrue(clientspage.isClientPresentInTable(data.getClientName()));
		clientspage.unclickContactVerifDisableChkbox();
		String mainwindow =  webdriver.getWindowHandle();
		ClientContactsWebPage  clientContactsWebPage = clientspage.clickContactsLinkForClientOpenDialogWindow(data.getClientName());

		//delete contact1 if such exists
		if (clientContactsWebPage.isClientContactPresentInTable(data.getContactFirstName(), data.getContactLastName())){
			clientContactsWebPage.clickDeleteClientContact(data.getContactFirstName());
		}

		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage =
				clientContactsWebPage.clickAddUserBtn();
		Assert.assertTrue(addEditClientUsersContactsDialogWebPage.isNotificationVisible());
		Assert.assertEquals(data.getNotification(),  addEditClientUsersContactsDialogWebPage.getNotification());
		ClientContactsWebPage  clientContactsWebPage1 =
				addEditClientUsersContactsDialogWebPage.createContactWithRequiredField(data.getContactFirstName(),
						data.getContactLastName(), data.getContactEmail());

		Assert.assertEquals(data.getPendingSendEmailMess(),
                clientContactsWebPage1.getClientStatusText(data.getContactFirstName()));

		clientContactsWebPage1.waitForMessageChange(data.getContactFirstName(), data.getNotVerifiedMess());

		// verify message text and color for contact1
		Assert.assertEquals(data.getNotVerifiedMess(),
                clientContactsWebPage1.getClientStatusText(data.getContactFirstName()));
		Assert.assertEquals(data.getColorRed(),
                clientContactsWebPage1.getClientStatusTextColor(data.getContactFirstName()));
		clientContactsWebPage1.closeNewTab(mainwindow);

		//Click on Contact Verification Disable and create contact 2 for client
		clientspage.clickContactVerifDisableChkbox();
		ClientContactsWebPage  clientContactsWebPage2 = clientspage.
				clickContactsLinkForClientOpenDialogWindow(data.getClientName());

		//delete contact2 if such exists
		if (clientContactsWebPage2.isClientContactPresentInTable(data.getContactFirstName2(), data.getContactLastName2())){
			clientContactsWebPage2.clickDeleteClientContact(data.getContactFirstName2());
		}

		AddEditClientUsersContactsDialogWebPage dialogWebPage = clientContactsWebPage2.clickAddUserBtn();
		Assert.assertFalse(dialogWebPage.isNotificationVisible());
		ClientContactsWebPage  clientContactsWebPage3 = dialogWebPage.createContactWithRequiredField(
		        data.getContactFirstName2(), data.getContactLastName2(), data.getContactEmail2());

		// verify message text and color for contact2
		Assert.assertEquals(data.getVerifiedMess(), clientContactsWebPage3.getClientStatusText(data.getContactFirstName2()));
		Assert.assertEquals(data.getColorGreen(), clientContactsWebPage3.getClientStatusTextColor(data.getContactFirstName2()));

		//delete all created contacts
		clientContactsWebPage3.clickDeleteClientContact(data.getContactFirstName());
		clientContactsWebPage3.clickDeleteClientContact(data.getContactFirstName2());

		Assert.assertFalse(clientContactsWebPage3.isClientContactPresentInTable(data.getContactFirstName(), data.getContactLastName()));
		Assert.assertFalse(clientContactsWebPage3.isClientContactPresentInTable(data.getContactFirstName2(), data.getContactLastName2()));
		clientContactsWebPage3.closeNewTab(mainwindow);
		clientspage.unclickContactVerifDisableChkbox();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCompanyClientsContactsUpdate(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		CompanyWebPage companypage = backOfficeHeader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.verifyEmployeesTableColumnsAreVisible();
		clientspage.searchClientByName(data.getClientName());
		Assert.assertTrue(clientspage.isClientPresentInTable(data.getClientName()));
		clientspage.unclickContactVerifDisableChkbox();
		ClientContactsWebPage  clientContactsWebPage = clientspage.
				clickContactsLinkForClientOpenDialogWindow(data.getClientName());


		if (clientContactsWebPage.isClientContactPresentInTable(data.getUserFirstName(), data.getUserLastName())){
			clientContactsWebPage.clickDeleteClientContact(data.getUserFirstName());
		}

		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage =
				clientContactsWebPage.clickAddUserBtn();

		ClientContactsWebPage  clientContactsWebPage1 = addEditClientUsersContactsDialogWebPage.
		         createContactWithRequiredFieldAndSkipValidation(data.getUserFirstName(), data.getUserLastName(), data.getContactEmail());

		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage1 =
				clientContactsWebPage1.clickEditContactUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserFirstName(),  addEditClientUsersContactsDialogWebPage1.getContactFirstName());
		Assert.assertEquals(data.getUserLastName(),  addEditClientUsersContactsDialogWebPage1.getContactLastName());
		Assert.assertEquals(data.getContactEmail(),  addEditClientUsersContactsDialogWebPage1.getContactEmail());

		ClientContactsWebPage  clientContactsWebPage2 = addEditClientUsersContactsDialogWebPage1.
		         createContactWithAllFields(data.getUserFirstName(), data.getUserLastName(), data.getContactEmail(),
		        		 data.getCompanyName(), data.getContactPhone(), data.getUserAddress(), data.getUserAddress2(),
		        		 data.getUserCity(), data.getUserCountry(), data.getUserStateProvince(), data.getUserZipPostCode());


		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage2 =
				clientContactsWebPage2.clickEditContactUser(data.getUserFirstName());

		Assert.assertEquals(data.getUserFirstName(),  addEditClientUsersContactsDialogWebPage2.getContactFirstName());
		Assert.assertEquals(data.getUserLastName(),  addEditClientUsersContactsDialogWebPage2.getContactLastName());
		Assert.assertEquals(data.getContactEmail(),  addEditClientUsersContactsDialogWebPage2.getContactEmail());
		Assert.assertEquals(data.getCompanyName(),  addEditClientUsersContactsDialogWebPage2.getContactCompany());
		Assert.assertEquals(data.getContactPhone(),  addEditClientUsersContactsDialogWebPage2.getContactPhone());
		Assert.assertEquals(data.getUserAddress(),  addEditClientUsersContactsDialogWebPage2.getContactAdress());
		Assert.assertEquals(data.getUserAddress2(),  addEditClientUsersContactsDialogWebPage2.getContactAdress2());
		Assert.assertEquals(data.getUserCity(),  addEditClientUsersContactsDialogWebPage2.getContactCity());
		Assert.assertEquals(data.getUserCountry(),  addEditClientUsersContactsDialogWebPage2.getCountry());
		Assert.assertEquals(data.getUserStateProvince(),  addEditClientUsersContactsDialogWebPage2.getStateProvince());
		Assert.assertEquals(data.getUserZipPostCode(),  addEditClientUsersContactsDialogWebPage2.getContactZipPostCode());

		ClientContactsWebPage  clientContactsWebPage3 = addEditClientUsersContactsDialogWebPage2.closeContactsDialogWebPageWithoutEdit();
		clientContactsWebPage3.clickDeleteClientContact(data.getUserFirstName());
		Assert.assertFalse(clientContactsWebPage3.isClientContactPresentInTable(data.getUserFirstName(), data.getUserLastName()));

		clientContactsWebPage3.closePage();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testClientUserAddClient(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientUsersWebPage  clientUsersWebPage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .makeSearchPanelVisible()
                .setClientSearchCriteria(data.getClientName())
                .clickFindButton()
                .scrollDownToText(data.getClientName())
                .clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

        while (clientUsersWebPage.isClientUserPresentInTable(data.getUserFirstName(), data.getUserFirstName())){
			clientUsersWebPage.clickDeleteClientUser(data.getUserFirstName());
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientMonManagReadOnlyChkbox();
		Assert.assertFalse(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserSalesPersonMonitorManager(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientUsersWebPage  clientUsersWebPage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .makeSearchPanelVisible()
                .setClientSearchCriteria(data.getClientName())
                .clickFindButton()
                .scrollDownToText(data.getClientName())
                .clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

        while (clientUsersWebPage.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName())){
			clientUsersWebPage.clickDeleteClientUser(data.getUserName());
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickSalesPersonMonManagChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testClientUserSalesPerson(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientUsersWebPage  clientUsersWebPage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .makeSearchPanelVisible()
                .setClientSearchCriteria(data.getClientName())
                .clickFindButton()
                .scrollDownToText(data.getClientName())
                .clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

        clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
        AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickSalesPersonChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testClientUserClientInspector(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientUsersWebPage  clientUsersWebPage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .makeSearchPanelVisible()
                .setSearchUserParameter(data.getClientName())
                .clickFindButton()
                .scrollDownToText(data.getClientName())
                .clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

        clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
        AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientInspectChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
		clientUsersWebPage1.closePage();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testClientUserClientMonitorManager(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientUsersWebPage  clientUsersWebPage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .makeSearchPanelVisible()
                .setSearchUserParameter(data.getClientName())
                .clickFindButton()
                .scrollDownToText(data.getClientName())
                .clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

        clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
        AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
        addclientUsersDialogWebPage.clickButtonOk();
        Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
        addclientUsersDialogWebPage.clickButtonOk();
        Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
        addclientUsersDialogWebPage.clickClientMonManagChkbox();
        Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
        ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
                createUserWithRequiredFields(data.getUserEmail(), data.getUserName(), data.getUserFirstName());
        Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(data.getUserName(), data.getUserFirstName()));
        clientUsersWebPage1.clickResendButton();
        clientUsersWebPage1.clickDeleteClientUser(data.getUserName());
        clientUsersWebPage1.closePage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testClientUserClientAccountant(String rowID, String description, JSONObject testData) {

        BOCompanyClientsData data = JSonDataParser.getTestDataFromJson(testData, BOCompanyClientsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ClientUsersWebPage  clientUsersWebPage = backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .makeSearchPanelVisible()
                .setSearchUserParameter(data.getClientName())
                .clickFindButton()
                .scrollDownToText(data.getClientName())
                .clickClientUsersLinkForClientOpenDialogWindow(data.getClientName());

        clientUsersWebPage.verifyClientUserDoesNotExist(data.getUserName(), data.getUserFirstName());
        AddEditClientUsersDialogWebPage editClientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		editClientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(editClientUsersDialogWebPage.checkAddUserPopUp());
		editClientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(editClientUsersDialogWebPage.checkAllPossibleValidators());
		editClientUsersDialogWebPage.clickClientAccountChkbox();
		Assert.assertTrue(editClientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = editClientUsersDialogWebPage.
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
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        String randomClientName = data.getRandomName();
        backOfficeHeader
                .clickCompanyLink()
                .clickClientsLink()
                .clickAddClientButton()
                .switchToWholesaleCustomer()
                .setCompanyName(randomClientName)
                .clickOtherTab()
                .clickSingleWOtypeCheckbox()
                .selectRandomSingleWOtype()
                .clickOKButton()
                .makeSearchPanelVisible()
                .selectSearchType(data.getClientType())
                .setClientSearchCriteria(randomClientName)
                .clickFindButton();
        //todo finish

    }
}
