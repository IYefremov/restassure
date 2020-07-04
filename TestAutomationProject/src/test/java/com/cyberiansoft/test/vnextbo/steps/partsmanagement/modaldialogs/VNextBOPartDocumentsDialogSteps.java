package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.dataclasses.vNextBO.alerts.VNextBOAlertMessages;
import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOConfirmationDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogValidations;
import org.testng.Assert;

public class VNextBOPartDocumentsDialogSteps {

    public static void openAddNewDocumentDialog() {
        VNextBOPartDocumentsDialogInteractions.clickAddNewDocumentButton();
        VNextBOPartAddNewDocumentDialogValidations.verifyAddNewDocumentDialogIsOpened(true);
    }

    public static void deleteDocument(int order) {
        final int documentsSize = VNextBOPartDocumentsDialogInteractions.getDocumentsSize();
        clickDeleteDocumentButton(order);
        VNextBOConfirmationDialogInteractions.clickModalDialogButton(new VNextBOConfirmationDialog().getYesButton());
        VNextBOPartDocumentsDialogInteractions.waitForDocumentsSizeToBeUpdated(documentsSize - 1);
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDocumentsSize(), documentsSize - 1,
                "The document hasn't been deleted");
    }

    public static void deleteDocumentAndCancelByClickingCloseButton(int order) {
        final int documentsSize = VNextBOPartDocumentsDialogInteractions.getDocumentsSize();
        clickDeleteDocumentButton(order);
        VNextBOConfirmationDialogInteractions.clickModalDialogButton(new VNextBOConfirmationDialog().getCloseButton());
        VNextBOPartDocumentsDialogInteractions.waitForDocumentsSizeToBeUpdated(documentsSize);
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDocumentsSize(), documentsSize,
                "The document has been deleted");
    }

    public static void deleteDocumentAndCancelByClickingCancelButton(int order) {
        final int documentsSize = VNextBOPartDocumentsDialogInteractions.getDocumentsSize();
        clickDeleteDocumentButton(order);
        VNextBOConfirmationDialogInteractions.clickModalDialogButton(new VNextBOConfirmationDialog().getNoButton());
        VNextBOPartDocumentsDialogInteractions.waitForDocumentsSizeToBeUpdated(documentsSize);
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDocumentsSize(), documentsSize,
                "The document has been deleted");
    }

    private static void clickDeleteDocumentButton(int order) {
        VNextBOPartDocumentsDialogInteractions.clickDeleteDocumentButton(order);
        VNextBOConfirmationDialogValidations.verifyDialogMessageIsDisplayed(VNextBOAlertMessages.VERIFY_TO_BE_DELETED);
    }
}
