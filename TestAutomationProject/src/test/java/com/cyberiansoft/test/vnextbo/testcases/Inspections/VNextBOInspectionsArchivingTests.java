package com.cyberiansoft.test.vnextbo.testcases.Inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsArchivingTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/Inspections/VNextBOInspectionsArchivingData.json";
    private VNextBOLoginScreenWebPage loginPage;
    private VNextBOInspectionsWebPage inspectionsWebPage;

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanCancelArchivingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        Assert.assertTrue(inspectionsWebPage.isArchiveIconDisplayed(), "Archive icon hasn't been displayed.");
        inspectionsWebPage.clickArchiveIcon();
        inspectionsWebPage.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(confirmArchivingDialog.isYesButtonDisplayed(), "Confirmation dialog hasn't had \"Yes\" button.");
        Assert.assertTrue(confirmArchivingDialog.isNoButtonDisplayed(), "Confirmation dialog hasn't had \"No\" button.");
        Assert.assertTrue(confirmArchivingDialog.isCloseButtonDisplayed(), "Confirmation dialog hasn't had \"Close\" button.");
        confirmArchivingDialog.clickNoButton();
        Assert.assertTrue(confirmArchivingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelArchivingWithCloseButton(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickArchiveIcon();
        inspectionsWebPage.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog(webdriver);
        confirmArchivingDialog.clickCloseButton();
        Assert.assertTrue(confirmArchivingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanArchiveInspection(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        inspectionsWebPage.clickArchiveIcon();
        inspectionsWebPage.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog(webdriver);
        confirmArchivingDialog.clickYesButton();
        Assert.assertTrue(confirmArchivingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
        BaseUtils.waitABit(2000);
        Assert.assertEquals(inspectionsWebPage.getInspectionStatus(data.getInspectionId()),
                "Archived", "Inspection status hasn't been changed to Archived");
        Assert.assertEquals(inspectionsWebPage.getSelectedInspectionArchivingReason(),
                "Inspection archived with reason: Test",
                "Archiving reason hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanCancelUnArchivingWithNoButton(String rowID, String description, JSONObject testData) {

        Assert.assertTrue(inspectionsWebPage.isUnArchiveIconDisplayed(), "Unarchive icon hasn't been displayed.");
        inspectionsWebPage.clickUnArchiveIcon();
        VNextBOModalDialog confirmUnArchivingDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(confirmUnArchivingDialog.isYesButtonDisplayed(), "Confirmation dialog hasn't had \"Yes\" button.");
        Assert.assertTrue(confirmUnArchivingDialog.isNoButtonDisplayed(), "Confirmation dialog hasn't had \"No\" button.");
        Assert.assertTrue(confirmUnArchivingDialog.isCloseButtonDisplayed(), "Confirmation dialog hasn't had \"Close\" button.");
        confirmUnArchivingDialog.clickNoButton();
        Assert.assertTrue(confirmUnArchivingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCancelUnArchivingWithCloseButton(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickUnArchiveIcon();
        VNextBOModalDialog confirmUnArchivingDialog = new VNextBOModalDialog(webdriver);
        confirmUnArchivingDialog.clickCloseButton();
        Assert.assertTrue(confirmUnArchivingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanUnArchiveInspection(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        inspectionsWebPage.clickUnArchiveIcon();
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog(webdriver);
        confirmArchivingDialog.clickYesButton();
        Assert.assertTrue(confirmArchivingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
        BaseUtils.waitABit(2000);
        Assert.assertEquals(inspectionsWebPage.getInspectionStatus(data.getInspectionId()),
                "New", "Inspection status hasn't been changed to Archived");
    }
}
