package com.cyberiansoft.test.ios10_client.types.servicerequeststypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum UATServiceRequestTypes implements IServiceRequestTypes {

    SR_TYPE_ALL_PHASES("SR_type_all_phases");

    private final String srType;

    UATServiceRequestTypes(final String srType) {
        this.srType = srType;
    }

    public String getServiceRequestTypeName() {
        return srType;
    }

    public UATServiceRequestTypes getServiceRequestType(){
        for(UATServiceRequestTypes type : values()){
            if(type.getServiceRequestTypeName().equals(srType)){
                return type;
            }
        }

        throw new IllegalArgumentException(srType + " is not a valid ServiceRequestTypes");
    }

    public <T extends IBaseWizardScreen>T getFirstVizardScreen() {
        UATServiceRequestTypes type = getServiceRequestType();
        switch (type) {
            case SR_TYPE_ALL_PHASES:
                if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new VehicleScreen();
                else
                    return (T) new RegularVehicleScreen();

        }
        return null;
    }
}
