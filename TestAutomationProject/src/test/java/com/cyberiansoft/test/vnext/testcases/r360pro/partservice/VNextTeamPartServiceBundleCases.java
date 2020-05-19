package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.BundleServiceData;
import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.services.BundleServiceScreenInteractrions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.BundleServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.LaborServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.BundleServiceValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamPartServiceBundleCases extends BaseTestClass {

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceBundleCaseDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultiplePartsServicesToLaborService(String rowID,
                                                                        String description, JSONObject testData) {

        List<String> serviceParts = new ArrayList<>();
        serviceParts.add("Assortments > Brake Fitting Assortment > N/A");
        serviceParts.add("Filters > Engine Oil Filter > Main");

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        LaborServiceData laborServiceInsideBundle = bundleServiceData.getLaborService();
        List<PartServiceData> partsInsideLabor = laborServiceInsideBundle.getPartServiceDataList();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(bundleServiceData.getBundleServiceName());
        AvailableServicesScreenSteps.openServiceDetails(bundleServiceData.getBundleServiceName());
        BundleServiceSteps.openServiceDetails(laborServiceInsideBundle.getServiceName());
        LaborServiceSteps.addPartService();
        partsInsideLabor.forEach((service) -> {
            PartServiceSteps.selectPartService(service);
            PartServiceSteps.confirmPartInfo();
        });
        ScreenNavigationSteps.pressBackButton();
        PartServiceSteps.confirmPartInfo();
        BundleServiceSteps.setBundlePrice(BundleServiceScreenInteractrions.getBundleServiceSelectedAmount());

        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(laborServiceInsideBundle.getServiceName());
        partsInsideLabor.stream().map(PartServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
        WizardScreenSteps.saveAction();
        final String inspectionID = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionID);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openLaborServiceDetails(bundleServiceData.getBundleServiceName());
        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(laborServiceInsideBundle.getServiceName());
        partsInsideLabor.stream().map(PartServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
        BundleServiceValidations.validateNumberOfServicesSelected(laborServiceInsideBundle.getServiceName(), serviceParts.size());
        serviceParts.forEach((partValue) -> BundleServiceValidations.validateServicesHasPartsValues(laborServiceInsideBundle.getServiceName(), partValue));

        WizardScreenSteps.saveAction();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultipleLaborServicesToPartService(String rowID,
                                                                       String description, JSONObject testData) {

        final String partValue = "Dash > Dash Panel > N/A";

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        PartServiceData partServiceInsideBundle = bundleServiceData.getPartService().get(0);
        List<LaborServiceData> laborServiceListInsideParts = partServiceInsideBundle.getLaborServiceDataList();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(bundleServiceData.getBundleServiceName());
        AvailableServicesScreenSteps.openServiceDetails(bundleServiceData.getBundleServiceName());
        BundleServiceSteps.openServiceDetails(partServiceInsideBundle.getServiceName());
        PartServiceSteps.selectPartServiceDetails(partServiceInsideBundle);
        PartServiceSteps.confirmPartInfo();
        PartServiceSteps.addLaborService();
        laborServiceListInsideParts.forEach((service) -> LaborServiceSteps.selectService(service.getServiceName()));
        ScreenNavigationSteps.pressBackButton();
        PartServiceSteps.confirmPartInfo();
        BundleServiceSteps.setBundlePrice(BundleServiceScreenInteractrions.getBundleServiceSelectedAmount());

        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(partServiceInsideBundle.getServiceName());
        laborServiceListInsideParts.stream().map(LaborServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
        WizardScreenSteps.saveAction();
        final String inspectionID = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionID);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openLaborServiceDetails(bundleServiceData.getBundleServiceName());
        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(partServiceInsideBundle.getServiceName());
        laborServiceListInsideParts.stream().map(LaborServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
        laborServiceListInsideParts.forEach((service) -> BundleServiceValidations.validateServicesHasPartsValues(service.getServiceName(), partValue));
        WizardScreenSteps.saveAction();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
