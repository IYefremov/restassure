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
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeActiveTabDetails(String rowID, String description, JSONObject testData) {

        VNextBOActiveDevicesTabValidations.verifyDevicesTableContainsCorrectColumns();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelAddingNewDeviceCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        String licenseNumber = VNextBOAddNewDeviceDialogSteps.setAllAddNewDeviceFields(deviceData);
        deviceData.setLicenseNumber(licenseNumber);
        VNextBOAddNewDeviceDialogSteps.cancelAddingNewDeviceCancelButton();
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelAddingNewDeviceXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.clickAddNewDeviceButton();
        String licenseNumber = VNextBOAddNewDeviceDialogSteps.setAllAddNewDeviceFields(deviceData);
        deviceData.setLicenseNumber(licenseNumber);
        VNextBOAddNewDeviceDialogSteps.cancelAddingNewDeviceXIcon();
        VNextBODeviceManagementSteps.openPendingRegistrationDevicesTab();
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(deviceData.getNickname());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewDevice(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.addNewDevice(deviceData);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePendingRegistrationsDevices(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.addNewDevice(deviceData);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsCorrectColumns();
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangePhoneNumberOfPendingRegistrationDevice(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.addNewDevice(deviceData);
        VNextBOPendingRegistrationTabSteps.changePhoneNumberByDeviceNickName(deviceData.getNickname(), "+380952222222");
        VNextBOPendingRegistrationsTabValidations.verifyPhoneNumberIsCorrectByDeviceNickName(deviceData.getNickname(), "Phone", "+380952222222");
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelDeletionPendingRegistrationDeviceCancelButton(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.addNewDevice(deviceData);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCancelButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelDeletionPendingRegistrationDeviceXIcon(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.addNewDevice(deviceData);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        VNextBOPendingRegistrationsTabValidations.verifyDevicesTableContainsDevice(deviceData.getNickname());
        VNextBOPendingRegistrationTabSteps.deletePendingRegistrationDeviceByUser(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteDeviceFromPendingRegistrationsList(String rowID, String description, JSONObject testData) {

        VNextBODeviceManagementData deviceData = JSonDataParser.getTestDataFromJson(testData, VNextBODeviceManagementData.class);
        VNextBODeviceManagementSteps.addNewDevice(deviceData);
        VNextBOPendingRegistrationTabSteps.clickDeleteButtonForDeviceByNickName(deviceData.getNickname());
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogSteps.clickYesButton();
        VNextBOModalDialogValidations.verifyDialogIsClosed(confirmationDialog);
        if (VNextBOPendingRegistrationTabSteps.checkWhetherDevicesNotFoundMessageIsDisplayed())
            VNextBOPendingRegistrationsTabValidations.verifyPendingRegistrationDevicesNotFoundMessageIsCorrect();
        else VNextBOPendingRegistrationsTabValidations.verifyDevicesTableDoesNotContainDevice(deviceData.getNickname());
        VNextBODeviceManagementSteps.openActiveDevicesTab();
    }
}