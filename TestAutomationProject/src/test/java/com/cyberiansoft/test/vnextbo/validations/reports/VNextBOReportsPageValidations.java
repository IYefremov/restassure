package com.cyberiansoft.test.vnextbo.validations.reports;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.reports.VNextBOReportsPage;
import org.testng.Assert;

import java.util.List;

public class VNextBOReportsPageValidations {

    public static void verifyReportsColumnsAreDisplayed() {
        final VNextBOReportsPage reportsPage = new VNextBOReportsPage();
        Assert.assertTrue(Utils.isElementDisplayed(reportsPage.getTableHeaderReport()),
                "The 'Report' table column hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(reportsPage.getTableHeaderAction()),
                "The 'Action' table column hasn't been displayed");
    }

    public static void verifyFileContainsText(String fileText, String text) {
        Assert.assertTrue(fileText.contains(text), "The *.pdf file doesn't contain the '" + text + "' text");
    }

    public static void verifyReportsAreDisplayed() {
        final List<String> reports = Utils.getText(new VNextBOReportsPage().getReportOptions());
        reports.forEach(report -> Assert.assertNotEquals(report, "", "The report options haven't been displayed"));
    }
}
