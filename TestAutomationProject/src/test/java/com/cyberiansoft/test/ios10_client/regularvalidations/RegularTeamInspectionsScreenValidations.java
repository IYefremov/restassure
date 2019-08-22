package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInspectionsScreen;
import org.testng.Assert;

public class RegularTeamInspectionsScreenValidations {

    public static void verifyTeamInspectionExists(String inspectionID, boolean exsists) {
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        if (exsists)
            Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionID));
        else
            Assert.assertFalse(teamInspectionsScreen.isInspectionExists(inspectionID));
    }
}
