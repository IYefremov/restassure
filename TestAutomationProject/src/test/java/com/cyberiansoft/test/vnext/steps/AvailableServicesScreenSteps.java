package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.interactions.AvailableServiceScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;

import java.util.List;
import java.util.stream.Collectors;

public class AvailableServicesScreenSteps {

    public static void selectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
        serviceDataList.stream().map(ServiceData::getServiceName).collect(Collectors.toList()).forEach(AvailableServicesScreenSteps::selectService);
    }

    public static void selectService(String serviceName) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
        SearchSteps.textSearch(serviceName);
        servicesScreen.selectService(serviceName);
    }

    public static void openServiceDetails(String serviceName) {
        AvailableServiceScreenInteractions.switchToAvailableServicesView();
        AvailableServiceScreenInteractions.openServiceDetails(serviceName);
    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            VNextPriceMatrixesScreen priceMatrixesScreen = new VNextPriceMatrixesScreen();
            priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        }
    }

    public static void openServiceDetails(ServiceData serviceData) {
        AvailableServicesScreenSteps.openServiceDetails(serviceData.getServiceName());
    }
}
