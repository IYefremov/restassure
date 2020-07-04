package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBODocumentData;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartDocumentsDialog;
import org.testng.Assert;

public class VNextBOPartDocumentsDialogValidations {

    public static void verifyPartDocumentsDialogIsOpened(boolean opened) {
        final boolean visible = WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartDocumentsDialog().getPartDocumentsDialog(), opened);
        Assert.assertTrue(visible, "The part documents dialog hasn't been opened/closed");
    }

    public static void verifyType(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getTypeValue(order), expected,
                "The type isn't correct");
    }

    public static void verifyNumber(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getNumberValue(order), expected,
                "The number isn't correct");
    }

    public static void verifyDate(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDateValue(order), expected,
                "The date isn't correct");
    }

    public static void verifyDueDate(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDueDateValue(order), expected,
                "The due date isn't correct");
    }

    public static void verifyAmount(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getAmountValue(order), expected,
                "The amount isn't correct");
    }

    public static void verifyNotes(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getNotesValue(order), expected,
                "The notes value isn't correct");
    }

    public static void verifyAttachment(int order, String expected) {
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getAttachmentValue(order), expected,
                "The attachment isn't correct");
    }

    public static void verifyPartDocumentsFields(int order, VNextBODocumentData data) {
        verifyPartDocumentsDialogIsOpened(true);
        WaitUtilsWebDriver.waitABit(1500);
        verifyType(order, data.getType());
        verifyNumber(order, data.getNumber());
        verifyNotes(order, data.getNotes());
        verifyDate(order, BackOfficeUtils.getCurrentDate(true));
        verifyDueDate(order, BackOfficeUtils.getCurrentDate(true));
        verifyAmount(order, data.getAmount());
        verifyAttachment(order, data.getDisplayedAttachmentName());
    }
}
