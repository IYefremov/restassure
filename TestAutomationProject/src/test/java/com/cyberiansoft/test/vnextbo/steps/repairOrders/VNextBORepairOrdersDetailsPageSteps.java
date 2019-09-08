package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORepairOrdersDetailsPageInteractions;
import org.testng.Assert;

public class VNextBORepairOrdersDetailsPageSteps {

    private VNextBORepairOrdersDetailsPageInteractions repairOrdersDetailsPageInteractions;

    public VNextBORepairOrdersDetailsPageSteps() {
        repairOrdersDetailsPageInteractions = new VNextBORepairOrdersDetailsPageInteractions();
    }

    public void openServicesTableForStatus(String status, String service) {
        repairOrdersDetailsPageInteractions.setStatus(status);
        repairOrdersDetailsPageInteractions.expandServicesTable(service);
    }

    public void setServiceStatusForService(String phase, String status) {
        final String serviceId = repairOrdersDetailsPageInteractions.getServiceId(phase);
        repairOrdersDetailsPageInteractions.setServiceStatusForService(serviceId, status);
        Assert.assertEquals(status, repairOrdersDetailsPageInteractions.getServiceStatusValue(serviceId),
                "The status hasn't been set for service");
    }
}