package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.baseutils.AppiumAndroidUtils;

public class BaseTestCaseWithDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")	
	public void settingUp() throws Exception {
		setUp();	
		AppiumAndroidUtils.setNetworkOn();
		registerDevice();		
	}

}
