package com.cyberiansoft.test.vnextbo.steps.reports;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.reports.VNextBOReportsPage;

public class VNextBOReportsPageSteps {

    public static void waitForReportsPageToBeLoaded() {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        final VNextBOReportsPage reportsPage = new VNextBOReportsPage();
        WaitUtilsWebDriver.waitForAttributeNotToContain(reportsPage.getReportsBlock(), "class", "hidden", 15);
        WaitUtilsWebDriver.elementShouldBeVisible(reportsPage.getReportTable(), true, 3);
    }

    public static void clickGenerateReportButton(String report) {
        final VNextBOReportsPage reportsPage = new VNextBOReportsPage();
        Utils.clickElement(reportsPage.getGenerateButtonForReport(report));
        WaitUtilsWebDriver.waitForPageToBeLoaded(2);
    }
}
