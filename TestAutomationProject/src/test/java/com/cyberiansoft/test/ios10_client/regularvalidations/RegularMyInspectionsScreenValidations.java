package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularMyInspectionsSteps;
import org.testng.Assert;

public class RegularMyInspectionsScreenValidations {

    public static void verifyInspectionTotalPrice(String inspectionID, String expectedTotalPrice) {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionID), expectedTotalPrice);
    }

    public static void verifyInspectionApprovedPrice(String inspectionID, String expectedApprovedPrice) {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspectionID), expectedApprovedPrice);
    }

    public static void verifyNotesIconPresentForInspection(String inspectionID, boolean isPresent) {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        if (isPresent)
            Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(inspectionID));
        else
            Assert.assertFalse(myInspectionsScreen.isNotesIconPresentForInspection(inspectionID));
    }

    public static void verifyApproveIconPresentForInspection(String inspectionID, boolean isPresent) {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        if (isPresent)
            Assert.assertFalse(myInspectionsScreen.isInspectionIsApproved(inspectionID));
        else
            Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspectionID));
    }

    public static void verifyInspectionPresent(String inspectionID, boolean isPresent) {
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        if (isPresent)
            Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspectionID));
        else
            Assert.assertFalse(myInspectionsScreen.checkInspectionExists(inspectionID));
    }
}
