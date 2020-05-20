package com.cyberiansoft.test.vnext.testcases.r360pro.servicerequests;

import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.interactions.servicerequests.ServiceRequestsScreenInteractions;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.servicerequests.ServiceRequestsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.servicerequests.ServiceRequestsScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ServiceRequestsRegressionTestCases extends BaseTestClass {

    @BeforeClass(description = "Service Requests regression test cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getServiceRequestsTestCasesDataPath();
        HomeScreenSteps.openServiceRequests();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateInspectionFromServiceRequest(String rowID,
                                                                    String description, JSONObject testData) {
        ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);

        ServiceRequestsScreenSteps.findServiceRequest(serviceRequestData.getServiceRequestNumber());
        TopScreenPanelSteps.cancelSearch();
        int initialInspectionsCount = ServiceRequestsScreenInteractions.getFirstSRInspectionCount();
        ServiceRequestsScreenSteps.createInspection(serviceRequestData);
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        HomeScreenSteps.openServiceRequests();
        ServiceRequestsScreenValidations.verifyServiceRequestHasInspection(serviceRequestData, initialInspectionsCount + 1);
        ServiceRequestsScreenInteractions.tapOnFirstServiceRequestRecord();
        MenuValidations.menuItemShouldBeVisible(MenuItems.VIEW, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_INPSECTION, true);
        MenuValidations.verifyMenuItemIsVisible("Inspections (" + (initialInspectionsCount + 1) + ")");
        //TODO: Step 14 was skipped due to the bug 126432
        MenuSteps.closeMenu();
        ServiceRequestsScreenSteps.createInspection(serviceRequestData);
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        HomeScreenSteps.openServiceRequests();
        ServiceRequestsScreenValidations.verifyServiceRequestHasInspection(serviceRequestData, initialInspectionsCount + 2);
        ServiceRequestsScreenInteractions.tapOnFirstServiceRequestRecord();
        MenuValidations.menuItemShouldBeVisible(MenuItems.VIEW, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_INPSECTION, true);
        MenuValidations.verifyMenuItemIsVisible("Inspections (" + (initialInspectionsCount + 2) + ")");
        MenuSteps.closeMenu();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}