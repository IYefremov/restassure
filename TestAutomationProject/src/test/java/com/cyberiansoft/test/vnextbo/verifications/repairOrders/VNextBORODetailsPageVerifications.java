package com.cyberiansoft.test.vnextbo.verifications.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
import org.testng.Assert;

public class VNextBORODetailsPageVerifications {

    public static String verifyServiceIsDisplayedForCollapsedPhase(String service, String phase) {
        new VNextBORODetailsPageInteractions().expandServicesTable(phase);
        return verifyServiceIsDisplayedForExpandedPhase(service);
    }

    public static String verifyServiceIsDisplayedForExpandedPhase(String service) {
        final String serviceId = new VNextBORODetailsPageInteractions().getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service " + service + " hasn't been displayed");
        return serviceId;
    }

    public static void verifyVendorTechnicianNameIsSet(String name) {
        Assert.assertNotEquals(0, new VNextBORODetailsPageInteractions().getNumberOfVendorTechnicianOptionsByName(name),
                "The Vendor/Technician '" + name + "' hasn't been found.");
    }

    public static void verifyServiceVendorPriceIsSet(String serviceId, String service, String vendorPrice) {
        final VNextBORODetailsPageInteractions detailsPageInteractions = new VNextBORODetailsPageInteractions();
        System.out.println("Vendor price: " + detailsPageInteractions.getServiceVendorPrice(serviceId));
        System.out.println("vendor price to be inserted: " + vendorPrice);
        detailsPageInteractions.setServiceVendorPrice(serviceId, service, vendorPrice);
        Assert.assertEquals(detailsPageInteractions.getServiceVendorPrice(serviceId), vendorPrice,
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
        final VNextBORODetailsPageInteractions detailsPageInteractions = new VNextBORODetailsPageInteractions();
        detailsPageInteractions.openActionsDropDownForPhase();
        if (isCheckInOptionDisplayedForPhase()) {
            detailsPageInteractions.closeActionsDropDownForPhase();
        } else {
            detailsPageInteractions.clickCheckOutOptionForPhase();
        }
    }

    public static boolean isReportProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsReportProblemOption(phase));
    }

    public static boolean isResolveProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseActionsResolveProblemOption(phase));
    }

    public static boolean isProblemIconDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(new VNextBORODetailsPage().getPhaseProblemIcon(phase));
    }

    public static void verifyPhaseStatuses(String[] phaseStatuses) {
        final boolean notMatching = new VNextBORODetailsPageInteractions().getPhaseStatusValues()
                .stream()
                .allMatch(status -> status.equals(phaseStatuses[0])
                        || status.equals(phaseStatuses[1])
                        || status.equals(phaseStatuses[2])
                        || status.equals(phaseStatuses[3]));
        Assert.assertFalse(notMatching, "The phases contain the restricted statuses");
    }
}