package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SinglePageInspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSSinglePageInspectionTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Single Page Inspection Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getSinglePageInspectionTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatSinglePageInspectionIsSavedWithoutCrush(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String firstQuestionTxt = "Warning! " +
                "Question 'Is all good?' in section 'Required trafficlight' should be answered.";

        final String secondQuestionTxt = "Warning! " +
                "Question 'Question 2' in section 'Zayats Section1' should be answered.";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_SINGLE_PAGE);
        SinglePageInspectionScreen singlePageInspectionScreen = new SinglePageInspectionScreen();

        final String inspectionNumber = singlePageInspectionScreen.getInspectionNumber();

        singlePageInspectionScreen.expandToFullScreeenSevicesSection();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(inspectionData.getMatrixServiceData());
        InspectionsSteps.saveInspectionAsFinal();

        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        WizardScreensSteps.clickSaveButton();
        InspectionsSteps.saveInspectionAsFinal();

        AlertsValidations.acceptAlertAndValidateAlertMessage(firstQuestionTxt);
        singlePageInspectionScreen.expandToFullScreeenQuestionsSection();

        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.answerAllIsGoodQuestion();
        InspectionsSteps.saveInspectionAsFinal();
        AlertsValidations.acceptAlertAndValidateAlertMessage(secondQuestionTxt);

        QuestionsScreenSteps.answerQuestion(inspectionData.getQuestionScreenData().getQuestionData());
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.selectInspectionInTable(inspectionNumber);
        myInspectionsScreen.isApproveInspectionMenuActionExists();
        myInspectionsScreen.clickArchiveInspectionButton();
        myInspectionsScreen.selectReasonToArchive(inspectionData.getDeclineReason());
        NavigationSteps.navigateBackScreen();
        settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        NavigationSteps.navigateBackScreen();
    }
}
