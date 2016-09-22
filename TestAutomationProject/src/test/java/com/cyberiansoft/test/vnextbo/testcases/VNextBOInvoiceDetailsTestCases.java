package com.cyberiansoft.test.vnextbo.testcases;

import java.util.UUID;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewClientDialogWebPage;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.screens.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextBOInvoiceDetailsTestCases extends BaseTestCase {
	
	final String techfirstname = "Test";
	final String techlastname = "Technician";
	final String usermailprefix = "test.cyberiansoft+";
	final String usermailpostbox = "@gmail.com";
	final String techuserphone = "98988989";
	
	@Test(description = "Test Case 45496:vNext: setup configuration to run Invoice list suite")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "backofficeold.url" })
	public void testSetupConfigurationToRunInvoiceListSuite(String backofficeurl,
			String userName, String userPassword, String oldbourl) {
		final String companyname = "AMT";
		final String firstname = "Retail";
		final String lastname = "Automation";
		final String companymail = "test.cyberiansoft+retail@gmail.com";
		final String companyphone = "911";
		final String shiptoaddress = "Rynok square 12";
		final String shiptoaddress2 = "AP 24";
		final String shiptocity = "Lviv";
		final String country = "United States";
		final String state = "California";
		final String shiptozip = "79031";
		
		webdriverGotoWebPage(backofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		final String usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(techfirstname, techlastname, usermail, techuserphone, false);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
		
		webdriverGotoWebPage(oldbourl);
		BackOfficeLoginWebPage oldloginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		oldloginpage.UserLogin(userName, userPassword);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		final String fullcustomer = firstname + " " + lastname;
		clientspage.searchClientByName(fullcustomer);
		
		if (!clientspage.isClientExistsInTable(fullcustomer)) {	
			NewClientDialogWebPage newclientpage = clientspage.clickAddClientButton();
			newclientpage.setCompanyName(companyname);
			newclientpage.setClientFirstName(firstname);
			newclientpage.setClientLastName(lastname);
			newclientpage.setCompanyMail(companymail);
			newclientpage.setCompanyPhone(companyphone);
			newclientpage.setCompanyShipToAddress(shiptoaddress);
			newclientpage.setCompanyShipToAddress2(shiptoaddress2);
			newclientpage.setCompanyShipToCity(shiptocity);
			newclientpage.selectCompanyShipToCountry(country);
			newclientpage.selectCompanyShipToState(state);
			newclientpage.setCompanyShipToZip(shiptozip);
			newclientpage.clickOKButton();
		}
	}

}
