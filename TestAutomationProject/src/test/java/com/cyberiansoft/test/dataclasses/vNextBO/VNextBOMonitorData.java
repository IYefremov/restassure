package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class VNextBOMonitorData  {

    @JsonProperty("location")
    private String location;

    @JsonProperty("locationChanged")
    private String locationChanged;

    @JsonProperty("searchLocation")
    private String searchLocation;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("roNumber")
    private String roNumber;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("copyright")
    private String copyright;

    @JsonProperty("AMT")
    private String AMT;

    @JsonProperty("company")
    private String company;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("phase")
    private String phase;

    @JsonProperty("department")
    private String department;

    @JsonProperty("departments")
    private List<String> departments;

    @JsonProperty("phases")
    private List<String> phases;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("woNum")
    private String woNum;

    @JsonProperty("roNum")
    private String roNum;

    @JsonProperty("stockNum")
    private String stockNum;

    @JsonProperty("vinNum")
    private String vinNum;

    @JsonProperty("daysInPhase")
    private String daysInPhase;

    @JsonProperty("daysNum")
    private String daysNum;

    @JsonProperty("daysNumStart")
    private String daysNumStart;

    @JsonProperty("daysInProcess")
    private String daysInProcess;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("repairStatus")
    private String repairStatus;

    @JsonProperty("status")
    private String status;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("title")
    private String title;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("searchName")
    private String searchName;

    @JsonProperty("phaseText")
    private String phaseText;

    @JsonProperty("departmentText")
    private String departmentText;

    @JsonProperty("woTypeText")
    private String woTypeText;

    @JsonProperty("timeFrameText")
    private String timeFrameText;

    @JsonProperty("repairStatusText")
    private String repairStatusText;

    @JsonProperty("daysInProcessText")
    private String daysInProcessText;

    @JsonProperty("daysInPhaseText")
    private String daysInPhaseText;

    @JsonProperty("flagText")
    private String flagText;

    @JsonProperty("customerText")
    private String customerText;

    @JsonProperty("stockNumText")
    private String stockNumText;

    @JsonProperty("vinNumText")
    private String vinNumText;

    @JsonProperty("roNumText")
    private String roNumText;

    @JsonProperty("woNumText")
    private String woNumText;

    @JsonProperty("employeeText")
    private String employeeText;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("priceType")
    private String priceType;

    @JsonProperty("service")
    private String service;

    @JsonProperty("serviceDescription")
    private String serviceDescription;

    @JsonProperty("serviceDetails")
    private String serviceDetails;

    @JsonProperty("servicePrice")
    private String servicePrice;

    @JsonProperty("serviceQuantity")
    private String serviceQuantity;

    @JsonProperty("serviceLaborRate")
    private String serviceLaborRate;

    @JsonProperty("serviceLaborTime")
    private String serviceLaborTime;

    @JsonProperty("serviceCategory")
    private String serviceCategory;

    @JsonProperty("serviceSubcategory")
    private String serviceSubcategory;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("technician")
    private String technician;

    @JsonProperty("vendor1")
    private String vendor1;

    @JsonProperty("technician1")
    private String technician1;

    @JsonProperty("serviceNotesMessage")
    private String serviceNotesMessage;

    @JsonProperty("serviceStartedDate")
    private String serviceStartedDate;

    @JsonProperty("serviceCompletedDate")
    private String serviceCompletedDate;

    @JsonProperty("servicePhase")
    private String servicePhase;

    @JsonProperty("services")
    private String[] services;

    @JsonProperty("serviceTabs")
    private String[] serviceTabs;

    @JsonProperty("servicesTableFields")
    private String[] servicesTableFields;

    @JsonProperty("serviceParts")
    private String[] serviceParts;

    @JsonProperty("titles")
    private String[] titles;

    @JsonProperty("titlesRepeater")
    private String[] titlesRepeater;

    @JsonProperty("flags")
    private String[] flags;

    @JsonProperty("locations")
    private String[] locations;

    @JsonProperty("daysNumList")
    private String[] daysNumList;

    @JsonProperty("stockNumbers")
    private String[] stockNumbers;

    @JsonProperty("roNumbers")
    private String[] roNumbers;

    @JsonProperty("serviceVendorPrices")
    private String[] serviceVendorPrices;

    @JsonProperty("serviceStatuses")
    private String[] serviceStatuses;

    @JsonProperty("auditLogTabs")
    private String[] auditLogTabs;

    @JsonProperty("informationFields")
    private String[] informationFields;

    @JsonProperty("servicePhaseHeaders")
    private String[] servicePhaseHeaders;

    public List<String> getTitles() {
        return Arrays.asList(titles);
    }

    public List<String> getTitlesRepeater() {
        return Arrays.asList(titlesRepeater);
    }

    public List<String> getFullAdvancedSearchElementsList() {
        return Stream.of(departmentText, phaseText, customerText, stockNumText, vinNumText, roNumText, woNumText,
                employeeText, woTypeText, timeFrameText, repairStatusText, daysInProcessText, daysInPhaseText, flagText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public List<String> getAdvancedSearchDialogElements() {
        return Arrays.asList(phase, timeFrame, department,
                repairStatus, daysInProcess, daysInPhase, flag);
    }

    public List<String> getAdvancedSearchDialogDefaultTextList() {
        return Arrays.asList(phaseText, timeFrameText, departmentText,
                repairStatusText, daysInProcessText, daysInPhaseText, flagText);
    }

    public String getServiceCompletedDate() {
        LocalDate date = LocalDate.now(ZoneId.of("US/Pacific"));
        return date.format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
    }
}