package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class InspectionMenuSteps {
    public static void selectCreateWorkOrder() {
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);
    }

    public static void approveInspection() {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        WaitUtils.elementShouldBeVisible(approveScreen.getApprocescreen(), true);
        approveScreen.drawSignature();
        approveScreen.clickSaveButton();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.waitNotificationMessageDissapears();
    }
}
