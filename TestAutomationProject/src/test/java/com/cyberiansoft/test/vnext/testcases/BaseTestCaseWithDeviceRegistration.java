package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTestCaseWithDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")
	@Parameters({ "user.name", "user.psw", "device.license", "selenium.browser" })	
	public void settingUp(String deviceofficeurl, String deviceuser, String devicepsw, String licensename, String defbrowser) throws Exception {
		defaultbrowser = defbrowser;
		setUp();	
		setNetworkOn();
		registerDevice(deviceuser, devicepsw, licensename);
		
	}

}
