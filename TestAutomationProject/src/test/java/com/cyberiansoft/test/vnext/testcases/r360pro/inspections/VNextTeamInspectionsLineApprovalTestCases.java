package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.*;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class VNextTeamInspectionsLineApprovalTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Inspections Line Approval Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsLineApprovalTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanApproveServiceForInspectionIfLineApprovalEqualsON(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveServicesScreenValidations.verifyApproveAllButtonDisplayed(true);
        ApproveServicesScreenValidations.verifyDeclineAllButtonDisplayed(true);
        ApproveServicesScreenValidations.verifySkipAllButtonDisplayed(true);
        for (ServiceData service : services) {
            ApproveServicesScreenValidations.validateServiceExistsForApprove(service.getServiceName(), true);
            ApproveServicesSteps.setServiceStatus(service.getServiceName(), service.getServiceStatus());
        }
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserNeedToSelectReasonForDeclineSkipAllServicesInInspection(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String declineReason = "Too expensive";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveServicesScreenValidations.verifyApproveAllButtonDisplayed(true);
        ApproveServicesScreenValidations.verifyDeclineAllButtonDisplayed(true);
        ApproveServicesScreenValidations.verifySkipAllButtonDisplayed(true);
        for (ServiceData service : services) {
            ApproveServicesScreenValidations.validateServiceExistsForApprove(service.getServiceName(), true);
            ApproveServicesSteps.setServiceStatus(service.getServiceName(), service.getServiceStatus());
        }
        ApproveServicesSteps.clickDeclineAllButton();
        ApproveServicesSteps.saveApprovedServices();
        ApproveServicesSteps.selectDeclineReason(declineReason);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSelectSeveralInspectionWithLineApproveForApprove(String rowID,
                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<ServiceData> services = inspectionData.getServicesList();
        List<String> inspectionsNumbers = new ArrayList<>();
        HomeScreenSteps.openInspections();
        for (ServiceData service : services) {
            InspectionSteps.clickAddInspectionButton();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            AvailableServicesScreenSteps.selectService(service);
            inspectionsNumbers.add(InspectionSteps.saveInspection());
        }
        inspectionsNumbers.forEach(InspectionSteps::selectInspection);
        InspectionSteps.clickMultiSelectInspectionsApproveButton();
        inspectionsNumbers.forEach(s -> ApproveInspectionsScreenValidations.validateInspectionExists(s, true));
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantAprroveInspectionsWithDifferentCustomers(String rowID,
                                                                           String description, JSONObject testData) {
        RetailCustomer secondCustomer = new RetailCustomer();
        secondCustomer.setFirstName("Alexei");
        secondCustomer.setLastName("M");
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<ServiceData> services = inspectionData.getServicesList();
        List<String> inspectionsNumbers = new ArrayList<>();
        HomeScreenSteps.openInspections();
        for (int i = 0; i <= services.size()-1; i++) {
            InspectionSteps.clickAddInspectionButton();

            RetailCustomer customer = i == 0 ? testcustomer : secondCustomer;
            InspectionSteps.createInspection(customer, InspectionTypes.O_KRAMAR3, inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            AvailableServicesScreenSteps.selectService(services.get(i));
            inspectionsNumbers.add(InspectionSteps.saveInspection());
        }
        inspectionsNumbers.forEach(InspectionSteps::selectInspection);
        InspectionSteps.clickMultiSelectInspectionsApproveButton();
        SelectCustomerScreenSteps.selectCustomer(secondCustomer);
        inspectionsNumbers.remove(0);
        inspectionsNumbers.forEach(s -> ApproveInspectionsScreenValidations.validateInspectionExists(s, true));
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanApproveDeclineAndSkipDifferentServicesInOneInspection(String rowID,
                                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        for (ServiceData service : services) {
            if (service.getServiceStatus() != null)
                ApproveServicesSteps.setServiceStatus(service.getServiceName(), service.getServiceStatus());
        }
        ApproveServicesSteps.saveApprovedServices();
        ApproveValidations.verifyApprovePriceValue(inspectionData.getInspectionApprovedPrice());
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        InspectionsValidations.verifyInspectionApprovedPrice(inspectionNumber, inspectionData.getInspectionApprovedPrice());

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeclineAllServiceIfLineApprovalEqualsON(String rowID,
                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveServicesSteps.clickDeclineAllButton();
        ApproveServicesSteps.saveApprovedServices();
        ApproveInspectionsSteps.selectDeclineReason(inspectionData.getDeclineReason());

        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.DECLINED);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantApproveOrDeclineServicesIfLineApprovalEqualsOFF(String rowID,
                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);

        ApproveValidations.verifyApprovePriceValue(inspectionData.getInspectionApprovedPrice());
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
        InspectionsValidations.verifyInspectionApprovedPrice(inspectionNumber, inspectionData.getInspectionApprovedPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyStateChangedToNewIfUserEditInspection(String rowID,
                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);

        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionHaveStateApproveIfUserSelectOnlyOneServiceAsApproved(String rowID,
                                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveServicesSteps.clickDeclineAllButton();
        ApproveServicesSteps.setServiceStatus(inspectionData.getServicesList().get(0).getServiceName(), inspectionData.getServicesList().get(0).getServiceStatus());
        ApproveServicesSteps.saveApprovedServices();

        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalAmountIncludeOnlyApprovedServices(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        List<ServiceData> services = inspectionData.getServicesList();
        services.forEach(AvailableServicesScreenSteps::selectService);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        for (ServiceData service : services) {
            ApproveServicesSteps.setServiceStatus(service.getServiceName(), service.getServiceStatus());
        }

        ApproveServicesSteps.saveApprovedServices();
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();

        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        InspectionsValidations.verifyInspectionApprovedPrice(inspectionNumber, inspectionData.getInspectionApprovedPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeOnlyTotalAmountForSelectedMatrixWhenApproveInspectionWithLineApprovalEqualsON(String rowID,
                                                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.switchToAvailableServices();

        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        List<VehiclePartData> vehiclePartsData = matrixServiceData.getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : vehiclePartsData) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
                List<ServiceData> additionalServices = vehiclePartData.getVehiclePartAdditionalServices();
                for (ServiceData additionalService : additionalServices) {
                    if (additionalService.getServicePrice() != null) {
                        vehiclePartInfoScreen.openServiceDetailsScreen(additionalService.getServiceName());
                        ServiceDetailsScreenSteps.changeServicePrice(additionalService.getServicePrice());
                        ServiceDetailsScreenSteps.changeServiceQuantity(additionalService.getServiceQuantity());
                        ServiceDetailsScreenSteps.saveServiceDetails();
                    } else
                        vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
                }

            }
            vehiclePartInfoScreen.clickScreenBackButton();
        }
        vehiclePartsScreen.clickVehiclePartsSaveButton();

        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveServicesScreenValidations.validateServiceExistsForApprove(inspectionData.getMatrixServiceData().getMatrixServiceName(), true);
        ApproveServicesScreenValidations.validateServicePrice(inspectionData.getMatrixServiceData().getMatrixServiceName(),
                inspectionData.getInspectionPrice());
        ApproveServicesSteps.clickApproveAllButton();
        ApproveServicesSteps.saveApprovedServices();
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();

        InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanApproveSeveralInspectionsWithOneCustomersWithLineApproveAndWithoutLineApprove(String rowID,
                                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

        HomeScreenSteps.openInspections();
        for (InspectionData inspectionData : inspectionsData) {
            InspectionSteps.switchToMyInspections();
            InspectionSteps.clickAddInspectionButton();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.valueOf(inspectionData.getInspectionType()), inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            inspectionData.getServicesList().forEach(AvailableServicesScreenSteps::selectService);
            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
        }
        inspectionsData.forEach(inspectionData -> InspectionSteps.selectInspection(inspectionData.getInspectionNumber()));
        InspectionSteps.clickMultiSelectInspectionsApproveButton();
        inspectionsData.forEach(inspectionData ->
                ApproveInspectionsScreenValidations.validateInspectionExists(inspectionData.getInspectionNumber(), true));

        ApproveInspectionsScreenValidations.validateApproveIconForInspectionSelected(inspectionsData.get(0).getInspectionNumber(), true);
        ApproveInspectionsScreenValidations.validateInspectionServicesStatusesCanBeChanged(inspectionsData.get(0).getInspectionNumber(), false);
        ApproveInspectionsScreenValidations.validateApproveIconForInspectionSelected(inspectionsData.get(1).getInspectionNumber(), false);
        ApproveInspectionsScreenValidations.validateInspectionServicesStatusesCanBeChanged(inspectionsData.get(1).getInspectionNumber(), true);
        ScreenNavigationSteps.pressBackButton();
        inspectionsData.forEach(inspectionData -> InspectionSteps.unSelectInspection(inspectionData.getInspectionNumber()));
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSelectServiceForApproveOnMultiLineApproveScreenIfLineApprovalEqualsONForThisInspection(String rowID,
                                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

        HomeScreenSteps.openInspections();
        for (InspectionData inspectionData : inspectionsData) {
            InspectionSteps.switchToMyInspections();
            InspectionSteps.clickAddInspectionButton();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            inspectionData.getServicesList().forEach(AvailableServicesScreenSteps::selectService);
            SelectedServicesScreenSteps.switchToSelectedService();
            inspectionData.getServicesList().forEach(service -> {
                SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
                ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
                ServiceDetailsScreenSteps.saveServiceDetails();
            });

            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
        }
        inspectionsData.forEach(inspectionData -> InspectionSteps.selectInspection(inspectionData.getInspectionNumber()));
        InspectionSteps.clickMultiSelectInspectionsApproveButton();

        for (InspectionData inspectionData : inspectionsData) {
            ApproveInspectionsScreenValidations.validateInspectionExists(inspectionData.getInspectionNumber(), true);
            ApproveInspectionsSteps.openApproveServicesScreen(inspectionData.getInspectionNumber());
            List<ServiceData> services = inspectionData.getServicesList();
            services.forEach(service -> {
                if (service.getServiceStatus() != null)
                    ApproveServicesScreenValidations.validateServiceExistsForApprove(service.getServiceName(), true);
            });
            ScreenNavigationSteps.pressBackButton();
        }
        ScreenNavigationSteps.pressBackButton();
        inspectionsData.forEach(inspectionData -> InspectionSteps.unSelectInspection(inspectionData.getInspectionNumber()));
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeleteInspectionFromLineApproveList(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

        HomeScreenSteps.openInspections();
        for (InspectionData inspectionData : inspectionsData) {
            InspectionSteps.switchToMyInspections();
            InspectionSteps.clickAddInspectionButton();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

            inspectionData.getServicesList().forEach(AvailableServicesScreenSteps::selectService);
            SelectedServicesScreenSteps.switchToSelectedService();
            inspectionData.getServicesList().forEach(service -> {
                SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
                ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
                ServiceDetailsScreenSteps.saveServiceDetails();
            });
            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
        }
        inspectionsData.forEach(inspectionData -> InspectionSteps.selectInspection(inspectionData.getInspectionNumber()));
        InspectionSteps.clickMultiSelectInspectionsApproveButton();
        ApproveInspectionsSteps.deleteInspection(inspectionsData.get(1).getInspectionNumber());
        ApproveInspectionsScreenValidations.validateInspectionExists(inspectionsData.get(1).getInspectionNumber(), false);
        ApproveInspectionsScreenValidations.validateInspectionExists(inspectionsData.get(0).getInspectionNumber(), true);
        ApproveInspectionsSteps.deleteInspection(inspectionsData.get(0).getInspectionNumber());
        ApproveInspectionsScreenValidations.validateInspectionExists(inspectionsData.get(1).getInspectionNumber(), false);
        ApproveInspectionsScreenValidations.validateInspectionExists(inspectionsData.get(0).getInspectionNumber(), true);

        ScreenNavigationSteps.pressBackButton();
        for (InspectionData inspectionData : inspectionsData)
            InspectionSteps.unSelectInspection(inspectionData.getInspectionNumber());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalApprovalAmountIncludeOnlySUMOfApprovedService(String rowID,
                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();
        final String approveTotal = "$111.15";

        HomeScreenSteps.openInspections();
        for (InspectionData inspectionData : inspectionsData) {
            InspectionSteps.switchToMyInspections();
            InspectionSteps.clickAddInspectionButton();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            inspectionData.getServicesList().forEach(AvailableServicesScreenSteps::selectService);

            SelectedServicesScreenSteps.switchToSelectedService();
            inspectionData.getServicesList().forEach(service -> {
                if (service.getServicePrice() != null) {
                    SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
                    ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
                    ServiceDetailsScreenSteps.saveServiceDetails();
                }
            });
            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
        }
        inspectionsData.forEach(inspectionData -> InspectionSteps.selectInspection(inspectionData.getInspectionNumber()));
        InspectionSteps.clickMultiSelectInspectionsApproveButton();
        for (InspectionData inspectionData : inspectionsData) {
            ApproveInspectionsScreenValidations.validateInspectionExists(inspectionData.getInspectionNumber(), true);
            ApproveInspectionsSteps.openApproveServicesScreen(inspectionData.getInspectionNumber());
            List<ServiceData> services = inspectionData.getServicesList();

            for (ServiceData service : services) {
                if (service.getServiceStatus() != null)
                    ApproveServicesSteps.setServiceStatus(service.getServiceName(), service.getServiceStatus());
            }
            ApproveServicesSteps.saveApprovedServices();
            ApproveInspectionsScreenValidations.validateInspectionApprovedAmount(inspectionData.getInspectionNumber(), inspectionData.getInspectionApprovedPrice());
        }
        ApproveInspectionsSteps.saveApprove();
        ApproveValidations.verifyApprovePriceValue(approveTotal);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserNeedToSelectReasonForAllDeclinedInspection(String rowID,
                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

        HomeScreenSteps.openInspections();

        for (InspectionData inspectionData : inspectionsData) {
            InspectionSteps.switchToMyInspections();
            InspectionSteps.clickAddInspectionButton();
            Instant begin = Instant.now();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
            System.out.println("++++a: " + Duration.between(begin, Instant.now()).toMillis()/1000);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            inspectionData.getServicesList().forEach(AvailableServicesScreenSteps::selectService);
            SelectedServicesScreenSteps.switchToSelectedService();
            inspectionData.getServicesList().forEach(service -> {
                if (service.getServicePrice() != null) {
                    SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
                    ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
                    ServiceDetailsScreenSteps.saveServiceDetails();
                }
            });
            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
        }

        inspectionsData.forEach(inspectionData -> InspectionSteps.selectInspection(inspectionData.getInspectionNumber()));
        InspectionSteps.clickMultiSelectInspectionsApproveButton();
        for (InspectionData inspectionData : inspectionsData) {
            if (inspectionData.getInspectionStatus().equals(InspectionStatus.DECLINED.getStatusString())) {
                ApproveInspectionsSteps.declineInspection(inspectionData.getInspectionNumber(), inspectionData.getDeclineReason());
            }
        }
        ApproveInspectionsSteps.saveApprove();
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        inspectionsData.forEach(inspectionData -> InspectionsValidations.verifyInspectionStatus(inspectionData.getInspectionNumber(), InspectionStatus.DECLINED));
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionStatusUpdatedAfterApproveDecline(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

        HomeScreenSteps.openInspections();
        for (InspectionData inspectionData : inspectionsData) {
            InspectionSteps.switchToMyInspections();
            InspectionSteps.clickAddInspectionButton();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            inspectionData.getServicesList().forEach(AvailableServicesScreenSteps::selectService);

            SelectedServicesScreenSteps.switchToSelectedService();
            inspectionData.getServicesList().forEach(service -> {
                if (service.getServicePrice() != null) {
                    SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
                    ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
                    ServiceDetailsScreenSteps.saveServiceDetails();
                }
            });
            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
        }
        inspectionsData.forEach(inspectionData -> InspectionSteps.selectInspection(inspectionData.getInspectionNumber()));
        InspectionSteps.clickMultiSelectInspectionsApproveButton();

        for (InspectionData inspectionData : inspectionsData) {
            ApproveInspectionsSteps.openApproveServicesScreen(inspectionData.getInspectionNumber());
            List<ServiceData> services = inspectionData.getServicesList();

            for (ServiceData service : services) {
                if (service.getServiceStatus() != null)
                    ApproveServicesSteps.setServiceStatus(service.getServiceName(), service.getServiceStatus());
            }
            ApproveServicesSteps.saveApprovedServices();
            if (inspectionData.getDeclineReason() != null) {
                ApproveInspectionsSteps.selectDeclineReason(inspectionData.getDeclineReason());
            }
            if (!inspectionData.getInspectionStatus().equals(InspectionStatus.DECLINED.getStatusString()))
                ApproveInspectionsScreenValidations.validateInspectionApprovedAmount(inspectionData.getInspectionNumber(), inspectionData.getInspectionApprovedPrice());
        }
        ApproveInspectionsSteps.saveApprove();
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        ScreenNavigationSteps.pressBackButton();
    }

}
