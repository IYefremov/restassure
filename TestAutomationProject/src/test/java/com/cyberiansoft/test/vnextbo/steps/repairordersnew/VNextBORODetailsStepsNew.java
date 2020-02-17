package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.*;
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

        Utils.clickElement(new VNextBORODetailsWebPageNew().expandPhaseButton(phase));
        WaitUtilsWebDriver.waitABit(2000);
    }

    public static void collapsePhaseByName(String phase) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().collapsePhaseButton(phase));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }

    public static void reportProblemOnPhaseLevelWithoutDescription(String phase, String problemReason) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        Utils.clickElement(detailsWebPageNew.getReportProblemForPhaseActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithoutDescription(problemReason);
        WaitUtilsWebDriver.waitABit(3000);
    }

    public static void reportProblemForServiceWithoutDescription(String service, String problemReason) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        Utils.clickElement(detailsWebPageNew.getReportProblemForServiceActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithoutDescription(problemReason);
        WaitUtilsWebDriver.waitABit(3000);
    }

    public static void openCompleteCurrentPhaseDialog(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        Utils.clickElement(detailsWebPageNew.getCompleteCurrentPhaseActionButton());
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.visibilityOf(new VNextBOROCompleteCurrentPhaseDialogNew().getCompleteCurrentPhaseDialog()));
    }

    public static void reportProblemOnPhaseLevelWithDescription(String phase, String problemReason, String problemDescription) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        Utils.clickElement(detailsWebPageNew.getReportProblemForPhaseActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithDescription(problemReason, problemDescription);
        WaitUtilsWebDriver.waitABit(3000);
    }

    public static void reportProblemForServiceWithDescription(String service, String problemReason, String problemDescription) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        Utils.clickElement(detailsWebPageNew.getReportProblemForServiceActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithDescription(problemReason, problemDescription);
        WaitUtilsWebDriver.waitABit(3000);
    }

    public static void resolveProblemOnPhaseLevel(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForPhase(phase));
        Utils.clickElement(detailsWebPageNew.getResolveProblemForPhaseActionButton());
        VNextBOROResolveProblemDialogStepsNew.resolveProblem();
        WaitUtilsWebDriver.waitABit(3000);
    }

    public static void resolveProblemForService(String service) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.actionsMenuButtonForService(service));
        Utils.clickElement(detailsWebPageNew.getResolveProblemForServiceActionButton());
        VNextBOROResolveProblemDialogStepsNew.resolveProblem();
        WaitUtilsWebDriver.waitABit(3000);
    }

    public static void setPhaseStatusIfNeeded(String phase, String expectedStatus) {

        if (Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals("Problem")) {
            resolveProblemOnPhaseLevel(phase);
        }
        if (!Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals(expectedStatus)) {
            Utils.clickElement(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase));
            Utils.clickWithJS(new VNextBORODetailsWebPageNew().dropDownOption(expectedStatus));
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        }
    }

    public static void setServiceStatusIfNeeded(String service, String expectedStatus) {

        VNextBORODetailsWebPageNew detailsWebPage = new VNextBORODetailsWebPageNew();
        WaitUtilsWebDriver.waitABit(3000);
        if (Utils.getText(detailsWebPage.serviceStatusDropDownByService(service)).equals("Problem")) {
            resolveProblemForService(service);
        }
        if (!Utils.getText(detailsWebPage.serviceStatusDropDownByService(service)).equals(expectedStatus)) {
            Utils.clickElement(detailsWebPage.serviceStatusDropDownByService(service));
            Utils.clickWithJS(detailsWebPage.dropDownOption(expectedStatus));
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            WaitUtilsWebDriver.waitABit(3000);
        }
    }

    public static void setServiceQuantity(String service, String quantity) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
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
        WaitUtilsWebDriver.waitABit(1000);
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
        WaitUtilsWebDriver.waitABit(5000);
        Utils.clickWithJS(detailsWebPageNew.dropDownOption(technician));
        WaitUtilsWebDriver.waitForTextToBePresentInElement(detailsWebPageNew.serviceTechnicianDropDown(service), technician);
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

    public static void closeOrderWithCompletedReason() {

        changeOrderStatus("Closed");
        VNextBOCloseRODialogStepsNew.closeOrderWithCompletedReason();
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

    public static void changePriority(String newPriority) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.getPriorityDropDown());
        Utils.clickWithJS(detailsWebPageNew.dropDownOption(newPriority));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void addService(VNextBOMonitorData serviceData) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddServiceButton());
        VNextBOAddNewServiceDialogSteps.addService(serviceData);
        Utils.refreshPage();
    }

    public static void addServiceWithoutSaveXIcon(VNextBOMonitorData serviceData) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddServiceButton());
        VNextBOAddNewServiceDialogSteps.populateServiceDataAndClickXIcon(serviceData);
    }

    public static void addServiceWithoutSaveCancelButton(VNextBOMonitorData serviceData) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddServiceButton());
        VNextBOAddNewServiceDialogSteps.populateServiceDataAndClickCancelButton(serviceData);
    }

    public static void addLaborService(VNextBOMonitorData serviceData) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddServiceButton());
        VNextBOAddNewServiceDialogSteps.addLaborService(serviceData);
        Utils.refreshPage();
    }

    public static void addPartService(VNextBOMonitorData serviceData) {

        Utils.clickElement(new VNextBORODetailsWebPageNew().getAddServiceButton());
        VNextBOAddNewServiceDialogSteps.addPartService(serviceData);
        Utils.refreshPage();
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
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(new VNextBOChangeTechnicianDialogNew().getDialogContent()));
    }
}
