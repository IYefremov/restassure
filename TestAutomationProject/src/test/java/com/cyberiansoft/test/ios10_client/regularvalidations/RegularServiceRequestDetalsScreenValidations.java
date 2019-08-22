package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServiceRequestDetailsScreen;
import org.testng.Assert;

public class RegularServiceRequestDetalsScreenValidations {

    public static void verifySRSummaryAppointmentsInformationExists(boolean isExists) {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.waitForServiceRequestDetailsScreenLoaded();
        if (isExists)
            Assert.assertTrue(serviceRequestDetailsScreen.isSRSummaryAppointmentsInformationExists());
        else
            Assert.assertTrue(serviceRequestDetailsScreen.isSRSummaryAppointmentsInformationExists());
    }

    public static void verifySRSheduledAppointmentExists(boolean isExists) {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.waitForServiceRequestDetailsScreenLoaded();
        if (isExists)
            Assert.assertTrue(serviceRequestDetailsScreen.isSRSheduledAppointmentExists());
        else
            Assert.assertTrue(serviceRequestDetailsScreen.isSRSheduledAppointmentExists());
    }
}
