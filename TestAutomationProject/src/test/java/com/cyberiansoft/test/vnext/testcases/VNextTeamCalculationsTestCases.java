package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
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

    @BeforeClass(description = "Team Inspections Line Approval Test Cases")
    public void settingUp() throws Exception {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() throws Exception {
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
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        selectServicesScreen.selectService(inspdata.getServiceName());
        selectServicesScreen.selectService(inspdata.getPercentageServicesList().get(0).getServiceName());

        selectServicesScreen.clickSaveSelectedServicesButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        List<ServiceData> percentageServices = inspdata.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices) {
            inpsctionservicesscreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(percentageService.getServicePrice())/100);
            Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
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
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        selectServicesScreen.selectService(inspdata.getServiceName());
        selectServicesScreen.selectService(inspdata.getMoneyServicesList().get(0).getServiceName());

        selectServicesScreen.clickSaveSelectedServicesButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            inpsctionservicesscreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice())/100);
            Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
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
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        selectServicesScreen.selectService(inspdata.getMoneyServiceName());
        selectServicesScreen.selectService(inspdata.getPercentageServiceName());

        selectServicesScreen.clickSaveSelectedServicesButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.setServiceAmountValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServicePrice());
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        inpsctionservicesscreen.clickSaveInspectionMenuButton();
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
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        selectServicesScreen.selectService(inspdata.getMoneyServiceName());
        selectServicesScreen.selectService(inspdata.getPercentageServiceName());

        selectServicesScreen.clickSaveSelectedServicesButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.setServiceAmountValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServicePrice());
        inpsctionservicesscreen.setServiceQuantityValue(inspdata.getMoneyServiceName(), inspdata.getMoneyServiceQuantity());
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());

        inpsctionservicesscreen.clickSaveInspectionMenuButton();
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
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        selectServicesScreen.selectService(inspdata.getServiceName());
        selectServicesScreen.selectService(inspdata.getMoneyServicesList().get(0).getServiceName());

        selectServicesScreen.clickSaveSelectedServicesButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices) {
            inpsctionservicesscreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
            float moneyServicePrice = BackOfficeUtils.getServicePriceValue(moneyService.getServicePrice())*
                    BackOfficeUtils.getServiceQuantityValue(moneyService.getServiceQuantity());
            String newprice =  BackOfficeUtils.getFormattedServicePriceValue(moneyServicePrice + moneyServicePrice * BackOfficeUtils.getServicePriceValue(inspdata.getServicePrice())/100);
            Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), newprice);
        }

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
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
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        selectServicesScreen.selectService(inspdata.getServiceName());
        List<ServiceData> percentageServices = inspdata.getPercentageServicesList();
        for (ServiceData percentageService : percentageServices)
            selectServicesScreen.selectService(percentageService.getServiceName());

        selectServicesScreen.clickSaveSelectedServicesButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

        for (ServiceData percentageService : percentageServices)
            inpsctionservicesscreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());

        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
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
        VNextSelectServicesScreen selectServicesScreen = inpsctionservicesscreen.clickAddServicesButton();
        List<ServiceData> moneyServices = inspdata.getMoneyServicesList();
        for (ServiceData moneyService : moneyServices)
            selectServicesScreen.selectService(moneyService.getServiceName());
        selectServicesScreen.clickSaveSelectedServicesButton();

        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        for (ServiceData moneyService : moneyServices) {
            inpsctionservicesscreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
            inpsctionservicesscreen.setServiceQuantityValue(moneyService.getServiceName(), moneyService.getServiceQuantity());
        }
        Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
        homescreen = inspectionscreen.clickBackButton();
    }
}