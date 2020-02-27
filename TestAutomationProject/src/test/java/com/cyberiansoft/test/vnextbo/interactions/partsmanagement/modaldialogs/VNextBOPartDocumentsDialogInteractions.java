package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartDocumentsDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogValidations;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class VNextBOPartDocumentsDialogInteractions {

    public static void clickAddNewDocumentButton() {
        Utils.clickElement(new VNextBOPartDocumentsDialog().getAddNewDocumentButton());
    }

    public static String getTypeValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getTypeList().get(order));
    }

    public static String getNumberValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getNumberList().get(order));
    }

    public static String getDateValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getDateList().get(order));
    }

    public static String getDueDateValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getDueDateList().get(order));
    }

    public static String getAmountValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getAmountList().get(order))
                .replace("$", "")
                .replace(".00", "");
    }

    public static String getNotesValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getNotesList().get(order));
    }

    public static String getAttachmentValue(int order) {
        return Utils.getText(new VNextBOPartDocumentsDialog().getAttachmentList().get(order));
    }

    public static void clickDeleteDocumentButton(int order) {
        Utils.clickElement(new VNextBOPartDocumentsDialog().getDeleteButtons().get(order));
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOConfirmationDialog().getConfirmDialog(), true, 3);
    }

    public static int getDocumentsSize() {
        try {
            return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOPartDocumentsDialog().getTypeList(), 3).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static void waitForDocumentsSizeToBeUpdated(int expected) {
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver -> getDocumentsSize() == expected);
        } catch (Exception ignored) {}
    }

    public static void closePartDocumentsDialog() {
        final VNextBOPartDocumentsDialog partDocumentsDialog = new VNextBOPartDocumentsDialog();
        Utils.clickElement(partDocumentsDialog.getXIcon());
        VNextBOPartDocumentsDialogValidations.verifyPartDocumentsDialogIsOpened(false);
    }
}
