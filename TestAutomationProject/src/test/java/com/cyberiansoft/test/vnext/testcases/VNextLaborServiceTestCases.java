package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextLaborServiceTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-calculations-data.json";

    @BeforeClass(description = "Team Inspections Line Approval Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test()
    public void testVerifyCorrectTotalIsShownAfterEditingPercentageServiceInMyInspection() {

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType("O_Kramar");
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN("777777777777777777");
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = inpsctionservicesscreen.
                selectLaborService("Labor_expence");
        laborServiceDetailsScreen.selectPanelAndPart("Front Door", "Refinish Left Front Door Cladding");
        inpsctionservicesscreen = laborServiceDetailsScreen.saveLaborServiceDetails();
        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
        System.out.println("+++++++++++ " + selectedServicesScreen.getSelectedServicePriceValue("Labor_expence"));


    }
}
