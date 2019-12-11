package com.cyberiansoft.test.ios10_client.testcases.hd.workorders;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSWorkOrdersCalculationsTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Work Orders Calculations Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getWorkOrdersCalculationsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO(String rowID,
                                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String zeroPrice = "0";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_MONITOR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
            if (serviceData.getServicePrice().equals(zeroPrice)) {
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                if (serviceData.getServicePrice().equals(zeroPrice))
                    AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
                selectedServiceDetailsScreen.clickTechniciansIcon();
                AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
            } else {
                selectedServiceDetailsScreen.clickTechniciansIcon();
                AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
                selectedServiceDetailsScreen.cancelSelectedServiceDetails();
                selectedServiceDetailsScreen.setServiceRateValue(serviceData.getServicePrice());
                selectedServiceDetailsScreen.clickTechniciansIcon();
            }
            TechniciansPopup techniciansPopup = new TechniciansPopup();
            techniciansPopup.searchTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFirstName());
            techniciansPopup.selecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            techniciansPopup.cancelTechViewDetails();
            techniciansPopup.searchTechnician(serviceData.getServiceNewTechnician().getTechnicianFirstName());
            techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
            techniciansPopup.cancelTechViewDetails();
            DriverBuilder.getInstance().getAppiumDriver().hideKeyboard();
            if (serviceData.getServicePrice().equals(zeroPrice)) {
                Assert.assertFalse(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
            } else {
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
            }
            techniciansPopup.saveTechViewDetails();
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        vehicleScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet(String rowID,
                                                                                        String description, JSONObject testData) {

        final String questionName = "Q1";
        final String questionAswer = "No - rate 0";
        final String questionAswerSecond = "A1";
        final String questionVehiclePart = "Deck Lid";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_PANEL_GROUP);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        NavigationSteps.navigateToScreen("Zayats Section2");
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.selectAnswerForQuestionWithAdditionalConditions(questionName, questionAswer, questionAswerSecond, questionVehiclePart);
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));

        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }
}
