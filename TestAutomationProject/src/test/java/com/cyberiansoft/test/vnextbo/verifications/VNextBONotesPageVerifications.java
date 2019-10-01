package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOOrderServiceNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOEditNotesDialog;

public class VNextBONotesPageVerifications {

    private VNextBOOrderServiceNotesDialog orderServiceNotesDialog;

    public VNextBONotesPageVerifications() {
        orderServiceNotesDialog = new VNextBOOrderServiceNotesDialog();
    }

    public boolean isEditOrderServiceNotesBlockDisplayed() {
        return Utils.isElementDisplayed(orderServiceNotesDialog.getEditOrderServiceNotesModalDialog());
    }

    public boolean isRoEditNotesModalDialogDisplayed() {
        return Utils.isElementDisplayed(new VNextBOEditNotesDialog().getRoEditNotesModalDialog());
    }

    public boolean isRoEditNotesModalDialogHidden() {
        return Utils.isElementNotDisplayed(new VNextBOEditNotesDialog().getRoEditNotesModalDialog());
    }

    public boolean isRONoteTextAreaDisplayed() {
        return Utils.isElementDisplayed(orderServiceNotesDialog.getRoNoteTextArea(), 5);
    }
}