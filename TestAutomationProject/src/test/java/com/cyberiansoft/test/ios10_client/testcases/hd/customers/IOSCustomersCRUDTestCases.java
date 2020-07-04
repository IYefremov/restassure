package com.cyberiansoft.test.ios10_client.testcases.hd.customers;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.AddCustomerScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCustomersCRUDTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Customers CRUD Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCustomersCRUDTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateRetailCustomer(String rowID,
                                         String description, JSONObject testData) {

        RetailCustomer newCustomer = new RetailCustomer("supermy12", "super");
        newCustomer.setCompanyName("supercompany");
        newCustomer.setCustomerAddress1("470 Copper Drive");
        newCustomer.setCustomerCity("New Port");
        newCustomer.setCustomerZip("19804");
        newCustomer.setCustomerPhone("723-1234567");
        newCustomer.setMailAddress("test@cyberiansoft.com");
        newCustomer.setCustomerState("Delaware");
        newCustomer.setCustomerCountry("United States");

        RetailCustomer editedCustomer = new RetailCustomer("supernewmy12", "superedited");
        editedCustomer.setCompanyName("supercompanyedited");
        editedCustomer.setCustomerAddress1("600 Markley Street");
        editedCustomer.setCustomerCity("Port Reading");
        editedCustomer.setCustomerZip("07064");
        editedCustomer.setCustomerPhone("723-1234576");
        editedCustomer.setMailAddress("test@getnada.com");
        editedCustomer.setCustomerState("California");
        editedCustomer.setCustomerCountry("United States");

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BaseUtils.waitABit(1000);
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        ClientsWebPage clientsWebPage = new ClientsWebPage(webdriver);
        companyWebPage.clickClientsLink();
        clientsWebPage.searchClientByName(newCustomer.getFullName());
        if (clientsWebPage.isClientPresentInTable(newCustomer.getFullName()))
            clientsWebPage.archiveFirstClient();

        clientsWebPage.searchClientByName(editedCustomer.getFullName());
        if (clientsWebPage.isClientPresentInTable(editedCustomer.getFullName()))
            clientsWebPage.archiveFirstClient();

        DriverBuilder.getInstance().getDriver().quit();

        HomeScreen homeScreen = new HomeScreen();
        homeScreen.clickLogoutButton();
        MainScreen mainScreen = new MainScreen();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        AddCustomerScreen addCustomerScreen = customersScreen.clickAddCustomersButton();

        addCustomerScreen.addCustomer(newCustomer);
        addCustomerScreen.clickSaveBtn();
        Assert.assertTrue(customersScreen.isCustomerExists(newCustomer.getFirstName()));
        customersScreen.selectCustomerToEdit(newCustomer);

        addCustomerScreen.editCustomer(editedCustomer);
        addCustomerScreen.clickSaveBtn();
        Assert.assertTrue(customersScreen.isCustomerExists(editedCustomer.getFirstName()));
        customersScreen.clickHomeButton();
        homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BaseUtils.waitABit(1000);
        backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        companyWebPage = new CompanyWebPage(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        clientsWebPage = new ClientsWebPage(webdriver);
        companyWebPage.clickClientsLink();

        clientsWebPage.deleteUserViaSearch(editedCustomer.getFullName());

        DriverBuilder.getInstance().getDriver().quit();

        homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        Assert.assertFalse(customersScreen.isCustomerExists(editedCustomer.getFirstName()));
        customersScreen.clickHomeButton();
    }
}
