package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROProblemsInteractions;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCompleteCurrentPhaseDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBORODetailsPageSteps {

    public static void openServicesTableForStatus(String status, String service) {
        VNextBORODetailsPageInteractions.setStatus(status);
        VNextBORODetailsPageInteractions.expandPhasesTable(service);
    }

    public static void openServicesTableForStatus(String status) {
        VNextBORODetailsPageInteractions.setStatus(status);
        VNextBORODetailsPageInteractions.expandPhasesTable();
    }

    public static void setServiceStatusForService(String phase, String status) {
        final String serviceId = VNextBORODetailsPageInteractions.getServiceId(phase);
        setServiceStatusByServiceId(serviceId, status);
    }

    public static void setServiceStatusByServiceId(String serviceId, String status) {
        final String serviceStatusValue = VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId);
        if (serviceStatusValue.equals("Problem")) {
            VNextBORODetailsPageInteractions.clickServiceStatusBox(serviceId);
            VNextBOROProblemsInteractions.resolveProblem();
            VNextBORODetailsPageInteractions.selectServiceStatus(status);
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        } else if (!(serviceStatusValue.isEmpty() || serviceStatusValue.equals(status))) {
            VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, status);
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        }
    }

    public static void setServiceStatusForMultipleServicesByServiceId(List<String> serviceIds, String status) {
        for (String serviceId : serviceIds) {
            setServiceStatusByServiceId(serviceId, status);
            VNextBOROProblemsInteractions.clickResolveButton();
        }
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

    public static void setCompleteCurrentPhaseForPhaseWithProblem(String phase) {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isCompleteCurrentPhaseDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickCompleteCurrentPhaseForPhase(phase);
        }
        Assert.assertTrue(VNextBOCompleteCurrentPhaseDialogValidations.isCompleteCurrentPhaseDialogDisplayed(),
                "The Complete Current phase dialog hasn't been opened");
    }

    public static void setCompleteCurrentPhase(String phase) {
        VNextBORODetailsPageInteractions.openActionsDropDownForPhase(phase);
        if (VNextBORODetailsPageValidations.isCompleteCurrentPhaseDisplayedForPhase(phase)) {
            VNextBORODetailsPageInteractions.clickCompleteCurrentPhaseForPhase(phase);
        } else {
            VNextBORODetailsPageInteractions.closeActionsDropDownForPhase();
        }
        WaitUtilsWebDriver.waitABit(500);
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

    public static void setReportProblemForMultipleServices(List<String> serviceIds, String problem, String description) {
        for (String serviceId : serviceIds) {
            setReportProblemForService(serviceId, problem, description);
        }
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

    public static void openRoPageByClickingBreadCrumbRo() {
        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionNotDisplayed(),
                "The RO page has been displayed after clicking the breadcrumb 'Repair Orders'.");
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static List<String> getAllPhaseServicesId(String phase) {
        VNextBORODetailsPageInteractions.expandPhasesTable(phase);
        return VNextBORODetailsPageInteractions.getPhasesServicesId(phase);
    }

    public static List<String> setTechniciansForAllPhaseServices(String phase) {
        return getAllPhaseServicesId(phase)
                .stream()
                .map(VNextBORODetailsPageInteractions::setTechnician)
                .collect(Collectors.toList());
    }

    public static void resetServiceStartDate(String serviceId) {
        VNextBORODetailsPageInteractions.clickActionsIcon(serviceId);
        VNextBORODetailsPageInteractions.clickResetStartDate(serviceId);
    }

    public static void resetServiceStartDate(List<String> allServicesId) {
        allServicesId.forEach(VNextBORODetailsPageSteps::resetServiceStartDate);
    }
}