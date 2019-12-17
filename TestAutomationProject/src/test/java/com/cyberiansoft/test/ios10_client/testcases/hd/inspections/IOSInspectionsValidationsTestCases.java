package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
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

public class IOSInspectionsValidationsTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Inspections Validations Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInspectionsValidationsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
                                                                                                               String description, JSONObject testData) {
        final String newLineSymbol = "\n";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_VIN_ONLY);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.getVINField().click();
        Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
        vehicleScreen.getVINField().click();
        vehicleScreen.getVINField().sendKeys(newLineSymbol);
        vehicleScreen.cancelOrder();
        NavigationSteps.navigateBackScreen();
    }
}
