package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
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

    public static void openSelectedServiceDetails(String serviceName) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.openSelectedServiceDetails(serviceName);
    }

    public static void openSelectedServiceDetailsViaCustomButton(String serviceName) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.clickCustomServiceDetailsButton(serviceName);
    }

    public static void deleteSelectedService(String serviceName) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.openSelectedServiceDetails(serviceName);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.removeService();
    }
}
