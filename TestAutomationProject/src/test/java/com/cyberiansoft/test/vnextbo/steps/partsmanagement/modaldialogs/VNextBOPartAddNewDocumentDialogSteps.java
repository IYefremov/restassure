package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBODocumentData;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogInteractions;

public class VNextBOPartAddNewDocumentDialogSteps {

    public static void setDocumentFields(VNextBODocumentData data) {
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentType(data.getType());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(data.getNumber());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNotes(data.getNotes());
        VNextBOPartAddNewDocumentDialogInteractions.clickSaveButton();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
    }
}
