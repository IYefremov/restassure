package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextHelpingScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class HelpingScreenInteractions {

    public static void dismissHelpingScreenIfPresent() {
        VNextHelpingScreen helpingScreen = new VNextHelpingScreen();
        BaseUtils.waitABit(3000);
        if (WaitUtils.isElementPresent(helpingScreen.getOkButtonLocator())) {
            helpingScreen.getOkDismissButton().click();
            WaitUtils.elementShouldBeVisible(helpingScreen.getOkDismissButton(), false);
            BaseUtils.waitABit(1000);
        }
    }
}
