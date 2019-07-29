package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.interactions.AvailableServiceScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;

import java.util.List;

public class AvailableServicesScreenSteps {

    public static void selectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
        servicesScreen.selectServices(serviceDataList);
    }

    public static void openServiceDetails(String serviceName) {
        AvailableServiceScreenInteractions.switchToAvailableServicesView();
        AvailableServiceScreenInteractions.openServiceDetails(serviceName);

    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            VNextPriceMatrixesScreen priceMatrixesScreen = new VNextPriceMatrixesScreen() ;
            priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        }
    }
}
