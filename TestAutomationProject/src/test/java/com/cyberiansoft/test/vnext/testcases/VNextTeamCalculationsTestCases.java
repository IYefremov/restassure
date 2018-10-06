package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.HailMatrixService;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamCalculationsTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-calculations-data.json";

    @BeforeClass(description = "Team Inspections Calculations Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingPercentageServiceInMyInspection(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspdata.getServiceName());
        availableservicesscreen.selectService(inspdata.getPercentageServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        List<ServiceData> percentageServices = inspdata.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingMoneyServiceInMyInspection(String rowID,
                                                                                         String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspdata.getServiceName());
        availableservicesscreen.selectService(inspdata.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyMyInspectionTotalAmountCantBeLessThan0(String rowID,
                                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspdata.getMoneyServiceName());
        availableservicesscreen.selectService(inspdata.getPercentageServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServicePrice());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_CANT_BE_LESS_THAN_0);
        inspectionscreen = availableservicesscreen.cancelInspection();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyMyInspectionTotalAmountCantBeMoreThanOneMillion(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspdata.getMoneyServiceName());
        availableservicesscreen.selectService(inspdata.getPercentageServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServiceQuantity());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_EXCEEDS_THE_MAXIMUM_ALLOWED);
        inspectionscreen = availableservicesscreen.cancelInspection();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingMoneyServiceQTYInMyInspection(String rowID,
                                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspdata.getServiceName());
        availableservicesscreen.selectService(inspdata.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice())*
                    BackOfficeUtils.getServiceQuantityValue(moneyService.getServiceQuantity());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownMyInspectionWithNegativeAndPositivePercentageServices(String rowID,
                                                                                         String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspdata.getServiceName());
        List<ServiceData> percentageServices = inspdata.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices)
            availableservicesscreen.selectService(percentageService.getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();

        for (ServiceData percentageService : percentageServices)
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());

        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithFewMoneyServices(String rowID,
                                                                                       String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices)
            availableservicesscreen.selectService(moneyService.getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsStateFarmMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

        VNextPriceMatrixesScreen pricematrixesscreen = availableservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(inspdata.getMatrixServiceData().getHailMatrixName());
        List<HailMatrixService>  hailMatrixServices = inspdata.getMatrixServiceData().getHailMatrixServices();
        for (HailMatrixService  hailMatrixService : hailMatrixServices) {
            VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            if (hailMatrixService.getMatrixAdditionalServices() != null) {
                List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclepartinfoscreen.clickSaveVehiclePartInfo();
            vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        }
        vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        availableservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(availableservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsNationwideInsuranceMatrix(String rowID,
                                                                                                String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

        VNextPriceMatrixesScreen pricematrixesscreen = availableservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(inspdata.getMatrixServiceData().getHailMatrixName());
        List<HailMatrixService>  hailMatrixServices = inspdata.getMatrixServiceData().getHailMatrixServices();
        for (HailMatrixService  hailMatrixService : hailMatrixServices) {
            VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            if (hailMatrixService.getMatrixAdditionalServices() != null) {
                List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        }
        vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        availableservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

        Assert.assertEquals(availableservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownForMyInspectionWithPanelsProgressiveMatrix(String rowID,
                                                                                                String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

        VNextPriceMatrixesScreen pricematrixesscreen = availableservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(inspdata.getMatrixServiceData().getHailMatrixName());
        List<HailMatrixService>  hailMatrixServices = inspdata.getMatrixServiceData().getHailMatrixServices();
        for (HailMatrixService  hailMatrixService : hailMatrixServices) {
            VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            if (hailMatrixService.getMatrixAdditionalServices() != null) {
                List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
                for (ServiceData additionalService : additionalServices)
                    vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
            }
            vehiclepartinfoscreen.clickSaveVehiclePartInfo();
            vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        }
        vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        availableservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(availableservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        inspectionscreen.clickBackButton();
    }
}
