package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.MyInspectionsSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSArchiveInspectionsTestCases extends IOSHDBaseTestCase {

    private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "Simple_PO#_Req");

    @BeforeClass(description = "Archive Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getArchiveInspectionsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveAndUnArchiveTheInspection(String rowID,
                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String archiveReason = "Reason 2";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        String myinspetoarchive = vehicleScreen.getInspectionNumber();
        vehicleScreen.saveWizard();

        NavigationSteps.navigateBackScreen();

        myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        myInspectionsScreen.archiveInspection(myinspetoarchive,
                archiveReason);
        Assert.assertFalse(myInspectionsScreen.isInspectionExists(myinspetoarchive));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveInspectionsOnDeviceViaAction(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionsID = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        NavigationSteps.navigateBackScreen();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
            vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
            vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
            inspectionsID.add(vehicleScreen.getInspectionNumber());
            vehicleScreen.saveWizard();

        }
        myInspectionsScreen.archiveInspections(inspectionsID, testCaseData.getArchiveReason());

        for (String inspectionNumber : inspectionsID) {
            Assert.assertFalse(myInspectionsScreen.isInspectionExists(inspectionNumber));
        }
        NavigationSteps.navigateBackScreen();
    }
}
