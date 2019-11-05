package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBOROPageValidations;

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