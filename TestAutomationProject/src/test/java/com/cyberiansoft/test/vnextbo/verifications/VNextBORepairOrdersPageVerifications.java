package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORepairOrdersPageInteractions;
import org.testng.Assert;

public class VNextBORepairOrdersPageVerifications {

    private VNextBORepairOrdersPageInteractions repairOrdersPageInteractions;

    public VNextBORepairOrdersPageVerifications() {
        repairOrdersPageInteractions = new VNextBORepairOrdersPageInteractions();
    }

    public void verifyTechnicianIsDisplayed(String woNumber, String technician) {
        Assert.assertEquals(repairOrdersPageInteractions.getTechniciansValueForWO(woNumber), technician,
                "The technician hasn't been changed");
    }
}