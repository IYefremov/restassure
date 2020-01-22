package com.cyberiansoft.test.vnext.factories.workordertypes;

public enum WorkOrderTypes {

    O_KRAMAR("O_Kramar"),
    O_KRAMAR2("O_Kramar2"),
    KRAMAR_AUTO("Kramar_auto"),
    KRAMAR_AUTO2("Kramar_auto2"),
    O_KRAMAR_INVOICE("O_Kramar_Invoice"),
    O_KRAMAR_CREATE_INVOICE("O_Kramar_Create_Invoice"),
    O_KRAMAR_NO_DRAFT("O_Kramar_No_Draft"),
    O_KRAMAR_3_SERVICE_GROUPING("O_Kramar 3 Service grouping"),
    ALL_AUTO_PHASES("All_auto_Phases"),
    AUTOMATION_MONITORING("automationMonitoring");

    private final String woType;

    WorkOrderTypes(final String woType) {
        this.woType = woType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public WorkOrderTypes getWorkOrderType() {
        for (WorkOrderTypes type : values()) {
            if (type.getWorkOrderTypeName().equals(woType)) {
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid Work Order Type");
    }
}
