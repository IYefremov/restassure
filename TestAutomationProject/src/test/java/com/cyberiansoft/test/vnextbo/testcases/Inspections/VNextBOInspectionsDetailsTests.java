package com.cyberiansoft.test.vnextbo.testcases.Inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionMaximizedImageDialog;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionNoteDialog;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsDetailsTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/Inspections/VNextBOInspectionsDetailsData.json";
    private VNextBOInspectionsWebPage inspectionsWebPage;
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

        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectInspectionsMenu();
        inspectionsWebPage = new VNextBOInspectionsWebPage(webdriver);
    }

    @AfterClass
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
            if (headerPanel.logOutLinkExists()) {
                headerPanel.userLogout();
            }
        } catch (RuntimeException ignored) {}

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInspectionImage(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        Assert.assertTrue(inspectionsWebPage.isInspectionImageZoomIconDisplayed(),
                "Inspection's image hasn't had Zoom icon");
        inspectionsWebPage.clickInspectionImageZoomIcon();
        VNextBOInspectionMaximizedImageDialog vNextBOInspectionMaximizedImageDialog =
                new VNextBOInspectionMaximizedImageDialog(webdriver);
        Assert.assertTrue(vNextBOInspectionMaximizedImageDialog.isInspectionZoomedImageDisplayed(),
                "Inspection's image hasn't been maximized");
        vNextBOInspectionMaximizedImageDialog.closeInspectionMaximizedImageDialog();
        Assert.assertTrue(vNextBOInspectionMaximizedImageDialog.isInspectionZoomedImageClosed(),
                "Inspection's image hasn't been minimized");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInspectionNotes(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        Assert.assertTrue(inspectionsWebPage.isInspectionNotesIconDisplayed(),
                "Notes icon hasn't been displayed");
        inspectionsWebPage.clickInspectionNotesIcon();
        VNextBOInspectionNoteDialog vNextBOInspectionNoteDialog = new  VNextBOInspectionNoteDialog(webdriver);
        Assert.assertTrue(vNextBOInspectionNoteDialog.isInspectionNoteTextDisplayed(),
                "Notes dialog  hasn't been opened");
        vNextBOInspectionNoteDialog.closeInspectionNote();
        Assert.assertTrue(vNextBOInspectionNoteDialog.isNoteDialogClosed(),
                "Notes dialog  hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePrintSupplementDetails(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        Assert.assertTrue(inspectionsWebPage.isPrintSupplementButtonDisplayed(),
                "Print supplement button hasn't been displayed");
        inspectionsWebPage.clickPrintSupplementButton();
        Assert.assertTrue(inspectionsWebPage.isPrintWindowOpened(),
                "Print supplement window hasn't been opened");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePrintInspectionDetails(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        Assert.assertTrue(inspectionsWebPage.isPrintInspectionButtonDisplayed(),
                "Print inspection button hasn't been displayed");
        inspectionsWebPage.clickPrintInspectionButton();
        Assert.assertTrue(inspectionsWebPage.isPrintWindowOpened(),
                "Print inspection details window hasn't been opened");
    }
}
