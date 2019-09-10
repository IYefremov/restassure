package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.StringUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextCustomersMenuScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamCustomersTestCases extends BaseTestCaseTeamEditionRegistration {

	@BeforeClass(description = "Team Customers Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getCustomersTestCasesDataPath();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateNewCustomerWithEmptyFirstNameAndLastName(String rowID,
																   String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getCompany(), "");

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToRetailMode();
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
		DriverBuilder.getInstance().setDriver(browsertype);
		WebDriver webdriver = DriverBuilder.getInstance().getDriver();
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
		loginpage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = new ClientsWebPage(webdriver);
		companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(testcustomer.getCompany());
		clientspage.waitABit(1000);
		clientspage.deleteClient(testcustomer.getCompany());
		Assert.assertFalse(clientspage.isClientPresentInTable(testcustomer.getCompany()));
		webdriver.quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCustomerCreatedInOfflineModeIsAvailableAfterDBUpdate(String rowID,
																			   String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.setNetworkOff();
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToRetailMode();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		AppiumUtils.setAndroidNetworkOn();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyNewCustomerIsAvailableAfterDBUpdate(String rowID,
															  String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToRetailMode();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
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
		DriverBuilder.getInstance().setDriver(browsertype);
		WebDriver webdriver = DriverBuilder.getInstance().getDriver();
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
		loginpage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = new ClientsWebPage(webdriver);
		companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		clientspage.searchClientByName(customerDelete);
		clientspage.waitABit(1000);
		while (clientspage.isClientPresentInTable(customerDelete))
			clientspage.deleteClient(customerDelete);
		Assert.assertFalse(clientspage.isClientPresentInTable(customerDelete));
		DriverBuilder.getInstance().getDriver().quit();
	}
}
