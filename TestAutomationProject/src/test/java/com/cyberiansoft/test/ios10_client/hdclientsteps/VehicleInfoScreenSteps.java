package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import org.testng.Assert;

public class VehicleInfoScreenSteps {


    public static void setVIN(String VIN) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(VIN);
    }

    public static void waitVehicleScreenLoaded() {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
    }

    public static void setMakeAndModel(String make, String model) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setMakeAndModel(make, model);
    }

    public static void selectColor(String vehicleColor) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setColor(vehicleColor);
    }

    public static void setMileage(String mileage) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setMileage(mileage);
    }

    public static void setStockNumber(String stockNumber) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setStock(stockNumber + "\n");
    }

    public static void setRoNumber(String roNumber) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setRO(roNumber + "\n");
    }

    public static void setPoNumber(String poNumber) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setPO(poNumber + "\n");
    }

    public static void setLicPlate(String licPlate) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setLicensePlate(licPlate + "\n");
    }

    public static void setYear(String year) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setYear(year);
    }

    public static void setTrim(String trimValue) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setTrim(trimValue);
    }

    public static void setType(String typeValue) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setType(typeValue);
    }

    public static void verifyMakeModelyearValues(VehicleInfoData vehicleInfoData) {
        VehicleScreen vehicleScreen = new VehicleScreen();
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
        if (vehicleInfoData.getVehicleType() != null) {
            setType(vehicleInfoData.getVehicleType());
        }
        if (vehicleInfoData.getStockNumber() != null) {
            setStockNumber(vehicleInfoData.getStockNumber());
        }
        if (vehicleInfoData.getRoNumber() != null) {
            setRoNumber(vehicleInfoData.getRoNumber());
        }
        if (vehicleInfoData.getPoNumber() != null) {
            setPoNumber(vehicleInfoData.getPoNumber());
        }
        if (vehicleInfoData.getLicPlate() != null) {
            setLicPlate(vehicleInfoData.getLicPlate());
        }
        if (vehicleInfoData.getTrim() != null) {
            setTrim(vehicleInfoData.getTrim());
        }
    }

    public static String getInspectionNumber() {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        return vehicleScreen.getInspectionNumber();
    }

    public static void selectAdvisor(String advisor) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.selectAdvisor(advisor);
    }

    public static void clickTech() {
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.clickTech();
    }

}
