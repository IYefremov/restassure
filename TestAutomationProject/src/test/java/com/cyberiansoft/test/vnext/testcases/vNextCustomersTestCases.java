package com.cyberiansoft.test.vnext.testcases;


import com.cyberiansoft.test.baseutils.StringUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextCustomersMenuScreen;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;


public class vNextCustomersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/customers-testcases-data.json";
	private static String settingsbofficeurl;

	@BeforeClass(description="Team Customers Test Cases")
	public void beforeClass() {

		if (envType.equals(EnvironmentType.DEVELOPMENT))
			settingsbofficeurl = VNextFreeRegistrationInfo.getInstance().getR360BackOfficeSettingsStagingURL();
		else if (envType.equals(EnvironmentType.INTEGRATION))
			settingsbofficeurl = VNextFreeRegistrationInfo.getInstance().getR360BackOfficeSettingsIntegrationURL();
		JSONDataProvider.dataFile = DATA_FILE;
	}
	
	/*@AfterSuite
	public void deleteClients() throws IOException {
		VNextWebServicesUtils.deleteClientsByMail("cnolan@gmail.com");
	}*/

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateNewCustomerWithEmptyFirstNameAndLastName(String rowID,
																   String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getCompany(), "");

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomerByCompanyName(testcustomer.getCompany());
		VNextCustomersMenuScreen customersMenuScreen = new VNextCustomersMenuScreen(DriverBuilder.getInstance().getAppiumDriver());
		newcustomerscreen = customersMenuScreen.clickEditCustomerMenuItem();
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newcustomerscreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newcustomerscreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newcustomerscreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), StringUtils.upperCaseAllFirst(testcustomer.getCustomerAddress1()));
		Assert.assertEquals(newcustomerscreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newcustomerscreen.getCustomerState(), testcustomer.getCustomerState());
		customersscreen = newcustomerscreen.clickBackButton();
		customersscreen.clickBackButton();
		BaseUtils.waitABit(45000);
WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(settingsbofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getUserVnextDevUserName(),
				VNextFreeRegistrationInfo.getInstance().getUserVnextDevUserPassword());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyCustomerCreatedInOfflineModeIsAvailableAfterDBUpdate(String rowID,
																			   String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.setNetworkOff();
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		AppiumUtils.setAndroidNetworkOn();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		BaseUtils.waitABit(1000);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB(employee);
		//homescreen = statusscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		newcustomerscreen = customersscreen.openCustomerForEdit(testcustomer);
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
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyNewCustomerIsAvailableAfterDBUpdate(String rowID,
															  String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());
		
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		BaseUtils.waitABit(30000);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB(employee);
		//homescreen = statusscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		newcustomerscreen = customersscreen.openCustomerForEdit(testcustomer);
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
	}
	
	private void deleteCustomerOnBackOffice(String firstName, String lastName) {
		
		String customerDelete = "";
		
		if (lastName.length() > 2)
			customerDelete = firstName + " " + lastName;
		else
			customerDelete = firstName;
		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(settingsbofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getUserVnextDevUserName(),
				VNextFreeRegistrationInfo.getInstance().getUserVnextDevUserPassword());
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

}
