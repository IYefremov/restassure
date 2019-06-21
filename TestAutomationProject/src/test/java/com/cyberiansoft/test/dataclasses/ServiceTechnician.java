package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ServiceTechnician {

    @JsonProperty("technicianFirstName")
    private String technicianFirstName;

    @JsonProperty("technicianLastName")
    private String technicianLastName;

    @JsonProperty("technicianPercentageValue")
    private String technicianPercentageValue;

    @JsonProperty("technicianPriceValue")
    private String technicianPriceValue;

    @JsonProperty("isSelected")
    private boolean isSelected;

    public String getTechnicianFullName() {
        String fullName = technicianFirstName + " " + technicianLastName;
        return fullName.trim();
    }
}
