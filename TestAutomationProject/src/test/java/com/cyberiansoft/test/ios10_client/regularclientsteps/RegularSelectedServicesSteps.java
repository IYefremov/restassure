package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;

public class RegularSelectedServicesSteps {

    public static void waitSelectedServicesScreenLoaded() {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
    }
}
