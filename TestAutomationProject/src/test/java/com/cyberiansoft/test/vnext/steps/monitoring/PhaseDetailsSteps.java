package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.interactions.PhaseDetailsScreenInteractions;

public class PhaseDetailsSteps {
    public static void selectProblem(String problem) {
        PhaseDetailsScreenInteractions.openProblem(problem);
    }
}
