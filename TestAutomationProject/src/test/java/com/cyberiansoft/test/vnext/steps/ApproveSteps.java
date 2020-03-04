package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;

public class  ApproveSteps {

    public static void drawSignature() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        approveScreen.drawSignature();
    }

    public static void saveApprove() {
        ListSelectPageInteractions.saveListPage();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.waitNotificationMessageDissapears();
    }

    public static void clickClearSignatureButton() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        approveScreen.clickClearSignatureButton();
    }
}
