package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.dialogs.InformationDialogSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PaintCodesScreenValidations;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextInspectionsPainCodesTestCases extends BaseTestClass {

    @BeforeClass(description="Inspections Paint Codes Test Cases")
    public void beforeClass()  {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsPaintCodesTestCasesDataPath();
    }


    //todo add to suite. Add View screen validation
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyPaintCodesFunctionality(String rowID,
                                                                String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String firstColor = "Alpina Blue";
        final String secondColor = "Black Forest";

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
        VehicleInfoScreenInteractions.setYear("2021");
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
        ScreenNavigationSteps.pressBackButton();

        ScreenNavigationSteps.pressBackButton();
    }
}
