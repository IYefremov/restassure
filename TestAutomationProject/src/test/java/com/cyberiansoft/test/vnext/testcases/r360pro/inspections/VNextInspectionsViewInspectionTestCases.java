package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.VehiclePartsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.ViewScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextInspectionsViewInspectionTestCases extends BaseTestClass {

    @BeforeClass(description = "View Inspection Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getViewInspectionTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectDateTimeInfoIsShownOnPrinting(String rowID,
                                                               String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyEstimationDataFieldFormat();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectCustomerInfoIsShownOnPrinting(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RetailCustomer retailCustomer = new RetailCustomer("PrintingCustomer", "PrintingCustomerLast");
        retailCustomer.setCompanyName("Printing Company");
        retailCustomer.setCustomerAddress1("Address 1");
        retailCustomer.setCustomerAddress2("Address 2");
        retailCustomer.setMailAddress("test.printing@cyberiansoft.com");
        retailCustomer.setCustomerCity("New York");
        retailCustomer.setCustomerPhone("+1234567890");
        retailCustomer.setCustomerCountry("United States of America");
        retailCustomer.setCustomerState("Alaska");
        retailCustomer.setCustomerZip("12345");

        final String stateAbbreviation = "AK";
        final String countryAbbreviation = "US";

        HomeScreenSteps.openCreateMyInspection();
        CustomersScreenSteps.createNewRetailCustomer(retailCustomer);
        InspectionSteps.selectInspectionType(InspectionTypes.O_KRAMAR);
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);

        retailCustomer.setCustomerCountry(countryAbbreviation);
        retailCustomer.setCustomerState(stateAbbreviation);
        ViewScreenValidations.verifyEstimationCustomerValues(retailCustomer);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyBackButtonDoesntSaveInfoForVehiclePartPrinting(String rowID,
                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            VehiclePartsScreenSteps.selectVehiclePart(vehiclePartData.getVehiclePartName());
            ScreenNavigationSteps.pressBackButton();
        }
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyServiceIsPresent(inspectionData.getMatrixServiceData().getMatrixServiceName(), false);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInformationIsUpdatedOnViewScreenIfUserEditInspection(String rowID,
                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            VehiclePartsScreenSteps.selectVehiclePart(vehiclePartData.getVehiclePartName());
            ScreenNavigationSteps.pressBackButton();
        }
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyServiceIsPresent(inspectionData.getMatrixServiceData().getMatrixServiceName(), false);
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            VehiclePartsScreenSteps.selectVehiclePartData(vehiclePartData);
        }
        VehiclePartsScreenSteps.saveVehicleParts();
        InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyServiceIsPresent(inspectionData.getMatrixServiceData().getMatrixServiceName(), true);
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            ViewScreenValidations.verifyVehiclePartIsPresent(inspectionData.getMatrixServiceData().getMatrixServiceName(),
                    vehiclePartData, true);
        }
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyNotesForAdditionalServicesInPriceMatrixIsShownOnMobile(String rowID,
                                                                               String description, JSONObject testData) {

        final String notesText = "Test notes";
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            VehiclePartsScreenSteps.selectVehiclePart(vehiclePartData.getVehiclePartName());
            VehiclePartsScreenSteps.selectVehiclePartSizeAndSeverity(vehiclePartData.getVehiclePartSize(),
                    vehiclePartData.getVehiclePartSeverity());
            VehiclePartsScreenSteps.openAdditionalServiceDetails(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
            ServiceDetailsScreenSteps.setServiceTextNotes(notesText);
            ServiceDetailsScreenSteps.closeServiceDetailsScreen();

            ScreenNavigationSteps.pressBackButton();
        }
        VehiclePartsScreenSteps.saveVehicleParts();
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        ViewScreenValidations.verifyServiceIsPresent(inspectionData.getMatrixServiceData().getMatrixServiceName(), true);
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData())
            ViewScreenValidations.verifyMatrixServiceHasNotes(vehiclePartData.getVehiclePartAdditionalService().getServiceName(), notesText);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

}
