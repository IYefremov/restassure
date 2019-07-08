package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;

import java.util.List;

public class ServiceDetailsScreenSteps {

    public static void setServiceDetailsData(ServiceData serviceData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        if (serviceData.getServicePrice() != null)
            setServicePriceValue(serviceData.getServicePrice());
        if (serviceData.getServiceQuantity() != null)
            setServiceQuantityValue(serviceData.getServiceQuantity());
        if (serviceData.getVehiclePart() != null)
            slectServiceVehiclePart(serviceData.getVehiclePart());
        if (serviceData.getVehicleParts() != null)
            slectServiceVehicleParts(serviceData.getVehicleParts());
        if (serviceData.getQuestionData() != null)
            selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
        saveServiceDetails();
    }

    public static void setServicePriceValue(String servicePrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(servicePrice);
    }

    public static void setServiceQuantityValue(String serviceQuantity) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceQuantityValue(serviceQuantity);
    }

    public static void slectServiceVehiclePart(VehiclePartData vehiclePartData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void slectServiceVehicleParts(List<VehiclePartData> vehiclePartsData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (VehiclePartData vehiclePartData : vehiclePartsData)
            selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void saveServiceDetails() {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }
}
