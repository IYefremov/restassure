package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import org.testng.Assert;

public class RegularAvailableServicesScreenValidations {

    public static void verifyServiceExixts(String serviceName, boolean isExists) {
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        if (isExists)
            Assert.assertTrue(servicesScreen.isServiceExists(serviceName));
        else
            Assert.assertFalse(servicesScreen.isServiceExists(serviceName));
    }
}
