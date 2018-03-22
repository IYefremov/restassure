package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.BeforeClass;

import com.cyberiansoft.test.baseutils.AppiumAndroidUtils;
import com.cyberiansoft.test.vnext.config.VNextUserRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;

public class BaseTestCaseWithDeviceRegistrationAndExistingUserLogin extends VNextBaseTestCase {
	
	final static String testEmployee = VNextUserRegistrationInfo.getInstance().getDeviceEmployeeName();
	final static String testEmployeePsw = VNextUserRegistrationInfo.getInstance().getDeviceEmployeePassword();

	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {

		setUp();	
		AppiumAndroidUtils.setNetworkOn();
		resetApp();
		registerDevice(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail(), 
				VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneNumber());
		//registerDevice("Osmak.oksana+917@gmail.com", "9176361455");
		VNextHomeScreen homecreen = new VNextHomeScreen(appiumdriver);
	}

}
