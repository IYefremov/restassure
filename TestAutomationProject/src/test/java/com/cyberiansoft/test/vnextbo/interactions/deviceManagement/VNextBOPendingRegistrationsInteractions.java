package com.cyberiansoft.test.vnextbo.interactions.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOPendingRegistrationWebPage;
import org.openqa.selenium.support.PageFactory;

public class VNextBOPendingRegistrationsInteractions {

    private VNextBOPendingRegistrationWebPage pendingRegistrationWebPage;
    private VNextBODeviceManagementInteractions deviceManagementInteractions;

    public VNextBOPendingRegistrationsInteractions() {
        pendingRegistrationWebPage = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOPendingRegistrationWebPage.class);
        deviceManagementInteractions = new VNextBODeviceManagementInteractions();
    }

    public void verifyPendingRegistrationTabIsOpened() {
        if (!deviceManagementInteractions.isPendingRegistrationTabOpened()) {
            deviceManagementInteractions.clickPendingRegistrationsTab();
        }
    }

    public boolean isUserDisplayedInPendingRegistrationTable(String user) {
        return Utils.isElementDisplayed(pendingRegistrationWebPage.getDeleteDeviceButton(user), 10);
    }

    public boolean isUserNotDisplayedInPendingRegistrationTable(String user) {
        try {
            WaitUtilsWebDriver.waitForInvisibility(pendingRegistrationWebPage.getDeleteDeviceButton(user), 10);
            return true;
        } catch (NullPointerException ignored) {
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void clickDeleteDeviceButtonForUser(String user) {
        Utils.clickElement(pendingRegistrationWebPage.getDeleteDeviceButton(user));
    }
}