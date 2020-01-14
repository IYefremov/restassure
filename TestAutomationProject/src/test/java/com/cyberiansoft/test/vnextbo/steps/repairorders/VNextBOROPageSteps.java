package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOChangeTechniciansDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOChangeTechniciansDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.testng.Assert;

public class VNextBOROPageSteps {

    public static String setTechnicianAndVendorByWoNumber(String woNumber, String vendor) {
        openChangeTechniciansDialogForWO(woNumber);
        VNextBOChangeTechniciansDialogInteractions.setVendor(vendor);
        String technician = VNextBOChangeTechniciansDialogInteractions.setTechnician();
        if (technician.isEmpty()) {
            technician = VNextBOChangeTechniciansDialogInteractions.setTechnician();
        }
        VNextBOChangeTechniciansDialogInteractions.clickOkButton();
        Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogClosed(),
                "The Change Technician dialog hasn't been closed");
        return technician;
    }

    public static void openChangeTechniciansDialogForWO(String woNumber) {
        VNextBOROPageInteractions.clickTechniciansFieldForWO(woNumber);
        WaitUtilsWebDriver.waitABit(1000);
        Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogOpened(),
                "The Change Technician dialog hasn't been opened");
    }

    public static void openRODetailsPage(String woNumber) {
        VNextBOROPageInteractions.clickWoLink(woNumber);
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }

    public static void openRODetailsPage() {
        VNextBOROPageInteractions.clickWoLink();
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(),
                "The RO Details page hasn't been opened");
    }

    public static void setSavedSearchOption(String option) {
        VNextBOROPageInteractions.clickSavedSearchArrow();
        VNextBOROPageInteractions.selectSavedSearchDropDownOption(option);
    }

    public static void openAdvancedSearchDialog() {
        VNextBOROPageInteractions.clickAdvancedSearchCaret();
        VNextBOROPageValidations.verifyAdvancedSearchDialogIsDisplayed();
    }

    public static void selectViewProblemsOptionInOtherDropDown(String order) {
        VNextBOROPageInteractions.clickWorkOrderOtherMenuButton(order);
        VNextBOROPageInteractions.clickViewProblemsLink();
    }
}