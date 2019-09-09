package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInspectionsScreen;

public class RegularTeamInspectionsSteps {

    public static void selectInspectionForApprovaViaAction(String inspectionID) {
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        teamInspectionsScreen.selectInspectionForApprove(inspectionID);
    }
}
