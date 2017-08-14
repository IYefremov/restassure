package com.cyberiansoft.test.vnext.testcases;

import java.io.IOException;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;


public class vNextCustomersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	/*@AfterSuite
	public void deleteClients() throws IOException {
		VNextWebServicesUtils.deleteClientsByMail("cnolan@gmail.com");
	}*/
	
	@Test(testName= "Test Case 43519:vNext - Create new Customer with empty First Name and Last Name", 
			description = "Create new Customer with empty First Name and Last Name")
	public void testCreateNewCustomerWithEmptyFirstNameAndLastName() throws InterruptedException {
		
		final String firstname = "";
		final String lastname = "";
		final String companyname = "AquaAuto";
		final String customeremail = "";
		final String customerphone = "444-51-09";
		final String customeraddress = "Test address street, 1";
		final String customercountry = "Mexico";
		final String customerstate = "Colima";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(firstname, lastname, companyname, customeremail, customerphone, customeraddress, customercountry, customerstate);
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomerByCompanyName(WordUtils.capitalize(companyname));
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), firstname);
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), lastname);
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), companyname);
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), customeremail);
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), customerphone);
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), WordUtils.capitalize(customeraddress));
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), customercountry);
		Assert.assertEquals(newcustomerscreen.getCustomerState(), customerstate);
		customersscreen = newcustomerscreen.clickBackButton();
		homescreen = customersscreen.clickBackButton();
		homescreen.waitABit(45000);
		initiateWebDriver();
		webdriver.get(VNextConfigInfo.getInstance().getBackOfficeVnextDevURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextConfigInfo.getInstance().getUserVnextDevUserName(), VNextConfigInfo.getInstance().getUserVnextDevUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(companyname);
		clientspage.waitABit(1000);
		clientspage.deleteClient(companyname);
		Assert.assertFalse(clientspage.isClientExistsInTable(companyname));
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 43522:vNext - Verify customer created in Offline mode is available after DB update", 
			description = "Verify customer created in Offline mode is available after DB update")
	public void testVerifyCustomerCreatedInOfflineModeIsAvailableAfterDBUpdate() {
		
		final String firstname = "Christofer";
		final String lastname = "Nolan";
		final String companyname = "Toyota-Diamant";
		final String customeremail = "cnolan@gmail.com";
		final String customerphone = "032-449-56";
		final String customeraddress = "";
		final String customercountry = "Germany";
		final String customerstate = "Saarland";
		
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		setNetworkOff();
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(firstname, lastname, companyname, customeremail, customerphone, customeraddress, customercountry, customerstate);
		setNetworkOn();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		homescreen = customersscreen.clickBackButton();
		homescreen.waitABit(1000);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		final String customer = firstname + " " + lastname;
		customersscreen.selectCustomer(customer);
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), firstname);
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), lastname);
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), companyname);
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), customeremail);
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), customerphone);
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), customeraddress);
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), customercountry);
		Assert.assertEquals(newcustomerscreen.getCustomerState(), customerstate);
		customersscreen = newcustomerscreen.clickBackButton();
		homescreen = customersscreen.clickBackButton();
		
	}
	
	@Test(testName= "vNext - Verify new customer is available after DB update", 
			description = "Verify new customer is available after DB update")
	public void testVerifyNewCustomerIsAvailableAfterDBUpdate() {
		
		final String firstname = "Test";
		final String lastname = "DBUpdateCustomer";
		final String companyname = "Toyota-Diamant";
		final String customeremail = "cnolan@gmail.com";
		final String customerphone = "032-449-56";
		final String customeraddress = "";
		final String customercountry = "Germany";
		final String customerstate = "Saarland";
		
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(firstname, lastname, companyname, customeremail, customerphone, customeraddress, customercountry, customerstate);
		customersscreen = new VNextCustomersScreen(appiumdriver);
		homescreen = customersscreen.clickBackButton();
		homescreen.waitABit(1000);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		final String customer = firstname + " " + lastname;
		customersscreen.selectCustomer(customer);
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), firstname);
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), lastname);
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), companyname);
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), customeremail);
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), customerphone);
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), customeraddress);
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), customercountry);
		Assert.assertEquals(newcustomerscreen.getCustomerState(), customerstate);
		customersscreen = newcustomerscreen.clickBackButton();
		homescreen = customersscreen.clickBackButton();
		
	}

}
