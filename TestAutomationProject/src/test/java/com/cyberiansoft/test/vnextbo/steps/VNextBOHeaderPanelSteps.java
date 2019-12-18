package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOHeaderPanelInteractions;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOHeaderPanelValidations;

public class VNextBOHeaderPanelSteps {

    public static void logout() {
        try {
            if (VNextBOHeaderPanelValidations.logOutLinkExists()) {
                VNextBOHeaderPanelInteractions.userLogout();
            }
        } catch (RuntimeException ignored) {}
    }

    public static void clickUpgradeNowBanner() {
        new VNextBOHeaderPanelInteractions().clickUpgradeNowBanner();
    }
}
