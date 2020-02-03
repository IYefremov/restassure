package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROReportProblemDialogNew;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.Optional;

public class VNextBOROReportProblemDialogStepsNew {

    public static void reportProblemWithoutDescription(String problemReason) {

        VNextBOROReportProblemDialogNew reportProblemDialog = new VNextBOROReportProblemDialogNew();
        WaitUtilsWebDriver.waitABit(3000);
        new Select(reportProblemDialog.getProblemReasonDropDown()).selectByVisibleText(problemReason);
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickWithActions(reportProblemDialog.getAddButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void reportProblemWithDescription(String problemReason, String problemDescription) {

        VNextBOROReportProblemDialogNew reportProblemDialog = new VNextBOROReportProblemDialogNew();
        WaitUtilsWebDriver.waitABit(3000);
        new Select(reportProblemDialog.getProblemReasonDropDown()).selectByVisibleText(problemReason);
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clearAndType(reportProblemDialog.getProblemReasonDescription(), String.valueOf(problemDescription));
        Utils.clickWithActions(reportProblemDialog.getAddButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
