package com.cyberiansoft.test.vnextbo.interactions.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBODeviceManagementWebPage;
import org.openqa.selenium.support.PageFactory;

public class VNextBODeviceManagementInteractions {

    private VNextBODeviceManagementWebPage deviceManagementPage;

    public VNextBODeviceManagementInteractions() {
        deviceManagementPage = PageFactory
                .initElements(DriverBuilder.getInstance().getDriver(), VNextBODeviceManagementWebPage.class);
    }

    public void clickAddNewDeviceButton() {
        Utils.clickElement(deviceManagementPage.getAddNewDeviceButton());
    }

    public void clickActiveDevicesTab() {
        Utils.clickElement(deviceManagementPage.getActiveDevicesTab());
        WaitUtilsWebDriver.waitABit(1000);
    }

    public void clickPendingRegistrationsTab() {
        Utils.clickElement(deviceManagementPage.getPendingRegistrationsTab());
        WaitUtilsWebDriver.waitABit(1000);
    }

    public boolean isActiveDevicesTabOpened() {
        try {
            WaitUtilsWebDriver.waitForAttributeToBe(deviceManagementPage.getActiveDevicesTab(),
                    "aria-expanded", "true", 7);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPendingRegistrationTabOpened() {
        try {
            WaitUtilsWebDriver.waitForAttributeToBe(deviceManagementPage.getPendingRegistrationsTab(),
                    "aria-expanded", "true", 7);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void setDeviceManagementSearchText(String searchText) {
        Utils.clearAndType(deviceManagementPage.getSearchInputField(), searchText);
    }

    public void clickDeviceManagementSearchLoupeIcon() {
        Utils.clickElement(deviceManagementPage.getSearchLoupeIcon());
        WaitUtilsWebDriver.waitForLoading();
    }
}