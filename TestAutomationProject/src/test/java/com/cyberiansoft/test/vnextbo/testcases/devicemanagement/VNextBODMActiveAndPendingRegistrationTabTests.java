package com.cyberiansoft.test.vnextbo.testcases.devicemanagement;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOAuditLogDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.*;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOActiveDevicesTabValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOAuditLogsDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.devicemanagement.VNextBOPendingRegistrationsTabValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBODMActiveAndPendingRegistrationTabTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getDeviceManagementActiveAndPendingRegistrationTabsTD();
        VNextBOLeftMenuInteractions.selectDeviceManagementMenu();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBODeviceManagementSteps.openActiveDevicesTab();
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
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanCancelAddingNewDeviceXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setAllAddNewDeviceFields(deviceData);
        VNextBOAddNewDeviceDialogSteps.cancelAddingNewDeviceXIcon();
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanCancelDeviceLicenseDeletionCancelButton(String rowID, String description, JSONObject testData) {

        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickActionsButtonForDevice(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickDeleteButtonForDevice(testDeviceName);
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCancelDeviceLicenseDeletionXIcon(String rowID, String description, JSONObject testData) {

        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickActionsButtonForDevice(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickDeleteButtonForDevice(testDeviceName);
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanCloseAuditLogWindow(String rowID, String description, JSONObject testData) {

        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickActionsButtonForDevice(testDeviceName);
        VNextBOActiveDevicesTabSteps.clickAuditLogButtonForDevice(testDeviceName);
        VNextBOAuditLogsDialogValidations.verifyDialogIsDisplayed();
        VNextBOAuditLogDialog auditLogDialog = new VNextBOAuditLogDialog();
        VNextBOAuditLogsDialogSteps.closeDialog();
        VNextBOAuditLogsDialogValidations.verifyDialogIsClosed(auditLogDialog);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanEditDeviceSettingsAndNotSaveXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabSteps.openEditDeviceDialog(testDeviceName);
        VNextBOEditDeviceDialogSteps.editDeviceFields(deviceData);
        VNextBOEditDeviceDialogSteps.clickCloseXIconButton();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(deviceData.getNickname());
        VNextBOActiveDevicesTabValidations.verifyActiveDevicesNotFoundMessageIsCorrect();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanEditDeviceSettingsAndNotSaveCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        String testDeviceName = "AndroidZak";
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabSteps.openEditDeviceDialog(testDeviceName);
        VNextBOEditDeviceDialogSteps.editDeviceFields(deviceData);
        VNextBOEditDeviceDialogSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(deviceData.getNickname());
        VNextBOActiveDevicesTabValidations.verifyActiveDevicesNotFoundMessageIsCorrect();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(testDeviceName);
        VNextBOActiveDevicesTabValidations.verifySearchResultIsCorrectForColumnWithText("Name", testDeviceName);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanAddNewDevice(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        VNextBOAddNewDeviceDialogSteps.setNewDeviceValuesAndSubmit(deviceData);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSeePendingRegistrationsDevices(String rowID, String description, JSONObject testData) {

        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsCorrectColumns();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanChangePhoneNumberOfPendingRegistrationDevice(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        VNextBOPendingRegistrationTabSteps.changePhoneNumberByDeviceNickName(deviceData.getNickname(), deviceData.getPhoneNumber());
        VNextBOPendingRegistrationsTabValidations.verifyPhoneNumberIsCorrectByDeviceNickName(deviceData.getNickname(), "Phone", deviceData.getPhoneNumber());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanCancelDeletionPendingRegistrationDeviceCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanCancelDeletionPendingRegistrationDeviceXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanDeleteDeviceFromPendingRegistrationsList(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(deviceData.getNickname());
    }
}