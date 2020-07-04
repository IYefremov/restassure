package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.BundleServiceScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

import java.util.stream.Collectors;

public class BundleServiceValidations {
    public static void validateServiceSelected(String serviceName) {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.collectionSizeIsGreaterThan(bundleServiceScreen.getServiceList(), 0);
        Assert.assertNotNull(bundleServiceScreen.getServiceListItem(serviceName), "Service is not present in selectedSection");
    }

    public static void validateNumberOfServicesSelected(String serviceName, int expectedNumber) {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.collectionSizeIsGreaterThan(bundleServiceScreen.getServiceList(), 0);
        Assert.assertEquals(bundleServiceScreen.getServiceListItems(serviceName).size(), expectedNumber);
    }

    public static void validateServicesHasPartsValues(String serviceName, String expectedPartInfo) {
        BundleServiceScreen bundleServiceScreen = new BundleServiceScreen();
        WaitUtils.collectionSizeIsGreaterThan(bundleServiceScreen.getServiceList(), 0);
        WaitUtils.collectionSizeIsGreaterThan(bundleServiceScreen.getServiceListItems(serviceName).stream().filter(item ->
                item.getServicePartInfo().equals(expectedPartInfo)).collect(Collectors.toList()), 0);
    }
}
