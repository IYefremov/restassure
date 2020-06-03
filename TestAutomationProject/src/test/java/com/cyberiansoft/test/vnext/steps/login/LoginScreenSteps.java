package com.cyberiansoft.test.vnext.steps.login;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class LoginScreenSteps {

    public static void incorrectUserLogin(String employeeName, String userPassword) {

        VNextLoginScreen loginScreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        openLoginDialog(employeeName);
        ConditionWaiter.create(15000, 500, __ -> loginScreen.getLoginBtn().isEnabled()).execute();
        loginScreen.getLoginBtn().click();
        BaseUtils.waitABit(500);
    }

    public static void openLoginDialog(String employeeName) {

        VNextLoginScreen loginScreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        TopScreenPanelSteps.searchData(employeeName);
        loginScreen.getEmployeesNamesList().get(0).click();
    }

    public static void cancelLogin() {

        WaitUtils.click(new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()).getCancelBtn());
    }
}
