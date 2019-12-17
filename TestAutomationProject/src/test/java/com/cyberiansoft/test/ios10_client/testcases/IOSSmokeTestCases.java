package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class IOSSmokeTestCases extends ReconProBaseTestCase {

    //public HomeScreen homeScreen;
    private WholesailCustomer Specific_Client = new WholesailCustomer();
    private WholesailCustomer ZAZ_Motors = new WholesailCustomer();
    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();
    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();
    private WholesailCustomer _004_Test_Customer = new WholesailCustomer();
    private WholesailCustomer Test_Company_Customer = new WholesailCustomer();
    private RetailCustomer testRetailCustomer = new RetailCustomer("Retail", "Customer");
    private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "Simple_PO#_Req");

    @BeforeClass
    public void setUpSuite() {
        Specific_Client.setCompanyName("Specific_Client");
        ZAZ_Motors.setCompanyName("Zaz Motors");
        _002_Test_Customer.setCompanyName("002 - Test Company");
        _003_Test_Customer.setCompanyName("003 - Test Company");
        _004_Test_Customer.setCompanyName("004 - Test Company");
        Test_Company_Customer.setCompanyName("Test Company");
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getGeneralSuiteTestCasesDataPath();
        mobilePlatform = MobilePlatform.IOS_HD;
        initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Ios_automation",
                envType);
        MainScreen mainScreen = new MainScreen();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.setInsvoicesCustomLayoutOff();
        settingsScreen.clickHomeButton();
    }

    @AfterMethod
    public void closeBrowser() {
        if (webdriver != null)
            webdriver.quit();
    }

}