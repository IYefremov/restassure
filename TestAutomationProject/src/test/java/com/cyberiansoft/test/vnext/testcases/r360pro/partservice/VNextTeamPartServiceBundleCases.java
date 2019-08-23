package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.BundleServiceData;
import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.BundleServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.LaborServiceSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.BundleServiceValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamPartServiceBundleCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceBundleCaseDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultiplePartsServicesToLaborService(String rowID,
                                                                        String description, JSONObject testData) {
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
        WizardScreenSteps.saveAction();
        WizardScreenSteps.saveAction();
        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(laborServiceInsideBundle.getServiceName());
        partsInsideLabor.stream().map(PartServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultipleLaborServicesToPartService(String rowID,
                                                                       String description, JSONObject testData) {
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
        PartServiceSteps.selectpartServiceDetails(partServiceInsideBundle);
        PartServiceSteps.addLaborService();
        laborServiceListInsideParts.forEach((service) -> LaborServiceSteps.selectService(service.getServiceName()));
        WizardScreenSteps.saveAction();
        PartServiceSteps.confirmPartInfo();
        BundleServiceSteps.switchToSelectedServices();
        BundleServiceValidations.validateServiceSelected(partServiceInsideBundle.getServiceName());
        laborServiceListInsideParts.stream().map(LaborServiceData::getServiceName).forEach(BundleServiceValidations::validateServiceSelected);
    }
}
