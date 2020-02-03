package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROCompleteCurrentPhaseDialogNew;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBORODetailsWebPageNew;
import org.openqa.selenium.Keys;
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
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
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
            Utils.clickWithJS(new VNextBORODetailsWebPageNew().statusDropDownOption(expectedStatus));
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        }
    }

    public static void setServiceStatusIfNeeded(String service, String expectedStatus) {

        if (Utils.getText(new VNextBORODetailsWebPageNew().serviceStatusDropDownByService(service)).equals("Problem")) {
            resolveProblemForService(service);
        }
        if (!Utils.getText(new VNextBORODetailsWebPageNew().serviceStatusDropDownByService(service)).equals(expectedStatus)) {
            Utils.clickElement(new VNextBORODetailsWebPageNew().serviceStatusDropDownByService(service));
            Utils.clickWithJS(new VNextBORODetailsWebPageNew().statusDropDownOption(expectedStatus));
            WaitUtilsWebDriver.waitForPageToBeLoaded();
            WaitUtilsWebDriver.waitABit(3000);
        }
    }

    public static void setServiceQuantity(String service, String quantity) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.serviceQtyInputField(service));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clearAndType(detailsWebPageNew.serviceQtyInputField(service), quantity);
        Utils.clickElement(detailsWebPageNew.serviceNameWebElement(service));
    }

    public static void setServicePrice(String service, String price) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.servicePriceInputField(service));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clearAndType(detailsWebPageNew.servicePriceInputField(service), price);
        Utils.clickElement(detailsWebPageNew.serviceNameWebElement(service));
    }

    public static void setServiceVendorPrice(String service, String vendorPrice) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        Utils.clickElement(detailsWebPageNew.serviceVendorPriceInputField(service));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.sendKeysWithJS(detailsWebPageNew.serviceVendorPriceInputField(service), vendorPrice);
        Utils.clickElement(detailsWebPageNew.serviceNameWebElement(service));
    }

    public static String getPhaseTotalPrice(String phase) {

        return Utils.getText(new VNextBORODetailsWebPageNew().phaseTotalPrice(phase));
    }
}
