package com.cyberiansoft.test.vnext.factories.inspectiontypes;

public class InspectionTypeData {

    private String inspTypeID;
    private boolean canBeFinalDraft;

    public InspectionTypeData(InspectionTypes inspectionType) {
        switch (inspectionType) {
            case O_KRAMAR:
                inspTypeID = "09c12a7f-a625-4f57-9c76-7288483193cf";
                canBeFinalDraft= false;
                break;
            case O_KRAMAR2:
                inspTypeID = "1b0b6caa-2b6f-4551-af0a-08d4d4b2380b";
                canBeFinalDraft= false;
                break;
            case O_KRAMAR3:
                inspTypeID = "78f25f3a-b01f-411a-8682-2db9168a963a";
                canBeFinalDraft= true;
                break;
            case INSP_TYPE_APPROV_REQUIRED:
                inspTypeID = "";
                canBeFinalDraft= false;
                break;
            case O_KRAMAR_NO_SHARING:
                inspTypeID = "";
                canBeFinalDraft= false;
                break;
        }
    }

    public String getInspTypeID() { return inspTypeID; }

    public boolean isCanBeFinalDraft() { return canBeFinalDraft; }
}
