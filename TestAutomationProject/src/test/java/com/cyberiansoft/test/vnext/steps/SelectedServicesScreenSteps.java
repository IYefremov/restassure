package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import org.testng.Assert;

import java.util.List;

public class SelectedServicesScreenSteps {

    public static void unselectServices(List<ServiceData> serviceDataList) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        serviceDataList.forEach(service -> selectedServicesScreen.uselectService(service.getServiceName()));
    }

    public static void verifySelectedServices(List<ServiceData> expectedServiceList) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        expectedServiceList.forEach(serviceData -> Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName())));
    }


}
