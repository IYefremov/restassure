package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOQuickNotesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOQuickNotesTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOQuickNotesData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNexBOLeftMenuPanel leftMenu;

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);
        VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
        headerPanel.executeJsForAddOnSettings(); //todo use the method getJsForAddOnSettings() from VNextBOServicesPartsAndLaborBundleData.java after fix
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
                VNextBOHeaderPanel.class);
        if (headerpanel.logOutLinkExists())
            headerpanel.userLogout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
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