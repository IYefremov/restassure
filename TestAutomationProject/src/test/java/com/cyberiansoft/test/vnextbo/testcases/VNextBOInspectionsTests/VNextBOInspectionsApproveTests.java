package com.cyberiansoft.test.vnextbo.testcases.VNextBOInspectionsTests;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsApproveTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOInspectionsApproveData.json";
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanCancelApprovingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        inspectionsWebPage.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        inspectionsWebPage.clickInspectionApproveButton();
        VNextBOModalDialog confirmApprovingDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(confirmApprovingDialog.isYesButtonDisplayed(), "Confirmation dialog hasn't had \"Yes\" button.");
        Assert.assertTrue(confirmApprovingDialog.isNoButtonDisplayed(), "Confirmation dialog hasn't had \"No\" button.");
        Assert.assertTrue(confirmApprovingDialog.isCloseButtonDisplayed(), "Confirmation dialog hasn't had \"Close\" button.");
        confirmApprovingDialog.clickNoButton();
        Assert.assertTrue(confirmApprovingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelApprovingWithCloseButton(String rowID, String description, JSONObject testData) {

        inspectionsWebPage.clickInspectionApproveButton();
        VNextBOModalDialog confirmApprovingDialog = new VNextBOModalDialog(webdriver);
        confirmApprovingDialog.clickCloseButton();
        Assert.assertTrue(confirmApprovingDialog.isDialogClosed(), "Confirmation dialog hasn't been closed");
    }
}