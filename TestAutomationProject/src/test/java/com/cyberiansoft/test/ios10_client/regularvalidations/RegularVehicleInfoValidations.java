package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import org.testng.Assert;

public class RegularVehicleInfoValidations {

    public static void validateVehicleInfoData(VehicleInfoData vehicleInfoData) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
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
}
