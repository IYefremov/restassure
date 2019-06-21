package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.VisualScreenSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInspectionsServicesTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description="Team Inspections Services Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsServicesTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddSeveralServicesFromAllListOnVisualsScreen(String rowID,
                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);


        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToVisualScreen();

        for (DamageData damageData : inspectionData.getDamagesData()) {
            VisualScreenSteps.addDamage(damageData);
        }
        VisualScreenSteps.verifyNumberOfAddedDamages(inspectionData.getDamagesData().size());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToVisualScreen();
        VisualScreenSteps.verifyNumberOfAddedDamages(inspectionData.getDamagesData().size());
        InspectionSteps.cancelInspection();
        InspectionSteps.navigateHomeScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddSeveralServicesOnVisualsScreenSwitchingBetweenDefaultAndAllMode(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToVisualScreen();

        for (DamageData damageData : inspectionData.getDamagesData()) {
            VisualScreenSteps.addDamage(damageData);
        }
        VisualScreenSteps.verifyNumberOfAddedDamages(inspectionData.getDamagesData().size());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToVisualScreen();
        VisualScreenSteps.verifyNumberOfAddedDamages(inspectionData.getDamagesData().size());
        InspectionSteps.cancelInspection();
        InspectionSteps.navigateHomeScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServiceOnVisualScreenAfterServiceWasAdded(String rowID,
                                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToVisualScreen();

        VisualScreenSteps.addDamage(inspectionData.getDamageData());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToVisualScreen();
        VisualScreenSteps.editVisualDamage(
                String.valueOf(BackOfficeUtils.getServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice())));
        VisualScreenSteps.verifyInspectionTotalPrice(inspectionData.getDamageData().getMoneyService().getServicePrice());

        InspectionSteps.saveInspection();
        InspectionSteps.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getDamageData().getMoneyService().getServicePrice());
        InspectionSteps.navigateHomeScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServiceOnVisualScreenWhenAddingService(String rowID,
                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToVisualScreen();

        VisualScreenSteps.addDamage(inspectionData.getDamageData());
        VisualScreenSteps.editVisualDamage(
                String.valueOf(BackOfficeUtils.getServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice())));
        VisualScreenSteps.verifyInspectionTotalPrice(inspectionData.getDamageData().getMoneyService().getServicePrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getDamageData().getMoneyService().getServicePrice());

        InspectionSteps.openInspectionToEdit(inspectionNumber);
        WizardScreenSteps.navigateToVisualScreen();
        VisualScreenSteps.removeAllBreakages();

        VisualScreenSteps.verifyInspectionTotalPrice(inspectionData.getInspectionPrice());

        InspectionSteps.cancelInspection();
        InspectionSteps.navigateHomeScreen();
    }
}
