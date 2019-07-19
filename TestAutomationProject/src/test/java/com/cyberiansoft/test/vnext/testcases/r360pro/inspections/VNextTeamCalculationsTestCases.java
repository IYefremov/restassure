package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextQuestionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamCalculationsTestCases extends BaseTestCaseTeamEditionRegistration {

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

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice());
            String newPrice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice()) / 100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newPrice);
        }

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingMoneyServiceInMyInspection(String rowID,
                                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice()) / 100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMyInspectionTotalAmountCantBeLessThan0(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_CANT_BE_LESS_THAN_0);
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMyInspectionTotalAmountCantBeMoreThanOneMillion(String rowID,
                                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_EXCEEDS_THE_MAXIMUM_ALLOWED);
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingMoneyServiceQTYInMyInspection(String rowID,
                                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice()) *
                    BackOfficeUtils.getServiceQuantityValue(moneyService.getServiceQuantity());
            String newprice = BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServiceData().getServicePrice()) / 100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownMyInspectionWithNegativeAndPositivePercentageServices(String rowID,
                                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices)
            availableServicesScreen.selectService(percentageService.getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

        for (ServiceData percentageService : percentageServices)
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());

        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithFewMoneyServices(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices)
            availableServicesScreen.selectService(moneyService.getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsStateFarmMatrix(String rowID,
                                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        List<VehiclePartData> vehiclePartsData = inspectionData.getMatrixServiceData().getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
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
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsNationwideInsuranceMatrix(String rowID,
                                                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        List<VehiclePartData> vehiclePartsData = inspectionData.getMatrixServiceData().getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
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
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsProgressiveMatrix(String rowID,
                                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        List<VehiclePartData> vehiclePartsData = inspectionData.getMatrixServiceData().getVehiclePartsData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
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
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalIsCorrectWhenAddingSeveralMoneyServicesOnVisualsScreen(String rowID,
                                                                                      String description, JSONObject testData) {

        final String selectdamage = "Detail";

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);

        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        //visualScreen.clickAddServiceButton();
        visualScreen.selectDefaultDamage(selectdamage);
        visualScreen.clickCarImageACoupleTimes(moneyServices.size());
        BaseUtils.waitABit(1000);
        int markerIndex = 0;

        for (ServiceData moneyService : moneyServices) {
            VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker(markerIndex);
            serviceDetailsScreen.setServiceAmountValue(moneyService.getServicePrice());
            serviceDetailsScreen.setServiceQuantityValue(moneyService.getServiceQuantity());
            VNextQuestionsScreen questionsScreen = serviceDetailsScreen.clickServiceQuestionSection("Test Section");
            questionsScreen.setAllRequiredQuestions("test 1");
            questionsScreen.clickDoneButton();
            serviceDetailsScreen = new VNextServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
            serviceDetailsScreen.clickServiceDetailsDoneButton();
            visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
            BaseUtils.waitABit(1000);
            markerIndex++;
        }
        visualScreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = visualScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionPrice(), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTotalIsCorrectWhenAddingMoneyAndPercentageServicesOnVisualsScreen(String rowID,
                                                                                            String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String panelName = "Detail";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);

        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceData().getServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceQuantity());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextSelectDamagesScreen selectdamagesscreen = visualScreen.clickAddServiceButton();
        selectdamagesscreen.selectAllDamagesTab();
        VNextVisualServicesScreen visualServicesScreen = selectdamagesscreen.clickCustomDamageType(panelName);
        visualScreen = visualServicesScreen.selectCustomService(inspectionData.getPercentageServiceData().getServiceName());
        BaseUtils.waitABit(1000);
        visualScreen.clickCarImageSecondTime();

        visualScreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = visualScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionPrice(), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithNegativePrice(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceData().getServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.clickServiceAmountField();
        Assert.assertEquals(serviceDetailsScreen.getServiceAmountValue(), inspectionData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        visualScreen.clickDamageCancelEditingButton();
        visualScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }
}
