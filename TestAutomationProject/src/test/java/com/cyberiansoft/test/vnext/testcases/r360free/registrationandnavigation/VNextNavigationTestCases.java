package com.cyberiansoft.test.vnext.testcases.r360free.registrationandnavigation;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextViewScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
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

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanNavigateByActionScreenInInspectionsForAndroid(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (ServiceData service : inspectionData.getServicesList())
            availableServicesScreen.selectService(service.getServiceName());
        availableServicesScreen.clickScreenBackButton();
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.clickScreenForwardButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));

        WizardScreenSteps.navigateToWizardScreen(ScreenType.VEHICLE_INFO);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();

        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickMenuButton();
        ScreenNavigationSteps.pressBackButton();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        baseWizardScreen.clickScreenTitleCaption();
        ScreenNavigationSteps.pressBackButton();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionExists(inspectionNumber, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyActionScreenWorksCorrectly(String rowID,
                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        
        final String userMail = "anastasiia.naumenko@cyberiansoft.com";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);

        InspectionSteps.openInspectionMenu(inspNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        viewScreen.waitViewScreenLoaded();
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.openInspectionToEdit(inspNumber);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        final String VIN = VehicleInfoScreenInteractions.getDataFieldValue(VehicleDataField.VIN);
        VehicleInfoScreenInteractions.selectColor(inspectionData.getVehicleInfo().getVehicleColor());
        VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.VIN, VIN);
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.CANCEL_EDITING_INSPECTION_ALERT);
        vehicleInfoScreen.clickScreenForwardButton();
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.clickScreenForwardButton();
        SelectedServicesScreenSteps.switchToSelectedService();
        InspectionSteps.openInspectionMenu(inspNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        if (!emailScreen.getToEmailFieldValue().equals(userMail))
            emailScreen.sentToEmailAddress(userMail);

        emailScreen.clickSendEmailsButton();
        msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGE_HAS_BEEEN_ADDDED_TO_THE_QUEUE);
        Assert.assertTrue(inspectionsScreen.isEmailSentIconPresentForInspection(inspNumber));
        inspectionsScreen.clickBackButton();
    }

}