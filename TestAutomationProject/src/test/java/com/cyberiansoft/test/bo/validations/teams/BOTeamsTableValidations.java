package com.cyberiansoft.test.bo.validations.teams;

import com.cyberiansoft.test.bo.steps.company.teams.BOTeamsTableSteps;
import org.testng.Assert;

public class BOTeamsTableValidations {

    public static void verifyEitherLocationOrAreaIsSet(String area) {
        Assert.assertEquals(BOTeamsTableSteps.getValueByColumnName("Area"), area,
                "The provider is neither set to location, nor to the '" + area + "' area");
    }
}
