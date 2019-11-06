package com.cyberiansoft.test.vnextbo.testcases.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionMaximizedImageDialog;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionNoteDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionMaximizedImageDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionNoteDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.inspections.VNextBOInspectionMaximizedImageDialogValidations;
import com.cyberiansoft.test.vnextbo.verifications.inspections.VNextBOInspectionNoteDialogValidations;
import com.cyberiansoft.test.vnextbo.verifications.inspections.VNextBOInspectionsPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.*;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsDetailsTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/inspections/VNextBOInspectionsDetailsData.json";
    private VNextBOLoginScreenWebPage loginPage;

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = DATA_FILE;
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectInspectionsMenu();
    }

    @AfterClass
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
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
