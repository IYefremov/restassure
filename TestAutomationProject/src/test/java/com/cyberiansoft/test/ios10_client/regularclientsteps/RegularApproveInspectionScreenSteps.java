package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;

public class RegularApproveInspectionScreenSteps {

    public static void clickApproveAllServicesButton() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickApproveAllServicesButton();
    }

    public static void clickDeclineAllServicesButton() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickDeclineAllServicesButton();
    }

    public static void saveApprovedServices() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickSaveButton();
    }

    public static void clickSingnAndDrawSignature() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
    }

    public static void selectInspectionForApprove(String inspectionID) {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionID);
    }

    public static void clickDeclinePopupButton() {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickDeclinePopupButton();
    }

    public static void selectApproveInspectionServiceStatus(ServiceData serviceData) {
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
    }
}
