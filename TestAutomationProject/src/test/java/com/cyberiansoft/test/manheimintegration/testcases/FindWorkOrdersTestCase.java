package com.cyberiansoft.test.manheimintegration.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.manheimintegration.data.ManheimTestCasesDataPaths;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.ProblemReportingSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class FindWorkOrdersTestCase extends BaseTestClass {

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = ManheimTestCasesDataPaths.getInstance().getFindWorkOrdersTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTheWOsAreAvailableOnBO(String rowID,
                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();
        WebDriver chromeDriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
        chromeDriver.get("https://manheim-uat.cyberianconcepts.com/");
        VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(workOrdersData.get(0).getMonitoring().getLocation());

        for (WorkOrderData workOrderData : workOrdersData) {
            Monitoring monitoringData = workOrderData.getMonitoring();
            String stockNumber = monitoringData.getStockNumber();

            VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
            VNextBOROAdvancedSearchDialogInteractions.setStockNum(stockNumber);
            VNextBOROAdvancedSearchDialogInteractions.setRepairStatus("All");
            VNextBOROAdvancedSearchDialogSteps.search();
            VNextBOROPageValidations.validateWorkOrderDisplayedByStockNumber(stockNumber, true);
        }
        chromeDriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTheWOsAreAvailableOnMobileDevice(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();

        for (WorkOrderData workOrderData : workOrdersData) {
            Monitoring monitoringData = workOrderData.getMonitoring();
            String stockNumber = monitoringData.getStockNumber();
            HomeScreenSteps.openUpdateWork();
            UpdateWorkSteps.searchRepairOrder(stockNumber);
            PhaseScreenValidations.validatePhaseStockNumber(stockNumber);
            WizardScreenSteps.saveAction();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTheApprovedServicesMustHaveActiveStatus(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        userCanFindWorkOrdersOnBOAndDevice(workOrderData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTheDeclinedServicesMustHaveRefusedStatus(String rowID,
                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        userCanFindWorkOrdersOnBOAndDevice(workOrderData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyVerifyThatApprovedHasActiveStatusAndDeclinedHasRefusedStatus(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        userCanFindWorkOrdersOnBOAndDevice(workOrderData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyVerifyThatServicesHasOppositeToPreviousStatus(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        userCanFindWorkOrdersOnBOAndDevice(workOrderData);
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCompletePhasesDevice(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();
        String stockNumber = monitoringData.getStockNumber();

        HomeScreenSteps.openUpdateWork();
        UpdateWorkSteps.searchRepairOrder(stockNumber);
        monitoringData.getOrderPhasesDto().forEach(phaseDTO -> phaseDTO.getPhaseServices().forEach(service -> {
            PhaseScreenSteps.startService(service.getMonitorService());
            PhaseScreenSteps.completeService(service.getMonitorService());
        }));

        monitoringData.getOrderPhasesDto().forEach(PhaseScreenValidations::validatePhaseStatus);
        WizardScreenSteps.saveAction();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanResolveProblemOnServiceLevelOnDevice(String rowID,
                                                    String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        ServiceData serviceDto = workOrderData.getServiceData();

        HomeScreenSteps.openUpdateWork();
        UpdateWorkSteps.searchRepairOrder(workOrderData.getMonitoring().getStockNumber());
        PhaseScreenSteps.startService(serviceDto);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(serviceDto.getProblemReason());
        serviceDto.setServiceStatus(ServiceStatus.PROBLEM);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.RESOLVE_PROBLEM);
        ProblemReportingSteps.resolveProblem();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        WizardScreenSteps.saveAction();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanResolveProblemOnServiceLevelOnBackOffice(String rowID,
                                                            String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();
        OrderPhaseDto phaseDto = monitoringData.getOrderPhaseDto();
        ServiceData serviceDto = phaseDto.getPhaseServices().get(0).getMonitorService();

        HomeScreenSteps.openUpdateWork();
        UpdateWorkSteps.searchRepairOrder(workOrderData.getMonitoring().getStockNumber());
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(serviceDto.getProblemReason());
        serviceDto.setServiceStatus(ServiceStatus.PROBLEM);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        WizardScreenSteps.saveAction();

        WebDriver chromeDriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
        chromeDriver.get("https://manheim-uat.cyberianconcepts.com/");
        VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(monitoringData.getLocation());
        VNextBOROPageStepsNew.searchOrdersByStockNumber(monitoringData.getStockNumber());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.expandPhaseByName(phaseDto.getPhaseName());
        VNextBORODetailsStepsNew.resolveProblemForService(serviceDto.getServiceName());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBORODetailsValidationsNew.verifyServiceStatusIsCorrect(serviceDto.getServiceName(), "Active");
        VNextBORODetailsStepsNew.collapsePhaseByName(phaseDto.getPhaseName());
        chromeDriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanStartStopCompleteServices(String rowID,
                                                            String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();

        HomeScreenSteps.openUpdateWork();
        UpdateWorkSteps.searchRepairOrder(workOrderData.getMonitoring().getStockNumber());
        monitoringData.getOrderPhasesDto().forEach(phaseDto -> phaseDto.getPhaseServices().forEach(monitorServiceDTO -> {
            if (monitorServiceDTO.getMonitorServiceStatus().equals(ServiceStatus.ACTIVE.getStatus()))
                PhaseScreenInteractions.selectService(monitorServiceDTO.getMonitorService());
        }));
        PhaseScreenSteps.startServices();
        //Wait 1 minute
        BaseUtils.waitABit(60*1000);
        monitoringData.getOrderPhasesDto().forEach(phaseDto -> phaseDto.getPhaseServices().forEach(monitorServiceDTO -> {
            if (monitorServiceDTO.getMonitorServiceStatus().equals(ServiceStatus.ACTIVE.getStatus()))
                PhaseScreenInteractions.selectService(monitorServiceDTO.getMonitorService());
        }));
        PhaseScreenSteps.stopServices();
        //Wait 1 minute
        BaseUtils.waitABit(60*1000);
        monitoringData.getOrderPhasesDto().forEach(phaseDto -> phaseDto.getPhaseServices().forEach(monitorServiceDTO -> {
            if (monitorServiceDTO.getMonitorServiceStatus().equals(ServiceStatus.ACTIVE.getStatus()))
                PhaseScreenInteractions.selectService(monitorServiceDTO.getMonitorService());
        }));
        PhaseScreenSteps.completeServices();
        monitoringData.getOrderPhasesDto().forEach(phaseDto -> phaseDto.getPhaseServices().forEach(monitorServiceDTO -> {
            if (monitorServiceDTO.getMonitorServiceStatus().equals(ServiceStatus.ACTIVE.getStatus()))
                PhaseScreenValidations.validateServiceStatus(monitorServiceDTO.getMonitorService(), ServiceStatus.COMPLETED);
        }));
        WizardScreenSteps.saveAction();
    }

    private void userCanFindWorkOrdersOnBOAndDevice(WorkOrderData workOrderData) {
        Monitoring monitoringData = workOrderData.getMonitoring();
        String stockNumber = monitoringData.getStockNumber();

        HomeScreenSteps.openUpdateWork();
        UpdateWorkSteps.searchRepairOrder(stockNumber);
        PhaseScreenValidations.validatePhaseStockNumber(stockNumber);
        monitoringData.getOrderPhasesDto().forEach(phaseDto -> {
            PhaseScreenValidations.validatePhaseStatus(phaseDto);
            phaseDto.getPhaseServices().forEach(monitorServiceDTO ->
                PhaseScreenValidations.validateServiceStatus(monitorServiceDTO.getMonitorService(), ServiceStatus.getStatus(monitorServiceDTO.getMonitorServiceStatus()))
            );
        });

        WizardScreenSteps.saveAction();
    }
}
