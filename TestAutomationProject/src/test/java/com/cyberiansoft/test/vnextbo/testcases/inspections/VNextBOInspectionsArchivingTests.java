package com.cyberiansoft.test.vnextbo.testcases.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionsDetailsData;
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
        VNextBOInspectionsPageSteps.clickArchiveIcon();
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
        VNextBOInspectionsPageSteps.clickArchiveIcon();
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
        VNextBOInspectionsPageSteps.clickArchiveIcon();
        VNextBOInspectionsPageSteps.selectArchiveReason("Reason: Test");
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmArchivingDialog);
        VNextBOInspectionsPageValidations.verifyInspectionStatusIsCorrect(data.getInspectionId(), "Archived");
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionArchivingReason(),
                "Inspection archived with reason: Test",
                "Archiving reason hasn't been correct");
        VNextBOInspectionsPageSteps.unArchiveInspection();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelUnArchivingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveInspection(data, "Reason: Test");
        VNextBOInspectionsPageValidations.verifyUnArchiveIconIsDisplayed();
        VNextBOInspectionsPageSteps.clickUnArchiveIcon();
        VNextBOModalDialog confirmUnArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyYesButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyNoButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmUnArchivingDialog);
        VNextBOInspectionsPageSteps.unArchiveInspection();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelUnArchivingWithCloseButton(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveInspection(data, "Reason: Test");
        VNextBOInspectionsPageSteps.clickUnArchiveIcon();
        VNextBOModalDialog confirmUnArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmUnArchivingDialog);
        VNextBOInspectionsPageSteps.unArchiveInspection();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUnArchiveInspection(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionsDetailsData.class);
        VNextBOInspectionsPageSteps.archiveInspection(data, "Reason: Test");
        VNextBOInspectionsPageSteps.clickUnArchiveIcon();
        VNextBOModalDialog confirmArchivingDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmArchivingDialog);
        VNextBOInspectionsPageValidations.verifyInspectionStatusIsCorrect(data.getInspectionId(), "New");
    }
}
