package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.deviceManagement.VNextBODeviceManagementData;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOAddNewDeviceDialog;

public class VNextBOAddNewDeviceDialogSteps {

    private static void setExpiresInHoursField(String hours) {

        VNextBOAddNewDeviceDialog addNewDeviceDialog = new VNextBOAddNewDeviceDialog();
        Utils.clickElement(addNewDeviceDialog.getExpiresInHoursField());
        Utils.sendKeysWithJS(addNewDeviceDialog.getHoursInputFieldToBeEdited(), hours);
        Utils.clickElement(addNewDeviceDialog.getExpiresInLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewDeviceDialog.getExpiresInHoursField(), hours);
    }

    private static void setExpiresInMinutesField(String minutes) {

        VNextBOAddNewDeviceDialog addNewDeviceDialog = new VNextBOAddNewDeviceDialog();
        Utils.clickElement(addNewDeviceDialog.getExpiresInMinutesField());
        Utils.sendKeysWithJS(addNewDeviceDialog.getMinutesInputFieldToBeEdited(), minutes);
        Utils.clickElement(addNewDeviceDialog.getExpiresInLabel());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(addNewDeviceDialog.getExpiresInMinutesField(), minutes);
    }

    private static void setNickNameField(String nickName) {
        Utils.clearAndType(new VNextBOAddNewDeviceDialog().getNickNameField(), nickName);
    }

    private static void setPhoneNumberField(String phoneNumber) {
        Utils.clearAndType(new VNextBOAddNewDeviceDialog().getPhoneNumberField(), phoneNumber);
    }

    private static void setTeamField(String teamName) {
        VNextBOAddNewDeviceDialog addNewDeviceDialog = new VNextBOAddNewDeviceDialog();
        Utils.clickElement(addNewDeviceDialog.getTeamDropDownField());
        Utils.selectOptionInDropDownWithJs(addNewDeviceDialog.getTeamDropDownOptionsList(),
                addNewDeviceDialog.dropDownFieldOption(teamName));
    }

    private static String setLicenseField(String license) {
        VNextBOAddNewDeviceDialog addNewDeviceDialog = new VNextBOAddNewDeviceDialog();
        Utils.clickElement(addNewDeviceDialog.getLicenseDropDownField());
        String firstLicenseNumber = Utils.getText(addNewDeviceDialog.getFirstLicenseDropDownFieldOptions());
        Utils.selectOptionInDropDownWithJs(addNewDeviceDialog.getLicenseDropDownOptionsList(),
                addNewDeviceDialog.getFirstLicenseDropDownFieldOptions());
        return firstLicenseNumber;
    }

    private static void setTimeZoneField(String timeZone) {
        VNextBOAddNewDeviceDialog addNewDeviceDialog = new VNextBOAddNewDeviceDialog();
        Utils.clickElement(addNewDeviceDialog.getTimeZoneDropDownField());
        Utils.selectOptionInDropDownWithJs(addNewDeviceDialog.getTimeZoneDropDownOptionsList(),
                addNewDeviceDialog.dropDownFieldOption(timeZone));
    }

    private static void setTechnicianField(String technicianName) {
        VNextBOAddNewDeviceDialog addNewDeviceDialog = new VNextBOAddNewDeviceDialog();
        Utils.clickElement(addNewDeviceDialog.getTechnicianDropDownField());
        Utils.selectOptionInDropDownWithJs(addNewDeviceDialog.getTechnicianDropDownOptionsList(),
                addNewDeviceDialog.dropDownFieldOption(technicianName));
    }

    public static String setAllAddNewDeviceFields(VNextBODeviceManagementData deviceManagementData) {

        setExpiresInHoursField(deviceManagementData.getHours());
        setExpiresInMinutesField(deviceManagementData.getMinutes());
        setTeamField(deviceManagementData.getTeam());
        setNickNameField(deviceManagementData.getNickname());
        String licenseNumber = setLicenseField(deviceManagementData.getLicenseNumber());
        setTimeZoneField(deviceManagementData.getTimeZone());
        setTechnicianField(deviceManagementData.getTechnician());
        setPhoneNumberField(deviceManagementData.getPhoneNumber());
        return licenseNumber;
    }

    public static void setNewDeviceValuesAndSubmit(VNextBODeviceManagementData deviceManagementData) {

        setAllAddNewDeviceFields(deviceManagementData);
        Utils.clickElement(new VNextBOAddNewDeviceDialog().getOkButton());
    }

    public static void cancelAddingNewDeviceCancelButton() {

        Utils.clickElement(new VNextBOAddNewDeviceDialog().getCancelButton());
    }

    public static void cancelAddingNewDeviceXIcon() {

        Utils.clickElement(new VNextBOAddNewDeviceDialog().getXIconButton());
    }
}