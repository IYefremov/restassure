package com.cyberiansoft.test.dataprovider;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.r360.DeviceDTO;
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataclasses.r360.VehicleDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    public static VehicleDTO getVehicleDTO(File vehicleDTOFile) throws IOException {
        return JSonDataParser.getTestDataFromJson(vehicleDTOFile, VehicleDTO.class);
    }

    public static Employee getEmployeeDTO(File employeeDTOFile) throws IOException {
        return JSonDataParser.getTestDataFromJson(employeeDTOFile, Employee.class);
    }

    public static DeviceDTO getDeviceDTO(File deviceDTOFile) throws IOException {
        return JSonDataParser.getTestDataFromJson(deviceDTOFile, DeviceDTO.class);
    }

    public static RetailCustomer getRetailCustomerDTO(File customerDTOFile) throws IOException {
        return JSonDataParser.getTestDataFromJson(customerDTOFile, RetailCustomer.class);
    }

    public static WorkOrderDTO getWorkOrderDTO(File workOrderDTOFile, File vehicleDTOFile,
                                               File deviceDTOFile) throws IOException {
        WorkOrderDTO workOrderDTO = JSonDataParser.getTestDataFromJson(workOrderDTOFile, WorkOrderDTO.class);
        workOrderDTO.setDevice(getDeviceDTO(deviceDTOFile));
        workOrderDTO.setVehicle(getVehicleDTO(vehicleDTOFile));
        return workOrderDTO;
    }

    public static InspectionDTO getInspectionDTO(File inspectionDTOFile, File vehicleDTOFile,
                                                 File deviceDTOFile) throws IOException {
        InspectionDTO inspectionDTO = JSonDataParser.getTestDataFromJson(inspectionDTOFile, InspectionDTO.class);
        inspectionDTO.setDevice(getDeviceDTO(deviceDTOFile));
        inspectionDTO.setVehicle(getVehicleDTO(vehicleDTOFile));
        return inspectionDTO;
    }
}
