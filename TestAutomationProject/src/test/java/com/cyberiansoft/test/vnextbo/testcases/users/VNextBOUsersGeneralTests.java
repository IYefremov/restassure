package com.cyberiansoft.test.vnextbo.testcases.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOUserProfileDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOUserProfileDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import com.cyberiansoft.test.vnextbo.validations.homepage.VNextBOHomeWebPageValidations;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOUsersGeneralTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getUsersTD();
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanOpenUsersPage(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageValidations.verifyAddNewUserBtnIsDisplayed();
        VNextBOUsersPageValidations.verifyUsersTableIsDisplayed();
        VNextBOUsersPageValidations.verifyTermsAndConditionsLinkIsDisplayed();
        VNextBOUsersPageValidations.verifyPrivacyPolicyLinkIsDisplayed();
        VNextBOUsersPageValidations.verifyIntercomButtonIsDisplayed();
        VNextBOUsersPageValidations.verifyLogoIsDisplayed();
        VNextBOUsersPageValidations.verifyTimeBoxIsDisplayed();
        VNextBOSearchPanelValidations.verifySearchPanelIsDisplayed();
        VNextBOPageSwitcherValidations.verifyPageNavigationElementsAreDisplayed();
        VNextBOUsersPageValidations.verifyUserInfoBlockIsDisplayed();
        VNextBOUsersPageValidations.verifyLogoutButtonIsDisplayed();
        VNextBOUsersPageValidations.verifyHelpButtonIsDisplayed();
        VNextBOUsersPageValidations.verifyCopyRightTextIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyIntercomMessengerCanBeOpenedClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openIntercomMessenger();
        VNextBOUsersPageValidations.verifyIntercomMessengerIsOpened();
        VNextBOUsersPageSteps.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyTermsAndConditionsCanBeOpenedAndClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyPrivacyPolicyCanBeOpenedAndClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanOpenAndCloseMainMenu(String rowID, String description, JSONObject testData) {

        VNextBOLeftMenuInteractions.expandMainMenu();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMainMenuExpanded(), "Main menu hasn't been opened");
        VNextBOLeftMenuInteractions.collapseMainMenu();
        Assert.assertFalse(VNextBOLeftMenuValidations.isMainMenuExpanded(), "Main menu hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanReturnToHomePageByClickingLogo(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickLogo();
        VNextBOHomeWebPageValidations.verifySupportForBOButtonIsDisplayed();
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanOpenUserProfile(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openUserProfile();
        VNextBOUserProfileDialogValidations.verifyAllDialogElementsAreDisplayed();
        VNextBOUserProfileDialogSteps.closeDialog();
        VNextBOUserProfileDialogValidations.verifyUserProfileDialogIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanLogout(String rowID, String description, JSONObject testData) {

        VNextBOHeaderPanelSteps.logout();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        Assert.assertTrue(VNextBOLoginValidations.isLoginFormDisplayed(), "Login page hasn't been closed");
        Utils.refreshPage();
        VNextBOLoginSteps.userLogin(VNextBOConfigInfo.getInstance().getVNextBONadaMail(),
                VNextBOConfigInfo.getInstance().getVNextBOPassword());
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanUseHelp(String rowID, String description, JSONObject testData) {

        final String actualHelpPageUrl = VNextBOHomeWebPageSteps.openHelpPage();
        VNextBOHomeWebPageValidations.verifyHelpPageIsOpened(actualHelpPageUrl);
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanNavigateBetweenPages(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(),
                "Bottom Last page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(), "Top First page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifySystemSavesCurrentPageNumber(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.openPageByNumber(4);
        VNextBOUsersPageSteps.clickLogo();
        VNextBOLeftMenuInteractions.selectUsersMenu();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("4");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanChangeItemsPerPage(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("20");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifySystemSavesCurrentValueItemsPerPage(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherSteps.openPageByNumber(2);
        VNextBOUsersPageSteps.clickLogo();
        VNextBOLeftMenuInteractions.selectUsersMenu();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("50");
    }
}