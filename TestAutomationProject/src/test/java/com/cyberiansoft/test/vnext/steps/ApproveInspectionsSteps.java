package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextDeclineReasonScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ApproveInspectionListElement;

public class ApproveInspectionsSteps {

    public static void declineInspection(String inspectionId, String declineReason) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        ApproveInspectionListElement inspection = approveInspectionsScreen.getInspectionElement(inspectionId);
        inspection.clickDeclineButton();
        selectDeclineReason(declineReason);
    }

    public static void selectDeclineReason(String declineReason) {
        VNextDeclineReasonScreen declineReasonScreen = new VNextDeclineReasonScreen();
        declineReasonScreen.selectDeclineReason(declineReason);
    }

    public static void deleteInspection(String inspectionId) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        ApproveInspectionListElement inspection = approveInspectionsScreen.getInspectionElement(inspectionId);
        inspection.clickDeleteButton();
    }

    public static void openApproveServicesScreen(String inspectionId) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        ApproveInspectionListElement inspection = approveInspectionsScreen.getInspectionElement(inspectionId);
        inspection.clickOnInspection();
    }

    public static void saveApprove() {
        ListSelectPageInteractions.saveListPage();
    }
}
