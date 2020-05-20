package com.cyberiansoft.test.vnext.factories.inspectiontypes;

public enum InspectionTypes {

    O_KRAMAR("O_Kramar"),
    O_KRAMAR2("O_Kramar2"),
    O_KRAMAR3("O_Kramar3"),
    INSP_TYPE_APPROV_REQUIRED("Insp_type_approv_req"),
    O_KRAMAR_NO_SHARING("O_Kramar_No_Sharing"),
    AUTOMATION_MONITORING("automationMonitoring"),
    ROZ_QUESTIONS_IT("Roz_questions_IT"),
    ROZ_TEXT_QUESTION("Roz_text_questions"),
    ROZ_WITHOUT_QUESTIONS("Rozstalnoy_IT_without_questions"),
    ROZSTALNOY_IT("Rozstalnoy_IT"),
    ROZSTALNOY_TEXT_QUESTIONS_IT("Roz_text_questions_IT"),
    WITH_QUESTIONS_NOT_REQUIRED("Insp with QF not required"),
    AUTOTEST_QUESTIONS_FORMS("Autotest - Question forms"),
    WITH_QUESTIONS_ANSWER_SERVICES("Insp with QF answer service");

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

        throw new IllegalArgumentException(inspType + " is not a valid Inspection Type");
    }
}
