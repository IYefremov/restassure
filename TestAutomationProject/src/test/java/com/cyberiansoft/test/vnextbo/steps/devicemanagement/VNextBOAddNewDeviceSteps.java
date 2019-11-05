package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOAddNewDeviceDialogInteractions;

public class VNextBOAddNewDeviceSteps {

    private static void setAddNewDeviceFields(VNextBODeviceManagementData deviceManagementData, String nickName) {
        final VNextBOAddNewDeviceDialogInteractions addNewDeviceDialogInteractions = new VNextBOAddNewDeviceDialogInteractions();
        addNewDeviceDialogInteractions.setExpiresInHours(deviceManagementData.getHours());
        addNewDeviceDialogInteractions.setExpiresInMinutes(deviceManagementData.getMinutes());
        addNewDeviceDialogInteractions.setTeam(deviceManagementData.getTeam());
        addNewDeviceDialogInteractions.setNickName(nickName);
        addNewDeviceDialogInteractions.setRandomLicense();
        addNewDeviceDialogInteractions.setTimeZone(deviceManagementData.getTimeZone());
        addNewDeviceDialogInteractions.setTechnician(deviceManagementData.getTechnician());
        addNewDeviceDialogInteractions.setRandomPhoneNumber();
    }

    public static void setNewDeviceValuesAndSubmit(VNextBODeviceManagementData deviceManagementData, String nickName) {
        setAddNewDeviceFields(deviceManagementData, nickName);
        new VNextBOAddNewDeviceDialogInteractions().clickSubmitButton();
    }

    public static void setNewDeviceValuesAndCancel(VNextBODeviceManagementData deviceManagementData, String nickName) {
        setAddNewDeviceFields(deviceManagementData, nickName);
        new VNextBOAddNewDeviceDialogInteractions().clickCancelButton();
    }
}