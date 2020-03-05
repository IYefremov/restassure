package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextApproveInspectionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ApproveInspectionListElement;
import org.testng.Assert;

public class ApproveInspectionsScreenValidations {

    public static void validateInspectionApprovedAmount(String inspectionId, String expectedAmount) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        ApproveInspectionListElement inspection = approveInspectionsScreen.getInspectionElement(inspectionId);
        Assert.assertEquals(inspection.getInspectionApprovedAmount(), expectedAmount);
    }

    public static void validateApproveIconForInspectionSelected(String inspectionId, boolean isSelected) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        ApproveInspectionListElement inspection = approveInspectionsScreen.getInspectionElement(inspectionId);
        if (isSelected)
            Assert.assertTrue(inspection.isApproveIconForInspectionSelected());
        else
            Assert.assertFalse(inspection.isApproveIconForInspectionSelected());
    }

    public static void validateInspectionServicesStatusesCanBeChanged(String inspectionId, boolean canBeChanged) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        ApproveInspectionListElement inspection = approveInspectionsScreen.getInspectionElement(inspectionId);
        if (canBeChanged)
            Assert.assertTrue(inspection.isInspectionServicesStatusesCanBeChanged());
        else
            Assert.assertFalse(inspection.isInspectionServicesStatusesCanBeChanged());
    }

    public static void validateInspectionExists(String inspectionId, boolean isExists) {
        VNextApproveInspectionsScreen approveInspectionsScreen = new VNextApproveInspectionsScreen();
        WaitUtils.waitUntilElementIsClickable(approveInspectionsScreen.getRootElement());
        WaitUtils.getGeneralFluentWait().until((webdriver) -> approveInspectionsScreen.getApproveInspectionsList().size() > 0);
        if (isExists)
            Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionId));
        else
            Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionId));
    }
}
