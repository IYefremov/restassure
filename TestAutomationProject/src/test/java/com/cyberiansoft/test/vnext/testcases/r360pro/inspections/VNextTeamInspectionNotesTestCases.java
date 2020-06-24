package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.NotesValidations;
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
        final String quickNoteText2 = "Note15";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressHardwareBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
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

        BaseUtils.waitABit(15000);
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(myInspectionNote);
        NotesSteps.setNoteText(teamInspectionNote);
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.switchToMyInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(myInspectionNote + teamInspectionNote);
        BaseUtils.waitABit(1000);
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
        NotesValidations.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(15000);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(noteText);
        NotesValidations.verifyPicturesPresent();
        NotesSteps.deleteAllPictures();
        NotesValidations.verifyNoPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanEditNoteFromTeamInspectionsList(String rowID,
                                                       String description, JSONObject testData) {

        final String newNoteText = "new Notes";
        final String quickNoteText1 = "Note15";
        final String quickNoteText2 = "Note1";
        final String noteText = UUID.randomUUID().toString();

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressHardwareBackButton();
        BaseUtils.waitABit(10000);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
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
        NotesValidations.verifyNoteIsPresent(newNoteText + '\n' + quickNoteText1);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(newNoteText + '\n' + quickNoteText1);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyNotesIconDisplaysIfUserAddNotesForInspectionMyTeam(String rowID,
                                                                             String description, JSONObject testData) {

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Warranty expired";
        final String quickNoteText2 = "Note15";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressHardwareBackButton();
        BaseUtils.waitABit(10000);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        BaseUtils.waitABit(1000);
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
        final String quickNoteText1 = "Note15";

        HomeScreenSteps.openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addPhotoFromCamera();
        ScreenNavigationSteps.pressHardwareBackButton();
        BaseUtils.waitABit(15000);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1);
        NotesValidations.verifyPicturesPresent();
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
        resultNoteText = noteText + "\n" + NotesSteps.addQuickNotesFromListByCount(5);
        ScreenNavigationSteps.pressHardwareBackButton();
        BaseUtils.waitABit(15000);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNoteIsPresent(resultNoteText.trim());
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }
}
