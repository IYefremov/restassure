package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;

public class RegularWorkOrderSummaryScreenSteps {

    public static void waitWorkOrderSummaryScreenLoad() {
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
    }
}
