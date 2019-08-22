package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import org.testng.Assert;

public class RegularTeamWorkOrdersScreenValidations {

    public static void verifyTeamInspectionExists(String workOrderID, boolean exsists) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        if (exsists)
            Assert.assertTrue(teamWorkOrdersScreen.isWorkOrderExists(workOrderID));
        else
            Assert.assertFalse(teamWorkOrdersScreen.isWorkOrderExists(workOrderID));
    }
}
