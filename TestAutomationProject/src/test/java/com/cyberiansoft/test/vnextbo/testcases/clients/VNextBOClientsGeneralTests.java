package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOClientsGeneralTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        VNextBOLeftMenuInteractions.selectClientsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenClientsPageWithFullSetOfElements(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageValidations.isAddNewClientBtnDisplayed();
        VNextBOClientsPageValidations.isActiveTabDisplayed();
        VNextBOClientsPageValidations.isArchivedTabDisplayed();
        VNextBOClientsPageValidations.isClientsTableDisplayed();
        VNextBOSearchPanelValidations.isSearchPanelDisplayed();
        VNextBOPageSwitcherValidations.arePageNavigationElementsDisplayed();
        VNextBOClientsPageValidations.isTermsAndConditionsLinkDisplayed();
        VNextBOClientsPageValidations.isPrivacyPolicyLinkDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyIntercomMessengerCanBeOpenedClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openIntercomMessenger();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOUsersPageValidations.isIntercomMessengerOpened();
        VNextBOUsersPageSteps.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedOkButton(String rowID, String description, JSONObject testData) {

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
    public void verifyTermsAndConditionsCanBeOpenedAndClosedXIcon(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isCloseButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedOkButton(String rowID, String description, JSONObject testData) {

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
    public void verifyPrivacyPolicyCanBeOpenedAndClosedXIcon(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isCloseButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSwitchBetweenPagesActiveTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openActiveTab();
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
    public void verifyUserCanSwitchBetweenPagesArchivedTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
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
    public void verifyUserCanUsePageFilterActiveTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openActiveTab();
        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("20");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("50");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(50);
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("100");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(100);
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUsePageFilterArchivedTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("20");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("50");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(50);
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("100");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(100);
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");

    }
}