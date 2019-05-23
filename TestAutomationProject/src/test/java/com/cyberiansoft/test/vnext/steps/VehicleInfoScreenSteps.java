package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VehicleInfoScreenSteps {

    public static void setVIN(String vin) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(vehicleInfoScreen.getRootElement());
        GeneralSteps.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.setVIN(vin);
    }
}
