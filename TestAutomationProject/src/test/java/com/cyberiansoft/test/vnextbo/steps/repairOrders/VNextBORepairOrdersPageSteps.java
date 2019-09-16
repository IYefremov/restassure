package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOChangeTechniciansDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;
import org.testng.Assert;

public class VNextBORepairOrdersPageSteps {

    private VNextBOROPageInteractions repairOrdersPageInteractions;
    private VNextBOChangeTechniciansDialogInteractions changeTechniciansDialogInteractions;
    private VNextBORODetailsPageInteractions roDetailsPageInteractions;

    public VNextBORepairOrdersPageSteps() {
        repairOrdersPageInteractions = new VNextBOROPageInteractions();
        changeTechniciansDialogInteractions = new VNextBOChangeTechniciansDialogInteractions();
        roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
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
        Assert.assertTrue(roDetailsPageInteractions.isRODetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }

    public void openRODetailsPage() {
        repairOrdersPageInteractions.clickWoLink();
        Assert.assertTrue(roDetailsPageInteractions.isRODetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }
}