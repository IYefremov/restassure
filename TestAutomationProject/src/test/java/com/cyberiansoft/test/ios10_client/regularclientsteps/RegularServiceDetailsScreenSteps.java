package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import org.testng.Assert;

import java.util.List;

public class RegularServiceDetailsScreenSteps {

    public static void serServiceDetailsData(ServiceData serviceData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (serviceData.getServicePrice() != null)
            setServicePriceValue(serviceData.getServicePrice());
        if (serviceData.getServiceQuantity() != null)
            setServiceQuantityValue(serviceData.getServiceQuantity());
        if (serviceData.getVehiclePart() != null)
            slectServiceVehiclePart(serviceData.getVehiclePart());
        if (serviceData.getVehicleParts() != null)
            slectServiceVehicleParts(serviceData.getVehicleParts());
        if (serviceData.getQuestionData() != null)
            selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
        saveServiceDetails();
    }

    public static void setServicePriceValue(String servicePrice) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(servicePrice);
    }

    public static void setServiceQuantityValue(String serviceQuantity) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceQuantityValue(serviceQuantity);
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

    public static void saveServiceDetails() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
    }

    public static void clickServiceTechniciansIcon() {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
    }

    public static void verifyServiceTechnicianIsSelected(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (serviceTechnician.isSelected())
            Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
        else
            Assert.assertFalse(selectedServiceDetailsScreen.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
    }

}
