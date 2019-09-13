package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.vnext.interactions.services.BundleServiceScreenInteractrions;

public class BundleServiceSteps {
    public static void openServiceDetails(String serviceName) {
        BundleServiceScreenInteractrions.selectService(serviceName);
    }

    public static void switchToSelectedServices() {
        BundleServiceScreenInteractrions.switchToSelectedServices();
    }
}
