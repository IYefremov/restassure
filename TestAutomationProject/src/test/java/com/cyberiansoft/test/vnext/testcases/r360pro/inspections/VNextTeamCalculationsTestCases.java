package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.HailMatrixService;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
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
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import io.appium.java_client.MobileElement;
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

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        List<ServiceData> percentageServices = inspectionData.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspectionData.getServicePrice());
            String newPrice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newPrice);
        }

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServicePrice());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_CANT_BE_LESS_THAN_0);
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServiceQuantity());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_EXCEEDS_THE_MAXIMUM_ALLOWED);
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice())*
                    BackOfficeUtils.getServiceQuantityValue(moneyService.getServiceQuantity());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspectionData.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceName());
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

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        List<ServiceData> moneyServices = inspectionData.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices)
            availableServicesScreen.selectService(moneyService.getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(  inspectionData.getMatrixServiceData().getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(inspectionData.getMatrixServiceData().getHailMatrixName());
        List<HailMatrixService>  hailMatrixServices = inspectionData.getMatrixServiceData().getHailMatrixServices();
        for (HailMatrixService  hailMatrixService : hailMatrixServices) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclePartInfoScreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            if (hailMatrixService.getMatrixAdditionalServices() != null) {
                List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();
            vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        }
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(  inspectionData.getMatrixServiceData().getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(inspectionData.getMatrixServiceData().getHailMatrixName());
        List<HailMatrixService>  hailMatrixServices = inspectionData.getMatrixServiceData().getHailMatrixServices();
        for (HailMatrixService  hailMatrixService : hailMatrixServices) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclePartInfoScreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            if (hailMatrixService.getMatrixAdditionalServices() != null) {
                List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        }
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();

        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(  inspectionData.getMatrixServiceData().getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(inspectionData.getMatrixServiceData().getHailMatrixName());
        List<HailMatrixService>  hailMatrixServices = inspectionData.getMatrixServiceData().getHailMatrixServices();
        for (HailMatrixService  hailMatrixService : hailMatrixServices) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclePartInfoScreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            if (hailMatrixService.getMatrixAdditionalServices() != null) {
                List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();
            vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        }
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
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

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);

        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.setServiceAmountValue(inspectionData.getMoneyServicePrice());
        serviceDetailsScreen.setServiceQuantityValue(inspectionData.getMoneyServiceQuantity());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextSelectDamagesScreen selectdamagesscreen = visualScreen.clickAddServiceButton();
        selectdamagesscreen.selectAllDamagesTab();
        VNextVisualServicesScreen visualServicesScreen = selectdamagesscreen.clickCustomDamageType(panelName);
        visualScreen = visualServicesScreen.selectCustomService(inspectionData.getPercentageServiceName());
        BaseUtils.waitABit(1000);
        visualScreen.clickCarImageSecondTime();

        visualScreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsScreen = visualScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getFirstInspectionPrice(), inspectionData.getInspectionPrice());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInspectionWithNegativePrice(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.setServiceAmountValue(inspectionData.getMoneyServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        serviceDetailsScreen = visualScreen.clickCarImageMarker();
        serviceDetailsScreen.clickServiceAmountField();
        Assert.assertEquals(serviceDetailsScreen.getServiceAmountValue(), inspectionData.getMoneyServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        visualScreen.clickDamageCancelEditingButton();
        visualScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }
}
