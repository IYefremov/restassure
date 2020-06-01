package com.cyberiansoft.test.vnextbo.validations.servicerequests.details;

import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRContactsSteps;
import org.testng.Assert;

public class VNextBOSRContactsValidations {

    public static void verifyOwnerNameIsDisplayed(String expected) {
        Assert.assertEquals(VNextBOSRContactsSteps.getOwnerName(), expected,
                "The 'Owner name' hasn't been displayed properly");
    }

    public static void verifyAdvisorNameIsDisplayed(String expected) {
        Assert.assertEquals(VNextBOSRContactsSteps.getAdvisorName(), expected,
                "The 'Advisor name' hasn't been displayed properly");
    }
}
