package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import org.testng.Assert;

public class VNextBORODetailsValidationsNew {

    public static void verifyServiceOrTaskDescriptionsContainText(String text) {

        boolean present = VNextBORODetailsStepsNew.getServiceAndTaskDescriptionsList()
                .stream()
                .anyMatch(string -> string.contains(text));

        Assert.assertTrue(present, "The order contains neither the service nor the task '" + text + "'.");
    }
}
