package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;

public class RegularServicesScreenSteps {

    public static void selectServiceWithServiceData(ServiceData serviceData) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
        RegularServiceDetailsScreenSteps.serServiceDetailsData(serviceData);
    }

    public static void selectService(String serviceName) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(serviceName);
    }
}
