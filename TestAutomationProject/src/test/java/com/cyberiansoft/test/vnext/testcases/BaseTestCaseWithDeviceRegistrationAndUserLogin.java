package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;

public class BaseTestCaseWithDeviceRegistrationAndUserLogin extends VNextBaseTestCase {
	
	final static String testEmployee = "VD_Employee VD_Employee";
	final static String testEmployeePsw = "1111";

	@BeforeClass(description = "Setting up new suite")
	@Parameters({ "user.name", "user.psw", "device.license", "selenium.browser" })	
	public void settingUp(String deviceuser, String devicepsw, String licensename, String defbrowser) throws Exception {
		defaultbrowser = defbrowser;
		
		setUp();	
		setNetworkOn();
		resetApp();
		registerDevice(deviceuser, devicepsw, licensename);
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.userLogin(testEmployee, testEmployeePsw);
	}
}
