package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

public enum DentWizardInspectionsTypes implements IInspectionsTypes {

    carmaxinspectiontype("CarMax"),
    routecanadainspectiontype("Route - Canada"),
    routeinspectiontype("Route"),
    wizprotrackerrouteunspectiontype("WizardPro Tracker Route"),
    servicedriveinspectiondertype("Service Drive"),
    economicalinspectiondertype("Economical"),
    wizprotrackerrouteinspectiontype("WizardPro Tracker Route"),
    wizardprotrackerrouteinspectiondertype("WizardPro Tracker Service");

    private final String inspType;

    DentWizardInspectionsTypes(final String srType) {
        this.inspType = srType;
    }

    public String getInspectionTypeName() {
        return inspType;
    }

    public DentWizardInspectionsTypes getInspectionType(){
        for(DentWizardInspectionsTypes type : values()){
            if(type.getInspectionTypeName().equals(inspType)){
                return type;
            }
        }

        throw new IllegalArgumentException(inspType + " is not a valid InspectionsTypes");
    }
}
