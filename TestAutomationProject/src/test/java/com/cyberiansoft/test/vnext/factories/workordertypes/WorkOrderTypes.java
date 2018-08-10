package com.cyberiansoft.test.vnext.factories.workordertypes;

public enum WorkOrderTypes {

    O_KRAMAR("O_Kramar");

    private final String woType;

    WorkOrderTypes(final String woType) {
        this.woType = woType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public WorkOrderTypes getWorkOrderType(){
        for(WorkOrderTypes type : values()){
            if(type.getWorkOrderTypeName().equals(woType)){
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid Work Order Type");
    }
}
