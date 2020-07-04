package com.cyberiansoft.test.bo.validations.operations.servicerequestslist;

import com.cyberiansoft.test.bo.steps.operations.servicerequestslist.BOSRListBlockSteps;
import org.testng.Assert;

public class BOSRListValidations {

    public static void verifySRIsDisplayed(String srNumber) {
        Assert.assertEquals(BOSRListBlockSteps.getSrByNumber(srNumber), srNumber,
                "The SR '" + srNumber + "' hasn't been displayed");
    }
}
