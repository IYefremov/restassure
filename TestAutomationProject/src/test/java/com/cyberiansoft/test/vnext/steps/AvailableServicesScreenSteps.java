package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;

import java.util.List;

public class AvailableServicesScreenSteps {

    public static void selectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
        servicesScreen.selectServices(serviceDataList);
    }
}
