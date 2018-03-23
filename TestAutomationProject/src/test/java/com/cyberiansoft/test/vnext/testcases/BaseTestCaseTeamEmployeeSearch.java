package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;

public class BaseTestCaseTeamEmployeeSearch extends VNextBaseTestCase {
	
	final static String testEmployee = VNextTeamRegistrationInfo.getInstance().getDeviceEmployeeName();
	final static String testEmployeePsw = VNextTeamRegistrationInfo.getInstance().getDeviceEmployeePassword();

	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {

		setUp();	
		VNextAppUtils.resetApp();
		registerTeamEdition(VNextTeamRegistrationInfo.getInstance().getDeviceEmployeeSearchLicenseName());
		//registerDevice();
		//VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		//loginscreen.userLogin(testEmployee, testEmployeePsw);
	}

}
