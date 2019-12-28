package com.cyberiansoft.test.vnextbo.testcases.quicknotes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.quicknotes.VNextBONewNotesDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.quicknotes.VNextBOQuickNotesWebPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.quicknotes.VNextBONewNotesDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.quicknotes.VNextBOQuickNotesWebPageValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOQuickNotesTestCases extends BaseTestCase {

    private String newNoteDescription = "AutoTestNoteDescription";
    private String editedNoteDescription = "Edited Auto Test Note Description";

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getQuickNotesTD();
        VNextBOLeftMenuInteractions.selectQuickNotesMenu();
    }
    
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanAddQuickNotes(String rowID, String description, JSONObject testData) {

        int initialNotesAmount = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.addNewNote(newNoteDescription);
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(initialNotesAmount + 1);
        VNextBOQuickNotesWebPageValidations.verifyLastNoteDescription(newNoteDescription, true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyQuickNoteIsNotAddedAfterReject(String rowID, String description, JSONObject testData) {

        int initialNotesAmount = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.addNewNoteWithoutSave(newNoteDescription);
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(initialNotesAmount);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCannotAddEmptyQuickNotes(String rowID, String description, JSONObject testData) {

        VNextBOQuickNotesWebPageSteps.clickAddNoteButton();
        VNextBONewNotesDialogSteps.populateDescriptionField("");
        VNextBONewNotesDialogSteps.clickAddButton();
        VNextBONewNotesDialogValidations.verifyErrorMessageIsDisplayedAndCorrect();
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCannotSaveUpdating(String rowID, String description, JSONObject testData) {

        int initialNotesAmount = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.clickEditNoteButtonForNoteByDescription(newNoteDescription);
        VNextBONewNotesDialogSteps.populateDescriptionField(editedNoteDescription);
        VNextBONewNotesDialogSteps.closeDialog();
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(initialNotesAmount);
        VNextBOQuickNotesWebPageValidations.verifyNoteIsNotPresentedInTheList(editedNoteDescription);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCannotSaveEmptyUpdatedQuickNote(String rowID, String description, JSONObject testData) {

        VNextBOQuickNotesWebPageSteps.clickEditNoteButtonForNoteByDescription(newNoteDescription);
        VNextBONewNotesDialogSteps.populateDescriptionField("");
        VNextBONewNotesDialogSteps.clickUpdateButton();
        VNextBONewNotesDialogValidations.verifyErrorMessageIsDisplayedAndCorrect();
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanEditQuickNotes(String rowID, String description, JSONObject testData) {

        int initialNotesAmount = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.updateNote(newNoteDescription, editedNoteDescription);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(initialNotesAmount);
        VNextBOQuickNotesWebPageValidations.verifyLastNoteDescription(editedNoteDescription, true);
        VNextBOQuickNotesWebPageValidations.verifyNoteIsNotPresentedInTheList(newNoteDescription);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanMoveNotation(String rowID, String description, JSONObject testData) {

        int initialNotesAmount = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.moveNoteFromTheLastToTheFirstPositionInTheList();
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(initialNotesAmount);
        VNextBOQuickNotesWebPageValidations.verifyNoteIsPresentedInTheList(editedNoteDescription);
        VNextBOQuickNotesWebPageValidations.verifyLastNoteDescription(editedNoteDescription, false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanDeleteQuickNotes(String rowID, String description, JSONObject testData) {

        int initialNotesAmount = VNextBOQuickNotesWebPageSteps.getNotesAmount();
        VNextBOQuickNotesWebPageSteps.deleteNote(editedNoteDescription);
        VNextBOQuickNotesWebPageValidations.verifyNotesAmountIsCorrect(initialNotesAmount - 1);
        VNextBOQuickNotesWebPageValidations.verifyNoteIsNotPresentedInTheList(editedNoteDescription);
    }
}