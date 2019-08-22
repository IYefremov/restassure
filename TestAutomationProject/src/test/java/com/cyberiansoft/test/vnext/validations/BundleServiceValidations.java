package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.BundleServiceScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.springframework.util.Assert;

public class BundleServiceValidations {
    public static void validateServiceSelected(String serviceName) {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.collectionSizeIsGreaterThan(bundleServiceScreen.getServiceList(), 0);
        Assert.notNull(bundleServiceScreen.getServiceListItem(serviceName), "Service is not present in selectedSection");
    }
}
