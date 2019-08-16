package com.cyberiansoft.test.vnextbo.steps.deviceManagement;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOActiveDevicesInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOPendingRegistrationsInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class VNextBODeviceManagementSteps {

    private VNextBODeviceManagementInteractions deviceManagementInteractions;
    private VNextBOPendingRegistrationsInteractions pendingRegistrationsInteractions;
    private VNextBOActiveDevicesInteractions activeDevicesInteractions;
    private VNextBOConfirmationDialog confirmationDialog;

    public VNextBODeviceManagementSteps() {
        deviceManagementInteractions = new VNextBODeviceManagementInteractions();
        confirmationDialog = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOConfirmationDialog.class);
        pendingRegistrationsInteractions = new VNextBOPendingRegistrationsInteractions();
        activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
    }

    public void deletePendingRegistrationDeviceByUser(String user) {
        pendingRegistrationsInteractions.verifyPendingRegistrationTabIsOpened();
        pendingRegistrationsInteractions.clickDeleteDeviceButtonForUser(user);
        confirmationDialog.clickYesButton();
    }

    public void verifyUserCanUncoverRegistrationCode(String deviceName) {
        deviceManagementInteractions.clickActiveDevicesTab();
        activeDevicesInteractions.verifyReplaceButtonIsDisplayedForDevice(deviceName);
        activeDevicesInteractions.clickReplaceButtonByDeviceName(deviceName);
        Assert.assertTrue(!activeDevicesInteractions.getRegistrationNumberForDevice(deviceName).isEmpty(),
                "The registration code hasn't been uncovered for device " + deviceName);
    }

    public void verifyUserCanHideRegistrationCode(String deviceName) {
        activeDevicesInteractions.verifyActiveDevicesTabIsOpened();
        activeDevicesInteractions.clickRegistrationNumberClearButtonForDevice(deviceName);
        Assert.assertTrue(activeDevicesInteractions.isReplaceButtonDisplayedForDevice(deviceName),
                "The 'Replace' button hasn't been uncovered for device " + deviceName);
    }
}