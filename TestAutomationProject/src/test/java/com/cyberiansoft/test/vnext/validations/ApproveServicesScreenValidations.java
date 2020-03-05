package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextApproveServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ApproveServicesListElement;
import org.testng.Assert;

public class ApproveServicesScreenValidations {

    public static void validateServiceExistsForApprove(String serviceName, boolean isExists) {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.waitUntilElementIsClickable(approveServicesScreen.getRootElement());
        if (isExists)
            Assert.assertTrue(approveServicesScreen.isServiceExistsForApprove(serviceName));
        else
            Assert.assertFalse(approveServicesScreen.isServiceExistsForApprove(serviceName));
    }

    public static void validateServicePrice(String serviceName, String expectedPrice) {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.waitUntilElementIsClickable(approveServicesScreen.getRootElement());
        ApproveServicesListElement service = approveServicesScreen.getServiceElement(serviceName);
        Assert.assertEquals(service.getServicePrice(), expectedPrice);
    }

    public static void verifyApproveAllButtonDisplayed(boolean isDisplayed) {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.waitUntilElementIsClickable(approveServicesScreen.getRootElement());
        if (isDisplayed)
            Assert.assertTrue(approveServicesScreen.getApproveAllBtn().isDisplayed());
        else
            Assert.assertFalse(approveServicesScreen.getApproveAllBtn().isDisplayed());
    }

    public static void verifyDeclineAllButtonDisplayed(boolean isDisplayed) {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.waitUntilElementIsClickable(approveServicesScreen.getRootElement());
        if (isDisplayed)
            Assert.assertTrue(approveServicesScreen.getDeclineAllBtn().isDisplayed());
        else
            Assert.assertFalse(approveServicesScreen.getDeclineAllBtn().isDisplayed());
    }

    public static void verifySkipAllButtonDisplayed(boolean isDisplayed) {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.waitUntilElementIsClickable(approveServicesScreen.getRootElement());
        if (isDisplayed)
            Assert.assertTrue(approveServicesScreen.getSkipAllBtn().isDisplayed());
        else
            Assert.assertFalse(approveServicesScreen.getSkipAllBtn().isDisplayed());
    }
}
