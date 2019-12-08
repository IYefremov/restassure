package com.cyberiansoft.test.ios10_client.testcases.regular.deviceregistrationupdate;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSDeviceRegistrationAndUpdateCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Device Registration And Update Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getDeviceRegistrationAndUpdateCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUpdateDatabase(String rowID,
                                   String description, JSONObject testData) {
        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickStatusButton();
        homeScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUpdateVIN(String rowID,
                              String description, JSONObject testData) {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateVIN();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickStatusButton();
        homeScreen.updateVIN();
        homeScreen.clickHomeButton();
    }
}
