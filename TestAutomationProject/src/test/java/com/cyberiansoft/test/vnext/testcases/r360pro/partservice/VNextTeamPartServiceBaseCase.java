package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.*;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamPartServiceBaseCase extends BaseTestClass {
    private String inspectionId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceBasicCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanChangePartServiceValuesAndSaveThem(String rowID,
                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceDataList();
        PartServiceData basicPartService = partServiceData.get(0);
        PartServiceData editedPartService = partServiceData.get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartService.getServiceName());
        PartServiceSteps.selectPartService(basicPartService);
        PartServiceSteps.confirmPartInfo();
        SelectedServicesScreenSteps.openServiceDetails("PREF:  " + basicPartService.getPartName().getPartNameList().get(0));
        ServiceDetailsScreenSteps.changeServicePrice(editedPartService.getServicePrice());
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.changeCategory(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        //SearchSteps.textSearch(inspectionId);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails("PREF:  Engine Oil Filter Kit");
        ServiceDetailsValidations.verifyServicePrice(editedPartService.getServicePrice());
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.validatePartInfo(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanEditPartService(String rowID,
                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceDataList();
        PartServiceData basicPartService = partServiceData.get(0);
        PartServiceData editedPartService = partServiceData.get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartService.getServiceName());
        PartServiceSteps.selectPartService(basicPartService);
        PartServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        //SearchSteps.searchByText(inspectionId);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails("PREF:  " + basicPartService.getPartName().getPartNameList().get(0));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.changeCategory(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails("PREF:  Parking Brake Cable Lever");
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.validatePartInfo(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddPartServiceWithoutSelectedPartName(String rowID,
                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceDataList();
        PartServiceData basicPartService = partServiceData.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartService.getServiceName());
        AvailableServicesScreenSteps.openServiceDetails(basicPartService.getServiceName());
        PartServiceSteps.selectCategory(basicPartService.getCategory());
        PartServiceSteps.selectSubCategory(basicPartService.getSubCategory());
        PartServiceSteps.acceptDetailsScreen();
        GeneralValidations.errorDialogShouldBePresent(true, "Please select at least one Part");
        GeneralSteps.closeErrorDialog();
        PartServiceSteps.selectPartName(basicPartService.getPartName());
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantEditPreselectedValues(String rowID,
                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceDataList();
        PartServiceData basicPartService = partServiceData.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartService.getServiceName());
        AvailableServicesScreenSteps.openServiceDetails(basicPartService.getServiceName());
        PartServiceSteps.selectPartPosition(basicPartService.getPartPosition());
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.CATEGORY);
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.SUB_CATEGORY);
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.PART_NAME);
        PartInfoScreenValidations.fieldShouldBeReadonly(false, PartInfoScreenField.PART_POSITION);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultipleLaborServicesToPartServiceForServicesWizardStep(String rowID,
                                                                                            String description, JSONObject testData) {

        final String partValue = "Assortments > Brake Fitting Assortment > N/A";
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceDataList();
        PartServiceData basicPartService = partServiceData.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartService.getServiceName());
        AvailableServicesScreenSteps.openServiceDetails(basicPartService.getServiceName());
        PartServiceSteps.selectPartPosition(basicPartService.getPartPosition());
        PartServiceSteps.confirmPartInfo();
        PartServiceSteps.addLaborService();
        basicPartService.getLaborServiceDataList().stream().map(LaborServiceData::getServiceName).forEach(LaborServiceSteps::selectService);
        ScreenNavigationSteps.pressBackButton();
        PartServiceSteps.confirmPartInfo();
        MatrixServiceSteps.switchToSelectedServices();
        basicPartService.getLaborServiceDataList().stream().map(LaborServiceData::getServiceName).forEach(MatrixServiceDetailsValidations::validateServiceSelected);
        basicPartService.getLaborServiceDataList().forEach((service) -> BundleServiceValidations.validateServicesHasPartsValues(service.getServiceName(), partValue));
        MatrixServiceDetailsValidations.validateServiceSelected(basicPartService.getServiceName());
        BundleServiceValidations.validateServicesHasPartsValues(basicPartService.getServiceName(), partValue);

        final String inspectionID = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionID);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        MatrixServiceSteps.switchToSelectedServices();
        basicPartService.getLaborServiceDataList().stream().map(LaborServiceData::getServiceName).forEach(MatrixServiceDetailsValidations::validateServiceSelected);
        basicPartService.getLaborServiceDataList().forEach((service) -> BundleServiceValidations.validateServicesHasPartsValues(service.getServiceName(), partValue));
        MatrixServiceDetailsValidations.validateServiceSelected(basicPartService.getServiceName());
        BundleServiceValidations.validateServicesHasPartsValues(basicPartService.getServiceName(), partValue);
        InspectionSteps.cancelInspection();

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultiplePartsServicesToLaborServiceForServicesWizardStep(String rowID,
                                                                                             String description, JSONObject testData) {

        List<String> serviceParts = new ArrayList<>();
        serviceParts.add("Assortments > Brake Fitting Assortment > N/A");
        serviceParts.add("Filters > Engine Oil Filter > Main");

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        LaborServiceData laborServiceData = workOrderData.getLaborServiceData();
        List<PartServiceData> partsInsideLabor = laborServiceData.getPartServiceDataList();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(laborServiceData.getServiceName());
        AvailableServicesScreenSteps.openServiceDetails(laborServiceData.getServiceName());
        LaborServiceSteps.addPartService();
        partsInsideLabor.forEach((service) -> {
            SearchSteps.textSearch(service.getServiceName());
            PartServiceSteps.selectPartService(service);
            PartServiceSteps.confirmPartInfo();
        });
        ScreenNavigationSteps.pressBackButton();
        PartServiceSteps.confirmPartInfo();

        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(laborServiceData.getServiceName());
        partsInsideLabor.stream().map(PartServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
        BundleServiceValidations.validateNumberOfServicesSelected(laborServiceData.getServiceName(), serviceParts.size());
        serviceParts.forEach((partValue) -> BundleServiceValidations.validateServicesHasPartsValues(laborServiceData.getServiceName(), partValue));

        final String inspectionID = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionID);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        BundleServiceSteps.switchToSelectedServices();

        BundleServiceValidations.validateServiceSelected(laborServiceData.getServiceName());
        partsInsideLabor.stream().map(PartServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
        BundleServiceValidations.validateNumberOfServicesSelected(laborServiceData.getServiceName(), serviceParts.size());
        serviceParts.forEach((partValue) -> BundleServiceValidations.validateServicesHasPartsValues(laborServiceData.getServiceName(), partValue));
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
