package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

public enum ProdInspectionsTypes implements IInspectionsTypes {

    MATRIX_INSPECTION("Matrix Inspection"),
    PAINT_INSPECTION("Paint inspection"),
    INTERIOR_INSPECTION("Interior inspection"),
    WHEEL_INSPECTION("Wheel Inspection"),
    INTERIOR_DETAIL("Interior Detail"),
    EXTERIOR_DETAIL("Exterior Detail");

    private final String inspType;

    ProdInspectionsTypes(final String srType) {
        this.inspType = srType;
    }

    public String getInspectionTypeName() {
        return inspType;
    }

    public ProdInspectionsTypes getInspectionType(){
        for(ProdInspectionsTypes type : values()){
            if(type.getInspectionTypeName().equals(inspType)){
                return type;
            }
        }

        throw new IllegalArgumentException(inspType + " is not a valid InspectionsTypes");
    }
}
