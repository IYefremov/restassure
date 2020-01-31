package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOShoppingCartDialog;

public class VNextBOShoppingCartDialogInteractions {

    private static VNextBOShoppingCartDialog shoppingCartDialog;

    static {
        shoppingCartDialog = new VNextBOShoppingCartDialog();
    }

    public static String[] getPriceAndCorePriceByPartName(String partName) {
        return Utils.getText(shoppingCartDialog.getPriceByPartName(partName)).replace("$", "").split("\n");
    }

    public static void selectPartByPartService(String store, String partName) {
        Utils.clickElement(shoppingCartDialog.getRepairOrderPartInputByPartName(store, partName));
        Utils.selectOptionInDropDown(shoppingCartDialog.getPartStatusDropDown(),
                shoppingCartDialog.getPartStatusListBoxOptions());
    }

    public static void clickOrderButton() {
        WaitUtilsWebDriver.elementShouldBeClickable(shoppingCartDialog.getOrderButton(), true, 5);
        Utils.clickElement(shoppingCartDialog.getOrderButton());
        WaitUtilsWebDriver.waitABit(45*60);
    }
}
