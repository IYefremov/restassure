package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamVehicleInfoTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Vehicle Info Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getVehicleInfoTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyLettersIOQAreTrimmedWhileManualEntry(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String redRGBColor = "rgba(239, 83, 78, 1)";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);

        VehicleInfoScreenValidations.vinValidationMessageShouldExist(true);
        VehicleInfoScreenValidations.vinValidationColorShouldBeEqual(redRGBColor);

        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, inspectionData.getNewVinNumber());

        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyVINWithMoreThan17ValidDigitsIsTrimmedToFirst17CharactersWhileManualEntry(String rowID,
                                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String greyRGBColor = "rgba(211, 211, 211, 1)";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        VehicleInfoScreenValidations.vinValidationMessageShouldExist(true);
        VehicleInfoScreenValidations.vinValidationColorShouldBeEqual(greyRGBColor);
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.VIN, inspectionData.getVehicleInfo().getVINNumber().substring(0, inspectionData.getVehicleInfo().getVINNumber().length() - 1));
        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
