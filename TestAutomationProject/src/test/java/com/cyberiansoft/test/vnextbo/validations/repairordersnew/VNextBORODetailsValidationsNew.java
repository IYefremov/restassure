package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBORODetailsWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

public class VNextBORODetailsValidationsNew {

    public static void verifyServiceOrTaskDescriptionsContainText(String text) {

        boolean present = VNextBORODetailsStepsNew.getServiceAndTaskDescriptionsList()
                .stream()
                .anyMatch(string -> string.contains(text));

        Assert.assertTrue(present, "The order contains neither the service nor the task '" + text + "'.");
    }

    public static void verifyProblemIndicatorIsDisplayedForPhase(String phase) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().problemIndicatorByPhase(phase)),
                "Problem indicator hasn't been displayed for the phase '" + phase + "'.");
    }

    public static void verifyPhaseStatusIsCorrect(String phase, String status) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)), status,
                "Status hasn't been correct for the '" + phase + "'.");
    }

    public static void verifyActionsButtonIsNotDisplayed(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        boolean buttonIsDisplayed = false;
        try {
            buttonIsDisplayed = Utils.isElementDisplayed(detailsWebPageNew.actionsMenuButtonByPhase(phase));
            Assert.assertFalse(buttonIsDisplayed, "Actions button has been displayed");
        } catch (NoSuchElementException ex) {
            Assert.assertFalse(buttonIsDisplayed, "Actions button has been displayed");
        }
    }
}
