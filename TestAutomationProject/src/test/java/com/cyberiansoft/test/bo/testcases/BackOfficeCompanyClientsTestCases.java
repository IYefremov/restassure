package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@SuppressWarnings("LossyEncoding")
public class BackOfficeCompanyClientsTestCases extends BaseTestCase {
	
	@BeforeMethod
	public void backOfficeLogin(Method method) {
        System.out.printf("\n* Starting test : %s Method : %s\n", getClass(), method.getName());
        WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
		Thread.sleep(3000);
	}
	
	@Test(description = "Test Case 15322:Company- Clients: Search")
	public void testCompanyClientsSearch() throws Exception {
		final String clientname = "IntCompany";
		final String clienttype = "Wholesale";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		
		clientspage.verifyEmployeesTableColumnsAreVisible();
		
		Assert.assertEquals("1", clientspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", clientspage.getGoToPageFieldValue());
		
		clientspage.setPageSize("1");
		Assert.assertEquals(1, clientspage.getClientsTableRowsCount());
		
		String lastpagenumber = clientspage.getLastPageNumber();
		clientspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, clientspage.getGoToPageFieldValue().replace(",", ""));
		
		clientspage.clickGoToFirstPage();
//		Thread.sleep(1000);
		Assert.assertEquals("1", clientspage.getGoToPageFieldValue());
		
		clientspage.clickGoToNextPage();
		Assert.assertEquals("2", clientspage.getGoToPageFieldValue());
		
		clientspage.clickGoToPreviousPage();
		Assert.assertEquals("1", clientspage.getGoToPageFieldValue());

		clientspage.setPageSize("999");
		Assert.assertEquals(clientspage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(clientspage.getClientsTableRowsCount()));
		
		clientspage.makeSearchPanelVisible();
		clientspage.selectSearchType(clienttype);
		clientspage.setClientSearchCriteria(clientname.substring(0, 4).toLowerCase());
		clientspage.clickFindButton();
		
		clientspage.isClientPresentInTable(clientname);
	}	

	@Test(testName = "Test Case 24158:Company - Clients: Verify that �Notes� are added on �Client Details� screen and saved correctly for New Client", description = "Company - Clients: Verify that �Notes� are added on �Client Details� screen and saved correctly for New Client")
	public void testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForNewClient() throws Exception {
		final String companyname = "NotesCompany";
		final String companynote = "First note\nSecond note";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientPresentInTable(companyname)) {
			clientspage.deleteClient(companyname);
		}
		
		NewClientDialogWebPage newclientdlg = clientspage.clickAddClientButton();
		newclientdlg.setCompanyName(companyname);
		newclientdlg.setCompanyNotes(companynote);
		newclientdlg.clickOKButton();
		
		clientspage.searchClientByName(companyname);
		
