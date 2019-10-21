package com.cyberiansoft.test.vnextbo.testcases.Inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsApproveTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/Inspections/VNextBOInspectionsApproveData.json";
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanCancelApprovingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageSteps.clickInspectionApproveButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(webdriver);
        VNextBOModalDialogValidations.isYesButtonDisplayed();
        VNextBOModalDialogValidations.isNoButtonDisplayed();
        VNextBOModalDialogValidations.isCloseButtonDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOModalDialogValidations.isDialogClosed(confirmationDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelApprovingWithCloseButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickInspectionApproveButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(webdriver);
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(confirmationDialog);
    }
}