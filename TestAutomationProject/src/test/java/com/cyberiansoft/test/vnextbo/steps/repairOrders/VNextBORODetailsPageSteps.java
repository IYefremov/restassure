package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.verifications.VNextBORODetailsPageVerifications;
import org.testng.Assert;

public class VNextBORODetailsPageSteps {

    private VNextBORODetailsPageInteractions detailsPageInteractions;
    private VNextBORODetailsPageVerifications detailsPageVerifications;

    public VNextBORODetailsPageSteps() {
        detailsPageInteractions = new VNextBORODetailsPageInteractions();
        detailsPageVerifications = new VNextBORODetailsPageVerifications();
    }

    public void openServicesTableForStatus(String status, String service) {
        detailsPageInteractions.setStatus(status);
        detailsPageInteractions.expandServicesTable(service);
    }

    public void openServicesTableForStatus(String status) {
        detailsPageInteractions.setStatus(status);
        detailsPageInteractions.expandServicesTable();
    }

    public void setServiceStatusForService(String phase, String status) {
        final String serviceId = detailsPageInteractions.getServiceId(phase);
        detailsPageInteractions.setServiceStatusForService(serviceId, status);
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertEquals(detailsPageInteractions.getServiceStatusValue(serviceId), status,
                "The status hasn't been set for service");
    }

    public void setCheckInOptionForPhase() {
        detailsPageInteractions.openActionsDropDownForPhase();
        Assert.assertTrue(detailsPageVerifications.isCheckInOptionDisplayedForPhase(),
                "The 'Check in' option hasn't been displayed for phase");
        detailsPageInteractions.clickCheckInOptionForPhase();
    }

    public void setCheckOutOptionForPhase() {
        detailsPageInteractions.openActionsDropDownForPhase();
        Assert.assertTrue(detailsPageVerifications.isCheckOutOptionDisplayedForPhase(),
                "The 'Check out' option hasn't been displayed for phase");
        detailsPageInteractions.clickCheckOutOptionForPhase();
    }
}