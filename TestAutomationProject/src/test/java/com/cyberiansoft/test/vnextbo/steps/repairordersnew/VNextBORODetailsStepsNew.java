package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBORODetailsWebPageNew;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VNextBORODetailsStepsNew {

    public static List<String> getServiceAndTaskDescriptionsList() {

        final List<WebElement> serviceAndTaskDescriptionsList = new VNextBORODetailsWebPageNew().getServiceAndTaskDescriptionsList();
        return serviceAndTaskDescriptionsList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static void expandAllServiceRows() {

        for (WebElement expander : new VNextBORODetailsWebPageNew().getServiceExpanderList()) {
            Utils.clickElement(expander);
            WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        }
    }

    public static void reportProblemOnPhaseLevelWithoutDescription(String phase, String problemReason) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickWithJS(detailsWebPageNew.actionsMenuButtonByPhase(phase));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickWithJS(detailsWebPageNew.getReportProblemActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithoutDescription(problemReason);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void reportProblemOnPhaseLevelWithDescription(String phase, String problemReason, String problemDescription) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickWithJS(detailsWebPageNew.actionsMenuButtonByPhase(phase));
        Utils.clickWithJS(detailsWebPageNew.getReportProblemActionButton());
        VNextBOROReportProblemDialogStepsNew.reportProblemWithDescription(problemReason, problemDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void resolveProblemOnPhaseLevel(String phase) {

        VNextBORODetailsWebPageNew detailsWebPageNew = new VNextBORODetailsWebPageNew();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickWithJS(detailsWebPageNew.actionsMenuButtonByPhase(phase));
        Utils.clickWithJS(detailsWebPageNew.getResolveProblemActionButton());
        VNextBOROResolveProblemDialogStepsNew.resolveProblemWithoutDescription();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void setPhaseStatusIfNeeded(String phase, String expectedStatus) {

        if (Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals("Problem")) {
            resolveProblemOnPhaseLevel(phase);
        }
        if (!Utils.getText(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase)).equals(expectedStatus)) {
            Utils.clickElement(new VNextBORODetailsWebPageNew().phaseStatusDropDownByPhase(phase));
            Utils.clickElement(new VNextBORODetailsWebPageNew().phaseStatusDropDownOption(expectedStatus));
        }
    }
}
