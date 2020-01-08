package com.cyberiansoft.test.vnext.interactions.services;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextCustomKeyboard;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.BundleServiceScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class BundleServiceScreenInteractrions {

    public static void selectService(String serviceName) {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        bundleServiceScreen.getServiceListItem(serviceName).addService();
    }

    public static void switchToSelectedServices() {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.click(bundleServiceScreen.getSelectedServiceScreen());
    }

    public static void setBundlePrice(String bundlePrice) {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.click(bundleServiceScreen.getBundlePriceInput());
        BaseUtils.waitABit(500);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        keyboard.typeValue(bundlePrice);
        keyboard.clickKeyboardDoneButton();
    }

    public static  String getBundleServiceSelectedAmount() {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.waitUntilElementIsClickable(bundleServiceScreen.getSelectedServicesAmount());
        return bundleServiceScreen.getSelectedServicesAmount().getText().replace("$", "").trim();
    }
}
