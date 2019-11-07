package com.cyberiansoft.test.vnextbo.validations.dialogs;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOChangeTechniciansDialogInteractions;
import org.testng.Assert;

public class VNextBOChangeTechniciansDialogVerifications {

    public static void verifyTechnicianIsSet(String technician) {
        final VNextBOChangeTechniciansDialogInteractions techniciansDialogInteractions =
                new VNextBOChangeTechniciansDialogInteractions();
        techniciansDialogInteractions.setOrderServiceTechnician(technician);
        Assert.assertEquals(techniciansDialogInteractions.getTechnician(), technician,
                "The technician hasn't been set");
    }

    public static void verifyVendorIsSet(String vendor) {
        final VNextBOChangeTechniciansDialogInteractions techniciansDialogInteractions =
                new VNextBOChangeTechniciansDialogInteractions();
        techniciansDialogInteractions.setOrderServiceVendor(vendor);
        Assert.assertEquals(techniciansDialogInteractions.getVendor(), vendor,
                "The vendor hasn't been set");
    }
}