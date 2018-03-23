package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeTest;

import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;

public class BaseTestCaseTeamEditionRegistration extends VNextBaseTestCase {
	
	final static String testEmployee = VNextTeamRegistrationInfo.getInstance().getDeviceEmployeeName();
	final static String testEmployeePsw = VNextTeamRegistrationInfo.getInstance().getDeviceEmployeePassword();

	@BeforeTest
	public void beforeTest() throws Exception {
		setUp();	
		VNextAppUtils.resetApp();
		registerTeamEdition(VNextTeamRegistrationInfo.getInstance().getDeviceDefaultLicenseName());
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.userLogin(testEmployee, testEmployeePsw);
		System.out.println("pppppppppppppppppppppppppppppp");
	}

}
