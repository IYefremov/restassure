package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;

public class BaseTestCaseWithDeviceRegistrationAndUserLogin extends VNextBaseTestCase {
	
	final static String testEmployee = "Test User";
	final static String testEmployeePsw = "1111";

	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {

		setUp();	
		//setNetworkOn();
		resetApp();
		registerDevice();
		VNextHomeScreen homecreen = new VNextHomeScreen(appiumdriver);
	}
}
