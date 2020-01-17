package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROReportProblemDialog;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROResolveProblemDialog;
import org.openqa.selenium.support.ui.Select;


public class VNextBOROProblemsInteractions {

    public static void setReportProblem(String problem) {
        final VNextBOROReportProblemDialog reportProblemDialog = new VNextBOROReportProblemDialog();
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(reportProblemDialog.getProblemReason());
        new Select(reportProblemDialog.getProblemReason()).selectByVisibleText(problem);
    }

    public static void setProblemDescription(String description) {
        Utils.clearAndType(new VNextBOROReportProblemDialog().getProblemReasonDescription(), description);
    }

    public static void clickAddProblemButton() {
        final VNextBOROReportProblemDialog reportProblemDialog = new VNextBOROReportProblemDialog();
        Utils.clickElement(reportProblemDialog.getProblemReasonAddButton());
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForInvisibility(reportProblemDialog.getReportProblemDialog(), 5);
    }

    public static void clickCloseResolveProblemDialogButton() {
        Utils.clickElement(new VNextBOROResolveProblemDialog().getResolveProblemCloseButton());
    }

    public static void clickResolveButton() {
        final VNextBOROResolveProblemDialog resolveProblemDialog = new VNextBOROResolveProblemDialog();
        if (Utils.isElementDisplayed(resolveProblemDialog.getResolveProblemButton(), 3)) {
            Utils.clickElement(resolveProblemDialog.getResolveProblemButton());
            WaitUtilsWebDriver.waitForInvisibility(resolveProblemDialog.getResolveProblemDialog(), 5);
            WaitUtilsWebDriver.waitABit(2500);
        }
    }

    public static void resolveProblem() {
        final VNextBOROResolveProblemDialog resolveProblemDialog = new VNextBOROResolveProblemDialog();
        if (WaitUtilsWebDriver.elementShouldBeVisible(resolveProblemDialog.getResolveProblemButton(), true, 3)) {
            Utils.clickElement(resolveProblemDialog.getResolveProblemButton());
            WaitUtilsWebDriver.waitForInvisibility(resolveProblemDialog.getResolveProblemDialog(), 5);
            WaitUtilsWebDriver.waitABit(2500);
        }
    }
}