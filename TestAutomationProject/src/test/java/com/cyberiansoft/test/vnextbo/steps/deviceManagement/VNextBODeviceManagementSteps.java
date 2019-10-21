package com.cyberiansoft.test.vnextbo.steps.deviceManagement;

import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOActiveDevicesInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOEditDeviceDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOPendingRegistrationsInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOActiveDevicesWebPage;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOPendingRegistrationsValidations;
import org.testng.Assert;

public class VNextBODeviceManagementSteps {

    public static void deletePendingRegistrationDeviceByUser(String user) {
        VNextBOPendingRegistrationsValidations.verifyPendingRegistrationTabIsOpened();
        new VNextBOPendingRegistrationsInteractions().clickDeleteDeviceButtonForUser(user);
        new VNextBOConfirmationDialog().clickYesButton();
    }

    public static void verifyUserCanUncoverRegistrationCode(String deviceName) {
        final VNextBOActiveDevicesInteractions activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        activeDevicesInteractions.verifyReplaceButtonIsDisplayedForDevice(deviceName);
        activeDevicesInteractions.clickReplaceButtonByDeviceName(deviceName);
        Assert.assertTrue(!activeDevicesInteractions.getRegistrationNumberForDevice(deviceName).isEmpty(),
                "The registration code hasn't been uncovered for device " + deviceName);
    }

    public static void verifyUserCanHideRegistrationCode(String deviceName) {
        final VNextBOActiveDevicesInteractions activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        activeDevicesInteractions.verifyActiveDevicesTabIsOpened();
        activeDevicesInteractions.clickRegistrationNumberClearButtonForDevice(deviceName);
        Assert.assertTrue(activeDevicesInteractions.isReplaceButtonDisplayedForDevice(deviceName),
                "The 'Replace' button hasn't been uncovered for device " + deviceName);
    }

    public static void openEditDeviceDialog(String deviceName) {
        final VNextBODeviceManagementInteractions deviceManagementInteractions = new VNextBODeviceManagementInteractions();
        deviceManagementInteractions.clickActiveDevicesTab();

        final VNextBOActiveDevicesWebPage activeDevicesWebPage = new VNextBOActiveDevicesWebPage();

        if (activeDevicesWebPage.getDeviceByName(deviceName) == null) {
            deviceName = activeDevicesWebPage.getDeviceByPartialNameMatch(deviceName).getText();
        }

        final VNextBOActiveDevicesInteractions activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        activeDevicesInteractions.clickActionsButtonForDevice(deviceName);
        Assert.assertTrue(activeDevicesInteractions.isActionsDropDownMenuDisplayedForDevice(deviceName),
                "The actions dropdown menu hasn't been opened");
        activeDevicesInteractions.clickActionsEditButtonForDevice(deviceName);
        Assert.assertTrue(new VNextBOEditDeviceDialogInteractions().isEditDeviceDialogDisplayed(),
                "The 'Edit device dialog' is not opened");
    }

    public static void searchByText(String searchText) {
        new VNextBODeviceManagementInteractions().setDeviceManagementSearchText(searchText);
        new VNextBODeviceManagementInteractions().clickDeviceManagementSearchLoupeIcon();
    }
}