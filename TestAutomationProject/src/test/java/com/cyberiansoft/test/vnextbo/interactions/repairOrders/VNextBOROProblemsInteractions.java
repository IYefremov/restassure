package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROReportProblemDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROResolveProblemDialog;
import org.openqa.selenium.support.ui.Select;


public class VNextBOROProblemsInteractions {

    private VNextBOROReportProblemDialog reportProblemDialog;
    private VNextBOROResolveProblemDialog resolveProblemDialog;

    public VNextBOROProblemsInteractions() {
        reportProblemDialog = new VNextBOROReportProblemDialog();
        resolveProblemDialog = new VNextBOROResolveProblemDialog();
    }

    public void setReportProblem(String problem) {
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(reportProblemDialog.getProblemReason());
        new Select(reportProblemDialog.getProblemReason()).selectByVisibleText(problem);
    }

    public void setProblemDescription(String description) {
        Utils.clearAndType(reportProblemDialog.getProblemReasonDescription(), description);
    }

    public void clickAddProblemButton() {
        Utils.clickElement(reportProblemDialog.getProblemReasonAddButton());
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForInvisibility(reportProblemDialog.getReportProblemDialog(), 5);
    }

    public void clickCloseResolveProblemButton() {
        Utils.clickElement(resolveProblemDialog.getResolveProblemCloseButton());
    }

    public void clickResolveButton() {
        Utils.clickElement(resolveProblemDialog.getResolveProblemButton());
        try {
            WaitUtilsWebDriver.waitForInvisibility(resolveProblemDialog.getResolveProblemDialog(), 5);
        } catch (Exception e) {
            // TODO bug #93678 delete catch block after fix
            clickCloseResolveProblemButton();
            Utils.refreshPage();
            WaitUtilsWebDriver.waitForInvisibility(resolveProblemDialog.getResolveProblemDialog(), 5);
        }
    }
}