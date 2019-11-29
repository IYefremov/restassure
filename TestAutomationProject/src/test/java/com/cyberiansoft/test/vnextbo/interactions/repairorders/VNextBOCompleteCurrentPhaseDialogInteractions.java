package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCompleteCurrentPhaseDialog;

public class VNextBOCompleteCurrentPhaseDialogInteractions {

    public static String getServiceName(String service) {
        return Utils.getText(new VNextBOCompleteCurrentPhaseDialog().getService(service)).trim();
    }

    public static void clickResolveButtonForService(String service) {
        Utils.clickElement(new VNextBOCompleteCurrentPhaseDialog().getResolveButtonForService(service));
    }

    public static void clickCompleteCurrentPhaseButton() {
        Utils.clickElement(new VNextBOCompleteCurrentPhaseDialog().getCompleteCurrentPhaseButton());
    }
}
