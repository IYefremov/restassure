package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceData {
    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("servicePrice")
    private String servicePrice;
    @JsonProperty("servicePrice2")
    private String servicePrice2;
    @JsonProperty("serviceQuantity")
    private String serviceQuantity;
    @JsonProperty("serviceQuantity2")
    private String serviceQuantity2;
    @JsonProperty("serviceStatus")
    private String serviceStatus;
    @JsonProperty("serviceAdjustment")
    private ServiceAdjustmentData serviceAdjustment;
    @JsonProperty("vehiclePart")
    private VehiclePartData vehiclePart;
    @JsonProperty("vehicleParts")
    private List<VehiclePartData> vehicleParts;
    @JsonProperty("servicePartData")
    private ServicePartData servicePartData;
    @JsonProperty("questionData")
    private QuestionsData questionData;
    @JsonProperty("isNotMultiple")
    private boolean isNotMultiple;
    @JsonProperty("isSelected")
    private boolean isSelected;
    @JsonProperty("problemReason")
    private String problemReason;

    public ServiceStatus getServiceStatus() {
        return ServiceStatus.getStatus(serviceStatus);
    }
    public void setServiceStatus(ServiceStatus serviceStatus){
        this.serviceStatus = serviceStatus.getStatus();
    }
    public String getStatus(){
        return serviceStatus;
    }
}
