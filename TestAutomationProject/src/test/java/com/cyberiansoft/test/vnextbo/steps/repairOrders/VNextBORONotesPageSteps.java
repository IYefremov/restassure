package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;

public class VNextBORONotesPageSteps {

    private VNextBORONotesPageInteractions roNotesPageInteractions;
    private VNextBOROPageInteractions roPageInteractions;

    public VNextBORONotesPageSteps() {
        roNotesPageInteractions = new VNextBORONotesPageInteractions();
        roPageInteractions = new VNextBOROPageInteractions();
    }

    public void openNoteDialog(String woNumber) {
        roPageInteractions.revealNoteForWorkOrder(woNumber);
        roPageInteractions.openNotesDialog();
    }

    public void setRONoteMessage(String message) {
        roNotesPageInteractions.openRONoteTextArea();
        roNotesPageInteractions.typeRONotesMessage(message);
    }

    public void setRONoteMessageAndSave(String message) {
        setRONoteMessage(message);
        roNotesPageInteractions.clickRONoteSaveButton();
        roNotesPageInteractions.closeRONoteDialog();
    }
}