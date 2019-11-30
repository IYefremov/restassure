package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROAdvancedSearchDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.testng.Assert;

public class VNextBOROAdvancedSearchDialogSteps {

    public static void searchByActivePhase(String phase, String phaseStatus, String timeFrame) {
        VNextBOROAdvancedSearchDialogInteractions.setPhase(phase);
        VNextBOROAdvancedSearchDialogInteractions.setPhaseStatus(phaseStatus);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(timeFrame);
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();
    }

    public static void openAdvancedSearchDialog() {
        if (!VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(1)) {
            VNextBOROPageInteractions.clickAdvancedSearchCaret();
        }
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(3),
                "The advanced search dialog is not opened");
    }

    public static void closeAdvancedSearchDialog() {
        VNextBOROAdvancedSearchDialogInteractions.closeAdvancedSearchDialog();
        VNextBOROPageValidations.verifyAdvancedSearchDialogIsClosed();
    }
}