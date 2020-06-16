package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.r360.RepairOrdersSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.RepairOrdersCommonFiltersPageSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.*;
import com.cyberiansoft.test.vnext.validations.monitor.RepairOrderInfoValidations;
import com.cyberiansoft.test.vnext.validations.monitor.RepairOrdersCommonFiltersPageValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VNextTeamMonitoringBaseCase extends BaseTestClass {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Base Case")
    public void beforeClass() throws IOException {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();
        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(false);
        roleSettingsDTO.setMonitorCanEditService(true);
        roleSettingsDTO.setMonitorCanRemoveService(false);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanViewOrderInfoViaEdit(String rowID,
                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderInfoDto expectedOrderInfo = workOrderData.getMonitoring().getOrderInfoDto();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation(workOrderData.getMonitoring().getLocation());
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToInfo();
        expectedOrderInfo.setStartDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        RepairOrderInfoValidations.verifyOrderInfo(expectedOrderInfo);
        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCompleteTheOrder(String rowID,
                                                  String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Employee locationManagerEmployee = new Employee();
        locationManagerEmployee.setEmployeeFirstName("Oleg");
        locationManagerEmployee.setEmployeeLastName("Romanchuk");
        locationManagerEmployee.setEmployeePassword("54321");

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_WO_MONITOR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectServices(workOrderData.getDamageData().getMoneyServices());
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(1000);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation(workOrderData.getMonitoring().getLocation());
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);

        MenuSteps.selectMenuItem(MenuItems.START_RO);
        InformationDialogValidations.clickStartAndVerifyMessage(VNextAlertMessages.START_REPAIR_ORDER);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        MonitorValidations.verifyRepairOrderValues(workOrderId, workOrderData.getMonitoring().getRepairOrderData());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddTheNoteUsingQuickNotesInputField(String rowID,
                                                                     String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String quickNotes = "Warranty expired";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_WO_MONITOR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectServices(workOrderData.getDamageData().getMoneyServices());
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(1000);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation(workOrderData.getMonitoring().getLocation());
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);

        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.addNote();
        NotesSteps.addQuickNote(quickNotes);
        ScreenNavigationSteps.pressBackButton();
        NotesValidations.verifyNotePresentInList(quickNotes);
        ScreenNavigationSteps.pressBackButton();

        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesValidations.verifyNotePresentInList(quickNotes);
        ScreenNavigationSteps.pressBackButton();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSelectTechnicianOnTheAssignTechPage(String rowID,
                                                                     String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RetailCustomer technician = new RetailCustomer();
        technician.setFirstName("1111");
        technician.setLastName("2222");

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_WO_MONITOR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectServices(workOrderData.getDamageData().getMoneyServices());
        ScreenNavigationSteps.pressBackButton();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation(workOrderData.getMonitoring().getLocation());
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START_RO);
        InformationDialogValidations.clickStartAndVerifyMessage(VNextAlertMessages.START_REPAIR_ORDER);
        MonitorSteps.openItem(workOrderId);

        MenuSteps.selectMenuItem(MenuItems.ASSIGN_TECH);
        GeneralListSteps.selectListItem(technician.getFullName());

        MonitorSteps.openItem(workOrderId);

        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenValidations.validateServiceTechnician(workOrderData.getServiceData());
        WizardScreenSteps.saveAction();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyCommonFiltersOpenAsSeparatePage(String rowID, String description, JSONObject testData) throws Exception {

        final String DEFAULT_FIELD_VALUES = "src/test/java/com/cyberiansoft/test/vnext/data/r360pro/monitoring/default-common-filters-fields-values.json";
        RepairOrdersSearchData searchData = JSonDataParser.getTestDataFromJson(testData, RepairOrdersSearchData.class);
        RepairOrdersSearchData defaultFieldValues = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(DEFAULT_FIELD_VALUES), RepairOrdersSearchData.class);
        HomeScreenSteps.openMonitor();

        //todo: Rapair Order search timout 30 sec
        BaseUtils.waitABit(45*1000);
        MonitorSteps.openCommonFiltersPage();
        RepairOrdersCommonFiltersPageValidations.verifyCommonFiltersScreenHasAllElements();
        RepairOrdersCommonFiltersPageValidations.verifyTimeFrameDropDownContainsCorrectOptions();
        RepairOrdersCommonFiltersPageValidations.verifyPriorityDropDownContainsCorrectOptions();
        RepairOrdersCommonFiltersPageSteps.setAllSearchFields(searchData);
        RepairOrdersCommonFiltersPageValidations.verifyAllFieldsContainCorrectValues(searchData);
        SearchSteps.search();
        MonitorValidations.verifyRepairOrdersScreenIsOpenedWithOrders();
        MonitorSteps.openCommonFiltersPage();
        RepairOrdersCommonFiltersPageSteps.clearFilters();
        RepairOrdersCommonFiltersPageValidations.verifyAllFieldsContainCorrectValues(defaultFieldValues);
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyActionMenuIsOpenedWithoutLoadingSpinner(String rowID, String description, JSONObject testData) {

        HomeScreenSteps.openMonitor();
        MonitorSteps.tapOnFirstOrder();
        MenuValidations.verifyMenuScreenIsOpened();
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySearchCanBePerformedByPriorityAndReturnBack(String rowID, String description, JSONObject testData) throws Exception {

        RepairOrdersSearchData searchData = JSonDataParser.getTestDataFromJson(testData, RepairOrdersSearchData.class);
        HomeScreenSteps.openMonitor();
        //todo: Rapair Order search timout 30 sec
        BaseUtils.waitABit(45*1000);
        MonitorSteps.openCommonFiltersPage();
        RepairOrdersCommonFiltersPageValidations.verifyPriorityDropDownContainsCorrectOptions();
        RepairOrdersCommonFiltersPageSteps.selectPriority(searchData.getPriority());
        RepairOrdersCommonFiltersPageValidations.verifyPriorityFieldContainsCorrectValue(searchData.getPriority());
        RepairOrdersCommonFiltersPageSteps.tapSearchButton();
        MonitorValidations.verifyRepairOrdersScreenIsOpenedWithOrders();
        MonitorValidations.verifySearchMaskContainsSearchValue(searchData.getPriority());
        MonitorSteps.clearSearchFilters();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}
