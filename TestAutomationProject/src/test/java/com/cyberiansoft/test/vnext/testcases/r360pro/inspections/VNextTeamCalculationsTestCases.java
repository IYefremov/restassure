package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.PricesUtils;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.ServiceDetailsValidations;
import com.cyberiansoft.test.vnext.validations.WizardScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamCalculationsTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Inspections Calculations Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getCalculationsTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingPercentageServiceInMyInspection(String rowID,
                                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServicesList().get(0));

        SelectedServicesScreenSteps.switchToSelectedService();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());

        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            SelectedServicesScreenSteps.changeSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice());
            String newPrice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice()) / 100);
            WizardScreenValidations.validateTotalPriceValue(newPrice);
        }

        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingMoneyServiceInMyInspection(String rowID,
                                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServicesList().get(0));

        SelectedServicesScreenSteps.switchToSelectedService();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            SelectedServicesScreenSteps.changeSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice()) / 100);
            WizardScreenValidations.validateTotalPriceValue(newprice);
        }

        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMyInspectionTotalAmountCantBeLessThan0(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());

        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());

        InspectionSteps.trySaveInspection();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_CANT_BE_LESS_THAN_0);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMyInspectionTotalAmountCantBeMoreThanOneMillion(String rowID,
                                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());

        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());

        InspectionSteps.trySaveInspection();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_EXCEEDS_THE_MAXIMUM_ALLOWED);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingMoneyServiceQTYInMyInspection(String rowID,
                                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServicesList().get(0));

        SelectedServicesScreenSteps.switchToSelectedService();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            SelectedServicesScreenSteps.changeSelectedServiceQuantity(moneyService.getServiceName(), moneyService.getServiceQuantity());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice()) *
                    BackOfficeUtils.getServiceQuantityValue(moneyService.getServiceQuantity());
            String newprice = PricesUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice()) / 100);
            WizardScreenValidations.validateTotalPriceValue(newprice);
        }

        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownMyInspectionWithNegativeAndPositivePercentageServices(String rowID,
                                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices)
            AvailableServicesScreenSteps.selectService(percentageService);

        SelectedServicesScreenSteps.switchToSelectedService();

        for (ServiceData percentageService : percentageServices)
            SelectedServicesScreenSteps.changeSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice());

        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithFewMoneyServices(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        moneyServices.forEach(moneyService -> {
                AvailableServicesScreenSteps.openServiceDetails(moneyService);
                ServiceDetailsScreenSteps.saveServiceDetails();
        });
        SelectedServicesScreenSteps.switchToSelectedService();
        for (ServiceData moneyService : moneyServices) {
            SelectedServicesScreenSteps.changeSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                SelectedServicesScreenSteps.changeSelectedServiceQuantity(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsStateFarmMatrix(String rowID,
                                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        List<VehiclePartData> vehiclePartsData = inspectionData.getMatrixServiceData().getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : vehiclePartsData) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
                List<ServiceData> additionalServices = vehiclePartData.getVehiclePartAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclePartInfoScreen.clickScreenBackButton();
        }
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsNationwideInsuranceMatrix(String rowID,
                                                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        List<VehiclePartData> vehiclePartsData = inspectionData.getMatrixServiceData().getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : vehiclePartsData) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
                List<ServiceData> additionalServices = vehiclePartData.getVehiclePartAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclePartInfoScreen.clickScreenBackButton();
        }
        vehiclePartsScreen.clickVehiclePartsSaveButton();

        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsProgressiveMatrix(String rowID,
                                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        List<VehiclePartData> vehiclePartsData = inspectionData.getMatrixServiceData().getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : vehiclePartsData) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
                List<ServiceData> additionalServices = vehiclePartData.getVehiclePartAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclePartInfoScreen.clickScreenBackButton();
        }
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalIsCorrectWhenAddingSeveralMoneyServicesOnVisualsScreen(String rowID,
                                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<DamageData> damagesData = inspectionData.getDamagesData();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        damagesData.forEach(damageData -> {
            VisualScreenSteps.addDefaultDamage(damageData);
            VisualScreenSteps.openEditDamage(VisualScreenSteps.getNumberOfAddedDamages()-1);
            ServiceDetailsScreenSteps.changeServicePrice(damageData.getMoneyService().getServicePrice());
            ServiceDetailsScreenSteps.changeServiceQuantity(damageData.getMoneyService().getServiceQuantity());
            ServiceDetailsScreenSteps.openQuestionForm(damageData.getMoneyService().getQuestionData().getQuestionSetionName());
            QuestionFormSteps.answerLogicalQuestion(damageData.getMoneyService().getQuestionData().getLogicalQuestionData());
            QuestionFormSteps.answerTextQuestion(damageData.getMoneyService().getQuestionData().getTextQuestionData());
            QuestionFormSteps.saveQuestionForm();
            ServiceDetailsScreenSteps.saveServiceDetails();
        });

        VisualScreenSteps.clickDamageCancelEditingButton();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalIsCorrectWhenAddingMoneyAndPercentageServicesOnVisualsScreen(String rowID,
                                                                                            String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final DamageData moneyDamage = inspectionData.getDamagesData().get(0);
        final DamageData percentageDamage = inspectionData.getDamagesData().get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR_NO_SHARING, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.addDefaultDamage(moneyDamage);
        VisualScreenSteps.openEditDamage();

        ServiceDetailsScreenSteps.changeServicePrice(moneyDamage.getMoneyService().getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(moneyDamage.getMoneyService().getServiceQuantity());
        ServiceDetailsScreenSteps.saveServiceDetails();

        VisualScreenSteps.addNonDefaultDamage(percentageDamage, percentageDamage.getPercentageService().getServiceName());

        VisualScreenSteps.clickDamageCancelEditingButton();
        WizardScreenValidations.validateTotalPriceValue(inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithNegativePrice(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.addDefaultDamage(inspectionData.getDamageData());
        VisualScreenSteps.openEditDamage();
        ServiceDetailsScreenSteps.changeServicePrice(inspectionData.getDamageData().getMoneyService().getServicePrice());
        ServiceDetailsScreenSteps.saveServiceDetails();

        VisualScreenSteps.openEditDamage();
        ServiceDetailsValidations.verifyServicePrice(inspectionData.getDamageData().getMoneyService().getServicePrice());
        ServiceDetailsScreenSteps.saveServiceDetails();

        VisualScreenSteps.clickDamageCancelEditingButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
