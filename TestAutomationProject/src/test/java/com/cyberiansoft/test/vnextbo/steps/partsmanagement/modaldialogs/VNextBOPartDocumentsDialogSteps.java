package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.testng.Assert;

public class VNextBOPartDocumentsDialogSteps {

    public static void deleteDocument(int order) {
        final int documentsSize = VNextBOPartDocumentsDialogInteractions.getDocumentsSize();
        VNextBOPartDocumentsDialogInteractions.clickDeleteDocumentButton(order);
        Assert.assertTrue(VNextBOConfirmationDialogInteractions.getConfirmationDialogMessage().contains(
                VNextBOAlertMessages.VERIFY_TO_BE_DELETED), "The message hasn't been displayed");
        VNextBOConfirmationDialogInteractions.clickModalDialogButton(new VNextBOConfirmationDialog().getYesButton());
        VNextBOPartDocumentsDialogInteractions.waitForDocumentsSizeToBeUpdated(documentsSize - 1);
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDocumentsSize(), documentsSize - 1,
                "The document hasn't been deleted");
    }
}
