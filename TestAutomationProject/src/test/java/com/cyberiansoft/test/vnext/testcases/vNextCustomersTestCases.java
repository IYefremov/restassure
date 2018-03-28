package com.cyberiansoft.test.vnext.testcases;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.utils.VNextRetailCustomer;


public class vNextCustomersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	/*@AfterSuite
	public void deleteClients() throws IOException {
		VNextWebServicesUtils.deleteClientsByMail("cnolan@gmail.com");
	}*/
	
	@Test(testName= "Test Case 43519:vNext - Create new Customer with empty First Name and Last Name", 
			description = "Create new Customer with empty First Name and Last Name")
	public void testCreateNewCustomerWithEmptyFirstNameAndLastName() throws InterruptedException {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer();
		testcustomer.setMailAddress("");
		testcustomer.setCompanyName("AquaAuto");
		testcustomer.setCustomerAddress1("Test address street, 1");
		testcustomer.setCustomerPhone("444-51-09");
		testcustomer.setCustomerCountry("Mexico");
		testcustomer.setCustomerState("Colima");
		
		deleteCustomerOnBackOffice(testcustomer.getCompany(), "");

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomerByCompanyName(testcustomer.getCompany());
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), upperCaseAllFirst(testcustomer.getCustomerAddress1()));
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newcustomerscreen.getCustomerState(), testcustomer.getCustomerState());
		customersscreen = newcustomerscreen.clickBackButton();
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		BaseUtils.waitABit(45000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(VNextConfigInfo.getInstance().getBackOfficeVnextDevURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextConfigInfo.getInstance().getUserVnextDevUserName(), VNextConfigInfo.getInstance().getUserVnextDevUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(testcustomer.getCompany());
		clientspage.waitABit(1000);
		clientspage.deleteClient(testcustomer.getCompany());
		Assert.assertFalse(clientspage.isClientPresentInTable(testcustomer.getCompany()));
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 43522:vNext - Verify customer created in Offline mode is available after DB update", 
			description = "Verify customer created in Offline mode is available after DB update")
	public void testVerifyCustomerCreatedInOfflineModeIsAvailableAfterDBUpdate() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Christofer", "Nolan");
		testcustomer.setMailAddress("cnolan@gmail.com");
		testcustomer.setCompanyName("Toyota-Diamant");
		testcustomer.setCustomerAddress1("");
		testcustomer.setCustomerPhone("032-449-56");
		testcustomer.setCustomerCountry("Germany");
		testcustomer.setCustomerState("Saarland");
		
		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		AppiumUtils.setNetworkOff();
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		AppiumUtils.setNetworkOn();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		BaseUtils.waitABit(1000);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.selectCustomer(testcustomer);
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), testcustomer.getCustomerAddress1());
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newcustomerscreen.getCustomerState(), testcustomer.getCustomerState());
		customersscreen = newcustomerscreen.clickBackButton();
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
	}
	
	@Test(testName= "vNext - Verify new customer is available after DB update", 
			description = "Verify new customer is available after DB update")
	public void testVerifyNewCustomerIsAvailableAfterDBUpdate() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Test", "DBUpdateCustomer");
		testcustomer.setMailAddress("cnolan@gmail.com");
		testcustomer.setCompanyName("Toyota-Diamant");
		testcustomer.setCustomerAddress1("");
		testcustomer.setCustomerPhone("032-449-56");
		testcustomer.setCustomerCountry("Germany");
		testcustomer.setCustomerState("Saarland");
		
		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		BaseUtils.waitABit(30000);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.selectCustomer(testcustomer);
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), testcustomer.getCustomerAddress1());
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newcustomerscreen.getCustomerState(), testcustomer.getCustomerState());
		customersscreen = newcustomerscreen.clickBackButton();
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
	}
	
	public void deleteCustomerOnBackOffice(String firstName, String lastName) {
		
		String customerDelete = "";
		
		if (lastName.length() > 2)
			customerDelete = firstName + " " + lastName;
		else
			customerDelete = firstName;
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(VNextConfigInfo.getInstance().getBackOfficeVnextDevURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextConfigInfo.getInstance().getUserVnextDevUserName(), VNextConfigInfo.getInstance().getUserVnextDevUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(customerDelete);
		clientspage.waitABit(1000);
		while (clientspage.isClientPresentInTable(customerDelete))
			clientspage.deleteClient(customerDelete);
		Assert.assertFalse(clientspage.isClientPresentInTable(customerDelete));
		webdriver.quit();
	}
	
	public String upperCaseAllFirst(String value) {

        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }
        return new String(array);
    }

}
