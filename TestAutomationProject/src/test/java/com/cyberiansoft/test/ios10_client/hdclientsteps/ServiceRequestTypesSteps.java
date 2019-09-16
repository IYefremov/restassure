package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.ServiceRequestTypesPopup;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;

public class ServiceRequestTypesSteps {

    public static void selectServiceRequestType(IServiceRequestTypes serviceRequestType) {
        ServiceRequestTypesPopup serviceRequestTypesPopup = new ServiceRequestTypesPopup();
        serviceRequestTypesPopup.selectServiceRequestType(serviceRequestType.getServiceRequestTypeName());
    }
}
