package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOEditDeviceDialog;

public class VNextBOEditDeviceDialogSteps {

    private static void setNickNameField(String nickName) {
        Utils.clearAndType(new VNextBOEditDeviceDialog().getNickNameField(), nickName);
    }

    private static void setTeamField(String teamName) {
        VNextBOEditDeviceDialog editDeviceDialog = new VNextBOEditDeviceDialog();
        Utils.clickElement(editDeviceDialog.getTeamDropDownField());
        Utils.selectOptionInDropDownWithJs(editDeviceDialog.getTeamDropDownOptionsList(),
                editDeviceDialog.dropDownFieldOption(teamName));
    }

    private static void setTimeZoneField(String timeZone) {
        VNextBOEditDeviceDialog editDeviceDialog = new VNextBOEditDeviceDialog();
        Utils.clickElement(editDeviceDialog.getTimeZoneDropDownField());
        Utils.selectOptionInDropDownWithJs(editDeviceDialog.getTimeZoneDropDownOptionsList(),
                editDeviceDialog.dropDownFieldOption(timeZone));
    }

    public static void editDeviceFields(VNextBODeviceManagementData deviceManagementData) {

        setTeamField(deviceManagementData.getTeam());
        setNickNameField(deviceManagementData.getNickname());
        setTimeZoneField(deviceManagementData.getTimeZone());
    }

    public static void setNewDeviceValuesAndSubmit(VNextBODeviceManagementData deviceManagementData) {

        editDeviceFields(deviceManagementData);
        Utils.clickElement(new VNextBOEditDeviceDialog().getSubmitButton());
    }

    public static void clickCancelButton() {

        Utils.clickElement(new VNextBOEditDeviceDialog().getCancelButton());
    }

    public static void clickCloseXIconButton() {

        Utils.clickElement(new VNextBOEditDeviceDialog().getXIconButton());
    }
}