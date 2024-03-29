package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.enums.partservice.PartServiceWizardScreen;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.PriceMatrixScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.services.MatrixServiceDetailsScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.services.MatrixServicePdrScreenInteractions;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;

public class MatrixServiceSteps {
    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        AvailableServicesScreenSteps.openServiceDetails(matrixServiceData.getMatrixServiceName());
        MatrixServiceSteps.selectServiceDetails(matrixServiceData);
    }

    public static void selectServiceDetails(MatrixServiceData matrixServiceData) {
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        if (matrixServiceData.getHailMatrixName() != null)
            MatrixServiceSteps.selectPriceMatrix(matrixServiceData.getHailMatrixName());
        if (vehiclePartData.getVehiclePartName() != null)
            MatrixServiceSteps.selectVehicle(matrixServiceData);
        if (vehiclePartData.getVehiclePartSize() != null)
            MatrixServiceSteps.selectSize(matrixServiceData);
        if (vehiclePartData.getVehiclePartSeverity() != null)
            MatrixServiceSteps.selectSeverity(matrixServiceData);
    }

    private static void selectPriceMatrix(String matrixName) {
        PriceMatrixScreenInteractions.selectItem(matrixName);
    }

    public static void selectVehicle(MatrixServiceData matrixServiceData) {
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        ListSelectPageInteractions.waitListPageReady(matrixServiceData.getMatrixServiceName());
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        ListSelectPageInteractions.selectItem(vehiclePartData.getVehiclePartName());
    }

    public static void selectSize(MatrixServiceData matrixServiceData) {
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        ListSelectPageInteractions.waitListPageReady(vehiclePartData.getVehiclePartName());
        MatrixServicePdrScreenInteractions.openSize();
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SIZE.getValue());
        MatrixServicePdrScreenInteractions.selectSize(vehiclePartData.getVehiclePartSize());
    }

    public static void selectSeverity(MatrixServiceData matrixServiceData) {
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        ListSelectPageInteractions.waitListPageReady(vehiclePartData.getVehiclePartName());
        MatrixServicePdrScreenInteractions.openSeverity();
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SEVERITY.getValue());
        MatrixServicePdrScreenInteractions.selectSeverity(vehiclePartData.getVehiclePartSeverity());
    }

    public static void selectPartServiceInsideMatrixService(PartServiceData vehiclePartData) {
        MatrixServiceSteps.openPartServiceDetailsInsideMatrixService(vehiclePartData);
        PartServiceSteps.acceptDetailsScreen();
        ServiceDetailsScreenSteps.saveServiceDetails();
    }

    public static void openPartServiceDetailsInsideMatrixService(PartServiceData partServiceData) {
        MatrixServiceDetailsScreenInteractions.selectService(partServiceData.getServiceName());
        PartServiceSteps.selectPartServiceDetails(partServiceData);
    }

    public static void openPartServiceDetails(PartServiceData partService) {
        MatrixServiceDetailsScreenInteractions.selectService(partService.getServiceName());
    }

    public static void switchToSelectedServices() {
        MatrixServiceDetailsScreenInteractions.switchToSelectedServices();
    }

    public static void acceptDetailsScreen() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void openServiceDetailsInsideMatrixService(String serviceName) {
        MatrixServiceDetailsScreenInteractions.selectService(serviceName);
    }

    public static void setMatrixServiceVehiclePartPrice(String priceValue) {
        MatrixServiceDetailsScreenInteractions.setPrice(priceValue);
    }
}
