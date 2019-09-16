package com.cyberiansoft.test.ios10_client.types.servicerequeststypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum ServiceRequestTypes implements IServiceRequestTypes {

    SR_EST_WO_REQ_SRTYPE("SR_Est_WO_Req"),
    SR_ALL_PHASES("SR_all_phases"),
    MULTIPLE_INSPECTION_SERVICE_TYPE_ALM("Multiple Inspection Service Type - ALM"),
    SR_ONLY_ACC_ESTIMATE("SR_only_Acc_Estimate"),
    SR_TYPE_WO_AUTO_CREATE("SR_type_WO_auto_create"),
    SR_TYPE_CHECKIN_ON("SR_type_check_in_ON"),
    SR_TYPE_DONOT_ALLOW_CLOSE_SR("SR_type_do_not_allow_close_SR"),
    SR_TYPE_ALLOW_CLOSE_SR("SR_type_allow_close_SR"),
    SR_WO_ONLY("SR_WO_only"),
    SR_INSP_ONLY("SR_Insp_only"),
    SR_ACCEPT_ON_MOBILE("SR_Accept_on_mobile");

    private final String srType;

    ServiceRequestTypes(final String srType) {
        this.srType = srType;
    }

    public String getServiceRequestTypeName() {
        return srType;
    }

    public ServiceRequestTypes getServiceRequestType(){
        for(ServiceRequestTypes type : values()){
            if(type.getServiceRequestTypeName().equals(srType)){
                return type;
            }
        }

        throw new IllegalArgumentException(srType + " is not a valid ServiceRequestTypes");
    }
}
