package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.vnext.utils.AppContexts;

public class BaseTestCaseWithoutDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license", "selenium.browser"})	
	public void settingUp(String deviceofficeurl, String deviceuser, String devicepsw, String licensename, String defbrowser) {
		defaultbrowser = defbrowser;
		setUp();
		setNetworkOn();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@AfterClass(description = "Setting up new suite")
	public void tearDown() throws Exception {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.resetApp();	
	}

}
