package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularMyWorkOrdersSteps;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class RegularMyWorkOrdersScreenValidations {

    public static void verifyWorkOrderTotalPrice(String workOrderID, String expectedTotalPrice) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderID), expectedTotalPrice);
    }

    public static void verifyWorkOrderPresent(String workOrderID, boolean isPresent) {
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        if (isPresent)
            Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderID));
        else
            Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderID));
    }

    public static void verifyNotesIconPresentForInspection(String workOrderID, boolean isPresent) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        if (isPresent)
            Assert.assertTrue(myWorkOrdersScreen.isNotesIconPresentForWorkOrder(workOrderID));
        else
            Assert.assertFalse(myWorkOrdersScreen.isNotesIconPresentForWorkOrder(workOrderID));
    }

    public static void verifyWorkOrderHasApproveIcon(String workOrderID, boolean isPresent) {
        DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        if (isPresent)
            Assert.assertTrue(myWorkOrdersScreen.isApproveIconPresentForWorkOrder(workOrderID));
        else
            Assert.assertFalse(myWorkOrdersScreen.isApproveIconPresentForWorkOrder(workOrderID));
    }

    public static void verifyWorkOrderHasInvoiceIcon(String workOrderID, boolean isPresent) {
        DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        if (isPresent)
            Assert.assertTrue(myWorkOrdersScreen.isInvoiceIconPresentForWorkOrder(workOrderID));
        else
            Assert.assertFalse(myWorkOrdersScreen.isInvoiceIconPresentForWorkOrder(workOrderID));
    }
}
