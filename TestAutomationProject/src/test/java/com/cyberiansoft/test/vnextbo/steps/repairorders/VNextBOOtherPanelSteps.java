package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOOtherPanelInteractions;

public class VNextBOOtherPanelSteps {

    public static void setHighPriority(String orderNumber) {
        VNextBOROPageSteps.openOtherPanel(orderNumber);
        VNextBOOtherPanelInteractions.clickRedPriorityButton(orderNumber);
    }

    public static void setLowPriority(String orderNumber) {
        VNextBOROPageSteps.openOtherPanel(orderNumber);
        VNextBOOtherPanelInteractions.clickGreenPriorityButton(orderNumber);
    }

    public static void setMidPriority(String orderNumber) {
        VNextBOROPageSteps.openOtherPanel(orderNumber);
        VNextBOOtherPanelInteractions.clickOrangePriorityButton(orderNumber);
    }

    public static void setFlag(String orderNumber, String flag) {
        VNextBOROPageSteps.openOtherPanel(orderNumber);
        VNextBOOtherPanelInteractions.clickFlagButton(orderNumber, flag);
    }
}
