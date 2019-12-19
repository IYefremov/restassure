package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOPendingRegistrationWebPage;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.testng.Assert;

public class VNextBOPendingRegistrationTabSteps extends VNextBODeviceManagementSteps {

    public static String getPendingRegistrationDevicesNotFoundMessage() {

        return Utils.getText(new VNextBOPendingRegistrationWebPage().getNoDevicesFoundMessage());
    }

    public static void changePhoneNumberByDeviceNickName(String deviceNickName, String newPhoneNumber) {

        VNextBOPendingRegistrationWebPage pendingRegistrationWebPage = new VNextBOPendingRegistrationWebPage();
        Utils.clickElement(pendingRegistrationWebPage.phoneNumberFieldByDeviceName(deviceNickName));
        WaitUtilsWebDriver.waitABit(1000);
        Utils.sendKeysWithJS(pendingRegistrationWebPage.phoneNumberFieldByDeviceName(deviceNickName), newPhoneNumber);
        Utils.clickElement(pendingRegistrationWebPage.getPendingRegistrationTab());
    }

    public static void clickDeleteButtonForDeviceByNickName(String deviceNickName) {

        Utils.clickElement(new VNextBOPendingRegistrationWebPage().deleteDeviceButtonByDeviceName(deviceNickName));
    }

    public static void deletePendingRegistrationDeviceByUser(String deviceNickName) {

        clickDeleteButtonForDeviceByNickName(deviceNickName);
        VNextBOModalDialogSteps.clickYesButton();
    }

    public static boolean checkWhetherDevicesNotFoundMessageIsDisplayed() {

        return Utils.isElementDisplayed(new VNextBOPendingRegistrationWebPage().getNoDevicesFoundMessage());
    }
}