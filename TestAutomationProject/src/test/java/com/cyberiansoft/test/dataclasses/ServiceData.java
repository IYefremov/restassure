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
    @JsonProperty("vehiclePart")
    private VehiclePartData vehiclePart;
    @JsonProperty("vehicleParts")
    private List<VehiclePartData> vehicleParts;
    @JsonProperty("servicePartData")
    private ServicePartData servicePartData;
    @JsonProperty("question")
    private QuestionsData question;
    @JsonProperty("isNotMultiple")
    private boolean isNotMultiple;

    public ServiceStatus getServiceStatus() {
        return ServiceStatus.getStatus(serviceStatus);
    }
    public String getStatus(){
        return serviceStatus;
    }
}
