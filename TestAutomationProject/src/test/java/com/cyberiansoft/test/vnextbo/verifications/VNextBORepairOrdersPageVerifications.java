package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;
import org.testng.Assert;

public class VNextBORepairOrdersPageVerifications {

    private VNextBOROPageInteractions repairOrdersPageInteractions;
    private VNextBOROAdvancedSearchDialogInteractions advancedSearchDialogInteractions;

    public VNextBORepairOrdersPageVerifications() {
        repairOrdersPageInteractions = new VNextBOROPageInteractions();
        advancedSearchDialogInteractions = new VNextBOROAdvancedSearchDialogInteractions();
    }

    public void verifyTechnicianIsDisplayed(String woNumber, String technician) {
        Assert.assertEquals(repairOrdersPageInteractions.getTechniciansValueForWO(woNumber), technician,
                "The technician hasn't been changed");
    }

    public void verifyAdvancedSearchDialogIsDisplayed() {
        repairOrdersPageInteractions.clickAdvancedSearchCaret();
        Assert.assertTrue(advancedSearchDialogInteractions.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");
    }
}