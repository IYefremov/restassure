package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import org.testng.Assert;

public class TechniciansPopupValidations {

    public static void verifyServiceTechnicianIsSelected(ServiceTechnician serviceTechnician) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        if (serviceTechnician.isSelected())
            Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
        else
            Assert.assertFalse(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
    }

    public static void verifyServiceTechnicianPriceValue(ServiceTechnician serviceTechnician, String expactedPrice) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(serviceTechnician.getTechnicianFullName()),
                PricesCalculations.getPriceRepresentation(expactedPrice));
    }

    public static void verifyServiceTechnicianPercentageValue(ServiceTechnician serviceTechnician, String expactedPercentageValue) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        Assert.assertEquals(techniciansPopup.getTechnicianPercentage(serviceTechnician.getTechnicianFullName()),
                PricesCalculations.getPercentageRepresentation(expactedPercentageValue));
    }

    public static void verifyServiceTechnicianCustomPercentageValue(ServiceTechnician serviceTechnician, String expactedPercentageValue) {
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(serviceTechnician.getTechnicianFullName()),
                PricesCalculations.getPercentageRepresentation(expactedPercentageValue));
    }
}
