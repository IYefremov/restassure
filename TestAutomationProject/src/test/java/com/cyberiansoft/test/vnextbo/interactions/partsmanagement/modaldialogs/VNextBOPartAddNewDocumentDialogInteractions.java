package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
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
        Utils.clearAndTypeWithJS(addNewDocumentDialog.getAmountField(), documentAmount);
    }

    public static void clickSaveButton() {
        Utils.clickElement(new VNextBOPartAddNewDocumentDialog().getSaveButton());
    }
}
