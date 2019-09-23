package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import org.testng.Assert;

public class RegularPriceMatrixScreenValidations {

    public static void verifyPriceMatrixScreenSubTotalValue(String expectedPrice) {
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.waitPriceMatrixScreenLoad();
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartSubTotalPrice(), expectedPrice);
    }

}
