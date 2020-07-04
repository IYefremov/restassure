package com.cyberiansoft.test.vnextbo.validations.servicerequests.details;

import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRVehicleInfoSteps;
import org.testng.Assert;

public class VNextBOSRVehicleInfoValidations {

    public static void verifyVinIsDisplayed(String expected) {
        Assert.assertEquals(VNextBOSRVehicleInfoSteps.getVinNum(), expected, "The VIN# hasn't been displayed properly");
    }

    public static void verifyVinIsFinishedWithValue(String expected) {
        final String vinNum = VNextBOSRVehicleInfoSteps.getVinNum();
        final String lastVinValues = vinNum.substring(vinNum.length() - expected.length());
        Assert.assertEquals(lastVinValues, expected, "The VIN# is not finished with '" + expected + "'");
    }
}
