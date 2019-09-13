package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;

import java.util.List;

public class SelectedServicesScreenSteps {

    public static void unselectServices(List<ServiceData> serviceDataList) {
        serviceDataList.stream().map(ServiceData::getServiceName).forEach(SelectedServicesScreenSteps::unselectService);
    }

    public static void unselectService(String serviceName) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.uselectService(serviceName);
    }

    public static void openServiceDetails(String serviceName) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToSelectedServicesView();
        servicesScreen.openServiceDetails(serviceName);
    }

    public static void switchToSelectedService() {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToSelectedServicesView();
    }
}
