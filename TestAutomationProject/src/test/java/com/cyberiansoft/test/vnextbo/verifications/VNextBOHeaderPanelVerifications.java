package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;

public class VNextBOHeaderPanelVerifications {

    public static boolean logOutLinkExists() {
        return Utils.isElementDisplayed(new VNextBOHeaderPanel().getLogoutLink());
    }
}
