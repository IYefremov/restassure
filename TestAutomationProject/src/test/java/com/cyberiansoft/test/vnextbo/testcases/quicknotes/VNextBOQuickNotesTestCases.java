package com.cyberiansoft.test.vnextbo.testcases.quicknotes;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOQuickNotesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBONewNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOQuickNotesWebPage;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOQuickNotesTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getQuickNotesTD();
    }

    private VNexBOLeftMenuPanel leftMenu;

    //@Override
    @BeforeMethod
    public void setUp() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginSteps.userLogin(userName, userPassword);
        VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel();
        headerPanel.executeJsForAddOnSettings();
        leftMenu = new VNexBOLeftMenuPanel();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        final int numberOfQuickNotes = quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription());
        quickNotesPage
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertEquals(numberOfQuickNotes + 1,
                quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyQuickNoteIsNotAddedAfterReject(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        final int numberOfQuickNotes = quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription());
        quickNotesPage
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogCloseButton();
        Assert.assertEquals(numberOfQuickNotes,
                quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotAddEmptyQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        final int numberOfQuickNotes = quickNotesPage.getNumberOfQuickNotesDisplayed();
        VNextBONewNotesDialog addNewNotesDialog = quickNotesPage.clickAddNotesButton();
        addNewNotesDialog
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertTrue(addNewNotesDialog.isQuickNotesDescriptionErrorMessageDisplayed());
        addNewNotesDialog.clickQuickNotesDialogCloseButton();

        Assert.assertEquals(numberOfQuickNotes, quickNotesPage.getNumberOfQuickNotesDisplayed());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        final int numberOfQuickNotes = quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription());
        quickNotesPage
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertEquals(numberOfQuickNotes + 1,
                quickNotesPage.getNumberOfQuickNotesDisplayed(data.getQuickNotesDescription()));
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));
        quickNotesPage.deleteQuickNote(data.getQuickNotesDescription());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditQuickNotes(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        quickNotesPage
                .deleteQuickNotesIfPresent(data.getQuickNotesDescription())
                .deleteQuickNotesIfPresent(data.getQuickNotesDescriptionEdited())
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));

        quickNotesPage.clickEditQuickNote(data.getQuickNotesDescription())
                .typeDescription(data.getQuickNotesDescriptionEdited())
                .clickQuickNotesDialogUpdateButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescriptionEdited()));
        quickNotesPage.deleteQuickNote(data.getQuickNotesDescriptionEdited());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotSaveUpdating(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        quickNotesPage
                .deleteQuickNotesIfPresent(data.getQuickNotesDescription())
                .deleteQuickNotesIfPresent(data.getQuickNotesDescriptionEdited())
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));

        quickNotesPage.clickEditQuickNote(data.getQuickNotesDescription())
                .typeDescription(data.getQuickNotesDescriptionEdited())
                .clickQuickNotesDialogCloseButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));
        Assert.assertFalse(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescriptionEdited()));
        quickNotesPage.deleteQuickNote(data.getQuickNotesDescription());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotSaveEmptyUpdatedQuickNote(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        quickNotesPage
                .deleteQuickNotesIfPresent(data.getQuickNotesDescription())
                .clickAddNotesButton()
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertTrue(quickNotesPage.isQuickNoteDisplayed(data.getQuickNotesDescription()));

        VNextBONewNotesDialog addNewNotesDialog = quickNotesPage
                .clickEditQuickNote(data.getQuickNotesDescription())
                .typeDescription(data.getQuickNotesDescriptionEdited());
        addNewNotesDialog.clickQuickNotesDialogUpdateButton();

        Assert.assertTrue(addNewNotesDialog.isQuickNotesDescriptionErrorMessageDisplayed());
        addNewNotesDialog
                .clickQuickNotesDialogCloseButton()
                .deleteQuickNote(data.getQuickNotesDescription());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class) //todo drag&drop doesn't work!!!
    public void verifyUserCanMoveNotation(String rowID, String description, JSONObject testData) {
        VNextBOQuickNotesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOQuickNotesData.class);

        VNextBOQuickNotesWebPage quickNotesPage = leftMenu.selectQuickNotesMenu();
        quickNotesPage.deleteQuickNotesIfPresent(data.getQuickNotesDescriptionList());

        quickNotesPage.addQuickNotes(data.getQuickNotesDescriptionList());

        int firstQuickNoteOrderNumber = quickNotesPage.getQuickNoteOrderInList(data.getQuickNotesDescriptionList()[0]);
        System.out.println(firstQuickNoteOrderNumber);
        int secondQuickNoteOrderNumber = quickNotesPage.getQuickNoteOrderInList(data.getQuickNotesDescriptionList()[1]);
        System.out.println(secondQuickNoteOrderNumber);
        Assert.assertTrue(quickNotesPage.areQuickNotesDisplayed(data.getQuickNotesDescriptionList()));
        quickNotesPage.moveQuickNotes(data.getQuickNotesDescriptionList()[1], data.getQuickNotesDescriptionList()[0]);

        int firstQuickNoteNumberUpdated = quickNotesPage.getQuickNoteOrderInList(data.getQuickNotesDescriptionList()[0]);
        int secondQuickNoteNumberUpdated = quickNotesPage.getQuickNoteOrderInList(data.getQuickNotesDescriptionList()[1]);
        System.out.println(firstQuickNoteNumberUpdated);
        System.out.println(secondQuickNoteNumberUpdated);

        Assert.assertEquals(secondQuickNoteOrderNumber, firstQuickNoteNumberUpdated);
        Assert.assertEquals(firstQuickNoteOrderNumber, secondQuickNoteNumberUpdated);

        quickNotesPage.deleteQuickNotesIfPresent(data.getQuickNotesDescriptionList());
    }
}