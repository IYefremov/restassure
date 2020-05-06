package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamDraftInspectionsTestCases extends BaseTestClass {

    @BeforeClass(description="Team Draft Inspections Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getDraftInspectionsTestCasesDataPath();

    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInspectionWithoutPopulateRequiredFields(String rowID,
                                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());

        final String inspectionNumber = InspectionSteps.saveInspectionAsDraft();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.DRAFT);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveInspectionIfDraftModeEqualsOFF(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditDraftInspection(String rowID,
                                             String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspectionAsDraft();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.DRAFT);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        InspectionSteps.saveInspectionAsDraft();

        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.DRAFT);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantEditInspectionInStateFinal(String rowID,
                                                     String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.EDIT, false);
        MenuSteps.closeMenu();

        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditInspectionIfDraftModeEqualsOFF(String rowID,
                                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveFinalInspectionWithoutPopulateRequiredField(String rowID,
                                                     String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        CustomersScreenSteps.selectCustomer(testcustomer);
        InspectionSteps.selectInspectionType(InspectionTypes.O_KRAMAR3);
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        InspectionSteps.trySaveInspection();
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clcikSaveViaMenuAsFinal();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.VIN_REQUIRED_MSG);
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        final  String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }
}
