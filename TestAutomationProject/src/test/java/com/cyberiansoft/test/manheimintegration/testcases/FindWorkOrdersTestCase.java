package com.cyberiansoft.test.manheimintegration.testcases;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.manheimintegration.data.ManheimTestCasesDataPaths;
import com.cyberiansoft.test.targetprocessintegration.dto.TestCaseRunDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunsDTO;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.UpdateWorkSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseDetailsSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class FindWorkOrdersTestCase extends BaseTestClass {

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = ManheimTestCasesDataPaths.getInstance().getFindWorkOrdersTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanFindWorkOrdersOnBOAndDevice(String rowID,
                                                   String description, JSONObject testData) throws IOException, UnirestException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();

        /*TPIntegrationService tpIntegrationService = new TPIntegrationService();
        TestPlanRunsDTO testPlanRunsDTO = tpIntegrationService.getTestPlanRunStatuses("107123");
        int firstRunNumbers = testPlanRunsDTO.getItems().size();

        boolean runTCs = true;
        for (int i = 0; i <= 20; i++) {
            if (!runTCs) {
                testPlanRunsDTO = tpIntegrationService.getTestPlanRunStatuses("107123");
                int currentRunsNumber = testPlanRunsDTO.getItems().size();
                if (currentRunsNumber > firstRunNumbers) {
                    runTCs = true;
                } else {
                    BaseUtils.waitABit(1000*60*2);
                }

            } else {


                final TestPlanRunDTO testPlanRunDTO = testPlanRunsDTO.getItems().get(0);
                TestCaseRunDTO testCaseRunDTO = testPlanRunDTO.getTestCaseRuns().getItems().stream()
                        .filter(testCaseRun -> testCaseRun.getTestCase().getId().equals(106995))
                        .findFirst().orElseThrow(() -> new RuntimeException("Can't find test cases with ID " + 106995));

                if (testCaseRunDTO.getStatus().equals("Passed")) {
                    String[] stockNumbers = testCaseRunDTO.getComment().split(",");

                    for (String stockNumber : stockNumbers) {
                        stockNumber = stockNumber.replace("<div>", "").replace("</div>", "");
*/

        for (WorkOrderData workOrderData : workOrdersData) {
            Monitoring monitoringData = workOrderData.getMonitoring();
            String stockNumber = monitoringData.getStockNumber();
            WebDriver chromeDriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
            chromeDriver.get("https://manheim-uat.cyberianconcepts.com/");
            VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());

            VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(monitoringData.getLocation());
            VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
            VNextBOROAdvancedSearchDialogInteractions.setStockNum(stockNumber);
            VNextBOROAdvancedSearchDialogInteractions.setRepairStatus("All");
            VNextBOROAdvancedSearchDialogSteps.search();
            VNextBOROPageValidations.validateWorkOrderDisplayedByStockNumber(stockNumber, true);
            final String workOrderId = VNextBOROPageInteractions.getWorkOrderNumberByStockNumber(stockNumber);
            chromeDriver.quit();

            HomeScreenSteps.openUpdateWork();
            UpdateWorkSteps.searchRepairOrder(stockNumber);
            PhaseScreenValidations.validatePhaseWorkOrderID(workOrderId);
            PhaseScreenValidations.validatePhaseStockNumber(stockNumber);
            monitoringData.getOrderPhasesDto().forEach(phaseDto -> {
                PhaseScreenValidations.validatePhaseStatus(phaseDto);
                phaseDto.getPhaseServices().forEach(monitorServiceDTO -> {
                    PhaseScreenValidations.validateServiceStatus(monitorServiceDTO.getMonitorService(), ServiceStatus.getStatus(monitorServiceDTO.getMonitorServiceStatus()));
                });
            });

            WizardScreenSteps.saveAction();
        }
    }
                //}
          //  }
      //  }
    //}
}
