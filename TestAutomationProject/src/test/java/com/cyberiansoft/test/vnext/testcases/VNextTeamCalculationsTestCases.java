package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
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
        inspectionscreen = new VNextInspectionsScreen(appiumdriver);
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
        inspectionscreen = new VNextInspectionsScreen(appiumdriver);
        homescreen = inspectionscreen.clickBackButton();
    }
}
