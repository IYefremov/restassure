package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROProblemsInteractions;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import org.testng.Assert;

public class VNextBORODetailsPageSteps {

    public static void openServicesTableForStatus(String status, String service) {
        VNextBORODetailsPageInteractions.setStatus(status);
        VNextBORODetailsPageInteractions.expandServicesTable(service);
    }

    public static void openServicesTableForStatus(String status) {
        VNextBORODetailsPageInteractions.setStatus(status);
        VNextBORODetailsPageInteractions.expandServicesTable();
    }

    public static void setServiceStatusForService(String phase, String status) {
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(phase);
        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, status);
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), status,
                "The status hasn't been set for service");
    }

    public static void setCheckInOptionForPhase() {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase();
        Assert.assertTrue(VNextBORODetailsPageValidations.isCheckInOptionDisplayedForPhase(),
                "The 'Check in' option hasn't been displayed for phase");
        VNextBORODetailsPageInteractions.clickCheckInOptionForPhase();
    }

    public static void setCheckOutOptionForPhase() {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase();
        Assert.assertTrue(VNextBORODetailsPageValidations.isCheckOutOptionDisplayedForPhase(),
                "The 'Check out' option hasn't been displayed for phase");
        VNextBORODetailsPageInteractions.clickCheckOutOptionForPhase();
    }

    public static void setReportProblemForPhase(String phase, String problem, String description) {
        if (VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(phase)) {
            setResolveProblemForPhase(phase);
        }
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        VNextBORODetailsPageInteractions.clickReportProblemForPhase(phase);
        handleReportProblemDialog(problem, description);
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(phase),
                "The Problem icon hasn't been displayed");
    }

    public static void setResolveProblemForPhase(String phase) {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isResolveProblemOptionDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickResolveProblemForPhase(phase);
            new VNextBOROProblemsInteractions().clickResolveButton();
        }
    }

    public static void handleReportProblemDialog(String problem, String description) {
        final VNextBOROProblemsInteractions roProblemsInteractions = new VNextBOROProblemsInteractions();
        roProblemsInteractions.setReportProblem(problem);
        roProblemsInteractions.setProblemDescription(description);
        roProblemsInteractions.clickAddProblemButton();
    }

    public static void openEditNotesDialog(String serviceId) {
        VNextBORODetailsPageInteractions.openNotesDialog(serviceId);
        Assert.assertTrue(VNextBONotesPageValidations.isEditOrderServiceNotesBlockDisplayed(),
                "The notes block hasn't been displayed");
    }
}