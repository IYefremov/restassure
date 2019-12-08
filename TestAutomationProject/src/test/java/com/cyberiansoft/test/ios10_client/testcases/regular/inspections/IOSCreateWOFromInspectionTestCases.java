package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCreateWOFromInspectionTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Create WO From Inspection Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCreateWOFromInspectionTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumberber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.waitServicesScreenLoaded();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumberber);
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumberber);
        approveInspectionsScreen.approveInspectionApproveAllAndSignature();
        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumberber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            if (serviceData.isSelected())
                Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData));
            else
                Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }
}
