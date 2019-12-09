package com.cyberiansoft.test.vnextbo.testcases.devicemanagement;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOActiveDevicesTabValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBODeviceManagementPageValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

public class VNextBODeviceManagementGeneralTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getDeviceManagementTD();
        com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanOpenDeviceManagementPageWithFullSetOfElements(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabValidations.verifyDevicesTableIsDisplayed();
        VNextBODeviceManagementPageValidations.verifyActiveDevicesTabIsDisplayed();
        VNextBODeviceManagementPageValidations.verifyPendingRegistrationsTabIsDisplayed();
        VNextBODeviceManagementPageValidations.verifyAddNewDeviceButtonIsDisplayed();
        VNextBOSearchPanelValidations.verifySearchFieldIsDisplayed();
        VNextBOPageSwitcherValidations.verifyPageNavigationElementsAreDisplayed();
        VNextBODeviceManagementPageValidations.verifyTermsAndConditionsLinkIsDisplayed();
        VNextBODeviceManagementPageValidations.verifyPrivacyPolicyLinkIsDisplayed();
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("10");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSelectXItemsPerPage(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.changeItemsPerPage("20");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("20");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOActiveDevicesTabValidations.verifyCorrectRecordsAmountIsDisplayed(20);
        VNextBOPageSwitcherSteps.changeItemsPerPage("50");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("50");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOActiveDevicesTabValidations.verifyCorrectRecordsAmountIsDisplayed(50);
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("100");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOActiveDevicesTabValidations.verifyCorrectRecordsAmountIsDisplayed(100);
        VNextBOPageSwitcherSteps.changeItemsPerPage("10");
        VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("10");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOActiveDevicesTabValidations.verifyCorrectRecordsAmountIsDisplayed(10);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanNavigateBetweenPagesOfActiveDevicesTab(String rowID, String description, JSONObject testData) {

        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
        VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(),
                "Bottom Last page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.verifyHeaderLastPageButtonIsClickable(),
                "Top Last page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.verifyHeaderFirstPageButtonIsClickable(), "Top First page button has been clickable.");
        Assert.assertFalse(VNextBOPageSwitcherValidations.verifyFooterFirstPageButtonIsClickable(), "Bottom First page button has been clickable.");
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
        VNextBOPageSwitcherSteps.openPageByNumber(3);
        VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
        VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanOpenAndCloseTermsAndConditionsOkButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanOpenAndCloseTermsAndConditionsXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickTermsAndConditionsLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanOpenAndClosePrivacyPolicyOkButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanOpenAndClosePrivacyPolicyXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementSteps.openIntercomMessenger();
        WaitUtilsWebDriver.waitForLoading();
        VNextBODeviceManagementPageValidations.verifyIntercomMessengerIsOpened();
        VNextBODeviceManagementSteps.closeIntercom();
    }
}