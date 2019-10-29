package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import org.testng.Assert;

public class VehicleInfoValidations {

    public static void validateVehicleInfoData(VehicleInfoData vehicleInfoData) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        if (vehicleInfoData.getVehicleMake() != null) {
            Assert.assertEquals(vehicleScreen.getMake(), vehicleInfoData.getVehicleMake());
        }
        if (vehicleInfoData.getVehicleModel() != null) {
            Assert.assertEquals(vehicleScreen.getModel(), vehicleInfoData.getVehicleModel());
        }
        if (vehicleInfoData.getVehicleColor() != null) {
            Assert.assertEquals(vehicleScreen.getColor(), vehicleInfoData.getVehicleColor());
        }
        if (vehicleInfoData.getVehicleYear() != null) {
            Assert.assertEquals(vehicleScreen.getYear(), vehicleInfoData.getVehicleYear());
        }
        if (vehicleInfoData.getMileage() != null) {
            Assert.assertEquals(vehicleScreen.getMileage(), vehicleInfoData.getMileage());
        }
        if (vehicleInfoData.getStockNumber() != null) {
            Assert.assertEquals(vehicleScreen.getStock(), vehicleInfoData.getStockNumber());
        }
        if (vehicleInfoData.getRoNumber() != null) {
            Assert.assertEquals(vehicleScreen.getRO(), vehicleInfoData.getRoNumber());
        }
        if (vehicleInfoData.getLicPlate() != null) {
            Assert.assertEquals(vehicleScreen.getLicensePlate(), vehicleInfoData.getLicPlate());
        }
        if (vehicleInfoData.getTrim() != null) {
            Assert.assertEquals(vehicleScreen.getTrim(), vehicleInfoData.getTrim());
        }
    }

    public static void verifyVehicleInfoScreenCustomerValue(AppCustomer expectedCustomer) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        Assert.assertEquals(vehicleScreen.getCustomerValue(), expectedCustomer.getFullName().trim());
    }

    public static void verifyVehicleInfoScreenTechValue(ServiceTechnician serviceTechnician) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        Assert.assertEquals(vehicleScreen.getTechnician(), serviceTechnician.getTechnicianFullName().trim());
    }

}
