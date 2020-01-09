package com.cyberiansoft.test.manheimintegration.testcases;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.Monitoring;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.manheimintegration.data.ManheimTestCasesDataPaths;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FindWorkOrdersTestCase extends BaseTestClass {

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = ManheimTestCasesDataPaths.getInstance().getFindWorkOrdersTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanFindWorkOrdersonBOAndDevice(String rowID,
                                                                        String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Monitoring monitoringData = workOrderData.getMonitoring();
        final String workOrderId = "O-000-00341";

        WebDriver chromeDriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
        chromeDriver.get("https://manheim-uat.cyberianconcepts.com/");
        VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(monitoringData.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(monitoringData.getStockNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(workOrderId, true));

        chromeDriver.quit();

        HomeScreenSteps.openUpdateWork();
        UpdateWorkSteps.searchRepairOrder(monitoringData.getStockNumber());
        PhaseScreenValidations.validatePhaseWorkOrderID(workOrderId);
        PhaseScreenValidations.validatePhaseVINNumber(monitoringData.getRepairOrderData().getVin());
        PhaseScreenValidations.validatePhaseStockNumber(monitoringData.getStockNumber());
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }
}
