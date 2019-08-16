package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.LaborServiceSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class VNextTeamPartServiceLaborCases extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";

    @BeforeClass(description = "Team Monitoring labor tests")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceLaborCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddPartServiceAndAssignThatToLaborService(String rowID,
                                                                 String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceDataList();
        PartServiceData basicPartService = partServiceData.get(0);
        List<LaborServiceData> laborServiceData = basicPartService.getLaborServiceDataList();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartService.getServiceName());
        PartServiceSteps.selectPartService(basicPartService);
        PartServiceSteps.addLaborService();
        laborServiceData.stream()
                .map(LaborServiceData::getServiceName)
                .collect(Collectors.toList())
                .forEach(LaborServiceSteps::selectService);
        WizardScreenSteps.saveAction();
        PartServiceSteps.confirmPartInfo();
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.verifyServiceSelected(basicPartService.getServiceName());
        laborServiceData.stream()
                .map(LaborServiceData::getServiceName)
                .collect(Collectors.toList())
                .forEach(SelectedServicesScreenSteps::verifyServiceSelected);
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddLaborServiceAndAssignToPartService(String rowID,
                                                             String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        LaborServiceData laborServiceData = workOrderData.getLaborServiceData();
        List<PartServiceData> partServiceData = laborServiceData.getPartServiceDataList();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(laborServiceData.getServiceName());
        AvailableServicesScreenSteps.openServiceDetails(laborServiceData.getServiceName());
        LaborServiceSteps.addPartService();
        partServiceData
                .forEach(service -> {
                    SearchSteps.textSearch(service.getServiceName());
                    PartServiceSteps.selectPartService(service);
                    PartServiceSteps.acceptDetailsScreen();
                });
        WizardScreenSteps.saveAction();
        PartServiceSteps.confirmPartInfo();
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.verifyServiceSelected(laborServiceData.getServiceName());
        partServiceData.stream()
                .map(PartServiceData::getServiceName)
                .collect(Collectors.toList())
                .forEach(SelectedServicesScreenSteps::verifyServiceSelected);
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
