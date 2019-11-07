package com.cyberiansoft.test.vnextbo.validations.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;

public class VNextBOHeaderPanelValidations {

    public static boolean logOutLinkExists() {
        return Utils.isElementDisplayed(new VNextBOHeaderPanel().getLogoutLink());
    }
}
