package com.cyberiansoft.test.objectpools;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.r360.DeviceDTO;
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypeData;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.restclient.ApiUtils;
import com.cyberiansoft.test.vnext.restclient.BasicResponse;
import com.cyberiansoft.test.vnext.restclient.InspectionsListResponse;
import retrofit2.Response;

import java.io.IOException;

public class InspectionFactory implements ObjectFactory <InspectionDTO> {

    private InspectionTypes inspectionType;
    private InspectionDTO inspection;
    private DeviceDTO device;
    private Employee employee;
    private String appID;
    private String appLicenseEntity;

    public InspectionFactory(InspectionTypes inspectionType, InspectionDTO inspection,
                                 DeviceDTO device, Employee employee, String appID,
                                 String appLicenseEntity) {
        super();

        this.inspectionType = inspectionType;
        this.inspection = inspection;
        this.device = device;
        this.employee = employee;
        this.appID = appID;
        this.appLicenseEntity = appLicenseEntity;
    }


    @Override
    public InspectionDTO createNew() throws IOException {
        int inspnumber = getLastInspectionNumber(device.getLicenceId(), device.getDeviceId(), appID,
                employee.getEmployeeID(), true, GlobalUtils.INSPECTION_SYMBOL + appLicenseEntity).getLocalNo();
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
        return getLastInspectionNumber(device.getLicenceId(), device.getDeviceId(), appID,
                employee.getEmployeeID(), true, GlobalUtils.INSPECTION_SYMBOL + appLicenseEntity);
    }

    public InspectionDTO getLastInspectionNumber(String licenceId, String deviceId, String applicationId,
                                              String userId, boolean json, String searchText) throws IOException {
        Response<InspectionsListResponse> res = ApiUtils.getAPIService().getLastMyInspection(licenceId,
                deviceId, applicationId,userId , json, 0, 1, "-1",
                searchText).execute();
        return res.body().getResult().get(0);
    }
}
