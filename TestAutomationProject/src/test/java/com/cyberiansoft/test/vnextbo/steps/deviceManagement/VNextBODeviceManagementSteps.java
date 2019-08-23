package com.cyberiansoft.test.vnextbo.steps.deviceManagement;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOActiveDevicesInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOEditDeviceDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOPendingRegistrationsInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOActiveDevicesWebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class VNextBODeviceManagementSteps {

    private VNextBODeviceManagementInteractions deviceManagementInteractions;
    private VNextBOPendingRegistrationsInteractions pendingRegistrationsInteractions;
    private VNextBOActiveDevicesInteractions activeDevicesInteractions;
    private VNextBOEditDeviceDialogInteractions editDeviceDialogInteractions;
    private VNextBOConfirmationDialog confirmationDialog;

    public VNextBODeviceManagementSteps() {
        deviceManagementInteractions = new VNextBODeviceManagementInteractions();
        pendingRegistrationsInteractions = new VNextBOPendingRegistrationsInteractions();
        activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        editDeviceDialogInteractions = new VNextBOEditDeviceDialogInteractions();
        confirmationDialog = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOConfirmationDialog.class);
    }

    public void deletePendingRegistrationDeviceByUser(String user) {
        pendingRegistrationsInteractions.verifyPendingRegistrationTabIsOpened();
        pendingRegistrationsInteractions.clickDeleteDeviceButtonForUser(user);
        confirmationDialog.clickYesButton();
    }

    public void verifyUserCanUncoverRegistrationCode(String deviceName) {
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

    public void openEditDeviceDialog(String deviceName) {
        deviceManagementInteractions.clickActiveDevicesTab();

        final VNextBOActiveDevicesWebPage activeDevicesWebPage = new VNextBOActiveDevicesWebPage();

        if (activeDevicesWebPage.getDeviceByName(deviceName) == null) {
            deviceName = activeDevicesWebPage.getDeviceByPartialNameMatch(deviceName).getText();
        }

        activeDevicesInteractions.clickActionsButtonForDevice(deviceName);
        Assert.assertTrue(activeDevicesInteractions.isActionsDropDownMenuDisplayedForDevice(deviceName),
                "The actions dropdown menu hasn't been opened");
        activeDevicesInteractions.clickActionsEditButtonForDevice(deviceName);
        Assert.assertTrue(editDeviceDialogInteractions.isEditDeviceDialogDisplayed(),
                "The 'Edit device dialog' is not opened");
    }

    public void searchByText(String searchText) {
        deviceManagementInteractions.setDeviceManagementSearchText(searchText);
        deviceManagementInteractions.clickDeviceManagementSearchLoupeIcon();
    }
}