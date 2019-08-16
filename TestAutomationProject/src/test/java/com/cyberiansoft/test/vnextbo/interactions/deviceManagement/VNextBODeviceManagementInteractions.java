package com.cyberiansoft.test.vnextbo.interactions.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOPendingRegistrationWebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class VNextBODeviceManagementInteractions {

    private VNextBODeviceManagementWebPage deviceManagementPage;
    private VNextBOPendingRegistrationWebPage pendingRegistrationWebPage;

    public VNextBODeviceManagementInteractions() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        deviceManagementPage = PageFactory.initElements(
                driver, VNextBODeviceManagementWebPage.class);
        pendingRegistrationWebPage = PageFactory.initElements(
                driver, VNextBOPendingRegistrationWebPage.class);
    }

    public void clickAddNewDeviceButton() {
        Utils.clickElement(deviceManagementPage.getAddNewDeviceButton());
    }

    public void clickDeleteDeviceButtonForUser(String user) {
        Utils.clickElement(pendingRegistrationWebPage.getDeleteDeviceButton(user));
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
}