package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import org.testng.Assert;

public class VNextBORODetailsPageVerifications {

    private VNextBORODetailsPageInteractions repairOrdersDetailsPageInteractions;

    public VNextBORODetailsPageVerifications() {
        repairOrdersDetailsPageInteractions = new VNextBORODetailsPageInteractions();
    }

    public String verifyServiceIsDisplayedForCollapsedPhase(String service, String phase) {
        repairOrdersDetailsPageInteractions.expandServicesTable(phase);
        return verifyServiceIsDisplayedForExpandedPhase(service);
    }

    public String verifyServiceIsDisplayedForExpandedPhase(String service) {
        final String serviceId = repairOrdersDetailsPageInteractions.getServiceId(service);
        Assert.assertNotEquals(serviceId, "", "The service " + service + " hasn't been displayed");
        return serviceId;
    }

    public void verifyVendorTechnicianNameIsSet(String name) {
        Assert.assertNotEquals(0, repairOrdersDetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(name),
                "The Vendor/Technician '" + name + "' hasn't been found.");
    }

    public void verifyServiceVendorPriceIsSet(String serviceId, String service, String vendorPrice) {
        System.out.println("Vendor price: " + repairOrdersDetailsPageInteractions.getServiceVendorPrice(serviceId));
        System.out.println("vendor price to be inserted: " + vendorPrice);
        repairOrdersDetailsPageInteractions.setServiceVendorPrice(serviceId, service, vendorPrice);
        Assert.assertEquals(repairOrdersDetailsPageInteractions.getServiceVendorPrice(serviceId), vendorPrice,
                "The Vendor Price hasn't been changed");
    }
}