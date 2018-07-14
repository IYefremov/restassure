package com.cyberiansoft.test.dataclasses.ibs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IBSLicenceAddingNotificationData {

    @JsonProperty("sectionBillingProfiles")
    private String sectionBillingProfiles;

    @JsonProperty("sectionBillingProfileItems")
    private String sectionBillingProfileItems;

    @JsonProperty("sectionClientAgreements")
    private String sectionClientAgreements;

    @JsonProperty("alertText")
    private String alertText;

    @JsonProperty("yearly")
    private String yearly;

    @JsonProperty("monthly")
    private String monthly;

    @JsonProperty("effectiveDate")
    private String effectiveDate;

    @JsonProperty("expirationDate")
    private String expirationDate;

    public String getSectionBillingProfiles() {
        return sectionBillingProfiles;
    }

    public String getSectionBillingProfileItems() {
        return sectionBillingProfileItems;
    }

    public String getSectionClientAgreements() {
        return sectionClientAgreements;
    }

    public String getAlertText() {
        return alertText;
    }

    public String getYearly() {
        return yearly;
    }

    public String getMonthly() {
        return monthly;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
