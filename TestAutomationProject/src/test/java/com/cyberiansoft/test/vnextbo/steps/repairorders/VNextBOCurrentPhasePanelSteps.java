package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCurrentPhasePanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;

public class VNextBOCurrentPhasePanelSteps {

    public static void completeWorkOrderServiceStatus(String orderNumber, String serviceName) {
        VNextBOROPageInteractions.clickWorkOrderCurrentPhaseMenu(orderNumber);
        VNextBOCurrentPhasePanelInteractions.completeWorkOrderServiceStatus(orderNumber, serviceName);
    }

    public static void completeCurrentPhase(String orderNumber) {
        VNextBOROPageInteractions.clickWorkOrderCurrentPhaseMenu(orderNumber);
        VNextBOCurrentPhasePanelInteractions.completeCurrentPhase(orderNumber);
    }
}
