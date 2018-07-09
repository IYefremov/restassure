package com.cyberiansoft.test.dataclasses.ibs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IBSLicenceAddingNotificationData {

    @JsonProperty("sectionBillingProfiles")
    private String sectionBillingProfiles;

    @JsonProperty("sectionBillingProfileItems")
    private String sectionBillingProfileItems;

    @JsonProperty("sectionClientAgreements")
    private String sectionClientAgreements;

    public String getSectionBillingProfiles() {
        return sectionBillingProfiles;
    }

    public String getSectionBillingProfileItems() {
        return sectionBillingProfileItems;
    }

    public String getSectionClientAgreements() {
        return sectionClientAgreements;
    }
}
