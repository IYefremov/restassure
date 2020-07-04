package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServiceRequestDetailsScreen;
import org.testng.Assert;

public class RegularServiceRequestDetalsScreenValidations {

    public static void verifySRSummaryAppointmentsInformationExists(boolean isExists) {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.waitForServiceRequestDetailsScreenLoaded();
        Assert.assertTrue(serviceRequestDetailsScreen.isSRSummaryAppointmentsInformationExists());
    }

    public static void verifySRSheduledAppointmentExists(boolean isExists) {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.waitForServiceRequestDetailsScreenLoaded();
        Assert.assertTrue(serviceRequestDetailsScreen.isSRSheduledAppointmentExists());
    }
}
