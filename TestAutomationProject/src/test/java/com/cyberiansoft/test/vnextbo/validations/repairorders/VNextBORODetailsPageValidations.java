package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class VNextBORODetailsPageValidations {

    public static String verifyServiceIsDisplayedForCollapsedPhase(String service, String phase) {
        VNextBORODetailsPageInteractions.expandPhasesTable(phase);
        return verifyServiceIsDisplayedForExpandedPhase(service);
    }

    public static String verifyServiceIsDisplayedForExpandedPhase(String service) {
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service " + service + " hasn't been displayed");
        return serviceId;
    }

    public static void verifyVendorTechnicianNameIsSet(String name) {
        Assert.assertNotEquals(0, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(name),
                "The Vendor/Technician '" + name + "' hasn't been found.");
    }

    public static void verifyServiceVendorPriceIsSet(String serviceId, String vendorPrice) {
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId), vendorPrice,
                "The Vendor Price hasn't been changed");
    }

    public static boolean isPhaseActionsTriggerDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsTrigger());
    }

    public static boolean isPhaseActionsTriggerDisplayed(String phase) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBORODetailsPage().getPhaseActionsTrigger(phase), true, 5);
    }

    public static boolean isCheckInOptionDisplayedForPhase() {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBORODetailsPage().getPhaseActionsCheckInOption(), true, 2);
    }

    public static boolean isCheckOutOptionDisplayedForPhase() {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBORODetailsPage().getPhaseActionsCheckOutOption(), true, 2);
    }

    public static void verifyCheckInOptionIsDisplayedForPhase() {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase();
        if (isCheckInOptionDisplayedForPhase()) {
            VNextBORODetailsPageInteractions.closeActionsDropDownForPhase();
        } else {
            VNextBORODetailsPageInteractions.clickCheckOutOptionForPhase();
        }
    }

    public static boolean isRoDetailsSectionDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBORODetailsPage().getRoDetailsSection(), true, 5);
    }

    public static boolean isRoDetailsSectionNotDisplayed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBORODetailsPage().getRoDetailsSection(), false, 7);
    }

    public static void verifyServiceOrTaskDescriptionsContainText(String text) {
        final boolean present = VNextBORODetailsPageInteractions.getServiceAndTaskDescriptionsList()
                .stream()
                .anyMatch(string -> string.contains(text));

        Assert.assertTrue(present, "The order contains neither the service nor the task '" + text + "'.");
    }

    public static boolean isReportProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsReportProblemOption(phase), 5);
    }

    public static boolean isResolveProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsResolveProblemOption(phase));
    }

    public static boolean isProblemIconDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseProblemIcon(phase), 5);
    }

    public static boolean isProblemIconNotDisplayedForPhase(String phase) {
        return Utils.isElementNotDisplayed(new VNextBORODetailsPage().getPhaseProblemIcon(phase), 5);
    }

    public static boolean isCompleteCurrentPhaseDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getCompleteCurrentPhaseActionsOption(phase));
    }

    public static boolean isProblemIconDisplayedForService(String serviceId) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getServiceProblemIcon(serviceId), 5);
    }

    public static boolean isProblemIconNotDisplayedForService(String serviceId) {
        return Utils.isElementNotDisplayed(new VNextBORODetailsPage().getServiceProblemIcon(serviceId), 5);
    }

    public static void verifyPhaseStatuses(String[] phaseStatuses) {
        final boolean notMatching = VNextBORODetailsPageInteractions.getPhaseStatusValues()
                .stream()
                .allMatch(status -> status.equals(phaseStatuses[0])
                        || status.equals(phaseStatuses[1])
                        || status.equals(phaseStatuses[2])
                        || status.equals(phaseStatuses[3]));
        Assert.assertFalse(notMatching, "The phases contain the restricted statuses");
    }

    public static void verifyPhaseStatusIsDisplayed(String phase, String status) {
        Assert.assertEquals(Utils.getText(new VNextBORODetailsPage().getPhaseStatusBoxValue(phase)), status,
                "The Phase status is not displayed as expected");
    }

    public static void verifyPhaseStatus(String phase, String... phaseStatusesNotToBeDisplayed) {
        final WebElement phaseStatusBoxValue = new VNextBORODetailsPage().getPhaseStatusBoxValue(phase);
        for (String status : phaseStatusesNotToBeDisplayed) {
            Assert.assertNotEquals(Utils.getText(phaseStatusBoxValue), status,
                    "The phase status shouldn't contain the status " + status);
        }
    }

    public static void verifyPhaseStatusOrPartPhaseStatusIsDisplayed(String phase, String status, String[] phaseStatuses) {
        if (!Utils.getText(new VNextBORODetailsPage().getPhaseStatusBoxValue(phase)).equals(status)) {
            verifyPartPhaseStatusIsCorrect(phaseStatuses);
        }
    }

    private static void verifyPartPhaseStatusIsCorrect(String[] phaseStatuses) {
        final List<WebElement> partsPhaseStatusDropDowns = new VNextBORODetailsPage().getPartsPhaseStatusDropDowns();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(partsPhaseStatusDropDowns);
        if (!partsPhaseStatusDropDowns.isEmpty()) {
            final List<String> statuses = Utils.getText(partsPhaseStatusDropDowns);
            final boolean matching = statuses.stream().allMatch(partStatus -> partStatus.equals(phaseStatuses[0])
                    || partStatus.equals(phaseStatuses[1])
                    || partStatus.equals(phaseStatuses[2])
                    || partStatus.equals(phaseStatuses[3]));
            Assert.assertTrue(matching, "The parts phases contain the restricted statuses");
        }
    }

    public static void verifyActionsMenuIconIsHiddenForPhase(String phase) {
        Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBORODetailsPage().getActionsTriggerForPhase(phase), 5),
                "The actions trigger has been displayed for phase " + phase);
    }

    public static boolean isReportProblemOptionDisplayedForService(String serviceId) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getServiceReportProblemOption(serviceId), 4);
    }

    public static boolean isReportProblemOptionNotDisplayedForService(String serviceId) {
        return Utils.isElementNotDisplayed(new VNextBORODetailsPage().getServiceReportProblemOption(serviceId), 4);
    }

    public static boolean isResolveProblemOptionDisplayedForService(String serviceId) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getServiceResolveProblemOption(serviceId), 4);
    }

    public static boolean isServiceStatusPresentInOptionsList(String option) {
        return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBORODetailsPage().getServiceStatusListBoxOptions())
                .stream()
                .map(WebElement::getText)
                .anyMatch(service -> service.equals(option));
    }

    public static void verifyStatusHasBeenSetForService(String serviceId, String status) {
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), status,
                "The status hasn't been set for service");
    }

    public static void verifyStatusHasBeenSetForAllServices(List<String> serviceIds, String status) {
        for (String serviceId : serviceIds) {
            verifyStatusHasBeenSetForService(serviceId, status);
        }
    }

    public static boolean isImageOnHoldStatusDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getOnHoldValue());
    }

    public static boolean isFlagsDropDownOpened() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getFlagsDropDown(), 5);
    }

    public static boolean isStartOrderButtonVisible() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getStartOrderButton());
    }

    public static boolean isToBeAddedLaterServiceNotificationDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getToBeAddedLaterServiceNotification(), 10);
    }

    public static boolean isServiceNotificationToBeAddedLaterDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getToBeAddedLaterServiceNotification(), 10);
    }

    public static boolean isHelpInfoDialogDisplayed(String serviceId, String status) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getInfoDialog(serviceId, status));
    }

    public static boolean isServiceCompletedDateDisplayed(String serviceId) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getServiceCompletedDate(serviceId));
    }
}