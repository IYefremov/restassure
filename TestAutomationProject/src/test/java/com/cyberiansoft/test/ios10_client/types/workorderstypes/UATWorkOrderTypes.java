package com.cyberiansoft.test.ios10_client.types.workorderstypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum UATWorkOrderTypes implements IWorkOrdersTypes {

    WO_FINAL_INVOICE("WO_Final_Invoice_AQA");

    private final String woType;

    UATWorkOrderTypes(final String srType) {
        this.woType = srType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public UATWorkOrderTypes getWorkOrderType() {
        for (UATWorkOrderTypes type : values()) {
            if (type.getWorkOrderTypeName().equals(woType)) {
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid WorkOrdersTypes");
    }
}
