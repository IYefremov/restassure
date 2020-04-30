package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.ViewScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;


public class VNextTeamSupplementsTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Supplements Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getSupplementsTestCasesDataPath();
        HomeScreenSteps.openInspections();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.clickSearchButtonAndClear();
        ScreenNavigationSteps.pressBackButton();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddSupplementIfAllowSupplementsSetToON(String rowID,
                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.ADD_SUPPLEMENT);
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, inspectionData.getNewVinNumber());
        InspectionSteps.trySaveInspection();
        InformationDialogValidations.clickOKAndVerifyMessage(VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantCreateSupplementIfAllowSupplementsSetToOff(String rowID,
                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.ADD_SUPPLEMENT, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddSupplementWhenEditInspection(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(0));
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceNameByIndex(0));
        ServiceDetailsScreenSteps.changeServicePrice(inspectionData.getServicePriceByIndex(0));
        ServiceDetailsScreenSteps.saveServiceDetails();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(1));

        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceNameByIndex(1));
        ServiceDetailsScreenSteps.changeServicePrice(inspectionData.getServicePriceByIndex(1));
        ServiceDetailsScreenSteps.changeServiceQuantity(inspectionData.getServicePriceByIndex(1));
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.trySaveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.ADD_SUPPLEMENT, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddSupplementAfterApproveInspection(String rowID,
                                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.ADD_SUPPLEMENT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData().getServiceName());
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(inspectionData.getServiceData().getServicePrice());
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySupplementWillNOTCreatedIfUserDontChangePriceOrQuantity(String rowID,
                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData().getServiceName());
        String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.ADD_SUPPLEMENT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        InspectionSteps.trySaveInspection();
        InformationDialogValidations.clickOKAndVerifyMessage(VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.ADD_SUPPLEMENT, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddSupplementsOnlyForApprovedOrNewInspection(String rowID,
                                                                              String description, JSONObject testData) {
        final String declineReason = "Too expensive";

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceAndSetData(inspectionData.getServiceData());
        ServiceDetailsScreenSteps.saveServiceDetails();
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);

        ApproveServicesSteps.clickApproveAllButton();
        ApproveServicesSteps.saveApprovedServices();
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();

        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.SUPPLEMENT);
        MenuSteps.selectMenuItem(MenuItems.ADD_SUPPLEMENT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        for (ServiceData service : inspectionData.getServicesList())
            AvailableServicesScreenSteps.selectService(service.getServiceName());
        InspectionSteps.trySaveInspection();

        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveServicesSteps.clickDeclineAllButton();
        ApproveServicesSteps.saveApprovedServices();
        ApproveServicesSteps.selectDeclineReason(declineReason);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.DECLINED);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.ADD_SUPPLEMENT, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAmountForNewServiceInSupplement(String rowID,
                                                                              String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String serviceOriginalAmaunt = "$9.00";
        final String supplementAmaunt = "$141.00";
        final String serviceTotalAmaunt = "$150.00";

        final String serviceOriginalAmaunt2 = "$150.00";
        final String supplementAmaunt2 = "$60.00";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceAndSetData(inspectionData.getMoneyServiceData());
        ServiceDetailsScreenSteps.saveServiceDetails();
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.SUPPLEMENT);
        MenuSteps.selectMenuItem(MenuItems.ADD_SUPPLEMENT);
        /*ViewScreenValidations.verifyServiceOriginalAmaunt(inspectionData.getMoneyServiceData().getServiceName(), serviceOriginalAmaunt);
        ViewScreenValidations.verifySupplementAmaunt(supplementAmaunt);
        ViewScreenValidations.verifyServiceTotalAmaunt(inspectionData.getMoneyServiceData().getServiceName(), serviceTotalAmaunt);
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.SUPPLEMENT);
        MenuSteps.selectMenuItem(MenuItems.VIEW_SUPPLEMENT);*/
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectServiceAndSetData(inspectionData.getServiceData());
        ServiceDetailsScreenSteps.saveServiceDetails();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(inspectionData.getMoneyServiceData().getServicePrice2());
        ServiceDetailsScreenSteps.saveServiceDetails();

        InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.SUPPLEMENT);
        MenuSteps.selectMenuItem(MenuItems.VIEW_SUPPLEMENT);
        ViewScreenValidations.verifyServiceOriginalAmaunt(inspectionData.getMoneyServiceData().getServiceName(), serviceOriginalAmaunt2);
        ViewScreenValidations.verifySupplementAmaunt(supplementAmaunt2);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionPDFDoesntIncludeSupplement(String rowID,
                                                                        String description, JSONObject testData) throws Exception {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.ADD_SUPPLEMENT);
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, inspectionData.getNewVinNumber());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            AvailableServicesScreenSteps.selectServiceAndSetData(serviceData);
            ServiceDetailsScreenSteps.saveServiceDetails();
        }

        InspectionSteps.trySaveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.sendEmail(nadaEMailService.getEmailId());

        ScreenNavigationSteps.pressBackButton();

        final String inspectionReportFileName = inspectionNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionNumber, inspectionReportFileName);
        Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionNumber +
                " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nadaEMailService.deleteMessageWithSubject(inspectionNumber);

        File pdfDoc = new File(inspectionReportFileName);
        String pdfText = PDFReader.getPDFText(pdfDoc);
        Assert.assertFalse(pdfText.contains("Supplement"));
    }

}
