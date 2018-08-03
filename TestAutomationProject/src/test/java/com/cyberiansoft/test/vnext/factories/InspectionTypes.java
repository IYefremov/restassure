package com.cyberiansoft.test.vnext.factories;

public enum InspectionTypes {

    O_KRAMAR("O_Kramar"),
    O_KRAMAR2("O_Kramar2"),
    O_KRAMAR3("O_Kramar3"),
    INSP_TYPE_APPROV_REQUIRED("Insp_type_approv_req"),
    ANASTASIA_TEAM("Anastasia_team");

    private final String inspType;

    InspectionTypes(final String inspType) {
        this.inspType = inspType;
    }

    public String getInspectionTypeName() {
        return inspType;
    }

    public InspectionTypes getInspectionType(){
        for(InspectionTypes type : values()){
            if(type.getInspectionTypeName().equals(inspType)){
                return type;
            }
        }

        throw new IllegalArgumentException(inspType + " is not a valid DentWizardInvoiceTypes");
    }
}
