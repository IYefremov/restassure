package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import org.testng.Assert;

public class PriceMatrixScreenValidations {

    public static void verifyPriceMatrixScreenSubTotalValue(String expectedPrice) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartSubTotalPrice(), expectedPrice);
    }

    public static void verifyPriceMatrixScreenTotalValue(String expectedPrice) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartTotalPrice(), expectedPrice);
    }

    public static void verifyVehiclePartPriceValue(String vehiclePart, String expectedPrice) {
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartPriceValue(vehiclePart), expectedPrice);
    }
}
