package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.partsmanagement.PartStatus;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPMNotesDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPMNotesDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPMNotesDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartDocumentsDialogValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPMOrderDetailsActionsTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsActionsTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteThePart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.addPartWithPartsListUpdate(data.getPartData(), woNum);
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(
                VNextBOPartsDetailsPanelSteps.getPartNumberInTheListByServiceName(data.getPartData().getPartItems()[0]));
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts - 1);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewDocumentAndDeleteIt(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.addPartIfNotPresentWithPartsListUpdate(data.getPartData(), woNum);
        final int partNumber = VNextBOPartsDetailsPanelSteps
                .getPartNumberInTheListByServiceName(data.getPartData().getPartItems()[0]);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(partNumber);
        VNextBOPartDocumentsDialogSteps.openAddNewDocumentDialog();
        VNextBOPartAddNewDocumentDialogSteps.setDocumentFields(data.getDocumentData());
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartDocumentsDialogValidations.verifyPartDocumentsFields(0, data.getDocumentData());
        VNextBOPartDocumentsDialogSteps.deleteDocument(0);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotAddNewDocumentWithoutRequiredFieldsAndCancelDeletion(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.addPartIfNotPresentWithPartsListUpdate(data.getPartData(), woNum);
        final int partNumber = VNextBOPartsDetailsPanelSteps
                .getPartNumberInTheListByServiceName(data.getPartData().getPartItems()[0]);
        VNextBOPartsDetailsPanelSteps.openDocumentsDialogByNumberInList(partNumber);
        final int documentsSize = VNextBOPartDocumentsDialogInteractions.getDocumentsSize();
        VNextBOPartDocumentsDialogSteps.openAddNewDocumentDialog();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentType(data.getDocumentData().getType());
        VNextBOPartAddNewDocumentDialogSteps.saveEmptyDocumentFields();
        Assert.assertTrue(VNextBOPartAddNewDocumentDialogValidations.isWarningMessageDisplayed(),
                "The warning message hasn't been displayed");
        VNextBOPartAddNewDocumentDialogSteps.closeDialogWithXIcon();
        Assert.assertEquals(VNextBOPartDocumentsDialogInteractions.getDocumentsSize(), documentsSize,
                "The documents size has been changed");

        VNextBOPartDocumentsDialogSteps.openAddNewDocumentDialog();
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentType(data.getDocumentData().getType());
        VNextBOPartAddNewDocumentDialogInteractions.setDocumentNumber(data.getDocumentData().getNumber());
        VNextBOPartAddNewDocumentDialogSteps.saveDocumentFields();
        VNextBOPartDocumentsDialogValidations.verifyNumber(0, data.getDocumentData().getNumber());
        VNextBOPartDocumentsDialogValidations.verifyAmount(0, "0");

        VNextBOPartDocumentsDialogSteps.deleteDocumentAndCancelByClickingCloseButton(0);
        VNextBOPartDocumentsDialogSteps.deleteDocumentAndCancelByClickingCancelButton(0);
        VNextBOPartDocumentsDialogSteps.deleteDocument(0);
        VNextBOPartDocumentsDialogInteractions.closePartDocumentsDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDuplicateThePart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.duplicatePartByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.updatePartsListAfterDuplicating(woNum, numberOfParts + 1);
        VNextBOPartsDetailsPanelValidations.verifyLaborsExtenderIsDisplayed(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartCheckboxIsDisplayed(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartDefaultValues(numberOfParts);
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(numberOfParts, PartStatus.OPEN.getStatus());
        VNextBOPartsDetailsPanelSteps.deletePartByNumberInList(VNextBOPartsDetailsPanelSteps.getPartsListSize() - 1);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelDuplicatingThePartWithXIcon(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.clickDuplicatePartsAndCancelWithXIcon(0);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts);
        VNextBOPartsDetailsPanelSteps.updatePartsListAfterDuplicating(woNum, numberOfParts);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelDuplicatingThePartWithCancelButton(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        final int numberOfParts = VNextBOPartsDetailsPanelSteps.getPartsListSize();
        VNextBOPartsDetailsPanelSteps.clickDuplicatePartsAndCancel(0);
        VNextBOPartsDetailsPanelValidations.verifyPartsAmountIsUpdated(numberOfParts);
        VNextBOPartsDetailsPanelSteps.updatePartsListAfterDuplicating(woNum, numberOfParts);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNotes(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        final String note = data.getRandomNote();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.openNotesDialogByNumberInList(0);
        final int notesListSize = VNextBOPMNotesDialogInteractions.getNotesListSize();
        VNextBOPMNotesDialogSteps.addNote(note);
        VNextBOPMNotesDialogInteractions.clickSaveNoteButton();
        VNextBOPMNotesDialogValidations.verifyNotesListHasBeenUpdated(notesListSize + 1);
        VNextBOPMNotesDialogValidations.verifyNewNoteHasBeenAdded(note);
        VNextBOPMNotesDialogSteps.closeNoteDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanTypeButNotSaveNote(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        final String note = data.getRandomNote();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.openNotesDialogByNumberInList(0);
        final int notesListSize = VNextBOPMNotesDialogInteractions.getNotesListSize();
        VNextBOPMNotesDialogSteps.addNote(note);
        VNextBOPMNotesDialogInteractions.clickCancelNoteButton();
        VNextBOPMNotesDialogValidations.verifyNotesListHasNotBeenUpdated(notesListSize + 1);
        VNextBOPMNotesDialogValidations.verifyNewNoteHasNotBeenAdded(note);
        VNextBOPMNotesDialogSteps.closeNoteDialog();
    }
}
