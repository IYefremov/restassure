package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTestCaseWithDeviceRegistration extends VNextBaseTestCase {
	
	@BeforeClass(description = "Setting up new suite")	
	public void settingUp() throws Exception {
		setUp();	
		setNetworkOn();
		registerDevice();		
	}

}
