package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
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

        WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage
                .userLogin(userName, userPassword)
                .executeJsForAddOnSettings(); //todo use the method getJsForAddOnSettings() from VNextBOServicesPartsAndLaborBundleData.java after fix
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
                VNextBOHeaderPanel.class);
        if (headerpanel.logOutLinkExists())
            headerpanel.userLogout();
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
        VNextBOAddNewNotesDialog addNewNotesDialog = quickNotesPage.clickAddNotesButton();
        addNewNotesDialog
                .typeDescription(data.getQuickNotesDescription())
                .clickQuickNotesDialogAddButton();
        Assert.assertTrue(addNewNotesDialog.isQuickNotesDescriptionErrorMessageDisplayed());
        addNewNotesDialog.clickQuickNotesDialogCloseButton();

        Assert.assertEquals(numberOfQuickNotes, quickNotesPage.getNumberOfQuickNotesDisplayed());
    }
}
