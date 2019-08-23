package com.cyberiansoft.test.vnext.interactions;

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
}
