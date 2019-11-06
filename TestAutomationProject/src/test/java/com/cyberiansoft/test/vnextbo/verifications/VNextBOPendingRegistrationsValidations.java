package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOPendingRegistrationWebPage;

public class VNextBOPendingRegistrationsValidations {

    public static void verifyPendingRegistrationTabIsOpened() {
        final VNextBODeviceManagementInteractions deviceManagementInteractions = new VNextBODeviceManagementInteractions();
        if (!deviceManagementInteractions.isPendingRegistrationTabOpened()) {
            deviceManagementInteractions.clickPendingRegistrationsTab();
        }
    }

    public static boolean isUserDisplayedInPendingRegistrationTable(String user) {
        return Utils.isElementDisplayed(new VNextBOPendingRegistrationWebPage().getDeleteDeviceButton(user));
    }

    public static boolean isUserNotDisplayedInPendingRegistrationTable(String user) {
        try {
            WaitUtilsWebDriver.waitForInvisibility(new VNextBOPendingRegistrationWebPage().getDeleteDeviceButton(user), 10);
            return true;
        } catch (NullPointerException ignored) {
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}