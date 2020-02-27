package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialog;

public class VNextBOPartAddNewDocumentDialogInteractions {

    public static void setDocumentType(String documentType) {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.clickElement(addNewDocumentDialog.getTypeField());
        Utils.selectOptionInDropDownWithJs(addNewDocumentDialog.getTypeDropDown(), addNewDocumentDialog.getTypeListBox(), documentType);
    }

    public static void setDocumentNumber(String documentNumber) {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.clearAndType(addNewDocumentDialog.getNumberField(), documentNumber);
    }

    public static void setDocumentNotes(String documentNotes) {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.clearAndType(addNewDocumentDialog.getNotesField(), documentNotes);
    }

    public static void setDocumentAmount(String documentAmount) {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.sendKeys(addNewDocumentDialog.getAmountField(), documentAmount);
    }

    public static void setDocumentCurrentDate() {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.clickElement(addNewDocumentDialog.getDateButton());
        WaitUtilsWebDriver.waitForAttributeToContain(addNewDocumentDialog.getDatePicker(), "class", "k-state-active");
        Utils.clickElement(addNewDocumentDialog.getTodayDateButton());
        WaitUtilsWebDriver.waitForAttributeNotToContain(addNewDocumentDialog.getDatePicker(), "class", "k-state-active", 4);
    }

    public static void setDocumentCurrentDueDate() {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        Utils.clickElement(addNewDocumentDialog.getDueDateButton());
        WaitUtilsWebDriver.waitForAttributeToContain(addNewDocumentDialog.getDueDatePicker(), "class", "k-state-active");
        Utils.clickElement(addNewDocumentDialog.getTodayDueDateButton());
        WaitUtilsWebDriver.waitForAttributeNotToContain(addNewDocumentDialog.getDueDatePicker(), "class", "k-state-active", 4);
    }

    public static void setDocumentAttachment(String attachment) {
        final VNextBOPartAddNewDocumentDialog addNewDocumentDialog = new VNextBOPartAddNewDocumentDialog();
        WaitUtilsWebDriver.waitForVisibility(addNewDocumentDialog.getAttachmentField());
        addNewDocumentDialog.getAttachmentField().sendKeys(attachment);
    }

    public static void clickSaveButton() {
        Utils.clickElement(new VNextBOPartAddNewDocumentDialog().getSaveButton());
    }

    public static void clickXIcon() {
        Utils.clickElement(new VNextBOPartAddNewDocumentDialog().getXIcon());
    }
}
