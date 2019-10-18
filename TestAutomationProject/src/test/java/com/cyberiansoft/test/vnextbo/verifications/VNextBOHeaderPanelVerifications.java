package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;

public class VNextBOHeaderPanelVerifications {

    private VNextBOHeaderPanel headerPanel;

    public VNextBOHeaderPanelVerifications() {
        headerPanel = new VNextBOHeaderPanel();
    }

    public boolean logOutLinkExists() {
        return Utils.isElementDisplayed(headerPanel.getLogoutLink());
    }
}
