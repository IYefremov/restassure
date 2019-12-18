package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.ServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCreateInspectionsTestCases extends IOSHDBaseTestCase {

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

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        final String inspNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
            visualInteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
            visualInteriorScreen.tapInterior();
            if (visualScreenData.getScreenTotalPrice() != null)
                Assert.assertEquals(visualInteriorScreen.getTotalAmaunt(), visualScreenData.getScreenTotalPrice());
        }
        for (QuestionScreenData questionScreenData : inspectionData.getQuestionScreensData())
            QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName()));
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenValidations.verifyServicePrice(inspectionData.getMoneyServiceData().getServicePrice());
        ServiceDetailsScreenValidations.verifyServiceDetailsAdjustmentValue(inspectionData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName()));
        // =====================================
        servicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getPercentageServiceData().getServiceName()));
        servicesScreen.openServiceDetails(inspectionData.getPercentageServiceData().getServiceName());
        ServiceDetailsScreenValidations.verifyServicePrice(inspectionData.getPercentageServiceData().getServicePrice());
        selectedServiceDetailsScreen.clickAdjustments();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
                inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
        selectedServiceDetailsScreen.selectAdjustment(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        ServiceDetailsScreenValidations.verifyServiceDetailsAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getPercentageServiceData().getServiceName()));
        // =====================================
        ServicesScreenSteps.selectBundleService(inspectionData.getBundleService());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
        // =====================================
        for (ServiceData serviceData : inspectionData.getPercentageServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        // =====================================
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
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
        servicesScreen.saveWizard();
        myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.selectInspectionForCopy(inspNumber);
        vehicleScreen = new VehicleScreen();
        final String copiedInspection = vehicleScreen.getInspectionNumber();
        Assert.assertEquals(vehicleScreen.getMake(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), inspectionData.getVehicleInfo().getVehicleModel());

        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
            visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
        }

        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreenSteps.verifyServicesAreSelected(inspectionData.getSelectedServices());

        NavigationSteps.navigateToScreen(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
        QuestionsScreen questionScreen = new QuestionsScreen();
        questionScreen.waitQuestionsScreenLoaded();
        SinglePageInspectionScreen singlePageInspectionScreen = new SinglePageInspectionScreen();
        Assert.assertTrue(singlePageInspectionScreen.isSignaturePresent());
        singlePageInspectionScreen.selectNextScreen("BATTERY PERFORMANCE");
        vehicleScreen.swipeScreenRight();
        Assert.assertTrue(singlePageInspectionScreen.isAnswerPresent("Test Answer 1"));
        servicesScreen.clickSave();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(copiedInspection), inspectionData.getInspectionPrice());
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavingInspectionsWithThreeMatrix(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.VITALY_TEST_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setType(inspectionData.getVehicleInfo().getVehicleType());
        vehicleScreen.setPO(inspectionData.getVehicleInfo().getPoNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimScreen.setClaim(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claimScreen.setAccidentDate();

        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
            visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
            if (visualScreenData.getDamagesData() != null) {
                visualInteriorScreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
                visualInteriorScreen.tapInteriorWithCoords(1);
                visualInteriorScreen.tapInteriorWithCoords(2);
            } else {
                visualInteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
                VisualInteriorScreen.tapExteriorWithCoords(100, 500);
            }
            Assert.assertEquals(visualInteriorScreen.getTotalAmaunt(), visualScreenData.getScreenTotalPrice());
            Assert.assertEquals(visualInteriorScreen.getSubTotalAmaunt(), visualScreenData.getScreenPrice());
        }
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
        }

        NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.selectService(inspectionData.getServiceData().getServiceName());

        NavigationSteps.navigateToScreen(inspectionData.getVisualScreenData().getScreenName());
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
        visualInteriorScreen.tapCarImage();
        Assert.assertEquals(visualInteriorScreen.getTotalAmaunt(), inspectionData.getVisualScreenData().getScreenTotalPrice());
        Assert.assertEquals(visualInteriorScreen.getSubTotalAmaunt(), inspectionData.getVisualScreenData().getScreenPrice());
        visualInteriorScreen.saveWizard();
        myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
        vehicleScreen.waitVehicleScreenLoaded();

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                PriceMatrixScreenSteps.verifyIfVehiclePartContainsPriceValue(vehiclePartData);
            }
        }

        servicesScreen.cancelWizard();
        myInspectionsScreen.selectInspectionForCopy(inspectionNumber);
        String copiedinspectionNumber = vehicleScreen.getInspectionNumber();
        servicesScreen.saveWizard();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(copiedinspectionNumber));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
                                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        VehicleInfoScreenSteps.verifyMakeModelyearValues(inspectionData.getVehicleInfo());
        String inspnum = vehicleScreen.getInspectionNumber();
        PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        vehicleScreen.saveWizard();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspnum));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
        servicesScreen.saveWizard();

        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        int i = 0;
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
            servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            i++;
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
            Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(), vehiclePartData.getVehiclePartName());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromInvoiceWithTwoWOs(String rowID, String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, _002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        NavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectDefaultInvoiceType();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.addWorkOrder(workOrderNumber1);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        NavigationSteps.navigateBackScreen();
    }
}
