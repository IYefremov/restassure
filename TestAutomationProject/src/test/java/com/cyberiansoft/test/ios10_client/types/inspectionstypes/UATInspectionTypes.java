package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

import lombok.Getter;

public enum UATInspectionTypes implements IInspectionsTypes {

    INSP_APPROVE_MULTISELECT("Insp_approve_multiselect_AQA"),
    INSPECTION_PART_GROUP("Inspection_Part_Group_AQA"),
    INSPECTION_SERVICE_GROUP("Inspection_Service_Group_AQA"),
    INSPECTION_NO_GROUP("Inspection_No_Group"),
    AUTOCREATEWO("AutoCreateWO"),
    INSP_ARBITRATION_DATE("Insp_arbitration_date");

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
}
