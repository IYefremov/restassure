package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.partsManagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsManagementWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsOrdersListPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsManagementWebPageValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsOrdersListPanelValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;

public class VNextBOPartsManagementDashboardTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenOperationsPartsManagementWithFullSetOfElements(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelValidations.verifySearchFieldIsDisplayed();
        VNextBOPartsManagementWebPageValidations.verifyTermsAndConditionsLinkIsDisplayed();
        VNextBOPartsManagementWebPageValidations.verifyPrivacyPolicyLinkIsDisplayed();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelValidations.verifyOrdersListPanelIsDisplayed();
        VNextBOPartsManagementWebPageValidations.verifyPastDuePartsBoxIsDisplayed();
        VNextBOPartsManagementWebPageValidations.verifyInProgressBoxIsDisplayed();
        VNextBOPartsManagementWebPageValidations.verifyCompletedBoxIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePartsDetailsOfDifferentROs(String rowID, String description, JSONObject testData) {

        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(3);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithPastDuePartsParts(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageValidations.verifyPastDuePartsBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(2);
        VNextBOPartsDetailsPanelValidations.verifyEtaDateIsCorrectDependsOnPhase(0, "Past Due Parts");
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithInProgressParts(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
        VNextBOPartsManagementWebPageValidations.verifyInProgressBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(4);
        VNextBOPartsDetailsPanelValidations.verifyEtaDateIsCorrectDependsOnPhase(0, "In Progress");
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithCompletedParts(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
        VNextBOPartsManagementWebPageValidations.verifyCompletedBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(5);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithPartDuePartsAndInProgressParts(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
        VNextBOPartsManagementWebPageValidations.verifyPastDuePartsBoxIsHighlighted();
        VNextBOPartsManagementWebPageValidations.verifyInProgressBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(6);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithPartDuePartsAndCompletedParts(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
        VNextBOPartsManagementWebPageValidations.verifyPastDuePartsBoxIsHighlighted();
        VNextBOPartsManagementWebPageValidations.verifyCompletedBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(7);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithInProgressAndCompletedParts(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
        VNextBOPartsManagementWebPageValidations.verifyInProgressBoxIsHighlighted();
        VNextBOPartsManagementWebPageValidations.verifyCompletedBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(8);
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithPastDuePartsInProgressAndCompletedParts(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
        VNextBOPartsManagementWebPageValidations.verifyPastDuePartsBoxIsHighlighted();
        VNextBOPartsManagementWebPageValidations.verifyInProgressBoxIsHighlighted();
        VNextBOPartsManagementWebPageValidations.verifyCompletedBoxIsHighlighted();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsDetailsPanelValidations.verifyDetailsPanelIsDisplayed();
        VNextBOPartsManagementWebPageSteps.clickPastDuePartsButton();
        VNextBOPartsManagementWebPageSteps.clickInProgressButton();
        VNextBOPartsManagementWebPageSteps.clickCompletedButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openIntercomMessenger();
        VNextBOPartsManagementWebPageValidations.verifyIntercomMessengerIsOpened();
        VNextBOPartsManagementWebPageSteps.closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseTermsAndConditions(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickTermsAndConditionsLink();
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
    public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.clickPrivacyPolicyLink();
        VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "Privacy Policy",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
    }
}