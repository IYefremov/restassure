package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.InspectionTypesPopup;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;

public class InspectionTypesSteps {

    public static void selectInspectionType(IInspectionsTypes inspectionType) {
        InspectionTypesPopup inspectionTypesPopup = new InspectionTypesPopup();
        inspectionTypesPopup.selectInspectionType(inspectionType);
    }
}
