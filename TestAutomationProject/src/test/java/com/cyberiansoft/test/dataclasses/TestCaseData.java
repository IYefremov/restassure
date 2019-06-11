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

    @JsonProperty("inspectionData")
    List<InspectionData> inspectionsData;

    @JsonProperty("workOrderData")
    WorkOrderData workOrderData;

    @JsonProperty("workOrdersData")
    List<WorkOrderData> workOrdersData;

    @JsonProperty("invoiceData")
    InvoiceData invoiceData;

    @JsonProperty("serviceRequestData")
    ServiceRequestData serviceRequestData;

    @JsonProperty("archiveReason")
    String archiveReason;

}
