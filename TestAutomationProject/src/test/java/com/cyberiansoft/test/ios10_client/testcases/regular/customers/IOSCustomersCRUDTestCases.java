package com.cyberiansoft.test.ios10_client.testcases.regular.customers;

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
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularAddCustomerScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularHomeScreenSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularMainScreenSteps;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCustomersCRUDTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Customers CRUD Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCustomersCRUDTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateRetailCustomer(String rowID,
                                         String description, JSONObject testData) {

        RetailCustomer newCustomer = new RetailCustomer("supermy12", "super");
        newCustomer.setCompanyName("supercompany");
        newCustomer.setCustomerAddress1( "First streer");
        newCustomer.setCustomerCity("New York");
        newCustomer.setCustomerZip("79031");
        newCustomer.setCustomerPhone("723-1234567");
        newCustomer.setMailAddress("test@cyberiansoft.com");
        newCustomer.setCustomerState("Alberta");
        newCustomer.setCustomerCountry("Canada");

        RetailCustomer editedCustomer = new RetailCustomer("supernewmy12", "superedited");
        editedCustomer.setCompanyName("supercompanyedited");
        editedCustomer.setCustomerAddress1( "Second streer");
        editedCustomer.setCustomerCity("New York1");
        editedCustomer.setCustomerZip("79035");
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

        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.swtchToRetailMode();
        customersScreen.clickAddCustomersButton();
        RegularAddCustomerScreen addcustomerscreen = new RegularAddCustomerScreen();
        addcustomerscreen.addCustomer(newCustomer);
        addcustomerscreen.clickSaveBtn();
        Assert.assertTrue(customersScreen.checkCustomerExists(newCustomer.getFirstName()));

        customersScreen.selectCustomerToEdit(newCustomer.getFirstName());
        addcustomerscreen.editCustomer(editedCustomer);
        addcustomerscreen.clickSaveBtn();
        Assert.assertTrue(customersScreen.checkCustomerExists(editedCustomer.getFirstName()));
        customersScreen.clickCancel();
        customersScreen.clickHomeButton();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

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

        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToCustomersScreen();
        customersScreen.swtchToRetailMode();
        Assert.assertFalse(customersScreen.checkCustomerExists(editedCustomer.getFirstName()));
        customersScreen.clickHomeButton();
    }
}