		newclientdlg = clientspage.clickEditClient(companyname);
		Assert.assertEquals(companyname, newclientdlg.getCompanyName());
		Assert.assertEquals(companynote, newclientdlg.getCompanyNotes());
		newclientdlg.clickCancelButton();
		clientspage.deleteClient(companyname);
		clientspage.searchClientByName(companyname);
		Assert.assertFalse(clientspage.isClientPresentInTable(companyname));
	}
	
	@Test(testName = "Test Case 24159:Company - Clients: Verify that �Notes� are added on �Client Details� screen and saved correctly for Existing Client", description = "Company - Clients: Verify that �Notes� are added on �Client Details� screen and saved correctly for Existing Client")
	public void testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForExistingClient() throws Exception {

		final String companyname = "NotesCompany";
		final String companynote = "First note\nSecond note";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientPresentInTable(companyname)) {
			clientspage.deleteClient(companyname);
		}
		
		NewClientDialogWebPage newclientdlg = clientspage.clickAddClientButton();
		newclientdlg.setCompanyName(companyname);
		newclientdlg.clickOKButton();
		
		clientspage.searchClientByName(companyname);
		
		newclientdlg = clientspage.clickEditClient(companyname);
		Assert.assertEquals(companyname, newclientdlg.getCompanyName());
		newclientdlg.setCompanyNotes(companynote);
		newclientdlg.clickOKButton();
		newclientdlg = clientspage.clickEditClient(companyname);
		Assert.assertEquals(companyname, newclientdlg.getCompanyName());
		Assert.assertEquals(companynote, newclientdlg.getCompanyNotes());
		newclientdlg.clickCancelButton();

		clientspage.deleteClient(companyname);
		clientspage.searchClientByName(companyname);
		Assert.assertFalse(clientspage.isClientPresentInTable(companyname));
	}

	@Test(testName = "Test Case 24160:Company - Clients: Verify that added �Notes� on �Client Details� screen are visible as a popup on Clients grid", description = "Company - Clients: Verify that added �Notes� on �Client Details� screen are visible as a popup on Clients grid")
	public void testCompanyClientsVerifyThatAddedNotesOnClientDetailsScreenAreVisibleAsAPopupOnClientsGrid() throws Exception {

		final String companyname = "NotesCompany";
		final String companynote = "First note";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientPresentInTable(companyname)) {
			clientspage.deleteClient(companyname);
		}
		
		NewClientDialogWebPage newclientdlg = clientspage.clickAddClientButton();
		newclientdlg.setCompanyName(companyname);
		newclientdlg.setCompanyNotes(companynote);
		newclientdlg.clickOKButton();
		
		clientspage.searchClientByName(companyname);
		String notetxt = clientspage.mouseMoveToClientNotesGridAndGetNoteContent(companyname);
		Assert.assertEquals(companynote, notetxt);
		
		clientspage.deleteClient(companyname);
		clientspage.searchClientByName(companyname);
		Assert.assertFalse(clientspage.isClientPresentInTable(companyname));
	}

	@Test(testName = "Test Case 24209:Company - Clients: Verify that Client 'Notes' are imported from csv file", description = "Test Case 24209:Company - Clients: Verify that Client 'Notes' are imported from csv file")
	public void testCompanyClientsVerifyThatClientNotesAreImportedFromCSVFile() throws Exception {

		final String companyname = "CompanyNoteTest";
		final String companynote = "Test Note for import";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientPresentInTable(companyname)) {
			clientspage.deleteClient(companyname);
		}
		clientspage.importClients("Clients.csv");
		clientspage.searchClientByName(companyname);
		String notetxt = clientspage.mouseMoveToClientNotesGridAndGetNoteContent(companyname);
		Assert.assertEquals(companynote, notetxt);
				
		clientspage.deleteClient(companyname);
		clientspage.searchClientByName(companyname);
		Assert.assertFalse(clientspage.isClientPresentInTable(companyname));
	}
	
	@Test(testName = "Test Case 26725:Company- Clients: Archive", description = "Company- Clients: Archive")
	public void testCompanyClientsArchive() throws Exception {

		final String companyname = "000 1111";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();		
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		for (int i = 0; i < 3; i ++) {
			clientspage.deleteClient(companyname);
			clientspage.searchClientByName(companyname);
			Assert.assertFalse(clientspage.isClientPresentInTable(companyname));
		
			clientspage.clickArchivedTab();
			clientspage.searchClientByName(companyname);
			Assert.assertTrue(clientspage.isClientExistsInArchivedTable(companyname));
			clientspage.restoreClient(companyname);
			Assert.assertFalse(clientspage.isClientExistsInArchivedTable(companyname));
			clientspage.clickActiveTab();
		
			clientspage.searchClientByName(companyname);
			Assert.assertTrue(clientspage.isClientPresentInTable(companyname));
		}
	}
	
	@Test(testName = "Test Case 29760: Company- Clients: Client Users Update", description = "Company- Clients: Client Users Update" )
	public void testCompanyClientsClientUsersUpdate() throws Exception {
		
		final String clientname = "AlExTest";
		final String useremail = "test@cyberiansoft.com";
		final String userfstname = "test";
		final String userlstname = "automation";
		final String usercompany = "companytest";
		final String useraddress = "adress";
		final String usercity = "city";
		final String usercountry = "United States";
		final String userstateprovince = "Colorado";
		final String userzippostcode = "12345";
		final String userphone = "12345";
		final String useraccountid = "testtest";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		
		ClientsWebPage clientspage = companypage.clickClientsLink();
		
		clientspage.verifyEmployeesTableColumnsAreVisible();
		
		clientspage.searchClientByName(clientname);
		
		Assert.assertTrue(clientspage.isClientPresentInTable(clientname));
		
		ClientUsersWebPage  clientUsersWebPage = clientspage.
				clickClientUsersLinkForClientOpenDialogWindow(clientname);
		
		if (clientUsersWebPage.isClientUserPresentInTable(userfstname, userlstname)){
			clientUsersWebPage.clickDeleteClientUser(userfstname);
		}
			
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage = 
				clientUsersWebPage.clickAddUserBtn();
		
	        
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields(useremail, userfstname, userlstname);
		
      
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable(userfstname, userlstname));
		
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage1 =  
				clientUsersWebPage1.clickEditClientUser(userfstname);
		
		Assert.assertEquals(useremail, addclientUsersDialogWebPage1.getUserEmail());
		Assert.assertEquals(userfstname, addclientUsersDialogWebPage1.getUserFirstName());
		Assert.assertEquals(userlstname, addclientUsersDialogWebPage1.getUserLastName());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientChkboxChecked());
		
		ClientUsersWebPage  clientUsersWebPage2 = addclientUsersDialogWebPage1.
				editUserWithAlldFields(userfstname, userlstname, 
						usercompany, useraddress, usercity, usercountry, 
						userstateprovince, userzippostcode, userphone, useraccountid);
		
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage2 =  
				clientUsersWebPage2.clickEditClientUser(userfstname);
		
		Assert.assertEquals(useremail, addclientUsersDialogWebPage2.getUserEmail());
		Assert.assertEquals(userfstname, addclientUsersDialogWebPage2.getUserFirstName());
		Assert.assertEquals(userlstname, addclientUsersDialogWebPage2.getUserLastName());
		Assert.assertEquals(usercompany, addclientUsersDialogWebPage2.getUserCompanyName());
		Assert.assertEquals(useraddress, addclientUsersDialogWebPage2.getUserAddress());
		Assert.assertEquals(usercity, addclientUsersDialogWebPage2.getUserCity());
		Assert.assertEquals(usercountry, addclientUsersDialogWebPage2.getCountry());
		Assert.assertEquals(userstateprovince, addclientUsersDialogWebPage2.getStateProvince());
		Assert.assertEquals(userzippostcode, addclientUsersDialogWebPage2.getUserZipPostCode());
		Assert.assertEquals(userphone, addclientUsersDialogWebPage2.getUserPhone());
		Assert.assertEquals(useraccountid, addclientUsersDialogWebPage2.getUserAccountId());
		Assert.assertTrue(addclientUsersDialogWebPage1.isAllowCreatChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientAccountChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientMonManagChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isClientInspectChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isSalesPersonChkboxChecked());
		Assert.assertTrue(addclientUsersDialogWebPage1.isSalesPersonMonManagChkboxChecked());
 
		ClientUsersWebPage  clientUsersWebPage3 = 
				addclientUsersDialogWebPage2.closeUsersDialogWebPageWithoutEdit();
		
		
		clientUsersWebPage3.clickDeleteClientUser(userfstname);
 
		
		Assert.assertFalse(clientUsersWebPage3.isClientUserPresentInTable(userfstname, userlstname));
		
		clientUsersWebPage3.closePage();
 
	}
	
	
	//@Test(testName = "Test Case 48949: Company - Clients : Hours of operation - Add Wholesale", description = "Company - Clients : Hours of operation - Add Wholesale") 
	public void testCompanyClientsHoursOfOperationAddWholesale() throws Exception {
		
		final String companyname = "companyroman";
		final String workHoursstart = "10:00 AM";
		final String workHoursfinish = "3:00 PM";
		final String timeframe = "Working hours: 10:00 AM - 3:00 PM";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		
		clientspage.verifyEmployeesTableColumnsAreVisible();
		
		clientspage.searchClientByName(companyname);
		
		if (clientspage.isClientPresentInTable(companyname)) {
			clientspage.deleteClient(companyname);
		}
		
		NewClientDialogWebPage newClientDialogWebPage = clientspage.clickAddClientButton();
		
		Assert.assertTrue(newClientDialogWebPage.isCompanyWholesale());
 
		newClientDialogWebPage.setCompanyName(companyname);
		
		newClientDialogWebPage.clickToLinkInClientsEdit("Working Hours");
		newClientDialogWebPage.setWorkHoursStart(workHoursstart);
		newClientDialogWebPage.setWorkHoursFinish(workHoursfinish);
		
		newClientDialogWebPage.clickOKButton();
		
		clientspage.searchClientByName(companyname);
		
		Assert.assertEquals(timeframe, clientspage.mouseMoveToClientNotesGridAndGetNoteContent(companyname));
		
		clientspage.deleteClient(companyname);
		
		Assert.assertFalse(clientspage.isClientPresentInTable(companyname));
		
	}
	
	
	@Test(testName = "Test Case 48950: Company - Clients : Hours of operation - Add Retail", description = "Company - Clients : Hours of operation - Add Retail")
	public void testCompanyClientsHoursOfOperationAddRetail() throws Exception {
	 
		final String companyname = "companyroman";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		
		clientspage.verifyEmployeesTableColumnsAreVisible();
		
        NewClientDialogWebPage newClientDialogWebPage = clientspage.clickAddClientButton();
		
		Assert.assertTrue(newClientDialogWebPage.isCompanyWholesale());
		
		newClientDialogWebPage.setCompanyName(companyname);
		
		newClientDialogWebPage.switchToRetailCustomer();
	
		Assert.assertEquals(companyname,  newClientDialogWebPage.getCompanyName()); 
		Assert.assertFalse(newClientDialogWebPage.isWorkHoursStartVisible());
		Assert.assertFalse(newClientDialogWebPage.isWorkHoursFinishtVisible());
		
		newClientDialogWebPage.clickCancelButton();
	}
	
	
	@Test(testName = "Test Case 29762: Company - Clients: Contacts Verification Disable", description = "Company - Clients: Contacts Verification Disable")
	public void testCompanyClientsContactsVerificationDisable () throws Exception {
		
		final String clientname = "AlExTest";
		
		final String contactemail1 = "test1@cyberiansoft.com";
		final String contactfstname1 = "test1";
		final String contactlstname1 = "automation1";
		
		final String contactemail2 = "test2@cyberiansoft.com";
		final String contactfstname2 = "test2";
		final String contactlstname2 = "automation2";
		
		final String notification = "Email address is not validated and validation email is going to be sent to the following address.";
		final String pendingSendEmailMess = "Pending (sending email)";
		final String notVerifiedMess = "Not verified (email was sent)";
		final String verifiedMess = "Verified";
		final String colorRed = "red";
		final String colorGreen = "green";
		

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		
		ClientsWebPage clientspage = companypage.clickClientsLink();
		
		clientspage.verifyEmployeesTableColumnsAreVisible();
		
		clientspage.searchClientByName(clientname);
		
		Assert.assertTrue(clientspage.isClientPresentInTable(clientname));
		
		clientspage.unclickContactVerifDisableChkbox();
		
		String mainwindow =  webdriver.getWindowHandle();
		 
		ClientContactsWebPage  clientContactsWebPage = clientspage.
				clickContactsLinkForClientOpenDialogWindow(clientname);
		
		//delete contact1 if such exists
		if (clientContactsWebPage.isClientContactExistsInTable(contactfstname1, contactlstname1)){
			clientContactsWebPage.clickDeleteClientContact(contactfstname1);
		}
		
		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage =
				clientContactsWebPage.clickAddUserBtn();
		
		Assert.assertTrue(addEditClientUsersContactsDialogWebPage.isNotificationVisible());
		Assert.assertEquals(notification,  addEditClientUsersContactsDialogWebPage.getNotification()); 
		
		ClientContactsWebPage  clientContactsWebPage1 = 
				addEditClientUsersContactsDialogWebPage.createContactWithRequiredField(contactfstname1, 
						contactlstname1, contactemail1);
		
		Assert.assertEquals(pendingSendEmailMess, clientContactsWebPage1.getClientStatusText(contactfstname1));
 
		clientContactsWebPage1.waitForMessageChange(contactfstname1, notVerifiedMess);
		
		// verify message text and color for contact1
		Assert.assertEquals(notVerifiedMess, clientContactsWebPage1.getClientStatusText(contactfstname1));
		Assert.assertEquals(colorRed, clientContactsWebPage1.getClientStatusTextColor(contactfstname1));
		
		clientContactsWebPage1.closeNewTab(mainwindow);
		
		//Click on Contact Verification Disable and create contact 2 for client 
		clientspage.clickContactVerifDisableChkbox();
		
		ClientContactsWebPage  clientContactsWebPage2 = clientspage.
				clickContactsLinkForClientOpenDialogWindow(clientname);
		
		//delete contact2 if such exists
		if (clientContactsWebPage2.isClientContactExistsInTable(contactfstname2, contactlstname2)){
			clientContactsWebPage2.clickDeleteClientContact(contactfstname2);
		}
		
		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage1 =
				clientContactsWebPage2.clickAddUserBtn();
		Assert.assertFalse(addEditClientUsersContactsDialogWebPage1.isNotificationVisible());
	 
		ClientContactsWebPage  clientContactsWebPage3 = 
		addEditClientUsersContactsDialogWebPage1.createContactWithRequiredField(contactfstname2, 
						contactlstname2, contactemail2);
		
		// verify message text and color for contact2
		Assert.assertEquals(verifiedMess, clientContactsWebPage3.getClientStatusText(contactfstname2));
		Assert.assertEquals(colorGreen, clientContactsWebPage3.getClientStatusTextColor(contactfstname2));
		
		//delete all created contacts
		clientContactsWebPage3.clickDeleteClientContact(contactfstname1);
		clientContactsWebPage3.clickDeleteClientContact(contactfstname2);
		
		Assert.assertFalse(clientContactsWebPage3.isClientContactExistsInTable(contactfstname1, contactlstname1));
		Assert.assertFalse(clientContactsWebPage3.isClientContactExistsInTable(contactfstname2, contactlstname2));
		
		clientContactsWebPage3.closeNewTab(mainwindow);
		
		clientspage.unclickContactVerifDisableChkbox();
		
	}
	
	
	
	@Test(testName = "Test Case 29763: Company- Clients: Contacts Update", description = "Company- Clients: Contacts Update")
	public void testCompanyClientsContactsUpdate() throws Exception {
		
		final String clientname = "AlExTest";
		final String contactemail = "test@cyberiansoft.com";
		final String contactfstname = "test";
		final String contactlstname = "automation";
		final String contactcompany = "testcompany";  
		final String contactphone = "testphone";
		final String contactadress = "testadress"; 
		final String contactadress2 = "testadress2"; 
		final String contactcity = "testcity";
		final String contactcountry = "United States"; 
		final String contactstateprovince = "Colorado"; 
		final String contactzippostcode = "111111";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		
		ClientsWebPage clientspage = companypage.clickClientsLink();
		
		clientspage.verifyEmployeesTableColumnsAreVisible();
		
		clientspage.searchClientByName(clientname);
		
		Assert.assertTrue(clientspage.isClientPresentInTable(clientname));
		
		clientspage.unclickContactVerifDisableChkbox();
		 
		ClientContactsWebPage  clientContactsWebPage = clientspage.
				clickContactsLinkForClientOpenDialogWindow(clientname);
		
		
		if (clientContactsWebPage.isClientContactExistsInTable(contactfstname, contactlstname)){
			clientContactsWebPage.clickDeleteClientContact(contactfstname);
		}
		
		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage =
				clientContactsWebPage.clickAddUserBtn();
		
		ClientContactsWebPage  clientContactsWebPage1 = addEditClientUsersContactsDialogWebPage.
		         createContactWithRequiredFieldAndSkipValidation(contactfstname, contactlstname, contactemail);
		
		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage1 =
				clientContactsWebPage1.clickEditContactUser(contactfstname);
		
		Assert.assertEquals(contactfstname,  addEditClientUsersContactsDialogWebPage1.getContactFirstName());
		Assert.assertEquals(contactlstname,  addEditClientUsersContactsDialogWebPage1.getContactLastName());
		Assert.assertEquals(contactemail,  addEditClientUsersContactsDialogWebPage1.getContactEmail());
		
		ClientContactsWebPage  clientContactsWebPage2 = addEditClientUsersContactsDialogWebPage1.
		         createContactWithAllFields(contactfstname, contactlstname, contactemail, 
		        		 contactcompany, contactphone, contactadress, contactadress2, 
		        		 contactcity, contactcountry, contactstateprovince, contactzippostcode);
		
		
		AddEditClientUsersContactsDialogWebPage addEditClientUsersContactsDialogWebPage2 = 
				clientContactsWebPage2.clickEditContactUser(contactfstname);
		
		Assert.assertEquals(contactfstname,  addEditClientUsersContactsDialogWebPage2.getContactFirstName());
		Assert.assertEquals(contactlstname,  addEditClientUsersContactsDialogWebPage2.getContactLastName());
		Assert.assertEquals(contactemail,  addEditClientUsersContactsDialogWebPage2.getContactEmail());
		Assert.assertEquals(contactcompany,  addEditClientUsersContactsDialogWebPage2.getContactCompany());
		Assert.assertEquals(contactphone,  addEditClientUsersContactsDialogWebPage2.getContactPhone());
		Assert.assertEquals(contactadress,  addEditClientUsersContactsDialogWebPage2.getContactAdress());
		Assert.assertEquals(contactadress2,  addEditClientUsersContactsDialogWebPage2.getContactAdress2());
		Assert.assertEquals(contactcity,  addEditClientUsersContactsDialogWebPage2.getContactCity());
		Assert.assertEquals(contactcountry,  addEditClientUsersContactsDialogWebPage2.getCountry());
		Assert.assertEquals(contactstateprovince,  addEditClientUsersContactsDialogWebPage2.getStateProvince());
		Assert.assertEquals(contactzippostcode,  addEditClientUsersContactsDialogWebPage2.getContactZipPostCode());
		
		ClientContactsWebPage  clientContactsWebPage3 = addEditClientUsersContactsDialogWebPage2.closeContactsDialogWebPageWithoutEdit();
		clientContactsWebPage3.clickDeleteClientContact(contactfstname);
		Assert.assertFalse(clientContactsWebPage3.isClientContactExistsInTable(contactfstname, contactlstname));
		
		clientContactsWebPage3.closePage();
	}

	@Test(testName= "Test Case 66217:Company - Clients User : Add Client")
	public void testClientUserAddClient() throws InterruptedException{
	    String clientName = "000 15.11 Companey";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.scrollDownToText(clientName);
		ClientUsersWebPage  clientUsersWebPage =
				clientspage.clickClientUsersLinkForClientOpenDialogWindow(clientName);
		if (clientUsersWebPage.isClientUserPresentInTable( "test", "automation")){
			clientUsersWebPage.clickDeleteClientUser( "test");
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientMonManagReadOnlyChkbox();
		Assert.assertFalse(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields("automationvozniuk@gmail.com", "test", "automation");
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable("test", "automation"));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser("test");
		clientUsersWebPage1.closePage();
	}

	@Test(testName= "Test Case 66215:Company - Clients User : Sales Person Monitor Manager")
	public void testClientUserSalesPersonMonitorManager() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.scrollDownToText("000 15.11 Companey");
		ClientUsersWebPage  clientUsersWebPage =
				clientspage.clickClientUsersLinkForClientOpenDialogWindow("000 15.11 Companey");
		if (clientUsersWebPage.isClientUserPresentInTable( "test", "automation")){
			clientUsersWebPage.clickDeleteClientUser( "test");
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickSalesPersonMonManagChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields("automationvozniuk@gmail.com", "test", "automation");
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable("test", "automation"));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser("test");
		clientUsersWebPage1.closePage();
	}
	
	@Test(testName= "Test Case 66214:Company - Clients User : SalesPerson")
	public void testClientUserSalesPerson() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.scrollDownToText("000 15.11 Companey");
		ClientUsersWebPage  clientUsersWebPage =
				clientspage.clickClientUsersLinkForClientOpenDialogWindow("000 15.11 Companey");
		if (clientUsersWebPage.isClientUserPresentInTable( "test", "automation")){
			clientUsersWebPage.clickDeleteClientUser( "test");
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickSalesPersonChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields("automationvozniuk@gmail.com", "test", "automation");
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable("test", "automation"));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser("test");
		clientUsersWebPage1.closePage();
	}
	
	@Test(testName= "Test Case 66213:Company - Clients User : Client Inspector")
	public void testClientUserClientInspector() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.scrollDownToText("000 15.11 Companey");
		ClientUsersWebPage  clientUsersWebPage =
				clientspage.clickClientUsersLinkForClientOpenDialogWindow("000 15.11 Companey");
		if (clientUsersWebPage.isClientUserPresentInTable( "test", "automation")){
			clientUsersWebPage.clickDeleteClientUser( "test");
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientInspectChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields("automationvozniuk@gmail.com", "test", "automation");
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable("test", "automation"));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser("test");
		clientUsersWebPage1.closePage();
	}
	
	@Test(testName= "Test Case 66212:Company - Clients User : Client Monitor Manager")
	public void testClientUserClientMonitorManager() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.scrollDownToText("000 15.11 Companey");
		ClientUsersWebPage  clientUsersWebPage =
				clientspage.clickClientUsersLinkForClientOpenDialogWindow("000 15.11 Companey");
		if (clientUsersWebPage.isClientUserPresentInTable( "test", "automation")){
			clientUsersWebPage.clickDeleteClientUser( "test");
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientMonManagChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields("automationvozniuk@gmail.com", "test", "automation");
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable("test", "automation"));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser("test");
		clientUsersWebPage1.closePage();
	}
	
	@Test(testName= "Test Case 66211:Company - Clients User : ClientAccountant")
	public void testClientUserClientAccountant() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.scrollDownToText("000 15.11 Companey");
		ClientUsersWebPage  clientUsersWebPage =
				clientspage.clickClientUsersLinkForClientOpenDialogWindow("000 15.11 Companey");
		if (clientUsersWebPage.isClientUserPresentInTable( "test", "automation")){
			clientUsersWebPage.clickDeleteClientUser( "test");
		}
		AddEditClientUsersDialogWebPage addclientUsersDialogWebPage =  clientUsersWebPage.clickAddUserBtn();
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAddUserPopUp());
		addclientUsersDialogWebPage.clickButtonOk();
		Assert.assertTrue(addclientUsersDialogWebPage.checkAllPossibleValidators());
		addclientUsersDialogWebPage.clickClientAccountChkbox();
		Assert.assertTrue(addclientUsersDialogWebPage.checkIfOtherCheckBoxesRolesAvailable());
		ClientUsersWebPage  clientUsersWebPage1 = addclientUsersDialogWebPage.
				createUserWithRequiredFields("automationvozniuk@gmail.com", "test", "automation");
		Assert.assertTrue(clientUsersWebPage1.isClientUserPresentInTable("test", "automation"));
		clientUsersWebPage1.clickResendButton();
		clientUsersWebPage1.clickDeleteClientUser("test");
		clientUsersWebPage1.closePage();
	}
}
