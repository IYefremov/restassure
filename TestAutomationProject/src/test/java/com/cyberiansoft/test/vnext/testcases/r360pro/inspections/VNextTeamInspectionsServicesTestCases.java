package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.VisualScreenValidations;
import com.cyberiansoft.test.vnext.validations.WizardScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInspectionsServicesTestCases extends BaseTestClass {

    @BeforeClass(description="Team Inspections Services Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsServicesTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddSeveralServicesFromAllListOnVisualsScreen(String rowID,
                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        inspectionData.getDamagesData().forEach(VisualScreenSteps::addDefaultDamage);
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(inspectionData.getDamagesData().size());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(inspectionData.getDamagesData().size());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddSeveralServicesOnVisualsScreenSwitchingBetweenDefaultAndAllMode(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        for (DamageData damageData : inspectionData.getDamagesData()) {
            VisualScreenSteps.addDamage(damageData);
        }
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(inspectionData.getDamagesData().size());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(inspectionData.getDamagesData().size());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServiceOnVisualScreenAfterServiceWasAdded(String rowID,
                                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.addDamage(inspectionData.getDamageData());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.editVisualDamage(
                String.valueOf(BackOfficeUtils.getServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice())));
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice());

        InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getDamageData().getMoneyService().getServicePrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServiceOnVisualScreenWhenAddingService(String rowID,
                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.addDamage(inspectionData.getDamageData());
        VisualScreenSteps.editVisualDamage(
                String.valueOf(BackOfficeUtils.getServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice())));
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getDamageData().getMoneyService().getServicePrice());

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.removeAllBreakages();

        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
