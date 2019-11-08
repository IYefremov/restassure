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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOClientsArchiveRestoreTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsArchiveRestoreTD();
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        VNextBOLeftMenuInteractions.selectClientsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanCancelArchiveXIcon(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageSteps.clickActionsButtonForClient("RozstalnoyCO");
        VNextBOClientsPageSteps.clickArchiveDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        VNextBOModalDialogValidations.isCloseButtonDisplayed();
        VNextBOModalDialogValidations.isCancelButtonDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(confirmationDialog);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "RozstalnoyCO");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelArchiveCancelButton(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageSteps.clickActionsButtonForClient("RozstalnoyCO");
        VNextBOClientsPageSteps.clickArchiveDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.isDialogClosed(confirmationDialog);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "RozstalnoyCO");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanArchiveClient(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageSteps.archiveClient("RozstalnoyCO");
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "RozstalnoyCO");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanCancelRestoreXIcon(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageSteps.clickActionsButtonForClient("RozstalnoyCO");
        VNextBOClientsPageSteps.clickRestoreDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        VNextBOModalDialogValidations.isCloseButtonDisplayed();
        VNextBOModalDialogValidations.isCancelButtonDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.isDialogClosed(confirmationDialog);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "RozstalnoyCO");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCancelRestoreCancelButton(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageSteps.clickActionsButtonForClient("RozstalnoyCO");
        VNextBOClientsPageSteps.clickRestoreDropMenuButton();
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.isDialogClosed(confirmationDialog);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "RozstalnoyCO");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanRestoreClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsPageSteps.openArchivedTab();
        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageSteps.restoreClient("RozstalnoyCO");
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOClientsPageSteps.openActiveTab();
        VNextBOSearchPanelSteps.searchByText("RozstalnoyCO");
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", "RozstalnoyCO");
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}