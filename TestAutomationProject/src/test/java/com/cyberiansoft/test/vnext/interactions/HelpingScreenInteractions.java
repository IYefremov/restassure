package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextHelpingScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class HelpingScreenInteractions {

    public static void dismissHelpingScreenIfPresent() {
        VNextHelpingScreen helpingScreen = new VNextHelpingScreen();
        if (WaitUtils.isElementPresent(helpingScreen.getOkButtonLocator())) {
            WaitUtils.click(helpingScreen.getOkDismissButton());
        }
    }
}
