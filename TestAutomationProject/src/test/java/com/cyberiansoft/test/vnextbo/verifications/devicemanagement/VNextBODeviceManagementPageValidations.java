package com.cyberiansoft.test.vnextbo.verifications.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBODeviceManagementPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyAddNewDeviceButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getAddNewDeviceButton()),
                "\"Add New Device\" button hasn't been displayed.");
    }

    public static void verifyActiveDevicesTabIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getActiveDevicesTab()),
                "\"Active Devices\" tab hasn't been displayed.");
    }

    public static void verifyPendingRegistrationsTabIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getPendingRegistrationTab()),
                "\"Pending Registrations\" tab hasn't been displayed.");
    }
}