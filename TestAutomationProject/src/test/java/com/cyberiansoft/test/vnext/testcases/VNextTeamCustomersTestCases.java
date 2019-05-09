package com.cyberiansoft.test.vnext.testcases;

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
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextCustomersMenuScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamCustomersTestCases extends BaseTestCaseTeamEditionRegistration {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/customers-testcases-data.json";


    @BeforeClass(description="Team Customers Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateNewCustomerWithEmptyFirstNameAndLastName(String rowID,
                                                                   String description, JSONObject testData) {

        final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

        deleteCustomerOnBackOffice(testcustomer.getCompany(), "");

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
        newcustomerscreen.createNewCustomer(testcustomer);
        customersscreen = new VNextCustomersScreen(appiumdriver);
        customersscreen.selectCustomerByCompanyName(testcustomer.getCompany());
        VNextCustomersMenuScreen customersMenuScreen = new VNextCustomersMenuScreen(appiumdriver);
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

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceofficeurl);
        BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
        loginpage.UserLogin(VNextConfigInfo.getInstance().getUserVnextDevUserName(), VNextConfigInfo.getInstance().getUserVnextDevUserPassword());
        BackOfficeHeaderPanel backofficeheader = new BackOfficeHeaderPanel(webdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        AppiumUtils.setNetworkOff();
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
        newcustomerscreen.createNewCustomer(testcustomer);
        AppiumUtils.setNetworkOn();
        customersscreen = new VNextCustomersScreen(appiumdriver);
        customersscreen.clickBackButton();
        homescreen = new VNextHomeScreen(appiumdriver);
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

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyNewCustomerIsAvailableAfterDBUpdate(String rowID,
                                                              String description, JSONObject testData) {

        final RetailCustomer testcustomer = JSonDataParser.getTestDataFromJson(testData, RetailCustomer.class);

        deleteCustomerOnBackOffice(testcustomer.getFirstName(), testcustomer.getLastName());

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
        newcustomerscreen.createNewCustomer(testcustomer);
        customersscreen = new VNextCustomersScreen(appiumdriver);
        customersscreen.clickBackButton();
        homescreen = new VNextHomeScreen(appiumdriver);
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

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceofficeurl);
        BackOfficeLoginWebPage loginpage = new BackOfficeLoginWebPage(webdriver);
        loginpage.UserLogin(VNextConfigInfo.getInstance().getUserVnextDevUserName(), VNextConfigInfo.getInstance().getUserVnextDevUserPassword());
        BackOfficeHeaderPanel backofficeheader = new BackOfficeHeaderPanel(webdriver);
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
