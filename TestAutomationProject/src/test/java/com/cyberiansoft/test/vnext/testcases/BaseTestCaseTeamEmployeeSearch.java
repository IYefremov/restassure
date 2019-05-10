package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import org.testng.annotations.BeforeClass;

public class BaseTestCaseTeamEmployeeSearch extends VNextBaseTestCase {

    @BeforeClass(description = "Setting up new suite")
    public void settingUp() {
        VNextAppUtils.resetApp();
        registerTeamEdition(VNextTeamRegistrationInfo.getInstance().getDeviceEmployeeSearchLicenseName());
        //registerDevice();
        //VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
        //loginscreen.userLogin(testEmployee, testEmployeePsw);
    }
}
