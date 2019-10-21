package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOHeaderPanelInteractions;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOHeaderPanelVerifications;

public class VNextBOHeaderPanelSteps {

    public static void logout() {
        try {
            if (VNextBOHeaderPanelVerifications.logOutLinkExists()) {
                new VNextBOHeaderPanelInteractions().userLogout();
            }
        } catch (RuntimeException ignored) {}
    }

    public static void clickUpgradeNowBanner() {
        new VNextBOHeaderPanelInteractions().clickUpgradeNowBanner();
    }
}
