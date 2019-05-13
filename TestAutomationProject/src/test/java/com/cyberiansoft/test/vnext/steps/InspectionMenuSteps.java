package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class InspectionMenuSteps {
    public static void selectCreateWorkOrder() {
        VNextInspectionsMenuScreen inspectionMenuList = new VNextInspectionsMenuScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionMenuList.clickCreateWorkOrderInspectionMenuItem();
    }

    public static void approveInspection() {
        VNextInspectionsMenuScreen inspectionMenuList = new VNextInspectionsMenuScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionMenuList.clickApproveInspectionMenuItem();
        VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approveScreen.drawSignature();
        approveScreen.clickSaveButton();
    }

    public static void createWorkOrderMenuItemShouldBeVisible(Boolean shouldBeVisible) {
        VNextInspectionsMenuScreen inspectionMenuList = new VNextInspectionsMenuScreen(DriverBuilder.getInstance().getAppiumDriver());
        WaitUtils.elementShouldBeVisible(inspectionMenuList.getCreatewoinspectionbtn(), shouldBeVisible);
    }
}
