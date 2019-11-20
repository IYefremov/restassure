package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROProblemsInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROResolveProblemDialog;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCompleteCurrentPhaseValidations;
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
        setServiceStatusForServiceByServiceId(serviceId, status);
    }

    public static void setServiceStatusForServiceByServiceId(String serviceId, String status) {
        System.out.println(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId));
        if (VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId).equals("Problem")) {
            VNextBORODetailsPageInteractions.clickServiceStatusBox(serviceId);
            if (Utils.isElementDisplayed(new VNextBOROResolveProblemDialog().getResolveProblemButton(), 5)) {
                VNextBOROProblemsInteractions.clickResolveButton();
            }
            VNextBORODetailsPageInteractions.selectServiceStatus(status);
        } else {
            VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, status);
        }
        WaitUtilsWebDriver.waitForLoading();
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
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickResolveProblemForPhase(phase);
            VNextBOROProblemsInteractions.clickResolveButton();
        }
        VNextBORODetailsPageInteractions.clickReportProblemForPhase(phase);
        handleReportProblemDialog(problem, description);
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(phase),
                "The Problem icon is not displayed for phase after reporting the problem");
    }

    public static void setReportProblemForPhase(String phase, String description) {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickResolveProblemForPhase(phase);
            VNextBOROProblemsInteractions.clickResolveButton();
        }
        VNextBORODetailsPageInteractions.clickReportProblemForPhase(phase);
        VNextBOROProblemsInteractions.setProblemDescription(description);
        VNextBOROProblemsInteractions.clickAddProblemButton();
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForPhase(phase),
                "The Problem icon is not displayed for phase after reporting the problem");
    }

    public static void setResolveProblemForPhase(String phase) {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isResolveProblemOptionDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickResolveProblemForPhase(phase);
            VNextBOROProblemsInteractions.clickResolveButton();
        }
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconNotDisplayedForPhase(phase),
                "The Problem icon is displayed for phase after resolving the problem");
    }

    public static void setCompleteCurrentPhaseForPhase(String phase) {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isCompleteCurrentPhaseDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickCompleteCurrentPhaseForPhase(phase);
        }
        Assert.assertTrue(VNextBOCompleteCurrentPhaseValidations.isCompleteCurrentPhaseDialogDisplayed(),
                "The Complete Current phase dialog hasn't been opened");
    }

    public static void handleReportProblemDialog(String problem, String description) {
        VNextBOROProblemsInteractions.setReportProblem(problem);
        VNextBOROProblemsInteractions.setProblemDescription(description);
        VNextBOROProblemsInteractions.clickAddProblemButton();
    }

    public static void openEditNotesDialog(String serviceId) {
        VNextBORODetailsPageInteractions.openNotesDialog(serviceId);
        Assert.assertTrue(VNextBONotesPageValidations.isEditOrderServiceNotesBlockDisplayed(),
                "The notes block hasn't been displayed");
    }

    public static void setReportProblemForService(String serviceId, String problem, String description) {
        VNextBORODetailsPageInteractions.clickActionsIcon(serviceId);
        if (VNextBORODetailsPageValidations.isResolveProblemOptionDisplayedForService(serviceId)) {
            VNextBORODetailsPageInteractions.clickResolveProblemForService(serviceId);
            VNextBOROProblemsInteractions.clickResolveButton();
        }
        VNextBORODetailsPageInteractions.clickReportProblemForService(serviceId);
        handleReportProblemDialog(problem, description);
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconDisplayedForService(serviceId),
                "The Problem icon is not displayed for service after reporting the problem");
    }

    public static void setResolveProblemForService(String serviceId) {
        VNextBORODetailsPageInteractions.clickActionsIcon(serviceId);
        if (VNextBORODetailsPageValidations.isResolveProblemOptionDisplayedForService(serviceId)) {
            VNextBORODetailsPageInteractions.clickResolveProblemForService(serviceId);
            VNextBOROProblemsInteractions.clickResolveButton();
        }
        Assert.assertTrue(VNextBORODetailsPageValidations.isProblemIconNotDisplayedForService(serviceId),
                "The Problem icon is displayed for service after resolving the problem");
    }
}