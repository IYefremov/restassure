package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.EditListElement;
import org.testng.Assert;

public class EditOrderScreenValidations {
    public static void elementShouldHaveStartDate(String elementName, Boolean shouldHaveStartDate) {
        EditListElement pahseElement = new PhasesScreen().getPhaseElement(elementName);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            if (shouldHaveStartDate)
                Assert.assertTrue(new PhasesScreen().getPhaseElement(elementName).isStartDatePresent());
            else
                Assert.assertFalse(new PhasesScreen().getPhaseElement(elementName).isStartDatePresent());
            return true;
        });
    }

    public static void validateElementState(String elementName, ServiceStatus serviceStatus) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(new PhasesScreen().getPhaseElement(elementName).getStatus(), serviceStatus.getStatus());
            return true;
        });
    }
}
