package com.cyberiansoft.test.ios10_client.testcases.regular.workorders;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSWorkOrdersCalculationsTestCases extends IOSRegularBaseTestCase {

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_MONITOR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            if (serviceData.getServicePrice().equals(zeroPrice)) {
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
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

            selectedServiceDetailsScreen.searchTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFirstName());
            selectedServiceDetailsScreen.selecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.cancelSearchTechnician();
            selectedServiceDetailsScreen.searchTechnician(serviceData.getServiceNewTechnician().getTechnicianFirstName());
            selectedServiceDetailsScreen.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.cancelSearchTechnician();
            if (serviceData.getServicePrice().equals(zeroPrice)) {
                Assert.assertFalse(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
            } else {
                Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
            }
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
            selectedServiceDetailsScreen.saveSelectedServiceDetails();

        }
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_PANEL_GROUP);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToScreen("Zayats Section2");
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.selectAnswerForQuestionWithAdditionalConditions(questionName, questionAswer, questionAswerSecond, questionVehiclePart);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }
}
