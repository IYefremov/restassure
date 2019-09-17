package com.cyberiansoft.test.ios10_client.types.workorderstypes;

public enum DentWizardWorkOrdersTypes implements  IWorkOrdersTypes {

    wizprotrackerrouteworkordertype("WizardPro Tracker Route"),
    routeusworkordertype("Route - U.S."),
    servicedriveworkordertype("Service Drive"),
    routecanadaworkordertype("Route - Canada"),
    wholesailhailworkordertype("Wholesale Hail"),
    retailhailworkordertype("Retail Hail"),
    carmaxworkordertype("CarMax"),
    enterpriseworkordertype("Enterprise"),
    avisworkordertype("AVIS"),
    auctionworkordertype("Auction"),
    wizardprotrackeravisworkordertype("WizardPro Tracker Avis"),
    wizardprotrackerservicedriveworkordertype("WizardPro Tracker Service Drive");

    private final String woType;

    DentWizardWorkOrdersTypes(final String srType) {
        this.woType = srType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public DentWizardWorkOrdersTypes getWorkOrderType(){
        for(DentWizardWorkOrdersTypes type : values()){
            if(type.getWorkOrderTypeName().equals(woType)){
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid WorkOrdersTypes");
    }
}
