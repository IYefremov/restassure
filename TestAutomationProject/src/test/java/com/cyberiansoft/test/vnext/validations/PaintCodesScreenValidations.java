package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextVehiclePaintCodesScreen;
import org.testng.Assert;

public class PaintCodesScreenValidations {

    public static void verifyPainCodesListContainsItems(String colorName) {
        VNextVehiclePaintCodesScreen paintCodesScreen = new VNextVehiclePaintCodesScreen();
        paintCodesScreen.getColorsList().forEach(paintCodeListItem -> {
            Assert.assertTrue(paintCodeListItem.getColorValue().contains(colorName),
                    "Incorrect color in the list: " + paintCodeListItem.getColorValue() + "expected: " + colorName);
        });
    }

    public static void verifyPainCodesListEmpty() {
        VNextVehiclePaintCodesScreen paintCodesScreen = new VNextVehiclePaintCodesScreen();
        Assert.assertTrue(paintCodesScreen.getEmptyList().isDisplayed());
    }
}
