package com.cyberiansoft.test.bo.testcases;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewClientDialogWebPage;

public class BackOfficeCompanyClientsTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
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
		//Thread.sleep(3000);
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
		Thread.sleep(1000);
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
		
		clientspage.isClientExistsInTable(clientname);
	}	

	@Test(testName = "Test Case 24158:Company - Clients: Verify that ’Notes’ are added on ‘Client Details’ screen and saved correctly for New Client", description = "Company - Clients: Verify that ’Notes’ are added on ‘Client Details’ screen and saved correctly for New Client")
	public void testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForNewClient() throws Exception {

		final String companyname = "NotesCompany";
		final String companynote = "First note\nSecond note";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientExistsInTable(companyname)) {
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
		Assert.assertFalse(clientspage.isClientExistsInTable(companyname));
	}
	
	@Test(testName = "Test Case 24159:Company - Clients: Verify that ’Notes’ are added on ‘Client Details’ screen and saved correctly for Existing Client", description = "Company - Clients: Verify that ’Notes’ are added on ‘Client Details’ screen and saved correctly for Existing Client")
	public void testCompanyClientsVerifyThatNotesAreAddedOnClientDetailsScreenAndSavedCorrectlyForExistingClient() throws Exception {

		final String companyname = "NotesCompany";
		final String companynote = "First note\nSecond note";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientExistsInTable(companyname)) {
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
		Assert.assertFalse(clientspage.isClientExistsInTable(companyname));
	}
	

	@Test(testName = "Test Case 24160:Company - Clients: Verify that added ‘Notes’ on ‘Client Details’ screen are visible as a popup on Clients grid", description = "Company - Clients: Verify that added ‘Notes’ on ‘Client Details’ screen are visible as a popup on Clients grid")
	public void testCompanyClientsVerifyThatAddedNotesOnClientDetailsScreenAreVisibleAsAPopupOnClientsGrid() throws Exception {

		final String companyname = "NotesCompany";
		final String companynote = "First note";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		if (clientspage.isClientExistsInTable(companyname)) {
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
		Assert.assertFalse(clientspage.isClientExistsInTable(companyname));
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
		if (clientspage.isClientExistsInTable(companyname)) {
			clientspage.deleteClient(companyname);
		}
		clientspage.importClients("Clients.csv");
		clientspage.searchClientByName(companyname);
		String notetxt = clientspage.mouseMoveToClientNotesGridAndGetNoteContent(companyname);
		Assert.assertEquals(companynote, notetxt);
				
		clientspage.deleteClient(companyname);
		clientspage.searchClientByName(companyname);
		Assert.assertFalse(clientspage.isClientExistsInTable(companyname));
	}
	
	@Test(testName = "Test Case 26725:Company- Clients: Archive", description = "Company- Clients: Archive")
	public void testCompanyClientsArchive() throws Exception {

		final String companyname = "Ostap Bender";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();

		ClientsWebPage clientspage = companypage.clickClientsLink();		
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		for (int i = 0; i < 3; i ++) {
			clientspage.deleteClient(companyname);
			clientspage.searchClientByName(companyname);
			Assert.assertFalse(clientspage.isClientExistsInTable(companyname));
		
			clientspage.clickArchivedTab();
			clientspage.searchClientByName(companyname);
			Assert.assertTrue(clientspage.isClientExistsInArchivedTable(companyname));
			clientspage.restoreClient(companyname);
			Assert.assertFalse(clientspage.isClientExistsInArchivedTable(companyname));
			clientspage.clickActiveTab();
		
			clientspage.searchClientByName(companyname);
			Assert.assertTrue(clientspage.isClientExistsInTable(companyname));
		}
	}

}
