package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamVehicleInfoTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description="Team Vehicle Info Test Cases")
    public void beforeClass() throws Exception {
    }

    @Test(testName= "Test Case 72794:R360 client - Verify letters I,O,Q are trimmed while manual entry, "
            + "Test Case 72801:R360 client - Verify letters less than 17-digit VIN is treated as valid while manual entry",
            description = "Verify letters I,O,Q are trimmed while manual entry, "
                    + "Verify letters less than 17-digit VIN is treated as valid while manual entry")

    public void testVerifyLettersIOQAreTrimmedWhileManualEntry() {
        final String vinnumber = "AI0YQ56ONJ";
        final String vinnumberTrimmed = "A0Y56NJ";
        final String redRGBColor = "rgba(239, 83, 78, 1)";

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(vinnumber);
        Assert.assertTrue(vehicleinfoscreen.isVINValidationMessageExists());
        Assert.assertEquals(vehicleinfoscreen.getVINValidationMessageBackgroundColor(), redRGBColor);
        vehicleinfoscreen.setVIN(vinnumberTrimmed);
        vehicleinfoscreen.swipeScreenLeft();
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(appiumdriver);
        claimInfoScreen.cancelInspection();
        inspectionscreen.clickBackButton();
    }

    @Test(testName= "Test Case 72805:R360 client - Verify VIN with more than 17 valid digits is trimmed to first 17 characters while manual entry",
            description = "Verify VIN with more than 17 valid digits is trimmed to first 17 characters while manual entry")

    public void testVerifyVINWithMoreThan17ValidDigitsIsTrimmedToFirst17CharactersWhileManualEntry() {
        final String vinnumber = "WDBFA67E1SFA946591";
        final String greyRGBColor = "rgba(211, 211, 211, 1)";

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(vinnumber);
        Assert.assertTrue(vehicleinfoscreen.isVINValidationMessageExists());
        Assert.assertEquals(vehicleinfoscreen.getVINValidationMessageBackgroundColor(), greyRGBColor);
        Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(),vinnumber.substring(0, vinnumber.length()-1));
        vehicleinfoscreen.swipeScreenLeft();
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(appiumdriver);
        claimInfoScreen.cancelInspection();
        inspectionscreen.clickBackButton();
    }

}
