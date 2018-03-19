package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;

public class BaseTestCaseTeamEmployeeSearch extends VNextBaseTestCase {
	
	final static String testEmployee = "Oksi Employee";
	final static String testEmployeePsw = "1111";

	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {

		setUp();	
		resetApp();
		registerTeamEdition("AutoTests");
		//registerDevice();
		//VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		//loginscreen.userLogin(testEmployee, testEmployeePsw);
	}

}
