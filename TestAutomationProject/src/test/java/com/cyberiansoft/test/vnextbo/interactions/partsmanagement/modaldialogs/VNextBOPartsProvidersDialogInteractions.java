package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersDialog;

public class VNextBOPartsProvidersDialogInteractions {

    private static VNextBOPartsProvidersDialog partsProvidersDialog;

    static {
        partsProvidersDialog = new VNextBOPartsProvidersDialog();
    }

    public static void waitForPartsProvidersModalDialogToBeOpened() {
        WaitUtilsWebDriver.elementShouldBeVisible(
                partsProvidersDialog.getPartsProvidersModal(), true, 5);
    }

    public static void waitForPartsProvidersModalDialogToBeClosed() {
        WaitUtilsWebDriver.elementShouldBeVisible(
                partsProvidersDialog.getPartsProvidersModal(), false, 5);
    }

    public static void clickGetNewQuoteButton(String provider) {
        Utils.clickElement(partsProvidersDialog.getGetNewQuoteButtonByProviderName(provider));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOPartsProvidersRequestFormDialogInteractions.waitForRequestFormDialogToBeOpened();
    }

    public static void clickCloseButton() {
        Utils.clickElement(partsProvidersDialog.getCloseButton());
    }
}
