package com.cyberiansoft.test.bo.validations.company.teams;

import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsDialogSteps;
import org.testng.Assert;

public class BOTeamsDialogValidations {

    public static void verifyProviderValue(String expected) {
        Assert.assertEquals(BOTeamsDialogSteps.getPartProvider(), expected,
                "The part provider is not set properly");
    }
}
