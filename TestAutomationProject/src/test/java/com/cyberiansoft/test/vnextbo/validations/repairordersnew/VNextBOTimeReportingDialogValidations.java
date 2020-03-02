package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOTimeReportingDialog;
import org.testng.Assert;

public class VNextBOTimeReportingDialogValidations {

    public static void verifyTimeReportingDialogIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOTimeReportingDialog().getDialogContent()),
                "Time reporting dialog hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(new VNextBOTimeReportingDialog().getDialogContent()),
                "Time reporting dialog has been displayed");
    }

    public static void verifyStartedOnlyCheckBox(boolean shouldBeChecked) {

        if (shouldBeChecked) Assert.assertEquals(new VNextBOTimeReportingDialog().getStartedOnlyCheckBox().getAttribute("checked"), "true",
                "Started only checkbox hasn't been checked");
        else Assert.assertEquals(new VNextBOTimeReportingDialog().getStartedOnlyCheckBox().getAttribute("checked"), "false",
                "Started only checkbox hasn't been unchecked");
    }

    public static void verifyStopDatesAreCorrect(String expectedStopDate) {

        Assert.assertTrue(new VNextBOTimeReportingDialog().getEndDatesList().stream().allMatch(endDate -> Utils.getInputFieldValue(endDate).equals(expectedStopDate)),
                "Not all services have had correct stop date");
    }

    public static void verifyTechniciansAreCorrect(String expectedTechnicians) {

        Assert.assertTrue(new VNextBOTimeReportingDialog().getTechniciansList().stream().allMatch(technician -> Utils.getText(technician).equals(expectedTechnicians)),
                "Not all services have had correct technician");
    }

    public static void verifySavedRecordsNumberIsCorrect(int expectedRecordsNumber) {

        Assert.assertTrue(new VNextBOTimeReportingDialog().getSavedTimeRecords().size() == expectedRecordsNumber,
                "Saved records number hasn't been added");
    }

    public static void verifyNotSavedRecordIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(new VNextBOTimeReportingDialog().getNotSavedTimeRecords().size() == 1,
                "New record hasn't been added");
        else Assert.assertTrue(new VNextBOTimeReportingDialog().getNotSavedTimeRecords().size() == 0,
                "New record hasn't been deleted");
    }
}
