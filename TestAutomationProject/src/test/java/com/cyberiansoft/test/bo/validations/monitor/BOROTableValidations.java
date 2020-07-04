package com.cyberiansoft.test.bo.validations.monitor;

import com.cyberiansoft.test.bo.steps.monitor.repairorders.BOROTableSteps;
import org.testng.Assert;

public class BOROTableValidations {

    public static void verifyWOIsDisplayed(String woNumber) {
        final String wo = BOROTableSteps.getWOList()
                .stream()
                .filter(order -> order.equals(woNumber))
                .findFirst()
                .orElse("");
        Assert.assertEquals(wo, woNumber, "The wo '" + woNumber + "' hasn't been displayed");
    }
}
