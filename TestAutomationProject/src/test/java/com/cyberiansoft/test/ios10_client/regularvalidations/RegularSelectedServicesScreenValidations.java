package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import org.testng.Assert;

public class RegularSelectedServicesScreenValidations {

    public static void verifyServiceIsSelected(String serviceName, boolean isSelected) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        if (isSelected)
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceName));
        else
            Assert.assertFalse(selectedServicesScreen.checkServiceIsSelected(serviceName));
    }
}
