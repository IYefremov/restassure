package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBODocumentData;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogValidations;

public class VNextBOPartAddNewDocumentDialogSteps {

    public static void setDocumentFields(VNextBODocumentData data) {
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentType(data.getType());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(data.getNumber());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNotes(data.getNotes());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentCurrentDate();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentCurrentDueDate();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentAttachment(data.getAttachment());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentAmount(data.getAmount());
    }

    public static void saveDocumentFields() {
        VNextBOPartAddNewDocumentDialogInteractions.clickSaveButton();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
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
}
