package com.cyberiansoft.test.vnextbo.steps.servicerequests.details;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.details.VNextBOSRVehicleInfo;

public class VNextBOSRVehicleInfoSteps {

    public static String getVinNum() {
        return Utils.getText(new VNextBOSRVehicleInfo().getVin()).replace("VIN#: ", "");
    }
}
