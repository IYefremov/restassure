package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROProblemsInteractions;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBORODetailsPageVerifications;
import org.testng.Assert;

public class VNextBORODetailsPageSteps {

    public static void openServicesTableForStatus(String status, String service) {
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        roDetailsPageInteractions.setStatus(status);
        roDetailsPageInteractions.expandServicesTable(service);
    }

    public static void openServicesTableForStatus(String status) {
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        roDetailsPageInteractions.setStatus(status);
        roDetailsPageInteractions.expandServicesTable();
    }

    public static void setServiceStatusForService(String phase, String status) {
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        final String serviceId = roDetailsPageInteractions.getServiceId(phase);
        roDetailsPageInteractions.setServiceStatusForService(serviceId, status);
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertEquals(roDetailsPageInteractions.getServiceStatusValue(serviceId), status,
                "The status hasn't been set for service");
    }

    public static void setCheckInOptionForPhase() {
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        roDetailsPageInteractions.openActionsDropDownForPhase();
        Assert.assertTrue(VNextBORODetailsPageVerifications.isCheckInOptionDisplayedForPhase(),
                "The 'Check in' option hasn't been displayed for phase");
        roDetailsPageInteractions.clickCheckInOptionForPhase();
    }

    public static void setCheckOutOptionForPhase() {
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        roDetailsPageInteractions.openActionsDropDownForPhase();
        Assert.assertTrue(VNextBORODetailsPageVerifications.isCheckOutOptionDisplayedForPhase(),
                "The 'Check out' option hasn't been displayed for phase");
        roDetailsPageInteractions.clickCheckOutOptionForPhase();
    }

    public static void setReportProblemForPhase(String phase, String problem, String description) {
        if (VNextBORODetailsPageVerifications.isProblemIconDisplayedForPhase(phase)) {
            setResolveProblemForPhase(phase);
        }
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        roDetailsPageInteractions.openActionsDropDownForPhase(phase);
        roDetailsPageInteractions.clickReportProblemForPhase(phase);
        handleReportProblemDialog(problem, description);
        Assert.assertTrue(VNextBORODetailsPageVerifications.isProblemIconDisplayedForPhase(phase),
                "The Problem icon hasn't been displayed");
    }

    public static void setResolveProblemForPhase(String phase) {
        final VNextBORODetailsPageInteractions roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
        roDetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageVerifications.isResolveProblemOptionDisplayedForPhase(phase)) {
            roDetailsPageInteractions.clickResolveProblemForPhase(phase);
            new VNextBOROProblemsInteractions().clickResolveButton();
        }
    }

    public static void handleReportProblemDialog(String problem, String description) {
        final VNextBOROProblemsInteractions roProblemsInteractions = new VNextBOROProblemsInteractions();
        roProblemsInteractions.setReportProblem(problem);
        roProblemsInteractions.setProblemDescription(description);
        roProblemsInteractions.clickAddProblemButton();
    }
}