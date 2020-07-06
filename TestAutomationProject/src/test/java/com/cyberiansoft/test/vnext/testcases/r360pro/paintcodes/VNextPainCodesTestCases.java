package com.cyberiansoft.test.vnext.testcases.r360pro.paintcodes;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.dialogs.InformationDialogSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PaintCodesScreenValidations;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.ViewScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Year;

public class VNextPainCodesTestCases extends BaseTestClass {

    @BeforeClass(description="Inspections Paint Codes Test Cases")
    public void beforeClass()  {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsPaintCodesTestCasesDataPath();
    }


    //todo add to suite. Add View screen validation
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyPaintCodesFunctionality(String rowID,
                                                                String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String firstColor = "Alpina Blue";
        final String secondColor = "Black Forest";

        Year year = Year.now();
        HomeScreenSteps.openCreateMyInspection();
        CustomersScreenSteps.selectCustomer(testcustomer);
        InspectionSteps.selectInspectionType(InspectionTypes.O_KRAMAR);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        InformationDialogSteps.clickOkButton();
        ScreenNavigationSteps.pressBackButton();

        VehicleInfoScreenInteractions.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());

        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        InformationDialogSteps.clickOkButton();
        ScreenNavigationSteps.pressBackButton();
        VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());

        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        SearchSteps.searchByText("Blue");
        PaintCodesScreenValidations.verifyPainCodesListContainsItems("Blue");

        PaintCodesSteps.selectPaintCodeByColor(firstColor);
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.COLOR, firstColor);
        InspectionSteps.cancelInspection();

        InspectionSteps.clickAddInspectionButton();
        CustomersScreenSteps.selectCustomer(testcustomer);
        InspectionSteps.selectInspectionType(InspectionTypes.O_KRAMAR);
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, inspectionData.getVehicleInfo().getVINNumber());
        VehicleInfoScreenInteractions.setYear(year.plusYears(1).toString());
        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        PaintCodesScreenValidations.verifyPainCodesListEmpty();
        ScreenNavigationSteps.pressBackButton();
        VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());
        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        PaintCodesSteps.selectPaintCodeByColor(secondColor);
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.COLOR, secondColor);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyPainCodeIsPresent(secondColor);
        ScreenNavigationSteps.pressBackButton();

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWorkOrdersVerifyPaintCodesFunctionality(String rowID,
                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        final String firstColor = "Alpina Blue";
        final String secondColor = "Black Forest";

        Year year = Year.now();
        HomeScreenSteps.openCreateMyWorkOrder();
        CustomersScreenSteps.selectCustomer(testcustomer);
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        InformationDialogSteps.clickOkButton();
        ScreenNavigationSteps.pressBackButton();

        VehicleInfoScreenInteractions.selectMakeAndModel(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel());

        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        InformationDialogSteps.clickOkButton();
        ScreenNavigationSteps.pressBackButton();
        VehicleInfoScreenInteractions.setYear(workOrderData.getVehicleInfoData().getVehicleYear());

        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        SearchSteps.searchByText("Blue");
        PaintCodesScreenValidations.verifyPainCodesListContainsItems("Blue");

        PaintCodesSteps.selectPaintCodeByColor(firstColor);
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.COLOR, firstColor);
        WorkOrderSteps.cancelWorkOrder();

        WorkOrderSteps.clickAddWorkOrderButton();
        CustomersScreenSteps.selectCustomer(testcustomer);
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenInteractions.setYear(year.plusYears(1).toString());
        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        PaintCodesScreenValidations.verifyPainCodesListEmpty();
        ScreenNavigationSteps.pressBackButton();
        VehicleInfoScreenInteractions.setYear(workOrderData.getVehicleInfoData().getVehicleYear());
        VehicleInfoScreenInteractions.clickColorCell();
        VehicleInfoScreenInteractions.switchToPaintCodesMode();
        PaintCodesSteps.selectPaintCodeByColor(secondColor);
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.COLOR, secondColor);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());


        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyPainCodeIsPresent(secondColor);
        ScreenNavigationSteps.pressBackButton();

        ScreenNavigationSteps.pressBackButton();
    }
}
