package com.cyberiansoft.test.ios10_client.testcases.hd.ordermonitor;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.monitor.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.OrderMonitorScreenValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.ordermonitorphases.OrderMonitorPhases;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IOSOrderMonitorTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Order Monitor Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getOrderMonitorTestCasesDataPath();
        _003_Test_Customer.setCompanyName("003 - Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();

        HomeScreenSteps.navigateToTeamWorkOrdersScreen();
        TeamWorkOrdersScreen teamWorkordersScreen = new TeamWorkOrdersScreen();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_MUST_SERVICE_PHASE_BEFORE_CHANGING_STATUS);
        serviceDetailsPopup.clickStartService();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        serviceDetailsPopup.setCompletedServiceStatus();
        List<String> statuses = orderMonitorScreen.getPanelsStatuses(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        for (String status : statuses)
            Assert.assertEquals(status, orderMonitorData.getMonitorServiceData().getMonitorServiceStatus());
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String dyeService = "Dye";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();

        HomeScreenSteps.navigateToTeamWorkOrdersScreen();
        TeamWorkOrdersScreen teamWorkordersScreen = new TeamWorkOrdersScreen();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        Assert.assertTrue(orderMonitorScreen.isRepairPhaseExists());
        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clicksRepairPhaseLine();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_MUST_START_PHASE_BEFORE_CHANGING_STATUS);
        orderMonitorScreen.clickStartPhase();

        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(dyeService);
        Assert.assertFalse(serviceDetailsPopup.isStartPhaseButtonPresent());
        Assert.assertTrue(serviceDetailsPopup.isServiceStartDateExists());

        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        orderMonitorScreen.clicksRepairPhaseLine();
        orderMonitorScreen.setCompletedPhaseStatus();
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(monitorServiceData.getMonitorService().getServiceName()), monitorServiceData.getMonitorServiceStatus());
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatStartDateIsSetWhenStartService(String rowID,
                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();

        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertFalse(serviceDetailsPopup.isServiceStartDateExists());
        serviceDetailsPopup.clickStartService();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertTrue(serviceDetailsPopup.isServiceStartDateExists());
        serviceDetailsPopup.clickServiceDetailsCancelButton();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String defaultLocationValue = "Test Location ZZZ";
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(defaultLocationValue);
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(defaultLocationValue);
        teamWorkordersScreen.clickSearchSaveButton();

        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        MonitorServiceData firstMonitorServiceData = orderMonitorData.getMonitorServicesData().get(0);
        Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
        TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
        techniciansPopup.selecTechnician(firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
        Assert.assertEquals(serviceDetailsPopup.getTechnicianValue(), firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        orderMonitorScreen.clickStartOrderButton();
        final LocalDate repairOrderDate = LocalDate.now();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER, repairOrderDate.format(df)));

        MonitorServiceData secondMonitorServiceData = orderMonitorData.getMonitorServicesData().get(1);
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.clickTech();
        techniciansPopup.selecTechnician(secondMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.clickStartService();
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.setCompletedServiceStatus();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(secondMonitorServiceData.getMonitorService()), secondMonitorServiceData.getMonitorServiceStatus());
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.clickTech();
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String defaultLocationValue = "Test Location ZZZ";
        final String workOrderMonitorStatus = "On Hold";
        final String statusReason = "On Hold new reason";

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(defaultLocationValue);
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(defaultLocationValue);
        teamWorkordersScreen.clickSearchSaveButton();

        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
        orderMonitorScreen.changeStatusForWorkOrder(workOrderMonitorStatus, statusReason);

        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
        serviceDetailsPopup.clickTech();
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsNotPossibleToChangeStatusForServiceOrPhaseWhenCheckOutRequired(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.waitServicesScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickStartPhase();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickStartPhaseCheckOutButton();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(workOrderData.getMatrixServiceData().getMatrixServiceName()),
                OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
        orderMonitorScreen.clickBackButton();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase(String rowID,
                                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();

        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
        Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
        serviceDetailsPopup.clickServiceDetailsDoneButton();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clickStartPhase();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        Assert.assertFalse(orderMonitorScreen.isStartPhaseButtonPresent());
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getVehiclePart() != null)
                ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
            ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            TechniciansPopupSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
            TechniciansPopupSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
            TechniciansPopupSteps.saveTechViewDetails();
            ServiceDetailsScreenSteps.saveServiceDetails();
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.setSearchWorkOrderNumber(workOrderNumber);
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        orderMonitorScreen.clickStartOrderButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getVehiclePart() != null)
                ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
            ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            TechniciansPopup techniciansPopup = new TechniciansPopup();
            techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
            techniciansPopup.unselecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            ServiceDetailsScreenSteps.saveServiceDetails();
            ServiceDetailsScreenSteps.saveServiceDetails();
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.setSearchWorkOrderNumber(workOrderNumber);
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData), OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());

        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getServicesList().get(workOrderData.getServicesList().size() - 1));
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_MUST_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickOrderStartDateButton();
        LocalDate date = LocalDate.now();
        orderMonitorScreen.setOrderStartYearValue(date.getYear() + 1);
        Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear()));
        orderMonitorScreen.setOrderStartYearValue(date.getYear() - 1);
        Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear() - 1));
        orderMonitorScreen.closeSelectorderDatePicker();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();

        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase(String rowID,
                                                                                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));

        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(0).getMonitorServicesData()) {
            OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService().getServiceName());
            Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
            serviceDetailsPopup.clickStartService();
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
            serviceDetailsPopup.setCompletedServiceStatus();
            OrderMonitorScreenValidations.verifyServicesStatus(monitorServiceData.getMonitorService(), OrderMonitorStatuses.COMPLETED);
        }

        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clickStartPhase();
        OrderMonitorData repairPhaseOrderMonitorData = workOrderData.getOrderMonitorsData().get(1);
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(repairPhaseOrderMonitorData.getMonitorServicesData().get(0).getMonitorService());
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        orderMonitorScreen.clicksRepairPhaseLine();
        orderMonitorScreen.setCompletedPhaseStatus();

        for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData())
            OrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorStatuses.COMPLETED);

        for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData()) {
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
            Assert.assertEquals(orderMonitorScreen.getPanelStatusInPopup(), OrderMonitorStatuses.COMPLETED.getValue());
            orderMonitorScreen.clickCancelServiceDetails();
        }
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
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

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.selectService(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnician() != null) {
                ServicesScreen servicesScreen = new ServicesScreen();
                servicesScreen.openServiceDetails(serviceData.getServiceName());
                ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                if (!serviceData.getServiceDefaultTechnician().isSelected())
                    TechniciansPopupSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
                if (serviceData.getServiceNewTechnician().isSelected())
                    TechniciansPopupSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
                TechniciansPopupSteps.saveTechViewDetails();
                ServiceDetailsScreenSteps.saveServiceDetails();
            }
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
        BaseUtils.waitABit(1000 * 20);

        for (Employee employee : allEmployees) {
            MainScreen mainScreeneen = homeScreen.clickLogoutButton();
            homeScreen = mainScreeneen.userLogin(employee.getEmployeeFirstName(), employee.getEmployeePassword());
            TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
            teamWorkordersScreen.clickSearchButton();
            teamWorkordersScreen.selectSearchLocation(locationFilterValue);
            teamWorkordersScreen.clickSearchSaveButton();
            teamWorkordersScreen.clickOnWO(workOrderNumber);
            OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
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
            NavigationSteps.navigateBackScreen();
            NavigationSteps.navigateBackScreen();
        }

        MainScreen mainScreeneen = homeScreen.clickLogoutButton();

        homeScreen = mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
        backofficeHeader.clickMonitorLink();
        backofficeHeader.clickMonitorLink();
        RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
        monitorpage.clickRepairOrdersLink();
        repairorderspage.makeSearchPanelVisible();
        repairorderspage.selectSearchLocation("Default Location");
        repairorderspage.selectSearchTimeframe("Custom");
        repairorderspage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        repairorderspage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        repairorderspage.setSearchWoNumber(workOrderNumber);
        repairorderspage.clickFindButton();

        VendorOrderServicesWebPage vendororderservicespage = new VendorOrderServicesWebPage(webdriver);
        repairorderspage.clickOnWorkOrderLinkInTable(workOrderNumber);
        vendororderservicespage.changeRepairOrderServiceVendor(serviceToChangeRepairOrderVendor, "Device Team");
        vendororderservicespage.waitABit(3000);
        Assert.assertEquals(vendororderservicespage.getRepairOrderServiceTechnician(serviceToChangeRepairOrderVendor), "Oksi User");
        DriverBuilder.getInstance().getDriver().quit();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();

        for (ServiceData serviceData : workOrderData.getServicesList()) {
            if (serviceData.getServiceDefaultTechnician().isSelected())
                Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
            else
                Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
        }

        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();

        mainScreeneen.userLogin(allEmployees.get(1).getEmployeeFirstName(), allEmployees.get(1).getEmployeePassword());
        homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);
        orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
        }

        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }
}
