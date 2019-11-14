package com.cyberiansoft.test.vnextbo.testcases.inspections;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionMaximizedImageDialog;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionNoteDialog;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionMaximizedImageDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionNoteDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionMaximizedImageDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionNoteDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionsPageValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOInspectionsDetailsTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getInspectionsDetailsTD();
        VNextBOLeftMenuInteractions.selectInspectionsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInspectionImage(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageValidations.verifyInspectionImageZoomIconIsDisplayed();
        VNextBOInspectionsPageSteps.clickInspectionImageZoomIcon();
        VNextBOInspectionMaximizedImageDialog vNextBOInspectionMaximizedImageDialog =
                new VNextBOInspectionMaximizedImageDialog();
        VNextBOInspectionMaximizedImageDialogValidations.verifyInspectionZoomedImageIsDisplayed();
        VNextBOInspectionMaximizedImageDialogSteps.closeInspectionMaximizedImageDialog();
        VNextBOInspectionMaximizedImageDialogValidations.verifyInspectionZoomedImageIsClosed(vNextBOInspectionMaximizedImageDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInspectionNotes(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageValidations.verifyInspectionNotesIconIsDisplayed();
        VNextBOInspectionsPageSteps.clickInspectionNotesIcon();
        VNextBOInspectionNoteDialog vNextBOInspectionNoteDialog = new  VNextBOInspectionNoteDialog();
        VNextBOInspectionNoteDialogValidations.verifyInspectionNoteTextIsDisplayed();
        VNextBOInspectionNoteDialogSteps.closeInspectionNote();
        VNextBOInspectionNoteDialogValidations.verifyNoteDialogIsClosed(vNextBOInspectionNoteDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePrintSupplementDetails(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageValidations.verifyPrintSupplementButtonIsDisplayed();
        VNextBOInspectionsPageSteps.clickPrintSupplementButton();
        VNextBOInspectionsPageValidations.verifyPrintWindowIsOpened();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePrintInspectionDetails(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageValidations.verifyPrintInspectionButtonIsDisplayed();
        VNextBOInspectionsPageSteps.clickPrintInspectionButton();
        VNextBOInspectionsPageValidations.verifyPrintWindowIsOpened();
    }
}
