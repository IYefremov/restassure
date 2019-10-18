package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.ServicePartData;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import org.testng.Assert;

public class RegularServiceDetailsScreenValidations {

    public static void verifyServiceTechnicianIsSelected(ServiceTechnician serviceTechnician) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        if (serviceTechnician.isSelected())
            Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
        else
            Assert.assertFalse(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
    }

    public static void verifyServiceTechnicianPriceValue(ServiceTechnician serviceTechnician, String expactedPrice) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(serviceTechnician.getTechnicianFullName()),
                PricesCalculations.getPriceRepresentation(expactedPrice));
    }

    public static void verifyServiceTechnicianPercentageValue(ServiceTechnician serviceTechnician, String expactedPercentageValue) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(serviceTechnician.getTechnicianFullName()),
                PricesCalculations.getPercentageRepresentation(expactedPercentageValue));
    }

    public static void verifyServiceTechnicianCustomPercentageValue(ServiceTechnician serviceTechnician, String expactedPercentageValue) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(serviceTechnician.getTechnicianFullName()),
                PricesCalculations.getPercentageRepresentation(expactedPercentageValue));
    }

    public static void verifyServicePartValue(ServicePartData servicePartData) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), servicePartData.getServicePartValue());
    }

    public static void verifyServiceDetailsAdjustmentValue(String expectedValue) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), expectedValue);
    }

    public static void verifyServicePriceValue(String expectedPrice) {
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(expectedPrice));
    }
}
