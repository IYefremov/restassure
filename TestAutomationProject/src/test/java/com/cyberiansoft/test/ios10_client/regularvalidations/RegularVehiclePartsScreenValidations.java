package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import org.testng.Assert;

public class RegularVehiclePartsScreenValidations {

    public static void
    verifyVehiclePartScreenSubTotalValue(String expectedPrice) {
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        Assert.assertEquals(vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice(), expectedPrice);
    }
}
