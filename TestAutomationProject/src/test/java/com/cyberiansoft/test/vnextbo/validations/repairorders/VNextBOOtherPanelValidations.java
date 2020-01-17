package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOOtherPanel;

public class VNextBOOtherPanelValidations {

    public static boolean isOtherPanelOpened(String orderNumber) {
        return Utils.isElementDisplayed(new VNextBOOtherPanel().getOtherPanel(orderNumber));
    }

    public static boolean isOtherPanelClosed(String orderNumber) {
        return !Utils.isElementDisplayed(new VNextBOOtherPanel().getOtherPanel(orderNumber));
    }
}
