package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;

import java.util.List;

public class SelectedServicesScreenSteps {

    public static void unSelectServices(List<ServiceData> serviceDataList) {
        serviceDataList.stream().map(ServiceData::getServiceName).forEach(SelectedServicesScreenSteps::unSelectService);
    }

    public static void unSelectService(String serviceName) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        SelectedServicesScreenSteps.switchToSelectedService();
        selectedServicesScreen.getServiceListItem(serviceName).clickDeleteService();
    }

    public static void openServiceDetails(String serviceName) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.getServiceListItem(serviceName).openServiceDetails();
    }

    public static void openLaborServiceDetails(String serviceName) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToSelectedServicesView();
        servicesScreen.openServiceDetails(serviceName);
    }

    public static void switchToSelectedService() {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToSelectedServicesView();
    }

    public static void openMatrixServiceVehiclePartDetails(VehiclePartData vehiclePartData) {
        ListSelectPageInteractions.selectItem(vehiclePartData.getVehiclePartName());
    }

    public static void changeSelectedServicePrice(String serviceName, String servicePrice) {
        openServiceDetails(serviceName);
        ServiceDetailsScreenSteps.changeServicePrice(servicePrice);
        ServiceDetailsScreenSteps.saveServiceDetails();
    }

    public static void changeSelectedServiceQuantity(String serviceName, String serviceQuantity) {
        openServiceDetails(serviceName);
        ServiceDetailsScreenSteps.changeServiceQuantity(serviceQuantity);
        ServiceDetailsScreenSteps.saveServiceDetails();
    }
}
