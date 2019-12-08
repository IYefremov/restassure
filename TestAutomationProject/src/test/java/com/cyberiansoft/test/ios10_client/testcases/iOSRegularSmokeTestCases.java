package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;

import com.cyberiansoft.test.ios10_client.utils.*;
import org.testng.annotations.BeforeClass;


public class iOSRegularSmokeTestCases extends ReconProBaseTestCase {


	@BeforeClass
	public void setUpSuite() {
		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getGeneralSuiteTestCasesDataPath();
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Test_Automation_Regular3",
				envType);

		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToSettingsScreen();
		RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
		settingsScreen.setShowAvailableSelectedServicesOn();
		settingsScreen.clickHomeButton();
	}


}