package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOClientsArchiveRestoreTests extends BaseTestCase {

    String testClientName = "RozstalnoyCO";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsArchiveRestoreTD();
        VNextBOLeftMenuInteractions.selectClientsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelArchiveXIcon(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testClientName);
        VNextBOClientsPageSteps.clickActionsButtonForClient(testClientName);
        VNextBOClientsPageSteps.clickArchiveDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCancelButtonIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", testClientName);
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelArchiveCancelButton(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testClientName);
        VNextBOClientsPageSteps.clickActionsButtonForClient(testClientName);
        VNextBOClientsPageSteps.clickArchiveDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", testClientName);
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanArchiveClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.archiveClient(testClientName);
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testClientName);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", testClientName);
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageSteps.restoreClient(testClientName);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOClientsPageSteps.openActiveTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelRestoreXIcon(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.archiveClient(testClientName);
        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOClientsPageSteps.clickActionsButtonForClient(testClientName);
        VNextBOClientsPageSteps.clickRestoreDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCancelButtonIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", testClientName);
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageSteps.restoreClient(testClientName);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOClientsPageSteps.openActiveTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelRestoreCancelButton(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.archiveClient(testClientName);
        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOClientsPageSteps.clickActionsButtonForClient(testClientName);
        VNextBOClientsPageSteps.clickRestoreDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", testClientName);
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageSteps.restoreClient(testClientName);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOClientsPageSteps.openActiveTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRestoreClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.archiveClient(testClientName);
        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOClientsPageSteps.restoreClient(testClientName);
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOClientsPageSteps.openActiveTab();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testClientName);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", testClientName);
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}