package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
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
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import retrofit2.Response;

import java.io.IOException;

public class InspectionFactory extends BasePooledObjectFactory<InspectionDTO> {

    private InspectionDTO inspectionDTO;
    private InspectionTypes inspectionType;
    private String customerID;
    private String employeeID;
    private String licenseID;
    private String deviceID;
    private String appID;
    private String appLicenseEntity;

    public InspectionFactory(InspectionDTO inspectionDTO, InspectionTypes inspectionType, String customerID,
                             String employeeID, String licenseID, String deviceID, String appID,
                             String appLicenseEntity) {
        super();

        this.inspectionDTO = inspectionDTO;
        this.inspectionType = inspectionType;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.licenseID = licenseID;
        this.deviceID = deviceID;
        this.appID = appID;
        this.appLicenseEntity = appLicenseEntity;
    }

    @Override
    public InspectionDTO create() throws Exception {
        int inspnumber = Integer.valueOf(getLastInspectionNumber(licenseID, deviceID, appID,
                employeeID, true, GlobalUtils.getInspectionSymbol() + appLicenseEntity).getLocalNo());

        Gson gson = new Gson();
        JtwigTemplate template = JtwigTemplate.inlineTemplate(gson.toJson(inspectionDTO));
        JtwigModel model = JtwigModel.newModel()
                .with("VehicleID", GlobalUtils.getUUID())
                .with("EstimationId", GlobalUtils.getUUID())
                .with("ClientId", customerID)
                .with("EmployeeId", employeeID)
                .with("LicenceId", licenseID)
                .with("DeviceId", deviceID)
                .with("EstimationTypeId", new InspectionTypeData(inspectionType).getInspTypeID())
                .with("UTCTime", GlobalUtils.getVNextInspectionCreationTime())
                .with("EstimationDate", GlobalUtils.getVNextInspectionDate());

        String json = template.render(model);

        InspectionDTO newInspection = JSonDataParser.getTestDataFromJson(json, InspectionDTO.class);
        newInspection.setLocalNo(++inspnumber);

        Response<BasicResponse> res = ApiUtils.getAPIService().saveInspection(newInspection.getEstimationId(),
                newInspection.getLicenceID(),newInspection.getDevice().getDeviceId(), appID,
                newInspection.getEmployeeId(), true, newInspection).execute();


        return newInspection;
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
