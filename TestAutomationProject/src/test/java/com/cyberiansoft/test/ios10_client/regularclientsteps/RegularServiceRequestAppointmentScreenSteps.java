package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServiceRequestAppointmentScreen;

public class RegularServiceRequestAppointmentScreenSteps {

    public static void setDefaultServiceRequestAppointment() {
        RegularServiceRequestAppointmentScreen serviceRequestAppointmentScreen = new RegularServiceRequestAppointmentScreen();
        serviceRequestAppointmentScreen.clickAppointmentFromField();
        serviceRequestAppointmentScreen.clickDatePickerDoneButton();
        serviceRequestAppointmentScreen.clickAppointmentToField();
        serviceRequestAppointmentScreen.clickDatePickerDoneButton();
        serviceRequestAppointmentScreen.clickAppointmentSaveButton();
    }
}
