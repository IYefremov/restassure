package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
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

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        BaseUtils.waitABit(2000);
        VehicleInfoData expectedVehicleData = inspectionData.getVehicleInfo();
        VehicleInfoScreenValidations.validateVehicleInfo(expectedVehicleData);
        inspectionsScreen = vehicleInfoScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveInspectionUsingOptionFromHamburgerMenu_GeneralCase(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectServices(inspectionData.getServicesList());
        availableServicesScreen.clickSaveInspectionMenuButton();
        inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep(String rowID,
                                                                                                        String description, JSONObject testData) {

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
        vehicleInfoScreen.clickSaveInspectionMenuButton();

        informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();

        vehicleInfoScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigateBySwipeThroughWizardStepsForInspeciton(String rowID,
                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.clickSaveButton();
        inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspectionNumber);
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigateThroughWizardStepsForInspectionByHardwareBackButton(String rowID,
                                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String insuranceCompany = "Test Insurance Company";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(insuranceCompany);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        ScreenNavigationSteps.pressBackButton();
        new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        ScreenNavigationSteps.pressBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        availableServicesScreen.changeScreen(ScreenType.VEHICLE_INFO);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(3000);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.ARE_YOU_SURE_STOP_CREATING_INSPECTION);
        vehicleInfoScreen.clickSaveInspectionMenuButton();

        inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspectionNumber);
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditInspectionVehicleInfo(String rowID,
                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String VIN = "1D7HW48NX6S507810";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, VIN);
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        inspectionsScreen = claimInfoScreen.saveInspectionViaMenu();
        vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();

        vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);

        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());

        inspectionsScreen = vehicleInfoScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateCustomerAlongWithInspection(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        RetailCustomer inspCustomer = inspectionData.getInspectionRetailCustomer();

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
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
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.changeScreen(ScreenType.VEHICLE_INFO);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspnum);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspnum), inspCustomer.getFullName());
        homeScreen = inspectionsScreen.clickBackButton();
        customersScreen = homeScreen.clickCustomersMenuItem();
        newCustomerScreen = customersScreen.openCustomerForEdit(inspCustomer);
        Assert.assertEquals(newCustomerScreen.getCustomerFirstName(), inspCustomer.getFirstName());
        Assert.assertEquals(newCustomerScreen.getCustomerLastName(), inspCustomer.getLastName());
        Assert.assertEquals(newCustomerScreen.getCustomerAddress(), inspCustomer.getCustomerAddress1());
        Assert.assertEquals(newCustomerScreen.getCustomerCity(), inspCustomer.getCustomerCity());
        Assert.assertEquals(newCustomerScreen.getCustomerZIP(), inspCustomer.getCustomerZip());
        customersScreen = newCustomerScreen.clickBackButton();
        customersScreen.clickBackButton();
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
        inspectionsScreen.clickBackButton();

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

        inspectionsScreen.clickBackButton();

    }

}
