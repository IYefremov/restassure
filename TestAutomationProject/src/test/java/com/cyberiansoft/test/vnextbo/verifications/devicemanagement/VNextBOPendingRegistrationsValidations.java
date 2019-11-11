package com.cyberiansoft.test.vnextbo.verifications.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOPendingRegistrationWebPage;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBOPendingRegistrationTabSteps;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VNextBOPendingRegistrationsValidations {

    public static void verifyPendingRegistrationDevicesNotFoundMessageIsCorrect() {

        Assert.assertEquals(VNextBOPendingRegistrationTabSteps.getPendingRegistrationDevicesNotFoundMessage(), "There are no pre-registered devices to show.",
                "There are no pre-registered devices to show.");
    }

    public static void verifyDevicesTableContainsCorrectColumns() {

        List<String> expectedColumnsList = Arrays.asList("Device Nickname", "License #", "Reg Code", "Phone");
        List<String> actualColumnsList = new ArrayList<>();

        for (WebElement columnTitle : new VNextBOPendingRegistrationWebPage().getColumnsTitlesList()) {
            actualColumnsList.add(Utils.getText(columnTitle));
        }

        Assert.assertEquals(expectedColumnsList, actualColumnsList, "Not all columns have been displayed");
    }

    public static void verifyDevicesTableContainsDevice(String deviceNickName) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPendingRegistrationWebPage().deviceRowByName(deviceNickName)),
                "Device hasn't been displayed on the \"Pending Registration\" tab");
    }

    public static void verifyPhoneNumberIsCorrectByDeviceNickName(String deviceNickName, String columnName, String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOPendingRegistrationWebPage().phoneNumberFieldByDeviceName(deviceNickName)), expectedValue,
                "Phone number hasn't been correct");
    }
}