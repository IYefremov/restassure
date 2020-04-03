package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersDialog;

public class VNextBOPartsProvidersDialogInteractions {

    public static void waitForPartsProvidersModalDialogToBeOpened() {
        WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartsProvidersDialog().getPartsProvidersModal(), true, 5);
    }

    public static void waitForPartsProvidersModalDialogToBeClosed() {
        WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartsProvidersDialog().getPartsProvidersModal(), false, 5);
    }

    public static void clickGetNewQuoteButton(String provider) {
        Utils.clickElement(new VNextBOPartsProvidersDialog().getStoreGetNewQuoteButtonByProviderName(provider));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOPartsProvidersRequestFormDialogInteractions.waitForRequestFormDialogToBeOpened();
    }

    public static void clickGetNewQuoteButtonForGenericPartProvider(String provider) {
        Utils.clickElement(new VNextBOPartsProvidersDialog().getGenericPartProviderGetNewQuoteButtonByProviderName(provider));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOPartsProvidersRequestFormDialogInteractions.waitForRequestFormDialogToBeOpened();
    }

    public static void clickCloseButton() {
        Utils.clickElement(new VNextBOPartsProvidersDialog().getCloseButton());
    }
}
