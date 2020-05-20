package com.cyberiansoft.test.vnext.validations.servicerequests;

import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.vnext.interactions.servicerequests.ServiceRequestsScreenInteractions;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.servicerequests.ServiceRequestsScreenSteps;
import org.testng.Assert;

public class ServiceRequestsScreenValidations {

    public static void verifyServiceRequestHasInspection(ServiceRequestData serviceRequestData, int inspectionsCount) {

        ServiceRequestsScreenSteps.findServiceRequest(serviceRequestData.getServiceRequestNumber());
        TopScreenPanelSteps.cancelSearch();
        int actualInspectionsCount = ServiceRequestsScreenInteractions.getFirstSRInspectionCount();
        Assert.assertEquals(actualInspectionsCount, inspectionsCount,
                "Service request has contained incorrect requests number");
    }
}
