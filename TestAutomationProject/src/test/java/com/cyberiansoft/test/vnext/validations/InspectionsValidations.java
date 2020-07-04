package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

public class InspectionsValidations {

    public static void verifyInspectionStatus(String inspectionID, InspectionStatus inspectionStatus) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionID), inspectionStatus.getStatusString());
    }

    public static void verifyInspectionHasMailIcon(String inspectionID, boolean hasIcon) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        if (hasIcon)
            Assert.assertTrue(inspectionsScreen.isEmailSentIconPresentForInspection(inspectionID));
        else
            Assert.assertFalse(inspectionsScreen.isEmailSentIconPresentForInspection(inspectionID));
    }

    public static void verifyInspectionHasNotesIcon(String inspectionID, boolean hasIcon) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        if (hasIcon)
            Assert.assertTrue(inspectionsScreen.isNotesIconPresentForInspection(inspectionID));
        else
            Assert.assertFalse(inspectionsScreen.isNotesIconPresentForInspection(inspectionID));
    }

    public static void verifyInspectionExists(String inspectionID, boolean exists) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        if (exists)
            Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionID));
        else
            Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionID));
    }

    public static void verifyInspectionTotalPrice(String inspectionID, String expectedPrice) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionID), expectedPrice);
    }

    public static void verifyInspectionApprovedPrice(String inspectionID, String expectedPrice) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionApprovedPriceValue(inspectionID), expectedPrice);
    }

    public static void verifyInspectionCustomer(String inspectionID, AppCustomer expectedCustomer) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionID), expectedCustomer.getFullName());
    }

    public static void verifyTeamTabActive(boolean isActive) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        WaitUtils.elementShouldBeVisible(inspectionsScreen.getRootElement(),true);
        if (isActive)
            Assert.assertTrue(inspectionsScreen.isTeamViewActive());
        else
            Assert.assertFalse(inspectionsScreen.isTeamViewActive());
    }
}
