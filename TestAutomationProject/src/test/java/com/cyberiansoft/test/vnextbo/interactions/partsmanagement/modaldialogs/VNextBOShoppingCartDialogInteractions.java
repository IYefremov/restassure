package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOShoppingCartDialog;

public class VNextBOShoppingCartDialogInteractions {

    public static String[] getPriceAndCorePriceByPartName(String partName) {
        return Utils.getText(new VNextBOShoppingCartDialog().getPriceByPartName(partName)).replace("$", "").split("\n");
    }

    public static void selectPartByPartService(String store, String partName) {
        final VNextBOShoppingCartDialog shoppingCartDialog = new VNextBOShoppingCartDialog();
        Utils.clickElement(shoppingCartDialog.getRepairOrderPartInputByPartName(store, partName));
        Utils.selectOptionInDropDown(shoppingCartDialog.getPartStatusDropDown(),
                shoppingCartDialog.getPartStatusListBoxOptions());
    }

    public static void clickOrderButton() {
        final VNextBOShoppingCartDialog shoppingCartDialog = new VNextBOShoppingCartDialog();
        WaitUtilsWebDriver.elementShouldBeClickable(shoppingCartDialog.getOrderButton(), true, 5);
        Utils.clickElement(shoppingCartDialog.getOrderButton());
        WaitUtilsWebDriver.waitABit(45*60);
    }
}
