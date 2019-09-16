package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SinglePageInspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import lombok.Getter;

public enum InspectionsTypes implements IInspectionsTypes {

    DEFAULT("Default"),
    DEFAULT_INSPECTION_TYPE("Default inspection type"),
    FOR_COPY_INSP_INSPTYPE("For_Copy_Insp"),
    INSP_NOTLA_TS_INSPTYPE("Insp_NotLA_TS"),
    INSP_LA_DA_INSPTYPE("Insp_LA_DA"),
    INSP_FOR_SR_INSPTYPE("Insp_for_SR"),
    INSPTYPE_FOR_SR_INSPTYPE("Insp_type_for_Ser_Req"),
    INSP_CHANGE_INSPTYPE("Insp_Change_customer"),
    TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE("Type with preselected companies"),
    CLIENT_19319_INSPTYPE("Client 19319"),
    VITALY_TEST_INSPTYPE("Vitaly_TEST_Insp"),
    INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ("Ins_type_with_PrMatrix_App_req"),
    INSP_TYPE_FOR_PRICE_MATRIX("Ins_type_with_price_matrix"),
    INSP_DRAFT_MODE("Insp_Draft_Mode"),
    INSP_FOR_CALC("Insp_for_calc"),
    INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT("Insp_for_auto_WO_line_appr_multiselect"),
    INSP_FOR_AUTO_WO_LINE_APPR("Inspection_for_auto_WO_line_appr"),
    INSP_WITH_PART_SERVICES("Insp_with_part_services"),
    INSP_WITH_0_PRICE("Insp_with_0_Price"),
    INS_LINE_APPROVE_OFF("Ins_line_approve_OFF"),
    INSP_DRAFT_SINGLE_PAGE("Insp_Draft_Single_page"),
    INSP_SERVICE_TYPE_WITH_OUT_REQUIRED("Insp_Service_Type_with_out_required"),
    INSPECTION_GROUP_SERVICE("Inspection_group_service"),
    INSPECTION_DIRECT_ASSIGN("Inspection_direct_assign"),
    INSPECTION_VIN_ONLY("Inspection_VIN_only"),
    INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE("Insp_for_auto_WO_line_appr_simple"),
    INSP_SMOKE_TEST("Insp_smoke_test");

    @Getter
    private final String inspType;

    InspectionsTypes(final String srType) {
        this.inspType = srType;
    }

    public String getInspectionTypeName() {
        return inspType;
    }

    public InspectionsTypes getInspectionType(){
        for(InspectionsTypes type : values()){
            if(type.getInspectionTypeName().equals(inspType)){
                return type;
            }
        }

        throw new IllegalArgumentException(inspType + " is not a valid InspectionsTypes");
    }
}
