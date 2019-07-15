package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.TechnicianScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleInfoScreenSteps {
    public static void setVehicleInfo(VehicleInfoData vehicleInfoDto) {
        if (vehicleInfoDto.getVINNumber() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vehicleInfoDto.getVINNumber());
        if (vehicleInfoDto.getVehicleMake() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.MAKE, vehicleInfoDto.getVehicleMake());
        if (vehicleInfoDto.getVehicleModel() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.MODEL, vehicleInfoDto.getVehicleModel());
        if (vehicleInfoDto.getVehicleYear() != null)
            VehicleInfoScreenInteractions.setYear(vehicleInfoDto.getVehicleYear());
        if (vehicleInfoDto.getMileage() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.MILAGE, vehicleInfoDto.getMileage());
        if (vehicleInfoDto.getStockNumber() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.STOCK_NO, vehicleInfoDto.getStockNumber());
        if (vehicleInfoDto.getRoNumber() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.RO_NO, vehicleInfoDto.getRoNumber());
        if (vehicleInfoDto.getPoNumber() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.PO_NO, vehicleInfoDto.getPoNumber());
        if (vehicleInfoDto.getVehicleLicensePlate() != null)
            VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.LIC_PLATE, vehicleInfoDto.getVehicleLicensePlate());
        if (vehicleInfoDto.getVehicleColor() != null)
            VehicleInfoScreenInteractions.selectColor(vehicleInfoDto.getVehicleColor());
    }

    public static void setVIN(String vin) {
        GeneralSteps.dismissHelpingScreenIfPresent();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(vehicleInfoScreen.getRootElement());
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vin);
    }

    public static void selectTechnicians(List<Employee> employeeList) {
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectEvenlyOption();
        employeeList.forEach(employee -> TechnicianScreenInteractions.selectTechnician(employee.getEmployeeName()));
        TechnicianScreenInteractions.acceptScreen();
    }

    public static void deselectTechnicians(List<Employee> employeeList) {
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectEvenlyOption();
        employeeList.forEach(employee -> TechnicianScreenInteractions.deselectTechnician(employee.getEmployeeName()));
        TechnicianScreenInteractions.acceptScreen();
    }

    public static List<Employee> getSelectedTechnicians() {
        return WaitUtils.getGeneralWebdriverWait().until((driver) ->
                Arrays.stream(VehicleInfoScreenInteractions.getDataFieldValue(VehicleDataField.VEHICLE_TECH).split(",")).map(string -> {
                    Employee employee = new Employee();
                    employee.setEmployeeFirstName(string.trim().split(" ")[0].trim());
                    employee.setEmployeeLastName(string.trim().split(" ")[1].trim());
                    return employee;
                }).collect(Collectors.toList()));
    }
}
