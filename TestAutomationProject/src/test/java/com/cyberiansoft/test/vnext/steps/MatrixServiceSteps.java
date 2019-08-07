package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.partservice.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.enums.partservice.PartServiceWizardScreen;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.MatrixServiceDetailsScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.MatrixServicePdrScreenInteractions;

public class MatrixServiceSteps {
    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        AvailableServicesScreenSteps.openServiceDetails(matrixServiceData.getServiceName());
        MatrixServiceSteps.selectServiceDetails(matrixServiceData);
    }

    public static void selectServiceDetails(MatrixServiceData matrixServiceData) {
        if (matrixServiceData.getVehicle() != null)
            MatrixServiceSteps.selectVehicle(matrixServiceData);
        if (matrixServiceData.getSize() != null)
            MatrixServiceSteps.selectSize(matrixServiceData);
        if (matrixServiceData.getSeverity() != null)
            MatrixServiceSteps.selectSeverity(matrixServiceData);
    }

    public static void selectVehicle(MatrixServiceData matrixServiceData) {
        ListSelectPageInteractions.waitListPageReady(matrixServiceData.getServiceName());
        ListSelectPageInteractions.selectItem(matrixServiceData.getVehicle());
    }

    public static void selectSize(MatrixServiceData matrixServiceData) {
        ListSelectPageInteractions.waitListPageReady(matrixServiceData.getVehicle());
        MatrixServicePdrScreenInteractions.openSize();
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SIZE.getValue());
        MatrixServicePdrScreenInteractions.selectSize(matrixServiceData.getSize());
    }

    public static void selectSeverity(MatrixServiceData matrixServiceData) {
        ListSelectPageInteractions.waitListPageReady(matrixServiceData.getVehicle());
        MatrixServicePdrScreenInteractions.openSeverity();
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SEVERITY.getValue());
        MatrixServicePdrScreenInteractions.selectSeverity(matrixServiceData.getSeverity());
    }

    public static void selectPartServiceInsideMatrixService(PartServiceData partService) {
        MatrixServiceDetailsScreenInteractions.selectService(partService.getServiceName());
        PartServiceSteps.selectpartServiceDetails(partService);
        PartServiceSteps.acceptDetailsScreen();
    }

    public static void switchToSelectedServices() {
        MatrixServiceDetailsScreenInteractions.switchToSelectedServices();
    }

    public static void acceptDetailsScreen() {
        ListSelectPageInteractions.saveListPage();
    }
}
