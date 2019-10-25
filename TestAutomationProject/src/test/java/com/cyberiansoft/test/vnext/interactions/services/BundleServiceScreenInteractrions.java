package com.cyberiansoft.test.vnext.interactions.services;

import com.cyberiansoft.test.driverutils.DriverBuilder;
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
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(DriverBuilder.getInstance().getAppiumDriver());
        keyboard.typeValue(bundlePrice);
        keyboard.clickKeyboardDoneButton();
    }
}
