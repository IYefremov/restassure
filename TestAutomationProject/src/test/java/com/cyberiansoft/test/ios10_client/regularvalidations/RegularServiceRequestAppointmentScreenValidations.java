package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServiceRequestAppointmentsScreen;
import org.testng.Assert;

public class RegularServiceRequestAppointmentScreenValidations {

    public static void verifyAppointmentExistsForServiceRequest(boolean isExists) {
        RegularServiceRequestAppointmentsScreen serviceRequestAppointmentsScreen = new RegularServiceRequestAppointmentsScreen();
        serviceRequestAppointmentsScreen.waitForAppointmentsScreenLoad();
        if (isExists)
            Assert.assertTrue(serviceRequestAppointmentsScreen.isAppointmentExists());
        else
            Assert.assertFalse(serviceRequestAppointmentsScreen.isAppointmentExists());
    }
}
