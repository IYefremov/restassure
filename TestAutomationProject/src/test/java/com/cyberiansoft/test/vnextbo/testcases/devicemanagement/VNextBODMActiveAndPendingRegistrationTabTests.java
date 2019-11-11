package com.cyberiansoft.test.vnextbo.testcases.devicemanagement;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.*;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.devicemanagement.VNextBOActiveDevicesTabValidations;
import com.cyberiansoft.test.vnextbo.verifications.devicemanagement.VNextBOAuditLogsDialogValidations;
import com.cyberiansoft.test.vnextbo.verifications.devicemanagement.VNextBOPendingRegistrationsValidations;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBODMActiveAndPendingRegistrationTabTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/devicemanagement/VNextBODMActiveAndPendingRegistrationTabsData.json";
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
        leftMenuInteractions.selectDeviceManagementMenu();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSeeActiveTabDetails(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabValidations.verifyDevicesTableContainsCorrectColumns();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCancelAddingNewDeviceCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setAllAddNewDeviceFields(deviceData);
        VNextBOAddNewDeviceDialogSteps.cancelAddingNewDeviceCancelButton();
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        VNextBOPendingRegistrationsValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanCancelAddingNewDeviceXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setAllAddNewDeviceFields(deviceData);
        VNextBOAddNewDeviceDialogSteps.cancelAddingNewDeviceXIcon();
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        VNextBOPendingRegistrationsValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanCancelDeviceLicenseDeletionCancelButton(String rowID, String description, JSONObject testData) {

        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickActionsButtonForDevice(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickDeleteButtonForDevice(testDeviceName);
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCancelDeviceLicenseDeletionXIcon(String rowID, String description, JSONObject testData) {

        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickActionsButtonForDevice(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickDeleteButtonForDevice(testDeviceName);
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanCloseAuditLogWindow(String rowID, String description, JSONObject testData) {

        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickActionsButtonForDevice(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickAuditLogButtonForDevice(testDeviceName);
        VNextBOAuditLogsDialogValidations.verifyDialogIsDisplayed(true);
        VNextBOAuditLogsDialogSteps.closeDialog();
        VNextBOAuditLogsDialogValidations.verifyDialogIsDisplayed(false);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanEditDeviceSettingsANdNotSaveXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabSteps.openEditDeviceDialog(testDeviceName);
        VNextBOEditDeviceDialogSteps.editDeviceFields(deviceData);
        VNextBOEditDeviceDialogSteps.clickCloseXIconButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(deviceData.getNickname());
        VNextBOActiveDevicesTabValidations.verifyActiveDevicesNotFoundMessageIsCorrect();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanEditDeviceSettingsANdNotSaveCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabSteps.openEditDeviceDialog(testDeviceName);
        VNextBOEditDeviceDialogSteps.editDeviceFields(deviceData);
        VNextBOEditDeviceDialogSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(deviceData.getNickname());
        VNextBOActiveDevicesTabValidations.verifyActiveDevicesNotFoundMessageIsCorrect();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanAddNewDevice(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setNewDeviceValuesAndSubmit(deviceData);
        VNextBOPendingRegistrationsValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSeePendingRegistrationsDevices(String rowID, String description, JSONObject testData) {

        VNextBOPendingRegistrationsValidations.verifyDevicesTableContainsCorrectColumns();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanChangePhoneNumberOfPendingRegistrationDevice(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        VNextBOPendingRegistrationTabSteps.changePhoneNumberByDeviceNickName(deviceData.getNickname(), deviceData.getPhoneNumber());
        VNextBOPendingRegistrationsValidations.verifyPhoneNumberIsCorrectByDeviceNickName(deviceData.getNickname(), "Phone", deviceData.getPhoneNumber());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanCancelDeletionPendingRegistrationDeviceCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanCancelDeletionPendingRegistrationDeviceXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanDeleteDeviceFromPendingRegistrationsList(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
    }
}