package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;

import java.util.List;

public class RegularServiceDetailsScreenSteps {

    public static void serServiceDetailsData(ServiceData serviceData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (serviceData.getServicePrice() != null)
            selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
        if (serviceData.getServiceQuantity() != null)
            selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
        if (serviceData.getVehiclePart() != null)
            slectServiceVehiclePart(serviceData.getVehiclePart());
        if (serviceData.getVehicleParts() != null)
            slectServiceVehicleParts(serviceData.getVehicleParts());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void slectServiceVehiclePart(VehiclePartData vehiclePartData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void slectServiceVehicleParts(List<VehiclePartData> vehiclePartsData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (VehiclePartData vehiclePartData : vehiclePartsData)
            selectedServiceDetailsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

}
