package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SinglePageInspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import lombok.Getter;

public enum UATInspectionTypes implements IInspectionsTypes {

    INSP_APPROVE_MULTISELECT("Insp_approve_multiselect_AQA");

    @Getter
    private final String inspType;

    UATInspectionTypes(final String srType) {
        this.inspType = srType;
    }

    public String getInspectionTypeName() {
        return inspType;
    }

    public UATInspectionTypes getInspectionType(){
        for(UATInspectionTypes type : values()){
            if(type.getInspectionTypeName().equals(inspType)){
                return type;
            }
        }

        throw new IllegalArgumentException(inspType + " is not a valid InspectionsTypes");
    }

    public <T extends IBaseWizardScreen> T getFirstVizardScreen() {
        UATInspectionTypes type = getInspectionType();
        switch (type) {
            case INSP_APPROVE_MULTISELECT:
                if (BaseTestCase.inspSinglePageMode)
                    return (T) new SinglePageInspectionScreen();
                else if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new VehicleScreen();
                else
                    return (T) new RegularVehicleScreen();
        }
        return null;
    }
}
