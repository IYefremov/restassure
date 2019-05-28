package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextHelpingScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class GeneralSteps {
    public static void pressBackButton() {
        VNextBaseScreen vNextBaseScreen = new VNextBaseScreen();
        vNextBaseScreen.clickScreenBackButton();
    }

    public static void dismissHelpingScreenIfPresent() {
        VNextHelpingScreen vNextHelpingScreen = new VNextHelpingScreen();
        BaseUtils.waitABit(1000);
        if (vNextHelpingScreen.getOkDismissButton().isDisplayed()) {
            vNextHelpingScreen.getOkDismissButton().click();
            WaitUtils.elementShouldBeVisible(vNextHelpingScreen.getOkDismissButton(), false);
            BaseUtils.waitABit(2000);
        }
    }
}
