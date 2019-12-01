package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;

public class VNextBOROAdvancedSearchDialogSteps {

    public static void searchByActivePhase(String phase, String phaseStatus, String timeFrame) {
        final VNextBOROAdvancedSearchDialogInteractions advancedSearchDialogInteractions =
                new VNextBOROAdvancedSearchDialogInteractions();
        advancedSearchDialogInteractions.setPhase(phase);
        advancedSearchDialogInteractions.setPhaseStatus(phaseStatus);
        advancedSearchDialogInteractions.setTimeFrame(timeFrame);
        advancedSearchDialogInteractions.clickSearchButton();
    }

    public static void closeAdvancedSearchDialog() {

        new VNextBOROAdvancedSearchDialogInteractions().closeAdvancedSearchDialog();
        VNextBOROPageValidations.verifyAdvancedSearchDialogIsClosed();
    }
}