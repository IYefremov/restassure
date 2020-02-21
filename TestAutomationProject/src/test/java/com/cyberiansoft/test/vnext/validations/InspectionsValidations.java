package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.InspectionStatus;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import org.testng.Assert;

public class InspectionsValidations {

    public static void verifyInspectionStatus(String inspectionID, InspectionStatus inspectionStatus) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionID), inspectionStatus.getStatus());
    }

    public static void verifyInspectionHasMailIcon(String inspectionID, boolean hasIcon) {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        if (hasIcon)
            Assert.assertTrue(inspectionsScreen.isEmailSentIconPresentForInspection(inspectionID));
        else
            Assert.assertFalse(inspectionsScreen.isEmailSentIconPresentForInspection(inspectionID));
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
}
