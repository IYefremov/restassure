package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.FileAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    @BeforeClass(description = "R360 Inspections Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInspectionsTestCasesDataPath();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyVINIsDecodedCorrectlyForInspection(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        BaseUtils.waitABit(2000);
        VehicleInfoData expectedVehicleData = inspectionData.getVehicleInfo();
        VehicleInfoScreenValidations.validateVehicleInfo(expectedVehicleData);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveInspectionUsingOptionFromHamburgerMenu_GeneralCase(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectServices(inspectionData.getServicesList());
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep(String rowID,
                                                                                                        String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickAddInspectionButton();
        CustomersSreenSteps.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
        InspectionSteps.trySaveInspection();

        informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigateBySwipeThroughWizardStepsForInspeciton(String rowID,
                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionExists(inspectionNumber, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigateThroughWizardStepsForInspectionByHardwareBackButton(String rowID,
                                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String insuranceCompany = "Test Insurance Company";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(insuranceCompany);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        ScreenNavigationSteps.pressBackButton();
        new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        ScreenNavigationSteps.pressBackButton();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VEHICLE_INFO);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(3000);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.ARE_YOU_SURE_STOP_CREATING_INSPECTION);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionExists(inspectionNumber, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditInspectionVehicleInfo(String rowID,
                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateCustomerAlongWithInspection(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        RetailCustomer inspCustomer = inspectionData.getInspectionRetailCustomer();

        HomeScreenSteps.openCreateMyInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickAddInspectionButton();
        CustomersSreenSteps.clickAddCustomerButton();
        VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
        newCustomerScreen.setCustomerFirstName(inspCustomer.getFirstName());
        newCustomerScreen.setCustomerLastName(inspCustomer.getLastName());
        newCustomerScreen.setCustomerEmail(inspCustomer.getMailAddress());
        newCustomerScreen.setCustomerAddress(inspCustomer.getCustomerAddress1());
        newCustomerScreen.setCustomerCity(inspCustomer.getCustomerCity());
        newCustomerScreen.setCustomerZIP(inspCustomer.getCustomerZip());
        newCustomerScreen.clickSaveCustomerButton();
        BaseUtils.waitABit(7000);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.cutomerContextShouldBe(inspCustomer.getFullName());
        final String inspnum = vehicleInfoScreen.getNewInspectionNumber();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VEHICLE_INFO);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspnum);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspnum), inspCustomer.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenSteps.openCustomers();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.openCustomerForEdit(inspCustomer);
        Assert.assertEquals(newCustomerScreen.getCustomerFirstName(), inspCustomer.getFirstName());
        Assert.assertEquals(newCustomerScreen.getCustomerLastName(), inspCustomer.getLastName());
        Assert.assertEquals(newCustomerScreen.getCustomerAddress(), inspCustomer.getCustomerAddress1());
        Assert.assertEquals(newCustomerScreen.getCustomerCity(), inspCustomer.getCustomerCity());
        Assert.assertEquals(newCustomerScreen.getCustomerZIP(), inspCustomer.getCustomerZip());
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanArchiveCreatedInspection(String rowID,
                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);

        InspectionSteps.archiveInspection(inspectionNumber);
        Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionNumber), "Inspection: " + inspectionNumber +
                " still exists, but shouldn't");
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanArchiveSeveralInspections(String rowID,
                                                           String description, JSONObject testData) {

        final int inspToArchive = 3;

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        int inspectionNumbers = inspectionsScreen.getNumberOfInspectionsInList();

        for (int i = 0; i < inspToArchive; i++) {
            String inspectionNumber = inspectionsScreen.getFirstInspectionNumber();
            InspectionSteps.archiveInspection(inspectionNumber);
            Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionNumber));
        }
        Assert.assertEquals(inspectionsScreen.getNumberOfInspectionsInList(), inspectionNumbers - inspToArchive);
        ScreenNavigationSteps.pressBackButton();

    }

}
