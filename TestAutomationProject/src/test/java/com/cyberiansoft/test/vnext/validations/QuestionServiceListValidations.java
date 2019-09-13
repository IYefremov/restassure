package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import org.testng.Assert;

import java.util.stream.Collectors;

public class QuestionServiceListValidations {
    public static void validateServicePresent(String serviceName) {
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        Assert.assertFalse(availableServicesScreen.getServicesListItems().stream()
                .filter(
                        serviceElement -> serviceElement.getText().contains(serviceName)
                )
                .collect(Collectors.toList()).isEmpty());
    }
}
