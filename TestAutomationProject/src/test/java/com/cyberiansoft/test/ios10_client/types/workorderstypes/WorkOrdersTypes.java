package com.cyberiansoft.test.ios10_client.types.workorderstypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum WorkOrdersTypes implements  IWorkOrdersTypes {

    WO_FORR_MONITOR_WOTYPE("WO_for_Monitor"),
    SPECIFIC_CLIENT_TEST_WO1("Spec_Client_Test_WO_1"),
    WO_CLIENT_CHANGING_ON("WO client changing ON"),
    WOTYPE_BLOCK_VIN_ON("WO type - Block VIN is ON"),
    WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON("WO type Block for the same Services"),
    WO_WITH_PRESELECTED_CLIENTS("WO with preselected clients"),
    WO_TYPE_FOR_PRICE_MATRIX("WO_type_for_price_matrix"),
    WO_FOR_INV_PRINT("WO_for_invoice_print"),
    WO_MONITOR_DEVICE("WO_Monitor_Device"),
    WO_FOR_SR("WO_For_SR"),
    WO_TYPE_FOR_TEST_FEE("WO_Type_for_test_fee"),
    WO_TYPE_FOR_MONITOR("WO_type_for_monitor"),
    WO_FOR_FEE_ITEM_IN_2_PACKS("WO_for_fee_item_in_2_packs"),
    WO_FEE_PRICE_OVERRIDE("WO_fee_price_override"),
    WO_TYPE_FOR_CALC("WO_type_for_calc"),
    WO_TOTAL_SALE_NOT_REQUIRED("WO_Total_Sale_not_required"),
    WO_SMOKE_MONITOR("WO_smoke_monitor"),
    WO_SMOKE_TEST("WO_smoke_test"),
    WO_TYPE_WITH_JOB("WO_type_with_job"),
    WO_FOR_INVOICE_PRINT("WO_for_invoice_print"),
    WO_DELAY_START("WO_Delay_Start"),
    WO_VIN_ONLY("WO_VIN_only"),
    WO_VEHICLE_TRIM_VALIDATION("WO_Vehicle_Trim_Validation"),
    WO_PANEL_GROUP("WO_Panel_group"),
    WO_GROUP_SERVICE_TYPE("WO_group_Service_type"),
    WO_WITH_PART_SERVICE("WO_with_part_service"),
    WO_MONITOR_REQUIRED_START("WO_MOnitor_Required_Start"),
    WO_BUNDLE_REQ_DEF_TECH("WO_bundle_req_def_tech"),
    WO_MONITOR_REQUIRED_SERVICES_ALL("WO_Monitor_required_services_All");

    private final String woType;

    WorkOrdersTypes(final String srType) {
        this.woType = srType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public WorkOrdersTypes getWorkOrderType(){
        for(WorkOrdersTypes type : values()){
            if(type.getWorkOrderTypeName().equals(woType)){
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid WorkOrdersTypes");
    }

    public <T extends IBaseWizardScreen>T getFirstVizardScreen() {
        WorkOrdersTypes type = getWorkOrderType();
        switch (type) {
            case WO_FORR_MONITOR_WOTYPE:
            case WO_TYPE_FOR_TEST_FEE:
            case WO_FOR_FEE_ITEM_IN_2_PACKS:
            case WO_FEE_PRICE_OVERRIDE:
            case SPECIFIC_CLIENT_TEST_WO1:
            case WO_CLIENT_CHANGING_ON:
            case WOTYPE_BLOCK_VIN_ON:
            case WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON:
            case WO_WITH_PRESELECTED_CLIENTS:
            case WO_TYPE_FOR_PRICE_MATRIX:
            case WO_FOR_INV_PRINT:
            case WO_MONITOR_DEVICE:
            case WO_FOR_SR:
            case WO_TYPE_FOR_MONITOR:
            case WO_TYPE_FOR_CALC:
            case WO_TOTAL_SALE_NOT_REQUIRED:
            case WO_SMOKE_MONITOR:
            case WO_SMOKE_TEST:
            case WO_FOR_INVOICE_PRINT:
            case WO_DELAY_START:
            case WO_VIN_ONLY:
            case WO_VEHICLE_TRIM_VALIDATION:
            case WO_PANEL_GROUP:
            case WO_WITH_PART_SERVICE:
            case WO_GROUP_SERVICE_TYPE:
            case WO_TYPE_WITH_JOB:
            case WO_MONITOR_REQUIRED_START:
            case WO_BUNDLE_REQ_DEF_TECH:
            case WO_MONITOR_REQUIRED_SERVICES_ALL:
                if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new VehicleScreen();
                else
                    return (T) new RegularVehicleScreen();

        }
        return null;
    }

}
