package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.RandomStringUtils;

public class BOCompanyClientsData {

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientType")
    private String clientType;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("companyNote")
    private String companyNote;

    @JsonProperty("importFile")
    private String importFile;

    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("userFirstName")
    private String userFirstName;

    @JsonProperty("userLastName")
    private String userLastName;

    @JsonProperty("userCompany")
    private String userCompany;

    @JsonProperty("userAddress")
    private String userAddress;

    @JsonProperty("userCity")
    private String userCity;

    @JsonProperty("userCountry")
    private String userCountry;

    @JsonProperty("userStateProvince")
    private String userStateProvince;

    @JsonProperty("userZipPostCode")
    private String userZipPostCode;

    @JsonProperty("userPhone")
    private String userPhone;

    @JsonProperty("userAccountId")
    private String userAccountId;

    @JsonProperty("contactEmail")
    private String contactEmail;

    @JsonProperty("contactFirstName")
    private String contactFirstName;

    @JsonProperty("contactLastName")
    private String contactLastName;

    @JsonProperty("contactEmail2")
    private String contactEmail2;

    @JsonProperty("contactFirstName2")
    private String contactFirstName2;

    @JsonProperty("contactLastName2")
    private String contactLastName2;

    @JsonProperty("notification")
    private String notification;

    @JsonProperty("pendingSendEmailMess")
    private String pendingSendEmailMess;

    @JsonProperty("notVerifiedMess")
    private String notVerifiedMess;

    @JsonProperty("verifiedMess")
    private String verifiedMess;

    @JsonProperty("colorRed")
    private String colorRed;

    @JsonProperty("colorGreen")
    private String colorGreen;

    @JsonProperty("contactPhone")
    private String contactPhone;

    @JsonProperty("userAddress2")
    private String userAddress2;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("workHoursStart")
    private String workHoursStart;

    @JsonProperty("workHoursFinish")
    private String workHoursFinish;

    @JsonProperty("red")
    private String red;

    @JsonProperty("green")
    private String green;

    public String getClientName() {
        return clientName;
    }

    public String getClientType() {
        return clientType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public String getWorkHoursStart() {
        return workHoursStart;
    }

    public String getWorkHoursFinish() {
        return workHoursFinish;
    }

    public String getCompanyNote() {
        return companyNote;
    }

    public String getImportFile() {
        return importFile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public String getUserStateProvince() {
        return userStateProvince;
    }

    public String getUserZipPostCode() {
        return userZipPostCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getContactEmail2() {
        return contactEmail2;
    }

    public String getContactFirstName2() {
        return contactFirstName2;
    }

    public String getContactLastName2() {
        return contactLastName2;
    }

    public String getNotification() {
        return notification;
    }

    public String getPendingSendEmailMess() {
        return pendingSendEmailMess;
    }

    public String getNotVerifiedMess() {
        return notVerifiedMess;
    }

    public String getVerifiedMess() {
        return verifiedMess;
    }

    public String getColorRed() {
        return colorRed;
    }

    public String getColorGreen() {
        return colorGreen;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getUserAddress2() {
        return userAddress2;
    }

    public String getUserName() {
        return userName;
    }

    public String getRandomName() {
        return "test-" + RandomStringUtils.randomAlphabetic(7);
    }

    public String getRed() {
        return red;
    }

    public String getGreen() {
        return green;
    }
}