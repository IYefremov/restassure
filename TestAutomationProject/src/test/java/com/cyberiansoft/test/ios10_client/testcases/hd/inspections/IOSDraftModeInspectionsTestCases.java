package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSDraftModeInspectionsTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Draft Mode Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getDraftModeInspectionsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR(String rowID,
                                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_DRAFT_MODE);
        InspectionData inspectionData = testCaseData.getInspectionData();

        String inspectionnumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
        servicesScreen.clickSaveAsDraft();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamInspectionsScreen teamInspectionsScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        teamInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        InspectionsSteps.saveInspectionAsFinal();
        Assert.assertTrue(teamInspectionsScreen.isInspectionApproveButtonExists(inspectionnumber));
        teamInspectionsScreen.clickBackServiceRequest();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped(String rowID,
                                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
        SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
        servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
        servicedetailsscreen.saveSelectedServiceDetails();

        ServicesScreenSteps.selectMatrixServiceDataAndSave(inspectionData.getMatrixServiceData());

        servicesScreen.clickSaveAsFinal();
        myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);

        myInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
            if (serviceData.isSelected())
                Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData), "Can't find service:" + serviceData.getServiceName());
            else
                Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData));
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitInspectionsScreenLoaded();
        myInspectionsScreen.selectInspectionForCopy(inspectionNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.TEST_PACK_FOR_CALC);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
            if (serviceData.isSelected())
                Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.waitInspectionsScreenLoaded();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal(String rowID,
                                                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        servicesScreen.clickSaveAsDraft();
        Assert.assertTrue(myInspectionsScreen.isDraftIconPresentForInspection(inspectionNumber));
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.clickActionButton();
        Assert.assertFalse(myInspectionsScreen.isApproveInspectionMenuActionExists());
        Assert.assertFalse(myInspectionsScreen.isCreateWOInspectionMenuActionExists());
        Assert.assertFalse(myInspectionsScreen.isCreateServiceRequestInspectionMenuActionExists());
        Assert.assertFalse(myInspectionsScreen.isCopyInspectionMenuActionExists());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.clickDoneButton();

        myInspectionsScreen.clickHomeButton();
        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        Assert.assertTrue(teamInspectionsScreen.isDraftIconPresentForInspection(inspectionNumber));
        NavigationSteps.navigateBackScreen();
    }
}
