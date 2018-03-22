package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.baseutils.AppiumAndroidUtils;
import com.cyberiansoft.test.vnext.utils.AppContexts;

public class BaseTestCaseWithoutDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")
	@Parameters({ "user.name", "user.psw", "device.license", "selenium.browser"})	
	public void settingUp(String deviceuser, String devicepsw, String licensename, String defbrowser) {
		setUp();
		AppiumAndroidUtils.setNetworkOn();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@AfterClass(description = "Setting up new suite")
	public void tearDown() throws Exception {
		AppiumAndroidUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.resetApp();	
	}

}
