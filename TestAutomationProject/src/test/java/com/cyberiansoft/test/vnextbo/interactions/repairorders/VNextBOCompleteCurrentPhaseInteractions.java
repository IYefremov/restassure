package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCompleteCurrentPhaseDialog;

public class VNextBOCompleteCurrentPhaseInteractions {

    public static String getServiceName(String service) {
        return Utils.getText(new VNextBOCompleteCurrentPhaseDialog().getService(service)).trim();
    }
}
