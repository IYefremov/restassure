package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonObjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOClientsArchiveRestoreTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsArchiveRestoreData.json";
    private VNextBOLoginScreenWebPage loginPage;
    String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = DATA_FILE;
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectClientsMenu();
    }

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
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