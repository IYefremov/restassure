package com.cyberiansoft.test.ios10_client.types.workorderstypes;

public enum UATWorkOrderTypes implements IWorkOrdersTypes {

    WO_FINAL_INVOICE("WO_Final_Invoice_AQA"),
    WO_DRAFT_INVOICE("WO_Draft_Invoice"),
    WO_MONITOR("WO_Monitor_AQA");

    private final String woType;

    UATWorkOrderTypes(final String srType) {
        this.woType = srType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public UATWorkOrderTypes getWorkOrderType() {
        for (UATWorkOrderTypes type : values()) {
            if (type.getWorkOrderTypeName().equals(woType)) {
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid WorkOrdersTypes");
    }
}
