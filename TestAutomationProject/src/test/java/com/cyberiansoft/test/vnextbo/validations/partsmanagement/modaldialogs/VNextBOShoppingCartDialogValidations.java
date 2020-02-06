package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOShoppingCartDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOShoppingCartDialog;
import org.testng.Assert;

public class VNextBOShoppingCartDialogValidations {

    public static boolean isShoppingCartDialogDisplayed(boolean displayed) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOShoppingCartDialog().getShoppingCartDialog(), displayed, 10);
    }

    public static void verifyPriceAndCorePriceByPartName(String partName, String price, String corePrice) {
        final String[] prices = VNextBOShoppingCartDialogInteractions.getPriceAndCorePriceByPartName(partName);
        Assert.assertEquals(prices[0], price, "The price is not displayed properly in the shopping cart dialog");
        Assert.assertEquals(prices[1], corePrice, "The core price is not displayed properly in the shopping cart dialog");
    }
}
