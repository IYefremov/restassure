package com.cyberiansoft.test.vnextbo.verifications.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
import org.testng.Assert;

public class VNextBORODetailsPageVerifications {

    private VNextBORODetailsPageInteractions detailsPageInteractions;
    private VNextBORODetailsPage detailsPage;

    public VNextBORODetailsPageVerifications() {
        detailsPageInteractions = new VNextBORODetailsPageInteractions();
        detailsPage = new VNextBORODetailsPage();
    }

    public String verifyServiceIsDisplayedForCollapsedPhase(String service, String phase) {
        detailsPageInteractions.expandServicesTable(phase);
        return verifyServiceIsDisplayedForExpandedPhase(service);
    }

    public String verifyServiceIsDisplayedForExpandedPhase(String service) {
        final String serviceId = detailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service " + service + " hasn't been displayed");
        return serviceId;
    }

    public void verifyVendorTechnicianNameIsSet(String name) {
        Assert.assertNotEquals(0, detailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(name),
                "The Vendor/Technician '" + name + "' hasn't been found.");
    }

    public void verifyServiceVendorPriceIsSet(String serviceId, String service, String vendorPrice) {
        System.out.println("Vendor price: " + detailsPageInteractions.getServiceVendorPrice(serviceId));
        System.out.println("vendor price to be inserted: " + vendorPrice);
        detailsPageInteractions.setServiceVendorPrice(serviceId, service, vendorPrice);
        Assert.assertEquals(detailsPageInteractions.getServiceVendorPrice(serviceId), vendorPrice,
                "The Vendor Price hasn't been changed");
    }

    public boolean isPhaseActionsTriggerDisplayed() {
        return Utils.isElementDisplayed(detailsPage.getPhaseActionsTrigger());
    }

    public boolean isPhaseActionsTriggerDisplayed(String phase) {
        return Utils.isElementDisplayed(detailsPage.getPhaseActionsTrigger(phase));
    }

    public boolean isCheckInOptionDisplayedForPhase() {
        return Utils.isElementDisplayed(detailsPage.getPhaseActionsCheckInOption());
    }

    public boolean isCheckOutOptionDisplayedForPhase() {
        return Utils.isElementDisplayed(detailsPage.getPhaseActionsCheckOutOption());
    }

    public void verifyCheckInOptionIsDisplayedForPhase() {
        detailsPageInteractions.openActionsDropDownForPhase();
        if (isCheckInOptionDisplayedForPhase()) {
            detailsPageInteractions.closeActionsDropDownForPhase();
        } else {
            detailsPageInteractions.clickCheckOutOptionForPhase();
        }
    }

    public boolean isReportProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(detailsPage.getPhaseActionsReportProblemOption(phase));
    }

    public boolean isResolveProblemOptionDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(detailsPage.getPhaseActionsResolveProblemOption(phase));
    }

    public boolean isProblemIconDisplayedForPhase(String phase) {
        return Utils.isElementDisplayed(detailsPage.getPhaseProblemIcon(phase));
    }

    public void verifyPhaseStatuses(String[] phaseStatuses) {
        final boolean notMatching = detailsPageInteractions.getPhaseStatusValues()
                .stream()
                .allMatch(status -> status.equals(phaseStatuses[0])
                        || status.equals(phaseStatuses[1])
                        || status.equals(phaseStatuses[2])
                        || status.equals(phaseStatuses[3]));
        Assert.assertFalse(notMatching, "The phases contain the restricted statuses");
    }
}