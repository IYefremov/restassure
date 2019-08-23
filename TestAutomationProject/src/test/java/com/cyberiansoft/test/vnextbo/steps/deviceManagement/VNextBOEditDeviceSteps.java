package com.cyberiansoft.test.vnextbo.steps.deviceManagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOEditDeviceDialogInteractions;
import org.testng.Assert;

public class VNextBOEditDeviceSteps {

    private VNextBOEditDeviceDialogInteractions editDeviceDialogInteractions;

    public VNextBOEditDeviceSteps() {
        editDeviceDialogInteractions = new VNextBOEditDeviceDialogInteractions();
    }

    public void setEditDeviceFields(VNextBODeviceManagementData deviceManagementData, String nickName) {
        editDeviceDialogInteractions.setTeam(deviceManagementData.getTeam());
        editDeviceDialogInteractions.setNickName(nickName);
        editDeviceDialogInteractions.setTimeZone(deviceManagementData.getTimeZone());
    }

    public void setAllValuesAndSubmit(VNextBODeviceManagementData deviceManagementData, String nickName) {
        setEditDeviceFields(deviceManagementData, nickName);
        submitChanges();
    }

    public void setNickNameValueAndSubmit(String nickName) {
        editDeviceDialogInteractions.setNickName(nickName);
        submitChanges();
    }

    private void submitChanges() {
        editDeviceDialogInteractions.clickSubmitButton();
        Assert.assertTrue(editDeviceDialogInteractions.isEditDeviceDialogClosed(),
                "The 'Edit device' dialog hasn't been closed");
    }
}