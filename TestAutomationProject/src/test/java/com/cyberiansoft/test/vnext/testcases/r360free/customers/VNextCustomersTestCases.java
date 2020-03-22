package com.cyberiansoft.test.vnext.testcases.r360free.customers;


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
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.steps.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.StatusScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class VNextCustomersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private static String settingsbofficeurl;

	@BeforeClass(description = "R360 Customers Test Cases")
	public void beforeClass() {

		if (envType.equals(EnvironmentType.DEVELOPMENT))
			settingsbofficeurl = VNextFreeRegistrationInfo.getInstance().getR360BackOfficeSettingsStagingURL();
		else if (envType.equals(EnvironmentType.INTEGRATION))
			settingsbofficeurl = VNextFreeRegistrationInfo.getInstance().getR360BackOfficeSettingsIntegrationURL();
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getCustomersTestCasesDataPath();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateNewCustomerWithEmptyFirstNameAndLastName(String rowID,
																   String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getCompany(), "");

        HomeScreenSteps.openCustomers();
		CustomersScreenSteps.clickAddCustomerButton();
		VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
		newCustomerScreen.createNewCustomer(testcustomer);
		CustomersScreenSteps.selectCustomer(testcustomer);
        CustomersScreenSteps.openCustomerForEdit(testcustomer);
		Assert.assertEquals(newCustomerScreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newCustomerScreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newCustomerScreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newCustomerScreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newCustomerScreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newCustomerScreen.getCustomerAddress(), StringUtils.upperCaseAllFirst(testcustomer.getCustomerAddress1()));
		Assert.assertEquals(newCustomerScreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newCustomerScreen.getCustomerState(), testcustomer.getCustomerState());
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
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

		AppiumUtils.setNetworkOff();
		HomeScreenSteps.openCustomers();
		CustomersScreenSteps.clickAddCustomerButton();
		VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
		newCustomerScreen.createNewCustomer(testcustomer);
		AppiumUtils.setAndroidNetworkOn();
		ScreenNavigationSteps.pressBackButton();
		BaseUtils.waitABit(1000);
		StatusScreenSteps.updateMainDB();
		//homeScreen = statusscreen.clickBackButton();
		HomeScreenSteps.openCustomers();
		CustomersScreenSteps.openCustomerForEdit(testcustomer);
		Assert.assertEquals(newCustomerScreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newCustomerScreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newCustomerScreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newCustomerScreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newCustomerScreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newCustomerScreen.getCustomerAddress(), testcustomer.getCustomerAddress1());
		Assert.assertEquals(newCustomerScreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newCustomerScreen.getCustomerState(), testcustomer.getCustomerState());
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyNewCustomerIsAvailableAfterDBUpdate(String rowID,
															  String description, JSONObject testData) {

		final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

		deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());

		HomeScreenSteps.openCustomers();
		CustomersScreenSteps.clickAddCustomerButton();
		VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
		newCustomerScreen.createNewCustomer(testcustomer);
		ScreenNavigationSteps.pressBackButton();
		BaseUtils.waitABit(30000);
		StatusScreenSteps.updateMainDB();
		HomeScreenSteps.openCustomers();
		CustomersScreenSteps.openCustomerForEdit(testcustomer);
		Assert.assertEquals(newCustomerScreen.getCustomerFirstName(), testcustomer.getFirstName());
		Assert.assertEquals(newCustomerScreen.getCustomerLastName(), testcustomer.getLastName());
		Assert.assertEquals(newCustomerScreen.getCustomerCompanyName(), testcustomer.getCompany());
		Assert.assertEquals(newCustomerScreen.getCustomerEmail(), testcustomer.getMailAddress());
		Assert.assertEquals(newCustomerScreen.getCustomerPhone(), testcustomer.getCustomerPhone());
		Assert.assertEquals(newCustomerScreen.getCustomerAddress(), testcustomer.getCustomerAddress1());
		Assert.assertEquals(newCustomerScreen.getCustomerCountry(), testcustomer.getCustomerCountry());
		Assert.assertEquals(newCustomerScreen.getCustomerState(), testcustomer.getCustomerState());
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
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
		webdriver.quit();
	}

}
