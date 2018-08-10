package com.cyberiansoft.test.vnext.factories.workordertypes;

public class WorkOrderTypeData {
    private String workOrderTypeID;

    public WorkOrderTypeData(WorkOrderTypes workOrderType) {
        switch (workOrderType) {
            case O_KRAMAR:
                workOrderTypeID = "5db7c5ec-42c6-4e78-bab2-3f4edfc089b0";
                break;
        }
    }

    public String getWorkOrderTypeID() { return workOrderTypeID; };

}
