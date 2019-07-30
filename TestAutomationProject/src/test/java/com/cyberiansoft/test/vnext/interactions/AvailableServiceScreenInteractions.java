package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;

public class AvailableServiceScreenInteractions {
    public static void openServiceDetails(String serviceName) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.openServiceDetails(serviceName);
    }

    public static void switchToAvailableServicesView() {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
    }
}
