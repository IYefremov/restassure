package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

public class VNextTeamInspectionNotesTestCases extends BaseTestCaseTeamEditionRegistration {

    private String inspectionId = "";

    @BeforeClass(description = "Team Inspection Notes Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionNotesTestCasesDataPath();
    }

    @BeforeMethod
    public void beforeMethod() {
        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddNoteToTeamInspections(String rowID,
                                                String description, JSONObject testData) {

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Warranty expired";
        final String quickNoteText2 = "Test Quick Note 1";

        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        GeneralSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeeNotesFromMyInspectionsInTeamInspections(String rowID,
                                                                  String description, JSONObject testData) {
        final String myInspectionNote = UUID.randomUUID().toString();
        final String teamInspectionNote = UUID.randomUUID().toString();

        InspectionSteps.switchToMyInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(myInspectionNote);
        GeneralSteps.pressBackButton();

        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(myInspectionNote);
        NotesSteps.setNoteText(teamInspectionNote);
        GeneralSteps.pressBackButton();

        InspectionSteps.switchToMyInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(teamInspectionNote);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddAndRemovePictures(String rowID,
                                            String description, JSONObject testData) {
        final String noteText = UUID.randomUUID().toString();

        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addPhotoFromCamera();
        NotesSteps.verifyPicturesPresent();
        GeneralSteps.pressBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText);
        NotesSteps.verifyPicturesPresent();
        NotesSteps.deleteAllPictures();
        NotesSteps.verifyNoPicturesPresent();
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }
}
