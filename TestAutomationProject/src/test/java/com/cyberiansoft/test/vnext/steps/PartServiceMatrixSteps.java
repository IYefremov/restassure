package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceMatrixData;
import com.cyberiansoft.test.vnext.enums.partservice.PartServiceWizardScreen;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.MatrixServicePdrScreenInteractions;

public class PartServiceMatrixSteps {
    public static void selectPartMatrixService(PartServiceMatrixData partServiceMatrixData) {
        AvailableServicesScreenSteps.openServiceDetails(partServiceMatrixData.getServiceName());
        PartServiceMatrixSteps.selectServiceDetails(partServiceMatrixData);
    }

    public static void selectServiceDetails(PartServiceMatrixData partServiceMatrixData) {
        if (partServiceMatrixData.getVehicle() != null)
            PartServiceMatrixSteps.selectVehicle(partServiceMatrixData);
        if (partServiceMatrixData.getSize() != null)
            PartServiceMatrixSteps.selectSize(partServiceMatrixData);
        if (partServiceMatrixData.getSeverity() != null)
            PartServiceMatrixSteps.selectSeverity(partServiceMatrixData);
    }

    public static void selectVehicle(PartServiceMatrixData partServiceMatrixData) {
        ListSelectPageInteractions.waitListPageReady(partServiceMatrixData.getServiceName());
        ListSelectPageInteractions.selectItem(partServiceMatrixData.getVehicle());
    }

    public static void selectSize(PartServiceMatrixData partServiceMatrixData) {
        ListSelectPageInteractions.waitListPageReady(partServiceMatrixData.getVehicle());
        MatrixServicePdrScreenInteractions.openSize();
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SIZE.getValue());
        MatrixServicePdrScreenInteractions.selectSize(partServiceMatrixData.getSize());
    }

    public static void selectSeverity(PartServiceMatrixData partServiceMatrixData) {
        ListSelectPageInteractions.waitListPageReady(partServiceMatrixData.getVehicle());
        MatrixServicePdrScreenInteractions.openSeverity();
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SEVERITY.getValue());
        MatrixServicePdrScreenInteractions.selectSeverity(partServiceMatrixData.getSeverity());
    }

    public static void acceptDetailsScreen() {
        ListSelectPageInteractions.saveListPage();
    }
}
