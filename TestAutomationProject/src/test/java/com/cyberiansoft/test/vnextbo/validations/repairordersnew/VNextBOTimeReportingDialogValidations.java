package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
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

        Assert.assertTrue(new VNextBOTimeReportingDialog().getSavedRecordsStopDatesList().stream().allMatch(endDate -> Utils.getInputFieldValue(endDate).equals(expectedStopDate)),
                "Not all services have had correct stop date");
    }

    public static void verifyTechniciansAreCorrect(String expectedTechnicians) {

        Assert.assertTrue(new VNextBOTimeReportingDialog().getSavedRecordsTechniciansList().stream().allMatch(technician -> Utils.getText(technician).equals(expectedTechnicians)),
                "Not all services have had correct technician");
    }

    public static void verifySavedRecordsNumberIsCorrect(int expectedRecordsNumber) {

        ConditionWaiter.create(__ -> new VNextBOTimeReportingDialog().getSavedTimeRecordsList().size() == expectedRecordsNumber).execute();
        Assert.assertTrue(new VNextBOTimeReportingDialog().getSavedTimeRecordsList().size() == expectedRecordsNumber,
                "Saved records number hasn't been correct");
    }

    public static void verifyNotSavedRecordIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(new VNextBOTimeReportingDialog().getNotSavedTimeRecordsList().size() == 1,
                "New record hasn't been added");
        else Assert.assertTrue(new VNextBOTimeReportingDialog().getNotSavedTimeRecordsList().size() == 0,
                "New record hasn't been deleted");
    }

    public static void verifyStartedDateFieldIsHighLighted() {

        Assert.assertTrue(new VNextBOTimeReportingDialog().getNotSavedRecordStartDateDatePicker().getCssValue("border").equals("1px solid rgb(237, 28, 36)"),
                "Start date field hasn't been highlighted with red border");
    }

    public static void verifyTechnicianFieldIsHighLighted() {

        Assert.assertTrue(new VNextBOTimeReportingDialog().getNotSavedRecordTechnicianFieldWithBorder().getCssValue("border").equals("1px solid rgb(237, 28, 36)"),
                "Technician field hasn't been highlighted with red border");
    }

    public static void verifyTimerIconIsDisplayedByRecordNumber(int recordNumber, boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOTimeReportingDialog().getSavedRecordsTimerIconsList().get(recordNumber)),
                "Timer icon hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(new VNextBOTimeReportingDialog().getSavedRecordsTimerIconsList().get(recordNumber)),
                "Timer icon has been displayed");
    }

    public static void verifyTotalSumForServiceIsCorrect(String serviceName) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        double totalTimeForService = Double.parseDouble(Utils.getText(timeReportingDialog.totalTimeByServiceName(serviceName)).replace("hr", "").trim());
        double calculatedTotalTime = 0;
        for (int i = 0; i < 2; i++) {
            calculatedTotalTime += Double.parseDouble(Utils.getText(timeReportingDialog.getTimeRecordsWithTime().get(i)).replace("hr", "").trim());
        }
        Assert.assertEquals(calculatedTotalTime, totalTimeForService, "Total time for service " + serviceName + "hasn't been correct");
    }

    public static void verifyStartDateIsCorrectByRecordNumber(int recordNumber, String expectedDate) {

        ConditionWaiter.create(__ -> Utils.getInputFieldValue(new VNextBOTimeReportingDialog().getSavedRecordsStartDatesList().get(recordNumber)).contains(expectedDate)).execute();
        Assert.assertTrue(Utils.getInputFieldValue(new VNextBOTimeReportingDialog().getSavedRecordsStartDatesList().get(recordNumber)).contains(expectedDate),
                "Start date hasn't been correct");
    }

    public static void verifyStopDateIsCorrectByRecordNumber(int recordNumber, String expectedDate) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOTimeReportingDialog().getSavedRecordsStopDatesList().get(recordNumber)), expectedDate,
                "Stop date hasn't been correct");
    }

    public static void verifyTechnicianIsCorrectByRecordNumber(int recordNumber, String expectedTechnician) {

        Assert.assertEquals(Utils.getText(new VNextBOTimeReportingDialog().getSavedRecordsTechniciansList().get(recordNumber)), expectedTechnician,
                "Technician hasn't been correct");
    }
}
