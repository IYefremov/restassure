package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.services.ServiceDetailsInteractions;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceDetailsScreenSteps {

    public static void changeServicePrice(String newServicePrice) {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.elementShouldBeVisible(serviceDetailsScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(serviceDetailsScreen.getRootElement());
        serviceDetailsScreen.setServiceAmountValue(newServicePrice);
    }

    public static void closeServiceDetailsScreen() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
    }


    public static void openTechniciansScreen() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.openTechnicianMenu();
    }

    public static void increaseServiceQuantity() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.clickIncreaseQuantity();

    }

    public static void decreaseServiceQuantity() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.clickDecreaseQuantity();
    }

    public static void openPartServiceDetails() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.openPartServiceDetails();
    }

    public static void selectVehiclePart(VehiclePartData vehiclePart) {
        ServiceDetailsInteractions.openVehiclePartSelection();
        ListSelectPageInteractions.selectItem(vehiclePart.getVehiclePartName());
    }

    public static void selectVehicleParts(List<VehiclePartData> vehiclePart) {
        ServiceDetailsInteractions.openVehiclePartSelection();
        vehiclePart.stream()
                .map(VehiclePartData::getVehiclePartName).collect(Collectors.toList())
                .forEach(ListSelectPageInteractions::selectItem);
        ListSelectPageInteractions.saveListPage();
    }

    public static void saveServiceDetails() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void openQuestionForm(String questionFormFieldName) {
        ServiceDetailsInteractions.openQuestionForm(questionFormFieldName);
    }
}
