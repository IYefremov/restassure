package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOChangeTechniciansDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBOROPageVerifications;
import org.testng.Assert;

public class VNextBORepairOrdersPageSteps {

    public static String setTechnicianAndVendorByWoNumber(String woNumber, String vendor) {
        new VNextBOROPageInteractions().clickTechniciansFieldForWO(woNumber);
        final VNextBOChangeTechniciansDialogInteractions changeTechniciansDialogInteractions = new VNextBOChangeTechniciansDialogInteractions();
        Assert.assertTrue(changeTechniciansDialogInteractions.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been opened");
        changeTechniciansDialogInteractions.setVendor(vendor);
        final String technician = changeTechniciansDialogInteractions.setTechnician();
        changeTechniciansDialogInteractions.clickOkButton();
        Assert.assertFalse(changeTechniciansDialogInteractions.isChangeTechnicianDialogDisplayed(),
                "The Change Technician dialog hasn't been closed");
        return technician;
    }

    public static void openRODetailsPage(String woNumber) {
        new VNextBOROPageInteractions().clickWoLink(woNumber);
        Assert.assertTrue(new VNextBORODetailsPageInteractions().isRODetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }

    public static void openRODetailsPage() {
        new VNextBOROPageInteractions().clickWoLink();
        Assert.assertTrue(new VNextBORODetailsPageInteractions().isRODetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }

    public static void setSavedSearchOption(String option) {
        final VNextBOROPageInteractions roPageInteractions = new VNextBOROPageInteractions();
        roPageInteractions.clickSavedSearchArrow();
        roPageInteractions.selectSavedSearchDropDownOption(option);
    }

    public static void openAdvancedSearchDialog() {
        new VNextBOROPageInteractions().clickAdvancedSearchCaret();
        VNextBOROPageVerifications.verifyAdvancedSearchDialogIsDisplayed();
    }
}