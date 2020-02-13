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

    public static void verifyOrderStatusIsCorrect(String expectedStatus) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().getOrderStatusDropDown()), expectedStatus,
                "Order status hasn't been correct");
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

    public static void verifyOrderPriorityIsCorrect(String priority) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().getPriorityDropDown()), priority,
                "Priority hasn't been correct");
    }

    public static void verifyServiceIsDisplayed(String serviceDescription, boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().serviceDescription(serviceDescription)),
                "Service with description " + serviceDescription + "hasn't been added");
        else try {
            Assert.assertFalse(Utils.isElementDisplayed(new VNextBORODetailsWebPageNew().serviceDescription(serviceDescription)),
                    "Service with description " + serviceDescription + "has been added");
        } catch (NoSuchElementException ex) {}
    }

    public static void verifyServicePriceIsCorrect(String service, String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBORODetailsWebPageNew().servicePriceInputField(service)), expectedPrice,
                "Service price hasn't been correct");
    }

    public static void verifyServiceQuantityIsCorrect(String service, String expectedQuantity) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBORODetailsWebPageNew().serviceQtyInputField(service)), expectedQuantity,
                "Service quantity hasn't been correct");
    }

    public static void verifyPartServicesAmountIsCorrect(int expectedNumber) {

        Assert.assertEquals(new VNextBORODetailsWebPageNew().getPartServicesNamesList().size(), expectedNumber,
                "Part services amount hasn't been correct");
    }

    public static void verifyFlagIsCorrect(String flagColor) {

        Assert.assertTrue(new VNextBORODetailsWebPageNew().getFlagIcon().getAttribute("style").contains(flagColor),
                "Flag hasn't been correct");
    }

    public static void verifyServiceStartedDateIsCorrect(String service, String expectedStartDate) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceStartedDate(service)), expectedStartDate,
                "Service started date hasn't been correct");
    }

    public static void verifyServiceCompletedDateIsCorrect(String service, String expectedCompletedDate) {

        Assert.assertEquals(Utils.getText(new VNextBORODetailsWebPageNew().serviceCompletedDate(service)), expectedCompletedDate,
                "Service completed date hasn't been correct");
    }

    public static void verifyServiceHelpInfoIsCorrect(String service, String expectedHelpInfo) {

        VNextBORODetailsWebPageNew detailsPage = new VNextBORODetailsWebPageNew();
        Utils.hoverElement(detailsPage.serviceHelpIcon(service));
        Assert.assertTrue(Utils.isElementDisplayed(detailsPage.serviceHelpIconHelpInfo(service)),
                "Service help info pop-up hasn't been displayed");
        Assert.assertEquals(Utils.getText(detailsPage.serviceHelpIconHelpInfo(service)), expectedHelpInfo,
                "Service completed date hasn't been correct");
    }
}
