package com.cyberiansoft.test.ios10_client.testcases.hd.workorders;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.MyWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServicesScreenSteps;
import com.cyberiansoft.test.ios10_client.hdvalidations.ServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSWorkOrdersServicesTestCases extends IOSHDBaseTestCase {

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

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();

        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();

        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenValidations.verifyServicePrice(workOrderData.getMoneyServiceData().getServicePrice());
        ServiceDetailsScreenValidations.verifyServiceDetailsAdjustmentValue(workOrderData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
        ServiceDetailsScreenSteps.saveServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        // =====================================
        servicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());

        servicesScreen.openServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        ServiceDetailsScreenValidations.verifyServicePrice(workOrderData.getPercentageServiceData().getServicePrice());
        selectedServiceDetailsScreen.clickAdjustments();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
                workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
        selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        ServiceDetailsScreenValidations.verifyServiceDetailsAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        ServiceDetailsScreenSteps.saveServiceDetails();
        // =====================================
        servicesScreen.selectService(workOrderData.getBundleService().getBundleServiceName());
        SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
        for (ServiceData serviceData : workOrderData.getBundleService().getServices()) {
            if (serviceData.isSelected()) {
                Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(serviceData.getServiceName()));
                selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
                selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
            } else {
                Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(serviceData.getServiceName()));
                selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
            }
        }
        ServiceDetailsScreenSteps.saveServiceDetails();
        ServiceDetailsScreenSteps.saveServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
        // =====================================
        for (ServiceData serviceData : workOrderData.getPercentageServices()) {
            servicesScreen.selectService(serviceData.getServiceName());
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        }
        // =====================================
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        PriceMatrixScreen priceMatrixScreen = servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
        priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
        priceMatrixScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        Assert.assertEquals(priceMatrixScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
        Assert.assertTrue(priceMatrixScreen.isNotesExists());
        for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
            priceMatrixScreen.selectDiscaunt(serviceData.getServiceName());
        }
        priceMatrixScreen.clickSaveButton();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        for (ServiceData serviceData : workOrderData.getSelectedServices()) {
            Assert.assertTrue(orderSummaryScreen.checkServiceIsSelected(serviceData.getServiceName()));
        }
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        orderSummaryScreen.saveWizard();

        WorkOrderData workOrderDataCopiedServices = testCaseData.getWorkOrdersData().get(1);
        MyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, Specific_Client,
                WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
        vehicleScreen.setVIN(workOrderDataCopiedServices.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
        NavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
        orderSummaryScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
    }


}
