package com.cyberiansoft.test.vnextbo.testcases.deviceManagement;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.deviceManagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonObjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.deviceManagement.VNextBODeviceManagementPageValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBODeviceManagementGeneralTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getDeviceManagementTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanOpenDeviceManagementPageWithFullSetOfElements(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementPageValidations.isDevicesTableDisplayed();
        VNextBODeviceManagementPageValidations.isActiveDevicesTabDisplayed();
        VNextBODeviceManagementPageValidations.isPendingRegistrationsTabDisplayed();
        VNextBODeviceManagementPageValidations.isAddNewDeviceButtonDisplayed();
        VNextBOSearchPanelValidations.isSearchFieldDisplayed();
        VNextBOPageSwitcherValidations.arePageNavigationElementsDisplayed();
        VNextBODeviceManagementPageValidations.isTermsAndConditionsLinkDisplayed();
        VNextBODeviceManagementPageValidations.isPrivacyPolicyLinkDisplayed();
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("10");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSelectXItemsPerPage(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("20");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBODeviceManagementPageValidations.isCorrectRecordsAmountDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("50");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBODeviceManagementPageValidations.isCorrectRecordsAmountDisplayed(50);
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("100");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBODeviceManagementPageValidations.isCorrectRecordsAmountDisplayed(100);
        VNextBOPageSwitcherSteps.changeItemsPerPage("10");
        VNextBOPageSwitcherValidations.isItemsPerPageNumberCorrect("10");
        VNextBOPageSwitcherValidations.isOpenedPageNumberCorrect("1");
        VNextBODeviceManagementPageValidations.isCorrectRecordsAmountDisplayed(10);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanNavigateBetweenPagesOfActiveDevicesTab(String rowID, String description, JSONObject testData) {

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
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanOpenAndCloseTermsAndConditionsOkButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanOpenAndCloseTermsAndConditionsXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanOpenAndClosePrivacyPolicyOkButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanOpenAndClosePrivacyPolicyXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.openIntercomMessenger();
        WaitUtilsWebDriver.waitForLoading();
        VNextBODeviceManagementPageValidations.isIntercomMessengerOpened();
        VNextBODeviceManagementSteps.closeIntercom();
    }
}