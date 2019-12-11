package com.cyberiansoft.test.ios10_client.testcases.hd.deviceregistrationupdate;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.HomeScreenSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MainScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSDeviceRegistrationAndUpdateCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Device Registration And Update Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getDeviceRegistrationAndUpdateCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUpdateDatabase(String rowID,
                                   String description, JSONObject testData) {

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        HomeScreenSteps.navigateToStatusScreen();
        homeScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUpdateVIN(String rowID,
                              String description, JSONObject testData) {
        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateVIN();
        homeScreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        HomeScreenSteps.navigateToStatusScreen();
        homeScreen.updateVIN();
    }
}
