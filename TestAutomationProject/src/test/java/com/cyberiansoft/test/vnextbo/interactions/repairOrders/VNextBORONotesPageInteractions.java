package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOOrderServiceNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOEditNotesDialog;
import com.cyberiansoft.test.vnextbo.verifications.VNextBONotesPageVerifications;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VNextBORONotesPageInteractions {

    private VNextBOOrderServiceNotesDialog orderServiceNotesDialog;
    private VNextBOEditNotesDialog editNotesDialog;

    public VNextBORONotesPageInteractions() {
        orderServiceNotesDialog = new VNextBOOrderServiceNotesDialog();
        editNotesDialog = new VNextBOEditNotesDialog();
    }

    public void openRONoteTextArea() {
        if (!new VNextBONotesPageVerifications().isRONoteTextAreaDisplayed()) {
            clickAddNewNoteButton();
        }
    }

    public void clickAddNewNoteButton() {
        Utils.clickElement(orderServiceNotesDialog.getAddNewNoteButton());
    }

    public void typeRONotesMessage(String message) {
        Utils.clearAndType(orderServiceNotesDialog.getRoNoteTextArea(), message);
        clickRoNoteServiceTitle();
        WaitUtilsWebDriver.waitABit(1000);
    }

    public void clickRoNoteServiceTitle() {
        Utils.clickElement(orderServiceNotesDialog.getRoNoteTextTitle());
    }

    public void clickRONoteSaveButton() {
        Utils.clickElement(orderServiceNotesDialog.getRoNoteSaveButton());
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(orderServiceNotesDialog.getRoNoteSaveButton(), 5);
    }

    public void clickRepairNotesXButton() {
        Utils.clickElement(orderServiceNotesDialog.getRoNotesXbutton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void closeRONoteDialog() {
        Utils.clickElement(editNotesDialog.getRoNoteDialogCloseButton());
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(new VNextBOEditNotesDialog().getRoEditNotesModalDialog(), 5);
    }

    public String getRONoteTextAreaValue() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(orderServiceNotesDialog.getRoNoteTextArea(), 4).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public void waitUntilRONoteTextContainsValue(String value) {
        try {
            WaitUtilsWebDriver
                    .getShortWait()
                    .until(driver -> orderServiceNotesDialog.getRoNoteTextArea().getText().equals(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRepairNotesListNumber() {
        try {
            return Objects
                    .requireNonNull(WaitUtilsWebDriver
                            .waitForVisibilityOfAllOptionsIgnoringException(orderServiceNotesDialog.getRoNotesList(), 10))
                    .size();
        } catch (Exception ignored) {
            return 0;
        }
    }

    public List<String> getRepairNotesListValues() {
        try {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(orderServiceNotesDialog.getRoNotesList()).size();
            return orderServiceNotesDialog.getRoNotesList().stream().map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception ignored) {
            return null;
        }
    }
}