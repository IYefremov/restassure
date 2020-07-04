package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBODocumentData;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialog;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogSteps;
import org.testng.Assert;

public class VNextBOPartAddNewDocumentDialogValidations {

    public static void verifyAddNewDocumentDialogIsOpened(boolean opened) {
        final boolean visible = WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartAddNewDocumentDialog().getAddNewDocumentDialog(), opened, 2);
        Assert.assertTrue(visible, "The 'Add New Document' dialog hasn't been opened/closed");
    }

    public static boolean isWarningMessageDisplayed() {
        return WaitUtilsWebDriver.waitForAttributeToContain(
                new VNextBOPartAddNewDocumentDialog().getWarningMessage(), "class", "invalid", 3);
    }

    public static boolean isSaveButtonDisabled() {
        return Utils.attributeContains(new VNextBOPartAddNewDocumentDialog().getSaveButton(), "disabled", "true");
    }

    public static void verifySavedButtonIsDisabled() {
        Assert.assertTrue(isSaveButtonDisabled(), "The 'Save' button isn't disabled");
    }

    public static void verifyTypeFieldValue(String expectedValue) {
        Assert.assertEquals(VNextBOPartAddNewDocumentDialogSteps.getTypeFieldValue(), expectedValue,
                "The document 'Type' value isn't set properly");
    }

    public static void verifyNotesFieldValue(String expectedValue) {
        Assert.assertEquals(VNextBOPartAddNewDocumentDialogSteps.getNotesFieldValue(), expectedValue,
                "The document 'Notes' value isn't set properly");
    }

    public static void verifyDateFieldIsNotEmpty() {
        Assert.assertNotEquals(VNextBOPartAddNewDocumentDialogSteps.getDateFieldValue(), "",
                "The document 'Date' value is empty");
    }

    public static void verifyDueDateFieldIsNotEmpty() {
        Assert.assertNotEquals(VNextBOPartAddNewDocumentDialogSteps.getDueDateFieldValue(), "",
                "The document 'Due Date' value is empty");
    }

    public static void verifyAmountFieldValue(String expectedValue) {
        Assert.assertEquals(VNextBOPartAddNewDocumentDialogSteps.getAmountFieldValue(), expectedValue,
                "The document 'Amount' value isn't set properly");
    }

    public static void verifyAttachmentValue(String expectedValue) {
        Assert.assertEquals(VNextBOPartAddNewDocumentDialogSteps.getAttachmentValue(), expectedValue,
                "The file attachment value hasn't been displayed");
    }

    public static boolean isNotesFieldDisabled() {
        return Utils.attributeEquals(new VNextBOPartAddNewDocumentDialog().getNotesField(), "disabled", "true");
    }

    public static boolean isDateFieldDisabled() {
        return Utils.attributeEquals(new VNextBOPartAddNewDocumentDialog().getDateField(), "disabled", "true");
    }

    public static boolean isDueDateFieldDisabled() {
        return Utils.attributeEquals(new VNextBOPartAddNewDocumentDialog().getDueDateField(), "disabled", "true");
    }

    public static boolean isAmountFieldDisabled() {
        return Utils.attributeEquals(new VNextBOPartAddNewDocumentDialog().getAmountField(), "disabled", "true");
    }

    public static void verifyFieldsValuesAfterTheDocumentField(VNextBODocumentData data) {
        verifyNotesFieldValue(data.getNotes());
        verifyDateFieldIsNotEmpty();
        verifyDueDateFieldIsNotEmpty();
        verifyAmountFieldValue(data.getAmount());
        verifyAttachmentValue(data.getDisplayedAttachmentName());
    }

    public static void verifyFieldsAfterTheDocumentNumberAreDisabled() {
        Assert.assertTrue(isNotesFieldDisabled(), "The 'Notes' field hasn't been disabled");
        Assert.assertTrue(isDateFieldDisabled(), "The 'Date' field hasn't been disabled");
        Assert.assertTrue(isDueDateFieldDisabled(), "The 'Due date' field hasn't been disabled");
        Assert.assertTrue(isAmountFieldDisabled(), "The 'Amount' field hasn't been disabled");
    }
}
