package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "VehicleID",
        "VIN",
        "Make",
        "Model",
        "Color",
        "Year",
        "PlateNo",
        "Milage"
})
public class VehicleDTO {

    @JsonProperty("VehicleID")
    private String VehicleID;
    @JsonProperty("VIN")
    private String VIN;
    @JsonProperty("Make")
    private String Make;
    @JsonProperty("Model")
    private String Model;
    @JsonProperty("Color")
    private String Color;
    @JsonProperty("Year")
    private Integer Year;
    @JsonProperty("PlateNo")
    private String PlateNo;
    @JsonProperty("Milage")
    private String Milage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("VehicleID")
    public String getVehicleID() {
        return VehicleID;
    }

    @JsonProperty("VehicleID")
    public void setVehicleID(String vehicleID) {
        this.VehicleID = vehicleID;
    }

    @JsonProperty("VIN")
    public String getVIN() {
        return VIN;
    }

    @JsonProperty("VIN")
    public void setVIN(String vIN) {
        this.VIN = vIN;
    }

    @JsonProperty("Make")
    public String getMake() {
        return Make;
    }

    @JsonProperty("Make")
    public void setMake(String make) {
        this.Make = make;
    }

    @JsonProperty("Model")
    public String getModel() {
        return Model;
    }

    @JsonProperty("Model")
    public void setModel(String model) {
        this.Model = model;
    }

    @JsonProperty("Color")
    public String getColor() {
        return Color;
    }

    @JsonProperty("Color")
    public void setColor(String color) {
        this.Color = color;
    }

    @JsonProperty("Year")
    public Integer getYear() {
        return Year;
    }

    @JsonProperty("Year")
    public void setYear(Integer year) {
        this.Year = year;
    }

    @JsonProperty("PlateNo")
    public String getPlateNo() {
        return PlateNo;
    }

    @JsonProperty("PlateNo")
    public void setPlateNo(String plateNo) {
        this.PlateNo = plateNo;
    }

    @JsonProperty("Milage")
    public String getMilage() {
        return Milage;
    }

    @JsonProperty("Milage")
    public void setMilage(String milage) {
        this.Milage = milage;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
