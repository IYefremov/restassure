package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.InspectionMenuSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamWorkOrdersCreateMultipleWOFromInspectionTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-create-multiple-wo-from-inspection-testcases-data.json";

    @BeforeClass(description="Team Work Orders Create Multiple WO From Inspection Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void userCanSelectUnselectServicesInWorkorder(String rowID,
                                                      String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> summaryServiceList = new ArrayList<>();
        summaryServiceList.addAll(workOrderData.getInspectionData().getServicesList());
        summaryServiceList.addAll(workOrderData.getServicesList());

        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        InspectionSteps.openServiceScreen();
        InspectionSteps.selectServices(workOrderData.getInspectionData().getServicesList());
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        WorkOrderSteps.openServiceScreen();
        WorkOrderSteps.verifySelectedOrders(workOrderData.getInspectionData().getServicesList());
        WorkOrderSteps.selectServices(workOrderData.getServicesList());
        WorkOrderSteps.verifySelectedOrders(summaryServiceList);
        WorkOrderSteps.unselectServices(workOrderData.getInspectionData().getServicesList());
        WorkOrderSteps.verifySelectedOrders(workOrderData.getServicesList());
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.workOrderShouldBePresent(workOrderId);
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void userCanCreateWoFromApprovedInspection(String rowID,
                                                         String description, JSONObject testData) {

        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.createWorkOrderMenuItemShouldBeVisible(false);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.createWorkOrderMenuItemShouldBeVisible(true);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.workOrderShouldBePresent(workOrderId);
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrdersScreen.clickBackButton();
    }
}
