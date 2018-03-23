package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.vnext.utils.AppContexts;

public class BaseTestCaseWithoutDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")
	@Parameters({ "user.name", "user.psw", "device.license", "selenium.browser"})	
	public void settingUp(String deviceuser, String devicepsw, String licensename, String defbrowser) {
		setUp();
		AppiumUtils.setNetworkOn();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@AfterClass(description = "Setting up new suite")
	public void tearDown() throws Exception {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.resetApp();	
	}

}
