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
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(reportProblemDialog.getProblemReasonDropDown()));
        Select reportProblemSelect = new Select(reportProblemDialog.getProblemReasonDropDown());
        reportProblemSelect.selectByVisibleText(problemReason);
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickWithJS(reportProblemDialog.getAddButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void reportProblemWithDescription(String problemReason, String problemDescription) {

        VNextBOROReportProblemDialogNew reportProblemDialog = new VNextBOROReportProblemDialogNew();
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(reportProblemDialog.getProblemReasonDropDown()));
        Select reportProblemSelect = new Select(reportProblemDialog.getProblemReasonDropDown());
        reportProblemSelect.selectByVisibleText(problemReason);
        Utils.clearAndType(reportProblemDialog.getProblemReasonDescription(), String.valueOf(problemDescription));
        Utils.clickWithJS(reportProblemDialog.getAddButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
