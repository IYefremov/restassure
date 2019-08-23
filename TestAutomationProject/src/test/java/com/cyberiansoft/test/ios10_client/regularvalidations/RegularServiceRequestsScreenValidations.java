package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularServiceRequestSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularVehicleInfoScreenSteps;
import org.testng.Assert;

public class RegularServiceRequestsScreenValidations {

    public static void verifyInspectionIconPresentForServiceRequest(String serviceRequestNumber, boolean isPresent) {
        RegularServiceRequestSteps.waitServiceRequestScreenLoaded();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        if (isPresent)
            Assert.assertTrue(serviceRequestsScreen.isInspectionIconPresentForServiceRequest(serviceRequestNumber));
        else
            Assert.assertFalse(serviceRequestsScreen.isInspectionIconPresentForServiceRequest(serviceRequestNumber));
    }

    public static void verifyWorkOrderIconPresentForServiceRequest(String serviceRequestNumber, boolean isPresent) {
        RegularServiceRequestSteps.waitServiceRequestScreenLoaded();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        if (isPresent)
            Assert.assertTrue(serviceRequestsScreen.isWorkOrderIconPresentForServiceRequest(serviceRequestNumber));
        else
            Assert.assertFalse(serviceRequestsScreen.isWorkOrderIconPresentForServiceRequest(serviceRequestNumber));
    }
}
