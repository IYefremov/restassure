package com.cyberiansoft.test.vnextbo.verifications.dialogs;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOChangeTechniciansDialogInteractions;
import org.testng.Assert;

public class VNextBOChangeTechniciansDialogVerifications {

    private VNextBOChangeTechniciansDialogInteractions techniciansDialogInteractions;

    public VNextBOChangeTechniciansDialogVerifications() {
        techniciansDialogInteractions = new VNextBOChangeTechniciansDialogInteractions();
    }

    public void verifyTechnicianIsSet(String technician) {
        techniciansDialogInteractions.setOrderServiceTechnician(technician);
        Assert.assertEquals(techniciansDialogInteractions.getTechnician(), technician,
                "The technician hasn't been set");
    }

    public void verifyVendorIsSet(String vendor) {
        techniciansDialogInteractions.setOrderServiceVendor(vendor);
        Assert.assertEquals(techniciansDialogInteractions.getVendor(), vendor,
                "The vendor hasn't been set");
    }
}