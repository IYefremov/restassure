package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBODocumentData;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogValidations;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOPartAddNewDocumentDialogSteps {

    public static void setDocumentFields(VNextBODocumentData data) {
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentType(data.getType());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(data.getNumber());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNotes(data.getNotes());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentCurrentDate();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentCurrentDueDate();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentAmount(data.getAmount());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentAttachment(data.getAttachment());
    }

    public static void saveDocumentFields() {
        VNextBOPartAddNewDocumentDialogInteractions.clickSaveButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded(4);
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(false);
    }

    public static void saveEmptyDocumentFields() {
        VNextBOPartAddNewDocumentDialogInteractions.clickSaveButton();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
    }

    public static void closeDialogWithXIcon() {
        VNextBOPartAddNewDocumentDialogInteractions.clickXIcon();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(false);
    }

    public static String getTypeFieldValue() {
        return Utils.getText(new VNextBOPartAddNewDocumentDialog().getTypeFieldWithValue());
    }

    public static String getNotesFieldValue() {
        return Utils.getInputFieldValue(new VNextBOPartAddNewDocumentDialog().getNotesField());
    }

    public static String getDateFieldValue() {
        return Utils.getInputFieldValue(new VNextBOPartAddNewDocumentDialog().getDateField());
    }

    public static String getDueDateFieldValue() {
        return Utils.getInputFieldValue(new VNextBOPartAddNewDocumentDialog().getDueDateField());
    }

    public static String getAmountFieldValue() {
        return Utils.getInputFieldValue(new VNextBOPartAddNewDocumentDialog().getAmountField())
                .replace("$", "")
                .replaceAll(",", "")
                .replaceAll("[.]\\d{2}", "");
    }

    public static String getAttachmentValue() {
        return Utils.getText(new VNextBOPartAddNewDocumentDialog().getAttachmentValue());
    }

    public static List<String> getProvidersList() {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.clickElement(addNewDocumentDialog.getProviderField());
        WaitUtilsWebDriver.waitABit(1000);
        return Utils.getText(addNewDocumentDialog.getOpenedListBox());
    }

    public static List<String> getDocumentNumbersList() {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        List<String> numbers;
        Utils.clickElement(addNewDocumentDialog.getNumberInputFieldArrow());
        numbers = Utils.getText(addNewDocumentDialog.getOpenedListBox(), 2);
        Utils.clickElement(addNewDocumentDialog.getNumberLabel());
        return numbers;
    }

    public static void waitForNotesFieldToBecomeDisabled() {
        try {
            WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                            VNextBOPartAddNewDocumentDialogValidations.isNotesFieldDisabled());
        } catch (Exception ignored) {}
    }
}
