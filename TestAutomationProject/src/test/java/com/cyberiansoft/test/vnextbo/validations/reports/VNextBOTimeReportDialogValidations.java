package com.cyberiansoft.test.vnextbo.validations.reports;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.reports.VNextBOTimeReportDialog;
import com.cyberiansoft.test.vnextbo.steps.reports.VNextBOTimeReportDialogSteps;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.testng.Assert;

public class VNextBOTimeReportDialogValidations {

    public static void verifyTimeReportDialogIsDisplayed() {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOTimeReportDialog().getDialogHeader()),
                "The Time report dialog hasn't been opened");
    }

    public static void verifyTimeReportDialogIsClosed() {
        Assert.assertTrue(Utils.isElementInvisible(new VNextBOTimeReportDialog().getDialogHeader()),
                "The Time report dialog hasn't been closed");
    }

    public static void verifyEmailIsDisplayed(String email) {
        Assert.assertEquals(VNextBOTimeReportDialogSteps.getEmailValue(), email, "The email hasn't been displayed");
    }

    public static void verifyFromFieldValidationErrorIsDisplayed(String error) {
        Assert.assertEquals(Utils.getText(new VNextBOTimeReportDialog().getFromDateValidationError()), error,
                "The 'From' field validation error hasn't been displayed");
    }

    public static void verifyEmailErrorMessageIsDisplayed() {
        Assert.assertEquals(Utils.getText(new VNextBOTimeReportDialog().getEmailErrorMessage()),
                VNextBOAlertMessages.USE_VALID_EMAIL, "The error message hasn't been displayed");
    }

    public static void verifyPhoneErrorMessageIsDisplayed() {
        Assert.assertEquals(Utils.getText(new VNextBOTimeReportDialog().getPhoneErrorMessage()),
                VNextBOAlertMessages.USE_VALID_PHONE, "The error message hasn't been displayed");
    }

    public static void verifyTimeZone(String expectedTimeZone) {
        Assert.assertEquals(VNextBOTimeReportDialogSteps.getTimeZone(), expectedTimeZone,
                "The time zone hasn't been set properly");
    }
}
