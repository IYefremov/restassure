package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.testng.Assert;

import java.util.List;

public class SelectedServicesScreenSteps {

    public static void verifyServicesAreSelected(List<ServiceData> servicesData) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : servicesData) {
            if (serviceData.isSelected())
                Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
            else
                Assert.assertFalse(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        }

    }
}
