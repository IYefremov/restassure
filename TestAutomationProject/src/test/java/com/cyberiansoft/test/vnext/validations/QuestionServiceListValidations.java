package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class QuestionServiceListValidations {
    public static void validateServicePresent(String serviceName) {
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertNotEquals(availableServicesScreen.getServicesListItems().stream()
                    .filter(
                            serviceElement -> serviceElement.getText().contains(serviceName)
                    ).count(), 0);
            return true;
        });
    }
}
