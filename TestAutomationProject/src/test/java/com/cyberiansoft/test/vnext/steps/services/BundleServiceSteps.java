package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.vnext.interactions.services.BundleServiceScreenInteractions;

public class BundleServiceSteps {
    public static void openServiceDetails(String serviceName) {
        BundleServiceScreenInteractions.selectService(serviceName);
    }

    public static void switchToSelectedServices() {
        BundleServiceScreenInteractions.switchToSelectedServices();
    }

    public static void setBundlePrice(String bundlePrice) {
        BundleServiceScreenInteractions.setBundlePrice(bundlePrice);
    }
}
