package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.vnext.utils.VNextAppUtils;

public class BaseTestCaseWithDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")	
	public void settingUp() throws Exception {
		setUp();	
		VNextAppUtils.resetApp();
		registerDevice();		
	}

}
