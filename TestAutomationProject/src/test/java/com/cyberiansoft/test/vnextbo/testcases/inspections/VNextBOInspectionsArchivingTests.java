package com.cyberiansoft.test.vnextbo.testcases.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.inspections.VNextBOInspectionsDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionsPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOInspectionsArchivingTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getInspectionsArchivingTD();
        VNextBOLeftMenuInteractions.selectInspectionsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelArchivingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.searchInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageValidations.verifyArchiveIconIsDisplayed();
        VNextBOInspectionsPageSteps.clickInspectionDetailsArchiveIcon();
        VNextBOInspectionsPageSteps.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyYesButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyNoButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmArchivingDialog);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelArchivingWithCloseButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.searchInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageSteps.clickInspectionDetailsArchiveIcon();
        VNextBOInspectionsPageSteps.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmArchivingDialog);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanArchiveInspection(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.searchInspectionByCustomTimeFrameAndNumber(data.getInspectionId(), data.getFromDate(), data.getToDate());
        VNextBOInspectionsPageSteps.clickInspectionDetailsArchiveIcon();
        VNextBOInspectionsPageSteps.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmArchivingDialog);
        VNextBOInspectionsPageValidations.verifyInspectionStatusIsCorrect(data.getInspectionId(), "Archived");
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionArchivingReason(),
                "Inspection archived with reason: Test",
                "Archiving reason hasn't been correct");
        VNextBOInspectionsPageSteps.unArchiveInspectionFromInspectionDetails();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelUnArchivingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveInspectionFromInspectionDetails(data, "Reason: Test");
        VNextBOInspectionsPageValidations.verifyUnArchiveIconIsDisplayed();
        VNextBOInspectionsPageSteps.clickInspectionDetailsUnArchiveIcon();
        VNextBOModalDialog confirmUnArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyYesButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyNoButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmUnArchivingDialog);
        VNextBOInspectionsPageSteps.unArchiveInspectionFromInspectionDetails();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelUnArchivingWithCloseButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveInspectionFromInspectionDetails(data, "Reason: Test");
        VNextBOInspectionsPageSteps.clickInspectionDetailsUnArchiveIcon();
        VNextBOModalDialog confirmUnArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmUnArchivingDialog);
        VNextBOInspectionsPageSteps.unArchiveInspectionFromInspectionDetails();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUnArchiveInspection(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveFirstInspectionFromInspectionsList(data, "Reason: Test");
        VNextBOInspectionsPageValidations.verifyInspectionStatusIsCorrect(data.getInspectionId(), "Archived");
        VNextBOInspectionsPageSteps.unArchiveInspectionFromInspectionDetails();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUnArchiveInspectionsFromInspectionsList(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveFirstInspectionFromInspectionsList(data, "Reason: Test");
        VNextBOInspectionsPageSteps.unArchiveInspectionFromInspectionsList();
        VNextBOInspectionsPageValidations.verifyInspectionStatusIsCorrect(data.getInspectionId(), "New");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}
