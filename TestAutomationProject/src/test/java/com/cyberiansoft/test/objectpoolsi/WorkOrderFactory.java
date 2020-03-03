package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypeData;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.restclient.ApiUtils;
import com.cyberiansoft.test.vnext.restclient.BasicResponse;
import com.cyberiansoft.test.vnext.restclient.WorkOrdersListResponse;
import com.google.gson.Gson;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import retrofit2.Response;

import java.io.IOException;

public class WorkOrderFactory extends BasePooledObjectFactory<WorkOrderDTO> {

    private WorkOrderDTO workOrderDTO;
    private WorkOrderTypes workOrderType;
    private String customerID;
    private String employeeID;
    private String licenseID;
    private String deviceID;
    private String appID;
    private String appLicenseEntity;
    private int lastWONumber;

    public WorkOrderFactory(WorkOrderDTO workOrderDTO, WorkOrderTypes workOrderType, String customerID,
                            String employeeID, String licenseID, String deviceID, String appID,
                            String appLicenseEntity) {
        super();

        this.workOrderDTO = workOrderDTO;
        this.workOrderType = workOrderType;
        this.customerID = customerID;
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
                    employeeID, true, GlobalUtils.WORK_ORDER_SYMBOL + appLicenseEntity).getLocalNo();

        Gson gson = new Gson();
        JtwigTemplate template = JtwigTemplate.inlineTemplate(gson.toJson(workOrderDTO));
        JtwigModel model = JtwigModel.newModel()
                .with("VehicleID", GlobalUtils.getUUID())
                .with("OrderId", GlobalUtils.getUUID())
                .with("ClientId", customerID)
                .with("EmployeeId", employeeID)
                .with("OrderTypeId", new WorkOrderTypeData(workOrderType).getWorkOrderTypeID())
                .with("LicenceId", licenseID)
                .with("DeviceId", deviceID)
                .with("UTCTime", GlobalUtils.getVNextInspectionCreationTime())
                .with("OrderDate", GlobalUtils.getVNextInspectionDate());

        String json = template.render(model);

        WorkOrderDTO newWorkOrder = JSonDataParser.getTestDataFromJson(json, WorkOrderDTO.class);
        newWorkOrder.setLocalNo(++lastWONumber);

        Response<BasicResponse> res = ApiUtils.getAPIService().saveWorkOrder(newWorkOrder.getOrderId(),
                newWorkOrder.getLicenceId(),
                newWorkOrder.getDevice().getDeviceId(), appID,
                newWorkOrder.getEmployeeId(), true, newWorkOrder).execute();

        return newWorkOrder;
    }

    @Override
    public PooledObject<WorkOrderDTO> wrap(WorkOrderDTO workOrder) {
        return new DefaultPooledObject<>(workOrder);
    }

    public static WorkOrderDTO getLastWorkOrderNumber(String licenceId, String deviceId, String applicationId,
                                              String userId, boolean json, String searchText) throws IOException {
        Response<WorkOrdersListResponse> res = ApiUtils.getAPIService().getLastMyWorkOrder(licenceId,
                deviceId, applicationId,userId , json, 0, 1, "-1",
                searchText).execute();
        return res.body().getResult().get(0);
    }
}
