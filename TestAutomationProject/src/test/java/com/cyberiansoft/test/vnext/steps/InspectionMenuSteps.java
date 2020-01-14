package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InspectionMenuSteps {
    public static void selectCreateWorkOrder() {
        VNextInspectionsMenuScreen inspectionMenuList = new VNextInspectionsMenuScreen();
        inspectionMenuList.clickCreateWorkOrderInspectionMenuItem();
    }

    public static void approveInspection() {
        VNextInspectionsMenuScreen inspectionMenuList = new VNextInspectionsMenuScreen();
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        inspectionMenuList.clickApproveInspectionMenuItem();
        WaitUtils.elementShouldBeVisible(approveScreen.getApprocescreen(), true);
        approveScreen.drawSignature();
        approveScreen.clickSaveButton();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.waitNotificationMessageDissapears();
    }

    public static void createWorkOrderMenuItemShouldBeVisible(Boolean shouldBeVisible) {
        VNextInspectionsMenuScreen inspectionMenuList = new VNextInspectionsMenuScreen();
        WaitUtils.elementShouldBeVisible(inspectionMenuList.getCreatewoinspectionbtn(), shouldBeVisible);
    }
}
