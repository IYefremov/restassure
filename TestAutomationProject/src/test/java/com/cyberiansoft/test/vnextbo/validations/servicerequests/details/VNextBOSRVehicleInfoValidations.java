package com.cyberiansoft.test.vnextbo.validations.servicerequests.details;

import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRVehicleInfoSteps;
import org.testng.Assert;

public class VNextBOSRVehicleInfoValidations {

    public static void verifyVinIsDisplayed(String expected) {
        Assert.assertEquals(VNextBOSRVehicleInfoSteps.getVinNum(), expected, "The VIN# hasn't been displayed properly");
    }
}
