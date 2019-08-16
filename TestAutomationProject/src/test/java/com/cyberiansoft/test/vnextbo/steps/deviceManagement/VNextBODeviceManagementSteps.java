package com.cyberiansoft.test.vnextbo.steps.deviceManagement;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import org.openqa.selenium.support.PageFactory;

public class VNextBODeviceManagementSteps {

    private VNextBODeviceManagementInteractions deviceManagementInteractions;
    private VNextBOConfirmationDialog confirmationDialog;

    public VNextBODeviceManagementSteps() {
        deviceManagementInteractions = new VNextBODeviceManagementInteractions();
        confirmationDialog = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOConfirmationDialog.class);
    }

    public void deletePendingRegistrationDeviceByUser(String user) {
        deviceManagementInteractions.clickDeleteDeviceButtonForUser(user);
        confirmationDialog.clickYesButton();
    }
}