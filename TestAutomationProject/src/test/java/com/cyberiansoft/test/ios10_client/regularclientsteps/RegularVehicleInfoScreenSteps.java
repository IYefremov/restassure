package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import org.testng.Assert;

public class RegularVehicleInfoScreenSteps {

    public static void setVIN(String VIN) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(VIN);
    }

    public static void setMakeAndModel(String make, String model) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setMakeAndModel(make, model);
    }

    public static void selectColor(String vehicleColor) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setColor(vehicleColor);
    }

    public static void setMileage(String mileage) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setMileage(mileage);
    }

    public static void setStockNumber(String stockNumber) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setStock(stockNumber + "\n");
    }

    public static void setRoNumber(String roNumber) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setRO(roNumber + "\n");
    }

    public static void setLicPlate(String licPlate) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setLicensePlate(licPlate + "\n");
    }

    public static void setYear(String year) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setYear(year);
    }

    public static void setTrim(String trimValue) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setTrim(trimValue);
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
        if (vehicleInfoData.getVehicleMake() != null) {
            setMakeAndModel(vehicleInfoData.getVehicleMake(), vehicleInfoData.getVehicleModel());
        }
        if (vehicleInfoData.getVehicleColor() != null) {
            selectColor(vehicleInfoData.getVehicleColor());
        }
        if (vehicleInfoData.getVehicleYear() != null) {
            setYear(vehicleInfoData.getVehicleYear());
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
        if (vehicleInfoData.getTrim() != null) {
            setTrim(vehicleInfoData.getTrim());
        }
    }

    public static String getInspectionNumber() {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        return vehicleScreen.getInspectionNumber();
    }

    public static String getWorkOrderNumber() {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        return vehicleScreen.getWorkOrderNumber();
    }
}
