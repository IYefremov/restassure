package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOOrderServiceNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOEditNotesDialog;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VNextBORONotesPageInteractions {

    public static void openRONoteTextArea() {
        if (!VNextBONotesPageValidations.isRONoteTextAreaDisplayed()) {
            clickAddNewNoteButton();
        }
    }

    public static void clickAddNewNoteButton() {
        Utils.clickElement(new VNextBOOrderServiceNotesDialog().getAddNewNoteButton());
    }

    public static void typeRONotesMessage(String message) {
        Utils.clearAndType(new VNextBOOrderServiceNotesDialog().getRoNoteTextArea(), message);
        clickRoNoteServiceTitle();
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickRoNoteServiceTitle() {
        Utils.clickElement(new VNextBOOrderServiceNotesDialog().getRoNoteTextTitle());
    }

    public static void clickRONoteSaveButton() {
        final VNextBOOrderServiceNotesDialog notesDialog = new VNextBOOrderServiceNotesDialog();
        Utils.clickElement(notesDialog.getRoNoteSaveButton());
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(notesDialog.getRoNoteSaveButton(), 5);
    }

    public static void clickRepairNotesXButton() {
        Utils.clickElement(new VNextBOOrderServiceNotesDialog().getRoNotesXbutton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void closeRONoteDialog() {
        final VNextBOEditNotesDialog notesDialog = new VNextBOEditNotesDialog();
        Utils.clickElement(notesDialog.getRoNoteDialogCloseButton());
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(notesDialog.getRoEditNotesModalDialog(), 5);
    }

    public static String getRONoteTextAreaValue() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(new VNextBOOrderServiceNotesDialog().getRoNoteTextArea(), 4).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static void waitUntilRONoteTextContainsValue(String value) {
        try {
            WaitUtilsWebDriver
                    .getShortWait()
                    .until(driver -> new VNextBOOrderServiceNotesDialog().getRoNoteTextArea().getText().equals(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRepairNotesListNumber() {
        try {
            return Objects
                    .requireNonNull(WaitUtilsWebDriver
                            .waitForVisibilityOfAllOptions(new VNextBOOrderServiceNotesDialog().getRoNotesList(), 10))
                    .size();
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static List<String> getRepairNotesListValues() {
        try {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOOrderServiceNotesDialog().getRoNotesList()).size();
            return new VNextBOOrderServiceNotesDialog().getRoNotesList().stream().map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception ignored) {
            return null;
        }
    }
}