package com.cyberiansoft.test.vnext.factories.workordertypes;

public class WorkOrderTypeData {
    private String workOrderTypeID;
    private boolean canBeFinalDraft;

    public WorkOrderTypeData(WorkOrderTypes workOrderType) {
        switch (workOrderType) {
            case O_KRAMAR:
                workOrderTypeID = "5db7c5ec-42c6-4e78-bab2-3f4edfc089b0";
                canBeFinalDraft= true;
                break;
            case O_KRAMAR2:
                workOrderTypeID = "5db7c5ec-42c6-4e78-bab2-3f4edfc089b0";
                canBeFinalDraft= true;
                break;
            case KRAMAR_AUTO:
                workOrderTypeID = "5db7c5ec-42c6-4e78-bab2-3f4edfc089b0";
                canBeFinalDraft= false;
                break;
            case KRAMAR_AUTO2:
                workOrderTypeID = "5db7c5ec-42c6-4e78-bab2-3f4edfc089b0";
                canBeFinalDraft= false;
                break;
            case ALL_AUTO_PHASES:
                workOrderTypeID = "5db7c5ec-42c6-4e78-bab2-3f4edfc089b0";
                canBeFinalDraft= false;
                break;
        }
    }

    public String getWorkOrderTypeID() { return workOrderTypeID; };

    public boolean isCanBeDraft() { return canBeFinalDraft; };
}
