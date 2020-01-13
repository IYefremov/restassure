package com.cyberiansoft.test.vnextbo.testcases.homepage;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOUserProfileDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOUserProfileDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOPrivacyPolicyDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOTermsAndConditionsDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBODeviceManagementPageValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOUserProfileDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import com.cyberiansoft.test.vnextbo.validations.homepage.VNextBOHomeWebPageValidations;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOHomePageTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getHomePageTD();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeHomePage(String rowID, String description, JSONObject testData) {

        Assert.assertTrue(VNextBOLeftMenuValidations.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        VNextBOHomeWebPageValidations.verifyAllHomePageElements();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMaximizeMinimizeMenu(String rowID, String description, JSONObject testData) {

        VNextBOLeftMenuInteractions.expandMainMenu();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been expanded");
        VNextBOLeftMenuInteractions.collapseMainMenu();
        Assert.assertFalse(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been collapsed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMinimizeMaximizeMenu(String rowID, String description, JSONObject testData) {

        VNextBOLeftMenuInteractions.collapseMainMenu();
        Assert.assertFalse(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been collapsed");
        VNextBOLeftMenuInteractions.expandMainMenu();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMainMenuExpanded(), "The main menu hasn't been expanded");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeHomePageAfterClickingOnAMTLogo(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.clickLogo();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        VNextBOHomeWebPageValidations.verifyAllHomePageElements();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeEditWindowOfHisProfile(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.openUserProfile();
        VNextBOUserProfileDialogValidations.verifyAllDialogElementsAreDisplayed();
        VNextBOUserProfileDialogSteps.closeDialog();
        VNextBOUserProfileDialogValidations.verifyUserProfileDialogIsDisplayed(false);
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLogoutFromHisProfile(String rowID, String description, JSONObject testData) {

        VNextBOHeaderPanelSteps.logout();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        Assert.assertTrue(VNextBOLoginValidations.isLoginFormDisplayed(), "Login page hasn't been closed");
        Utils.refreshPage();
        VNextBOLoginSteps.userLogin(VNextBOConfigInfo.getInstance().getVNextBONadaMail(),
                VNextBOConfigInfo.getInstance().getVNextBOPassword());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenHelpPage(String rowID, String description, JSONObject testData) {

        final String actualHelpPageUrl = VNextBOHomeWebPageSteps.openHelpPage();
        VNextBOHomeWebPageValidations.verifyHelpPageIsOpened(actualHelpPageUrl);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenLearnPage(String rowID, String description, JSONObject testData) {

        final String actualLearnPageUrl = VNextBOHomeWebPageSteps.openLearnPage();
        VNextBOHomeWebPageValidations.verifyLearnPageIsOpened(actualLearnPageUrl);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickOnAccessClientPortalLink(String rowID, String description, JSONObject testData) {

        final String actualClientPortalPageUrl = VNextBOHomeWebPageSteps.openAccessClientPortalWindow();
        VNextBOHomeWebPageValidations.verifyClientPortalPageIsOpened(actualClientPortalPageUrl);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickOnAccessReconProBOLink(String rowID, String description, JSONObject testData) {

        final String actualReconProBoPageUrl = VNextBOHomeWebPageSteps.openAccessReconProBOWindow();
        VNextBOHomeWebPageValidations.verifyReconProBoPageIsOpened(actualReconProBoPageUrl);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenSupportForBOPage(String rowID, String description, JSONObject testData) {

        final String actualSupportForBoPageUrl = VNextBOHomeWebPageSteps.openSupportForBOWindow();
        VNextBOHomeWebPageValidations.verifySupportForBoPageIsOpened(actualSupportForBoPageUrl);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenSupportForMobileAppPage(String rowID, String description, JSONObject testData) {

        final String actualSupportForMobileAppPageUrl = VNextBOHomeWebPageSteps.openSupportForMobileAppWindow();
        VNextBOHomeWebPageValidations.verifySupportForMobileAppPageIsOpened(actualSupportForMobileAppPageUrl);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseTermsAndConditionsOkButton(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseTermsAndConditionsXIcon(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndClosePrivacyPolicyOkButton(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndClosePrivacyPolicyXIcon(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {

        VNextBOHomeWebPageSteps.openIntercomMessenger();
        VNextBOHomeWebPageValidations.verifyIntercomMessengerIsOpened();
        VNextBOHomeWebPageSteps.closeIntercom();
    }
}