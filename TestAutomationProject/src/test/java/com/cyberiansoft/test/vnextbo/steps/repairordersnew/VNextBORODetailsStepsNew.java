package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOChangeTechnicianDialogNew;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBONotesDialogNew;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROCompleteCurrentPhaseDialogNew;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBORODetailsWebPageNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBORODetailsStepsNew {

    public static List<String> getServiceAndTaskDescriptionsList() {

        final List<WebElement> serviceAndTaskDescriptionsList = new VNextBORODetailsWebPageNew().getServiceAndTaskDescriptionsList();
        return serviceAndTaskDescriptionsList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static void expandAllPhases() {

        for (WebElement expander : new VNextBORODetailsWebPageNew().getPhaseExpanderList()) {
            Utils.clickElement(expander);
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        }
    }

    public static void expandPhaseByName(String phase) {

        ConditionWaiter.create(30000, 1000, __ -> new VNextBORODetailsWebPageNew().expandPhaseButton(phase).isDisplayed()).execute();
        ConditionWaiter.create(__ -> new VNextBORODetailsWebPageNew().expandPhaseButton(phase).isEnabled()).execute();
        Utils.clickElement(new VNextBORODetailsWebPageNew().expandPhaseButton(phase));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void collapsePhaseByName(String phase) {

        ConditionWaiter.create(__ -> new VNextBORODetailsWebPageNew().collapsePhaseButton(phase).isEnabled()).execute();
        Utils.clickElement(new VNextBORODetailsWebPageNew().collapsePhaseButton(phase));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void reportProblemOnPhaseLevelWithoutDescription(String phase, String problemReason) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForPhase(phase).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        ConditionWaiter.create(__ -> detailsWebPageNew.getReportProblemForPhaseActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getReportProblemForPhaseActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithoutDescription(problemReason);
        ConditionWaiter.create(__ -> detailsWebPageNew.phaseStatusDropDownByPhase(phase).equals("Problem")).execute();
    }

    public static void reportProblemForServiceWithoutDescription(String service, String problemReason) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForService(service).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        ConditionWaiter.create(__ -> detailsWebPageNew.getReportProblemForServiceActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getReportProblemForServiceActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithoutDescription(problemReason);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        ConditionWaiter.create(__ -> !Utils.getText(detailsWebPageNew.serviceStatusDropDownByService(service)).equals("Problem")).execute();
    }

    public static void openCompleteCurrentPhaseDialog(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForPhase(phase).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        ConditionWaiter.create(__ -> detailsWebPageNew.getCompleteCurrentPhaseActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getCompleteCurrentPhaseActionButton());
        ConditionWaiter.create(__ -> new VNextBOROCompleteCurrentPhaseDialogNew().getCancelButton().isDisplayed()).execute();
    }

    public static void reportProblemOnPhaseLevelWithDescription(String phase, String problemReason, String problemDescription) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForPhase(phase).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        ConditionWaiter.create(__ -> detailsWebPageNew.getReportProblemForPhaseActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getReportProblemForPhaseActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithDescription(problemReason, problemDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        ConditionWaiter.create(__ -> detailsWebPageNew.phaseStatusDropDownByPhase(phase).equals("Problem")).execute();
    }

    public static void reportProblemForServiceWithDescription(String service, String problemReason, String problemDescription) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForService(service).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        ConditionWaiter.create(__ -> detailsWebPageNew.getReportProblemForServiceActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getReportProblemForServiceActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithDescription(problemReason, problemDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        ConditionWaiter.create(__ -> !Utils.getText(detailsWebPageNew.serviceStatusDropDownByService(service)).equals("Problem")).execute();
    }

    public static void resolveProblemOnPhaseLevel(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForPhase(phase).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        ConditionWaiter.create(__ -> detailsWebPageNew.getResolveProblemForPhaseActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getResolveProblemForPhaseActionButton());
        VNextBOROResolveProblemDialogStepsNew.resolveProblem();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void resolveProblemForService(String service) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.actionsMenuButtonForService(service).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        ConditionWaiter.create(__ -> detailsWebPageNew.getResolveProblemForServiceActionButton().isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.getResolveProblemForServiceActionButton());
        VNextBOROResolveProblemDialogStepsNew.resolveProblem();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void checkInCheckOutPhase(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        try {
            Utils.clickElement(detailsWebPageNew.getCheckInActionButton());
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            VNextBORODetailsValidationsNew.verifyPhaseIsCheckedInCheckedOut(phase, true);
        } catch (NoSuchElementException ex) {
            Utils.clickElement(detailsWebPageNew.getCheckOutActionButton());
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            VNextBORODetailsValidationsNew.verifyPhaseIsCheckedInCheckedOut(phase, false);
        }
    }

    public static void setPhaseStatusIfNeeded(String phase, String expectedStatus) {

        if (Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals("Problem")) {
            resolveProblemOnPhaseLevel(phase);
        }
        if (!Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals(expectedStatus)) {
            Utils.clickElement(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase));
            Utils.clickWithJS(new VNextBORODetailsWebPageNew().dropDownOption(expectedStatus));
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            WaitUtilsWebDriver.waitForPendingRequestsToComplete();
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        }
    }

    public static void setServiceStatusIfNeeded(String service, String expectedStatus) {

        VNextBORODetailsWebPageNew detailsWebPage = new VNextBORODetailsWebPageNew();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        if (Utils.getText(detailsWebPage.serviceStatusDropDownByService(service)).equals("Problem")) {
            resolveProblemForService(service);
            ConditionWaiter.create(__ -> !Utils.getText(detailsWebPage.serviceStatusDropDownByService(service)).equals("Problem")).execute();
        }
        if (!Utils.getText(detailsWebPage.serviceStatusDropDownByService(service)).equals(expectedStatus)) {
            Utils.clickElement(detailsWebPage.serviceStatusDropDownByService(service));
            Utils.clickWithJS(detailsWebPage.dropDownOption(expectedStatus));
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            WaitUtilsWebDriver.waitABit(5000);
            ConditionWaiter.create(__ -> detailsWebPage.serviceStatusDropDownByService(service).isEnabled()).execute();
            ConditionWaiter.create(__ -> Utils.getText(detailsWebPage.serviceStatusDropDownByService(service)).equals(expectedStatus)).execute();
        }
    }

    public static void setServiceQuantity(String service, String quantity) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        ConditionWaiter.create(__ -> detailsWebPageNew.serviceQtyInputField(service).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.serviceQtyInputField(service));
        Utils.clearAndTypeUsingKeyboard(detailsWebPageNew.serviceQtyInputField(service), quantity);
        Utils.clickElement(detailsWebPageNew.serviceNameWebElement(service));
    }

    public static void setServicePrice(String service, String price) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.servicePriceInputField(service));
        Utils.clearAndTypeUsingKeyboard(detailsWebPageNew.servicePriceInputField(service), price);
        Utils.clickElement(detailsWebPageNew.serviceNameWebElement(service));
    }

    public static void setServiceVendorPrice(String service, String vendorPrice) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.serviceVendorPriceInputField(service));
        Utils.clearAndTypeUsingKeyboard(detailsWebPageNew.serviceVendorPriceInputField(service), vendorPrice);
        ConditionWaiter.create(__ -> detailsWebPageNew.serviceNameWebElement(service).isEnabled()).execute();
        Utils.clickElement(detailsWebPageNew.serviceNameWebElement(service));
    }

    public static void setServiceVendor(String service, String vendorPrice) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.serviceVendorDropDown(service));
        Utils.clickWithJS(detailsWebPageNew.dropDownOption(vendorPrice));
        WaitUtilsWebDriver.waitForTextToBePresentInElement(detailsWebPageNew.serviceVendorDropDown(service), vendorPrice);
    }

    public static void setServiceTechnician(String service, String technician) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.serviceTechnicianDropDown(service));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        ConditionWaiter.create(__ -> detailsWebPageNew.dropDownOption(technician).isEnabled()).execute();
        Utils.clickWithJS(detailsWebPageNew.dropDownOption(technician));
        ConditionWaiter.create(__ -> Utils.getText(detailsWebPageNew.serviceTechnicianDropDown(service)).equals(technician)).execute();
    }

    public static String getPhaseTotalPrice(String phase) {

        return Utils.getText(new VNextBORODetailsWebPageNew().phaseTotalPrice(phase));
    }

    public static void changeOrderStatus(String newStatus) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.getOrderStatusDropDown());
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(detailsWebPageNew.dropDownOption(newStatus)));
        Utils.clickWithJS(detailsWebPageNew.dropDownOption(newStatus));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void reopenOrderIfNeeded() {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        if (Utils.getText(detailsWebPageNew.getOrderStatusDropDown()).equals("Closed"))
            Utils.clickElement(detailsWebPageNew.getReopenOrderButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static String getOrderStatus() {

        return (Utils.getText(new VNextBORODetailsWebPageNew().getOrderStatusDropDown()));
    }

    public static void closeOrderWithReason(String reason) {

        changeOrderStatus("Closed");
        VNextBOCloseRODialogStepsNew.closeOrderWithReason(reason);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void startServicesForPhase(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        Utils.clickElement(detailsWebPageNew.getStartServicesActionButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void startServiceByServiceName(String phase, String service) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        expandPhaseByName(phase);
        Utils.clickElement(detailsWebPageNew.startServiceButtonForService(service));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        collapsePhaseByName(phase);
    }

    public static void resetStartDateIfNeededByServiceName(String phase, String service) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        if (Utils.isElementDisplayed(detailsWebPageNew.getResetStartDateActionButton())) {
            Utils.clickElement(detailsWebPageNew.getResetStartDateActionButton());
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        } else Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));

    }

    public static void changePriority(String newPriority) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.getPriorityDropDown());
        Utils.clickWithJS(detailsWebPageNew.dropDownOption(newPriority));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void addService(VNextBOMonitorData serviceData) {

        openAddNewServiceDialog();
        VNextBOAddNewServiceDialogSteps.addService(serviceData);
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        Utils.refreshPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void addServiceWithoutSaveXIcon(VNextBOMonitorData serviceData) {

        openAddNewServiceDialog();
        VNextBOAddNewServiceDialogSteps.populateServiceDataAndClickXIcon(serviceData);
    }

    public static void addServiceWithoutSaveCancelButton(VNextBOMonitorData serviceData) {

        openAddNewServiceDialog();
        VNextBOAddNewServiceDialogSteps.populateServiceDataAndClickCancelButton(serviceData);
    }

    public static void addLaborService(VNextBOMonitorData serviceData) {

        openAddNewServiceDialog();
        VNextBOAddNewServiceDialogSteps.addLaborService(serviceData);
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        Utils.refreshPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void addPartService(VNextBOMonitorData serviceData) {

        openAddNewServiceDialog();
        VNextBOAddNewServiceDialogSteps.addPartService(serviceData);
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        Utils.refreshPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void openAddNewServiceDialog() {

        ConditionWaiter.create(__ -> new VNextBORODetailsWebPageNew().getAddServiceButton().isEnabled()).execute();
        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddServiceButton());
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static int getPartServicesAmount() {

        return new VNextBORODetailsWebPageNew().getPartServicesNamesList().size();
    }

    public static void changeFlag(String flagTitle, String flagColor) {

        VNextBORODetailsWebPageNew detailsPage = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsPage.getFlagIcon());
        Utils.clickElement(detailsPage.flagColorIconByFlagTitle(flagTitle));
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.attributeContains(detailsPage.getFlagIcon(), "style", flagColor));
    }

    public static void openNotesForService(String service) {

        VNextBORODetailsWebPageNew detailsPage = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsPage.actionsMenuButtonForService(service));
        Utils.clickElement(detailsPage.getNotesActionButton());
        WaitUtilsWebDriver.waitForVisibility(new VNextBONotesDialogNew().getNotesDialog());
    }

    public static void addNoteForService(String service, String noteText, boolean saveNote) {

        openNotesForService(service);
        if (saveNote) VNextBONotesDialogStepsNew.addNote(noteText, true);
        else VNextBONotesDialogStepsNew.addNote(noteText, false);
    }

    public static void openChangeTechnicianDialogForPhase(String phase) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().changeTechnicianForPhase(phase));
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(new VNextBOChangeTechnicianDialogNew().getCancelButton()));
    }

    public static void openLogInfo() {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getLogInfoButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void seeMoreInformationForOrder() {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getMoreInfoSection());
    }

    public static void turnOnOffPhaseEnforcement(boolean turnOn) {

        if (turnOn) Utils.clickElement(new VNextBORODetailsWebPageNew().getPhaseEnforcementOnButton());
        else Utils.clickElement(new VNextBORODetailsWebPageNew().getPhaseEnforcementOffButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void addNewTask(VNextBOMonitorData taskData, boolean requiredFieldsOnly, boolean saveTask) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddNewTaskButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        if (requiredFieldsOnly) VNextBOAddNewTaskDialogSteps.addNewTaskWithRequiredFields(taskData);
        else VNextBOAddNewTaskDialogSteps.addNewTaskWithAllFields(taskData, saveTask);
    }

    public static void addNewTaskWithPredefinedTechnician(VNextBOMonitorData taskData) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddNewTaskButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOAddNewTaskDialogSteps.addNewTaskWithPredefinedTechnician(taskData);
    }

    public static void openTimeReporting() {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getTimeReportingIcon());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
