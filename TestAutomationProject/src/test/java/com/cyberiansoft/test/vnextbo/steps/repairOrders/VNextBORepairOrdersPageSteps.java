package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOChangeTechniciansDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORepairOrdersDetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORepairOrdersPageInteractions;
import org.testng.Assert;

public class VNextBORepairOrdersPageSteps {

    private VNextBORepairOrdersPageInteractions repairOrdersPageInteractions;
    private VNextBOChangeTechniciansDialogInteractions changeTechniciansDialogInteractions;

    public VNextBORepairOrdersPageSteps() {
        repairOrdersPageInteractions = new VNextBORepairOrdersPageInteractions();
        changeTechniciansDialogInteractions = new VNextBOChangeTechniciansDialogInteractions();
    }

    public String setTechnicianAndVendorByWoNumber(String woNumber, String vendor) {
        repairOrdersPageInteractions.clickTechniciansFieldForWO(woNumber);
        Assert.assertTrue(changeTechniciansDialogInteractions.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been opened");
        changeTechniciansDialogInteractions.setVendor(vendor);
        final String technician = changeTechniciansDialogInteractions.setTechnician();
        changeTechniciansDialogInteractions.clickOkButton();
        Assert.assertFalse(changeTechniciansDialogInteractions.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been closed");
        return technician;
    }

    public void openRODetailsPage(String woNumber) {
        repairOrdersPageInteractions.clickWoLink(woNumber);
        Assert.assertTrue(new VNextBORepairOrdersDetailsPageInteractions().isRODetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }
}