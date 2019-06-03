package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextHelpingScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ConfirmationDialog;

public class GeneralSteps {
    public static void pressBackButton() {
        AppiumUtils.clickHardwareBackButton();
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

    public static void confirmDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        confirmationDialog.getConfirmDialogButton().click();
    }
}
