package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BOoperationsSRdata {

    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("addServiceRequestValue")
    private String addServiceRequestValue;

    @JsonProperty("assignedTo")
    private String assignedTo;

    @JsonProperty("insuranceCompanyName")
    private String insuranceCompanyName;

    @JsonProperty("poNum")
    private String poNum;

    @JsonProperty("roNum")
    private String roNum;

    @JsonProperty("ro")
    private String ro;

    @JsonProperty("stockNum")
    private String stockNum;

    @JsonProperty("stock123")
    private String stock123;

    @JsonProperty("newServiceRequest")
    private String newServiceRequest;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("insurance")
    private String insurance;

    @JsonProperty("label")
    private String label;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("technicianFieldValue")
    private String technicianFieldValue;

    @JsonProperty("status")
    private String status;

    @JsonProperty("locationType")
    private String locationType;

    @JsonProperty("clientAddress")
    private String clientAddress;

    @JsonProperty("clientCity")
    private String clientCity;

    @JsonProperty("clientZip")
    private String clientZip;

    @JsonProperty("clientZip2")
    private String clientZip2;

    @JsonProperty("location")
    private String location;

    @JsonProperty("anotherLogin")
    private String anotherLogin;

    @JsonProperty("anotherPassword")
    private String anotherPassword;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("checkInButton")
    private String checkInButton;

    @JsonProperty("undoCheckInButton")
    private String undoCheckInButton;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("tags")
    private String[] tags;

    @JsonProperty("descriptions")
    private String[] descriptions;

    @JsonProperty("serviceRequestType")
    private String serviceRequestType;

    @JsonProperty("newStatus")
    private String newStatus;

    @JsonProperty("vehicleStock")
    private String vehicleStock;

    @JsonProperty("vehicleVIN")
    private String vehicleVIN;

    @JsonProperty("event")
    private String event;

    @JsonProperty("eventNewName")
    private String eventNewName;

    @JsonProperty("emailNotification")
    private String emailNotification;

    @JsonProperty("serviceRequestGeneralInfo")
    private String serviceRequestGeneralInfo;

    @JsonProperty("technician")
    private String technician;

    @JsonProperty("emailKeyWordRemainder")
    private String emailKeyWordRemainder;

    @JsonProperty("emailKeyWordWasCreated")
    private String emailKeyWordWasCreated;

    @JsonProperty("emailKeyWord")
    private String emailKeyWord;

    @JsonProperty("notificationDropDown")
    private String notificationDropDown;

    @JsonProperty("alert")
    private String alert;

    @JsonProperty("selected")
    private String selected;

    @JsonProperty("testDescription")
    private String testDescription;

    @JsonProperty("service")
    private String service;

    public String getTestDescription() {
        return testDescription;
    }

    public String getAlert() {
        return alert;
    }

    public String getSelected() {
        return selected;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getAddServiceRequestValue() {
        return addServiceRequestValue;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public String getPoNum() {
        return poNum;
    }

    public String getRoNum() {
        return roNum;
    }

    public String getRo() {
        return ro;
    }

    public String getStockNum() {
        return stockNum;
    }

    public String getStock123() {
        return stock123;
    }

    public String getNewServiceRequest() {
        return newServiceRequest;
    }

    public String getVIN() {
        return VIN;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getLabel() {
        return label;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getClientName() {
        return clientName;
    }

    public String getTechnicianFieldValue() {
        return technicianFieldValue;
    }

    public String getStatus() {
        return status;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getClientCity() {
        return clientCity;
    }

    public String getClientZip() {
        return clientZip;
    }

    public String getClientZip2() {
        return clientZip2;
    }

    public String getLocation() {
        return location;
    }

    public String getAnotherLogin() {
        return anotherLogin;
    }

    public String getAnotherPassword() {
        return anotherPassword;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCheckInButton() {
        return checkInButton;
    }

    public String getUndoCheckInButton() {
        return undoCheckInButton;
    }

    public String getSymbol() {
        return symbol;
    }

    public String[] getTags() {
        return tags;
    }

    public String[] getDescriptions() {
        return descriptions;
    }

    public String getServiceRequestType() {
        return serviceRequestType;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public String getVehicleStock() {
        return vehicleStock;
    }

    public String getVehicleVIN() {
        return vehicleVIN;
    }

    public String getFirstDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        final LocalDate now = LocalDate.now(ZoneId.of("US/Pacific"));
        if (now.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            return now.plusDays(3).format(formatter);
        } else if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            return now.plusDays(2).format(formatter);
        } else {
            System.out.println(now.plusDays(1).format(formatter));
            return now.plusDays(1).format(formatter);
        }
    }

    public String getSecondDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        final LocalDate now = LocalDate.now(ZoneId.of("US/Pacific"));
        if (now.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            return now.plusDays(4).format(formatter);
        } else if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            return now.plusDays(3).format(formatter);
        } else {
            return now.plusDays(2).format(formatter);
        }
    }

    public String getEvent() {
        return event;
    }

    public String getEventNewName() {
        return eventNewName;
    }

    public String getEmailNotification() {
        return emailNotification;
    }

    public String getServiceRequestGeneralInfo() {
        return serviceRequestGeneralInfo;
    }

    public String getTechnician() {
        return technician;
    }

    public String getEmailKeyWordRemainder() {
        return emailKeyWordRemainder;
    }

    public String getEmailKeyWordWasCreated() {
        return emailKeyWordWasCreated;
    }

    public String getEmailKeyWord() {
        return emailKeyWord;
    }

    public String getNotificationDropDown() {
        return notificationDropDown;
    }

    public boolean isDateShifted() {
        final LocalDate now = LocalDate.now(ZoneId.of("US/Pacific"));
        return now.getDayOfWeek().equals(DayOfWeek.FRIDAY)
                || now.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public String getService() {
        return service;
    }
}