package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.ProblemReasonPage;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class ProblemReportingSteps {
    public static void setProblemReason(String problemReason) {
        ProblemReasonPage problemReasonPage = new ProblemReasonPage();

        WaitUtils.elementShouldBeVisible(problemReasonPage.getRootElement(), true);
        problemReasonPage.getProblemEditBox().clear();
        problemReasonPage.getProblemEditBox().sendKeys(problemReason);
        problemReasonPage.getCompleteButton().click();
    }

    public static void resolveProblem() {
        ProblemReasonPage problemReasonPage = new ProblemReasonPage();

        WaitUtils.elementShouldBeVisible(problemReasonPage.getRootElement(), true);
        WaitUtils.elementShouldBeVisible(problemReasonPage.getCompleteButton(), true);
        WaitUtils.elementShouldBeVisible(problemReasonPage.getProblemEditBox(), true);
        WaitUtils.click(problemReasonPage.getCompleteButton());
    }
}
