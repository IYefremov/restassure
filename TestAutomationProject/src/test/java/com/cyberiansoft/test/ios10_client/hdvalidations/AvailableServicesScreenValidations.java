package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import org.testng.Assert;

public class AvailableServicesScreenValidations {

    public static void verifyServiceExixts(String serviceName, boolean isExists) {
        ServicesScreen servicesScreen = new ServicesScreen();
        if (isExists)
            Assert.assertTrue(servicesScreen.isServiceExists(serviceName));
        else
            Assert.assertFalse(servicesScreen.isServiceExists(serviceName));
    }
}
