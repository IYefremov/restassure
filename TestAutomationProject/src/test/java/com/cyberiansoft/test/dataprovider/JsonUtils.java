package com.cyberiansoft.test.dataprovider;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.r360.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static InvoiceOrderDTO getInvoiceOrders(File invoiceOrdersDTOFile) throws IOException {
        return JSonDataParser.getTestDataFromJson(invoiceOrdersDTOFile, InvoiceOrderDTO.class);
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

    public static InvoiceDTO getInvoiceDTO(File invoiceDTOFile, File deviceDTOFile,
                                           File invoiceOrdersDTOFile) throws IOException {
        InvoiceDTO inspectionDTO = JSonDataParser.getTestDataFromJson(invoiceDTOFile, InvoiceDTO.class);
        inspectionDTO.setDevice(getDeviceDTO(deviceDTOFile));
        List<InvoiceOrderDTO> invoiceOrders = new ArrayList<>();
        invoiceOrders.add(getInvoiceOrders(invoiceOrdersDTOFile));
        inspectionDTO.setInvoiceOrders(invoiceOrders);
        inspectionDTO.setOrders(invoiceOrders);
        return inspectionDTO;
    }
}
