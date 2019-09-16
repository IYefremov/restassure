package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import org.testng.Assert;

public class VNextBORODetailsPageSteps {

    private VNextBORODetailsPageInteractions roDetailsPageInteractions;

    public VNextBORODetailsPageSteps() {
        roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
    }

    public void openServicesTableForStatus(String status, String service) {
        roDetailsPageInteractions.setStatus(status);
        roDetailsPageInteractions.expandServicesTable(service);
    }

    public void openServicesTableForStatus(String status) {
        roDetailsPageInteractions.setStatus(status);
        roDetailsPageInteractions.expandServicesTable();
    }

    public void setServiceStatusForService(String phase, String status) {
        final String serviceId = roDetailsPageInteractions.getServiceId(phase);
        roDetailsPageInteractions.setServiceStatusForService(serviceId, status);
        Assert.assertEquals(status, roDetailsPageInteractions.getServiceStatusValue(serviceId),
                "The status hasn't been set for service");
    }
}