package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.UATServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;

public class RegularServiceRequestSteps {

    public static void saveServiceRequest() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickSave();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.waitForServiceRequestScreenLoad();
    }

    public static void waitServiceRequestScreenLoaded() {
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.waitForServiceRequestScreenLoad();
    }

    public static void startCreatingServicerequest(AppCustomer appCustomer, IServiceRequestTypes serviceRequestTypes) {
        waitServiceRequestScreenLoaded();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.clickAddButton();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        serviceRequestsScreen.selectServiceRequestType(UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
    }
}
