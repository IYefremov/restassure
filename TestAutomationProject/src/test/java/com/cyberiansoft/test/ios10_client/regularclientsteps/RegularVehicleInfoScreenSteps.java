package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import org.testng.Assert;

public class RegularVehicleInfoScreenSteps {

    public static void setVIN(String VIN) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(VIN);
    }

    public static void setMileage(String mileage) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setMileage(mileage);
    }

    public static void setStockNumber(String stockNumber) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setStock(stockNumber);
    }

    public static void setRoNumber(String roNumber) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setRO(roNumber);
    }

    public static void setLicPlate(String licPlate) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setLicensePlate(licPlate);
    }

    public static void verifyMakeModelyearValues(VehicleInfoData vehicleInfoData) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        Assert.assertEquals(vehicleScreen.getMake(), vehicleInfoData.getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), vehicleInfoData.getVehicleModel());
        Assert.assertEquals(vehicleScreen.getYear(), vehicleInfoData.getVehicleYear());
    }

    public static void setVehicleInfoData(VehicleInfoData vehicleInfoData) {
        if (vehicleInfoData.getVINNumber() != null) {
            setVIN(vehicleInfoData.getVINNumber());
        }
        if (vehicleInfoData.getMileage() != null) {
            setMileage(vehicleInfoData.getMileage());
        }
        if (vehicleInfoData.getStockNumber() != null) {
            setStockNumber(vehicleInfoData.getStockNumber());
        }
        if (vehicleInfoData.getRoNumber() != null) {
            setRoNumber(vehicleInfoData.getRoNumber());
        }
        if (vehicleInfoData.getLicPlate() != null) {
            setLicPlate(vehicleInfoData.getLicPlate());
        }
    }
}
