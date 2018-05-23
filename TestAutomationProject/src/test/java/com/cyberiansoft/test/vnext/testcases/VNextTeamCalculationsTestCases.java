package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.HailMatrixService;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.selectService(inspdata.getServiceName());
        inpsctionservicesscreen.selectService(inspdata.getPercentageServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        List<ServiceData> percentageServices = inspdata.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.selectService(inspdata.getServiceName());
        inpsctionservicesscreen.selectService(inspdata.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice())/100);
            Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.selectService(inspdata.getMoneyServiceName());
        inpsctionservicesscreen.selectService(inspdata.getPercentageServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServicePrice());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_CANT_BE_LESS_THAN_0);
        inspectionscreen = inpsctionservicesscreen.cancelInspection();
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);;
        inpsctionservicesscreen.selectService(inspdata.getMoneyServiceName());
        inpsctionservicesscreen.selectService(inspdata.getPercentageServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();;
        selectedServicesScreen.setServiceAmountValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServiceQuantity());
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        selectedServicesScreen.clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.TOTAL_AMOUNT_OF_INSPECTION_EXCEEDS_THE_MAXIMUM_ALLOWED);
        inspectionscreen = inpsctionservicesscreen.cancelInspection();
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.selectService(inspdata.getServiceName());
        inpsctionservicesscreen.selectService(inspdata.getMoneyServicesList().get(0).getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
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
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.selectService(inspdata.getServiceName());
        List<ServiceData> percentageServices = inspdata.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices)
            inpsctionservicesscreen.selectService(percentageService.getServiceName());

        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();

        for (ServiceData percentageService : percentageServices)
            selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());

        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices)
            inpsctionservicesscreen.selectService(moneyService.getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
        for (ServiceData moneyService : moneyServices) {
            selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            selectedServicesScreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

        VNextPriceMatrixesScreen pricematrixesscreen = inpsctionservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
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
        inpsctionservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

        VNextPriceMatrixesScreen pricematrixesscreen = inpsctionservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
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
        inpsctionservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        homescreen = inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

        VNextPriceMatrixesScreen pricematrixesscreen = inpsctionservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
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
        inpsctionservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        homescreen = inspectionscreen.clickBackButton();
    }
}
