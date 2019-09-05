package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;

public class RegularSelectedServicesSteps {

    public static void waitSelectedServicesScreenLoaded() {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
    }

    public static void switchToAvailableServices() {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.switchToAvailableServicesTab();
    }
}
