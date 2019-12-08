package com.cyberiansoft.test.ios10_client.testcases.regular.workorders;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularSelectedServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSWorkOrdersServicesTestCases extends IOSRegularBaseTestCase {

    private WholesailCustomer Specific_Client = new WholesailCustomer();
    private WholesailCustomer ZAZ_Motors = new WholesailCustomer();

    @BeforeClass(description = "Work Orders Services Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getWorkOrdersServicesTestCasesDataPath();
        Specific_Client.setCompanyName("Specific_Client");
        ZAZ_Motors.setCompanyName("Zaz Motors");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateWorkOrderWithTeamSharingOption(String rowID,
                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrdersData().get(0);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePriceValue(workOrderData.getMoneyServiceData().getServicePrice());
        RegularServiceDetailsScreenValidations.verifyServiceAdjustmentsValue(workOrderData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        RegularServiceDetailsScreenSteps.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        // =====================================

        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePriceValue(workOrderData.getPercentageServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.clickAdjustmentsCell();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
                workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
        selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenValidations.verifyServiceAdjustmentsValue( workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        // =====================================
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : workOrderData.getBundleService().getServices()) {
            if (serviceData.isSelected()) {
                Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsSelected(serviceData.getServiceName()));
                selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.setServiceQuantityValue(serviceData.getServiceQuantity());
            } else {
                Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsNotSelected(serviceData.getServiceName()));
                selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
            }
        }
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenSteps.saveServiceDetails();


        // =====================================
        for (ServiceData serviceData : workOrderData.getPercentageServices()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        // =====================================
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        RegularVehiclePartsScreenSteps.selectVehiclePart(matrixServiceData.getVehiclePartData());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        Assert.assertEquals(vehiclePartScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        RegularVehiclePartsScreenSteps.saveVehiclePart();

        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreenValidations.verifyServicesAreSelected(workOrderData.getSelectedServices());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        RegularWorkOrdersSteps.saveWorkOrder();

        WorkOrderData workOrderDataCopiedServices = testCaseData.getWorkOrdersData().get(1);
        RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, Specific_Client, WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
        vehicleScreen.setVIN(workOrderDataCopiedServices.getVehicleInfoData().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreenValidations.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }
}
