package com.cyberiansoft.test.objectpools;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.r360.DeviceDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypeData;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.restclient.ApiUtils;
import com.cyberiansoft.test.vnext.restclient.BasicResponse;
import com.cyberiansoft.test.vnext.restclient.WorkOrdersListResponse;
import retrofit2.Response;

import java.io.IOException;

public class WOFactory implements ObjectFactory <WorkOrderDTO> {
    private WorkOrderTypes workOrderType;
    private WorkOrderDTO workOrder;
    private DeviceDTO device;
    private Employee employee;
    private String appID;
    private String appLicenseEntity;
    private int lastWONumber;

    public WOFactory(WorkOrderTypes workOrderType, WorkOrderDTO workOrder,
                            DeviceDTO device, Employee employee, String appID,
                            String appLicenseEntity) {
        super();

        this.workOrderType = workOrderType;
        this.workOrder = workOrder;
        this.device = device;
        this.employee = employee;
        this.appID = appID;
        this.appLicenseEntity = appLicenseEntity;
    }

    @Override
    public WorkOrderDTO createNew() throws IOException {
        if (lastWONumber == 0)
            lastWONumber = getLastWorkOrderNumber(device.getLicenceId(), device.getDeviceId(), appID,
                    employee.getEmployeeID(), true, GlobalUtils.WORK_ORDER_SYMBOL + appLicenseEntity).getLocalNo();
        device.setEmployeeId(employee.getEmployeeID());

        String orderId = GlobalUtils.getUUID();
        String vehicleID =  GlobalUtils.getUUID();
        workOrder.setVehicleID(vehicleID);
        workOrder.setOrderId(orderId);
        workOrder.setOrderTypeId(new WorkOrderTypeData(workOrderType).getWorkOrderTypeID());
        workOrder.setUTCTime(GlobalUtils.getVNextInspectionCreationTime());
        workOrder.setOrderDate(GlobalUtils.getVNextInspectionDate());
        workOrder.setLocalNo(++lastWONumber);
        workOrder.setDevice(device);
        workOrder.getVehicle().setVehicleID(vehicleID);
        workOrder.getOrderEmployees().get(0).setEmployeeId(employee.getEmployeeID());
        workOrder.getOrderEmployees().get(0).setOrderId(orderId);

        Response<BasicResponse> res = ApiUtils.getAPIService().saveWorkOrder(orderId, device.getLicenceId(),
                device.getDeviceId(), appID,
                employee.getEmployeeID(), true, workOrder).execute();
        System.out.println("===============================" + lastWONumber);
        System.out.println("===============================" + workOrder.getLocalNo());
        WorkOrderDTO newWO =  getLastWorkOrderNumber(device.getLicenceId(), device.getDeviceId(), appID,
                employee.getEmployeeID(), true, GlobalUtils.WORK_ORDER_SYMBOL + appLicenseEntity);
        newWO.setLocalNo(lastWONumber);
        return newWO;
    }

    public static WorkOrderDTO getLastWorkOrderNumber(String licenceId, String deviceId, String applicationId,
                                                   String userId, boolean json, String searchText) throws IOException {
        Response<WorkOrdersListResponse> res = ApiUtils.getAPIService().getLastMyWorkOrder(licenceId,
                deviceId, applicationId,userId , json, 0, 1, "-1",
                searchText).execute();
        return res.body().getResult().get(0);
    }
}
