package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class  ApproveSteps {

    public static void drawSignature() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        approveScreen.drawSignature();
    }

    public static void saveApprove() {
        ListSelectPageInteractions.saveListPage();
        WaitUtils.waitLoadDialogDisappears();
    }

    public static void clickClearSignatureButton() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        approveScreen.clickClearSignatureButton();
    }
}
