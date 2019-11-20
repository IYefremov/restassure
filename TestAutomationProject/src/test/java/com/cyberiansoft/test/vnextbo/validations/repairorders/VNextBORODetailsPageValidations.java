package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

public class VNextBORODetailsPageValidations {

    public static String verifyServiceIsDisplayedForCollapsedPhase(String service, String phase) {
        VNextBORODetailsPageInteractions.expandServicesTable(phase);
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

    public static void verifyServiceVendorPriceIsSet(String serviceId, String service, String vendorPrice) {
        System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
        System.out.println("vendor price to be inserted: " + vendorPrice);
        VNextBORODetailsPageInteractions.setServiceVendorPrice(serviceId, service, vendorPrice);
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId), vendorPrice,
                "The Vendor Price hasn't been changed");
    }

    public static boolean isPhaseActionsTriggerDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsTrigger());
    }

    public static boolean isPhaseActionsTriggerDisplayed(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsTrigger(phase));
    }

    public static boolean isCheckInOptionDisplayedForPhase() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsCheckInOption());
    }

    public static boolean isCheckOutOptionDisplayedForPhase() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsCheckOutOption());
    }

    public static void verifyCheckInOptionIsDisplayedForPhase() {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase();
        if (isCheckInOptionDisplayedForPhase()) {
            VNextBORODetailsPageInteractions.closeActionsDropDownForPhase();
        } else {
            VNextBORODetailsPageInteractions.clickCheckOutOptionForPhase();
        }
    }

    public static boolean isRODetailsSectionDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getRoDetailsSection());
    }

    public static boolean isReportProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsReportProblemOption(phase));
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
        try {
            WaitUtilsWebDriver.getWait().until((ExpectedCondition<Boolean>) service ->
                    VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId).equals(status));
        } catch (Exception ignored) {}
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), status,
                "The status hasn't been set for service");
    }

    public static boolean isRoDetailsSectionDisplayed() {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getRoDetailsSection());
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