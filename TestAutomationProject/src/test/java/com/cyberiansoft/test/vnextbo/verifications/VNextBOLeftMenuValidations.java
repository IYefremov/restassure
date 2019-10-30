package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;

public class VNextBOLeftMenuValidations {

    public static boolean isMenuButtonDisplayed() {
        return Utils.isElementDisplayed(new VNexBOLeftMenuPanel().getMenuButton(), 10);
    }

    public static boolean isSettingsMenuTabDisplayed() {
        return Utils.isElementDisplayed(new VNexBOLeftMenuPanel().getSettingsMenuTab(), 10);
    }

    public static boolean isSettingsMenuTabAbsent() {
        return Utils.isElementNotDisplayed(new VNexBOLeftMenuPanel().getSettingsMenuTab(), 5);
    }
}
