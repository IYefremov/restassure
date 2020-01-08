package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.monitoring.PhaseEditScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class PhaseDetailsScreenInteractions {
    public static void openProblem(String problem) {
        PhaseEditScreen phaseEditScreen = new PhaseEditScreen();
        WaitUtils.click(phaseEditScreen.getPhaseElement(problem));
    }

    public static void completePhase() {
        PhaseEditScreen phaseEditScreen = new PhaseEditScreen();
        WaitUtils.click(phaseEditScreen.getCompleteCurrentPhaseButton());
    }
}
