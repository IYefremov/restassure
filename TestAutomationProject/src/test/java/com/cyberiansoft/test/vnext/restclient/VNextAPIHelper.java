package com.cyberiansoft.test.vnext.restclient;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.r360.DeviceDTO;
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypeData;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypeData;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import retrofit2.Response;

import java.io.IOException;

public class VNextAPIHelper {

    //Todo  always  return  ""
    public static String createInspection(InspectionTypes inspectionType, InspectionDTO inspection,
                                          DeviceDTO device, Employee employee, String appID,
                                          String appLicenseEntity) throws IOException {

        int inspnumber = getLastInspectionNumber(device.getLicenceId(), device.getDeviceId(), appID,
                employee.getEmployeeID(), true, GlobalUtils.INSPECTION_SYMBOL + appLicenseEntity);
        device.setEmployeeId(employee.getEmployeeID());

        String estimationId = GlobalUtils.getUUID();
        String vehicleID =  GlobalUtils.getUUID();
        inspection.setVehicleID(vehicleID);
        inspection.setEstimationId(estimationId);
        inspection.setEstimationTypeId(new InspectionTypeData(inspectionType).getInspTypeID());
        inspection.setUTCTime(GlobalUtils.getVNextInspectionCreationTime());
        inspection.setEstimationDate(GlobalUtils.getVNextInspectionDate());
        inspection.setLocalNo(inspnumber+1);
        inspection.setDevice(device);
        inspection.getVehicle().setVehicleID(vehicleID);


        Response<BasicResponse> res = ApiUtils.getAPIService().saveInspection(estimationId, device.getLicenceId(),
                device.getDeviceId(), appID,
                employee.getEmployeeID(), true, inspection).execute();
        return "";
    }

    //Todo  always  return  ""
    public static String createWorkOrder(WorkOrderTypes workOrderType, WorkOrderDTO workOrder,
                                         DeviceDTO device, Employee employee, String appID,
                                         String appLicenseEntity) throws IOException {

        int inspnumber = getLastInspectionNumber(device.getLicenceId(), device.getDeviceId(), appID,
                employee.getEmployeeID(), true, GlobalUtils.INSPECTION_SYMBOL + appLicenseEntity);
        device.setEmployeeId(employee.getEmployeeID());

        String orderId = GlobalUtils.getUUID();
        String vehicleID =  GlobalUtils.getUUID();
        workOrder.setVehicleID(vehicleID);
        workOrder.setOrderId(orderId);
        workOrder.setOrderTypeId(new WorkOrderTypeData(workOrderType).getWorkOrderTypeID());
        workOrder.setUTCTime(GlobalUtils.getVNextInspectionCreationTime());
        workOrder.setOrderDate(GlobalUtils.getVNextInspectionDate());
        workOrder.setLocalNo(inspnumber+1);
        workOrder.setDevice(device);
        workOrder.getVehicle().setVehicleID(vehicleID);
        workOrder.getOrderEmployees().get(0).setEmployeeId(employee.getEmployeeID());
        workOrder.getOrderEmployees().get(0).setOrderId(orderId);

        Response<BasicResponse> res = ApiUtils.getAPIService().saveWorkOrder(orderId, device.getLicenceId(),
                device.getDeviceId(), appID,
                employee.getEmployeeID(), true, workOrder).execute();
        return "";
    }


    public static int getLastInspectionNumber(String licenceId, String deviceId, String applicationId,
                                              String userId, boolean json, String searchText) throws IOException {
        Response<InspectionsListResponse> res = ApiUtils.getAPIService().getLastMyInspection(licenceId,
                deviceId, applicationId,userId , json, 0, 1, "-1",
                searchText).execute();
        return res.body().getResult().get(0).getLocalNo();
    }
}
