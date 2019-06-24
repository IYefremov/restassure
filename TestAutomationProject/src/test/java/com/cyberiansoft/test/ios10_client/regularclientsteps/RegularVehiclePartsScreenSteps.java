package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import org.testng.Assert;

import java.util.List;

public class RegularVehiclePartsScreenSteps {

    public static void selectVehiclePartAndSetData(VehiclePartData vehiclePartData) {
        selectVehiclePart(vehiclePartData);
        if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                selectVehiclepartAdditionalService(serviceData);
            }
        }
        if (vehiclePartData.getVehiclePartAdditionalService() != null) {
            selectVehiclepartAdditionalService(vehiclePartData.getVehiclePartAdditionalService());
        }
        if (vehiclePartData.getVehiclePartPrice() != null)
            setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        if (vehiclePartData.getVehiclePartTime() != null)
            setVehiclePartTime(vehiclePartData.getVehiclePartTime());
        saveVehiclePart();
    }

    public static void saveVehiclePart() {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.saveVehiclePart();
    }

    public static void setVehiclePartTime(String vehiclePartTime) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.setTime(vehiclePartTime);
    }

    public static void setVehiclePartPrice(String vehiclePartPrice) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.setPrice(vehiclePartPrice);
    }

    public static void selectVehiclePart(VehiclePartData vehiclePartData) {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        if (vehiclePartData.getVehiclePartOption() != null)
            vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        if (vehiclePartData.getVehiclePartSize() != null)
            vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
    }

    public static void selectVehiclepartAdditionalService(ServiceData serviceData) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        if ((serviceData.getServiceQuantity() != null) || (serviceData.getServicePrice() != null)) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(serviceData);
        } else
            vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
    }

    public static void verifyIfVehiclePartContainsPriceValue(VehiclePartData vehiclePartData) {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        Assert.assertTrue(priceMatrixScreen.isPriceMatrixContainsPriceValue(vehiclePartData.getVehiclePartName(), vehiclePartData.getVehiclePartTotalPrice()));
    }

    public static void savePriceMatrixData() {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.clickSave();
    }

    public static void verifyVehiclePartTechnicianValue(ServiceTechnician serviceTechnician) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), serviceTechnician.getTechnicianFullName());
    }

    public static void verifyVehiclePartTechniciansValue(List<ServiceTechnician> serviceTechnicians) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        for (ServiceTechnician serviceTechnician : serviceTechnicians)
            Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
    }

    public static void selectVehiclePartTechnician(ServiceTechnician serviceTechnician) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.clickOnTechnicians();
        RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
        selectedservicescreen.selecTechnician(serviceTechnician.getTechnicianFullName());
        selectedservicescreen.saveSelectedServiceDetails();
    }
}
