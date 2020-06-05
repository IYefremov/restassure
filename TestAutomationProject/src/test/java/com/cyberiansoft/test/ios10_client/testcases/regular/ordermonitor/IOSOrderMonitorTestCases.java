package com.cyberiansoft.test.ios10_client.testcases.regular.ordermonitor;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.monitor.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularOrderMonitorScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.ordermonitorphases.OrderMonitorPhases;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class IOSOrderMonitorTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Order Monitor Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getOrderMonitorTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertTrue(orderMonitorScreen.isStartServiceButtonPresent());
        orderMonitorScreen.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_MUST_SERVICE_PHASE_BEFORE_CHANGING_STATUS);
        orderMonitorScreen.clickStartService();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        orderMonitorScreen.setCompletedServiceStatus();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName()), orderMonitorData.getMonitorServiceData().getMonitorServiceStatus());
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String dyeService = "Dye";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();

        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertTrue(orderMonitorScreen.isRepairPhaseExists());
        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonExists());
        orderMonitorScreen.clicksRepairPhaseLine();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_MUST_START_PHASE_BEFORE_CHANGING_STATUS);
        orderMonitorScreen.clickStartPhaseButton();

        orderMonitorScreen.selectPanel(dyeService);
        Assert.assertFalse(orderMonitorScreen.isStartServiceButtonPresent());
        Assert.assertTrue(orderMonitorScreen.isServiceStartDateExists());

        orderMonitorScreen.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        orderMonitorScreen.clickServiceDetailsDoneButton();

        orderMonitorScreen.clicksRepairPhaseLine();
        orderMonitorScreen.clickCompletedPhaseCell();

        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(monitorServiceData.getMonitorService().getServiceName()), monitorServiceData.getMonitorServiceStatus());
        RegularNavigationSteps.navigateBackScreen();
        RegularTeamWorkOrdersSteps.waitTeamWorkOrdersScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatStartDateIsSetWhenStartService(String rowID,
                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertFalse(orderMonitorScreen.isServiceStartDateExists());
        orderMonitorScreen.clickStartService();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertTrue(orderMonitorScreen.isServiceStartDateExists());
        orderMonitorScreen.clickServiceDetailsDoneButton();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String defaultLocationValue = "Test Location ZZZ";
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d, yyyy");


        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(defaultLocationValue);
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(defaultLocationValue);
        teamWorkOrdersScreen.clickSearchSaveButton();

        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        MonitorServiceData firstMonitorServiceData = orderMonitorData.getMonitorServicesData().get(0);
        orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
        orderMonitorScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selecTechnician(firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularOrderMonitorServiceDetailsScreen monitorServiceDetailsScreen = new RegularOrderMonitorServiceDetailsScreen();
        monitorServiceDetailsScreen.clickServiceDetailsDoneButton();
        orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
        Assert.assertEquals(orderMonitorScreen.getServiceTechnicianValue(), firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        orderMonitorScreen.clickServiceDetailsDoneButton();

        orderMonitorScreen.clickStartOrderButton();
        final LocalDate repairOrderDate = LocalDate.now();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER, repairOrderDate.format(df)));

        MonitorServiceData secondMonitorServiceData = orderMonitorData.getMonitorServicesData().get(1);
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        orderMonitorScreen.clickTech();
        selectedServiceDetailsScreen.selecTechnician(secondMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        monitorServiceDetailsScreen.clickServiceDetailsDoneButton();
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        orderMonitorScreen.clickStartService();
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        orderMonitorScreen.setCompletedServiceStatus();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(secondMonitorServiceData.getMonitorService()), secondMonitorServiceData.getMonitorServiceStatus());
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        orderMonitorScreen.clickTech();
        orderMonitorScreen.clickServiceDetailsDoneButton();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularTeamWorkOrdersSteps.waitTeamWorkOrdersScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String defaultLocationValue = "Test Location ZZZ";
        final String workOrderMonitorStatus = "On Hold";
        final String statusReason = "On Hold new reason";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(defaultLocationValue);
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(defaultLocationValue);
        teamWorkOrdersScreen.clickSearchSaveButton();

        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.changeStatusForWorkOrder(workOrderMonitorStatus, statusReason);
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
        orderMonitorScreen.clickTech();

        orderMonitorScreen.clickServiceDetailsCancelButton();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsNotPossibleToChangeStatusForServiceOrPhaseWhenCheckOutRequired(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        orderMonitorScreen.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        orderMonitorScreen.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickStartPhase();
        orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        orderMonitorScreen.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        orderMonitorScreen.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickStartPhaseCheckOutButton();
        orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        orderMonitorScreen.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        orderMonitorScreen.clickServiceDetailsCancelButton();

        orderMonitorScreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(workOrderData.getMatrixServiceData().getMatrixServiceName()),
                OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
        orderMonitorScreen.clickBackButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase(String rowID,
                                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
        Assert.assertTrue(orderMonitorScreen.isStartServiceButtonPresent());
        orderMonitorScreen.clickServiceDetailsDoneButton();

        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonExists());
        orderMonitorScreen.clickStartPhaseButton();
        Assert.assertFalse(orderMonitorScreen.isStartPhaseButtonExists());
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getVehiclePart() != null)
                RegularServiceDetailsScreenSteps.selectServiceVehiclePart(serviceData.getVehiclePart());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
            RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();

        orderMonitorScreen.clickStartOrderButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
        orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getVehiclePart() != null)
                RegularServiceDetailsScreenSteps.selectServiceVehiclePart(serviceData.getVehiclePart());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
            RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData),
                    OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
        orderMonitorScreen.selectPanel(workOrderData.getServicesList().get(workOrderData.getServicesList().size() - 1));
        orderMonitorScreen.clickServiceStatusCell();

        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_MUST_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
        orderMonitorScreen.clickServiceDetailsCancelButton();
        orderMonitorScreen.clickOrderStartDateButton();
        LocalDate date = LocalDate.now();
        orderMonitorScreen.setOrderStartYearValue(date.getYear() + 1);
        Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear()));
        orderMonitorScreen.setOrderStartYearValue(date.getYear() - 1);
        Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear() - 1));
        orderMonitorScreen.closeSelectorderDatePicker();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase(String rowID,
                                                                                                                                                     String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        }

        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(0).getMonitorServicesData()) {
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService().getServiceName());
            Assert.assertTrue(orderMonitorScreen.isStartServiceButtonPresent());
            orderMonitorScreen.clickStartService();
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
            orderMonitorScreen.setCompletedServiceStatus();
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);
        }

        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clickStartPhase();
        OrderMonitorData repairPhaseOrderMonitorData = workOrderData.getOrderMonitorsData().get(1);
        orderMonitorScreen.selectPanel(repairPhaseOrderMonitorData.getMonitorServicesData().get(0).getMonitorService());
        orderMonitorScreen.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        orderMonitorScreen.clickServiceDetailsDoneButton();

        orderMonitorScreen.clicksRepairPhaseLine();
        orderMonitorScreen.setCompletedPhaseStatus();

        for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);

        for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData()) {
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
            Assert.assertEquals(orderMonitorScreen.getServiceStatusInPopup(monitorServiceData.getMonitorService().getServiceName()), OrderMonitorStatuses.COMPLETED.getValue());
            ///orderMonitorScreen.waitOrderMonitorScreenLoaded();
        }

        orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.waitTeamWorkOrdersScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String locationFilterValue = "All locations";
        final String serviceToChangeRepairOrderVendor = "Dye";

        Employee defEmployee = new Employee();
        defEmployee.setEmployeeFirstName("Employee");
        defEmployee.setEmployeeLastName("Simple 20%");
        defEmployee.setEmployeePassword("12345");

        Employee defZayats = new Employee();
        defZayats.setEmployeeFirstName("Oksana");
        defZayats.setEmployeeLastName("Zayats");
        defZayats.setEmployeePassword("1111");

        Employee defInspector = new Employee();
        defInspector.setEmployeeFirstName("Inspector");
        defInspector.setEmployeeLastName("1");
        defInspector.setEmployeePassword("12345");

        ArrayList<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(defEmployee);
        allEmployees.add(defZayats);
        allEmployees.add(defInspector);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            if (serviceData.getServiceNewTechnician() != null) {
                RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                if (!serviceData.getServiceDefaultTechnician().isSelected())
                    RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
                if (serviceData.getServiceNewTechnician().isSelected())
                    RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else
                RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        RegularNavigationSteps.navigateBackScreen();

        BaseUtils.waitABit(20 * 1000);
        for (Employee employee : allEmployees) {
            RegularHomeScreenSteps.logoutUser();
            RegularMainScreenSteps.userLogin(employee.getEmployeeFirstName(), employee.getEmployeePassword());
            RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
            RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
            teamWorkOrdersScreen.clickSearchButton();
            teamWorkOrdersScreen.selectSearchLocation(locationFilterValue);
            teamWorkOrdersScreen.clickSearchSaveButton();
            RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
            RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
            orderMonitorScreen.checkMyWorkCheckbox();
            for (ServiceData serviceData : workOrderData.getServicesList()) {
                if (serviceData.getServiceDefaultTechnician().getTechnicianFullName().equals(employee.getEmployeeName())) {
                    if (serviceData.getServiceDefaultTechnician().isSelected())
                        Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                    else
                        Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                } else if (serviceData.getServiceNewTechnician() == null) {
                    Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                } else if (serviceData.getServiceNewTechnician().getTechnicianFullName().equals(employee.getEmployeeName())) {
                    if (serviceData.getServiceNewTechnician().isSelected())
                        Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                    else
                        Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                } else {
                    Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                }
            }
            RegularNavigationSteps.navigateBackScreen();
            RegularNavigationSteps.navigateBackScreen();
        }

        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
        BaseUtils.waitABit(1000);
        backOfficeHeaderPanel.clickMonitorLink();
        RepairOrdersWebPage repairOrdersWebPage = new RepairOrdersWebPage(webdriver);
        monitorWebPage.clickRepairOrdersLink();
        repairOrdersWebPage.makeSearchPanelVisible();
        repairOrdersWebPage.setSearchWoNumber(workOrderNumber);
        repairOrdersWebPage.selectSearchLocation("Default Location");

        repairOrdersWebPage.selectSearchTimeframe("Custom");
        repairOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        repairOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        repairOrdersWebPage.clickFindButton();

        VendorOrderServicesWebPage vendorOrderServicesWebPage = new VendorOrderServicesWebPage(webdriver);
        repairOrdersWebPage.clickOnWorkOrderLinkInTable(workOrderNumber);
        vendorOrderServicesWebPage.changeRepairOrderServiceVendor(serviceToChangeRepairOrderVendor, "Device Team");
        vendorOrderServicesWebPage.waitABit(3000);
        Assert.assertEquals(vendorOrderServicesWebPage.getRepairOrderServiceTechnician(serviceToChangeRepairOrderVendor), "Oksi User");
        DriverBuilder.getInstance().getDriver().quit();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            if (serviceData.getServiceDefaultTechnician().isSelected())
                Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
            else
                Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
        }
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();

        RegularMainScreenSteps.userLogin(allEmployees.get(1).getEmployeeFirstName(), allEmployees.get(1).getEmployeePassword());
        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
        }
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

}
