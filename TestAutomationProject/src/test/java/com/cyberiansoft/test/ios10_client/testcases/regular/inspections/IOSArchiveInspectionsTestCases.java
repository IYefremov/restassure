package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularHomeScreenSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularInspectionsSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularMyInspectionsSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularNavigationSteps;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyInspectionsScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSArchiveInspectionsTestCases extends IOSRegularBaseTestCase {

    private RetailCustomer testRetailCustomer = new RetailCustomer("Automation", "Retail Customer");

    @BeforeClass(description = "Archive Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getArchiveInspectionsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveAndUnArchiveTheInspection(String rowID,
                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        final String inspNumber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.archiveInspection(inspNumber,
                inspectionData.getArchiveReason());
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(inspNumber, false);
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveInspectionsOnDeviceViaAction(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionsID = new ArrayList<>();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
            vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
            vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
            inspectionsID.add(vehicleScreen.getInspectionNumber());
            RegularInspectionsSteps.saveInspection();
        }
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickActionButton();
        for (String inspectionNumber : inspectionsID) {
            myInspectionsScreen.clickOnInspection(inspectionNumber);
        }
        myInspectionsScreen.clickArchiveInspections();
        myInspectionsScreen.selectReasonToArchive(testCaseData.getArchiveReason());
        for (String inspectionNumber : inspectionsID) {
            RegularMyInspectionsScreenValidations.verifyInspectionPresent(inspectionNumber, false);
        }
        RegularNavigationSteps.navigateBackScreen();
    }
}
