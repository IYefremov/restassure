package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.restclient.ApiUtils;
import com.cyberiansoft.test.vnext.restclient.WorkOrdersListResponse;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import retrofit2.Response;

import java.io.IOException;

public class WorkOrderFactory extends BasePooledObjectFactory<WorkOrderDTO> {

    private WorkOrderDTO workOrderDTO;
    private WorkOrderTypes workOrderType;
    private String employeeID;
    private String licenseID;
    private String deviceID;
    private String appID;
    private String appLicenseEntity;
    private int lastWONumber;

    public WorkOrderFactory(WorkOrderDTO workOrderDTO, WorkOrderTypes workOrderType, String employeeID,
                            String licenseID, String deviceID, String appID,
                            String appLicenseEntity) {
        super();

        this.workOrderDTO = workOrderDTO;
        this.workOrderType = workOrderType;
        this.employeeID = employeeID;
        this.licenseID = licenseID;
        this.deviceID = deviceID;
        this.appID = appID;
        this.appLicenseEntity = appLicenseEntity;
    }

    @Override
    public WorkOrderDTO create() throws Exception {
        if (lastWONumber == 0)
            lastWONumber = getLastWorkOrderNumber(licenseID, deviceID, appID,
                    employeeID, true, GlobalUtils.getWorkOrderSymbol() + appLicenseEntity).getLocalNo();

        /*Gson gson = new Gson();
        System.out.println("===================" + gson.toJson(workOrderDTO));

        JtwigTemplate template = JtwigTemplate. (gson.toJson(workOrderDTO)));
        JtwigModel model = JtwigModel.newModel()
                .with("faker", "");

        String json = template.render(model);

        RetailCustomer retailCustomer = JSonDataParser.getTestDataFromJson(new File(VNextDataInfo.getInstance().getPathToDataFiles() +
                VNextDataInfo.getInstance().getDefaultRetaiCustomerDataFileName()), RetailCustomer.class);
        System.out.println("===================" + retailCustomer.getClientId());
        Gson gson = new Gson();
        WorkOrderDTO workOrder1 = gson.fromJson(json, WorkOrderDTO.class);

        System.out.println("===================" + workOrder1.getClientId());
        System.out.println(json);

        device.setEmployeeId(employee.getEmployeeID());

        String orderId = GlobalUtils.getUUID();
        String vehicleID =  GlobalUtils.getUUID();
        JSONObject jso = JSONDataProvider.extractData_JSON(workOrderTemplateFilePath);


        WorkOrderDTO workOrder = JSonDataParser.getTestDataFromJson(new File(workOrderTemplateFilePath), WorkOrderDTO.class);
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
                employee.getEmployeeID(), true, workOrder).execute();*/
        return null;
    }

    @Override
    public PooledObject<WorkOrderDTO> wrap(WorkOrderDTO workOrder) {
        return new DefaultPooledObject<WorkOrderDTO>(workOrder);
    }

    public static WorkOrderDTO getLastWorkOrderNumber(String licenceId, String deviceId, String applicationId,
                                              String userId, boolean json, String searchText) throws IOException {
        Response<WorkOrdersListResponse> res = ApiUtils.getAPIService().getLastMyWorkOrder(licenceId,
                deviceId, applicationId,userId , json, 0, 1, "-1",
                searchText).execute();
        return res.body().getResult().get(0);
    }
}
