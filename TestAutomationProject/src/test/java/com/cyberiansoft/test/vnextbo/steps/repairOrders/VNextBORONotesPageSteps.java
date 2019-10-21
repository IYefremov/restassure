package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;

public class VNextBORONotesPageSteps {

    public static void openNoteDialog(String woNumber) {
        final VNextBOROPageInteractions roPageInteractions = new VNextBOROPageInteractions();
        roPageInteractions.revealNoteForWorkOrder(woNumber);
        roPageInteractions.openNotesDialog();
    }

    public static void setRONoteMessage(String message) {
        final VNextBORONotesPageInteractions roNotesPageInteractions = new VNextBORONotesPageInteractions();
        roNotesPageInteractions.openRONoteTextArea();
        roNotesPageInteractions.typeRONotesMessage(message);
    }

    public static void setRONoteMessageAndSave(String message) {
        setRONoteMessage(message);
        final VNextBORONotesPageInteractions roNotesPageInteractions = new VNextBORONotesPageInteractions();
        roNotesPageInteractions.clickRONoteSaveButton();
        roNotesPageInteractions.closeRONoteDialog();
    }
}