package com.cyberiansoft.test.vnext.testcases.r360pro.vehicleparts;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.ServiceDetailsValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BasicVehiclePartsTests extends BaseTestClass {
    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getVehiclePartsCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCreateWoWithSingleVehiclePartForSinglePartSelect(String rowID,
                                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.openServiceDetails(serviceData.getServiceName());
        ServiceDetailsScreenSteps.selectVehiclePart(serviceData.getVehiclePart());
        ServiceDetailsScreenSteps.saveServiceDetails();
        ServiceDetailsScreenSteps.saveServiceDetails();
        SelectedServicesScreenSteps.openServiceDetails(serviceData.getServiceName());
        ServiceDetailsValidations.servicePartShouldBe(serviceData.getVehiclePart());
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectSinglePartForQuestionAnswerWhenCreatingWo(String rowID,
                                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData expectedServiceFromQuestion1 = workOrderData.getServicesList().get(0);
        ServiceData expectedServiceFromQuestion2 = workOrderData.getServicesList().get(1);
        QuestionsData moneyServiceQuestion = workOrderData.getQuestionScreenData().getQuestionsData().get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormSteps.answerGeneralSlideQuestion(moneyServiceQuestion);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES, 1);

        SelectedServicesScreenSteps.openServiceDetails(expectedServiceFromQuestion1.getServiceName());
        ServiceDetailsScreenSteps.selectVehiclePart(expectedServiceFromQuestion1.getVehiclePart());
        ServiceDetailsScreenSteps.saveServiceDetails();

        SelectedServicesScreenSteps.openServiceDetails(expectedServiceFromQuestion2.getServiceName());
        ServiceDetailsScreenSteps.selectVehiclePart(expectedServiceFromQuestion2.getVehiclePart());
        ServiceDetailsScreenSteps.saveServiceDetails();

        SelectedServicesScreenSteps.openServiceDetails(expectedServiceFromQuestion1.getServiceName());
        ServiceDetailsValidations.servicePartShouldBe(expectedServiceFromQuestion1.getVehiclePart());
        ScreenNavigationSteps.pressBackButton();

        SelectedServicesScreenSteps.openServiceDetails(expectedServiceFromQuestion2.getServiceName());
        ServiceDetailsValidations.servicePartShouldBe(expectedServiceFromQuestion2.getVehiclePart());
        ScreenNavigationSteps.pressBackButton();

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectSinglePartViaVisualFormWhenCreatingWo(String rowID,
                                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        DamageData damageData = workOrderData.getDamageData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.addDamage(damageData);
        VisualScreenSteps.openEditDamage();
        ServiceDetailsScreenSteps.selectVehiclePart(damageData.getMoneyService().getVehiclePart());
        ServiceDetailsValidations.servicePartShouldBe(damageData.getMoneyService().getVehiclePart());
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectMultipleVehiclePartsForService(String rowID,
                                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();
        List<VehiclePartData> vehiclePartData = serviceData.getVehicleParts();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.openServiceDetails(serviceData.getServiceName());
        ServiceDetailsScreenSteps.selectVehicleParts(vehiclePartData);
        ServiceDetailsScreenSteps.saveServiceDetails();
        SelectedServicesScreenSteps.switchToSelectedService();
        vehiclePartData.forEach(vehiclePart -> ListServicesValidations.verifyServiceWithDescriptionSelected(serviceData.getServiceName(), vehiclePart.getVehiclePartName()));
        SelectedServicesScreenSteps.openServiceDetails(serviceData.getServiceName());
        ServiceDetailsScreenSteps.selectVehiclePart(serviceData.getVehiclePart());
        ServiceDetailsValidations.verifyUserIsOnDetailsPage();
        ServiceDetailsScreenSteps.saveServiceDetails();
        ListServicesValidations.verifyServiceWithDescriptionSelected(serviceData.getServiceName(), serviceData.getVehiclePart().getVehiclePartName());
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
