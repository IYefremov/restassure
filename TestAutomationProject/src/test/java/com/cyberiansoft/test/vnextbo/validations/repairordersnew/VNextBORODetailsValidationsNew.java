package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
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

    public static void verifyOrderDetailsSectionIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().getOrderDetailsSection()),
                "Order details section hasn't been displayed");
    }

    public static void verifyProblemIndicatorIsDisplayedForPhase(String phase) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().problemIndicatorByPhase(phase)),
                "Problem indicator hasn't been displayed for the phase '" + phase + "'.");
    }

    public static void verifyProblemIndicatorIsDisplayedForService(String service) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().problemIndicatorByService(service)),
                "Problem indicator hasn't been displayed for the service '" + service + "'.");
    }

    public static void verifyPhaseStatusInDropdownFieldIsCorrect(String phase, String status) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)), status,
                "Status hasn't been correct for the '" + phase + "'.");
    }

    public static void verifyPhaseTextStatusIsCorrect(String phase, String status) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusTextByPhase(phase)), status,
                "Status hasn't been correct for the '" + phase + "'.");
    }
    public static void verifyServiceStatusIsCorrect(String service, String status) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceStatusDropDownByService(service)), status,
                "Status hasn't been correct for the '" + service + "'.");
    }

    public static void verifyActionsButtonIsNotDisplayedForPhase(String phase) {

        try {
            Assert.assertFalse(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().actionsMenuButtonForPhase(phase)),
                    "Actions button has been displayed");
        } catch (NoSuchElementException ex) {
        }
    }

    public static void verifyReportProblemActionButtonIsNotDisplayedForCompletedService(String service) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        try {
            Assert.assertFalse(Utils.isElementDisplayed(detailsWebPageNew.getReportProblemForServiceActionButton()),
                    "Actions button has been displayed");
        } catch (NoSuchElementException ex) {
        }
    }

    public static void verifyPhaseTotalPriceHasBeenChanged(String phase, String initialPrice) {

        Assert.assertFalse(Utils.getText(new VNextBORODetailsWebPageNew().phaseTotalPrice(phase)).equals(initialPrice),
                "Total price hasn't changed for the phase: " + phase);
    }
}
