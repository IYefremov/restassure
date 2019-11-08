package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;

public class VNextBORONotesPageSteps {

    public static void openNoteDialog(String woNumber) {
        VNextBOROPageInteractions.revealNoteForWorkOrder(woNumber);
        VNextBOROPageInteractions.openNotesDialog();
    }

    public static void setRONoteMessage(String message) {
        VNextBORONotesPageInteractions.openRONoteTextArea();
        VNextBORONotesPageInteractions.typeRONotesMessage(message);
    }

    public static void setRONoteMessageAndSave(String message) {
        setRONoteMessage(message);
        VNextBORONotesPageInteractions.clickRONoteSaveButton();
        VNextBORONotesPageInteractions.closeRONoteDialog();
    }
}