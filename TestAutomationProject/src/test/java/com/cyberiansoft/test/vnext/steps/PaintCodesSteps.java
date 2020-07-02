package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextVehiclePaintCodesScreen;

public class PaintCodesSteps {

    public static void selectPaintCodeByColor(String colorName) {
        VNextVehiclePaintCodesScreen paintCodesScreen = new VNextVehiclePaintCodesScreen();
        paintCodesScreen.getPaintCodeListItemByColorName(colorName).selectColor();
    }
}
