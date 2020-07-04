package com.cyberiansoft.test.vnextbo.interactions.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;

public class VNextBOHeaderPanelInteractions {

    public static void clickLogout() {
        Utils.clickElement(new VNextBOHeaderPanel().getLogoutLink());
    }

    public static void userLogout() {
        clickLogout();
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickUpgradeNowBanner() {
        Utils.clickElement(new VNextBOHeaderPanel().getUpgradeNowButton());
    }
}
