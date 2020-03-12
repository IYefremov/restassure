package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextQuestionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.PricesUtils;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
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
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServicesList().get(0));

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            SelectedServicesScreenSteps.changeSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice());
            String newPrice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice()) / 100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newPrice);
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
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServicesList().get(0));

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            SelectedServicesScreenSteps.changeSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice()) / 100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
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
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

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
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

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
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServicesList().get(0));

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            SelectedServicesScreenSteps.changeSelectedServiceQuantity(moneyService.getServiceName(), moneyService.getServiceQuantity());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice()) *
                    BackOfficeUtils.getServiceQuantityValue(moneyService.getServiceQuantity());
            String newprice = PricesUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice()) / 100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
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
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices)
            AvailableServicesScreenSteps.selectService(percentageService);

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

        for (ServiceData percentageService : percentageServices)
            SelectedServicesScreenSteps.changeSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice());

        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithFewMoneyServices(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        moneyServices.forEach(moneyService -> {
                AvailableServicesScreenSteps.openServiceDetails(moneyService);
                ServiceDetailsScreenSteps.saveServiceDetails();
        });
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData moneyService : moneyServices) {
            SelectedServicesScreenSteps.changeSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                SelectedServicesScreenSteps.changeSelectedServiceQuantity(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsStateFarmMatrix(String rowID,
                                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();

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
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
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
        VNextAvailableServicesScreen availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();

        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsProgressiveMatrix(String rowID,
                                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();

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
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalIsCorrectWhenAddingSeveralMoneyServicesOnVisualsScreen(String rowID,
                                                                                      String description, JSONObject testData) {

        final String selectdamage = "Detail";

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VNextVisualScreen visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        BaseUtils.waitABit(2000);
        visualScreen.selectDefaultDamage(selectdamage);
        visualScreen.clickCarImageACoupleTimes(moneyServices.size());
        BaseUtils.waitABit(1000);
        int markerIndex = 0;

        for (ServiceData moneyService : moneyServices) {
            VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker(markerIndex);
            ServiceDetailsScreenSteps.changeServicePrice(moneyService.getServicePrice());
            ServiceDetailsScreenSteps.changeServiceQuantity(moneyService.getServiceQuantity());
            VNextQuestionsScreen questionsScreen = serviceDetailsScreen.clickServiceQuestionSection("Test Section");
            QuestionsData questionsData = new QuestionsData();
            questionsData.setQuestionName("Free Text");
            questionsData.setQuestionAnswer("some text");
            QuestionFormSteps.answerGeneralTextQuestion(questionsData);
            questionsScreen.clickDoneButton();
            ServiceDetailsScreenSteps.saveServiceDetails();
            BaseUtils.waitABit(1000);
            markerIndex++;
        }
        visualScreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
        InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionPrice(), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalIsCorrectWhenAddingMoneyAndPercentageServicesOnVisualsScreen(String rowID,
                                                                                            String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String panelName = "Detail";
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR_NO_SHARING, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VNextVisualScreen visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceData().getServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        visualScreen.clickCarImageMarker();
        ServiceDetailsScreenSteps.changeServicePrice(inspectionData.getMoneyServiceData().getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(inspectionData.getMoneyServiceData().getServiceQuantity());
        ServiceDetailsScreenSteps.saveServiceDetails();

        VNextSelectDamagesScreen selectdamagesscreen = visualScreen.clickAddServiceButton();
        selectdamagesscreen.selectAllDamagesTab();
        VNextVisualServicesScreen visualServicesScreen = selectdamagesscreen.clickCustomDamageType(panelName);
        visualScreen = visualServicesScreen.selectCustomService(inspectionData.getPercentageServiceData().getServiceName());
        BaseUtils.waitABit(1000);
        visualScreen.clickCarImageSecondTime();

        visualScreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionPrice(), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithNegativePrice(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceData().getServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.clickServiceAmountField();
        Assert.assertEquals(serviceDetailsScreen.getServiceAmountValue(), inspectionData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        visualScreen.clickDamageCancelEditingButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
