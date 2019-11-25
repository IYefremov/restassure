package com.cyberiansoft.test.vnextbo.interactions.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;

public class VNextBOHeaderPanelInteractions {

    private VNextBOHeaderPanel headerPanel;

    public VNextBOHeaderPanelInteractions() {
        headerPanel = new VNextBOHeaderPanel();
    }

    public void clickLogout() {
        Utils.clickElement(headerPanel.getLogoutLink());
    }

    public void userLogout() {
        clickLogout();
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickUpgradeNowBanner() {
        Utils.clickElement(headerPanel.getUpgradeNowButton());
    }
}
