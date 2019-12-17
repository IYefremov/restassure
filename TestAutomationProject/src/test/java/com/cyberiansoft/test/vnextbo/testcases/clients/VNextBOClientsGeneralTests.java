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
import org.testng.annotations.Test;

public class VNextBOClientsGeneralTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsTD();
        VNextBOLeftMenuInteractions.selectClientsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenClientsPageWithFullSetOfElements(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageValidations.verifyAddNewClientBtnIsDisplayed();
        VNextBOClientsPageValidations.verifyActiveTabIsDisplayed();
        VNextBOClientsPageValidations.verifyArchivedTabIsDisplayed();
        VNextBOClientsPageValidations.verifyClientsTableIsDisplayed();
        VNextBOSearchPanelValidations.verifySearchPanelIsDisplayed();
        VNextBOPageSwitcherValidations.verifyPageNavigationElementsAreDisplayed();
        VNextBOClientsPageValidations.verifyTermsAndConditionsLinkIsDisplayed();
        VNextBOClientsPageValidations.verifyPrivacyPolicyLinkIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyIntercomMessengerCanBeOpenedClosed(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.openIntercomMessenger();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOUsersPageValidations.verifyIntercomMessengerIsOpened();
        VNextBOUsersPageSteps.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedOkButton(String rowID, String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTermsAndConditionsCanBeOpenedAndClosedXIcon(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedOkButton(String rowID, String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPrivacyPolicyCanBeOpenedAndClosedXIcon(String rowID, String description, JSONObject testData) {

        VNextBOUsersPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSwitchBetweenPagesActiveTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openActiveTab();
        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(false),
                "Bottom Last page button has been clickable.");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(false),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(false), "Top First page button has been clickable.");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(false), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSwitchBetweenPagesArchivedTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(false),
                "Bottom Last page button has been clickable.");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(false),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(false), "Top First page button has been clickable.");
        Assert.assertTrue(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(false), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUsePageFilterActiveTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openActiveTab();
        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("20");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("50");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(50);
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("100");
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(100);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUsePageFilterArchivedTab(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("20");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("50");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(50);
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("100");
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(100);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");

    }
}