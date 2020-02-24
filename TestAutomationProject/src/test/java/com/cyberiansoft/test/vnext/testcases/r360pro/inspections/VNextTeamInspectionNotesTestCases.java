package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

public class VNextTeamInspectionNotesTestCases extends BaseTestClass {

    private String inspectionId = "";

    @BeforeClass(description = "Team Inspection Notes Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionNotesTestCasesDataPath();
    }

    @BeforeMethod
    public void beforeMethod() {
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddNoteToTeamInspections(String rowID,
                                                String description, JSONObject testData) {

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Warranty expired";
        final String quickNoteText2 = "Test Quick Note 1";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeeNotesFromMyInspectionsInTeamInspections(String rowID,
                                                                  String description, JSONObject testData) {
        final String myInspectionNote = UUID.randomUUID().toString();
        final String teamInspectionNote = UUID.randomUUID().toString();

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToMyInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(myInspectionNote);
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(myInspectionNote);
        NotesSteps.setNoteText(teamInspectionNote);
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.switchToMyInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(teamInspectionNote);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddAndRemovePictures(String rowID,
                                            String description, JSONObject testData) {
        final String noteText = UUID.randomUUID().toString();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addPhotoFromCamera();
        NotesSteps.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText);
        NotesSteps.verifyPicturesPresent();
        NotesSteps.deleteAllPictures();
        NotesSteps.verifyNoPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanEditNoteFromTeamInspectionsList(String rowID,
                                                       String description, JSONObject testData) {

        final String newNoteText = "new Notes";
        final String quickNoteText1 = "Test Quick Note 1";
        final String quickNoteText2 = "tr5555";
        final String noteText = UUID.randomUUID().toString();

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.tapNoteTextAndClear();
        NotesSteps.setNoteText("new Notes");
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.verifyNoteIsPresent(newNoteText + '\n' + quickNoteText1);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(newNoteText + '\n' + quickNoteText1);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyNotesIconDisplaysIfUserAddNotesForInspectionMyTeam(String rowID,
                                                                             String description, JSONObject testData) {

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Warranty expired";
        final String quickNoteText2 = "Test Quick Note 1";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionsValidations.verifyInspectionHasNotesIcon(inspectionId, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySavingTextNoteOnTappingBackButton(String rowID,
                                                            String description, JSONObject testData) {

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Test Quick Note 1";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addPhotoFromCamera();
        ScreenNavigationSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1);
        NotesSteps.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        InvoiceSteps.switchToMyInvoicesView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyQuickNotesAreAddedAsNewLinesOfText(String rowID,
                                                             String description, JSONObject testData) {

        final String noteText = UUID.randomUUID().toString();
        String resultNoteText = "";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        resultNoteText = noteText + NotesSteps.addQuickNotesByCount(5);
        ScreenNavigationSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.verifyNoteIsPresent(resultNoteText);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }
}
