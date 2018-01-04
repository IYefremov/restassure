package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;

public class BaseTestCaseWithDeviceRegistrationAndExistingUserLogin extends VNextBaseTestCase {
	
	final static String testEmployee = "Test User";
	final static String testEmployeePsw = "1111";

	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {

		setUp();	
		setNetworkOn();
		resetApp();
		registerDevice("osmak.oksana+408@gmail.com", "978385064");
		//registerDevice("Osmak.oksana+917@gmail.com", "9176361455");
		VNextHomeScreen homecreen = new VNextHomeScreen(appiumdriver);
	}

}
