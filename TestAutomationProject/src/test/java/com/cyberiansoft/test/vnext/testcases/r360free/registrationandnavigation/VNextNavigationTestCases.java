package com.cyberiansoft.test.vnext.testcases.r360free.registrationandnavigation;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextViewScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextNavigationTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    @BeforeClass(description = "R360 Navigation Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getNavigationTestCasesDataPath();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNavigatingByActionScreenForAndroid(String rowID,
                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
        AppiumUtils.clickHardwareBackButton();
        new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        AppiumUtils.clickHardwareBackButton();
        new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanNavigateByActionScreenInInspectionsForAndroid(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (ServiceData service : inspectionData.getServicesList())
            inspservicesscreen.selectService(service.getServiceName());
        inspservicesscreen.clickScreenBackButton();
        VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        ;
        claiminfoscreen.clickScreenForwardButton();
        inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));

        inspservicesscreen.changeScreen(ScreenType.VEHICLE_INFO);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();

        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.clickMenuButton();
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.clickScreenTitleCaption();
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claiminfoscreen.changeScreen(ScreenType.SERVICES);
        inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));

        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertTrue(inspectionsscreen.isInspectionExists(inspNumber));
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyActionScreenWorksCorrectly(String rowID,
                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String notesText = "Test Notes";
        final String userMail = "anastasiia.naumenko@cyberiansoft.com";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);

        VNextInspectionsMenuScreen inspmenuscreen = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
        VNextViewScreen viewscreen = inspmenuscreen.clickViewInspectionMenuItem();
        viewscreen.clickScreenBackButton();
        inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextVehicleInfoScreen vehicleInfoScreen = inspectionsscreen.clickOpenInspectionToEdit(inspNumber);
        final String VIN = VehicleInfoScreenInteractions.getDataFieldValue(VehicleDataField.VIN);
        VehicleInfoScreenInteractions.selectColor(inspectionData.getVehicleInfo().getVehicleColor());
        VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.VIN, VIN);
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.CANCEL_EDITING_INSPECTION_ALERT);
        vehicleInfoScreen.clickScreenForwardButton();
        VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claiminfoscreen.clickScreenForwardButton();

        VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextEmailScreen emailscreen = inspectionsscreen.clickOnInspectionToEmail(inspNumber);
        if (!emailscreen.getToEmailFieldValue().equals(userMail))
            emailscreen.sentToEmailAddress(userMail);

        emailscreen.clickSendEmailsButton();
        informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGE_HAS_BEEEN_ADDDED_TO_THE_QUEUE);
        inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(inspectionsscreen.isEmailSentIconPresentForInspection(inspNumber));
        inspectionsscreen.clickBackButton();
    }

}