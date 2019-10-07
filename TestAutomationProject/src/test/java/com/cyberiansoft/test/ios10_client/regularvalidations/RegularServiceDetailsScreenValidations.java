package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.ServicePartData;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import org.testng.Assert;

public class RegularServiceDetailsScreenValidations {

    public static void verifyServiceTechnicianIsSelected(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (serviceTechnician.isSelected())
            Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
        else
            Assert.assertFalse(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
    }

    public static void verifyServiceParValue(ServicePartData servicePartData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), servicePartData.getServicePartValue());
    }
}
