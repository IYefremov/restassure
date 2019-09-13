package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.PartServiceListScreen;
import org.testng.Assert;

public class PartServiceListValidations {
    public static void validateNoServicePresent() {
        PartServiceListScreen partServiceListScreen = new PartServiceListScreen();
        Assert.assertTrue(partServiceListScreen.getServiceList().isEmpty());
    }

    public static void validateServicePresent(String serviceName) {
        PartServiceListScreen partServiceListScreen = new PartServiceListScreen();
        Assert.assertTrue(partServiceListScreen.getServiceList().stream().anyMatch(service -> service.getText().contains(serviceName)));
        Assert.assertTrue(partServiceListScreen.getServiceList().isEmpty());

    }
}
