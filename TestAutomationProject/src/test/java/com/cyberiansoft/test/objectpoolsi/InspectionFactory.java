package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.r360.DeviceDTO;
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypeData;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.restclient.ApiUtils;
import com.cyberiansoft.test.vnext.restclient.BasicResponse;
import com.cyberiansoft.test.vnext.restclient.InspectionsListResponse;
import com.google.gson.Gson;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.json.simple.JSONObject;
import retrofit2.Response;

import java.io.IOException;

public class InspectionFactory extends BasePooledObjectFactory<InspectionDTO> {

    private InspectionTypes inspectionType;
    private String inspectionTemplateFilePath;
    private DeviceDTO device;
    private Employee employee;
    private String appID;
    private String appLicenseEntity;

    public InspectionFactory(InspectionTypes inspectionType, String inspectionTemplateFilePath,
                             DeviceDTO device, Employee employee, String appID,
                             String appLicenseEntity) {
        super();

        this.inspectionType = inspectionType;
        this.inspectionTemplateFilePath = inspectionTemplateFilePath;
        this.device = device;
        this.employee = employee;
        this.appID = appID;
        this.appLicenseEntity = appLicenseEntity;
    }

    @Override
    public InspectionDTO create() throws Exception {
        int inspnumber = Integer.valueOf(getLastInspectionNumber(device.getLicenceId(), device.getDeviceId(), appID,
                employee.getEmployeeID(), true, GlobalUtils.getInspectionSymbol() + appLicenseEntity).getLocalNo());
        device.setEmployeeId(employee.getEmployeeID());

        String estimationId = GlobalUtils.getUUID();
        String vehicleID =  GlobalUtils.getUUID();
        JSONObject jso = JSONDataProvider.extractData_JSON(inspectionTemplateFilePath);
        Gson gson = new Gson();
        InspectionDTO inspection = gson.fromJson(jso.toString(), InspectionDTO.class);

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

        return inspection;
    }

    @Override
    public PooledObject<InspectionDTO> wrap(InspectionDTO isnp) {
        // TODO Auto-generated method stub
        return new DefaultPooledObject<InspectionDTO>(isnp);
    }

    public InspectionDTO getLastInspectionNumber(String licenceId, String deviceId, String applicationId,
                                              String userId, boolean json, String searchText) throws IOException {
        Response<InspectionsListResponse> res = ApiUtils.getAPIService().getLastMyInspection(licenceId,
                deviceId, applicationId,userId , json, 0, 1, "-1",
                searchText).execute();
        return res.body().getResult().get(0);
    }

}
