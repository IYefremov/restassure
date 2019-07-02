package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class VehicleInfoScreenSteps {

    public static void setVIN(String vin) {
        GeneralSteps.dismissHelpingScreenIfPresent();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(vehicleInfoScreen.getRootElement());
        vehicleInfoScreen.setVIN(vin);
    }
}
