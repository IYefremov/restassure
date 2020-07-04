package com.cyberiansoft.test.vnextbo.steps.servicerequests.details;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.details.VNextBOSRGeneralInfo;

public class VNextBOSRGeneralInfoSteps {

    public static String getRONum() {
        return Utils.getText(new VNextBOSRGeneralInfo().getRoNum()).replace("RO#: ", "");
    }
}
