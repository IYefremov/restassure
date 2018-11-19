package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "VehicleID",
        "VIN",
        "Year",
        "Make",
        "Model",
        "PlateNo",
        "Color",
        "Milage",
        "OwnerID",
        "ForeignId",
        "VehicleTypeId",
        "FuelTankLevel",
        "PartitionID"
})
public class VehicleDTO {

    @JsonProperty("VehicleID")
    private String VehicleID;
    @JsonProperty("VIN")
    private String VIN;
    @JsonProperty("Year")
    private Integer Year;
    @JsonProperty("Make")
    private String Make;
    @JsonProperty("Model")
    private String Model;
    @JsonProperty("PlateNo")
    private String PlateNo;
    @JsonProperty("Color")
    private String Color;
    @JsonProperty("Milage")
    private Integer Milage;
    @JsonProperty("OwnerID")
    private Object OwnerID;
    @JsonProperty("ForeignId")
    private Object ForeignId;
    @JsonProperty("VehicleTypeId")
    private Object VehicleTypeId;
    @JsonProperty("FuelTankLevel")
    private Integer FuelTankLevel;
    @JsonProperty("PartitionID")
    private Integer PartitionID;


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

    @JsonProperty("Year")
    public Integer getYear() {
        return Year;
    }

    @JsonProperty("Year")
    public void setYear(Integer year) {
        this.Year = year;
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

    @JsonProperty("PlateNo")
    public String getPlateNo() {
        return PlateNo;
    }

    @JsonProperty("PlateNo")
    public void setPlateNo(String plateNo) {
        this.PlateNo = plateNo;
    }

    @JsonProperty("Color")
    public String getColor() {
        return Color;
    }

    @JsonProperty("Color")
    public void setColor(String color) {
        this.Color = color;
    }

    @JsonProperty("Milage")
    public Integer getMilage() {
        return Milage;
    }

    @JsonProperty("Milage")
    public void setMilage(Integer milage) {
        this.Milage = milage;
    }

    @JsonProperty("OwnerID")
    public Object getOwnerID() {
        return OwnerID;
    }

    @JsonProperty("OwnerID")
    public void setOwnerID(Object ownerID) {
        this.OwnerID = ownerID;
    }

    @JsonProperty("ForeignId")
    public Object getForeignId() {
        return ForeignId;
    }

    @JsonProperty("ForeignId")
    public void setForeignId(Object foreignId) {
        this.ForeignId = foreignId;
    }

    @JsonProperty("VehicleTypeId")
    public Object getVehicleTypeId() {
        return VehicleTypeId;
    }

    @JsonProperty("VehicleTypeId")
    public void setVehicleTypeId(Object vehicleTypeId) {
        this.VehicleTypeId = vehicleTypeId;
    }

    @JsonProperty("FuelTankLevel")
    public Integer getFuelTankLevel() {
        return FuelTankLevel;
    }

    @JsonProperty("FuelTankLevel")
    public void setFuelTankLevel(Integer fuelTankLevel) {
        this.FuelTankLevel = fuelTankLevel;
    }

    @JsonProperty("PartitionID")
    public Integer getPartitionID() {
        return PartitionID;
    }

    @JsonProperty("PartitionID")
    public void setPartitionID(Integer partitionID) {
        this.PartitionID = partitionID;
    }
}
