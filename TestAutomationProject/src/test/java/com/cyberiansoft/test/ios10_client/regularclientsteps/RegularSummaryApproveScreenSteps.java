package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSummaryApproveScreen;

public class RegularSummaryApproveScreenSteps {

    public static void approveWorkOrder() {
        RegularSummaryApproveScreen approveScreen = new RegularSummaryApproveScreen();
        approveScreen.clickApproveButton();
    }
}
