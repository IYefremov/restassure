package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;

public class RegularApproveInspectionScreenActions {

    public static void clickApproveAllServicesButton() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickApproveAllServicesButton();
    }

    public static void saveApprovedServices() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickSaveButton();
    }

    public static void clickSingnAndDrawSignature() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }
}
