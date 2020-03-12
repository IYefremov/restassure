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
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.LaborServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class VNextTeamPartServiceLaborCases extends BaseTestClass {
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
        ScreenNavigationSteps.pressBackButton();
        PartServiceSteps.confirmPartInfo();
        SelectedServicesScreenSteps.switchToSelectedService();
        ListServicesValidations.verifyServiceSelected(basicPartService.getServiceName(), true);
        laborServiceData
                .forEach(serviceData -> ListServicesValidations.verifyServiceSelected(serviceData.getServiceName(), true));

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
        ScreenNavigationSteps.pressBackButton();
        PartServiceSteps.confirmPartInfo();
        SelectedServicesScreenSteps.switchToSelectedService();
        ListServicesValidations.verifyServiceSelected(laborServiceData.getServiceName(), true);
        partServiceData.forEach(serviceData -> ListServicesValidations.verifyServiceSelected(serviceData.getServiceName(), true));

        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanRemoveOrLeftPartServiceRemovingLaborService(String rowID,
                                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<LaborServiceData> laborServiceDataList = workOrderData.getLaborServicesList();

        LaborServiceData laborServiceToRemoveWithPartService = laborServiceDataList.get(0);
        PartServiceData expectedRemovedPartService = laborServiceToRemoveWithPartService.getPartServiceDataList().get(0);
        LaborServiceData laborServiceToRemoveWithoutPartService = laborServiceDataList.get(1);
        PartServiceData expectedPresentPartService = laborServiceToRemoveWithoutPartService.getPartServiceDataList().get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        laborServiceDataList.forEach(laborServiceData -> {
            SearchSteps.textSearch(laborServiceData.getServiceName());
            AvailableServicesScreenSteps.openServiceDetails(laborServiceData.getServiceName());
            LaborServiceSteps.addPartService();
            laborServiceData.getPartServiceDataList()
                    .forEach(service -> {
                        SearchSteps.textSearch(service.getServiceName());
                        PartServiceSteps.selectPartService(service);
                        PartServiceSteps.acceptDetailsScreen();
                    });
            ScreenNavigationSteps.pressBackButton();
            PartServiceSteps.confirmPartInfo();
        });
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.unSelectService(laborServiceToRemoveWithPartService.getServiceName());
        GeneralSteps.confirmDialog();
        SelectedServicesScreenSteps.unSelectService(laborServiceToRemoveWithoutPartService.getServiceName());
        GeneralSteps.declineDialog();

        ListServicesValidations.verifyServiceSelected(expectedPresentPartService.getServiceName(), true);
        ListServicesValidations.verifyServiceSelected(laborServiceToRemoveWithPartService.getServiceName(), false);
        ListServicesValidations.verifyServiceSelected(expectedRemovedPartService.getServiceName(), false);
        ListServicesValidations.verifyServiceSelected(laborServiceToRemoveWithoutPartService.getServiceName(), false);

        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanRemoveOrLeftLaborServiceRemovingPartService(String rowID,
                                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceDataList = workOrderData.getPartServiceDataList();
        PartServiceData partServiceToRemoveWithLaborService = partServiceDataList.get(0);
        LaborServiceData expectedRemovedLaborService = partServiceToRemoveWithLaborService.getLaborServiceDataList().get(0);
        PartServiceData partServiceToRemoveWithoutLaborService = partServiceDataList.get(1);
        LaborServiceData expectedPresentLaborService = partServiceToRemoveWithoutLaborService.getLaborServiceDataList().get(0);


        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        partServiceDataList.forEach(partServiceData -> {
            SearchSteps.textSearch(partServiceData.getServiceName());
            PartServiceSteps.selectPartService(partServiceData);
            PartServiceSteps.addLaborService();
            partServiceData.getLaborServiceDataList()
                    .forEach(service -> LaborServiceSteps.selectService(service.getServiceName()));
            ScreenNavigationSteps.pressBackButton();
            PartServiceSteps.confirmPartInfo();
        });
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.unSelectService(partServiceToRemoveWithLaborService.getServiceName());
        GeneralSteps.confirmDialog();
        SelectedServicesScreenSteps.unSelectService(partServiceToRemoveWithoutLaborService.getServiceName());
        GeneralSteps.declineDialog();

        ListServicesValidations.verifyServiceSelected(expectedPresentLaborService.getServiceName(), true);
        ListServicesValidations.verifyServiceSelected(partServiceToRemoveWithLaborService.getServiceName(), false);
        ListServicesValidations.verifyServiceSelected(expectedRemovedLaborService.getServiceName(), false);
        ListServicesValidations.verifyServiceSelected(partServiceToRemoveWithoutLaborService.getServiceName(), false);

        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
