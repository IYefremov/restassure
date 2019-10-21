package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOHeaderPanelInteractions;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOHeaderPanelVerifications;

public class VNextBOHeaderPanelSteps {

    private VNextBOHeaderPanelInteractions headerPanelInteractions;

    public VNextBOHeaderPanelSteps() {
        headerPanelInteractions = new VNextBOHeaderPanelInteractions();
    }

    public void logout() {
        try {
            if (VNextBOHeaderPanelVerifications.logOutLinkExists()) {
                headerPanelInteractions.userLogout();
            }
        } catch (RuntimeException ignored) {}
    }

    public void clickUpgradeNowBanner() {
        headerPanelInteractions.clickUpgradeNowBanner();
    }
}
