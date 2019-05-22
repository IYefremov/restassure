package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class VehicleInfoScreenSteps {

    public static void setVIN(String vin) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.waitUntilElementIsPresent(vehicleInfoScreen.getRootElement());
        GeneralSteps.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.setVIN(vin);
    }
}
