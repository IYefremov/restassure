package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InHouseUserData {

    @JsonProperty("name")
    private String name;

    @JsonProperty("newName")
    private String newName;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("address")
    private String address;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("city")
    private String city;

    @JsonProperty("businessPhone")
    private String businessPhone;

    @JsonProperty("cellPhone")
    private String cellPhone;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("title")
    private String title;

    @JsonProperty("email")
    private String email;;

    @JsonProperty("firstAgreement")
    private String firstAgreement;

    @JsonProperty("secondAgreement")
    private String secondAgreement;

    @JsonProperty("team")
    private String team;

    public String getName() {
        return name;
    }

    public String getNewName() {
        return newName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstAgreement() {
        return firstAgreement;
    }

    public String getSecondAgreement() {
        return secondAgreement;
    }

    public String getTeam() {
        return team;
    }
}
