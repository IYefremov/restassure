package com.cyberiansoft.test.vnextbo.steps.deviceManagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOAddNewDeviceDialogInteractions;

public class VNextBOAddNewDeviceSteps {

    private VNextBOAddNewDeviceDialogInteractions addNewDeviceDialogInteractions;

    public VNextBOAddNewDeviceSteps() {
        addNewDeviceDialogInteractions = new VNextBOAddNewDeviceDialogInteractions();
    }

    public void setAddNewDeviceFields(VNextBODeviceManagementData deviceManagementData, String nickName) {
        addNewDeviceDialogInteractions.setExpiresInHours(deviceManagementData.getHours());
        addNewDeviceDialogInteractions.setExpiresInMinutes(deviceManagementData.getMinutes());
        addNewDeviceDialogInteractions.setTeam(deviceManagementData.getTeam());
        addNewDeviceDialogInteractions.setNickName(nickName);
        addNewDeviceDialogInteractions.setRandomLicense();
        addNewDeviceDialogInteractions.setTimeZone(deviceManagementData.getTimeZone());
        addNewDeviceDialogInteractions.setTechnician(deviceManagementData.getTechnician());
        addNewDeviceDialogInteractions.setRandomPhoneNumber();
    }

    public void setNewDeviceValuesAndSubmit(VNextBODeviceManagementData deviceManagementData, String nickName) {
        setAddNewDeviceFields(deviceManagementData, nickName);
        addNewDeviceDialogInteractions.clickSubmitButton();
    }

    public void setNewDeviceValuesAndCancel(VNextBODeviceManagementData deviceManagementData, String nickName) {
        setAddNewDeviceFields(deviceManagementData, nickName);
        addNewDeviceDialogInteractions.clickCancelButton();
    }
}