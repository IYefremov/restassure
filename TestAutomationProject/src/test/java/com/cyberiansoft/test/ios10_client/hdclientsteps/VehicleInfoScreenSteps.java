package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import org.testng.Assert;

public class VehicleInfoScreenSteps {

    public static void verifyMakeModelyearValues(VehicleInfoData vehicleInfoData) {
        VehicleScreen vehicleScreen = new VehicleScreen();
        Assert.assertEquals(vehicleScreen.getMake(), vehicleInfoData.getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), vehicleInfoData.getVehicleModel());
        Assert.assertEquals(vehicleScreen.getYear(), vehicleInfoData.getVehicleYear());
    }
}
