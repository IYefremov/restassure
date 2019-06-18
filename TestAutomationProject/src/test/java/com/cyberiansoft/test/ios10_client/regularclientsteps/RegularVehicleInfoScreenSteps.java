package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import org.testng.Assert;

public class RegularVehicleInfoScreenSteps {

    public static void setVIN(String VIN) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(VIN);
    }

    public static void verifyMakeModelyearValues(VehicleInfoData vehicleInfoData) {
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        Assert.assertEquals(vehicleScreen.getMake(), vehicleInfoData.getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), vehicleInfoData.getVehicleModel());
        Assert.assertEquals(vehicleScreen.getYear(), vehicleInfoData.getVehicleYear());
    }
}
