package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SinglePageInspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularSelectedServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCreateInspectionsTestCases extends IOSRegularBaseTestCase {

    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Create Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCreateInspectionsTestCasesDataPath();
        _002_Test_Customer.setCompanyName("002 - Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCopyInspections(String rowID,
                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspNumber = vehicleScreen.getInspectionNumber();
        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
            visualinteriorScreen.clickServicesToolbarButton();
            visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
            Helpers.tapRegularCarImage();
            visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
            if (visualScreenData.getScreenTotalPrice() != null)
                Assert.assertEquals(visualinteriorScreen.getTotalPrice(), visualScreenData.getScreenTotalPrice());
        }

        for (QuestionScreenData questionScreenData : inspectionData.getQuestionScreensData())
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePriceValue(inspectionData.getMoneyServiceData().getServicePrice());
        RegularServiceDetailsScreenValidations.verifyServiceAdjustmentsValue(inspectionData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        // =====================================
        RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getPercentageServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePriceValue(inspectionData.getPercentageServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.clickAdjustmentsCell();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
                inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
        RegularServiceDetailsScreenSteps.selectServiceAdjustment(inspectionData.getPercentageServiceData().getServiceAdjustment());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenValidations.verifyServiceAdjustmentsValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        // =====================================
        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        RegularServicesScreenSteps.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
        }
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        // =====================================
        for (ServiceData serviceData : inspectionData.getPercentageServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        // =====================================
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
        vehiclePartScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        Assert.assertEquals(vehiclePartScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        vehiclePartScreen.saveVehiclePart();

        priceMatrixScreen.clickSave();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreenValidations.verifyServicesAreSelected(inspectionData.getSelectedServices());
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForCopy(inspNumber);
        Assert.assertEquals(vehicleScreen.getMake(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), inspectionData.getVehicleInfo().getVehicleModel());

        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
            visualinteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
        }
        RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
        RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
        visualinteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
        SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen();
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
        RegularNavigationSteps.navigateToScreen("BATTERY PERFORMANCE");
        questionsScreen.swipeScreenUp();
        questionsScreen.swipeScreenUp();
        Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
        RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreenValidations.verifyServicesAreSelected(inspectionData.getSelectedServices());

        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavingInspectionsWithThreeMatrix(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.VITALY_TEST_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setType(inspectionData.getVehicleInfo().getVehicleType());
        vehicleScreen.setPO(inspectionData.getVehicleInfo().getPoNumber());
        String inspectionNumberber = vehicleScreen.getInspectionNumber();

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimScreen.setClaim(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claimScreen.setAccidentDate();

        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
            visualinteriorScreen.clickServicesToolbarButton();
            if (visualScreenData.getDamagesData() != null) {
                visualinteriorScreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
                RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
                RegularVisualInteriorScreen.tapInteriorWithCoords(150, 150);
                visualinteriorScreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
            } else {
                visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
                RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
                visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
            }
            Assert.assertEquals(visualinteriorScreen.getSubTotalPrice(), visualScreenData.getScreenPrice());
            Assert.assertEquals(visualinteriorScreen.getTotalPrice(), visualScreenData.getScreenTotalPrice());
        }
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToPriceMatrixScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreenSteps.selectPriceMatrixData(priceMatrixScreenData);
        }

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectService(inspectionData.getServiceData().getServiceName());

        RegularNavigationSteps.navigateToScreen(inspectionData.getVisualScreenData().getScreenName());
        RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
        visualinteriorScreen.clickServicesToolbarButton();
        visualinteriorScreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
        Helpers.tapRegularCarImage();
        visualinteriorScreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
        Assert.assertEquals(visualinteriorScreen.getSubTotalPrice(), inspectionData.getVisualScreenData().getScreenPrice());
        Assert.assertEquals(visualinteriorScreen.getTotalPrice(), inspectionData.getVisualScreenData().getScreenTotalPrice());
        RegularInspectionsSteps.saveInspection();

        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumberber);
        vehicleScreen.waitVehicleScreenLoaded();

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                RegularVehiclePartsScreenSteps.verifyIfVehiclePartPriceValue(vehiclePartData);
            }
        }
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularMyInspectionsSteps.selectInspectionForCopy(inspectionNumberber);
        vehicleScreen.waitVehicleScreenLoaded();
        final String copiedinspectionNumberber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(copiedinspectionNumberber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
                                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(inspectionData.getVehicleInfo());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToPriceMatrixScreen(inspectionData.getPriceMatrixScreenData().getMatrixScreenName());
        RegularPriceMatrixScreenSteps.selectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
        //RegularVehiclePartsScreenSteps.saveVehiclePart();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspectionNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        int i = 0;
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
            selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            i++;
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(), vehiclePartData.getVehiclePartName());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromInvoiceWithTwoWOs(String rowID, String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServiceDetailsDataAndSave(workOrderData.getMoneyServiceData());
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, _002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        orderSummaryScreen.selectDefaultInvoiceType();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.addWorkOrder(workOrderNumber1);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
    }
}
