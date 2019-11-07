package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBODeviceManagementData;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOEditDeviceDialogInteractions;
import org.testng.Assert;

public class VNextBOEditDeviceSteps {

    private static void setEditDeviceFields(VNextBODeviceManagementData deviceManagementData, String nickName) {
        VNextBOEditDeviceDialogInteractions editDeviceDialogInteractions = new VNextBOEditDeviceDialogInteractions();
        editDeviceDialogInteractions.setTeam(deviceManagementData.getTeam());
        editDeviceDialogInteractions.setNickName(nickName);
        editDeviceDialogInteractions.setTimeZone(deviceManagementData.getTimeZone());
    }

    public static void setAllValuesAndSubmit(VNextBODeviceManagementData deviceManagementData, String nickName) {
        setEditDeviceFields(deviceManagementData, nickName);
        submitChanges();
    }

    public static void setNickNameValueAndSubmit(String nickName) {
        new VNextBOEditDeviceDialogInteractions().setNickName(nickName);
        submitChanges();
    }

    private static void submitChanges() {
        final VNextBOEditDeviceDialogInteractions editDeviceDialogInteractions = new VNextBOEditDeviceDialogInteractions();
        editDeviceDialogInteractions.clickSubmitButton();
        Assert.assertTrue(editDeviceDialogInteractions.isEditDeviceDialogClosed(),
                "The 'Edit device' dialog hasn't been closed");
    }
}