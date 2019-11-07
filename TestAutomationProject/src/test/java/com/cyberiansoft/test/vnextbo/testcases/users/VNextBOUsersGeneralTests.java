package com.cyberiansoft.test.vnextbo.testcases.users;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOUserProfileDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOUsersGeneralTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getUsersTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenUsersPage(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageValidations.isAddNewUserBtnDisplayed();
        VNextBOUsersPageValidations.isUsersTableDisplayed();
        VNextBOUsersPageValidations.isTermsAndConditionsLinkDisplayed();
        VNextBOUsersPageValidations.isPrivacyPolicyLinkDisplayed();
        VNextBOUsersPageValidations.isIntercomButtonDisplayed();
        VNextBOUsersPageValidations.isLogoDisplayed();
        VNextBOUsersPageValidations.isTimeBoxDisplayed();
        VNextBOSearchPanelValidations.isSearchPanelDisplayed();
        VNextBOPageSwitcherValidations.arePageNavigationElementsDisplayed();
        VNextBOUsersPageValidations.isUserInfoBlockDisplayed();
        VNextBOUsersPageValidations.isLogoutButtonDisplayed();
        VNextBOUsersPageValidations.isHelpButtonDisplayed();
        VNextBOUsersPageValidations.isCopyRightTextDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyIntercomMessengerCanBeOpenedClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openIntercomMessenger();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOUsersPageValidations.isIntercomMessengerOpened();
        VNextBOUsersPageSteps.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseMainMenu(String rowID, String description, JSONObject testData) {

        VNextBOLeftMenuInteractions.expandMainMenu();
        Assert.assertTrue(VNextBOLeftMenuValidations.isMainMenuExpanded(), "Main menu hasn't been opened");
        VNextBOLeftMenuInteractions.collapseMainMenu();
        Assert.assertFalse(VNextBOLeftMenuValidations.isMainMenuExpanded(), "Main menu hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnToHomePageByClickingLogo(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickLogo();
        Assert.assertTrue(new VNextBOHomeWebPage().isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenUserProfile(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openUserProfile();
        VNextBOUserProfileDialog vNextBOProfileDialog = new VNextBOUserProfileDialog(webdriver);
        Assert.assertTrue(vNextBOProfileDialog.isUserProfileDialogDisplayed(), "User profile dialog hasn't been displayed");
        vNextBOProfileDialog.closeDialog();
        Assert.assertTrue(vNextBOProfileDialog.isUserProfileDialogClosed(), "User profile dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLogout(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.logOut();
        Assert.assertTrue(VNextBOLoginValidations.isLoginFormDisplayed(), "Login page hasn't been closed");
        VNextBOLoginSteps.userLogin(VNextBOConfigInfo.getInstance().getVNextBONadaMail(),
                VNextBOConfigInfo.getInstance().getVNextBOPassword());
        VNextBOLeftMenuInteractions.selectUsersMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseHelp(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openHelpPage();
        VNextBOUsersPageValidations.isHelpPageOpened();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNavigateBetweenPages(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(),
                "Bottom Last page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(), "Top First page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("3");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemSavesCurrentPageNumber(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.openPageByNumber(4);
        VNextBOUsersPageSteps.clickLogo();
        VNextBOLeftMenuInteractions.selectUsersMenu();
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("4");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeItemsPerPage(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("20");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemSavesCurrentValueItemsPerPage(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherSteps.openPageByNumber(2);
        VNextBOUsersPageSteps.clickLogo();
        VNextBOLeftMenuInteractions.selectUsersMenu();
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("2");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("50");
    }
}