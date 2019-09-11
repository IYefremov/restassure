package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestCaseData {

    @JsonProperty("inspectionData")
    InspectionData inspectionData;

    @JsonProperty("inspectionsData")
    List<InspectionData> inspectionsData;

    @JsonProperty("workOrderData")
    WorkOrderData workOrderData;

    @JsonProperty("workOrdersData")
    List<WorkOrderData> workOrdersData;

    @JsonProperty("invoiceData")
    InvoiceData invoiceData;

    @JsonProperty("serviceRequestData")
    ServiceRequestData serviceRequestData;

    @JsonProperty("serviceRequestsData")
    List<ServiceRequestData> serviceRequestsData;

    @JsonProperty("archiveReason")
    String archiveReason;

    @JsonProperty("targetProcessTestCaseData")
    List<TargetProcessTestCaseData> targetProcessTestCaseData;

}
